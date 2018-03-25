package me.Tom.Gridiron.GameManager;

import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GameTimer extends BukkitRunnable {

    private PluginCore core;
    public GameTimer(PluginCore pl) {
        core = pl;
    }

    @Override
    public void run() {
        if(GameState.isState(GameState.IN_PROGRESS)) {
            if(core.getGameManager().getGameTimer() > 0) {
                core.getEventManager().getGameMechanics().ballCheck();
                core.getGameManager().setGameTimer(core.getGameManager().getGameTimer() - 1);
                Bukkit.getOnlinePlayers().forEach(p -> {
                    Teams team = core.getPlayerManagerMap().get(p).getTeam();
                    core.getPlayerGameScoreboardMap().get(p).setLines(core.getGameManager().getScoreboardLines(), p.getName(), team.getName(), team.getPoints(), core.getGameManager().getGoalSwitchCountdown(), core.getGameManager().getGameTimer());
                });
                int height = Bukkit.getWorld("world").getMaxHeight();
                for(int i = 0; i < height; i++) {
                    Bukkit.getWorld("world").spawnParticle(Particle.SPELL_WITCH, new Location(core.getGameManager().getCurrentGoal().getWorld(), core.getGameManager().getCurrentGoal().getX(), (double) i, core.getGameManager().getCurrentGoal().getZ()), 5);
                }
            }
            else {
                core.getGameManager().gameStop();
                cancel();
            }
            if(core.getGameManager().getGoalSwitchCountdown() > 0) {
                core.getGameManager().setGoalSwitchCountdown(core.getGameManager().getGoalSwitchCountdown() - 1);
            }
            else {
                core.getGameManager().setRandomGoal();
                MessageUtils.broadcastMessage(Messages.GOALLOCATIONCHANGE);

                List<Boolean> hasball = new ArrayList<>();
                core.getPlayerManagerMap().values().forEach(playerManager -> {
                    if (playerManager.hasBall()) {
                        hasball.add(playerManager.hasBall());
                    }
                });
                if (hasball.size() == 0) {
                    core.getGameManager().setRandomBall();
                    MessageUtils.broadcastMessage(Messages.BALLLOCATIONCHANGE);
                }
                core.getGameManager().setGoalSwitchCountdown(core.getConfigManager().getGridironCfg().getInt("GameManager.GoalSwitchTime"));
            }
        }
    }
}
