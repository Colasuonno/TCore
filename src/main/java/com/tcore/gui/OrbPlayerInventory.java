package com.tcore.gui;

import com.tcore.gui.util.InventoryUtil;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;

public class OrbPlayerInventory extends  OrbInventory{

    private InventoryView inventoryView;
    private PlayerInventory playerInventory;

    public OrbPlayerInventory(PlayerInventory playerInventory, InventoryView inventory, String metaData, boolean destroy, boolean cancel) {
        super(metaData, destroy,cancel);
        this.playerInventory = playerInventory;
        this.inventoryView = inventory;
        super.items = InventoryUtil.fromInventoryView(inventoryView,playerInventory);
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }
}
