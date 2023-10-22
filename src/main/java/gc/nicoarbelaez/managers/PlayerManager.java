package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.PlayerModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

public class PlayerManager {
    private GiftCalendar plugin;

    public PlayerManager(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public PlayerModel getPlayerModelFromUUID(String uuid) {
        for (PlayerModel player : plugin.getPlayerConfig().getPlayers()) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public PlayerModel getPlayerModelFromPlayer(Player player){
        return getPlayerModelFromUUID(player.getUniqueId().toString());
    }

    public void addClaimedDay(String uuid, String calendarName, int day) {
        PlayerModel player = getPlayerModelFromUUID(uuid);

        if (player == null) {
            Map<String, List<Integer>> calendarRewardsRegister = new HashMap<>();
            List<Integer> claimedDays = new ArrayList<>();
            claimedDays.add(day);
            calendarRewardsRegister.put(calendarName, claimedDays);

            Set<PlayerModel> players = plugin.getPlayerConfig().getPlayers();
            players.add(new PlayerModel(uuid, calendarRewardsRegister));
        } else {
            Map<String, List<Integer>> calendarRewardsRegister = player.getCalendarRewardsRegister();
            List<Integer> claimedDays = calendarRewardsRegister.get(calendarName);

            if (claimedDays == null) {
                claimedDays = new ArrayList<>();
                calendarRewardsRegister.put(calendarName, claimedDays);
            }

            claimedDays.add(day);
        }
    }

    public boolean hasClaimedDay(String uuid, String calendarName, int day) {
        PlayerModel player = getPlayerModelFromUUID(uuid);

        if (player == null) {
            return false;
        }

        List<Integer> claimedDays = player.getCalendarRewardsRegister().get(calendarName);
        return claimedDays != null && claimedDays.contains(day);
    }
}
