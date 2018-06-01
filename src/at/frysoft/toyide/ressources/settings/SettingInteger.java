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
public class SettingInteger extends Setting {

    private int value;

    protected SettingInteger(SettingId id, int value) {
        super(id);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof Integer))
            throw new SettingsException("Invalid data type, required: Integer");
        value = (int) o;
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.name(id.name).value(value);
    }

    @Override
    public void read(JsonReader reader) throws IOException {
        value = reader.nextInt();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
