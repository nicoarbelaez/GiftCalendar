package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.calendar.ItemModel;
import gc.nicoarbelaez.utils.ItemUtils;
import gc.nicoarbelaez.utils.TextUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import de.tr7zw.nbtapi.NBTItem;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private GiftCalendar plugin;

    public ItemManager(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    /**
     * Get an ItemStack based on the provided itemModels, item ID, and day.
     *
     * @param itemModels List of available item models
     * @param id         Item ID to search for
     * @param day        Day for which the item is created
     * @return ItemStack or null if not found
     */
    public ItemStack getItemFromPathName(List<ItemModel> itemModels, String id, int day) {
        for (ItemModel itemModel : itemModels) {
            if (itemModel.getId().equals(id)) {
                return createItem(itemModel, day);
            }
        }
        return null;
    }

    /**
     * Create an ItemStack based on an ItemModel and a specific day.
     *
     * @param itemModel ItemModel to create the ItemStack from
     * @param day       Day for which the item is created
     * @return ItemStack
     */
    public ItemStack createItem(ItemModel itemModel, int day) {
        ItemModel itemModelCopy = new ItemModel(itemModel);
        replaceDayPlaceholder(itemModelCopy, day);
        return createItemFromModel(itemModelCopy);
    }

    /**
     * Create an ItemStack based on an ItemModel.
     *
     * @param itemModel ItemModel to create the ItemStack from
     * @return ItemStack
     */
    public ItemStack createItem(ItemModel itemModel) {
        replaceDayPlaceholder(itemModel, -1);
        return createItemFromModel(itemModel);
    }

    /**
     * Replace the '%day%' placeholder in the item name and lore.
     *
     * @param itemModel ItemModel to modify
     * @param day       Day for which the item is created (-1 if not applicable)
     */
    private void replaceDayPlaceholder(ItemModel itemModel, int day) {
        if (itemModel.getName() != null) {
            itemModel.setName(TextUtils.formatText(itemModel.getName()).replace("%day%", day >= 0 ? day + "" : ""));
        }

        if (itemModel.getLore() != null) {
            List<String> lore = new ArrayList<>();

            for (String loreLine : itemModel.getLore()) {
                lore.add(TextUtils.formatText(loreLine).replace("%day%", day >= 0 ? day + "" : ""));
            }
            itemModel.setLore(lore);
        }
    }

    /**
     * Create an ItemStack from an ItemModel.
     *
     * @param itemModel ItemModel to create the ItemStack from
     * @return ItemStack
     */
    private ItemStack createItemFromModel(ItemModel itemModel) {
        ItemStack item = ItemUtils.createItemFromModel(itemModel);
        setNBTItemString(item, "giftcalendar_item_id", itemModel.getId());
        return item;
    }

    /**
     * Set an NBT String to an ItemStack.
     *
     * @param item  ItemStack to set NBT data
     * @param key   NBT key
     * @param value NBT value
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
     * Set an NBT Integer to an ItemStack.
     *
     * @param item  ItemStack to set NBT data
     * @param key   NBT key
     * @param value NBT value
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
     * Get an NBT String from an ItemStack.
     *
     * @param item ItemStack to get NBT data from
     * @param key  NBT key
     * @return NBT value as a String
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
     * Get an NBT String from an ItemStack.
     *
     * @param item ItemStack to get NBT data from
     * @param key  NBT key
     * @return NBT value as a Integer
     */
    public static int getNBTItemInteger(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) {
            return 0;
        }

        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasTag(key))
            return nbtItem.getInteger(key);

        return 0;
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }
}
