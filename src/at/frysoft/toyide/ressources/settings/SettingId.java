package at.frysoft.toyide.ressources.settings;

import java.awt.*;

/**
 * Created on : 27.05.2018
 * Last update: 27.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingId {

    protected int index;

    protected final String name;

    protected final Object defaultValue;

    public SettingId(String name, Object defaultValue) {
        this.index = 0;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public int getIndex() {
        return index;
    }

    public Setting createSetting() {
        return createSetting(defaultValue);
    }

    public Setting createSetting(Object value) {
        if(value instanceof Integer) return new SettingInteger(name, (int) value);
        if(value instanceof String)  return new SettingString (name, (String) value);
        if(value instanceof Color)   return new SettingColor  (name, (Color) value);
        return null;
    }

}
