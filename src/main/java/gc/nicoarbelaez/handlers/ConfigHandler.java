package gc.nicoarbelaez.handlers;

import gc.nicoarbelaez.GiftCalendar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class ConfigHandler {

    private String fileName;
    private FileConfiguration fileConfig = null;
    private File file = null;
    private GiftCalendar plugin;
    private boolean firstTime;

    public ConfigHandler(String fileName, GiftCalendar plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.firstTime = false;
    }

    /**
     * Register the config
     */
    public void registerConfig() {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            firstTime = true;
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    /**
     * Save the config
     */
    public void saveConfig() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the fileConfig
     */
    public FileConfiguration getConfig() {
        if (fileConfig == null) {
            reloadConfig();
        }
        return fileConfig;
    }

    /**
     * Reload the config
     */
    public void reloadConfig() {
        if (fileConfig == null) {
            file = new File(plugin.getDataFolder(), fileName);
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);

        if (firstTime) {
            Reader defConfigStream;
            try {
                defConfigStream = new InputStreamReader(plugin.getResource(fileName), "UTF8");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    fileConfig.setDefaults(defConfig);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
