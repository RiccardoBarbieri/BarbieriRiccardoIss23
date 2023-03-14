package unibo.appl1;

import unibo.basicomm23.http.HTTPCommApache;
import unibo.basicomm23.utils.CommUtils;
import unibo.common.CollisionException;
import unibo.common.IAppl1Core;
import unibo.common.IVrobotMoves;
import unibo.supports.VrobotHLMovesHTTPApache;

public class Appl1Core implements IAppl1Core {

    protected boolean started = false;
    protected boolean stopped = false;
    protected IVrobotMoves vrobotMoves;

    private int[] boundarySteps = {0, 0, 0, 0}; //For testing

    public Appl1Core() {
        configure();
        stopped = false;
    }

    protected void configure() {
        String URL = "localhost:8090/api/move";
        //URL potrebbe essere letto da un file di configurazione
        HTTPCommApache httpSupport = new HTTPCommApache(URL);
        vrobotMoves = new VrobotHLMovesHTTPApache(httpSupport);
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
        for (int i = 1; i <= 4; i++) {
            walkBySteppingWithStop();
            vrobotMoves.turnLeft();
        }
    }

    public void walkBySteppingWithStop() throws Exception {
        boolean stepOk = true;
        while (stepOk) {
            stepOk = vrobotMoves.step(350);
            CommUtils.delay(100);
            if (stopped) {
                waitResume();
            }
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
