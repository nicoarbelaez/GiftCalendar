package gc.nicoarbelaez.models.calendar;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import gc.nicoarbelaez.utils.InventoryUtils;

public class CalendarConfigModel {
    private final String calendarTitle;
    private final int size;
    private final int days;
    private final List<Integer> slots;
    private final boolean showDayAsAmount;
    private final boolean showRewardsInInventory;
    private final boolean syncWithServer;
    private final boolean notifyNewReward;
    private final boolean claimPastRewards;
    private final boolean completeMonthReward;
    private final List<String> completeCalendarRewardCommand;
    private final List<String> defaultRewardCommand;

    public CalendarConfigModel(ConfigurationSection configurationSection) {
        this.calendarTitle = configurationSection.getString("calendar-title");
        this.size = InventoryUtils.calculateAdjustedInventorySize(configurationSection.getInt("size"));
        this.days = configurationSection.getInt("days");
        this.slots = configurationSection.getIntegerList("slots");
        this.showDayAsAmount = configurationSection.getBoolean("show-day-as-amount");
        this.showRewardsInInventory = configurationSection.getBoolean("show-rewards-in-inventory");
        this.syncWithServer = configurationSection.getBoolean("sync-with-server");
        this.notifyNewReward = configurationSection.getBoolean("notify-new-reward");
        this.claimPastRewards = configurationSection.getBoolean("claim-past-rewards");
        this.completeMonthReward = configurationSection.getBoolean("complete-month-reward");
        this.completeCalendarRewardCommand = configurationSection.getStringList("complete-calendar-reward-command");
        this.defaultRewardCommand = configurationSection.getStringList("default-reward-command");
    }

    public String getCalendarTitle() {
        return calendarTitle;
    }

    public int getSize() {
        return size;
    }

    public int getDays() {
        return days;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public boolean isShowDayAsAmount() {
        return showDayAsAmount;
    }

    public boolean isShowRewardsInInventory() {
        return showRewardsInInventory;
    }

    public boolean isSyncWithServer() {
        return syncWithServer;
    }

    public boolean isNotifyNewReward() {
        return notifyNewReward;
    }

    public boolean isClaimPastRewards() {
        return claimPastRewards;
    }

    public boolean isCompleteMonthReward() {
        return completeMonthReward;
    }

    public List<String> getCompleteCalendarRewardCommand() {
        return completeCalendarRewardCommand;
    }

    public List<String> getDefaultRewardCommand() {
        return defaultRewardCommand;
    }
}
