package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends FineCommand {

    public VanishCommand(TCore plugin) {
        super(plugin, "vanish", "tcore.command.vanish", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            boolean vanish = player.isVanish();
            player.setVanish(!vanish);
            player.sendMessage("vanish-solo", (!vanish ? "enabled" : "disabled"));
        } else if (args.length == 1) {

            if (!sender.hasPermission("tcore.command.vanish.others")) {
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            boolean vanish = tPlayer.isVanish();
            tPlayer.setVanish(!vanish);
            super.plugin.getPlayerModule().sendMessage(sender, "vanish-others", (!vanish ? "enabled" : "disabled"), tPlayer.getName());
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/vanish <c>(player)");
    }
}