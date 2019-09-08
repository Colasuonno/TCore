package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.command.CommandSender;

public class NightCommand extends FineCommand {

    public NightCommand(TCore plugin) {
        super(plugin, "night", "tcore.command.night", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            player.getPlayer().getWorld().setTime(14000);
            player.sendMessage("night-solo");
        }
    }
}