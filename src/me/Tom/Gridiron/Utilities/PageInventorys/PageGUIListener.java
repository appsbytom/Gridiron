package me.Tom.Gridiron.Utilities.PageInventorys;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PageGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        int slot = e.getSlot();

        if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof PageGUI) {
            e.setCancelled(true);
            PageGUI pageGUI = (PageGUI) inv.getHolder();

            if(slot >= 45 && slot <= 53) {
                ControlButton controlButton = pageGUI.getControlButton(slot);
                if (controlButton != null && controlButton.getListener() != null) {
                    controlButton.getListener().onClick(e);
                }
            }
            else {
                List<ItemButton> itemButtons = pageGUI.getItemButtons((Player) e.getWhoClicked());
                if(slot < itemButtons.size()) {
                    ItemButton itemButton = itemButtons.get(slot);
                    if (itemButton != null && itemButton.getListener() != null) {
                        itemButton.getListener().onClick(e);
                    }
                }
            }
        }
    }
}
