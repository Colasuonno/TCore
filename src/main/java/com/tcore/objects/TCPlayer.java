package com.tcore.objects;

import com.tcore.TCore;
import com.tcore.api.TCoreAPI;
import com.tcore.api.objects.TPlayer;
import com.tcore.exception.TCoreException;
import com.tcore.utils.StringUtils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data public class TCPlayer implements TPlayer, TCoreAPI {

    private TCore api;

    private String name;
    private UUID uuid;
    private Player player;
    private OfflinePlayer offlinePlayer;

    public TCPlayer(TCore api, OfflinePlayer offlinePlayer) {
        this.api = api;
        this.offlinePlayer = offlinePlayer;
        this.name = offlinePlayer.getName();
        this.uuid = offlinePlayer.getUniqueId();
        if (isOnline()) this.player = Bukkit.getPlayer(this.uuid);
    }

    @Override
    public TCore getAPI() {
        return this.api;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    @Override
    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }

    @Override
    public TCoreAPI getApi() {
        return this;
    }

    @Override
    public void setFly(boolean value) {
        this.api.getPlayerModule().setFly(this, value);
    }

    @Override
    public boolean isFlying() {
        if (isOnline()) return player.getAllowFlight();
        else throw new TCoreException("Cannot check fly for offline player");
    }

    @Override
    public void sendMessage(String path, Object... args) {
        this.api.getPlayerModule().sendMessage(player, path, args);
    }

    public void restorePlayer(Player player){
        this.player = player;
    }

}
