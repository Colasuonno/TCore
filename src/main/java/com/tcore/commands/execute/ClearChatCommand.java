package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ClearChatCommand extends FineCommand {

    public ClearChatCommand(TCore plugin) {
        super(plugin, "clearchat", "tcore.command.clearchat", CommandManager.CommandType.ALL, "cc");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        for (int i = 0; i < 200; i++) {
            Bukkit.broadcastMessage(" ");
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "The chat has been cleared");
    }
}
