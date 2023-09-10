package gc.nicoarbelaez.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.managers.CustomItemManager;
import gc.nicoarbelaez.managers.InventoryManager;

public class InventoryListener implements Listener {
    private GiftCalendar plugin;

    public InventoryListener(GiftCalendar plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        plugin.getInventoryManager().removePlayer(player);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryManager inventoryManager = plugin.getInventoryManager();

        if (inventoryManager.isPlayerInList(player))
            event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getSlotType() == null
                || event.getCurrentItem().getItemMeta() == null)
            return;

        if (event.getClickedInventory().equals(player.getOpenInventory().getTopInventory())) {
            ItemStack item = event.getCurrentItem();
            String itemId = CustomItemManager.getNBTItemString(item, "giftcalendar_item_id");
            int day = CustomItemManager.getNBTItemInteger(item, "giftcalendar_item_day");

            if (itemId == null || !itemId.equals("available_gift"))
                return;

            plugin.getPlayerManager().addClaimedDay(player.getUniqueId().toString(), day);
            plugin.getPlayerConfig().save();
            plugin.getRewardsManager().executerCommandsPlayer(player, day);
            plugin.getInventoryManager().createInventory(player);
        }
    }
}
