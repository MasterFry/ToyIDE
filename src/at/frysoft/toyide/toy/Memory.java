package at.frysoft.toyide.toy;

public class Memory {

    private int data[];

    public Memory(int size) {
        data = new int[size];
    }

    public int read(int addr) {
        if(addr > data.length) {
            return -1;
        }
        return data[addr];
    }

    public boolean write(int addr, int data) {
        if(addr > this.data.length) {
            return false;
        }
        this.data[addr] = data;
        return true;
    }

    public void reset() {
        for(int i = 0; i < data.length; ++i) {
            data[i] = 0;
        }
    }

}
