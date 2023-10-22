package gc.nicoarbelaez.commands;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.commands.subCommands.SubCommand;
import gc.nicoarbelaez.commands.subCommands.SubCommandList;
import gc.nicoarbelaez.commands.subCommands.SubCommandOpen;
import gc.nicoarbelaez.commands.subCommands.SubCommandReload;
import gc.nicoarbelaez.utils.TextUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements CommandExecutor, TabCompleter {

    GiftCalendar plugin;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public MainCommand(GiftCalendar plugin) {
        this.plugin = plugin;
        subCommands.put("reload", new SubCommandReload(plugin));
        subCommands.put("open", new SubCommandOpen(plugin));
        subCommands.put("list", new SubCommandList(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(TextUtils.formatText("&cUse /calendar <open/list/reload> <calendarName> <page>"));
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage(TextUtils.formatText("&cUse /calendar <open/list/reload> <calendarName> <page>"));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            tabComplete.addAll(subCommands.keySet());
            tabComplete = TextUtils.autocompleteSuggestions(args[0], tabComplete);
        } else {
            SubCommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) {
                tabComplete = subCommand.tabComplete(sender, args);
            }
        }

        String str = "\n&7================================" + "\n"
        + "&9CommandSender &3sender &f" + sender.getName() + "\n"
        + "&9Command &3command &f" + command.getName() + "\n"
        + "&9String &3label &f" + label + "\n"
        + "&9String[] &3args &f" + Arrays.toString(args) + "\n"
        + "&7================================" + "\n"; // TODO: Remove
        sender.sendMessage(TextUtils.formatText(str)); // TODO: Remove

        return tabComplete;
    }
}
