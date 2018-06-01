package at.frysoft.toyide.ressources.settings;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingString extends Setting {

    private String value;

    protected SettingString(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof String))
            throw new SettingsException("Invalid data type, required: String");
        value = (String) o;
    }

    @Override
    public void set(String s) throws SettingsException {
        value = s;
    }

    @Override
    public String toString() {
        return (name + "=" + value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
