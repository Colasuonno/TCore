package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryCommand extends FineCommand {

    public InventoryCommand(TCore plugin) {
        super(plugin, "inventory", "tcore.command.inventory", CommandManager.CommandType.PLAYER, "inv", "invsee");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 1) {

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                player.sendMessage("player-not-found");
                return;
            }
            TPlayer target = plugin.getPlayersManager().fromPlayer(bukkit);
            player.getPlayer().openInventory(target.getInventory().getInventory());
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/inventory <c>(player)");
    }
}
