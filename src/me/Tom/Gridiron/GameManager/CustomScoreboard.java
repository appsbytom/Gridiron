package me.Tom.Gridiron.GameManager;

import org.bukkit.scoreboard.Scoreboard;

public interface CustomScoreboard {

    /**
     * @param line  - The text to display on the new line, 64 characters max.
     * @param score - The score value to give the new line.
     */
    void set(Object line, int score);

    /**
     * Removes the line with the given score.
     *
     * @param score - The score value associated with the target line.
     */
    void remove(int score);

    /**
     * Sets both {@link org.bukkit.scoreboard.Objective}s display names.
     *
     * @param title - The desired title for the {@link Scoreboard}.
     */
    void setTitle(Object title);

    /**
     * @param score - The score value to give the new blank line.
     */
    default void blank(int score) {
        set("", score);
    }

    /**
     * Returns the underlying {@link Scoreboard}.
     *
     * @return - The underlying {@link Scoreboard}.
     */
    Scoreboard getScoreboard();
}
