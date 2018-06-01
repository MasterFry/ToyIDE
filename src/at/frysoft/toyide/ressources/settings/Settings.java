package at.frysoft.toyide.ressources.settings;

import at.frysoft.toyide.Log;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.io.*;
import java.util.Vector;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Settings {

    static SettingsContainer settings;

    public final SettingId WORKSPACE = new SettingId("Workspace", System.getProperty("user.dir"));
    public final SettingId INDENT          = new SettingId("Indent", 4);
    public final SettingId FONT_FAMILY     = new SettingId("Font Family", "Monospaced");
    public final SettingId FONT_SIZE       = new SettingId("Font Size", 14);
    public final SettingId COLOR_BG_DARK_1 = new SettingId("Background Color Dark 1", new Color(43, 43, 43));
    public final SettingId COLOR_BG_DARK_2 = new SettingId("Background Color Dark 2", new Color(60, 63, 63));

    public Settings() {
        settings = new SettingsContainer();

        SettingId[] ids = { WORKSPACE, INDENT, FONT_FAMILY, FONT_SIZE, COLOR_BG_DARK_1, COLOR_BG_DARK_2 };

        for(SettingId id : ids)
            settings.add(id);
    }

    public void set(SettingId id, Object value) {
        try {
            settings.set(id, value);
        } catch (SettingsException ex) {
            ex.printStackTrace();
        }
    }

    public Setting get(SettingId id) {
        return settings.get(id);
    }

    public String getString(SettingId id) {
        Setting s = settings.get(id);

        if(!(s instanceof SettingString))
            throw new IllegalArgumentException("Setting: " + id.name + " is not of type String!");

        return ((SettingString) s).getValue();
    }

    public int getInt(SettingId id) {
        Setting s = settings.get(id);

        if(!(s instanceof SettingInteger))
            throw new IllegalArgumentException("Setting: " + id.name + " is not of type Integer!");

        return ((SettingInteger) s).getValue();
    }

    public Color getColor(SettingId id) {
        Setting s = settings.get(id);

        if(!(s instanceof SettingColor))
            throw new IllegalArgumentException("Setting: " + id.name + " is not of type Color!");

        return ((SettingColor) s).getValue();
    }

    public Setting[] getSettings() {
        return settings.getSettings();
    }

    public void saveSettings() {
        try {
            JsonWriter jw = new JsonWriter(new FileWriter("settings.json"));
            jw.setIndent("    ");
            jw.beginObject();

            for(Setting setting : settings.settings) {
                setting.write(jw);
            }

            jw.endObject();
            jw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        try {
            JsonReader jr = new JsonReader(new FileReader("settings.json"));
            jr.beginObject();

            boolean read;
            String name;
            Setting[] settings = getSettings();

            while(jr.hasNext()) {
                name = jr.nextName();
                read = false;

                for(Setting setting : settings) {
                    if(setting.id.name.equals(name)) {
                        read = true;
                        setting.read(jr);
                        break;
                    }
                }

                if(!read)
                    throw new IllegalArgumentException("Invalid Setting: " + name);
            }

            jr.endObject();
            jr.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class SettingsContainer {

        private Vector<Setting> settings;

        private SettingsContainer() {
            settings = new Vector<>();
        }

        private void add(SettingId setting) {
            setting.index = settings.size();
            settings.add(setting.createSetting());
        }

        private Setting get(SettingId id) {
            return settings.get(id.index);
        }

        private void set(SettingId id, Object value) throws SettingsException {
            settings.get(id.index).set(value);
        }

        private Setting[] getSettings() {
            return settings.toArray(new Setting[0]);
        }

    }

}
