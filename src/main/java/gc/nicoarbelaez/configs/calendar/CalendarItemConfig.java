package gc.nicoarbelaez.configs.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import gc.nicoarbelaez.models.calendar.ItemModel;
import gc.nicoarbelaez.utils.InventoryUtils;

class CalendarItemConfig {
    private static final String SECTION_ITEMS = "items";
    private static final String SECTION_CUSTOM_ITEMS = "custom-items";

    private final FileConfiguration yamlFile;
    private Map<String, ItemModel> items;
    private List<ItemModel> customItems;

    public CalendarItemConfig(FileConfiguration yamlFile) {
        this.yamlFile = yamlFile;
    }

    public void loadAllItems() {
        items = convertItemsToMap(loadItems(SECTION_ITEMS));
        if (yamlFile.contains(SECTION_CUSTOM_ITEMS)) {
            customItems = loadItems(SECTION_CUSTOM_ITEMS);
        }
    }

    private Map<String, ItemModel> convertItemsToMap(List<ItemModel> itemSet) {
        Map<String, ItemModel> itemMap = new HashMap<>();
        for (ItemModel item : itemSet) {
            itemMap.put(item.getId(), item);
        }
        return itemMap;
    }

    public List<ItemModel> loadItems(String section) {
        Set<ItemModel> itemSet = new HashSet<>();
        Set<String> itemIds = yamlFile.getConfigurationSection(section).getKeys(false);

        for (String itemId : itemIds) {
            String itemPath = section + "." + itemId;
            String material = yamlFile.getString(itemPath + ".material").toUpperCase();
            ItemModel item = new ItemModel(itemPath, itemId, material);

            loadItemProperties(item, itemPath);
            itemSet.add(item);
        }
        return new ArrayList<>(itemSet);
    }

    private void loadItemProperties(ItemModel item, String itemPath) {
        if (yamlFile.contains(itemPath + ".value")) {
            item.setValue(yamlFile.getString(itemPath + ".value"));
        }

        if (yamlFile.contains(itemPath + ".owner")) {
            item.setOwner(yamlFile.getString(itemPath + ".owner"));
        }

        if (yamlFile.contains(itemPath + ".name")) {
            item.setName(yamlFile.getString(itemPath + ".name"));
        }

        if (yamlFile.contains(itemPath + ".lore")) {
            item.setLore(yamlFile.getStringList(itemPath + ".lore"));
        }

        if (yamlFile.contains(itemPath + ".glow")) {
            item.setGlow(yamlFile.getBoolean(itemPath + ".glow"));
        }

        if (yamlFile.contains(itemPath + ".ammount")) {
            item.setAmmount(yamlFile.getInt(itemPath + ".ammount"));
        }

        if (yamlFile.contains(itemPath + ".commands")) {
            item.setCommands(yamlFile.getStringList(itemPath + ".commands"));
        }

        if (yamlFile.contains(itemPath + ".priority")) {
            item.setPriority(yamlFile.getInt(itemPath + ".priority"));
        }
        String slotPath = itemPath + ".slot";
        String slotsPath = itemPath + ".slots";

        if (yamlFile.contains(slotPath)) {
            item.setSlots(Arrays.asList(yamlFile.getInt(slotPath)));
        } else if (yamlFile.contains(slotsPath)) {
            item.setSlots(InventoryUtils.expandSlotList(yamlFile.getStringList(slotsPath)));
        }

        System.out.print("[CalendarItemConfig] §6" + item.toString()); // TODO: Remove
    }

    public FileConfiguration getYamlFile() {
        return yamlFile;
    }

    public Map<String, ItemModel> getItems() {
        return items;
    }

    public void setItems(Map<String, ItemModel> items) {
        this.items = items;
    }

    public List<ItemModel> getCustomItems() {
        return customItems;
    }

    public void setCustomItems(List<ItemModel> customItems) {
        this.customItems = customItems;
    }

}
