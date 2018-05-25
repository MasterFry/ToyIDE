package at.frysoft.toyide.toy1;

/**
 * Created by Stefan on 20.05.2018.
 */
public class PC {

    private int addr;
    private int prevAddr;
    private final int initialAddr;

    public PC(int addr) {
        initialAddr = addr;
    }

    public void reset() {
        this.addr = initialAddr;
        prevAddr = -1;
    }

    public void set(int addr) {
        prevAddr = this.addr;
        this.addr = addr;
    }

    public int get() {
        return addr;
    }

    public int getPrev() {
        return prevAddr;
    }

    public void increment() {
        prevAddr = addr;
        ++addr;
    }

}
