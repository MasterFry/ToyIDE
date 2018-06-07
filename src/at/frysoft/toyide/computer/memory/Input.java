package at.frysoft.toyide.computer.memory;

import at.frysoft.toyide.Log;
import at.frysoft.toyide.ressources.R;

import java.io.*;
import java.util.Vector;

public class Input {

    private int index;
    private int[] input;

    private Vector<IOListener> listeners;

    public Input() {
        index = 0;
        input = null;
        listeners = new Vector<>();
    }

    public void reset() {
        index = 0;

        for(IOListener listener : listeners)
            listener.onReset();
    }

    public void clear() {
        index = 0;
        input = null;

        for(IOListener listener : listeners)
            listener.onClear();
    }

    public Integer peek() {
        return peek(index);
    }

    public Integer peek(int index) {
        if(index < input.length)
            return input[index];
        return null;
    }

    public Integer read() {
        if(index >= input.length)
            return null;

        for(IOListener listener : listeners)
            listener.onRead(input[index]);

        return input[index++];
    }

    public void addIOListener(IOListener listener) {
        listeners.add(listener);
    }

    public void removeIOListener(IOListener listener) {
        listeners.remove(listener);
    }

    public boolean load(File file) {
        Vector<Integer> data = new Vector<>();

        if(!file.exists() || file.isDirectory()) {
            Log.err.println(R.strings.file.NOT_EXIST_OR_IS_DIR);
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while((line = br.readLine()) != null)
                if(!line.isEmpty())
                    data.add(Integer.parseInt(line, 16));

            for(IOListener listener : listeners)
                listener.onLoad(input);

            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        } // TODO print msg on invalid number

        return false;
    }

}
