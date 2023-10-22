package gc.nicoarbelaez.configs;

import org.bukkit.configuration.file.FileConfiguration;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.handlers.ConfigHandler;

public abstract class Config {
    private final GiftCalendar plugin;
    private ConfigHandler configManager;
    private FileConfiguration yamlFile;
    private String yamlFileName;

    public Config(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public Config(GiftCalendar plugin, String yamlFileName) {
        this.plugin = plugin;
        this.yamlFileName = yamlFileName;
        registerConfig();
    }

    public abstract void load();

    public void registerConfig() {
        configManager = new ConfigHandler(yamlFileName, plugin);
        configManager.registerConfig();
    }

    public void reloadConfig() {
        configManager.reloadConfig();
        loadConfig();
        load();
    }

    public void loadConfig() {
        yamlFile = configManager.getConfig();
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public ConfigHandler getConfigManager() {
        return configManager;
    }

    public FileConfiguration getYamlFile() {
        return yamlFile;
    }

    public String getYamlFileName() {
        return yamlFileName;
    }
}
