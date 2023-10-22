package gc.nicoarbelaez.handlers;

import gc.nicoarbelaez.GiftCalendar;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class ConfigHandler {
    private GiftCalendar plugin;
    private FileConfiguration fileConfigDefault;
    private File file;
    private String fileName;
    private String pathFile;
    private boolean firstTime;

    public ConfigHandler(String fileName, GiftCalendar plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.firstTime = true;
    }

    public ConfigHandler(String pathFile, String fileName, GiftCalendar plugin) {
        this.pathFile = pathFile;
        this.fileName = fileName;
        this.plugin = plugin;
        this.firstTime = true;
    }

    /**
     * Register the configuration file.
     */
    public void registerConfig() {
        if (pathFile == null) {
            file = new File(plugin.getDataFolder(), fileName);
        } else {
            file = new File(plugin.getDataFolder() + File.separator + pathFile, fileName);
        }

        if (!file.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
            firstTime = false;
        }
        firstTime = false;
    }

    /**
     * Get the configuration file.
     *
     * @return FileConfiguration
     */
    public FileConfiguration getConfig() {
        if (fileConfigDefault == null) {
            reloadConfig();
        }
        return fileConfigDefault;
    }

    /**
     * Save the configuration file.
     */
    public void saveConfig() {
        try {
            fileConfigDefault.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload the configuration file.
     */
    public void reloadConfig() {
        if (fileConfigDefault == null) {
            if (pathFile == null) {
                file = new File(plugin.getDataFolder(), fileName);
            } else {
                file = new File(plugin.getDataFolder() + File.separator + pathFile, fileName);
            }
        }
        fileConfigDefault = YamlConfiguration.loadConfiguration(file);

        if (firstTime) {
            Reader defConfigStream;
            try {
                if (pathFile == null) {
                    defConfigStream = new InputStreamReader(plugin.getResource(fileName), "UTF8");
                } else {
                    defConfigStream = new InputStreamReader(plugin.getResource(pathFile + "/" + fileName), "UTF8");
                }
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    fileConfigDefault.setDefaults(defConfig);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
