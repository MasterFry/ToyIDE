package at.frysoft.toyide.computer;

import at.frysoft.toyide.computer.memory.Input;
import at.frysoft.toyide.computer.memory.Memory;
import at.frysoft.toyide.computer.memory.Output;

public class Bus {

    private Memory mainMemory;
    private Input input;
    private Output output;

    public Bus(Memory mainMemory) {
        this.mainMemory = mainMemory;
        this.input = new Input();
        this.output = new Output();
    }

    public int read(int addr) {
        if(addr == 0xFF)
            return input.read();
        return mainMemory.read(addr);
    }

    public void write(int addr, int data) {
        if(addr == 0xFF)
            output.write(data);
        else
            mainMemory.write(addr, data);
    }

    public void reset() {
        mainMemory.reset();
        input.reset();
    }

    public Memory getMainMemory() {
        return mainMemory;
    }

    public Input getInput() {
        return input;
    }

    public Output getOutput() {
        return output;
    }

}
