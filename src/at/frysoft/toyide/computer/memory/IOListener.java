package at.frysoft.toyide.computer.memory;

public interface IOListener {

    void onRead(int readValue);

    void onWrite(int writeValue);

    void onReset();

    void onClear();

    void onLoad(int[] data);

}
