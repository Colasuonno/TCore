package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.command.CommandSender;

public class DayCommand extends FineCommand {

    public DayCommand(TCore plugin) {
        super(plugin, "day", "tcore.command.day", CommandManager.CommandType.ALL);
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            player.getPlayer().getWorld().setTime(6000);
            player.sendMessage("day-solo");
        }
    }
}
