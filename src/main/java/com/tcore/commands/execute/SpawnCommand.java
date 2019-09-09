package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.LocationUtil;
import com.tcore.utils.StringUtils;
import com.tcore.utils.YamlConfig;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class SpawnCommand extends FineCommand {

    public SpawnCommand(TCore plugin) {
        super(plugin, "spawn", "tcore.command.spawn", CommandManager.CommandType.PLAYER);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        String loc = plugin.getDataModule().getString("spawn-location");
        if (!StringUtils.isNone(loc)){
            player.getPlayer().teleport(LocationUtil.convertToLocation(loc));
        } else player.sendText("<c>Spawn location is not set");
    }
}
