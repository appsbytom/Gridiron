package me.Tom.Gridiron.EventManager.Events;

import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.GameManager.*;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.ItemBuilder;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.Map;

public class GameMechanics implements Listener {

    private PluginCore core;
    private GameManager gameManager;

    private Map<Player, PlayerManager> playerManagerMap;
    private Map<Player, GameScoreboard> playerGameScoreboardMap;
    public GameMechanics(PluginCore pl) {
        core = pl;
        gameManager = core.getGameManager();

        playerManagerMap = core.getPlayerManagerMap();
        playerGameScoreboardMap = core.getPlayerGameScoreboardMap();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        e.setJoinMessage("");
        p.setGameMode(GameMode.ADVENTURE);
        //If game is not in progress add player data and check if the game can start
        if (GameState.isState(GameState.IN_LOBBY) || GameState.isState(GameState.LOADING)) {
            playerManagerMap.put(p, new PlayerManager(p, null, false));
            p.teleport(gameManager.getLobbySpawn());
            gameManager.lobbyWait();
        } else {
            //TODO Implement bungee pushback
            p.kickPlayer("The game has already started");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.setQuitMessage("");
        p.setGlowing(false);
        p.getInventory().clear();
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        playerManagerMap.remove(p);
        playerGameScoreboardMap.remove(p);
    }

    public void ballCheck() {
        playerManagerMap.values().forEach(playerManager -> {
            if(playerManager.hasBall()) {
                Player p = playerManager.getPlayer();
                if(p.getLocation().distanceSquared(gameManager.getCurrentGoal()) <= 3 * 3) {
                    playerManager.getTeam().setPoints(playerManager.getTeam().getPoints() + 1);
                    playerManager.setHasBall(false);
                    p.setGlowing(false);
                    p.getInventory().setItem(8, new ItemBuilder(Material.AIR).getItem());
                    MessageUtils.broadcastMessage(Messages.GOALSCORED.replace("%PlayerName%", p.getName()).replace("%TeamName%", playerManager.getTeam().getName()));
                    gameManager.setRandomBall();
                }
            }
        });
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(GameState.isState(GameState.IN_PROGRESS)) {
            if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                if(playerManagerMap.get(e.getDamager()).getTeam() == playerManagerMap.get(e.getEntity()).getTeam()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        //Check if game is in progress and that a player was killed
        if (GameState.isState(GameState.IN_PROGRESS)) {
            if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Player) {
                Player p = e.getEntity().getPlayer();

                e.setDeathMessage("");
                e.getDrops().clear();
                //If player has a ball remove it from inventory/data and spawn one on the ground
                if(playerManagerMap.get(p).hasBall()) {
                    playerManagerMap.get(p).setHasBall(false);
                    p.setGlowing(false);
                    gameManager.spawnBall(p.getLocation().getBlock().getLocation().add(0, 2.0, 0));
                    MessageUtils.broadcastMessage(Messages.BALLDR0PPED);
                }
            }
        }
    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent e) {
        //Check if game is in progress
        if (GameState.isState(GameState.IN_PROGRESS)) {
            Player p = e.getPlayer();

            //Give the appropriate inventory based on the players teams
            Teams teams = playerManagerMap.get(p).getTeam();
            p.getInventory().setHelmet(teams.getGear().get(0));
            p.getInventory().setChestplate(teams.getGear().get(1));
            p.getInventory().setLeggings(teams.getGear().get(2));
            p.getInventory().setBoots(teams.getGear().get(3));
            p.getInventory().setItem(0, teams.getGear().get(4));
            p.teleport(teams.getSpawn());
        }
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if(!e.getPlayer().hasPermission("gridiron.admin")) {
            e.setCancelled(true);
        }
        else {
            if(GameState.isState(GameState.IN_PROGRESS)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if(!e.getWhoClicked().hasPermission("gridiron.admin")) {
            e.setCancelled(true);
        }
        else {
            if(GameState.isState(GameState.IN_PROGRESS)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void foodLoss(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
