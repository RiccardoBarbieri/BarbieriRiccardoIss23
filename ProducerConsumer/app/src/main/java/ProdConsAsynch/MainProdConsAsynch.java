package ProdConsAsynch;

import ProdConsInteraction.Consumer;
import ProdConsInteraction.ProdConsConfig;
import unibo.basicomm23.msg.ProtocolType;

/*
Il sistema deve dare le stesse risposte per interazioni basate su
    TCP, UDP, COAP
 */
public class MainProdConsAsynch {

    public static void main(String[] args) {
        new MainProdConsAsynch().configureTheSystemFinal();
    }

    public void configureTheSystemFinal() {
        ProdConsConfig.setProtocol(ProtocolType.tcp);  //tcp udp coap
        //Create the producers
        ProducerAsynch producer1 = new ProducerAsynch("prod1");
        ProducerAsynch producer2 = new ProducerAsynch("prod2");
        //Create the consumer
        Consumer consumer = new Consumer("consumer");
        //Activate
        consumer.start();
        producer1.start();
        producer2.start();
    }
}
