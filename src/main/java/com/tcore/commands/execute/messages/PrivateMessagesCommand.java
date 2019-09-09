package com.tcore.commands.execute.messages;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateMessagesCommand extends FineCommand {

    public PrivateMessagesCommand(TCore plugin) {
        super(plugin, "msg", "tcore.command.msg", CommandManager.CommandType.PLAYER, "m", "message", "a", "ma", "tell", "say");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {
        if (args.length >= 2) {
            Player p2 = Bukkit.getPlayer(args[0]);
            if (player == null || p2 == null) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }
            String message = StringUtils.buildSentence(args, 1);

            String virgin = plugin.getLangManager().getLangModule().getMessages().get("msg-syntax");

            plugin.getPlayerModule().sendVirginText(p2.getPlayer(), virgin, player.getName(), p2.getName(), ChatColor.translateAlternateColorCodes('&', message));
            plugin.getPlayerModule().sendVirginText(player.getPlayer(), virgin, player.getName(), p2.getName(), ChatColor.translateAlternateColorCodes('&', message));

            player.setLastTextedPlayer(p2.getUniqueId());
            plugin.getPlayersManager().fromPlayer(p2).setLastTextedPlayer(player.getUUID());

        } else super.plugin.getPlayerModule().sendText(sender, "<7>/msg <c>(player) (message)");
    }
}
