package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.CustomItemModel;
import gc.nicoarbelaez.utils.ItemUtils;
import gc.nicoarbelaez.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

import java.util.ArrayList;
import java.util.List;

public class CustomItemManager {
    private GiftCalendar plugin;
    private List<CustomItemModel> items;

    public CustomItemManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.items = plugin.getItemConfig().getItems();
    }

    public ItemStack getItemFromPathName(String path, int day) {
        for (CustomItemModel item : items) {
            if (item.getId().equals(path)) {
                return createItem(item, day);
            }
        }
        return null;
    }

    public ItemStack createItem(CustomItemModel customItem, int day) {
        if (customItem.getName() != null) {
            customItem.setName(TextUtils.formatText(customItem.getName()).replace("%day%", day + ""));
        }

        if (customItem.getLore() != null) {
            List<String> lore = new ArrayList<>();
            for (String loreLine : customItem.getLore()) {
                lore.add(TextUtils.formatText(loreLine).replace("%day%", day + ""));
            }

            customItem.setLore(lore);
        }

        customItem.setAmmount(day);

        ItemStack item = ItemUtils.createItemFromProperties(customItem);

        setNBTItemString(item, "giftcalendar_item_id", customItem.getId());
        setNBTItemInteger(item, "giftcalendar_item_day", day);
        return item;
    }

    /**
     * Set the NBT String to an item
     * 
     * @param item
     * @param key
     * @param value
     */
    public static void setNBTItemString(ItemStack item, String key, String value) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(key, value);
        nbtItem.applyNBT(item);
    }

    /**
     * Set the NBT Integer to an item
     * 
     * @param item
     * @param key
     * @param value
     */
    public static void setNBTItemInteger(ItemStack item, String key, int value) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger(key, value);
        nbtItem.applyNBT(item);
    }

    /**
     * Get the NBT String from an item
     * 
     * @param item
     * @param key
     * @return String
     */
    public static String getNBTItemString(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasTag(key))
            return nbtItem.getString(key);

        return null;
    }

    /**
     * Get the NBT Integer from an item
     * 
     * @param item
     * @param key
     * @return Integer
     */
    public static Integer getNBTItemInteger(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasTag(key))
            return nbtItem.getInteger(key);

        return null;
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public List<CustomItemModel> getItems() {
        return items;
    }

    public void setItems(List<CustomItemModel> items) {
        this.items = items;
    }
}
