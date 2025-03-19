package bookTrading;

import jade.core.behaviours.Behaviour;

public class RequestPerformer extends Behaviour {
    private boolean finished = false;

    public void action() {
        System.out.println("Performing book request...");
        finished = true;
    }

    public boolean done() {
        return finished;
    }
}
