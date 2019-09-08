package com.tcore.commands.execute.gamemodes;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand extends FineCommand {

    public GameModeCommand(TCore plugin) {
        super(plugin, "gm", "tcore.command.gamemode", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {

        if (args.length == 1) {
            GameMode found = getGamemode(args[0]);
            player.setGamemode(found);
            super.plugin.getPlayerModule().sendMessage(sender, "gamemode-solo", StringUtils.firstUpper(found.toString().toLowerCase()));
        } else if (args.length == 2) {

            if (!sender.hasPermission("tcore.command.gamemode.others")) {
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[1]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            GameMode found = getGamemode(args[0]);
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            tPlayer.setGamemode(found);
            super.plugin.getPlayerModule().sendMessage(sender, "gamemode-others", tPlayer.getName(), StringUtils.firstUpper(found.toString().toLowerCase()));
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/gm <c>(player)");
    }

    private GameMode getGamemode(String input) {
        switch (input.toLowerCase().substring(0, 1)) {
            case "1":
            case "c":
                return GameMode.CREATIVE;
            case "2":
            case "a":
                return GameMode.ADVENTURE;
            case "3":
                return GameMode.SPECTATOR;
            case "0":
            case "s":
            default:
                return GameMode.SURVIVAL;
        }
    }

}