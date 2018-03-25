package me.Tom.Gridiron.Utilities.PageInventorys;

import org.bukkit.inventory.ItemStack;

public class ItemButton {

    private ItemStack button;
    private ItemButtonListener listener;

    public ItemButton(ItemStack button) {
        this.button = button;
    }

    public ItemButton setListener(ItemButtonListener listener) {
        this.listener = listener;
        return this;
    }

    public ItemButtonListener getListener() {
        return listener;
    }

    public ItemStack getButton() {
        return button;
    }
}
