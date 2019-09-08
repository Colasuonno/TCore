package com.tcore.commands.execute.gamemodes;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalCommand extends FineCommand {

    public SurvivalCommand(TCore plugin) {
        super(plugin, "gms", "tcore.command.survival", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            player.setGamemode(GameMode.SURVIVAL);
            player.sendMessage("gamemode-solo", "Survival");
        } else if (args.length == 1) {

            if (!sender.hasPermission("tcore.command.survival.others")) {
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            tPlayer.setGamemode(GameMode.SURVIVAL);
            super.plugin.getPlayerModule().sendMessage(sender, "gamemode-others", tPlayer.getName(), "Survival");
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/gms <c>(player)");
    }
}
