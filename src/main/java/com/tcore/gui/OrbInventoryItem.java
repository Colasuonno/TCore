package com.tcore.gui;


import com.tcore.gui.util.PositionUtil;
import org.bukkit.inventory.ItemStack;

public class OrbInventoryItem {

   private ItemStack itemStack;
   private int x;
   private int y;
   private int bukkitPosition;
   private String metaData;

   public OrbInventoryItem(ItemStack itemStack, String metaData, int x, int y) {
       if (metaData == null) metaData = "default-meta";
       this.itemStack = itemStack;
       this.x = x;
       this.y = y;
       this.metaData = metaData;
       this.bukkitPosition = PositionUtil.getBukkitPosition(x,y);
   }

    public OrbInventoryItem(ItemStack itemStack, String metaData, int bukkitPosition) {
        if (metaData == null) metaData = "default-meta";
        this.itemStack = itemStack;
        this.metaData = metaData;
        this.bukkitPosition = PositionUtil.getBukkitPosition(x,y);
    }

   public OrbInventoryItem(ItemStack itemStack) {
       this.metaData = "default-meta";
       this.itemStack = itemStack;
   }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMetaData() {
        return metaData;
    }

    public int getBukkitPosition() {
        return bukkitPosition;
    }
}
