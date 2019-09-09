package com.tcore.utils;

import com.google.common.collect.Lists;;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.List;

public class ScoreboardUtil {

    /**
     * @param index
     * @return to a custom code of ChatColor
     */
    public static String key(int index) {
        return ChatColor.values()[index].toString() + ChatColor.RESET;
    }

    public static final class Component {

        private String a;
        private String b;

        public Component(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

}
