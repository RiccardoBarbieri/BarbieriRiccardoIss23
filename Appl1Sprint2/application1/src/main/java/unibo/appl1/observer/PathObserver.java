package unibo.appl1.observer;

import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class PathObserver extends ApplAbstractObserver {

    private boolean applIsTerminated;
    private Vector<String> moveHistory = new Vector<String>();
    private Set<String> moveCmds = new HashSet<String>();

    public PathObserver() {
        moveCmds.add("robot-stepdone");
        moveCmds.add("robot-collision");
        moveCmds.add("robot-turnLeft");
        moveCmds.add("robot-athomeend");
    }

    @Override
    public void update(String msg) {
        if (msg.contains("robot-stepdone")) {
            moveHistory.add("w");
        } else if (msg.contains("robot-turnLeft")) {
            moveHistory.add("l");
        } else if (msg.equals("robot-collision")) {
            moveHistory.add("|");
        } else if (msg.equals("robot-athomeend")) {
            setTerminated();
        }
    }

    private synchronized void setTerminated() {
        applIsTerminated = true;
        notifyAll(); //riattiva getPath
    }

    public synchronized String getPath() {
        while (!applIsTerminated) {
            try {
                wait();
            } catch (InterruptedException e) {
                CommUtils.outred("Appl1ObserverForpath: getPath ERROR");
            }
        }
        return getPathAsCompactString();
    }

    private String getPathAsCompactString() {
        String hflat = moveHistory.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", "");
        return hflat;
    }

    public boolean evalBoundaryDone() {
        String hflat = getPath();  //bloccante
        String[] splitted = hflat.toString().split("l");
        boolean boundaryDone = splitted[0].length() == splitted[2].length()
                && splitted[1].length() == splitted[3].length();
        return boundaryDone;
    }
}
