package minefuse.listeners;

import java.util.ArrayList;
import java.io.File;

import minefuse.Arena;
import minefuse.ArenaManager;
import minefuse.MineFuse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener{
	public static ArrayList<Player> firstThree = new ArrayList<Player>();
	public static ArrayList<Player> secondThree = new ArrayList<Player>();
	public static ArrayList<Player> allPlayers = new ArrayList<Player>();
	private Plugin plugin;
	public boolean canJoin = true;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Arena a = ArenaManager.getArena(1);
		a.players.add(e.getPlayer());
		Player p = e.getPlayer();
		if(Bukkit.getOnlinePlayers().length < 6 && canJoin == true){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lMineFuse > &bThere are &4"+Bukkit.getOnlinePlayers().length+" &bplayers online! &46 &bplayers needed to start!"));
		}else if(Bukkit.getOnlinePlayers().length == 6 && canJoin == true){
			
			
			for(Player player : Bukkit.getOnlinePlayers()){
				allPlayers.add(player);
			}
			
			for(Player player : getFirstThree()){
				MineFuse.teamTerrorist.addPlayer(player);
			}
			
			for(Player player : getSecondThree()){
				MineFuse.teamCounterTerrorist.addPlayer(player);
			}
			
			
			ArenaManager.getArena(1).setGameStarted(true);
			canJoin = false;
			
		}
		if(canJoin == false){
			p.kickPlayer("The Server is Full and/or IN-GAME. Please connect to a server that is not full. Thanks!");
		}

	}
	
	public static ArrayList<Player> getFirstThree(){
		firstThree.add(allPlayers.get(0));
		firstThree.add(allPlayers.get(1));
		firstThree.add(allPlayers.get(2));
		return firstThree;
	}
	public static ArrayList<Player> getSecondThree(){
		firstThree.add(allPlayers.get(3));
		firstThree.add(allPlayers.get(4));
		firstThree.add(allPlayers.get(5));
		return secondThree;
	}
	

}
