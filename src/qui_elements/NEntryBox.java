package qui_elements;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class NEntryBox extends NComponents {

    private String name, text;

    public NEntryBox(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Override
    protected JComponent create(ActionListener handler, Map<String, JComponent> componentMap) {
        JTextField textField = new JTextField();
        textField.setToolTipText(text);
        textField.setActionCommand(name);
        textField.addActionListener(handler);
        if (name != null) componentMap.put(name, textField);

        return textField;
    }

    @Override
    public String toString() {
        return "NEntryBox{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
