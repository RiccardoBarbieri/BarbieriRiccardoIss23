package unibo.http;

import unibo.basicomm23.http.HTTPCommApache;
import unibo.common.CollisionException;
import unibo.common.IVrobotMoves;
import unibo.supports.VrobotHLMovesHTTPApache;

public class Appl1HTTPSprint1 {
    private IVrobotMoves vr;

    public Appl1HTTPSprint1() {
        configure();
    }

    protected void configure() {
        String URL = "localhost:8090/api/move";
        //URL potrebbe essere letto da un file di configurazione
        HTTPCommApache httpSupport = new HTTPCommApache(URL);
        vr = new VrobotHLMovesHTTPApache(httpSupport);
    }

    public void walkAtBoundary() throws Exception {
        for (int i = 1; i <= 4; i++) {
            walkAheadUntilCollision(i);
            //walkByStepping();
            vr.turnLeft();
        }
    }

    private void walkAheadUntilCollision(int n) throws Exception {
        try {
            vr.forward(2300);
        } catch (CollisionException e) {
            return;
        }
        throw new Exception("no collision");
    }

    public static void main(String[] args) {
        Appl1HTTPSprint1 appl = new Appl1HTTPSprint1();
        try {
            appl.walkAtBoundary();  //ENTRY POINT
        } catch (Exception e) {...}
    }
}
