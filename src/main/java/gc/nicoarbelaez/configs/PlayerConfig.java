package gc.nicoarbelaez.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.PlayerModel;

public class PlayerConfig extends Config {
    private List<PlayerModel> players;

    public PlayerConfig(GiftCalendar plugin) {
        super(plugin, "players.yml");
        this.players = new ArrayList<>();
    }

    public void load() {
        getLogger().info("Uploading players...");
        loadConfig();

        String path = "Players";
        if (getConfig().contains(path)) {
            Set<String> playerUUIDs = getConfig().getConfigurationSection(path).getKeys(false);
            for (String playerUUID : playerUUIDs) {
                List<Integer> claimedDays = getConfig().getIntegerList(path + "." + playerUUID + ".claimedDays");
                PlayerModel player = new PlayerModel(playerUUID, claimedDays);

                players.add(player);
            }

            getLogger().info("Players uploaded successfully.");
        } else {
            getLogger().warning("'Players' section not found in players.yml");
        }
    }

    public void save() {
        getLogger().info("Saving players...");

        String path = "Players";
        getConfig().set(path, null);

        for (PlayerModel PlayerModel : players) {
            getConfig().set(path + "." + PlayerModel.getUuid() + ".claimedDays", PlayerModel.getClaimedDays());
        }

        getConfigFile().saveConfig();
        getLogger().info("Players saved successfully.");
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

}
