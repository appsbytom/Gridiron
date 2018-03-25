package me.Tom.Gridiron.GameManager;

import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCountdown extends BukkitRunnable {

    private PluginCore core;
    public LobbyCountdown(PluginCore pl) {
        core = pl;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        if(core.getGameManager().getLobbyCountdown() > 0) {
            core.getGameManager().setLobbyCountdown(core.getGameManager().getLobbyCountdown() - 1);
            Bukkit.getOnlinePlayers().forEach(p -> {
                MessageUtils.sendTitle(p, Messages.GAMELOADING, Messages.GAMELOADINGSUB.replace("%LobbyCountdown%", String.valueOf(core.getGameManager().getLobbyCountdown())));
            });
        }
        else {
            if(Bukkit.getOnlinePlayers().size() >= core.getGameManager().getPlayersNeeded()) {
                core.getGameManager().gameStart();
                Bukkit.getOnlinePlayers().forEach(p -> {
                    MessageUtils.sendTitle(p, Messages.GAMESTARTED, "");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.resetTitle();
                        }
                    }.runTaskLater(core, 20L);
                });
            }
            else {
                core.getGameManager().gameStop();
                Bukkit.getOnlinePlayers().forEach(p -> {
                    MessageUtils.sendTitle(p, Messages.GAMECANCELLED, Messages.GAMECANCELLEDSUB);
                });
            }
            cancel();
        }
    }
}
