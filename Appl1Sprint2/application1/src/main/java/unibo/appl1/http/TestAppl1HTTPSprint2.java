package unibo.appl1.http;

import org.junit.Before;
import org.junit.Test;
import unibo.appl1.observer.TestStartStopObserver;
import unibo.basicomm23.utils.CommUtils;

import java.util.Vector;

import static org.junit.Assert.fail;

public class TestAppl1HTTPSprint2 {

    private Appl1Core appl;
    private TestStartStopObserver obsStartStop;

    @Before
    protected void initSystemTotest() {
        appl = new Appl1Core(); //Il robot viene verificato in HOME !
        obsStartStop = new TestStartStopObserver();
        obsStartStop.init();
        //AGGIUNGO OSSERVATORE per start/stop
        appl.addObserver(obsStartStop);
        //appl.addObserver(obsPath); //Già aggiunto nel POJO
    }

    private void startTheApplication() {
        new Thread() {
            public void run() {
                try {
                    appl.start();
                } catch (Exception e) {
                    fail("startTheApplication " + e.getMessage());
                }
            }
        }.start();
    }

    private void checkHistoryAfterStop() {
        Vector<String> h = obsStartStop.getMoveHistoryAfterStop();
        assert (h.elementAt(0).equals("robot-athomebegin"));
        assert (h.elementAt(1).equals("robot-moving"));
        assert h.size() <= 3 || (h.elementAt(2).equals("robot-stepdone"));
        //Dopo il secondo item ci possono essere altre coppie robot-moving/robot-stepdone
        assert (h.elementAt(h.size() - 1).equals("robot-stopped"));
    }

    private void checkBoundaryDone() {
        assert (appl.evalBoundaryDone());
    }

    @Test
    public void testStartNoStop(){
        startTheApplication();
        checkBoundaryDone();  //wait
    }


    @Test
    public void testStop(){
        startTheApplication();
        for( int i=1; i<=3; i++ ) {
            CommUtils.delay(3000);
            appl.stop();
            checkHistoryAfterStop();
            CommUtils.delay(1500);
            appl.resume();
            checkResumed();
        }
        checkBoundaryDone();
    }

}
