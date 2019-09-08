package com.tcore.itemfall;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemFall extends ItemStack {


    /**
     * Deprecated constructor for building items (<1.14)
     *
     * @param material type
     */
    @Deprecated
    public ItemFall(Material material) {
        super(material, 1);

    }

    /**
     * Deprecated constructor for building items (<1.14)
     *
     * @param material    type
     * @param displayName name
     * @param amount      number
     * @param dur         data
     * @param lore        description
     */
    @Deprecated
    public ItemFall(Material material, String displayName, int amount, short dur, List<String> lore) {
        super(material, amount, dur);
        name(displayName);
        lore(lore);
    }

    /**
     * Deprecated constructor for building items (<1.14)
     *
     * @param material    type
     * @param displayName name
     * @param amount      number
     * @param dur         data
     * @see #ItemFall(Material, String, int, short, List)
     */
    @Deprecated
    public ItemFall(Material material, String displayName, int amount, short dur) {
        this(material, displayName, amount, dur, new ArrayList<String>());
    }

    /**
     * Converting ItemStack to ItemFall
     *
     * @param itemStack
     */
    public ItemFall(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Apply and translate the displayName given
     *
     * @param displayName to apply
     * @return named ItemFall
     */
    public ItemFall name(String displayName) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta(meta);
        return this;
    }

    /**
     * Apply the lore given
     *
     * @param lore to apply
     * @return ItemFall + lore
     */
    public ItemFall lore(List<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        itemMeta(meta);
        return this;
    }

    /**
     * Set the amount
     *
     * @param amount to apply
     * @return ItemFall * amount
     */
    public ItemFall amount(int amount) {
        setAmount(amount);
        return this;
    }

    /**
     * Set the ItemFlags
     *
     * @param flag varargs flags
     * @return the flagged ItemFall
     */
    public ItemFall flag(ItemFlag... flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        itemMeta(meta);
        return this;
    }

    /**
     * Deprecated durability method
     *
     * @param dur to apply
     * @return ItemFall + data
     */
    @Deprecated
    public ItemFall dur(short dur) {
        setDurability(dur);
        return this;
    }

    /**
     * Apply the ItemMeta to work with
     *
     * @param meta Data
     * @return the ItemFall + MetaData
     */
    public ItemFall itemMeta(ItemMeta meta) {
        setItemMeta(meta);
        return this;
    }

    /**
     * Enchant the item
     *
     * @param type  enchant
     * @param level to apply
     * @return enchanted itemfall
     */
    public ItemFall enchant(Enchantment type, int level) {
        addEnchantment(type, level);
        return this;
    }

    /**
     * Unsafely Enchant the item (e.g: prot 10, etc etc)
     *
     * @param type  enchant
     * @param level to apply
     * @return enchanted itemfall
     */
    public ItemFall unsafeEnchant(Enchantment type, int level) {
        addUnsafeEnchantment(type, level);
        return this;
    }

    /**
     * Cloning the ItemStack
     *
     * @return
     */
    public ItemStack asItemStack() {
        return super.clone();
    }

}