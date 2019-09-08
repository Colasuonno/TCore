package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand extends FineCommand {

    public GodCommand(TCore plugin) {
        super(plugin, "god", "tcore.command.god", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            boolean god = player.isGod();
            player.setGod(!god);
            player.sendMessage("god-solo", (!god ? "enabled" : "disabled"));
        }else if (args.length == 1){

            if (!sender.hasPermission("tcore.command.god.others")){
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            boolean god = tPlayer.isGod();
            tPlayer.setGod(!god);
            super.plugin.getPlayerModule().sendMessage(sender, "god-others",(!god ? "enabled" : "disabled"), tPlayer.getName());
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/god <c>(player)");
    }
}