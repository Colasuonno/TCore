package com.tcore.task;

import com.google.common.collect.Lists;
import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import com.tcore.modules.scoreboard.ScoreboardModule;
import com.tcore.scoreboard.FastBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;

public class AsyncScoreboardPlacer extends BukkitRunnable {

    @Getter
    private TCore tCore;
    @Getter
    private ScoreboardModule module;

    public AsyncScoreboardPlacer(TCore tCore) {
        this.tCore = tCore;
        this.module = tCore.getScoreboardModule();
    }


    @Override
    public void run() {

        if (tCore.getSettingsModule().getBoolean("scoreboard-active")) {
            if (module.getBoards().containsKey(tCore)) {
                for (FastBoard boards : new ArrayList<>(module.getBoards().get(tCore))) {
                    if (boards.getPlayer() == null || !boards.getPlayer().isOnline()) {
                        module.getBoards().get(tCore).remove(boards);
                    }
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (module.hasScoreboard(player, tCore)) {
                    System.out.println(player.getName());
                    TPlayer tPlayer = tCore.getPlayersManager().fromPlayer(player);

                    String title = tCore.getSettingsModule().getString("scoreboard-title");

                    module.updateTitle(player, tCore, ChatColor.translateAlternateColorCodes('&',  tCore.getPlayerModule().getChatReplacerModule().replace(tPlayer, title, "")));

                    List<String> lines = tCore.getSettingsModule().getStringList("scoreboard-scores");
                    List<String> replaced = tCore.getPlayerModule().getChatReplacerModule().replace(tPlayer, lines);

                    module.updateLines(player, tCore,
                            replaced.stream().toArray(String[]::new));
                } else {
                    module.registerPlayer(player, tCore);
                }

            }
        }
    }


}
