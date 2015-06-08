package minefuse;
//imports
import java.io.File;
import java.util.ArrayList;

import minefuse.events.GameStartedEvent;
import minefuse.events.WinEvent;
import minefuse.utils.ChatMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class Arena {
	
	public static SimpleScoreboard board = new SimpleScoreboard(ChatColor.translateAlternateColorCodes('&', "&4&lMine&b&lFuse"));
	
	public FileConfiguration arenaConfig;
	public static ArrayList<Player> counterTerrorists = new ArrayList<Player>();
	public static ArrayList<Player> terrorists = new ArrayList<Player>();
	public int terroristWins = 0, counterTerroristWins = 0, totalRounds = 30, currentRound = 1, terroristsLeft = 3, counterTerroristsLeft = 3;
	public boolean isBombSet = false, gameStarted = false, teamSwitched = false, secondBoolean;
	public int id;
	public Location terroristSpawn, counterTerroristSpawn, endLocation, bombSite, bombSite2, spectatorLocation, armedSite, bombSiteWithBomb;
	public static ArrayList<Arena> arenas = new ArrayList<Arena>();
	public ArrayList<Player> players = new ArrayList<Player>();
	
	public Arena(int arenaID){
		id = arenaID;
		arenas.add(this);
	}
	
	public Location getArmedBombSite(){
		return armedSite;
	}
	
	public Location getBombSiteWithBomb(){
		return bombSiteWithBomb;
	}
	
	public Location getBombSite(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.bombSite1.x");
			String locy = arenaConfig.getString("Spawns.bombSite1.y");
			String locz = arenaConfig.getString("Spawns.bombSite1.z");
			String world = arenaConfig.getString("Spawns.bombSite1.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
		
	}
	
	public Location getSpecLocation(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.Spectators.x");
			String locy = arenaConfig.getString("Spawns.Spectators.y");
			String locz = arenaConfig.getString("Spawns.Spectators.z");
			String world = arenaConfig.getString("Spawns.Spectators.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
		
	}
	
	public int getTerroristsLeft(){
		return terroristsLeft;
	}
	
	public int getCounterTerroristsLeft(){
		return counterTerroristsLeft;
	}
	
	public void removeTerrorist(){
		terroristsLeft--;
		if(getTerroristsLeft() == 0){
			if(isBombArmed() == true){
				for(Player p : getCounterTerrorists()){
					ChatMessage.sendMessage(p, "&3&lLast Terrorist was killed! Now you must defuse the bomb!");
				}
			}else{
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lMineFuse > &6The last Terrorist has been killed! Round win to Counter-Terrorists!"));
				addWinToCounterTerrorists();
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Terrorist Wins: &b" + getTerroristWins()));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Counter Terrorist Wins: &b"+ getCounterTerroristWins()));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Total Rounds: &b30"));
			}
		}
	}
	public void removeCounterTerrorist(){
		counterTerroristsLeft--;
		if(getCounterTerroristsLeft() == 0){
			if(isBombArmed() == true){
				for(Player p : getTerrorists()){
					ChatMessage.sendMessage(p, "&a&lAll Counter-Terrorists have been killed! Wait for bomb to detonate. Good Job!");
				}
			}else{
				addWinToTerrorists();
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lMineFuse > &6The last Counter-Terrorist has been killed! Round win to Terrorists!"));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Terrorist Wins: &b" + getTerroristWins()));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Counter Terrorist Wins: &b"+ getCounterTerroristWins()));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Total Rounds: &b30"));
			}
		}
	}
	
	public int getCurrentRound(){
		return currentRound;
	}
	
	public Location getBombSite2(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.bombSite2.x");
			String locy = arenaConfig.getString("Spawns.bombSite2.y");
			String locz = arenaConfig.getString("Spawns.bombSite2.z");
			String world = arenaConfig.getString("Spawns.bombSite2.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
		
	}
	
	public int getTerroristWins(){
		return terroristWins;
	}
	
	public int getCounterTerroristWins(){
		return counterTerroristWins;
	}
	
	public int getTotalRounds(){
		return totalRounds;
	}
	
	public void addWinToTerrorists(){
			terroristWins++;
			Bukkit.getServer().getPluginManager().callEvent(new WinEvent(this, "notbykill"));
	}
	
	public void addWinToCounterTerrorists(){
			counterTerroristWins++;
			Bukkit.getServer().getPluginManager().callEvent(new WinEvent(this, "notbykill"));

	}
	
	public int getID(){
		return id;
	}
	
	public Location getTerroristSpawn(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.Terrorists.x");
			String locy = arenaConfig.getString("Spawns.Terrorists.y");
			String locz = arenaConfig.getString("Spawns.Terrorists.z");
			String world = arenaConfig.getString("Spawns.Terrorists.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
	}
	
	public Location getCounterTerroristSpawn(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.Counter-Terrorists.x");
			String locy = arenaConfig.getString("Spawns.Counter-Terrorists.y");
			String locz = arenaConfig.getString("Spawns.Counter-Terrorists.z");
			String world = arenaConfig.getString("Spawns.Counter-Terrorists.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
		
	}
	
	public Location getEndLocation(){
		File arenaConfigFile = new File("arena.yml");
		arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
			String locx = arenaConfig.getString("Spawns.endlocation.x");
			String locy = arenaConfig.getString("Spawns.endlocation.y");
			String locz = arenaConfig.getString("Spawns.endlocation.z");
			String world = arenaConfig.getString("Spawns.endlocation.world");
			int x = Integer.parseInt(locx);
			int y = Integer.parseInt(locy);
			int z = Integer.parseInt(locz);
			
			Location location = new Location(Bukkit.getWorld(world), x, y, z);
			return location;
		
	}
	
	public boolean isBombArmed(){
		return isBombSet;
	}
	
	public void setBombArmed(boolean isArmed){
		this.isBombSet = isArmed;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void startBombTimer(){
		new BombTimer(MineFuse.getInstance().jp, 45, this).runTaskTimer(MineFuse.getInstance().jp, 20, 20);
	}
	
	public void startNewRound(){
		isBombSet = false;
		terroristsLeft = 3;
		counterTerroristsLeft = 3;
		secondBoolean = false;
		getBombSite().getBlock().setType(Material.AIR);
		getBombSite2().getBlock().setType(Material.AIR);
		for(Player p : Bukkit.getOnlinePlayers()){
			teleportToStart(p);
		}
	}
	
	public boolean gameStarted(){
		return gameStarted;
	}
	
	public void setGameStarted(boolean isStarted){
		gameStarted = isStarted;
		if(isStarted == true){
			Bukkit.broadcastMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"MineFuse > "+ChatColor.AQUA+"6th Player has joined! Game starting in 5 seconds!");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MineFuse.getInstance(), new Runnable(){
				public void run(){
					for(Player p : players){
						
						teleportToStart(p);
					}
				}
			}, 100);
		}
		else if(isStarted == false){
			for(Player p : players){
				p.teleport(getEndLocation());
				Team.playerTeam.remove(p);
			}
			players.clear();
		}
		
	}
	
	private void teleportToStart(Player player){
		/*board.blankLine();
		board.add(ChatColor.translateAlternateColorCodes('&', "&6Playing &4Mine&bFuse"));
		board.blankLine();
		board.add(ChatColor.translateAlternateColorCodes('&', "&6Your Team: "+Team.getTeam(player).getTeamType()));
		if(currentRound == 1){
			board.build();
		}
		board.send(player);
		*/
		if(Team.getTeam(player) == null){
			player.sendMessage("ur team is null");
		}
		else if(Team.getTeam(player).getTeamType().equalsIgnoreCase("Terrorist")){
			player.teleport(getTerroristSpawn());
			player.getInventory().clear();
			player.setHealth(20);
			player.setFoodLevel(20);
			ItemStack tnt = new ItemStack(Material.TNT);
			ItemMeta tntim = tnt.getItemMeta();
			tntim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb"));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.AQUA + "Plant this at the bomb site!");
			lore.add(ChatColor.GREEN+"Follow your compass to find the bomb site!");
			tntim.setLore(lore);
			tnt.setItemMeta(tntim);
			player.getInventory().addItem(tnt);
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta compassim = compass.getItemMeta();
			compassim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb Site &a&oTracker"));
			compass.setItemMeta(compassim);
			player.getInventory().addItem(compass);
			player.updateInventory();
			player.setCompassTarget(getBombSite());
			if(currentRound == 1){
				ChatMessage.sendMessage(player, "&bYou are a &4Terrorist&b! Plant the bomb at the bomb site!");
			}
		}		
		else if(Team.getTeam(player).getTeamType().equalsIgnoreCase("CounterTerrorist")){
			player.teleport(getCounterTerroristSpawn());
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
			ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
			ItemMeta pickim = pick.getItemMeta();
			pickim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb &a&oDefuser"));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&aDefuse the bomb with this!"));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&bDefuse bomb by mining the &5Obsidian &bblock at the bomb site!"));
			pickim.setLore(lore);
			pick.setItemMeta(pickim);
			player.getInventory().addItem(pick);
			player.updateInventory();
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta compassim = compass.getItemMeta();
			compassim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb Site &a&oTracker"));
			compass.setItemMeta(compassim);
			player.getInventory().addItem(compass);
			player.updateInventory();
			player.setCompassTarget(getBombSite());
			if(currentRound == 1){
				ChatMessage.sendMessage(player, "&c&lYou are a &4&lCounter-Terrorist&b! &cKill all the terrorists or defuse the bomb to win!");
			}
		}
	}
	
	public boolean inBombSite(Location location){
		if(location.getBlockX() == getBombSite().getBlockX() && location.getBlockY()+1 == getBombSite().getBlockY()){
			armedSite = getBombSite();
			return true;
		}
		if(location.getBlockX() == getBombSite2().getBlockX() && location.getBlockY()+1 == getBombSite2().getBlockY()){
			armedSite = getBombSite2();
			return true;
		}
		else{
			return false;
		}
	}
	public ArrayList<Player> getTerrorists(){
		return terrorists;
	}
	
	public ArrayList<Player> getCounterTerrorists(){
		return counterTerrorists;
	}

	
	
	

}
