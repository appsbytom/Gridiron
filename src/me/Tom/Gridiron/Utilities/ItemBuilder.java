package me.Tom.Gridiron.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private String colorise(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(Material mat) {
        item = new ItemStack(mat);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder type(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder data(int data) {
        item.setData(new MaterialData(item.getType(), (byte) data));
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(colorise(name));
        applyMeta();
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> itemlore = new ArrayList<>();
        lore.forEach(s -> itemlore.add(colorise(s)));
        itemMeta.setLore(itemlore);
        applyMeta();
        return this;
    }

    public ItemBuilder lores(String[] lore) {
        List<String> itemlore = new ArrayList<>();
        for (String s : lore) {
            itemlore.add(colorise(s));
        }
        itemMeta.setLore(itemlore);
        applyMeta();
        return this;
    }

    public ItemBuilder enchant(Enchantment enc, int level) {
        item.addUnsafeEnchantment(enc, level);
        return this;
    }

    public ItemBuilder enchants(Map<Enchantment, Integer> enchants) {
        item.addUnsafeEnchantments(enchants);
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        itemMeta.addItemFlags(flag);
        applyMeta();
        return this;
    }

    public ItemBuilder flags(ItemFlag[] flags) {
        itemMeta.addItemFlags(flags);
        applyMeta();
        return this;
    }

    public ItemBuilder color(Color color) {
        if(item.getType() == Material.LEATHER_HELMET ||
                item.getType() == Material.LEATHER_CHESTPLATE ||
                item.getType() == Material.LEATHER_LEGGINGS ||
                item.getType() == Material.LEATHER_BOOTS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
            meta.setColor(color);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder skull(String owner) {
        if(item.getType() == Material.SKULL_ITEM && item.getDurability() == 3) {
            SkullMeta meta = (SkullMeta) itemMeta;
            meta.setOwner(owner);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        flag(ItemFlag.HIDE_ENCHANTS);
        enchant(Enchantment.LUCK, 1);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        applyMeta();
        return this;
    }

    private void applyMeta() {
        item.setItemMeta(itemMeta);
    }

    public ItemStack getItem() {
        return item;
    }
}
