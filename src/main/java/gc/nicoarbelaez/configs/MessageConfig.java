package gc.nicoarbelaez.configs;

import gc.nicoarbelaez.GiftCalendar;

public class MessageConfig extends Config{

    public MessageConfig(GiftCalendar plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    public void load() {
        getLogger().info("Uploading messages...");
        loadConfig();
        getLogger().info("Messages uploaded successfully.");
    }
}
