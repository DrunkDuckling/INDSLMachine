package state_machine.metamodel;

public class Transition {
    private String event;
    private State to;
    private Runnable effect;

    public Transition(String event, State to, Runnable effect) {
        this.event = event;
        this.to = to;
        this.effect = effect;
    }
    public String getEvent() {
        return event;
    }
    public State getTo() {
        return to;
    }
    public void effect(){
        this.effect.run();
    }
}
