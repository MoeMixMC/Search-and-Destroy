package minefuse;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ArenaManager {
	
	private static ArenaManager arenaManager;
	
	public static Arena getArena(int id){
		for(Arena a : Arena.arenas){
			if(a.getID() == id){
				return a;
			}
		}
		return null;
	}
	
	public static boolean inBombSite(int arenaID, Location bombSite){
		Arena a  = getArena(arenaID);
		if(a.getBombSite().getBlock().getX() == bombSite.getBlock().getX() && a.getBombSite().getBlock().getY() == bombSite.getBlockY() && a.getBombSite().getBlockZ() == bombSite.getBlockZ()){
			return true;
		}
		if(a.getBombSite2().getBlockX() == bombSite.getBlockX() && a.getBombSite2().getBlockY() == bombSite.getBlockY() && a.getBombSite2().getBlockZ() == bombSite.getBlockZ()){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isInGame(Player p){
		for(Arena a : Arena.arenas){
			if(a.players.contains(p)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	public static Arena getArena(Player p){
		for(Arena a : Arena.arenas){
			if(a.players.contains(p)){
				return a;
			}
		}
		return null;
	}
	
	public static void setTerroristSpawnLocation(int arenaID, Location location){
		Arena a = getArena(arenaID);
		try{
			a.terroristSpawn = location;
		}catch(Exception e){
			Logger.getLogger("Minecraft").severe(""+e);
		}
	}
	
	public static void setEndLocation(int arenaID, Location location){
		Arena a = getArena(arenaID);
		try{
			a.endLocation = location;
		}catch(Exception e){
			Logger.getLogger("Minecraft").severe(""+e);
		}
	}
	
	public static void setCounterTerroristSpawnLoc(int arenaID, Location location){
		Arena a = getArena(arenaID);
		try{
			a.counterTerroristSpawn = location;
		}catch(Exception e){
			Logger.getLogger("Minecraft").severe(""+e);
		}
	}
	
	public static void removePlayer(int arenaID, Player p){
		if(ArenaManager.isInGame(p) == true){
			Arena a = getArena(arenaID);
			a.players.remove(p);
			p.teleport(a.getEndLocation());
		}else{
			p.sendMessage("Not in a game");
		}
	}
	
	public static void setBombSite(int arenaID, Location bombSite){
		Arena a = getArena(arenaID);
		a.bombSite = bombSite;
	}
	
	public static void setBombSite2(int arenaID, Location bombSite){
		Arena a = getArena(arenaID);
		a.bombSite2 = bombSite;
	}

	public static ArenaManager getInstance() {
		if (arenaManager == null)
			arenaManager = new ArenaManager();
		return arenaManager;
	}
	
	public static void setSpecLocation(int arenaID, Location location){
		getArena(arenaID).spectatorLocation = location;
	}
	
	
	
	
}
