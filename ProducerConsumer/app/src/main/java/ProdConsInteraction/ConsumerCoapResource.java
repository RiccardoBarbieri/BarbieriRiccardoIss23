package ProdConsInteraction;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import unibo.basicomm23.coap.CoapInteraction;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
import java.text.SimpleDateFormat;

public class ConsumerCoapResource  extends CoapResource implements IApplMsgHandler {
    private static final SimpleDateFormat sdf3 =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ConsumerLogic consunerLogic;

    public ConsumerCoapResource( String name, ConsumerLogic consunerLogic  ) {
        super(name);
        this.consunerLogic = consunerLogic;
    }
    @Override
    public void handleGET(CoapExchange exchange) {
        CommUtils.outgray(getName() + " | ConsumerCoapResource handleGET " + exchange.getRequestText());
        IApplMessage message = new ApplMessage(exchange.getRequestText());
        //buildAnswer(exchange);
        elaborate( message, new CoapInteraction(exchange));
    }

    public void handlePOST(CoapExchange exchange) {
        CommUtils.outgray(getName() + " | ConsumerCoapResource handlePOST " + exchange.getRequestText());
        exchange.accept();

        //List<String> queries = exchange.getRequestOptions().getURIQueries();
        // ...
        exchange.respond(CoAP.ResponseCode.CREATED);
    }

    public void handlePUT(CoapExchange exchange) {
        // ...
        CommUtils.outgray(getName() + " | ConsumerCoapResource handlePUT " + exchange.getRequestText()); //.getRequestText()
        //exchange.respond(CoAP.ResponseCode.CHANGED);
        //changed(); // notify all observers
        //buildAnswer(exchange);
        IApplMessage message = new ApplMessage(exchange.getRequestText());
        elaborate( message, new CoapInteraction(exchange));

    }

    protected void buildAnswer(CoapExchange exchange){
        try {
            IApplMessage message = new ApplMessage(exchange.getRequestText());
            String m             = consunerLogic.evalDistance(message.msgContent());
            //CommUtils.outgray(m);
            IApplMessage reply = CommUtils.buildReply("consumer", "outdata", m, message.msgSender());
            CommUtils.outgray(getName() + " | ConsumerCoapResource build reply: " + reply.toString());
            exchange.respond( reply.toString() );
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void elaborate(IApplMessage message, Interaction conn) {
        try {
            String m             = consunerLogic.evalDistance(message.msgContent());
            //CommUtils.outgray(m);
            IApplMessage reply = CommUtils.buildReply("consumer", "outdata", m, message.msgSender());
            CommUtils.outgray(getName() + " | ConsumerCoapResource build reply: " + reply.toString());
            conn.reply( reply.toString() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}