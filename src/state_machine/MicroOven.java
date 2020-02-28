package state_machine;

import state_machine.metamodel.Machine;

public class MicroOven extends StateMachine {

    public static void main(String[] args) {
        new MicroOven().test();
    }

    private void test(){
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
        mi.processEvent("power");
        mi.processEvent("stop");
        mi.processEvent("power");
        mi.processEvent("open");
        mi.processEvent("close");
        mi.processEvent("stop");
        mi.processEvent("stop");
    }
}
