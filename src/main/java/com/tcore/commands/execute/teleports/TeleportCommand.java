package com.tcore.commands.execute.teleports;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends FineCommand {

    public TeleportCommand(TCore plugin) {
        super(plugin, "teleport", "tcore.command.teleport", CommandManager.CommandType.ALL, "tp");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 1 && player != null) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            player.getPlayer().teleport(p);
            player.sendMessage("teleport-solo", p.getName());
        } else if (args.length == 2) {
            Player p = Bukkit.getPlayer(args[0]);
            Player p2 = Bukkit.getPlayer(args[1]);
            if (p == null || p2 == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            p.teleport(p2);
            player.sendMessage("teleport-others", p.getName(), p2.getName());
        } else if (args.length == 3 && player != null) {
            String x = args[0];
            String y = args[1];
            String z = args[2];
            if (!StringUtils.isInt(x) || !StringUtils.isInt(y) || !StringUtils.isInt(z)) {
                plugin.getPlayerModule().sendText(sender, ChatColor.RED + "Coordinates not valid");
                return;
            }

            player.getPlayer().teleport(new Location(player.getPlayer().getWorld(), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
            player.sendMessage("teleport-coords", x, y, z);
        }else super.plugin.getPlayerModule().sendText(sender, "<7>/teleport <c>(player)");
    }
}

