package at.frysoft.toyide.ressources.settings;

import java.awt.*;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingColor extends Setting {

    private Color value;

    protected SettingColor(String name, Color value) {
        super(name);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof Color))
            throw new SettingsException("Invalid data type, required: String");
        value = (Color) o;
    }

    @Override
    public void set(String s) throws SettingsException {
        throw new SettingsException("ColorSetting#set(String) should not be used");
    }

    @Override
    public String toString() {
        return (name + "=" + value);
    }

    public void setValue(Color value) {
        this.value = value;
    }

    public Color getValue() {
        return value;
    }

}
