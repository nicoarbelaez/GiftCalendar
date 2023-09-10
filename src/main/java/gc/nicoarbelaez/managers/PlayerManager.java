package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.PlayerModel;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private GiftCalendar plugin;
    private List<PlayerModel> players;

    public PlayerManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.players = plugin.getPlayerConfig().getPlayers();
    }

    public PlayerModel getPlayerFromUUID(String uuid) {
        for (PlayerModel player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public void addClaimedDay(String uuid, int day) {
        PlayerModel player = getPlayerFromUUID(uuid);

        if (player == null) {
            List<Integer> claimedDays = new ArrayList<>();
            claimedDays.add(day);

            players.add(new PlayerModel(uuid, claimedDays));
            return;
        }

        player.getClaimedDays().add(day);
        plugin.getPlayerConfig().save();
    }

    public boolean hasClaimedDay(String uuid, int day) {
        PlayerModel player = getPlayerFromUUID(uuid);

        if (player == null)
            return false;

        return player.getClaimedDays().contains(day);
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

}
