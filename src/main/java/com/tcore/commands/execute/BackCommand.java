package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BackCommand extends FineCommand {

    public BackCommand(TCore plugin) {
        super(plugin, "back", "tcore.command.back", CommandManager.CommandType.PLAYER);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (player.getLastTeleportLocation() != null){
            player.getPlayer().teleport(player.getLastTeleportLocation());
        } else player.sendMessage("back-solo");
    }
}
