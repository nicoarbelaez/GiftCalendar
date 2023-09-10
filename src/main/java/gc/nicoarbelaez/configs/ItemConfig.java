package gc.nicoarbelaez.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.CustomItemModel;
import gc.nicoarbelaez.utils.TextUtils;

public class ItemConfig extends Config {
    private List<CustomItemModel> items;

    public ItemConfig(GiftCalendar plugin) {
        super(plugin, "items.yml");
        this.items = new ArrayList<>();
    }

    @Override
    public void load() {
        getLogger().info("Uploading items...");
        loadConfig();

        String path = "Items";
        Set<String> itemIds = getConfig().getConfigurationSection(path).getKeys(false);

        for (String itemId : itemIds) {
            CustomItemModel item = createCustomItemFromConfig(path, itemId);
            items.add(item);
            logItemInfo(item);
        }

        getLogger().info("Items uploaded successfully.");
    }

    private CustomItemModel createCustomItemFromConfig(String path, String id) {
        String itemPath = path + "." + id;
        String material = getConfig().getString(itemPath + ".material").toUpperCase();
        CustomItemModel item = new CustomItemModel(itemPath, id, material);

        if (getConfig().contains(itemPath + ".value")) {
            item.setValue(getConfig().getString(itemPath + ".value"));
        }

        if (getConfig().contains(itemPath + ".name")) {
            item.setName(getConfig().getString(itemPath + ".name"));
        }

        if (getConfig().contains(itemPath + ".lore")) {
            item.setLore(getConfig().getStringList(itemPath + ".lore"));
        }

        if (getConfig().contains(itemPath + ".glow")) {
            item.setGlow(getConfig().getBoolean(itemPath + ".glow"));
        }

        return item;
    }

    private void logItemInfo(CustomItemModel item) {
        String message = TextUtils.formatText("\n"
                + item.getPath() + ":\n"
                + "\t&eid: &f" + item.getId() + "\n"
                + "\t&ematerial: &f" + item.getMaterial() + "\n"
                + "\t&evalue: &f" + item.getValue() + "\n"
                + "\t&ename: &f" + item.getName() + "\n"
                + "\t&elore: &f" + item.getLore().toString() + "\n"
                + "\t&eglow: &f" + item.isGlow());
        Bukkit.getConsoleSender().sendMessage(message);
        // getLogger().info(message);
    }

    public List<CustomItemModel> getItems() {
        return items;
    }

    public void setItems(List<CustomItemModel> items) {
        this.items = items;
    }

}
