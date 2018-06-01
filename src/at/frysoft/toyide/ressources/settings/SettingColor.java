package at.frysoft.toyide.ressources.settings;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.IOException;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingColor extends Setting {

    private Color value;

    protected SettingColor(SettingId id, Color value) {
        super(id);
        this.value = value;
    }

    @Override
    public void set(Object o) throws SettingsException {
        if(!(o instanceof Color))
            throw new SettingsException("Invalid data type, required: String");
        value = (Color) o;
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.name(id.name).value(String.format("#%06X", value.getRGB() & 0xFFFFFF));
    }

    @Override
    public void read(JsonReader reader) throws IOException {
        value = new Color(Integer.parseInt(reader.nextString().substring(1), 16));
    }

    public void setValue(Color value) {
        this.value = value;
    }

    public Color getValue() {
        return value;
    }

}
