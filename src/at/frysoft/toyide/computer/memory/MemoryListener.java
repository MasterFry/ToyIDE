package at.frysoft.toyide.computer.memory;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public interface MemoryListener {

    void onMemoryChanged(MemoryEvent e);

    void onMemoryImageLoaded(MemoryEvent e);

    void onMemoryReset();

}
