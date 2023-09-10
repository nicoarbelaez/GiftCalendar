package gc.nicoarbelaez.commands;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.utils.TextUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    GiftCalendar plugin;

    public MainCommand(GiftCalendar plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.getMainConfig().reloadConfig();
            plugin.getItemConfig().reloadConfig();
            plugin.getPlayerConfig().reloadConfig();
            sender.sendMessage(TextUtils.formatText("reload..."));
            return true;
        }
        plugin.getInventoryManager().createInventory(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (args.length == 1) {
            tabComplete.add("reload");
        }
        return tabComplete;
    }
}
