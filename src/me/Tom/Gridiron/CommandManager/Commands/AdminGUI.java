package me.Tom.Gridiron.CommandManager.Commands;

import me.Tom.Gridiron.CommandManager.SubCommands;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.GameManager.GameState;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminGUI extends SubCommands {

    private PluginCore core;
    public AdminGUI(PluginCore pl) {
        core = pl;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("gridiron.admin")) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.NOPERMS));
            return;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGUSER));
            return;
        }
        if(!GameState.isState(GameState.IN_LOBBY)) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.GAMEINPROGRESS));
            return;
        }
        Player p = (Player) sender;
        p.openInventory(core.getInventorys().getAdminGUI());
        p.sendMessage(MessageUtils.formattedMessage(Messages.ADMINGUIOPEN));
    }

    @Override
    public String name() {
        return core.getCommandManager().admin;
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