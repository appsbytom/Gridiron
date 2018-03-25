package me.Tom.Gridiron.Utilities.PageInventorys;

import org.bukkit.inventory.ItemStack;

public class ControlButton {

    private ItemStack button;
    private ControlButtonListener listener;

    public ControlButton(ItemStack button) {
        this.button = button;
    }

    public ControlButton setListener(ControlButtonListener listener) {
        this.listener = listener;
        return this;
    }

    public ControlButtonListener getListener() {
        return listener;
    }

    public ItemStack getButton() {
        return button;
    }
}
