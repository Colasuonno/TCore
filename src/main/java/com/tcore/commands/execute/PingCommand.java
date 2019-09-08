package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand extends FineCommand {

    public PingCommand(TCore plugin) {
        super(plugin, "ping", "tcore.command.heal", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {

        if (args.length == 0 && player != null) {
            player.sendMessage("ping-solo", player.getPing());
        } else if (args.length == 1) {

            if (!sender.hasPermission("tcore.command.ping.others")) {
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            super.plugin.getPlayerModule().sendMessage(sender, "ping-others", tPlayer.getName(), tPlayer.getPing());
        }
    }
}
