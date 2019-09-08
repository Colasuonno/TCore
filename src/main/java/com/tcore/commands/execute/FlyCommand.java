package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class FlyCommand extends FineCommand {

    public FlyCommand(TCore plugin) {
        super(plugin,"fly", "tcore.command.fly", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0){
            boolean flying = player.isFlying();
            player.setFly(!flying);
            player.sendMessage("fly-solo", (!flying ? "enabled" : "disabled") );
        }
    }
}
