package gc.nicoarbelaez.configs.calendar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.configs.Config;
import gc.nicoarbelaez.handlers.ConfigHandler;
import gc.nicoarbelaez.models.calendar.CalendarConfigModel;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.models.calendar.RewardModel;
import gc.nicoarbelaez.utils.InventoryUtils;

public class CalendarConfig extends Config {
    private static final String CALENDARS_DIR = "calendars";
    private static final String CONFIG_FILE = "config.yml";
    private static final String ITEMS_FILE = "items.yml";
    private static final String SECTION_CONFIG = "calendar-config";
    private static final String SECTION_REWARDS = "rewards";
    private static final String EXAMPLE_CALENDAR = "christmas";

    private final GiftCalendar plugin;
    private Set<CalendarModel> calendars;

    public CalendarConfig(GiftCalendar plugin) {
        super(plugin);
        this.plugin = plugin;
        this.calendars = new HashSet<>();
    }

    public void load() {
        getPlugin().getLogger().info("[CalendarConfig] Loading calendars..."); // TODO: Remove
        File dir = new File(plugin.getDataFolder() + File.separator + CALENDARS_DIR);

        if (!dir.exists()) {
            createDirectory(dir);
        }
        loadCalendars(dir);
        getPlugin().getLogger().info("[CalendarConfig] Calendars loaded!"); // TODO: Remove
    }

    public void reloadConfig() {
        calendars.clear();
        load();
    }

    private void loadCalendars(File dir) {
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            getPlugin().getLogger().info("[CalendarConfig] Loading file: " + file.getName()); // TODO: Remove

            if (!isValidCalendarDirectory(file)) {
                getPlugin().getLogger().warning("[CalendarConfig] File not found: " + file.getName()); // TODO: Remove
                continue;
            }
            String calendarDirName = file.getName();
            CalendarModel calendar = createCalendarFromDirectory(calendarDirName);
            getPlugin().getLogger().info("[CalendarConfig] Calendar loaded: " + calendarDirName); // TODO: Remove
            calendars.add(calendar);
        }
    }

    private boolean isValidCalendarDirectory(File file) {
        File ymlConfig = new File(file.getPath() + File.separator + CONFIG_FILE);
        File ymlItems = new File(file.getPath() + File.separator + ITEMS_FILE);
        return file.isDirectory() && ymlConfig.exists() && ymlItems.exists();
    }

    private CalendarModel createCalendarFromDirectory(String calendarDirName) {
        ConfigHandler yamlConfig = registerConfig(calendarDirName, CONFIG_FILE);
        ConfigHandler yamlItems = registerConfig(calendarDirName, ITEMS_FILE);

        String calendarName = calendarDirName.replace(" ", "_");
        CalendarConfigModel calendarConfig = new CalendarConfigModel(
                yamlConfig.getConfig().getConfigurationSection(SECTION_CONFIG));
        List<RewardModel> rewardList = loadRewardPerDay(yamlConfig);
        int[] validItemSlotsList = loadValidItemSlotsList(yamlConfig);
        CalendarItemConfig calendarItemConfig = new CalendarItemConfig(yamlItems.getConfig());
        calendarItemConfig.loadAllItems();

        CalendarModel calendar = new CalendarModel(
                calendarName,
                calendarConfig,
                rewardList,
                validItemSlotsList,
                calendarItemConfig.getItems(),
                calendarItemConfig.getCustomItems());
        calendar.sortCustomItemByPriority();
        return calendar;
    }

    private ConfigHandler registerConfig(String calendarName, String fileName) {
        getPlugin().getLogger().info("[CalendarConfig] Creating file: " + fileName); // TODO: Remove
        ConfigHandler yamlFile = new ConfigHandler(CALENDARS_DIR + "/" + calendarName, fileName, plugin);
        yamlFile.registerConfig();
        return yamlFile;
    }

    private int[] loadValidItemSlotsList(ConfigHandler yamlConfig) {
        int[] validItemSlotsList;
        List<String> slots = yamlConfig.getConfig().getStringList("calendar-config.slots");
        System.out.print("[CalendarConfig] slots: " + slots); // TODO: Remove
        int size = yamlConfig.getConfig().getInt("calendar-config.size");
        System.out.print("[CalendarConfig] size: " + size); // TODO: Remove

        if (slots == null || slots.isEmpty()) {
            validItemSlotsList = InventoryUtils.generateSlotInventory(size); // TODO: MODIFICAR ESTO
            System.out.print("[CalendarConfig] [1] validItemSlotsList: " + Arrays.toString(validItemSlotsList)); // TODO:
                                                                                                                 // Remove
            System.out.print("[CalendarConfig] [1] size: " + size); // TODO: Remove
        } else {
            validItemSlotsList = InventoryUtils.generateSlotInventory(slots, size); // TODO: MODIFICAR ESTO
            System.out.print("[CalendarConfig] [2] validItemSlotsList: " + Arrays.toString(validItemSlotsList)); // TODO:
                                                                                                                 // Remove
        }

        return validItemSlotsList;
    }

    private List<RewardModel> loadRewardPerDay(ConfigHandler yamlConfig) {
        FileConfiguration config = yamlConfig.getConfig();

        if (!config.contains(SECTION_REWARDS)) {
            getPlugin().getLogger().warning("[CalendarConfig] Rewards not found in config.yml"); // TODO: Remove
            return new ArrayList<>();
        }

        List<RewardModel> rewards = new ArrayList<>();
        Set<String> rewardsDays = config.getConfigurationSection(SECTION_REWARDS).getKeys(false);

        for (String dailyReward : rewardsDays) {
            List<String> rewardsPerDay = config.getStringList(SECTION_REWARDS + "." + dailyReward);
            rewards.add(new RewardModel(Integer.parseInt(dailyReward), rewardsPerDay));
            getPlugin().getLogger().info("[CalendarConfig] Daily reward loaded: " + dailyReward
                    + " with rewards: " + rewardsPerDay); // TODO: Remove
        }
        return rewards;
    }

    private void createDirectory(File dir) {
        try {
            getPlugin().getLogger().info("[CalendarConfig] Creating directory: " + CALENDARS_DIR); // TODO: Remove
            dir.mkdirs();
            getPlugin().getLogger().info("[CalendarConfig] Creating example calendar..."); // TODO: Remove
            registerConfig(EXAMPLE_CALENDAR, CONFIG_FILE);
            registerConfig(EXAMPLE_CALENDAR, ITEMS_FILE);
        } catch (SecurityException e) {
            getPlugin().getLogger().warning("Error creating directory: " + CALENDARS_DIR);
        }
    }

    public Set<CalendarModel> getCalendars() {
        return calendars;
    }

    public void setCalendars(Set<CalendarModel> calendars) {
        this.calendars = calendars;
    }
}
