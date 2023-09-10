package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.utils.OtherUtils;
import gc.nicoarbelaez.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private GiftCalendar plugin;
    private List<Player> players;

    public InventoryManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.players = new ArrayList<>();
    }

    public void createInventory(Player player) {
        LocalDate date = LocalDate.now();
        int today = date.getDayOfMonth();
        Month month = date.getMonth();

        String title = plugin.getMainConfig().getConfig().getString("Config.inventoryTitle")
                .replace("%day%", today + "")
                .replace("%day:last%", 24 + "")
                .replace("%month%", month.toString());
        Inventory inv = Bukkit.createInventory(null, 54, TextUtils.formatText(title));
        ArrayList<Integer> slots = new ArrayList<>();
        CustomItemManager customItemManager = plugin.getCustomItemManager();

        OtherUtils.addRangeToList(10, 16, slots);
        OtherUtils.addRangeToList(19, 25, slots);
        OtherUtils.addRangeToList(28, 34, slots);
        OtherUtils.addRangeToList(39, 41, slots);

        int day = 1;
        for (int slot : slots) {
            ItemStack item = null;

            if (plugin.getPlayerManager().hasClaimedDay(player.getUniqueId().toString(), day))
                item = customItemManager.getItemFromPathName("claimed_gift", day);
            else if (day < today)
                item = customItemManager.getItemFromPathName("lost_gift", day);
            else if (day == today)
                item = customItemManager.getItemFromPathName("available_gift", day);
            else if (day > today)
                item = customItemManager.getItemFromPathName("next_gift", day);

            inv.setItem(slot, item);
            day++;
        }

        player.openInventory(inv);
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isPlayerInList(Player player) {
        return players.contains(player);
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
