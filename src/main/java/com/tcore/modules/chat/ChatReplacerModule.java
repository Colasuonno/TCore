package com.tcore.modules.chat;

import com.tcore.api.objects.TPlayer;
import com.tcore.utils.StringUtils;
import org.bukkit.ChatColor;

public class ChatReplacerModule {

    public String replace(TPlayer player, String input, String message){
        return StringUtils.parseString(input
                .replace("<PLAYER_NAME>", player.getName())
                .replace("<DISPLAY_NAME>", player.getPlayer().getDisplayName())
                .replace("<MESSAGE>", ChatColor.translateAlternateColorCodes('&', message)));
    }

}
