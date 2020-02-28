package machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSLStateMachine implements ActionListener {

    public static void main(String[] args) {
        DSLStateMachine calc = new DSLStateMachine();
        calc.gui = new Qgui(new CountingGUI(),calc);

    }

    public static class CountingGUI extends Qgui.GUIBuilder {
        @Override
        public void build() {

            frame("new").
                label("text", "What is: ").label("Problem", "").
                newline().
                label("textfield", "Your result: ").label("display", "").label("Result", "").
                newline().
                button("1").button("2").button("3").
                newline().
                button("4").button("5").button("6").
                newline().
                button("7").button("8").button("9").
                newline().
                button("GO").button("=").
                newline().
                button("Delete").button("Quit").
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
        // Delete the given result
        if("Delete".equals(button)) setDisplay(Integer.toString(value=0), 1);
        // terminate the program
        else if("Quit".equals(button)) System.exit(0);
        // The Integer buttons used to type in numbers.
        else if("1".equals(button)) setDisplay(Integer.toString(value = value + 1),1);
        else if("2".equals(button)) setDisplay(Integer.toString(value = value + 2), 1);
        else if("3".equals(button)) setDisplay(Integer.toString(value = value + 3), 1);
        else if("4".equals(button)) setDisplay(Integer.toString(value = value + 4), 1);
        else if("5".equals(button)) setDisplay(Integer.toString(value = value + 5), 1);
        else if("6".equals(button)) setDisplay(Integer.toString(value = value + 6), 1);
        else if("7".equals(button)) setDisplay(Integer.toString(value = value + 7), 1);
        else if("8".equals(button)) setDisplay(Integer.toString(value = value + 8), 1);
        else if("9".equals(button)) setDisplay(Integer.toString(value = value + 9), 1);
        else if("GO".equals(button)){
            //Set the machine in "power on" state
            getRandomMath(1);
        }
        else if("=".equals(button)){
            // Compare solution with given result, and place it in the correct state if result is correct.
            getRandomMath(2);
        }


        else System.err.println("Warning: unknown event "+event);


    }

    /**
     * Update calculator display
     */
    private void setDisplay(String content, int c) {
        switch (c){
            case 1:{
                JLabel display = (JLabel)gui.getComponent("display");
                display.setText(content);
            }
            case 2:{
                JLabel display = (JLabel)gui.getComponent("Problem");
                display.setText(content);
            }
            case 3:{
                JLabel display = (JLabel)gui.getComponent("Result");
                display.setText(content);
            }
        }

    }

    private int getDisplay(){
        JLabel display = (JLabel)gui.getComponent("display");
        String d = display.getText();
        return Integer.parseInt(d.trim());
    }

    private void getRandomMath(int r){
        int number1 = (int)(Math.random()* 20) + 1;
        int number2 = (int)(Math.random()* 20) + 1;
        int operator = (int)(Math.random()*4) + 1;
        int correctResult = 0;
        String operation = "";

        if (r == 1){
            switch (operator){
                case 1: {
                    operation = "+";
                    correctResult = number1 + number2;
                    break;
                }
                case 2: {
                    operation = "-";
                    correctResult = number1 - number2;
                    break;
                }
                case 3: {
                    operation = "/";
                    correctResult = number1 / number2;
                    break;
                }
                case 4: {
                    operation = "*";
                    correctResult = number1 * number2;
                    break;
                } default: break;
            }
            setDisplay(number1 + operation + number2, 2);
        }else if (r == 2){
            if (getDisplay() == correctResult){
                setDisplay("CORRECT!!: ", 3);
            }
        }


        System.out.println("The math problem: " + number1 + operation + number2 + "Correct result: " + correctResult);
    }
}
