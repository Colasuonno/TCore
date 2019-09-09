package com.tcore.modules.chat;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.utils.StringUtils;
import gyurix.leveling.LevelingAPI;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class ChatReplacerModule {

    private final Map<int[], String> colorsValue = new HashMap<int[], String>(){
        {
            put(new int[]{0, 19}, ChatColor.GRAY.toString());
            put(new int[]{20, 39}, ChatColor.DARK_GREEN.toString());
            put(new int[]{40, 59}, ChatColor.DARK_AQUA.toString());
            put(new int[]{60, 79}, ChatColor.RED.toString());
            put(new int[]{80, 99}, ChatColor.GOLD.toString());
            put(new int[]{100, Integer.MAX_VALUE}, ChatColor.LIGHT_PURPLE.toString());

        }
    };

    private String getColor(int level){
        for (int[] values : colorsValue.keySet()){
            if (values[0] >= level && values[1] <= level) return colorsValue.get(values);
        }
        return ChatColor.GRAY.toString();
    }

    public String replace(TPlayer player, String input, String message){
        TCore api = player.getApi().getAPI();

        User user = api.getLuckPerms().getUser(player.getUUID());
        Contexts contexts = api.getLuckPerms().getContextManager().getStaticContexts();
        MetaData metaData = user.getCachedData().getMetaData(contexts);

        return StringUtils.parseString(input
                .replace("<PLAYER_NAME>", player.getName())
                .replace("<DISPLAY_NAME>", player.getPlayer().getDisplayName())
          //      .replace("<LEVEL>", String.valueOf(LevelingAPI.getLevel(player.getPlayer())))
          //      .replace("<LEVEL_COLOR>", getColor(LevelingAPI.getLevel(player.getPlayer())))
                .replace("<GROUP>", metaData.getPrefix() == null ? user.getPrimaryGroup() : ChatColor.translateAlternateColorCodes('&', metaData.getPrefix()))
                .replace("<MESSAGE>", ChatColor.translateAlternateColorCodes('&', message)));
    }

}
