package me.Tom.Gridiron.GameManager;

import me.Tom.Gridiron.PluginCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A 32 character no flicker scoreboard implementation, fast and lightweight.
 */
public class GameScoreboard implements CustomScoreboard {

    private PluginCore core;
    private final Objective objective;
    private final Scoreboard board;
    private final Map<Integer, String> entries = new HashMap<>();
    private final List<Integer> shown = new ArrayList<>();
    private int index = 0;
    private int times = 0;

    public GameScoreboard(PluginCore pl) {
        core = pl;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public void set(Object line, int score) {
        String stringLine = ChatColor.translateAlternateColorCodes('&', line.toString());
        Team team = board.getTeam(String.valueOf(score));
        if (team == null) {
            team = board.registerNewTeam(String.valueOf(score));
            String entry = getNextEntry();
            team.addEntry(entry);
            entries.put(score, entry);
        }
        if (!shown.contains(score)) {
            objective.getScore(entries.get(score)).setScore(score);
            shown.add(score);
        }

        String prefix = stringLine.substring(0, Math.min(stringLine.length(), 16));
        team.setPrefix(prefix);
        String lastColors = ChatColor.getLastColors(prefix);
        if (stringLine.length() > 16)
            team.setSuffix(lastColors + stringLine.substring(16, Math.min(stringLine.length(), 32 - lastColors.length())));

        if (stringLine.length() > 32)
            System.err.println("[CustomScoreboard] A " + stringLine.length() + " character line was truncated to 32 characters. Line: " + score + " Text: '" + stringLine + "'");
    }

    public void setLines(List<String> lines, String name, String team, int points, int goal, int timer) {
        for(int i = 0; i < lines.size(); i++) {
            set(lines.get(i)
                    .replace("%PlayersName%", name)
                    .replace("%PlayersTeam%", team)
                    .replace("%PlayersTeamsPoints%", String.valueOf(points))
                    .replace("%AllyPoints%", String.valueOf(core.getAllTeams().get(0).getPoints()))
                    .replace("%AxisPoints%", String.valueOf(core.getAllTeams().get(1).getPoints()))
                    .replace("%GameTimer%", String.valueOf(timer))
                    .replace("%GoalSwitch%", String.valueOf(goal)), (lines.size() - 1) - i);
        }
    }

    @Override
    public void remove(int score) {
        if (entries.containsKey(score))
            board.resetScores(entries.get(score));
        if (shown.contains(score))
            shown.remove(Integer.valueOf(score));
    }

    @Override
    public void setTitle(Object title) {
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title.toString()));
    }

    @Override
    public Scoreboard getScoreboard() {
        return board;
    }

    private String getNextEntry() {
        ChatColor color = ChatColor.values()[index++];
        if (index >= ChatColor.values().length) {
            index = 0;
            times++;
        }
        return color.toString() + ChatColor.values()[times] + ChatColor.RESET;
    }
}
