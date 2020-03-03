package machine;

import gui.Qgui;
import qui_elements.NEntryBox;
import state_machine.MachineInterpreter;
import state_machine.StateMachine;
import state_machine.metamodel.Machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSLStateMachine implements ActionListener {

    /**
     * Allows for the made state machines to be used by the GUI
     */
    private static MachineInterpreter gsm;
    public static Qgui gui;

    public static void main(String[] args) {
        DSLStateMachine calc = new DSLStateMachine();
        gui = new Qgui(new DSLGUI(),calc);
        gsm = new StateMachineBuilder().build();
    }

    /**
     * State machine pre build
     */
    public static class StateMachineBuilder extends StateMachine{
        private int value = 1;
        private static final int MIN_POWER = 0;
        private static final int MAX_POWER = 4;

        public MachineInterpreter build(){
            //Microwave build
            /*Machine mw = this.machine().
                    state("OFF").initial().
                        when("Power").to("ON", () -> DSLStateMachine.setDisplay("Power on")).
                    state("ON").
                        when("Stop").to("OFF", () -> DSLStateMachine.setDisplay("Turned off")).
                        when("Time").to("OFF", () -> DSLStateMachine.setDisplay("Time OUT")).
                        when("Open").to("PAUSE", () -> DSLStateMachine.setDisplay("Door Open")).
                    state("PAUSE").
                        when("Close").to("ON", () -> DSLStateMachine.setDisplay("Door closed")).
                        when("Stop").to("OFF", () -> DSLStateMachine.setDisplay("Stopped")).
                    build();*/

            //extractor hood build; Create some "if" statement that checks a max and min.
            Machine eh = this.machine().
                    state("OFF").initial().
                        when("On").to("ON", () -> DSLStateMachine.setDisplay("Power Level: " + value)).
                        when("Up").to("ON", () -> DSLStateMachine.setDisplay("Power Level: " + value)).
                    state("ON").
                        when("Off").to("OFF", () -> DSLStateMachine.setDisplay("Turned off: " + (value = 0))).
                        when("Up").to("ON", () -> DSLStateMachine.setDisplay("Power Level: " + ++value)).
                            ifStateEquals(value, MAX_POWER, "MAX", () -> DSLStateMachine.setDisplay("Power MAX:")).
                        when("Down").to("ON", () -> DSLStateMachine.setDisplay("Power Level: " + --value)).
                            ifStateEquals(value, MIN_POWER, "OFF", () -> DSLStateMachine.setDisplay("Power off: " + (value = 0))).
                    state("MAX").
                        when("Down").to("ON", () -> DSLStateMachine.setDisplay("Power Level: " + --value)).
                        when("Off").to("OFF", () -> DSLStateMachine.setDisplay("Turned off: " + (value = 0))).
                    build();

            return new MachineInterpreter(eh);
        }
    }

    /**
     * The pre build gui.
     */
    public static class DSLGUI extends Qgui.GUIBuilder {
        @Override
        public void build() {
            //Microwave build
            /*frame("new").label("text", "Current state: ").label("CurrentState", "?").
                    newline().
                    textField("textfield", "Enter command here: ").
                    newline().
                    button("Open").button("Close").
                    newline().
                    button("Time").button("Stop").button("Power").
                    newline();*/

            //extractor hood build
            frame("new").label("text", "Current state: ").label("CurrentState", "?").
                    newline().
                    textField("textfield", "Enter command here: ").
                    newline().
                    button("Up").button("Down").
                    button("On").button("Off").
                    newline();
        }
    }

    /**
     * Respond to GUI events, clicking a button
     * In this case changes the state of the machine.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String button = event.getActionCommand();
        // TODO - creating a textfield seems to cause from problems. cant get written text.
        if ("textfield".equals(button)){

        }else gsm.processEvent(button);
    }

    /**
     * Update calculator display
     * used to set the display when state changes.
     */
    public static void setDisplay(String content) {
            JLabel display = (JLabel) gui.getComponent("CurrentState");
            display.setText(content);
    }
}
