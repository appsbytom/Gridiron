package me.Tom.Gridiron.GameManager;

import org.bukkit.entity.Player;

public class PlayerManager {

    public PlayerManager(Player player, Teams teams, boolean hasball) {
        this.player = player;
        this.teams = teams;
        this.hasball = hasball;
    }

    private Player player;
    public Player getPlayer() {
        return player;
    }

    private Teams teams;
    public Teams getTeam() {
        return teams;
    }
    public void setTeam(Teams teams) {
        this.teams = teams;
    }

    private boolean hasball;
    public boolean hasBall() {
        return hasball;
    }
    public void setHasBall(boolean hasball) {
        this.hasball = hasball;
    }
}
