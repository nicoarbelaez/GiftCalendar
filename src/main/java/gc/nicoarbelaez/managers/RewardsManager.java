package gc.nicoarbelaez.managers;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.models.calendar.RewardModel;
import gc.nicoarbelaez.utils.CalendarUtils;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RewardsManager {
    private GiftCalendar plugin;
    private Set<CalendarModel> calendars;

    public RewardsManager(GiftCalendar plugin) {
        this.plugin = plugin;
        this.calendars = plugin.getCalendarConfig().getCalendars();
    }

    public void executerCommandsPlayer(Player player, CalendarModel calendar, int day) {
        String calendarName = calendar.getCalendarName();
        List<RewardModel> rewardModels = CalendarUtils.findCalendar(calendarName, this.calendars).getRewardList();
        List<String> dayCommands = CalendarUtils.findCalendar(calendarName, this.calendars).getCalendarConfig()
                .getDefaultRewardCommand();
        for (RewardModel rewardModel : rewardModels) {
            if (rewardModel.getDay() == day) {
                dayCommands = rewardModel.getCommands();
                break;
            }
        }

        player.sendMessage("Acabas de reclamar el dia " + day + " de regalos!");
        for (String command : dayCommands) {
            String cmd = command.replace("%player%", player.getName()).replace("%day%", day + "");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
        System.out.print("[RewardsManager] Player: " + player.getName()); // TODO: Remove
        System.out.print("[RewardsManager] Calendar: " + calendarName); // TODO: Remove
        System.out.print("[RewardsManager] Day: " + day); // TODO: Remove
        System.out.print("[RewardsManager] Commands: " + dayCommands.toString()); // TODO: Remove
        return;
    }

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public Set<CalendarModel> getCalendars() {
        return calendars;
    }

    public void setCalendars(Set<CalendarModel> calendars) {
        this.calendars = calendars;
    }
}
