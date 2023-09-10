package gc.nicoarbelaez.configs;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.handlers.ConfigHandler;

public abstract class Config {
    private GiftCalendar plugin;
    private ConfigHandler configFile;
    private FileConfiguration config;
    private String fileName;

    public Config(GiftCalendar plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        registerConfig();
    }

    public abstract void load();

    public void registerConfig() {
        configFile = new ConfigHandler(fileName, plugin);
        configFile.registerConfig();
    }

    public void reloadConfig() {
        configFile.reloadConfig();
        loadConfig();
        load();
    }

    public Logger getLogger() {
        return plugin.getLogger();
    }

    public void loadConfig(){
        config = configFile.getConfig();
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public ConfigHandler getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigHandler configFile) {
        this.configFile = configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
