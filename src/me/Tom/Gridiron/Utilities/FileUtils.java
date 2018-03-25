package me.Tom.Gridiron.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

public class FileUtils {

    public static String seraliseLocation(Location loc) {
        return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ();
    }

    public static Location deserialiseLocation(String[] storeloc) {
        return new Location(Bukkit.getWorld(storeloc[0]), Double.parseDouble(storeloc[1]), Double.parseDouble(storeloc[2]), Double.parseDouble(storeloc[3]));
    }

    public static Color getColourfromString(String colour) {
        switch(colour) {
            case "BLACK": return Color.BLACK;
            case "WHITE": return Color.WHITE;
            case "RED": return Color.RED;
            case "BLUE": return Color.BLUE;
            case "AQUA": return Color.AQUA;
            case "FUCHSIA": return Color.FUCHSIA;
            case "GRAY": return Color.GRAY;
            case "GREEN": return Color.GREEN;
            case "LIME": return Color.LIME;
            case "MAROON": return Color.MAROON;
            case "NAVY": return Color.NAVY;
            case "OLIVE": return Color.OLIVE;
            case "ORANGE": return Color.ORANGE;
            case "PURPLE": return Color.PURPLE;
            case "SILVER": return Color.SILVER;
            case "TEAL": return Color.TEAL;
            case "YELLOW": return Color.YELLOW;
        }
        return null;
    }
}
