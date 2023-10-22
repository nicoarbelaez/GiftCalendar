package gc.nicoarbelaez.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.managers.CalendarInventoryManager;
import gc.nicoarbelaez.managers.ItemManager;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.utils.CalendarUtils;

public class InventoryListener implements Listener {
    private GiftCalendar plugin;

    public InventoryListener(GiftCalendar plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        plugin.getCalendarInventoryManager().removePlayer(player);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        CalendarInventoryManager calendarInventoryManager = plugin.getCalendarInventoryManager();

        if (calendarInventoryManager.isPlayerInList(player)) {
            event.setCancelled(true);
        }

        if (event.getCurrentItem() == null || event.getSlotType() == null
                || event.getCurrentItem().getItemMeta() == null) {
            return;
        }

        if (event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())) {
            ItemStack item = event.getCurrentItem();
            String calendarName = ItemManager.getNBTItemString(item, "giftcalendar_item_calendarName");
            String itemId = ItemManager.getNBTItemString(item, "giftcalendar_item_id");
            int day = ItemManager.getNBTItemInteger(item, "giftcalendar_item_day");
            int page = ItemManager.getNBTItemInteger(item, "giftcalendar_item_page");
            CalendarModel calendar = CalendarUtils.findCalendar(calendarName,
                    plugin.getCalendarConfig().getCalendars());

            System.out.print("[InventoryListener] calendarName: " + calendarName); // TODO: Remove
            System.out.print("[InventoryListener] itemId: " + itemId); // TODO: Remove
            System.out.print("[InventoryListener] day: " + day); // TODO: Remove

            switch (itemId) {
                case "available-gift":
                    plugin.getPlayerManager().addClaimedDay(player.getUniqueId().toString(), calendarName, day);
                    plugin.getPlayerConfig().save();
                    plugin.getRewardsManager().executerCommandsPlayer(player, calendar, day);
                    plugin.getCalendarInventoryManager().openCalendarInventory(calendar, player, page);
                    System.out.print("[InventoryListener] page: " + page); // TODO: Remove
                    break;
                case "next-panel":
                    plugin.getCalendarInventoryManager().openCalendarInventory(calendar, player, page + 1);
                    System.out.print("[InventoryListener] page: " + (page + 1)); // TODO: Remove
                    break;
                case "close":
                    player.closeInventory();
                    break;
                case "previous-panel":
                    plugin.getCalendarInventoryManager().openCalendarInventory(calendar, player, page - 1);
                    System.out.print("[InventoryListener] page: " + (page - 1)); // TODO: Remove
                    break;
                default:
                    return;
            }
        }
    }
}
