package me.Tom.Gridiron.Utilities.PageInventorys;

import me.Tom.Gridiron.Utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

public class PageGUI implements InventoryHolder {

    private String name;
    public String getName() {
        return name;
    }

    private String title;
    private List<ItemButton> itemButtons = new ArrayList<>();
    private Map<Integer, ControlButton> controlButtons = new HashMap<>();
    private List<Inventory> pages;
    private List<List<ItemButton>> pageButtons;
    private Map<UUID, Integer> users = new HashMap<>();

    private String colorise(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public PageGUI(String title) {
        this.title = title;
        name = ChatColor.stripColor(title);
        pages = new ArrayList<>();
        pageButtons = new ArrayList<>();
    }

    public void addItemButtons(List<ItemButton> itemButtons) {
        this.itemButtons = itemButtons;
    }

    public List<ItemButton> getItemButtons(Player p) {
        return pageButtons.get(users.get(p.getUniqueId()));
    }

    public ControlButton getControlButton(int slot) {
        return controlButtons.get(slot);
    }

    private Inventory blankPage() {
        Inventory inv = Bukkit.createInventory(this, 54, colorise(title));

        ControlButton prevButton = new ControlButton(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("&bPrevious Page").skull("MHF_ArrowUp").getItem());
        prevButton.setListener(e -> decreasePage((Player) e.getWhoClicked()));
        controlButtons.put(45, prevButton);

        ControlButton nextButton = new ControlButton(new ItemBuilder(Material.SKULL_ITEM).durability(3).name("&bNext Page").skull("MHF_ArrowDown").getItem());
        nextButton.setListener(e -> increasePage((Player) e.getWhoClicked()));
        controlButtons.put(53, nextButton);

        for(int button : controlButtons.keySet()) {
            inv.setItem(button, controlButtons.get(button).getButton());
        }

        return inv;
    }

    private void increasePage(Player p) {
        UUID uuid = p.getUniqueId();

        if(users.get(uuid) != pages.size() - 1) {
            users.put(uuid, users.get(uuid) + 1);
            p.openInventory(pages.get(users.get(uuid)));
        }
    }

    private void decreasePage(Player p) {
        UUID uuid = p.getUniqueId();

        if(users.get(uuid) == 0) {
            p.closeInventory();
        }
        else {
            users.put(uuid, users.get(uuid) - 1);
            p.openInventory(pages.get(users.get(uuid)));
        }
    }

    public void firstPage(Player p) {
        users.put(p.getUniqueId(), 0);
        p.openInventory(pages.get(0));
    }

    public PageGUI build() {
        Inventory page = blankPage();
        List<ItemButton> buttons = new ArrayList<>();

        for(int i = 0; i < itemButtons.size(); i++) {
            if(page.firstEmpty() == 46) {
                pages.add(page);
                pageButtons.add(buttons);
                page = blankPage();
                buttons = new ArrayList<>();
                buttons.add(itemButtons.get(i));
                page.addItem(itemButtons.get(i).getButton());
            }
            else {
                buttons.add(itemButtons.get(i));
                page.addItem(itemButtons.get(i).getButton());
            }
        }
        pages.add(page);
        pageButtons.add(buttons);
        return this;
    }

    @Override
    public Inventory getInventory() {
        return pages.get(0);
    }
}
