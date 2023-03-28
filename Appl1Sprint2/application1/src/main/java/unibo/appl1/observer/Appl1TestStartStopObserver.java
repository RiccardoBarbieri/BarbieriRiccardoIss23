package unibo.appl1.observer;

import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Appl1TestStartStopObserver extends ApplAbstractObserver {
    private final Set<String> moveCmds = new HashSet<String>();
    private Vector<String> moveHistory = new Vector<String>();
    private boolean stopped = false;
    private boolean resumed = true;

    public Appl1TestStartStopObserver() {
        moveCmds.add("robot-moving");
        moveCmds.add("robot-stopped");
        moveCmds.add("robot-resumed");
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

    public Vector<String> getMoveHistory() {
        return moveHistory;
    }

    public synchronized Vector<String> getMoveHistoryAfterStop() {
        while (!stopped) { //! moveHistory.lastElement().equals("robot-stopped")
            CommUtils.outmagenta("Appl1CoreTestStartStopObserver | wait for stop");
            try {
                wait();
                //CommUtils.delay(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return moveHistory;
    }

    public synchronized void waitUntilResume() {
        while (!(resumed)) {
            CommUtils.outmagenta("Appl1CoreTestStartStopObserver | wait for resume");
            //CommUtils.delay(200);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
