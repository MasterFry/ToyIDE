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
public class SettingString extends Setting {

    private String value;

    protected SettingString(SettingId id, String value) {
        super(id);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof String))
            throw new SettingsException("Invalid data type, required: String");
        value = (String) o;
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.name(id.name).value(value);
    }

    @Override
    public void read(JsonReader reader) throws IOException {
        value = reader.nextString();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
