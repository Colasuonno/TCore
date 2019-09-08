package com.tcore.managers;

import com.tcore.TCore;
import com.tcore.api.TManager;
import com.tcore.api.objects.TPlayer;
import com.tcore.objects.TCPlayer;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersManager extends TitansManager {

    @Getter private static List<TPlayer> players = new ArrayList<>();
    private TCore tCore;

    public PlayersManager(TCore tCore) {
        this.tCore = tCore;
    }

    @Override
    public void enable() {
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
            players.add(new TCPlayer(tCore, offlinePlayer));
        }
    }

    @Override
    public void disable() {

    }

    public TPlayer registerPlayer(Player player){
        TPlayer tcPlayer = new TCPlayer(tCore, player);
        players.add(tcPlayer);
        return tcPlayer;
    }

    /**
     * Gets TPlayer from Player
     * @param player the player
     * @return the Tplayer if found
     */
    public TPlayer fromPlayer(Player player){
        if (contains(player)) return players.stream().filter(tc -> tc.getUUID().equals(player.getUniqueId())).findAny().orElse(null);
        else return null;
    }

    /**
     * Checks if we already located that kind of player
     * @param player to check
     * @return if it is loaded
     */
    public boolean contains(Player player){
        return players
                .stream()
                .anyMatch(tc -> tc.getUUID().equals(player.getUniqueId()));
    }

}
