package me.Tom.Gridiron.CommandManager.Commands;

import me.Tom.Gridiron.CommandManager.SubCommands;
import me.Tom.Gridiron.Utilities.MessageUtils;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.command.CommandSender;

public class Help extends SubCommands {

    private PluginCore core;
    public Help(PluginCore pl) {
        core = pl;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(MessageUtils.format("&7=-=-=-=-=-=-=-= &5Gridiron Help &7=-=-=-=-=-=-=-="));
        if(sender.hasPermission("gridiron.admin")) {
            sender.sendMessage(MessageUtils.format(" &7&m*&r &d/Gridiron Admin &7- Open the Admin GUI to edit settings"));
            sender.sendMessage(MessageUtils.format(" &7&m*&r &d/Gridiron Location Set [Lobby/Goal/Ball] &7- Set your location as the specified location"));
            sender.sendMessage(MessageUtils.format(" &7&m*&r &d/Gridiron Reload [Game/Messages] &7- Reload required configs"));
        }
        sender.sendMessage(MessageUtils.format(" &7&m*&r &d/Gridiron Help &7- Show all Gridiron commands"));
        sender.sendMessage(MessageUtils.format("&7=-=-=-=-=-=-=-= &5Gridiron Help &7=-=-=-=-=-=-=-="));
        sender.sendMessage("");
    }

    @Override
    public String name() {
        return core.getCommandManager().help;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
