package ProdConsInteraction;

public class Producer {
    private final ProducerCaller prodCaller;

    public Producer(String name) {

        ProducerLogic prodLogic = new ProducerLogic();
        prodCaller = new ProducerCaller(name, prodLogic,
                ProdConsConfig.protocol, ProdConsConfig.hostAddr, ProdConsConfig.entry);
    }

    public void start() {
        prodCaller.activate();
    }
}
