package gc.nicoarbelaez.configs;

import java.util.HashMap;
import java.util.Map;

import gc.nicoarbelaez.GiftCalendar;

public class MainConfig extends Config {
    private static final String SECTION_CONFIG = "config";

    private Map<String, Object> config;

    public MainConfig(GiftCalendar plugin) {
        super(plugin, "config.yml");
        this.config = new HashMap<>();
    }

    @Override
    public void load() {
        System.out.print("[MainConfig] §eLoading configuration..."); // TODO: Remove
        super.loadConfig();
        
        // Load configuration from YAML file
        config = super.getYamlFile().getConfigurationSection(SECTION_CONFIG).getValues(false);
        
        System.out.print("[MainConfig] §aConfiguration loaded!"); // TODO: Remove
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
