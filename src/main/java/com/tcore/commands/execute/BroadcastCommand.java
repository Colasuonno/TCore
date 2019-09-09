package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends FineCommand {

    public BroadcastCommand(TCore plugin) {
        super(plugin, "broadcast", "tcore.command.broadcast", CommandManager.CommandType.ALL, "announce", "bc");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        String message = StringUtils.buildSentence(args, 0);
        plugin.getPlayerModule().sendVirginBroadcastMessage("broadcast", ChatColor.translateAlternateColorCodes('&', message));
    }
}
