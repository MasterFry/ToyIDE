package at.frysoft.toyide.computer.memory;

import at.frysoft.toyide.Log;

import java.util.Vector;

public class Output {

    private Vector<IOListener> listeners;

    public Output() {
        listeners = new Vector<>();
    }

    public void write(int data) {
        for(IOListener listener : listeners)
            listener.onWrite(data);

        Log.out.println("[STDOUT]: " + data);
    }

    public void addIOListener(IOListener listener) {
        listeners.add(listener);
    }

    public void removeIOListener(IOListener listener) {
        listeners.remove(listener);
    }

}
