package gc.nicoarbelaez.configs;

import java.util.HashMap;
import java.util.Map;

import gc.nicoarbelaez.GiftCalendar;

public class MessageConfig extends Config {
    private static final String SECTION_MESSAGES = "messages";

    private Map<String, String> messages;

    public MessageConfig(GiftCalendar plugin) {
        super(plugin, "messages.yml");
        this.messages = new HashMap<>();
    }

    @Override
    public void load() {
        getPlugin().getLogger().info("[MessageConfig] Loading messages..."); // TODO: Remove

        super.loadConfig();

        // Load messages from the configuration file
        Map<String, Object> messageSectionValues = super.getYamlFile().getConfigurationSection(SECTION_MESSAGES)
                .getValues(false);
        for (Map.Entry<String, Object> entry : messageSectionValues.entrySet()) {
            messages.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        getPlugin().getLogger().info("[MessageConfig] Messages loaded!"); // TODO: Remove
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
