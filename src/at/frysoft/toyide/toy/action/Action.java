package at.frysoft.toyide.toy.action;

import at.frysoft.toyide.toy.Instruction;

/**
 * Created on : 01.06.2018
 * Last update: 01.06.2018
 * <p>
 * Contributors:
 * Stefan
 */
public interface Action {

    Object act(Instruction instruction, Object o);

}
