package me.Tom.Gridiron.EventManager.Events;

import me.Tom.Gridiron.ConfigManager.ConfigManager;
import me.Tom.Gridiron.GameManager.GameManager;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class AdminGUIClick implements Listener {

    private PluginCore core;
    private ConfigManager configManager;
    private GameManager gameManager;
    public AdminGUIClick(PluginCore pl) {
        core = pl;
        configManager = core.getConfigManager();
        gameManager = core.getGameManager();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        InventoryAction action = e.getAction();
        int slot = e.getSlot();

        if (inv != null && inv.equals(core.getInventorys().getAdminGUI())) {
            e.setCancelled(true);
            if (slot == 10) {
                if (action == InventoryAction.PICKUP_ALL) {
                    gameManager.setPlayersNeeded(gameManager.getPlayersNeeded() + 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Players needed increased to &d" + gameManager.getPlayersNeeded()));
                }
                if (action == InventoryAction.PICKUP_HALF) {
                    gameManager.setPlayersNeeded(gameManager.getPlayersNeeded() - 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Players needed decreased to &d" + gameManager.getPlayersNeeded()));
                }
            }
            if (slot == 12) {
                if (action == InventoryAction.PICKUP_ALL) {
                    gameManager.setLobbyCountdown(gameManager.getLobbyCountdown() + 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Lobby countdown increased to &d" + gameManager.getLobbyCountdown()));
                }
                if (action == InventoryAction.PICKUP_HALF) {
                    gameManager.setLobbyCountdown(gameManager.getLobbyCountdown() - 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Lobby countdown decreased to &d" + gameManager.getLobbyCountdown()));
                }
            }
            if (slot == 14) {
                if (action == InventoryAction.PICKUP_ALL) {
                    gameManager.setGoalSwitchCountdown(gameManager.getGoalSwitchCountdown() + 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Goal switch countdown increased to &d" + gameManager.getGoalSwitchCountdown()));
                }
                if (action == InventoryAction.PICKUP_HALF) {
                    gameManager.setGoalSwitchCountdown(gameManager.getGoalSwitchCountdown() - 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Goal switch countdown decreased to &d" + gameManager.getGoalSwitchCountdown()));
                }
            }
            if (slot == 16) {
                if (action == InventoryAction.PICKUP_ALL) {
                    gameManager.setGameTimer(gameManager.getGameTimer() + 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Game countdown increased to &d" + gameManager.getGameTimer()));
                }
                if (action == InventoryAction.PICKUP_HALF) {
                    gameManager.setGameTimer(gameManager.getGameTimer() - 1);
                    p.sendMessage(MessageUtils.formattedMessage("&7Game countdown increased to &d" + gameManager.getGameTimer()));
                }
            }
            if (slot == 31) {
                configManager.getGridiron().getCfg().set("GameManager.PlayersNeeded", gameManager.getPlayersNeeded());
                configManager.getGridiron().getCfg().set("GameManager.LobbyCountdownTime", gameManager.getLobbyCountdown());
                configManager.getGridiron().getCfg().set("GameManager.GoalSwitchTime", gameManager.getGoalSwitchCountdown());
                configManager.getGridiron().getCfg().set("GameManager.GameTime", gameManager.getGameTimer());
                configManager.getGridiron().saveCfg();
                p.sendMessage(MessageUtils.formattedMessage("&7Game settings have been updated!"));
            }
        }
    }
}
