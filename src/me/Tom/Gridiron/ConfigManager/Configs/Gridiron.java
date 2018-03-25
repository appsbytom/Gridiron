package me.Tom.Gridiron.ConfigManager.Configs;

import me.Tom.Gridiron.Utilities.MessageUtils;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Gridiron {

    private PluginCore core;
    public Gridiron(PluginCore pl) {
        core = pl;
    }

    private File gridiron;
    public File getFile() {
        return gridiron;
    }

    private FileConfiguration gridironcfg;
    public FileConfiguration getCfg() {
        return gridironcfg;
    }

    public void createCfg() {
        gridiron = new File(core.getDataFolder(), "gridiron.yml");
        if (!gridiron.exists()) {
            gridiron.getParentFile().mkdirs();
            core.saveResource("gridiron.yml", false);
        }
        reloadCfg();
    }
    public void reloadCfg() {
        gridironcfg = new YamlConfiguration();
        try {
            gridironcfg.load(gridiron);
            saveCfg();
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            MessageUtils.sendError("&cgridiron.yml has failed to load");
        }
    }
    public void saveCfg() {
        try {
            gridironcfg.save(gridiron);
        }
        catch (IOException e) {
            MessageUtils.sendError("&cgridiron.yml has failed to save");
        }
    }
}
