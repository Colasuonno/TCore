package com.tcore.itemfall;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HeadFall extends ItemFall {

    /**
     * Build an Head
     *
     * @param displayName name
     * @param amount      number
     * @param lore        description
     */
    public HeadFall(String displayName, int amount, List<String> lore) {
        super(skullMaterial(), displayName, amount, (short)0, lore);
        if (super.getType().name().toLowerCase().contains("skull")) {
            dur((short) 3); // setting player data for <1.13
        }
    }

    /**
     * Build an Head
     *
     * @param displayName name
     * @param amount      number
     * @see #HeadFall(String, int, List)
     */
    public HeadFall(String displayName, int amount) {
        this(displayName, amount, new ArrayList<String>());
    }

    /**
     * Build an Head
     *
     * @param displayName name
     * @see #HeadFall(String, int)
     */
    public HeadFall(String displayName) {
        this(displayName, 1, new ArrayList<String>());
    }

    /**
     * Build an Head
     *
     * @see #HeadFall(String)
     */
    public HeadFall() {
        this(skullMaterial().name().toLowerCase(), 1, new ArrayList<String>());
    }

    private static Material skullMaterial() {
        String version = Bukkit.getBukkitVersion();
        if (!version.contains("1.14") && !version.contains("1.13"))
            return Material.valueOf("SKULL_ITEM");
        else
            return Material.valueOf("PLAYER_HEAD");
    }

    /**
     * Set the head owner
     *
     * @param owner name
     * @return headfall
     */
    @Deprecated
    public HeadFall owner(String owner) {
        SkullMeta skullMeta = skullMeta();
        skullMeta.setOwner(owner);
        itemMeta(skullMeta);
        return this;
    }

    public SkullMeta skullMeta() {
        return (SkullMeta) getItemMeta();
    }

}