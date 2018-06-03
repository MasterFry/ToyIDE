package at.frysoft.toyide.toy.action;

import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;

/**
 * Created on : 01.06.2018
 * Last update: 01.06.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class WriteAction implements Action {

    private final Memory memory;

    private final int addressBitMask;

    public WriteAction(Memory memory, int addressBitMask) {
        this.memory = memory;
        this.addressBitMask = addressBitMask;
    }

    @Override
    public Object act(Instruction instruction, Object o) {
        memory.write(instruction.get(addressBitMask), (int) o);
        return null;
    }

}
