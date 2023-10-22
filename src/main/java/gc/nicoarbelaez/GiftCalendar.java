package gc.nicoarbelaez;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import gc.nicoarbelaez.commands.MainCommand;
import gc.nicoarbelaez.configs.MainConfig;
import gc.nicoarbelaez.configs.MessageConfig;
import gc.nicoarbelaez.configs.PlayerConfig;
import gc.nicoarbelaez.configs.calendar.CalendarConfig;
import gc.nicoarbelaez.listeners.InventoryListener;
import gc.nicoarbelaez.listeners.NotifyListen;
import gc.nicoarbelaez.managers.CalendarInventoryManager;
import gc.nicoarbelaez.managers.ItemManager;
import gc.nicoarbelaez.managers.PlayerManager;
import gc.nicoarbelaez.managers.RewardsManager;

public class GiftCalendar extends JavaPlugin {
    private MainConfig mainConfig;
    private PlayerConfig playerConfig;
    private MessageConfig messageConfig;
    private CalendarConfig calendarConfig;

    private ItemManager itemManager;
    private CalendarInventoryManager calendarInventoryManager;
    private PlayerManager playerManager;
    private RewardsManager rewardsManager;

    @Override
    public void onEnable() {
        String str = "";
        for (int i = 0; i < 100; i++) {
            str += "=";
        }
        System.out.print(str); // TODO: Remove
        // Load configurations and register commands/events when enabling the plugin
        loadConfigs();
        initializeManagers();
        registerCommands();
        registerListeners();
        getLogger().info(getName() + " v" + getDescription().getVersion() + " is running.");
        System.out.print(str); // TODO: Remove
    }

    @Override
    public void onDisable() {
        // Save configurations and display a disable message when disabling the plugin
        playerConfig.save();
        getLogger().info("Thank you for using the plugin.");
    }

    private void loadConfigs() {
        // Load plugin configurations from files
        mainConfig = new MainConfig(this);
        playerConfig = new PlayerConfig(this);
        messageConfig = new MessageConfig(this);
        calendarConfig = new CalendarConfig(this);

        mainConfig.load();
        playerConfig.load();
        messageConfig.load();
        calendarConfig.load();
    }

    private void initializeManagers() {
        // Initialize plugin object managers
        itemManager = new ItemManager(this);
        calendarInventoryManager = new CalendarInventoryManager(this);
        playerManager = new PlayerManager(this);
        rewardsManager = new RewardsManager(this);
    }

    private void registerCommands() {
        // Register the main plugin command
        MainCommand mainCommand = new MainCommand(this);
        getCommand("giftcalendar").setExecutor(mainCommand);
    }

    private void registerListeners() {
        // Register event listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new NotifyListen(this), this);
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public CalendarConfig getCalendarConfig() {
        return calendarConfig;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public CalendarInventoryManager getCalendarInventoryManager() {
        return calendarInventoryManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public RewardsManager getRewardsManager() {
        return rewardsManager;
    }
}
