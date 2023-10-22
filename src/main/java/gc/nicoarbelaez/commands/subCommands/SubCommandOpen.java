package gc.nicoarbelaez.commands.subCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.models.calendar.CalendarModel;
import gc.nicoarbelaez.utils.CalendarUtils;
import gc.nicoarbelaez.utils.OtherUtils;
import gc.nicoarbelaez.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SubCommandOpen extends SubCommand {

    public SubCommandOpen(GiftCalendar plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // <> Required | [] Optional
        // For console: /calendar open <calendarName> <nick> [page]
        // For in-game: /calendar open <calendarName> [nick|page] [page]
        if (sender instanceof Player) {
            executeInGame((Player) sender, args);
        } else {
            executeFromConsole(sender, args);
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length > 5) {
            return new ArrayList<>();
        }

        ArrayList<String> suggestions = new ArrayList<>();
        Set<CalendarModel> calendars = getPlugin().getCalendarConfig().getCalendars();
        ArrayList<CalendarModel> calendarList = new ArrayList<>(calendars);
        int indexArg = -1;

        if (args.length == 2) { // Suggesting calendar names
            for (CalendarModel calendar : calendars) {
                suggestions.add(calendar.getCalendarName());
            }
            indexArg = 1;
        } else if (args.length >= 3) { // Suggesting nick or page for a specific calendar
            CalendarModel calendarModel = CalendarUtils.findCalendar(args[1], calendarList);
            if (calendarModel != null) {
                if (args.length == 3) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        suggestions.add(player.getName());
                    }
                }
                addPageSuggestions(calendarModel, suggestions);
                indexArg = args.length - 1;
            }
        }

        return indexArg == -1 ? new ArrayList<>() : TextUtils.autocompleteSuggestions(args[indexArg], suggestions);
    }

    private void addPageSuggestions(CalendarModel calendarModel, ArrayList<String> suggestions) {
        int numPages = calendarModel.getPages();
        for (int i = 1; i <= numPages; i++) {
            suggestions.add(Integer.toString(i));
        }

    }

    private void executeInGame(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(TextUtils.formatText("&cUsage: &7/calendar open <calendarName> [nick] [page]"));
            return;
        }

        String calendarName = args[1];
        String targetNick = args.length >= 3 ? args[2] : null;
        String pageStr = args.length >= 4 ? args[3] : null;
        int page;
        if (OtherUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(args[3]);
        } else {
            page = 1;
        }

        if (OtherUtils.isNumeric(targetNick)) {
            page = Integer.parseInt(targetNick);
            targetNick = null;
        }

        player.sendMessage(TextUtils.formatText(getExecutionMessage(player, calendarName, targetNick, page)));
        openCalendar(player, calendarName, targetNick, page);
    }

    private void executeFromConsole(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(TextUtils.formatText("&cUsage: &7/calendar open <calendarName> <nick> [page]"));
            return;
        }

        String calendarName = args[1];
        String targetNick = args[2];
        String pageStr = args.length >= 4 ? args[3] : null;
        int page;
        if (OtherUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(args[3]);
        } else {
            page = 1;
        }

        sender.sendMessage(TextUtils.formatText(getExecutionMessage(sender, calendarName, targetNick, page)));
        openCalendar(sender, calendarName, targetNick, page);
    }

    private void openCalendar(CommandSender sender, String calendarName, String targetNick, int page) {
        Set<CalendarModel> calendars = getPlugin().getCalendarConfig().getCalendars();
        CalendarModel calendarModel = CalendarUtils.findCalendar(calendarName, new ArrayList<>(calendars));

        if (calendarModel == null) {
            sender.sendMessage(TextUtils.formatText("&c&l[!] &cThe calendar &7" + calendarName + " &cdoesn't exist"));
            return;
        }

        Player targetPlayer = getTargetPlayer(targetNick, sender);

        if (targetPlayer == null) {
            sender.sendMessage(TextUtils.formatText("&c&l[!] &cThe player &7" + targetNick + " &cdoesn't exist"));
            return;
        }

        page = Math.max(1, page);
        getPlugin().getCalendarInventoryManager().openCalendarInventory(calendarModel, targetPlayer, page);
    }

    private Player getTargetPlayer(String targetNick, CommandSender sender) {
        if (targetNick == null) {
            return sender instanceof Player ? (Player) sender : null;
        }

        return Bukkit.getPlayer(targetNick);
    }

    private String getExecutionMessage(CommandSender sender, String calendarName, String targetNick, int page) { // TODO:
                                                                                                                 // Remove
        return TextUtils.formatText("&7&lSender: &e" + sender.getName() + " &7&lCalendarName: &e"
                + calendarName + " &7&lTargetNick: &e" + targetNick + " &7&lPage: &e" + page);
    }
}
