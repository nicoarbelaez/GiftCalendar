package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.RewardModel;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RewardsManager {
    private GiftCalendar plugin;
    private List<RewardModel> rewardModels;

    public RewardsManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.rewardModels = plugin.getMainConfig().getRewards();
    }

    public void executerCommandsPlayer(Player player, int day) {
        for (RewardModel rewardModel : rewardModels) {
            if (rewardModel.getDay() != day)
                continue;

            player.sendMessage("Acabas de reclamar el dia " + day + " de regalos!");
            for (String command : rewardModel.getCommands()) {
                String cmd = command.replace("%player%", player.getName()).replace("%day%", day + "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
            plugin.getLogger().info("Player " + player.getName() + " claimed day " + day + " rewards.");
            return;
        }
        plugin.getLogger().warning(
                "Player " + player.getName() + " tried to claim day " + day + " rewards but it doesn't exist.");
        return;
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public List<RewardModel> getRewardModels() {
        return rewardModels;
    }

    public void setRewardModels(List<RewardModel> rewardModels) {
        this.rewardModels = rewardModels;
    }

}
