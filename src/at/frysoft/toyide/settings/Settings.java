package at.frysoft.toyide.settings;

import at.frysoft.toyide.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public abstract class Settings {

    public static final SettingId WORKSPACE;
    public static final SettingId INDENT;

    private static final int SETTINGS_COUNT;
    static {
        int i = 0;
        WORKSPACE   = new SettingId(i++, "Workspace", System.getProperty("user.dir"));
        INDENT      = new SettingId(i++, "Indent", 4);
        SETTINGS_COUNT = i;
    }

    private static SettingsContainer settings = new SettingsContainer();


    public static Setting get(SettingId id) {
        return settings.get(id);
    }

    public static void set(SettingId id, Object value) {
        settings.set(id, value);
    }

    public static Setting[] getSettings() {
        return settings.getSettings();
    }

    public static void saveSettings() {
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

    public static void loadSettings() {
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

    private static class SettingsContainer {

        private Setting[] settings;

        private SettingsContainer() {
            settings = new Setting[SETTINGS_COUNT];
        }

        private Setting[] getSettings() {
            return settings; // TODO clone?
        }

        private Setting get(SettingId id) {
            return settings[id.index];
        }

        private void set(SettingId id, Object value) {
            try { // TODO methode signature ?
                settings[id.index].set(value);
            } catch (SettingsException ex) {
                ex.printStackTrace();
            }
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

    }

}
