package gc.nicoarbelaez.commands.subCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.utils.TextUtils;

public class SubCommandList extends SubCommand {

    public SubCommandList(GiftCalendar plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Set<CalendarModel> calendars = super.getPlugin().getCalendarConfig().getCalendars();

        if (calendars.isEmpty()) {
            sender.sendMessage(TextUtils.formatText("&e&l[!] &eThere are no calendars available."));
            return;
        }

        List<String> listCalendarName = calendars.stream() // Stream runs through each element
                .map(CalendarModel::getCalendarName) // Map each element to its calendar name
                .collect(Collectors.toList()); // Collect all elements into a list
        sender.sendMessage(
                TextUtils.formatText("&e&l[!] &eList of calendars: &7" + String.join(", ", listCalendarName)));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
