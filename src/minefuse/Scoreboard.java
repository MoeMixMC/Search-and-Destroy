package minefuse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboard {
	/*
	 * Note:
	 * IDK if 2 different scoreboards are needed
	 * for 2 differnet teams
	 */
	public static org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
    public static Score seconds;
    public static Score maxplayers;
    public static org.bukkit.scoreboard.Scoreboard board;
    public static Score explosioncountdown;
	
    public static void setScoreBoard(Player p){
    	
    }
	
	public void LobbyScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("lobby", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"MineFuse");
		
		Score score = obj.getScore(ChatColor.GOLD+"Lobby Timer: ");
		score.setScore(0);
		Score score1 = obj.getScore(ChatColor.GOLD+"Team: ");
		score1.setScore(0);
		Score score2 = obj.getScore(ChatColor.GOLD+"Class: ");
		score2.setScore(0);
		Score score3 = obj.getScore(ChatColor.GOLD+"Players: ");
		score3.setScore(0);
		
		player.setScoreboard(board);
		
	}
	
	public static void CounterTerroristScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("counter", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"MineFuse");
		
		Score score = obj.getScore(ChatColor.GOLD+"Team: " + Team.getTeam(player).getTeamType());
		score.setScore(0);
		Score score1 = obj.getScore(ChatColor.GOLD+"Round: "+ArenaManager.getArena(1).getCurrentRound());
		score1.setScore(0);
		Score score2 = obj.getScore(ChatColor.GOLD+"Class: ");
		score2.setScore(0);
		player.setScoreboard(board);
		
	}
	
	public static void TerroristScoreboard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("terrorist", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"MineFuse");
		
		Score score = obj.getScore(ChatColor.GOLD+"Team: " + Team.getTeam(player).getTeamType());
		score.setScore(0);
		Score score1 = obj.getScore(ChatColor.GOLD+"Round: " +ArenaManager.getArena(1).getCurrentRound());
		score1.setScore(0);
		Score score2 = obj.getScore(ChatColor.GOLD+"Class: ");
		score2.setScore(0);
		player.setScoreboard(board);
		
	}
}
