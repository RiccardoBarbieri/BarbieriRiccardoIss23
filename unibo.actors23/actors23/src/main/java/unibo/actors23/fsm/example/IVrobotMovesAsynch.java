package unibo.actors23.fsm.example;

public interface IVrobotMovesAsynch {
    boolean step(int time) throws Exception;

    void turnLeft() throws Exception;

    void turnRight() throws Exception;

    void forward(int time) throws Exception;

    void backward(int time) throws Exception;

    void halt() throws Exception;

    void stepAsynch(int time) throws Exception;
}
