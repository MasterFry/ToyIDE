package at.frysoft.toyide.ressources.settings;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingInteger extends Setting {

    private int value;

    protected SettingInteger(String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof Integer))
            throw new SettingsException("Invalid data type, required: Integer");
        value = (int) o;
    }

    @Override
    public void set(String s) throws SettingsException {
        try {
            value = Integer.parseInt(s);
        }catch(NumberFormatException ex) {
            throw new SettingsException("Invalid data type, required: Integer");
        }
    }

    @Override
    public String toString() {
        return (name + "=" + value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
