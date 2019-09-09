package com.tcore.modules.scoreboard;

import com.tcore.scoreboard.FastBoard;
import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data public class ScoreboardModule {

    private Map<Plugin, List<FastBoard>> boards = new HashMap<>();

    public void registerPlayer(Player player, Plugin plugin) {
        List<FastBoard> old = boards.getOrDefault(plugin, new ArrayList<>());
        old.add(new FastBoard(player));
        boards.put(plugin, old);
    }

    public FastBoard getScoreboard(Player player, Plugin plugin) {
        if (hasScoreboard(player, plugin)) {
            return boards.get(plugin)
                    .stream()
                    .filter(fastBoard -> fastBoard.getPlayer().getName().equalsIgnoreCase(player.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public void remove(Player player, Plugin plugin) {
        if (hasScoreboard(player, plugin)) {
            FastBoard board = getScoreboard(player, plugin);
            board.delete();
            boards.get(plugin).remove(board);
        }
    }

    public void updateTitle(Player player, Plugin plugin, String title) {
        if (hasScoreboard(player, plugin)) {
            getScoreboard(player, plugin).updateTitle(title);
        }
    }

    public void updateLines(Player player, Plugin plugin, String... lines) {
        if (hasScoreboard(player, plugin)) {
            getScoreboard(player, plugin).updateLines(lines);
        }
    }

    public boolean hasScoreboard(Player player, Plugin plugin) {
        return boards.getOrDefault(plugin, new ArrayList<>()).stream()
                .anyMatch(fastBoard -> fastBoard.getPlayer().getName().equalsIgnoreCase(player.getName()));
    }
}
