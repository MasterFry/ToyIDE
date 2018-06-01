package at.frysoft.toyide.ressources.settings;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public abstract class Setting {

    public final SettingId id;

    protected Setting(SettingId id) {
        this.id = id;
    }

    public abstract void set(Object o) throws SettingsException;

    public abstract void write(JsonWriter writer) throws IOException;
    public abstract void read(JsonReader reader) throws IOException;

}
