package at.frysoft.toyide.toy;

public class Memory {

    private short data[];

    public Memory(int size) {
        data = new short[size];
    }

    public short read(int addr) {
        if(addr > data.length) {
            return -1;
        }
        return data[addr];
    }

    public boolean write(int addr, int data) {
        if(addr > this.data.length) {
            return false;
        }
        this.data[addr] = (short)data;
        return true;
    }

    public void reset() {
        for(int i = 0; i < data.length; ++i) {
            data[i] = 0;
        }
    }

}
