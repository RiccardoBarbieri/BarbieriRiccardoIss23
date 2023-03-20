package unibo;

import unibo.appl1.http.Appl1Core;
import unibo.basicomm23.utils.CommUtils;
import unibo.console.CmdConsoleSimulator;

public class Appl1HTTPSprint2 {
    private Appl1Core appl1Core;
    private CmdConsoleSimulator cmdConsole;

    public Appl1HTTPSprint2() {
        configureTheSystem();
    }

    public static void main(String[] args) throws Exception {
        CommUtils.aboutThreads("Before start - ");
        Appl1HTTPSprint2 appl = new Appl1HTTPSprint2();
        appl.doJob();
        CommUtils.aboutThreads("At end - ");
    }

    private void configureTheSystem() {
        appl1Core = new Appl1Core();
        cmdConsole = new CmdConsoleSimulator(appl1Core);
    }

    public void doJob() throws Exception {
        cmdConsole.activate();   //imvoca start/stop/resume
    }
}