package me.Tom.Gridiron.ConfigManager;

import me.Tom.Gridiron.ConfigManager.Configs.Gridiron;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private PluginCore core;
    public ConfigManager(PluginCore pl) {
        core = pl;
        gridiron = new Gridiron(core);
        messages = new Messages(core);
    }

    public void setup() {
        gridiron.createCfg();
        gridironcfg = gridiron.getCfg();
        messages.createCfg();
        messages.setup();
        messagecfg = messages.getCfg();
    }

    private Gridiron gridiron;
    public Gridiron getGridiron() {
        return gridiron;
    }

    private FileConfiguration gridironcfg;
    public FileConfiguration getGridironCfg() {
        return gridironcfg;
    }

    private Messages messages;
    public Messages getMessages() {
        return messages;
    }

    private FileConfiguration messagecfg;
    public FileConfiguration getMessageCfg() {
        return messagecfg;
    }
}
