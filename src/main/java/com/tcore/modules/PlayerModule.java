package com.tcore.modules;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.exception.TCoreException;
import com.tcore.managers.TitansManager;
import com.tcore.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerModule extends TitansManager {

    private TCore api;

    public PlayerModule(TCore api) {
        this.api = api;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void sendMessage(CommandSender sender, String path, Object... args){
        String message = api.getLangManager().getLangModule().getMessages().getOrDefault(path, "<none>");
        if (message.equalsIgnoreCase("<none>")) throw new TCoreException("Config path not found");
        else {
            String valid = (api.getLangManager().getLangModule().isUsingPrefix() ? api.getLangManager().getLangModule().getPrefix() : "") + " " +  message;
            sender.sendMessage(StringUtils.parseString(valid, args));
        }
    }

    public void setFly(TPlayer player, boolean value){
        if (player.isOnline()){
            Player online = player.getPlayer();
            online.setAllowFlight(value);
            online.setFlying(value);
        } else throw new TCoreException("Cannot set fly for offline player");
    }

}
