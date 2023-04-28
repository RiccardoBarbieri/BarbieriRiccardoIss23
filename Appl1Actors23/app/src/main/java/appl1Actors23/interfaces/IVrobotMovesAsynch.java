package appl1Actors23.interfaces;

public interface IVrobotMovesAsynch extends IVrobotMoves {
    void stepAsynch(int time) throws Exception;

    void setTrace(boolean v);
}
