package com.tcore.listener;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.objects.TCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private TCore tCore;

    public PlayerListener(TCore plugin) {
        this.tCore = plugin;
        Bukkit.getPluginManager().registerEvents(this, tCore);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        TPlayer player = tCore.getPlayersManager().fromPlayer(e.getPlayer());
        if (player == null){
            tCore.getPlayersManager().registerPlayer(e.getPlayer());
        } else ((TCPlayer)player).restorePlayer(e.getPlayer());

    }

}
