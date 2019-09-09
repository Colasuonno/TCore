package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand extends FineCommand {

    public SpeedCommand(TCore plugin) {
        super(plugin, "speed", "tcore.command.speed", CommandManager.CommandType.PLAYER);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            if (!StringUtils.isInt(args[0])) {
                super.plugin.getPlayerModule().sendText(sender, ChatColor.RED + "Insert a valid integer");
            } else {

                Player bukkit = player.getPlayer();

                int value = Integer.parseInt(args[0]);
                if (value < 1) value = 1;
                else if (value > 10) value = 10;

                if (bukkit.isOnGround()) bukkit.setWalkSpeed(value == 1 ? 0.2f : (float) value / 10.0f);
                else bukkit.setFlySpeed(value == 1 ? 0.2f : (float) value / 10.0f);
                player.sendMessage("speed-solo", (bukkit.isOnGround() ? "Walking" : "Flying"), value);

            }

        } else super.plugin.getPlayerModule().sendText(sender, ChatColor.GRAY + "/speed <num>");
    }
}
