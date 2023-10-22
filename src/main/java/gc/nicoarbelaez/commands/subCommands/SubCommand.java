package gc.nicoarbelaez.commands.subCommands;

import gc.nicoarbelaez.GiftCalendar;

import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    GiftCalendar plugin;

    public SubCommand(GiftCalendar plugin) {
        this.plugin = plugin;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(CommandSender sender, String[] args);

    public GiftCalendar getPlugin() {
        return plugin;
    }

    public void setPlugin(GiftCalendar plugin) {
        this.plugin = plugin;
    }
}
