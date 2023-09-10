package gc.nicoarbelaez.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.RewardModel;
import gc.nicoarbelaez.utils.TextUtils;

public class MainConfig extends Config {
    private List<RewardModel> rewards;

    public MainConfig(GiftCalendar plugin) {
        super(plugin, "config.yml");
        this.rewards = new ArrayList<>();
    }

    @Override
    public void load() {
        getLogger().info("Loading configuration...");
        loadConfig();

        String path = "Rewards";
        Set<String> rewardDays = getConfig().getConfigurationSection(path).getKeys(false);

        if (getConfig().contains(path)) {
            for (String rewardDay : rewardDays) {
                List<String> listOfDailyRewards = getConfig().getStringList(path + "." + rewardDay);
                RewardModel rewardModel = new RewardModel(Integer.parseInt(rewardDay), listOfDailyRewards);

                rewards.add(rewardModel);
                logConfigInfo(rewardModel);
            }
        } else {
            getLogger().warning("'Rewards' section not found in config.yml");
        }

        getLogger().info("Configuration successfully loaded.");
    }

    private void logConfigInfo(RewardModel rewardModel) {
        String message = TextUtils.formatText("\nRewards:\n"
                + "\t&cday: &f" + rewardModel.getDay() + "\n"
                + "\t&ccommands: &f" + rewardModel.getCommands().toString());
        Bukkit.getConsoleSender().sendMessage(message);
        // getLogger().info(message);
    }

    public List<RewardModel> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardModel> rewards) {
        this.rewards = rewards;
    }

}
