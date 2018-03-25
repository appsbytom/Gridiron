package me.Tom.Gridiron;

import me.Tom.Gridiron.CommandManager.CommandManager;
import me.Tom.Gridiron.ConfigManager.ConfigManager;
import me.Tom.Gridiron.EventManager.EventManager;
import me.Tom.Gridiron.GameManager.GameManager;
import me.Tom.Gridiron.GameManager.GameScoreboard;
import me.Tom.Gridiron.GameManager.PlayerManager;
import me.Tom.Gridiron.GameManager.Teams;
import me.Tom.Gridiron.Utilities.MessageUtils;
import me.Tom.Gridiron.Utilities.PageInventorys.PageGUI;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginCore extends JavaPlugin {

    private List<PageGUI> allPageGUIs = new ArrayList<>();
    public List<PageGUI> getAllPageGUIs() {
        return allPageGUIs;
    }
    public PageGUI getPageGUI(String name) {
        for (PageGUI pageGUI : allPageGUIs) {
            if(pageGUI.getName().equalsIgnoreCase(name)) {
                return pageGUI;
            }
        }
        return null;
    }

    private Map<Player, PlayerManager> playerManagerMap = new HashMap<>();
    public Map<Player, PlayerManager> getPlayerManagerMap() {
        return playerManagerMap;
    }

    private Map<Player, GameScoreboard> playerGameScoreboardMap = new HashMap<>();
    public Map<Player, GameScoreboard> getPlayerGameScoreboardMap() {
        return playerGameScoreboardMap;
    }

    private List<Teams> allTeams = new ArrayList<>();
    public List<Teams> getAllTeams() {
        return allTeams;
    }

    private ConfigManager configManager;
    public ConfigManager getConfigManager() {
        return configManager;
    }

    private CommandManager commandManager;
    public CommandManager getCommandManager() {
        return commandManager;
    }

    private EventManager eventManager;
    public EventManager getEventManager() {
        return eventManager;
    }

    private GameManager gameManager;
    public GameManager getGameManager() {
        return gameManager;
    }

    private Inventorys inventorys;
    public Inventorys getInventorys() {
        return inventorys;
    }

    public void onEnable() {
        MessageUtils.consoleMessage("&5=-=-=-=-=-=-=-= " + getDescription().getName() + " =-=-=-=-=-=-=-=");
        MessageUtils.consoleMessage("| &aVersion: " + getDescription().getVersion());
        MessageUtils.consoleMessage("| &aAuthor: " + getDescription().getAuthors());
        configManager = new ConfigManager(this);
        configManager.setup();
        checkDepends();
        inventorys = new Inventorys(this);
        inventorys.setup();
        gameManager = new GameManager(this);
        gameManager.setupGame();
        commandManager = new CommandManager(this);
        commandManager.setup();
        eventManager = new EventManager(this);
        eventManager.setup();

        verifyMethods();
    }

    private void checkDepends() {
        if(Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            MessageUtils.consoleMessage("| &aHolographicDisplays Features: Enabled");
        }
        else {
            MessageUtils.consoleMessage("| &cHolographicDisplays Features: Disabled");
        }
    }

    private void verifyMethods() {
        if(!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            verifyError("&7Gridiron cannot run without &cHolographicDisplays!");
            return;
        }
        MessageUtils.consoleMessage("&5=-=-=-=-=-=-= A WW2 Remake =-=-=-=-=-=-=");
    }

    public void onDisable() {
        gameManager.gameStop();
    }

    private void verifyError(String message) {
        MessageUtils.consoleMessage("");
        MessageUtils.consoleMessage(message);
        MessageUtils.consoleMessage("");
        MessageUtils.consoleMessage("&5=-=-=-=-=-=-= A WW2 Remake =-=-=-=-=-=-=");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void versionChecker() {
        try {
            String spigotversion = IOUtils.toString(new URL("https://api.spigotmc.org/legacy/update.php?resource=####"));
            String pluginversion = getDescription().getVersion();
            Double spigotver = Double.parseDouble(spigotversion);
            Double pluginver = Double.parseDouble(pluginversion);
            if(spigotver > pluginver) {
                MessageUtils.consoleMessage("");
                MessageUtils.consoleMessage(MessageUtils.format("&cYou are not using the most up to date version of " + getDescription().getName() + "!"));
                MessageUtils.consoleMessage(MessageUtils.format("&chttps://www.spigotmc.org/resources/####/"));
                MessageUtils.consoleMessage("");
            }
            else {
                MessageUtils.consoleMessage("");
                MessageUtils.consoleMessage(MessageUtils.format("&aYou are using the most up to date version of "  + getDescription().getName() + "!"));
                MessageUtils.consoleMessage("");
            }
        }
        catch (IOException e) {
            MessageUtils.consoleMessage("");
            MessageUtils.consoleMessage(MessageUtils.format("&cCould not make connection to SpigotMC.org to check for updates!"));
            MessageUtils.consoleMessage("");
        }
    }
}
