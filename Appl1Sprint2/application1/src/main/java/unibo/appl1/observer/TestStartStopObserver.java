package unibo.appl1.observer;

import unibo.basicomm23.utils.ApplAbstractObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class TestStartStopObserver extends ApplAbstractObserver {
    private final Set<String> moveCmds = new HashSet<String>();
    private Vector<String> moveHistory = new Vector<String>();
    private boolean stopped = false;
    private boolean resumed = true;

    public TestStartStopObserver() {
        moveCmds.add("robot-athomebegin");
        moveCmds.add("robot-moving");
        moveCmds.add("robot-stepdone");
        moveCmds.add("robot-stopped");
        moveCmds.add("robot-resumed");
        moveCmds.add("robot-athomeend");
    }

    //permette di riusare l'observer in fase di testing
    public void init() {
        moveHistory = new Vector<String>();
    }

    @Override
    public void update(String msg) {
        if (moveCmds.contains(msg)) {
            moveHistory.add(msg);
            if (msg.equals("robot-stopped")) {
                gotStopped();
            }
            if (msg.equals("robot-resumed")) {
                gotResumed();
            }
        }
    }

    private synchronized void gotStopped() {
        stopped = true;
        resumed = false;
        notifyAll();
    }

    private synchronized void gotResumed() {
        resumed = true;
        stopped = false;
        notifyAll();
    }

}
