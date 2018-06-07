package at.frysoft.toyide.computer;

import at.frysoft.toyide.computer.cpu.CPU;
import at.frysoft.toyide.computer.cpu.SToy;
import at.frysoft.toyide.computer.cpu.Toy;
import at.frysoft.toyide.computer.memory.Memory;

public class Computer {

    public static final int CPU_TOY = 0;
    public static final int CPU_STOY = 1;

    private Bus bus;
    private CPU cpu;

    public Computer(int type) {
        switch(type) {

            case CPU_TOY:
                bus = new Bus(new Memory(0x10, 0x100));
                cpu = new Toy(new Memory(0x10, 0x10), bus, 0x10);
                break;

            case CPU_STOY:
                bus = new Bus(new Memory(0x10, 0x100));
                cpu = new SToy(new Memory(0x10, 0x10), bus, 0x10);
                break;

        }
    }

    public void reset() {
        bus.reset();
        cpu.reset();
    }

    public Bus getBus() {
        return bus;
    }

    public CPU getCpu() {
        return cpu;
    }

}
