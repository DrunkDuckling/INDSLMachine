package machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main implements ActionListener {

    public static void main(String[] args) {
        main calc = new main();
        calc.gui = new Qgui(new CountingGUI(),calc);
    }

    public static class CountingGUI extends Qgui.GUIBuilder {
        @Override
        public void build() {
            /*frame("Counter").
                    label("display", "0").
                    newline().
                    button("Less").button("More").button("1").
                    newline().
                    button("Reset").button("Quit").
                    newline();*/

            frame("new").
                    label("textfield", "What is: ").label("display", "0").
                    newline().
                    button("1").button("2").button("3").
                    newline().
                    button("4").button("5").button("6").
                    newline().
                    button("7").button("8").button("9").
                    newline().
                    button("GO").button("Reset").button("Quit").
                    newline();
        }
    }

    /**
     * The GUI
     */
    private Qgui gui;
    private int value = 0;

    /**
     * Respond to GUI events
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String button = event.getActionCommand();
        if("Less".equals(button)) setDisplay(Integer.toString(--value));
        else if("More".equals(button)) setDisplay(Integer.toString(++value));
        else if("Reset".equals(button)) setDisplay(Integer.toString(value=0));
        else if("Quit".equals(button)) System.exit(0);


        else if("1".equals(button)) setDisplay(Integer.toString(value = value + 1));
        else if("2".equals(button)) setDisplay(Integer.toString(value = value + 2));
        else if("3".equals(button)) setDisplay(Integer.toString(value = value + 3));
        else if("4".equals(button)) setDisplay(Integer.toString(value = value + 4));
        else if("5".equals(button)) setDisplay(Integer.toString(value = value + 5));
        else if("6".equals(button)) setDisplay(Integer.toString(value = value + 6));
        else if("7".equals(button)) setDisplay(Integer.toString(value = value + 7));
        else if("8".equals(button)) setDisplay(Integer.toString(value = value + 8));
        else if("9".equals(button)) setDisplay(Integer.toString(value = value + 9));




        else System.err.println("Warning: unknown event "+event);


    }

    /**
     * Update calculator display
     */
    private void setDisplay(String content) {
        JLabel display = (JLabel)gui.getComponent("display");
        display.setText(content);
    }
}
