package com.tcore.objects;

import com.tcore.TCore;
import com.tcore.api.TCoreAPI;
import com.tcore.api.objects.TPlayer;
import com.tcore.exception.TCoreException;
import com.tcore.gui.OrbInventory;
import com.tcore.gui.OrbInventoryItem;
import com.tcore.itemfall.HeadFall;
import com.tcore.utils.ReflectionUtil;
import com.tcore.utils.StringUtils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.UUID;

@Data
public class TCPlayer implements TPlayer, TCoreAPI {

    private TCore api;

    private String name;
    private UUID uuid;
    private Player player;
    private OfflinePlayer offlinePlayer;

    // TCPlayer status

    private boolean god;
    private boolean vanish;

    private int gamemode;

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

    @Override
    public void setGod(boolean value) {
        this.god = value;
    }

    @Override
    public boolean isGod() {
        return god;
    }

    @Override
    public void setVanish(boolean value) {
        this.api.getPlayerModule().setVanish(this, value);
        this.vanish = value;
    }

    @Override
    public boolean isVanish() {
        return vanish;
    }

    @Override
    public void setHeal(int value) {
        if (isOnline()) player.setHealth(value);
        else throw new TCoreException("Cannot set heal for offline player");
    }

    @Override
    public void setFood(int value) {
        if (isOnline()) player.setFoodLevel(value);
        else throw new TCoreException("Cannot set food for offline player");
    }

    @Override
    public int getPing() {
        if (isOnline()) {
            try {
                Class<?> craftPlayer = ReflectionUtil.getCraftBukkitClass("entity.CraftPlayer");
                Method getHandle = craftPlayer.getMethod("getHandle");
                Object entityPlayer = getHandle.invoke(player);
                return (int) ReflectionUtil.getFieldValue(entityPlayer, "ping");
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } else throw new TCoreException("Cannot get ping for offline player");
    }

    @Override
    public OrbInventory getInventory() {

        OrbInventory inventory = new OrbInventory(name + "'s Inventory", 54, name, true, true);

        int x = 1;
        int y = 2;

        inventory.setItem(new OrbInventoryItem(
                new HeadFall(ChatColor.YELLOW + name)
                        .owner(name),
                "head", 5, 1
        ));

        for (ItemStack itemStack : player.getInventory().getContents()) {
            inventory.setItem(new OrbInventoryItem(
                    itemStack,
                    x + "", x, y
            ));

            if (x == 9) {
                y++;
                x = 1;
            } else x++;

        }

        x = 1;
        y = 6;

        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            inventory.setItem(new OrbInventoryItem(
                    itemStack,
                    x + "", x, y
            ));

            x++;
        }


        return inventory;
    }

    public void clearInventory() {
        if (isOnline()) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
        }
        else throw new TCoreException("Cannot clear the inventory for offline player");
    }

    @Override
    public void setGamemode(GameMode gamemode) {
        if (isOnline()) {
            player.setGameMode(gamemode);
        }
        else throw new TCoreException("Cannot set survival gamemode for offline player");
    }

    public void restorePlayer(Player player) {
        this.player = player;
    }


}
