package com.tcore.api.objects;

import com.tcore.api.TCoreAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface TPlayer {

    String getName();

    UUID getUUID();

    Player getPlayer();

    TCoreAPI getApi();

    OfflinePlayer getOfflinePlayer();

    boolean isOnline();

    void sendMessage(String path, Object... args);

    void setFly(boolean value);

    boolean isFlying();

}