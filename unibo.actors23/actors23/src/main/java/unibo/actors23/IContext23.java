package unibo.actors23;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;

import java.util.Vector;

public interface IContext23 {
    String getName();

    //public void activate();
    //public void deactivate();
    Vector<String> getLocalActorNames();

    ActorBasic23 getActor(String actorName);

    //public HashMap<String, IApplMessage> getRequestMap();
    void addActor(ActorBasic23 a);

    void removeActor(ActorBasic23 a);

    void showActorNames();

    void setActorAsRemote(String actorName,
                                 String entry, String host, ProtocolType protocol);

    Proxy getProxy(String actorName);

    void propagateEventToActors(IApplMessage event);

    void propagateEventToProxies(IApplMessage event);

    void showProxies();
}
