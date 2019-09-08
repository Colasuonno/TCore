package com.tcore.modules;

import com.tcore.api.objects.TPlayer;
import com.tcore.exception.TCoreException;
import com.tcore.managers.TitansManager;
import org.bukkit.entity.Player;

public class PlayerModule extends TitansManager {


    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void setFly(TPlayer player, boolean value){
        if (player.isOnline()){
            Player online = player.getPlayer();
            online.setAllowFlight(value);
            online.setFlying(value);
        } else throw new TCoreException("Cannot set fly for offline player");
    }

}
