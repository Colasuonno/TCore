package com.tcore.api.objects;

import com.maxmind.geoip2.model.CityResponse;
import com.tcore.api.TCoreAPI;
import com.tcore.gui.OrbInventory;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface TPlayer {

    String getName();

    UUID getUUID();

    Player getPlayer();

    TCoreAPI getApi();

    OfflinePlayer getOfflinePlayer();

    int getPing();

    boolean isOnline();

    void sendMessage(String path, Object... args);

    void sendText(String message);

    void setFly(boolean value);

    boolean isFlying();

    boolean isGod();

    void setGod(boolean value);

    boolean isVanish();

    void setVanish(boolean value);

    void setHeal(int value);

    void setFood(int value);

    OrbInventory getInventory();

    void clearInventory();

    void setGamemode(GameMode gamemode);

    CityResponse getCityResponse();

    Location getLastTeleportLocation();

    void setLastTeleportLocation(Location location);

    UUID getLastTextedPlayer();

    void setLastTextedPlayer(UUID uuid);
}
