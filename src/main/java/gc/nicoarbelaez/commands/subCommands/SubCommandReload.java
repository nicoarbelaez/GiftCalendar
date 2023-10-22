package gc.nicoarbelaez.commands.subCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import gc.nicoarbelaez.GiftCalendar;
import gc.nicoarbelaez.utils.TextUtils;

public class SubCommandReload extends SubCommand {

    public SubCommandReload(GiftCalendar plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.getMainConfig().reloadConfig();
        plugin.getMessageConfig().reloadConfig();
        plugin.getPlayerConfig().reloadConfig();
        plugin.getCalendarConfig().reloadConfig();
        String messageReload = plugin.getMessageConfig().getMessages().get("commandReload");

        sender.sendMessage(TextUtils.formatText(messageReload));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
