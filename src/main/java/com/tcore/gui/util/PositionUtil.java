package com.tcore.gui.util;

public class PositionUtil {

    /**
     * Calculate bukkit position having X and Y elements
     * Formula: ((y-1)*9)+x-1
     *
     * @return Bukkit position
     */
    public static int getBukkitPosition(int x, int y) {
        return ((y - 1) * 9) + x - 1;
    }

}
