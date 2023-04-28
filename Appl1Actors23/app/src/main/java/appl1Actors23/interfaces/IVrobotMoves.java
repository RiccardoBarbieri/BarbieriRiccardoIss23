package appl1Actors23.interfaces;

public interface IVrobotMoves {
    boolean step(int time) throws Exception;

    void turnLeft() throws Exception;

    void turnRight() throws Exception;

    void forward(int time) throws Exception;

    void backward(int time) throws Exception;

    void halt() throws Exception;
}
