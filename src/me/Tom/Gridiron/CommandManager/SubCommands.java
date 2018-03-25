package me.Tom.Gridiron.CommandManager;

import org.bukkit.command.CommandSender;

public abstract class SubCommands {

    public SubCommands() {
    }

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
}
