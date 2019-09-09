package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.LocationUtil;
import com.tcore.utils.YamlConfig;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class SetSpawnCommand extends FineCommand {

    public SetSpawnCommand(TCore plugin) {
        super(plugin, "setspawn", "tcore.command.setspawn", CommandManager.CommandType.PLAYER);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        Location loc = player.getPlayer().getLocation();
        YamlConfig.fastModify(plugin, "settings", "spawn-location", LocationUtil.convertToString(loc));
        player.sendText("<a>Spawn was set to " + loc);
    }
}
