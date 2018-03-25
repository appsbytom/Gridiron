package me.Tom.Gridiron.Utilities;

import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.PageInventorys.ItemButton;
import me.Tom.Gridiron.Utilities.PageInventorys.PageGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Example Usage(s):
 *   Inventory inventory = new InventoryBuilder(null, 56, "&cOnline Players").build();
 *   Inventory inventory = new InventoryBuilder(null, InventoryType.BEACON, "&6Powerups").setFiller(new ItemBuilder(Material.STAINED_GLASS_PANE).data(7).make()).build();
 *   Inventory inventory = new InventoryBuilder(null, 9, "&aRewards").addItem(4, new ItemBuilder(Material.DIAMOND).make()).build();
 *
 */

public class InventoryBuilder {

    private PluginCore pl = PluginCore.getPlugin(PluginCore.class);

    private InventoryHolder holder;
    private InventoryType type;
    private int size;
    private String title;
    private boolean pageable = false;

    private ItemStack filler;
    private Map<Integer, ItemStack> slotitems = new HashMap<>();
    private List<ItemStack> items = new ArrayList<>();
    private List<ItemButton> buttons = new ArrayList<>();

    private String colorise(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Initialise InventoryBuilder with a title
     */
    public InventoryBuilder(String title) {
        this.title = title;
    }

    /**
     * Set the size of the Inventory
     */
    public InventoryBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * Set the InventoryHolder of the Inventory
     */
    public InventoryBuilder holder(InventoryHolder holder) {
        this.holder = holder;
        return this;
    }

    /**
     * Set the InventoryType of the Inventory
     */
    public InventoryBuilder type(InventoryType type) {
        this.type = type;
        return this;
    }

    /**
     * Set the Inventory to be pageable
     */
    public InventoryBuilder pageable(boolean pageable) {
        this.pageable = pageable;
        return this;
    }

    /**
     * Set the ItemStack to be used for fillers
     */
    public InventoryBuilder filler(ItemStack filler) {
        this.filler = filler;
        return this;
    }

    /**
     * Add ItemStacks to the Inventory in specfic slots
     */
    public InventoryBuilder item(int slot, ItemStack item) {
        slotitems.put(slot, item);
        return this;
    }

    /**
     * Add a list of ItemButtons to the PageGUI
     */
    public InventoryBuilder buttons(List<ItemButton> buttons) {
        this.buttons = buttons;
        return this;
    }

    /**
     * Add a list of ItemStacks to the Inventory
     */
    public InventoryBuilder items(List<ItemStack> items) {
        this.items = items;
        return this;
    }

    /**
     * Builds the entire Inventory and then returns it
     */
    public Inventory build() {
        if(pageable) {
            PageGUI pageGUI = new PageGUI(colorise(title));
            pageGUI.addItemButtons(buttons);
            pageGUI.build();
            pl.getAllPageGUIs().add(pageGUI);
            return null;
        }
        else {
            Inventory inv;
            if (type != null) {
                inv = Bukkit.createInventory(holder, type, colorise(title));
            } else {
                inv = Bukkit.createInventory(holder, size, colorise(title));
            }
            for (int i : slotitems.keySet()) {
                inv.setItem(i, slotitems.get(i));
            }
            if (filler != null) {
                for (int i = 0; i < inv.getSize(); i++) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, filler);
                    }
                }
            }
            return inv;
        }
    }
}
