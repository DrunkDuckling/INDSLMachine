package state_machine;

import state_machine.metamodel.Machine;
import state_machine.metamodel.State;
import state_machine.metamodel.Transition;

public class MachineInterpreter {
    private Machine machine;
    private State currentState;

    public MachineInterpreter(Machine machine) {
        this.machine = machine;
        currentState = machine.getInitialState();
    }

    public void reset(){
        currentState = machine.getInitialState();
    }
    public void processEvent(String event){
        for (Transition t: currentState.getTrans()){
            if (t.getEvent().equals(event)){
                t.effect();
                currentState = t.getTo();
                return;
            }
        }
        System.out.println("Unhandled event: " + event);
    }
}
