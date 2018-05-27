package at.frysoft.toyide.settings;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public abstract class Setting {

    public final String name;

    protected Setting(String name) {
        this.name = name;
    }

    public abstract void set(Object o) throws SettingsException;
    public abstract void set(String s) throws SettingsException;

    @Override
    public abstract String toString();

}
