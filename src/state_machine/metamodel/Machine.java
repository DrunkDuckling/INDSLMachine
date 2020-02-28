package state_machine.metamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Machine {
    private List<State> states = new ArrayList<>();
    private State initialState;

    public Machine(Collection<State> states, State initialState) {
        this.states.addAll(states);
        this.initialState = initialState;
    }

    public List<State> getStates() {
        return states;
    }

    public State getInitialState() {
        return initialState;
    }
}
