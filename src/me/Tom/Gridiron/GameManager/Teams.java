package me.Tom.Gridiron.GameManager;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Teams {

    public Teams(String name, int points, Location spawn, List<ItemStack> gear) {
        this.name = name;
        this.points = points;
        this.spawn = spawn;
        this.gear = gear;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private int points;
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    private Location spawn;
    public Location getSpawn() {
        return spawn;
    }
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    private List<ItemStack> gear;
    public List<ItemStack> getGear() {
        return gear;
    }
    public void setGear(List<ItemStack> gear) {
        this.gear = gear;
    }
}
