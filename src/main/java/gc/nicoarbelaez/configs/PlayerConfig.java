package gc.nicoarbelaez.configs;

import org.bukkit.configuration.file.FileConfiguration;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.PlayerModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerConfig extends Config {
    private static final String SECTION_PLAYERS = "players";
    private static final String CALENDAR_REWARDS_REGISTER = "calendar-rewards-register";

    private Set<PlayerModel> players;
    private FileConfiguration fileYml;

    public PlayerConfig(GiftCalendar plugin) {
        super(plugin, "players.yml");
        this.players = new HashSet<>();
    }

    @Override
    public void load() {
        // Load player data from configuration file
        players.clear();
        getPlugin().getLogger().info("[PlayerConfig] Loading players..."); // TODO: Remove
        super.loadConfig();
        fileYml = super.getYamlFile();

        if (fileYml.contains(SECTION_PLAYERS)) {
            loadPlayers();
            getPlugin().getLogger().info("[PlayerConfig] Players loaded!"); // TODO: Remove
        } else {
            getPlugin().getLogger().info("[PlayerConfig] Players not found!"); // TODO: Remove
        }
    }

    private void loadPlayers() {
        // Load player data from the configuration file
        Set<String> playerUUIDs = fileYml.getConfigurationSection(SECTION_PLAYERS).getKeys(false);

        for (String playerUUID : playerUUIDs) {
            getPlugin().getLogger().info("[PlayerConfig] Loading player: " + playerUUID); // TODO: Remove
            Map<String, List<Integer>> calendarRewardsRegisterMap = loadCalendarRewards(playerUUID);
            PlayerModel player = new PlayerModel(playerUUID, calendarRewardsRegisterMap);
            players.add(player);
            getPlugin().getLogger().info("Player loaded: " + player.getUuid() + "\n"
                    + "Calendar rewards register: " + player.getCalendarRewardsRegister()); // TODO: Remove
        }
    }

    private Map<String, List<Integer>> loadCalendarRewards(String playerUUID) {
        // Load calendar rewards data for a player
        String patchCalendarRecord = SECTION_PLAYERS + "." + playerUUID + "." + CALENDAR_REWARDS_REGISTER;
        Set<String> calendarRewardsRegister = fileYml.getConfigurationSection(patchCalendarRecord).getKeys(false);
        Map<String, List<Integer>> calendarRewardsRegisterMap = new HashMap<>();

        for (String calendar : calendarRewardsRegister) {
            List<Integer> claimedDays = fileYml.getIntegerList(patchCalendarRecord + "." + calendar);
            calendarRewardsRegisterMap.put(calendar, claimedDays);
        }

        return calendarRewardsRegisterMap;
    }

    public void save() {
        // Save player data to the configuration file
        getPlugin().getLogger().info("Saving players...");
        fileYml.set(SECTION_PLAYERS, null);

        for (PlayerModel player : players) {
            savePlayer(player);
        }
        super.getConfigManager().saveConfig();
        getPlugin().getLogger().info("Players saved successfully.");
    }

    private void savePlayer(PlayerModel player) {
        // Save a player's calendar rewards data to the configuration file
        String patchCalendarRecord = SECTION_PLAYERS + "." + player.getUuid() + "." + CALENDAR_REWARDS_REGISTER;

        for (Map.Entry<String, List<Integer>> calendars : player.getCalendarRewardsRegister().entrySet()) {
            String calendarName = calendars.getKey();
            List<Integer> claimedDays = calendars.getValue();
            fileYml.set(patchCalendarRecord + "." + calendarName, claimedDays);
        }
    }

    public Set<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerModel> players) {
        this.players = players;
    }

    public FileConfiguration getFileYml() {
        return fileYml;
    }

    public void setFileYml(FileConfiguration fileYml) {
        this.fileYml = fileYml;
    }
}
