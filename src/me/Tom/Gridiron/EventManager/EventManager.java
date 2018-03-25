package me.Tom.Gridiron.EventManager;

import me.Tom.Gridiron.EventManager.Events.AdminGUIClick;
import me.Tom.Gridiron.EventManager.Events.GameMechanics;
import me.Tom.Gridiron.Utilities.PageInventorys.PageGUIListener;
import me.Tom.Gridiron.PluginCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    private PluginCore core;
    private PluginManager pm;
    public EventManager(PluginCore pl) {
        core = pl;
        pm = Bukkit.getPluginManager();
    }

    private GameMechanics gameMechanics;
    public GameMechanics getGameMechanics() {
        return gameMechanics;
    }

    public void setup() {
        //PageGUI event handler
        pm.registerEvents(new PageGUIListener(), core);

        gameMechanics = new GameMechanics(core);
        pm.registerEvents(gameMechanics, core);
        pm.registerEvents(new AdminGUIClick(core), core);
    }
}
