package gc.nicoarbelaez.listeners;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.utils.TextUtils;

public class NotifyListen implements Listener {
    private GiftCalendar plugin;

    public NotifyListen(GiftCalendar plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();

        plugin.getPlayerManager().getPlayerModelFromPlayer(player);
        for (Map.Entry<String, List<Integer>> entry : plugin.getPlayerManager().getPlayerModelFromPlayer(player)
                .getCalendarRewardsRegister().entrySet()) {
            if (!entry.getValue().contains(currentDay)) {
                String message = "&a&l[!] &aYou have pending rewards in the &e" + entry.getKey() + " &acalendar";
                player.sendMessage(TextUtils.formatText(message));
                continue;
            }
        }
    }
}
