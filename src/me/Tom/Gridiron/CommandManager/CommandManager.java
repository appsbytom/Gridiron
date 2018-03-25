package me.Tom.Gridiron.CommandManager;

import me.Tom.Gridiron.CommandManager.Commands.AdminGUI;
import me.Tom.Gridiron.CommandManager.Commands.Help;
import me.Tom.Gridiron.CommandManager.Commands.LocationEdit;
import me.Tom.Gridiron.CommandManager.Commands.Reload;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.Utilities.MessageUtils;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private PluginCore core;
    private List<SubCommands> commands = new ArrayList<>();
    public CommandManager(PluginCore pl) {
        core = pl;
    }

    public void setup() {
        core.getCommand(main).setExecutor(this);
        commands.add(new Help(core));
        commands.add(new AdminGUI(core));
        commands.add(new LocationEdit(core));
        commands.add(new Reload(core));
    }

    private String main = "gridiron";
    public String help = "help";
    public String admin = "admin";
    public String loc = "location";
    public String reload = "reload";

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                sender.sendMessage(MessageUtils.formattedMessage(Messages.NOCMD));
                return true;
            }

            SubCommands target = get(args[0]);

            if (target == null) {
                sender.sendMessage(MessageUtils.formattedMessage(Messages.NOCMD));
                return true;
            }

            List<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);

            try {
                target.onCommand(sender,args);
            }
            catch (Exception e){
                MessageUtils.sendError(sender, "&4Something has gone wrong when using this command!");
                e.printStackTrace();
            }
        }

        return true;
    }

    private SubCommands get(String name) {
        Iterator<SubCommands> subcommands = commands.iterator();

        while (subcommands.hasNext()) {
            SubCommands sc = (SubCommands) subcommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }
}
