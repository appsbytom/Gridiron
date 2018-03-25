package me.Tom.Gridiron.GameManager;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import me.Tom.Gridiron.ConfigManager.ConfigManager;
import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import me.Tom.Gridiron.PluginCore;
import me.Tom.Gridiron.Utilities.FileUtils;
import me.Tom.Gridiron.Utilities.ItemBuilder;
import me.Tom.Gridiron.Utilities.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.*;

@SuppressWarnings("deprecation")
public class GameManager {

    private PluginCore core;
    private ConfigManager configs;

    private Map<Player, PlayerManager> playerManagerMap;
    private List<Teams> allTeams;
    private Map<Player, GameScoreboard> playerGameScoreboardMap;
    public GameManager(PluginCore pl) {
        core = pl;
        configs = core.getConfigManager();

        playerManagerMap = core.getPlayerManagerMap();
        allTeams = core.getAllTeams();
        playerGameScoreboardMap = core.getPlayerGameScoreboardMap();
    }

    //Get all online players easily
    private List<Player> onlinePlayers = new ArrayList<>();

    //Key game information
    private int playersNeeded;
    public int getPlayersNeeded() {
        return playersNeeded;
    }
    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }

    private int lobbyCountdown;
    public int getLobbyCountdown() {
        return lobbyCountdown;
    }
    public void setLobbyCountdown(int lobbyCountdown) {
        this.lobbyCountdown = lobbyCountdown;
    }

    private int goalSwitchCountdown;
    public int getGoalSwitchCountdown() {
        return goalSwitchCountdown;
    }
    public void setGoalSwitchCountdown(int goalSwitchCountdown) {
        this.goalSwitchCountdown = goalSwitchCountdown;
    }

    private int gameTimer;
    public int getGameTimer() {
        return gameTimer;
    }
    public void setGameTimer(int gameTimer) {
        this.gameTimer = gameTimer;
    }

    private Location lobbySpawn;
    public Location getLobbySpawn() {
        return lobbySpawn;
    }
    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    private Location allySpawn;
    private Location axisSpawn;

    private List<Location> goalLocations = new ArrayList<>();
    public List<Location> getGoalLocations() {
        return goalLocations;
    }

    private Location currentGoal;
    public Location getCurrentGoal() {
        return currentGoal;
    }

    private List<Location> ballLocations = new ArrayList<>();
    public List<Location> getBallLocations() {
        return ballLocations;
    }

    private Location currentBall;
    private List<ItemStack> allyGear = new ArrayList<>();
    private List<ItemStack> axisGear = new ArrayList<>();
    private GameScoreboard board;
    private List<String> scoreboardLines;
    public List<String> getScoreboardLines() {
        return scoreboardLines;
    }

    public void setupGame() {
        playersNeeded = configs.getGridironCfg().getInt("GameManager.PlayersNeeded");
        lobbyCountdown = configs.getGridironCfg().getInt("GameManager.LobbyCountdownTime");
        goalSwitchCountdown = configs.getGridironCfg().getInt("GameManager.GoalSwitchTime");
        gameTimer = configs.getGridironCfg().getInt("GameManager.GameTime");

        String[] lobbyLoc = configs.getGridironCfg().getString("GameManager.LobbySpawn").split(", ");
        lobbySpawn = FileUtils.deserialiseLocation(lobbyLoc);

        configs.getGridironCfg().getStringList("GameManager.GoalLocations").forEach(s -> {
            String[] goalLoc = s.split(", ");
            goalLocations.add(FileUtils.deserialiseLocation(goalLoc));
        });

        configs.getGridironCfg().getStringList("GameManager.BallLocations").forEach(s -> {
            String[] ballLoc = s.split(", ");
            ballLocations.add(FileUtils.deserialiseLocation(ballLoc));
        });

        String[] allyLoc = configs.getGridironCfg().getString("Teams.Ally.Spawn").split(", ");
        allySpawn = FileUtils.deserialiseLocation(allyLoc);
        configs.getGridironCfg().getStringList("Teams.Ally.Gear.Armour").forEach(s -> {
            String[] armour = s.replace("[", "").replace("]", "").split(", ");
            String[] lore = armour[3].split(";");
            allyGear.add(new ItemBuilder(Material.valueOf(armour[0])).name(armour[1]).color(FileUtils.getColourfromString(armour[2])).lores(lore).flag(ItemFlag.HIDE_ATTRIBUTES).getItem());
        });
        String[] allySword = configs.getGridironCfg().getString("Teams.Ally.Gear.Sword").split(", ");
        String[] allySwordLore = allySword[3].split(";");
        allyGear.add(new ItemBuilder(Material.valueOf(allySword[0])).name(allySword[1]).durability(Integer.parseInt(allySword[2])).lores(allySwordLore).flags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE}).unbreakable(true).enchant(Enchantment.DAMAGE_ALL, 5).glow(true).getItem());
        allTeams.add(new Teams("Ally", 0, allySpawn, allyGear));

        String[] axisLoc = configs.getGridironCfg().getString("Teams.Axis.Spawn").split(", ");
        axisSpawn = FileUtils.deserialiseLocation(axisLoc);
        configs.getGridironCfg().getStringList("Teams.Axis.Gear.Armour").forEach(s -> {
            String[] armour = s.replace("[", "").replace("]", "").split(", ");
            String[] lore = armour[3].split(";");
            axisGear.add(new ItemBuilder(Material.valueOf(armour[0])).name(armour[1]).color(FileUtils.getColourfromString(armour[2])).lores(lore).flag(ItemFlag.HIDE_ATTRIBUTES).getItem());
        });
        String[] axisSword = configs.getGridironCfg().getString("Teams.Axis.Gear.Sword").split(", ");
        String[] axisSwordLore = axisSword[3].split(";");
        axisGear.add(new ItemBuilder(Material.valueOf(axisSword[0])).name(axisSword[1]).durability(Integer.parseInt(axisSword[2])).lores(axisSwordLore).flags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE}).unbreakable(true).enchant(Enchantment.DAMAGE_ALL, 5).glow(true).getItem());
        allTeams.add(new Teams("Axis", 0, axisSpawn, axisGear));

        scoreboardLines = configs.getGridironCfg().getStringList("Scoreboard.Lines");

        Bukkit.getOnlinePlayers().forEach(online -> {
            online.setGlowing(false);
            playerManagerMap.put(online, new PlayerManager(online, null, false));
        });

        GameState.setCurrentState(GameState.IN_LOBBY);

        lobbyWait();
    }

    public void lobbyWait() {
        int online = Bukkit.getOnlinePlayers().size();
        if(online > 0) {
            MessageUtils.broadcastMessage("");
            MessageUtils.broadcastMessage(Messages.PLAYERSNEEDED.replace("%Online%", String.valueOf(online)).replace("%PlayersNeeded%", String.valueOf(playersNeeded)));
            MessageUtils.broadcastMessage("");
            playerCheck(online);
        }
    }

    public void gameStart() {
        //Set the current goal
        setRandomGoal();

        //Spawn a ball in a random location
        setRandomBall();

        //Gets all online players and adds to list
        Bukkit.getOnlinePlayers().forEach(p -> onlinePlayers.add(p));

        //Shuffles list
        Collections.shuffle(onlinePlayers, new Random());

        //Runs through all players and puts into teams equally
        for(int online = 0; online < onlinePlayers.size(); online++) {
            Player p = onlinePlayers.get(online);
            if(online % 2 == 0) {
                playerManagerMap.get(p).setTeam(allTeams.get(0));
            }
            else {
                playerManagerMap.get(p).setTeam(allTeams.get(1));
            }
        }

        //Runs through online players and sets up inventorys/data and player locations based on teams
        onlinePlayers.forEach(p -> {
            p.resetTitle();
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.setFoodLevel(20);
            p.setHealth(20);
            Teams team = playerManagerMap.get(p).getTeam();
            p.getInventory().setHelmet(team.getGear().get(0));
            p.getInventory().setChestplate(team.getGear().get(1));
            p.getInventory().setLeggings(team.getGear().get(2));
            p.getInventory().setBoots(team.getGear().get(3));
            p.getInventory().setItem(0, team.getGear().get(4));
            p.teleport(team.getSpawn());
            board = new GameScoreboard(core);
            board.setTitle(MessageUtils.format(configs.getGridironCfg().getString("Scoreboard.Title")));
            board.setLines(scoreboardLines, p.getName(), team.getName(), team.getPoints(), goalSwitchCountdown, gameTimer);
            playerGameScoreboardMap.put(p, board);
            p.setScoreboard(board.getScoreboard());
        });

        new GameTimer(core).runTaskTimer(core, 0, 20L);

        //Set game to in progress
        GameState.setCurrentState(GameState.IN_PROGRESS);
    }

    public void gameStop() {
        if(GameState.getCurrentState() != null) {
            //Sets game to finished and removes all player information
            if(GameState.isState(GameState.IN_PROGRESS)) {
                GameState.setCurrentState(GameState.POST_GAME);
            }
            onlinePlayers.clear();
            playerManagerMap.clear();
            playerGameScoreboardMap.clear();
            ballLocations.clear();
            goalLocations.clear();
            allyGear.clear();
            axisGear.clear();
            String[] loc = configs.getGridironCfg().getString("GameManager.LobbySpawn").split(", ");
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                p.setGlowing(false);
                p.resetTitle();
                p.getInventory().clear();
                p.teleport(FileUtils.deserialiseLocation(loc));
                if(GameState.isState(GameState.POST_GAME)) {
                    p.kickPlayer(MessageUtils.format("&5&lGame over!\n&7Ally: &d" + allTeams.get(0).getPoints() + "\n&7Axis: &d" + allTeams.get(1).getPoints()));
                }
            });
            allTeams.clear();
            HologramsAPI.getHolograms(core).forEach(holo -> holo.delete());
        }
        Bukkit.getScheduler().cancelAllTasks();

        setupGame();
    }

    private void playerCheck(int online) {
        //Checks if required amount of players are online
        if(online >= playersNeeded) {
            //Check the game is not in progress
            if(GameState.isState(GameState.IN_LOBBY)) {
                //Start lobby countdown
                new LobbyCountdown(core).runTaskTimer(core, 0, 20L);
                GameState.setCurrentState(GameState.LOADING);
            }
        }
    }

    public void setRandomGoal() {
        //Set goal to a random location
        currentGoal = goalLocations.get(new Random().nextInt(goalLocations.size()));
    }

    public void setRandomBall() {
        HologramsAPI.getHolograms(core).forEach(hologram -> {
            if(hologram.getLine(0) instanceof TextLine) {
                if(((TextLine) hologram.getLine(0)).getText().equals(MessageUtils.format(configs.getGridironCfg().getString("Ball.Name")))) {
                    hologram.delete();
                }
            }
        });
        //Setting ball location to a random location
        currentBall = ballLocations.get(new Random().nextInt(ballLocations.size()));

        //Hologram spawning a ball
        spawnBall(currentBall);
    }

    public void spawnBall(Location loc) {
        final Hologram holo = HologramsAPI.createHologram(core, loc);
        holo.appendTextLine(MessageUtils.format(configs.getGridironCfg().getString("Ball.Name")));
        ItemLine item = holo.appendItemLine(new ItemBuilder(Material.valueOf(configs.getGridironCfg().getString("Ball.Item"))).getItem());
        item.setTouchHandler(p -> {
            if(GameState.isState(GameState.IN_PROGRESS)) {
                holo.delete();
                List<String> lore = new ArrayList<>();
                configs.getGridironCfg().getStringList("Ball.Lore").forEach(s -> {
                    lore.add(MessageUtils.format(s));
                });
                p.getInventory().setItem(8, new ItemBuilder(Material.valueOf(configs.getGridironCfg().getString("Ball.Item"))).name(configs.getGridironCfg().getString("Ball.Name")).lore(lore).glow(true).getItem());
                playerManagerMap.get(p).setHasBall(true);
                p.setGlowing(true);
                MessageUtils.broadcastMessage(Messages.BALLPICKEDUP.replace("%PlayerName%", p.getName()));
                //TODO action bar message
            }
        });
    }
}
