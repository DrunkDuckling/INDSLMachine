package machine;

import gui.Qgui;
import state_machine.MachineInterpreter;
import state_machine.StateMachine;
import state_machine.metamodel.Machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSLStateMachine implements ActionListener {

    public static void main(String[] args) {
        DSLStateMachine calc = new DSLStateMachine();
        calc.gui = new Qgui(new DSLGUI(),calc);
        // UHMMMMMMM
        new StateMachineBuilder();

    }

    public static class StateMachineBuilder extends StateMachine{
        Machine mo = this.machine().
                state("OFF").initial().
                when("power").to("ON", () -> System.out.println("turned on")).
                state("ON").
                when("stop").to("OFF", () -> System.out.println("Turned off")).
                when("time").to("OFF", () -> System.out.println("Time OUT")).
                when("open").to("PAUSE", () -> System.out.println("Door Open")).
                state("PAUSE").
                when("close").to("ON", () -> System.out.println("Door closed")).
                when("stop").to("OFF", () -> System.out.println("You pulled the plug mah higga")).
                build();

        MachineInterpreter mi = new MachineInterpreter(mo);
    }

    public static class DSLGUI extends Qgui.GUIBuilder {

        @Override
        public void build() {
            //Trying out something new.
            frame("new").label("text", "Current state: ").label("CurrentState", "?").
                    newline().
                    button("Start").button("Stop").button("Open").button("Close").
                    newline().
                    button("Trigger").button("Power On Machine").
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
        if ("Start".equals(button)) setDisplay("HELLOOOOO", 1);



        // ---------------OLD-------------
        // Delete the given result
        /*if("Delete".equals(button)) setDisplay(Integer.toString(value=0), 1);
        // terminate the program
        else if("Quit".equals(button)) System.exit(0);
        else if("GO".equals(button)){
            //Set the machine in "power on" state
            getRandomMath(1);
        }
        else if("=".equals(button)){
            // Compare solution with given result, and place it in the correct state if result is correct.
            getRandomMath(2);
        }*/


        else System.err.println("Warning: unknown event "+event);


    }

    /**
     * Update calculator display
     */
    private void setDisplay(String content, int c) {
        if (c == 1) {
            JLabel display = (JLabel) gui.getComponent("CurrentState");
            display.setText(content);
            /*case 2:{
                JLabel display = (JLabel)gui.getComponent("Problem");
                display.setText(content);
            }
            case 3:{
                JLabel display = (JLabel)gui.getComponent("Result");
                display.setText(content);
            }*/
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
