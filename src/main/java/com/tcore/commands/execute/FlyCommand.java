package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class FlyCommand extends FineCommand {

    public FlyCommand(TCore plugin) {
        super(plugin,"fly", "tcore.command.fly", CommandManager.CommandType.ALL, "flight");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null){
            boolean flying = player.isFlying();
            player.setFly(!flying);
            player.sendMessage("fly-solo", (!flying ? "enabled" : "disabled") );
        } else if (args.length == 1){

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            boolean flying = tPlayer.isFlying();
            tPlayer.setFly(!flying);
            tPlayer.sendMessage("fly-solo", (!flying ? "enabled" : "disabled") );
            super.plugin.getPlayerModule().sendMessage(sender, "fly-others",(!flying ? "enabled" : "disabled"), tPlayer.getName());
        }
    }
}
