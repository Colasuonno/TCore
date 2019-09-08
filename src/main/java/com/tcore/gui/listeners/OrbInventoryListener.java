package com.tcore.gui.listeners;


import com.tcore.gui.OrbInventory;
import com.tcore.gui.OrbInventoryItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface OrbInventoryListener {

    void onClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item);

    void onRightClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item);

    void onLeftClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item);

    void onClose(Player player, OrbInventory inventory);

    void onOpen(InventoryOpenEvent event, Player player, OrbInventory inventory);

}
