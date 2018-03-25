package me.Tom.Gridiron.CommandManager.Commands;

import me.Tom.Gridiron.CommandManager.SubCommands;
import me.Tom.Gridiron.ConfigManager.ConfigManager;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.GameManager.GameState;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommands {

    private PluginCore core;
    private ConfigManager configManager;
    public Reload(PluginCore pl) {
        core = pl;
        configManager = core.getConfigManager();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("gridiron.admin")) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.NOPERMS));
            return;
        }
        if(args.length == 1) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGFORMAT));
            return;
        }
        if(GameState.isState(GameState.IN_PROGRESS)) {
            sender.sendMessage(MessageUtils.formattedMessage("&7Config reloading cannot take place while a game is in progress"));
            return;
        }
        if(args[1].equalsIgnoreCase("game")) {
            configManager.getGridiron().reloadCfg();
            if (configManager.getGridiron().getCfg().getKeys(false).size() == 0) {
                configManager.getGridiron().getFile().delete();
                configManager.getGridiron().createCfg();
                sender.sendMessage(MessageUtils.formattedMessage("&cThere was an error in the &dGridiron &cconfig so it has been reset"));
            } else if (configManager.getGridiron().getCfg().getKeys(false).size() > 0) {
                core.getGameManager().setupGame();
                sender.sendMessage(MessageUtils.formattedMessage("&7The &dGridiron &7config has no errors and successfully reloaded"));
            }
        }
        else if(args[1].equalsIgnoreCase("messages")) {
            configManager.getMessages().reloadCfg();
            if (configManager.getMessages().getCfg().getKeys(false).size() == 0) {
                configManager.getMessages().getFile().delete();
                configManager.getMessages().createCfg();
                sender.sendMessage(MessageUtils.formattedMessage("&cThere was an error in the &dMessages &cconfig so it has been reset"));
            } else if (configManager.getMessages().getCfg().getKeys(false).size() > 0) {
                configManager.getMessages().setup();
                sender.sendMessage(MessageUtils.formattedMessage("&7The &dMessages &7config has no errors and successfully reloaded"));
            }
        } else {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGFORMAT));
        }
    }

    @Override
    public String name() {
        return core.getCommandManager().reload;
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