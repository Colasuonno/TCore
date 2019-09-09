package com.tcore.commands.execute.messages;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.commands.CommandManager;
import com.tcore.commands.FineCommand;
import com.tcore.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ReplyCommand extends FineCommand {

    public ReplyCommand(TCore plugin) {
        super(plugin, "reply", "tcore.command.msg", CommandManager.CommandType.PLAYER, "r");
    }

    @Override
    public void run(TPlayer player, CommandSender sender, String label, String[] args) {

        if (args.length >= 1) {

            UUID playerReply = player.getLastTextedPlayer();
            TPlayer reply = plugin.getPlayersManager().fromUUID(playerReply);

            if (playerReply == null || !reply.isOnline()) {
                super.plugin.getPlayerModule().sendMessage(sender, "player-not-found");
                return;
            }

            String message = StringUtils.buildSentence(args, 0);

            String virgin = plugin.getLangManager().getLangModule().getMessages().get("msg-syntax");
            plugin.getPlayerModule().sendVirginText(reply.getPlayer(), virgin, player.getName(), reply.getName(), ChatColor.translateAlternateColorCodes('&', message));
            plugin.getPlayerModule().sendVirginText(player.getPlayer(), virgin, player.getName(), reply.getName(), ChatColor.translateAlternateColorCodes('&', message));

        } else super.plugin.getPlayerModule().sendText(sender, "<7>/msg <c>(player) (message)");
    }
}
