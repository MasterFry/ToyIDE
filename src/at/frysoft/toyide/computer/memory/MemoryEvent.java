package at.frysoft.toyide.computer.memory;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class MemoryEvent {

    private final int address;

    private final int oldValue;
    private final int value;

    private final Memory memory;

    public MemoryEvent(int address, int oldValue, int value, Memory memory) {
        this.address = address;
        this.oldValue = oldValue;
        this.value = value;
        this.memory = memory;
    }

    public MemoryEvent(Memory memory) {
        this(0, 0, 0, memory);
    }

    public int getAddress() {
        return address;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getValue() {
        return value;
    }

    public Memory getMemory() {
        return memory;
    }

}
