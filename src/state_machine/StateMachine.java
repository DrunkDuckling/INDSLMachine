package state_machine;

import state_machine.metamodel.Machine;
import state_machine.metamodel.State;
import state_machine.metamodel.Transition;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    protected class MachineBuilder {
        private Map<String,State> states = new HashMap<>();
        private State current;
        private State initial;
        private String currentEvent;

        private State getState(String name){
            if (!states.containsKey(name)) states.put(name, new State(name));
            return states.get(name);
        }
        public MachineBuilder state(String name){
            current = getState(name);
            return this;
        }
        public MachineBuilder initial(){
            initial = current;
            return this;
        }
        public MachineBuilder when(String event){
            currentEvent = event;
            return this;
        }
        public MachineBuilder to(String state, Runnable effect){
            Transition t = new Transition(currentEvent, getState(state), effect);
            current.addTransition(t);
            return this;
        }

        /**
         * TODO - It's not currently working.
         * @param value Current power level of device
         * @param minmax Either the minumum or maximum power level.
         * @param state The state that it should change too
         * @param effect The lambda expression.
         * @return
         */
        public MachineBuilder ifStateEquals(int value, int minmax, String state, Runnable effect){
            if (value == minmax){
                Transition t = new Transition(currentEvent, getState(state), effect);
                current.addTransition(t);
                System.out.println("HEY2");
                return this;
            }
            System.out.println("HEY!");
            return this;
        }
        public Machine build(){
            return new Machine(states.values(), initial);
        }
    }
    protected MachineBuilder machine(){
        return new MachineBuilder();
    }
}
