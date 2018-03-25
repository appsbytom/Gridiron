package me.Tom.Gridiron.ConfigManager.Configs;

import me.Tom.Gridiron.Utilities.MessageUtils;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {

    private PluginCore core;
    public Messages(PluginCore pl) {
        core = pl;
    }

    public static String PREFIX;
    public static String NOPERMS;
    public static String NOCMD;
    public static String WRONGFORMAT;
    public static String WRONGUSER;

    public static String PLAYERSNEEDED;
    public static String GOALLOCATIONCHANGE;
    public static String BALLLOCATIONCHANGE;
    public static String BALLPICKEDUP;
    public static String BALLDR0PPED;
    public static String GAMEINPROGRESS;
    public static String ADMINGUIOPEN;
    public static String LOBBYSET;
    public static String GOALSET;
    public static String BALLSET;
    public static String LOCATIONSSAVE;

    public static String GOALSCORED;
    public static String GAMESTARTED;
    public static String GAMELOADING;
    public static String GAMELOADINGSUB;
    public static String GAMECANCELLED;
    public static String GAMECANCELLEDSUB;

    public void setup() {
        PREFIX = messagescfg.getString("Prefix");
        NOPERMS = messagescfg.getString("NoPerms");
        NOCMD = messagescfg.getString("NoCMD");
        WRONGFORMAT = messagescfg.getString("WrongFormat");
        WRONGUSER = messagescfg.getString("WrongUser");

        PLAYERSNEEDED = messagescfg.getString("PlayersNeeded");
        GOALLOCATIONCHANGE = messagescfg.getString("GoalLocationChange");
        BALLLOCATIONCHANGE = messagescfg.getString("BallLocationChange");
        BALLPICKEDUP = messagescfg.getString("BallPickedUp");
        BALLDR0PPED = messagescfg.getString("BallDropped");
        ADMINGUIOPEN = "&7AdminGUI opening...";
        GAMEINPROGRESS = "&7The game settings cannot be edited while a game is in progress!";
        LOBBYSET = "&7The &dLobby &7spawn has been set to your location";
        GOALSET = "&7Your location has been added as a &dGoal &7location";
        BALLSET = "&7Your location has been added as a &dBall &7spawn point";
        LOCATIONSSAVE = "&7Game locations have been updated!";

        GOALSCORED = messagescfg.getString("GoalScored");
        GAMESTARTED = messagescfg.getString("Title.GameStarted");
        GAMELOADING = messagescfg.getString("Title.GameLoading");
        GAMELOADINGSUB = messagescfg.getString("Title.GameLoadingSub");
        GAMECANCELLED = messagescfg.getString("Title.GameCancelled");
        GAMECANCELLEDSUB = messagescfg.getString("Title.GameCancelledSub");
    }

    private File messages;
    public File getFile() {
        return messages;
    }

    private FileConfiguration messagescfg;
    public FileConfiguration getCfg() {
        return messagescfg;
    }

    public void createCfg() {
        messages = new File(core.getDataFolder(), "messages.yml");
        if (!messages.exists()) {
            messages.getParentFile().mkdirs();
            core.saveResource("messages.yml", false);
        }
        reloadCfg();
    }
    public void reloadCfg() {
        messagescfg = new YamlConfiguration();
        try {
            messagescfg.load(messages);
            saveCfg();
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            MessageUtils.sendError("&cmessages.yml has failed to load");
        }
    }
    public void saveCfg() {
        try {
            messagescfg.save(messages);
        }
        catch (IOException e) {
            MessageUtils.sendError("&cmessages.yml has failed to save");
        }
    }
}
