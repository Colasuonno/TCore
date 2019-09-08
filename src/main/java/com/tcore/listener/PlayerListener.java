package com.tcore.listener;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.managers.PlayersManager;
import com.tcore.objects.TCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private TCore tCore;

    public PlayerListener(TCore plugin) {
        this.tCore = plugin;
        Bukkit.getPluginManager().registerEvents(this, tCore);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        TPlayer player = tCore.getPlayersManager().fromPlayer(e.getPlayer());
        if (player == null) {
            player = tCore.getPlayersManager().registerPlayer(e.getPlayer());
        } else ((TCPlayer) player).restorePlayer(e.getPlayer());

        PlayersManager.getPlayers()
                .stream()
                .filter(TPlayer::isVanish)
                .forEach(tPlayer ->
                        tCore.getPlayerModule().setVanish(tPlayer, true)
                );

    }

    @EventHandler
    public void onDamange(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            TPlayer player = tCore.getPlayersManager().fromPlayer((Player) e.getEntity());
            if (player.isGod()) e.setCancelled(true);
        }
    }
}