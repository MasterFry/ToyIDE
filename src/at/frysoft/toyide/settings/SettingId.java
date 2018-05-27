package at.frysoft.toyide.settings;

/**
 * Created on : 27.05.2018
 * Last update: 27.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingId {

    public final int index;

    public final String name;

    public final Object defaultValue;

    public SettingId(int index, String name, Object defaultValue) {
        this.index = index;
        this.name = name;
        this.defaultValue = defaultValue;
    }

}
