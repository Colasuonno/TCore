package com.tcore.gui.listeners;

import com.tcore.gui.OrbInventory;
import com.tcore.gui.OrbInventoryExitItem;
import com.tcore.gui.OrbInventoryItem;
import com.tcore.gui.OrbPlayerInventory;
import com.tcore.gui.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class InventoryListener implements Listener {

    public InventoryListener(Plugin instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        Inventory inventory = e.getInventory();
        Player player = (Player) e.getPlayer();

        if (InventoryUtil.contains(inventory)) {
            OrbInventory orb = InventoryUtil.getOrbInventory(inventory);
            orb.getListener().onOpen(e, player, orb);
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory().getType().equals(InventoryType.CRAFTING) ? player.getOpenInventory().getBottomInventory() : e.getInventory();

        if (InventoryUtil.contains(inventory)) {
            OrbInventory orb = InventoryUtil.getOrbInventory(inventory);
            if (orb.isCancel()) e.setCancelled(true);
            if (e.getRawSlot() >= orb.getInventory().getSize()) {
                return;
            }
            int rawSlot = e.getRawSlot();
            if (rawSlot < 0) return;
            if (orb instanceof OrbPlayerInventory && ((rawSlot <= 8) || rawSlot > orb.getItems().length)) return;
            OrbInventoryItem item = orb.getItem(rawSlot);
            if (item instanceof OrbInventoryExitItem) {
                e.setCancelled(true);
                player.closeInventory();
                orb.getListener().onClose(player, orb);
            } else {
                orb.getListener().onClick(e, player, orb, item);
                switch (e.getClick()) {
                    case LEFT:
                        orb.getListener().onLeftClick(e, player, orb, item);
                        break;
                    case RIGHT:
                        orb.getListener().onRightClick(e, player, orb, item);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        Player player = (Player) e.getPlayer();

        if (InventoryUtil.contains(inventory)) {
            OrbInventory orb = InventoryUtil.getOrbInventory(inventory);
            orb.getListener().onClose(player, orb);
            if (orb.isDestroy()) OrbInventory.inventories.remove(orb);
        }
    }


}
