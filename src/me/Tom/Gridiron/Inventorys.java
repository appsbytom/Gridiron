package me.Tom.Gridiron;

import me.Tom.Gridiron.Utilities.InventoryBuilder;
import me.Tom.Gridiron.Utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventorys {

    private PluginCore core;
    public Inventorys(PluginCore pl) {
        core = pl;
    }

    public void setup() {
        setupAdminGUI();
    }

    private Inventory adminGUI;
    public Inventory getAdminGUI() {
        return adminGUI;
    }
    public void setupAdminGUI() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7* Left click to increase");
        lore.add("&7* Right click to decrease");
        adminGUI = new InventoryBuilder("&dGame Settings").size(36)
                .item(10, new ItemBuilder(Material.SKULL_ITEM).durability(3).name("&dPlayers Needed").skull("TheTomTom3901").lore(lore).getItem())
                .item(12, new ItemBuilder(Material.COMPASS).name("&dLobby Countdown").lore(lore).getItem())
                .item(14, new ItemBuilder(Material.BEDROCK).name("&dGoal Switch").lore(lore).getItem())
                .item(16, new ItemBuilder(Material.WATCH).name("&dGame Time").lore(lore).getItem())
                .item(31, new ItemBuilder(Material.EMERALD).name("&dSave Settings").glow(true).getItem())
                .filler(new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name(" ").getItem())
                .build();
    }
}
