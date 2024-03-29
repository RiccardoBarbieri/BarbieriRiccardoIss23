package ProdConsFsm23;

import unibo.actors23.Actor23Utils;
import unibo.actors23.ActorContext23;
import unibo.actors23.annotations.State;
import unibo.actors23.annotations.Transition;
import unibo.actors23.annotations.TransitionGuard;
import unibo.actors23.fsm.ActorBasicFsm23;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ConsumerFsm23 extends ActorBasicFsm23 {
    String data = "";
    IApplMessage alarm;

    public ConsumerFsm23(String name, ActorContext23 ctx) {
        super(name, ctx);
    }

    @State(name = "s0", initial = true)
    @Transition(state = "startwork")  //emptyMove
    protected void s0(IApplMessage msg) {
        CommUtils.outgreen(name + " | s0 initial " + msg);
        alarm = CommUtils.buildEvent(name, "alarm", "toofast");
        Actor23Utils.showSystemConfiguration();
    }

    @State(name = "startwork")
    @Transition(state = "consume", msgId = "prodinfo")
    protected void startwork(IApplMessage msg) {
        CommUtils.outgreen(name + " | startwork " + msg);
    }

    @State(name = "consume")
    @Transition(state = "consume", msgId = "prodinfo", guard = "sizeinlimit")
    protected void consume(IApplMessage msg) {
        CommUtils.outgray(name + " | consume " + msg);
        data = data + "|" + msg.msgContent();
        CommUtils.outgreen(name + " | data=" + data);
    }

    @TransitionGuard
    protected boolean sizeinlimit() {
        CommUtils.outblack("sizeinlimit data.length()=" + data.length());
        if (data.length() == 4) {
            CommUtils.outred(name + " emit: " + alarm);
            emit(alarm);
        }
        boolean sizeok = data.length() < 10;
        if (!sizeok) {
            CommUtils.outred("limite raggiunto: " + data.length());
        }
        return sizeok;
    }

}
