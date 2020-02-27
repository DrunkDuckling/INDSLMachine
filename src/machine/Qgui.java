package machine;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


import qui_elements.*;
import qui_elements.NPanel.Layout;

/**
 * NuickGUI allows concise description of a simple GUI (one frame, buttons, labels, organized into panels)
 * to be instantiated as a GUI that can be used from the application.
 * This version uses method seNuence but only allows shallow GUIs to be built
 * @author ups
 */
public class Qgui {

    /**
     * Builder class: provides convenience methods for constructing a GUI metamodel
     * @author ups
     */
    public static abstract class GUIBuilder {
        /**
         * The top-level frame
         */
        private NFrame top;
        /**
         * The top-level panel
         */
        private NPanel panel;
        /**
         * The component currently being added to a row
         */
        private List<NComponents> currentRow = new ArrayList<NComponents>();
        /**
         * Create a frame with the given name and layout, containing the given nested model components,
         * and store it inside the builder
         * @param name title of the frame
         * @return the builder
         */
        public GUIBuilder frame(String name) {
            top = new NFrame(name);
            panel = new NPanel(Layout.VERTICAL);
            top.add(panel);
            return this;
        }
        /**
         * Get the top-level panel into which all elements must be stored.
         * TODO: remove this method as you solve the assignment
         */
        public NPanel getTopPanel() {
            return panel;
        }
        /**
         * Get the top-level panel into which all elements must be stored
         * TODO: remove this method as you solve the assignment
         */
        public List<NComponents> getCurrentRow() {
            return currentRow;
        }
        /**
         * Flush the components stored in currentRow into a new panel
         * TODO: implement this method
         */
        public GUIBuilder newline() {
            NPanel p0 = new NPanel(Layout.HORIZONTAL);
            for(NComponents c: currentRow) p0.add(c);
            getTopPanel().add(p0);
            currentRow.clear();
            return this;
        }
        /**
         * Create a label with the given properties
         * @param name the name of the label
         * @param text the text of the label
         * @return the builder
         * TODO: implement this method
         */
        public GUIBuilder label(String name, String text) {
            currentRow.add(new NLabel(name,text));
            return this;
        }
        /**
         * Create a label with identical name and text
         * @param text the name and text of the label
         * @return the builder
         */
        public GUIBuilder label(String text) {
            return this.label(text,text);
        }
        /**
         * Create a button with the given properties
         * @param name the name of the button
         * @param text the text of the button
         * @return the builder
         * TODO: implement this method
         */
        public GUIBuilder button(String name, String text) {
            currentRow.add(new NButton(name,text));
            return this;
        }
        /**
         * Create a button with identical name and text
         * @param text the name and text of the button
         * @return the builder
         */
        public GUIBuilder button(String text) {
            return this.button(text,text);
        }
        /**
         * Get the top-level frame
         * @return the top-level frame model
         */
        public NFrame getTop() {
            build();
            return top;
        }
        /**
         * Build a gui model, must have one and only one top-level frame
         */
        public abstract void build();
    }

    /**
     * Map from names to corresponding Swing components
     */
    private Map<String,JComponent> componentMap = new HashMap<String,JComponent>();

    /**
     * Instantiate a Swing GUI based on the given model
     * @param model the model to instantiate from
     * @param handler the handler to use when handling GUI events
     */
    public Qgui(final GUIBuilder model, final ActionListener handler) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildGUI(model.getTop(),handler);
            }
        });
    }

    /**
     * Does the actual task of building the GUI for the given model and handler
     * @param model to build the GUI from
     * @param handler for handling events
     */
    private void buildGUI(NFrame model, ActionListener handler) {
        // Create and set up the window.
        JFrame frame = new JFrame(model.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Content must be a panel
        NPanel panel = (NPanel)model.getContents().get(0);
        JPanel gui = panel.create(handler,componentMap);
        //Create and set up the content pane.
        frame.setContentPane(gui);
        gui.setOpaque(true);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Get the component with a given name
     * @param name the name of the component to find
     * @return the component with matching name, if any, signalling an error otherwise
     */
    public JComponent getComponent(String name) {
        JComponent component = componentMap.get(name);
        if(component==null) throw new Error("Component not found: "+name);
        return component;
    }
}