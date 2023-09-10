package gc.nicoarbelaez;

import gc.nicoarbelaez.commands.MainCommand;
import gc.nicoarbelaez.configs.ItemConfig;
import gc.nicoarbelaez.configs.MainConfig;
import gc.nicoarbelaez.configs.MessageConfig;
import gc.nicoarbelaez.configs.PlayerConfig;
import gc.nicoarbelaez.listeners.InventoryListener;
import gc.nicoarbelaez.managers.CustomItemManager;
import gc.nicoarbelaez.managers.InventoryManager;
import gc.nicoarbelaez.managers.PlayerManager;
import gc.nicoarbelaez.managers.RewardsManager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GiftCalendar extends JavaPlugin {
    public static String PLUGIN_NAME;
    public static String PLUGIN_VERSION;

    private ItemConfig itemConfig;
    private MainConfig mainConfig;
    private PlayerConfig playerConfig;
    private MessageConfig messageConfig;

    private CustomItemManager customItemManager;
    private InventoryManager inventoryManager;
    private PlayerManager playerManager;
    private RewardsManager rewardsManager;

    @Override
    public void onEnable() {
        PLUGIN_NAME = this.getName();
        PLUGIN_VERSION = this.getDescription().getVersion();

        this.itemConfig = new ItemConfig(this);
        this.mainConfig = new MainConfig(this);
        this.playerConfig = new PlayerConfig(this);
        this.messageConfig = new MessageConfig(this);

        this.customItemManager = new CustomItemManager(this);
        this.inventoryManager = new InventoryManager(this);
        this.playerManager = new PlayerManager(this);
        this.rewardsManager = new RewardsManager(this);

        this.itemConfig.load();
        this.mainConfig.load();
        this.playerConfig.load();
        this.messageConfig.load();

        this.getLogger().info(
                GiftCalendar.PLUGIN_NAME + " v" + GiftCalendar.PLUGIN_VERSION + " se esta ejecutando en el servidor.");

        registerComamnd();
        registerListeners();
    }

    @Override
    public void onDisable() {
        playerConfig.save();
        this.getLogger().info("Gracias por usar el plugin.");
    }

    /**
     * Register the events of the plugin
     */
    public void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryListener(this), this);
    }

    /**
     * Register the command of the plugin
     */
    public void registerComamnd() {
        MainCommand mainCommand = new MainCommand(this);
        this.getCommand("calendar").setExecutor(mainCommand);
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public ItemConfig getItemConfig() {
        return itemConfig;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public RewardsManager getRewardsManager() {
        return rewardsManager;
    }
}
