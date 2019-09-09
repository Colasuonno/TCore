package com.tcore.gui.util;

import com.tcore.gui.OrbInventory;
import com.tcore.gui.OrbInventoryItem;
import com.tcore.gui.OrbPlayerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtil {

    private static final int INVENTORY_PLAYER_SIZE = 45;

    public static boolean contains(Inventory inventory) {
        return OrbInventory.inventories.stream().anyMatch(obj -> obj instanceof OrbPlayerInventory ? contains(inventory, (OrbPlayerInventory) obj) : obj.getInventory().equals(inventory));
    }

    private static boolean contains(Inventory inventory, OrbPlayerInventory playerInventory) {
        return playerInventory.getInventoryView().getBottomInventory().equals(inventory) || playerInventory.getInventoryView().getTopInventory().equals(inventory);
    }

    @SuppressWarnings("unchecked")
    public static OrbInventory getOrbInventory(Inventory inventory) {
        return OrbInventory.inventories.stream().filter(obj -> obj instanceof OrbPlayerInventory ? contains(inventory, (OrbPlayerInventory) obj) : obj.getInventory().equals(inventory)).findAny().orElse(null);
    }

    public static OrbInventoryItem[] fromItemStackArray(ItemStack[] input) {
        OrbInventoryItem[] items = new OrbInventoryItem[input.length];
        for (int i = 0; i < input.length; i++) {
            items[i] = new OrbInventoryItem(input[i], "default-meta", i);
        }
        return items;
    }

    public static OrbInventoryItem[] fromInventoryView(InventoryView inventoryView, PlayerInventory playerInventory) {
        OrbInventoryItem[] items = new OrbInventoryItem[INVENTORY_PLAYER_SIZE];
        ItemStack[] array = inventoryView.getBottomInventory().getContents();
        ItemStack[] cont = playerInventory.getContents();
        for (int i = 9; i <= 35; i++) {
            items[i] = new OrbInventoryItem(array[i], "default-meta", i);
        }
        for (int i = 0; i < 9; i++) {
            items[i + 36] = new OrbInventoryItem(cont[i], "default-meta", i + 36);
        }
        return items;
    }

}
