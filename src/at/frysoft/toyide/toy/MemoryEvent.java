package at.frysoft.toyide.toy;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class MemoryEvent {

    private final int address;

    private final short oldValue;
    private final short value;

    private final Memory memory;

    public MemoryEvent(int address, short oldValue, short value, Memory memory) {
        this.address = address;
        this.oldValue = oldValue;
        this.value = value;
        this.memory = memory;
    }

    public MemoryEvent(Memory memory) {
        this((-1), (short)(-1), (short)(-1), memory);
    }

    public int getAddress() {
        return address;
    }

    public short getOldValue() {
        return oldValue;
    }

    public short getValue() {
        return value;
    }

    public Memory getMemory() {
        return memory;
    }

}
