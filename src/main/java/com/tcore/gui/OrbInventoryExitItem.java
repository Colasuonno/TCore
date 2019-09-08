package com.tcore.gui;

import org.bukkit.inventory.ItemStack;

/**
 * When clicked, the inventory will be closed
 */
public class OrbInventoryExitItem extends OrbInventoryItem {

    public OrbInventoryExitItem(ItemStack itemStack, String metaData, int x, int y) {
        super(itemStack, metaData, x, y);
    }

    public OrbInventoryExitItem(ItemStack itemStack) {
        super(itemStack);
    }
}
