package unibo.appl1.http;

import unibo.appl1.observer.ObserverForPath;
import unibo.basicomm23.http.HTTPCommApache;
import unibo.basicomm23.utils.CommUtils;
import unibo.appl1.common.IAppl1Core;
import unibo.appl1.common.IVrobotMoves;
import unibo.supports.VrobotHLMovesHTTPApache;

import java.util.Observable;

public class Appl1Core extends Observable implements IAppl1Core {

    protected boolean started = false;
    protected boolean stopped = false;
    protected IVrobotMoves vrobotMoves;

    private ObserverForPath observerForPath;

    public Appl1Core() {
        configure();
        stopped = false;
    }

    protected void configure() {
        String URL = "localhost:8090/api/move";
        //URL potrebbe essere letto da un file di configurazione
        HTTPCommApache httpSupport = new HTTPCommApache(URL);
        vrobotMoves = new VrobotHLMovesHTTPApache(httpSupport);
        observerForPath = new ObserverForPath();
        addObserver(observerForPath);
    }

    private void updateObservers(String msg) {
        setChanged();
        notifyObservers(msg);
    }

    private void robotMustBeAtHome(String msg) throws Exception {
        boolean b = checkRobotAtHome(msg);
        CommUtils.outblue("robotMustBeAtHome " + msg + " " + b);
        if (b) {
            if (msg.equals("START")) updateObservers("robot-athomebegin");
            else if (msg.equals("END")) updateObservers("robot-athomeend");
        } else {
            throw new Exception("Appl1Core | robot must be at home");
        }
    }

    private boolean checkRobotAtHome(String msg) {
        try {
            vrobotMoves.turnRight();
            boolean res = vrobotMoves.step(200);
            if (res) return false;
            vrobotMoves.turnRight();
            res = vrobotMoves.step(200);
            if (res) return false;
            vrobotMoves.turnLeft();
            vrobotMoves.turnLeft();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void start() throws Exception {
        if (!started) {
            started = true;
            walkAtBoundary();
            started = false;
        } else {
            CommUtils.outyellow("Appl1Core already started");
        }
    }

    public void walkAtBoundary() throws Exception {
        robotMustBeAtHome("START");
        for (int i = 1; i <= 4; i++) {
            walkBySteppingWithStop(i);
            vrobotMoves.turnLeft();
        }
        robotMustBeAtHome("END");
    }

    public void walkBySteppingWithStop(int n) throws Exception {
        CommUtils.outyellow("walkBySteppingWithStop n = " + n);
        boolean stepOk = true;
        while (stepOk) {
            if (stopped) {
                CommUtils.beep();
                updateObservers("robot-stopped");
                waitResume();
            }
            updateObservers("robot-moving");
            stepOk = vrobotMoves.step(350);
            if (stepOk) {
                updateObservers("robot-stepdone");
                CommUtils.outyellow("stepdone");
            } else {
                updateObservers("robot-collision");
                CommUtils.outyellow("collision");
            }
            CommUtils.delay(100);
        }
    }

    public synchronized void waitResume() throws Exception {
        while (stopped) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        updateObservers("robot-resumed");
    }

    public synchronized void resume() {
        stopped = false;
        notifyAll();
    }

    @Override
    public void stop() {
        stopped = true;
        try {
            vrobotMoves.halt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}