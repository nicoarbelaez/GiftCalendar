package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.calendar.CalendarConfigModel;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.models.calendar.ItemModel;
import gc.nicoarbelaez.utils.InventoryUtils;
import gc.nicoarbelaez.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalendarInventoryManager {
    private final GiftCalendar plugin;
    private final List<Player> openInventoryPlayers;
    private Inventory inventory;

    public CalendarInventoryManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.openInventoryPlayers = new ArrayList<>();
    }

    public void openCalendarInventory(CalendarModel calendar, Player player, int page) {
        page = Math.min(Math.max(1, page), calendar.getPages());
        createCalendarInventory(calendar, page, player);
        player.openInventory(inventory);
        openInventoryPlayers.add(player);
    }

    public void removePlayer(Player player) {
        openInventoryPlayers.remove(player);
    }

    public boolean isPlayerInList(Player player) {
        return openInventoryPlayers.contains(player);
    }

    private void createCalendarInventory(CalendarModel calendar, int page, Player player) {
        CalendarConfigModel calendarConfig = calendar.getCalendarConfig();
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();

        int totalDays = calendarConfig.getDays();
        int pages = calendar.getPages();
        int inventorySize = InventoryUtils.calculateAdjustedInventorySize(calendarConfig.getSize());
        String title = TextUtils.formatText(calendarConfig.getCalendarTitle())
                .replace("%day%", String.valueOf(currentDay))
                .replace("%days%", String.valueOf(totalDays))
                .replace("%page%", String.valueOf(page))
                .replace("%pages%", String.valueOf(pages));

        inventory = Bukkit.createInventory(null, inventorySize, title);
        String calendarName = calendar.getCalendarName();
        addCustomItemsForInventories(calendar.getCustomItemList());
        addInteractionItemsForInventories(calendar.getItemMap(), calendarName, page);
        addRewardItemsForInventories(calendar.getItemMap(), calendarName,
                calendar.getRewardSlotsPerPage(), calendarConfig.getDays(),
                page, calendarConfig.isShowDayAsAmount(), player);
    }

    private void addCustomItemsForInventories(List<ItemModel> items) {
        ItemManager itemManager = plugin.getItemManager();
        for (ItemModel item : items) {
            ItemStack itemStack = itemManager.createItem(item);
            List<Integer> slots = item.getSlots();

            addItemsToInventory(inventory, slots, itemStack);
        }
    }

    private void addInteractionItemsForInventories(Map<String, ItemModel> itemMap, String calendarName, int page) {
        ItemManager itemManager = plugin.getItemManager();
        String[] itemsIds = { "previous-panel", "next-panel", "close" };

        for (String itemId : itemsIds) {
            ItemModel itemModel = itemMap.get(itemId);
            ItemStack item = itemManager.createItem(itemModel);
            List<Integer> slots = itemModel.getSlots();
            ItemManager.setNBTItemString(item, "giftcalendar_item_calendarName", calendarName);
            ItemManager.setNBTItemInteger(item, "giftcalendar_item_page", page);
            addItemsToInventory(inventory, slots, item);
        }
    }

    private void addRewardItemsForInventories(Map<String, ItemModel> itemMap, String calendarName,
            int[] rewardItemSlots, int days,
            int page, boolean showDayInAmount, Player player) {
        ItemManager itemManager = plugin.getItemManager();

        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int slotsPerPage = rewardItemSlots.length;
        int startDay = (page - 1) * slotsPerPage + 1;
        int daysToShow = Math.min(slotsPerPage, days - startDay + 1);
        int day = startDay;
        System.out.print("[CalendarConfig] calendarName: " + calendarName); // TODO: Remove
        System.out.print("[CalendarConfig] currentDay: " + currentDay); // TODO: Remove
        System.out.print("[CalendarConfig] slotsPerPage: " + slotsPerPage); // TODO: Remove
        System.out.print("[CalendarConfig] startDay: " + startDay); // TODO: Remove
        System.out.print("[CalendarConfig] daysToShow: " + daysToShow); // TODO: Remove
        System.out.print("[CalendarConfig] day: " + day); // TODO: Remove
        System.out.print("[CalendarConfig] days: " + days); // TODO: Remove
        System.out.print("[CalendarConfig] page: " + page); // TODO: Remove
        System.out.print("[CalendarConfig] showDayInAmount: " + showDayInAmount); // TODO: Remove
        System.out.print("[CalendarConfig] rewardItemSlots: " + Arrays.toString(rewardItemSlots)); // TODO: Remove

        for (int i = 0; i < daysToShow; i++) {
            ItemStack item;
            ItemModel itemModel;

            if (plugin.getPlayerManager().hasClaimedDay(player.getUniqueId().toString(), calendarName, day)) {
                itemModel = itemMap.get("claimed-gift");
            } else if (currentDay == day) {
                itemModel = itemMap.get("available-gift");
            } else if (currentDay > day) {
                itemModel = itemMap.get("lost-gift");
            } else {
                itemModel = itemMap.get("next_gift");
            }

            item = itemManager.createItem(itemModel, day);
            List<Integer> slots = Arrays.asList(rewardItemSlots[i]);

            if (showDayInAmount) {
                item.setAmount(day);
            }
            ItemManager.setNBTItemInteger(item, "giftcalendar_item_day", day);
            ItemManager.setNBTItemString(item, "giftcalendar_item_calendarName", calendarName);
            ItemManager.setNBTItemInteger(item, "giftcalendar_item_page", page);
            addItemsToInventory(inventory, slots, item);
            day++;
        }
    }

    private void addItemsToInventory(Inventory inventory, List<Integer> slots, ItemStack... itemStacks) {
        if (slots == null || itemStacks == null) {
            return;
        }
        for (int i = 0; i < slots.size() && i < itemStacks.length; i++) {
            int slot = slots.get(i);
            if (slot >= 0 && slot < inventory.getSize()) {
                inventory.setItem(slot, itemStacks[i]);
            }
        }
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public List<Player> getOpenInventoryPlayers() {
        return openInventoryPlayers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
