package at.frysoft.toyide.ressources.settings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
            FileWriter fw = new FileWriter(get(WORKSPACE) + "settings.txt");

            for(Setting setting : settings.settings) {
                fw.write(setting.toString() + '\n');
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(get(WORKSPACE) + "settings.txt"));
            String line;
            String[] r;

            while((line = br.readLine()) != null) {
                r = line.split("=", 2);
                settings.set(r[0], r[1]);
            }

            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(SettingsException ex) {
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

        private void set(String name, String value) throws SettingsException {
            for(Setting setting : settings) {
                if(setting.name.equals(name)) {
                    setting.set(value);
                    return;
                }
            }
            throw new SettingsException("Invalid Setting: " + name);
        }

        private Setting[] getSettings() {
            return settings.toArray(new Setting[0]);
        }

    }

}
