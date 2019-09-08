package com.tcore.gui.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class LocationUtil {



    public static String convertToString(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static Location convertToLocation(String location) {
        String[] split = location.split(":");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    private static void modify(Location location, double x, double y, double z, Material newMaterial) {
        location.clone().add(x, y, z).getBlock().setType(newMaterial);
    }

}
