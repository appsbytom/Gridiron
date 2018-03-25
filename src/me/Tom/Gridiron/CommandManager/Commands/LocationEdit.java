package me.Tom.Gridiron.CommandManager.Commands;

import me.Tom.Gridiron.CommandManager.SubCommands;
import me.Tom.Gridiron.ConfigManager.ConfigManager;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.GameManager.GameManager;
import me.Tom.Gridiron.GameManager.GameState;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.FileUtils;
import me.Tom.Gridiron.Utilities.MessageUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationEdit extends SubCommands {

    private PluginCore core;
    private ConfigManager configManager;
    private GameManager gameManager;
    public LocationEdit(PluginCore pl) {
        core = pl;
        configManager = core.getConfigManager();
        gameManager = core.getGameManager();
    }

    private void saveLocations(Player p) {
        p.sendMessage("");
        p.spigot().sendMessage(new ComponentBuilder("Update game locations").color(ChatColor.LIGHT_PURPLE).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gridiron location save"))
                .create());
        p.sendMessage("");
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
        if(args.length == 1) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGFORMAT));
            return;
        }
        if(!GameState.isState(GameState.IN_LOBBY)) {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.GAMEINPROGRESS));
            return;
        }
        Player p = (Player) sender;
        Location pLocation = p.getLocation();

        if(args[1].equalsIgnoreCase("set")) {
            if (args[2].equalsIgnoreCase("lobby")) {
                gameManager.setLobbySpawn(pLocation);
                p.sendMessage(MessageUtils.formattedMessage(Messages.LOBBYSET));
                saveLocations(p);
            }
            else if (args[2].equalsIgnoreCase("goal")) {
                gameManager.getGoalLocations().add(pLocation);
                p.sendMessage(MessageUtils.formattedMessage(Messages.GOALSET));
                saveLocations(p);
            }
            else if (args[2].equalsIgnoreCase("ball")) {
                gameManager.getBallLocations().add(pLocation);
                p.sendMessage(MessageUtils.formattedMessage(Messages.BALLSET));
                saveLocations(p);
            }
            else {
                sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGFORMAT));
            }
        }
        else if(args[1].equalsIgnoreCase("save")) {
            configManager.getGridironCfg().set("GameManager.LobbySpawn", FileUtils.seraliseLocation(gameManager.getLobbySpawn()));

            List<String> goalocs = new ArrayList<>();
            gameManager.getGoalLocations().forEach(loc -> {
                goalocs.add(FileUtils.seraliseLocation(loc));
            });
            configManager.getGridironCfg().set("GameManager.GoalLocations", goalocs);

            List<String> balllocs = new ArrayList<>();
            gameManager.getBallLocations().forEach(loc -> {
                balllocs.add(FileUtils.seraliseLocation(loc));
            });
            configManager.getGridironCfg().set("GameManager.BallLocations", balllocs);

            configManager.getGridiron().saveCfg();
            sender.sendMessage(MessageUtils.formattedMessage(Messages.LOCATIONSSAVE));
        }
        else {
            sender.sendMessage(MessageUtils.formattedMessage(Messages.WRONGFORMAT));
        }
    }

    @Override
    public String name() {
        return core.getCommandManager().loc;
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