package com.tcore.commands.execute;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand extends FineCommand {

    public ClearInventoryCommand(TCore plugin) {
        super(plugin, "clearinventory", "tcore.command.clearinventory", CommandManager.CommandType.ALL, "ci");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length == 0 && player != null) {
            player.clearInventory();
            player.sendMessage("clearinventory-solo");
        } else if (args.length == 1) {

            if (!sender.hasPermission("tcore.command.clearinventory.others")) {
                super.plugin.getPlayerModule().sendMessage(sender, "no-permission");
                return;
            }

            Player bukkit = Bukkit.getPlayer(args[0]);
            if (bukkit == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            TPlayer tPlayer = super.plugin.getPlayersManager().fromPlayer(bukkit);
            tPlayer.clearInventory();
            super.plugin.getPlayerModule().sendMessage(sender, "clearinventory-others", tPlayer.getName());
        } else super.plugin.getPlayerModule().sendText(sender, "<7>/clearinventory <c>(player)");
    }
}
