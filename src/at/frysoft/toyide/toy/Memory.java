package at.frysoft.toyide.toy;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.ressources.R;

import java.io.*;
import java.util.Vector;

public class Memory {

    private int bitMask;

    private int data[];

    private Vector<Integer> lockedAddresses;

    private Vector<MemoryListener> memoryListeners;

    public Memory(int bitWidth, int size) {
        bitMask = (int)Math.pow(2, bitWidth) - 1;
        data = new int[size];
        lockedAddresses = new Vector<>();
        memoryListeners = new Vector<>();
    }

    public void lockAddress(int addr) {
        if(!lockedAddresses.contains(addr))
            lockedAddresses.add(addr);
    }

    public int read(int addr) {
        if(addr >= this.data.length || addr < 0)
            throw new IllegalArgumentException("Memory Address out of Bounds: " + addr);

        return data[addr];
    }

    public void write(int addr, int data) {
        if(addr >= this.data.length || addr < 0)
            throw new IllegalArgumentException("Memory Address out of Bounds: " + addr);

        if(lockedAddresses.contains(addr))
            return;

        int oldData = this.data[addr];
        data &= bitMask;
        this.data[addr] = data;

        if(memoryListeners.size() != 0) {
            MemoryEvent e = new MemoryEvent(addr, oldData, data, this);
            for(MemoryListener l : memoryListeners)
                l.onMemoryChanged(e);
        }
    }

    public void reset() {
        for(int i = 0; i < data.length; ++i) {
            data[i] = 0;
        }

        for(MemoryListener l : memoryListeners)
            l.onMemoryReset();
    }

    public int size() {
        return data.length;
    }

    public void addMemoryListener(MemoryListener memoryListener) {
        memoryListeners.add(memoryListener);
    }

    public void removeMemoryListener(MemoryListener memoryListener) {
        memoryListeners.remove(memoryListener);
    }
    public boolean load(File file) {
        reset();

        if(!file.exists() || file.isDirectory()) {
            Log.err.println(R.strings.file.NOT_EXIST_OR_IS_DIR);
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            String[] r;

            while((line = br.readLine()) != null) {
                r = line.split(": ");
                data[Integer.parseInt(r[0], 16)] = (short)Integer.parseInt(r[1], 16);
            }
            Log.out.println("Done reading .toy File.");

            br.close();

            if(memoryListeners.size() != 0) {
                MemoryEvent e = new MemoryEvent(this);
                for(MemoryListener l : memoryListeners)
                    l.onMemoryImageLoaded(e);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
