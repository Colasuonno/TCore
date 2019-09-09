package com.tcore.listener;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.managers.PlayersManager;
import com.tcore.objects.TCPlayer;
import com.tcore.utils.LocationUtil;
import com.tcore.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

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

        new BukkitRunnable() {
            @Override
            public void run() {
                TPlayer cloned = tCore.getPlayersManager().fromPlayer(e.getPlayer());
                String title = tCore.getSettingsModule().getString("scoreboard-title");

                tCore.getScoreboardModule().registerPlayer(e.getPlayer(), tCore);
                tCore.getScoreboardModule().updateTitle(e.getPlayer(), tCore, ChatColor.translateAlternateColorCodes('&',  tCore.getPlayerModule().getChatReplacerModule().replace(cloned, title, "")));
            }
        }.runTaskAsynchronously(tCore);

        PlayersManager.getPlayers()
                .stream()
                .filter(TPlayer::isVanish)
                .forEach(tPlayer ->
                        tCore.getPlayerModule().setVanish(tPlayer, true)
                );

        if (tCore.getSettingsModule().getBoolean("spawn-teleport")){
            String loc = tCore.getDataModule().getString("spawn-location");
            if (!StringUtils.isNone(loc)){
                player.getPlayer().teleport(LocationUtil.convertToLocation(loc));
            } else StringUtils.m(ChatColor.RED + "Spawn Teleport ACTIVE but location not set", Bukkit.getConsoleSender());
        }

    }

    @EventHandler
    public void onDamange(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            TPlayer player = tCore.getPlayersManager().fromPlayer((Player) e.getEntity());
            if (player.isGod()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        TPlayer player = tCore.getPlayersManager().fromPlayer(e.getPlayer());
        String format = tCore.getSettingsModule().getString("chat-format");
        e.setFormat(tCore.getPlayerModule().getChatReplacerModule().replace(player, format, e.getMessage()));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if (!e.isCancelled()){
            TPlayer player = tCore.getPlayersManager().fromPlayer(e.getPlayer());
            player.setLastTeleportLocation(e.getFrom());
        }
    }

}