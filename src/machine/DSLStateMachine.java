package machine;

import gui.Qgui;
import state_machine.MachineInterpreter;
import state_machine.StateMachine;
import state_machine.metamodel.Machine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSLStateMachine implements ActionListener {

    private static MachineInterpreter mo;

    public static void main(String[] args) {
        DSLStateMachine calc = new DSLStateMachine();
        calc.gui = new Qgui(new DSLGUI(),calc);
        mo = new StateMachineBuilder().build();
    }

    // The state machine
    public static class StateMachineBuilder extends StateMachine{
        public MachineInterpreter build(){
            Machine mo = this.machine().
                    state("OFF").initial().
                    when("Power").to("ON", () -> DSLStateMachine.setDisplay("Power on")).
                    state("ON").
                    when("Stop").to("OFF", () -> DSLStateMachine.setDisplay("Turned off")).
                    when("Time").to("OFF", () -> DSLStateMachine.setDisplay("Time OUT")).
                    when("Open").to("PAUSE", () -> DSLStateMachine.setDisplay("Door Open")).
                    state("PAUSE").
                    when("Close").to("ON", () -> DSLStateMachine.setDisplay("Door closed")).
                    when("Stop").to("OFF", () -> DSLStateMachine.setDisplay("You pulled the plug mah higga")).
                    build();

            MachineInterpreter mi = new MachineInterpreter(mo);
            return mi;
        }
    }

    // The GUI
    public static class DSLGUI extends Qgui.GUIBuilder {
        @Override
        public void build() {
            //Trying out something new.
            frame("new").label("text", "Current state: ").label("CurrentState", "?").
                    newline().
                    button("Start").button("Pause").button("Open").button("Close").
                    newline().
                    button("Time").button("Stop").button("Power").
                    newline();
        }
    }

    /**
     * The GUI
     */
    public static Qgui gui;
    private int value = 0;
    /**
     * Respond to GUI events, clicking a button
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String button = event.getActionCommand();
        mo.processEvent(button);
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
