package com.tcore.modules;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.exception.TCoreException;
import com.tcore.managers.TitansManager;
import com.tcore.modules.chat.ChatReplacerModule;
import com.tcore.utils.StringUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerModule extends TitansManager {

    private TCore api;
    @Getter
    private ChatReplacerModule chatReplacerModule;

    public PlayerModule(TCore api) {
        this.api = api;
        this.chatReplacerModule = new ChatReplacerModule();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void sendBroadcastMessage(String path, Object... args) {
        String message = api.getLangManager().getLangModule().getMessages().getOrDefault(path, "<none>");
        if (message.equalsIgnoreCase("<none>")) throw new TCoreException("Config path not found");
        else {
            String valid = (api.getLangManager().getLangModule().isUsingPrefix() ? api.getLangManager().getLangModule().getPrefix() : "") + " " + message;
            Bukkit.broadcastMessage(StringUtils.parseString(valid, args));
        }
    }

    public void sendVirginBroadcastMessage(String path, Object... args) {
        String message = api.getLangManager().getLangModule().getMessages().getOrDefault(path, "<none>");
        if (message.equalsIgnoreCase("<none>")) throw new TCoreException("Config path not found");
        else {
            Bukkit.broadcastMessage(StringUtils.parseString(message, args));
        }
    }

    public void sendBroadcastText(String message, Object... args) {
        String valid = (api.getLangManager().getLangModule().isUsingPrefix() ? api.getLangManager().getLangModule().getPrefix() : "") + " " + message;
        Bukkit.broadcastMessage(StringUtils.parseString(valid, args));
    }

    public void sendVirginText(CommandSender sender, String message, Object... args) {
        sender.sendMessage(StringUtils.parseString(message, args));
    }

    public void sendText(CommandSender sender, String message, Object... args) {
        String valid = (api.getLangManager().getLangModule().isUsingPrefix() ? api.getLangManager().getLangModule().getPrefix() : "") + " " + message;
        sender.sendMessage(StringUtils.parseString(valid, args));
    }

    public void sendMessage(CommandSender sender, String path, Object... args) {
        String message = api.getLangManager().getLangModule().getMessages().getOrDefault(path, "<none>");
        if (message.equalsIgnoreCase("<none>")) throw new TCoreException("Config path not found");
        else {
            String valid = (api.getLangManager().getLangModule().isUsingPrefix() ? api.getLangManager().getLangModule().getPrefix() : "") + " " + message;
            sender.sendMessage(StringUtils.parseString(valid, args));
        }
    }

    public void setFly(TPlayer player, boolean value) {
        if (player.isOnline()) {
            Player online = player.getPlayer();
            online.setAllowFlight(value);
            online.setFlying(value);
        } else throw new TCoreException("Cannot set fly for offline player");
    }


    public void setVanish(TPlayer player, boolean value) {
        Player online = player.getPlayer();
        if (player.isOnline()) {
            if (value) {
                for (Player all : Bukkit.getOnlinePlayers()) all.hidePlayer(online);
                online.spigot().setCollidesWithEntities(false);
            } else {
                for (Player all : Bukkit.getOnlinePlayers()) all.showPlayer(online);
                online.spigot().setCollidesWithEntities(true);
            }
        } else throw new TCoreException("Cannot set vanish for offline player");
    }

}
