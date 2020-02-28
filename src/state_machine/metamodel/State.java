package state_machine.metamodel;

import java.util.ArrayList;
import java.util.List;

public class State {
    private String name;
    private List<Transition> trans = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    public void addTransition(Transition t){
        this.trans.add(t);
    }

    public String getName() {
        return name;
    }

    public List<Transition> getTrans() {
        return trans;
    }
}
