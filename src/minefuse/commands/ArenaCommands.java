package minefuse.commands;

import java.io.File;
import java.io.IOException;

import minefuse.Arena;
import minefuse.ArenaManager;
import minefuse.MineFuse;
import minefuse.Team;
import minefuse.utils.ChatMessage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ArenaCommands implements CommandExecutor{

	private File worldFile;
	public FileConfiguration terroristsarenaConfig;
	public FileConfiguration counterterroristsarenaConfig;
	public FileConfiguration bombSite1arenaConfig;
	public FileConfiguration bombSite2arenaConfig;
	public FileConfiguration endLocationarenaConfig;
	public FileConfiguration SpectatearenaConfig;
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Arena a1 = ArenaManager.getArena(1);
		if(label.equalsIgnoreCase("teststart")){
			if(args.length == 0){

			a1.setGameStarted(true);
			}
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("switch")){
					if(Team.getTeam((Player)sender).getTeamType() == "Terrorist"){
						MineFuse.teamTerrorist.removePlayer((Player)sender);
						MineFuse.teamCounterTerrorist.addPlayer((Player)sender);
						((Player)sender).sendMessage("put in counters");
					}else if(Team.getTeam((Player)sender).getTeamType() == "CounterTerrorist"){
						MineFuse.teamCounterTerrorist.removePlayer((Player)sender);
						MineFuse.teamTerrorist.addPlayer((Player)sender);
						((Player)sender).sendMessage("put in terrorists");
					}
				}
				if(args[0].equalsIgnoreCase("terrorist")){
					MineFuse.teamTerrorist.addPlayer((Player)sender);
					((Player)sender).sendMessage("Put in Terrorists");
				}
				if(args[0].equalsIgnoreCase("counter-terrorist")){
					MineFuse.teamCounterTerrorist.addPlayer((Player)sender);
					((Player)sender).sendMessage("Put in Counter-Terrorists");
				}
				if(args[0].equalsIgnoreCase("armbomb")){
					a1.getBombSite().getBlock().setType(Material.OBSIDIAN);
					a1.isBombSet = true;
				}
			}
		}
		if(sender instanceof Player){
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("createarena")){
				Arena a = new Arena(Integer.parseInt(args[0]));
				minefuse.utils.ChatMessage.sendMessage(p, "&aSuccesfully created Arena!");
				minefuse.utils.ChatMessage.sendMessage(p, "&bArena ID: "+args[0]);
			}
			// command: /setspawn [teamType] [arenaID]
			if(label.equalsIgnoreCase("setspawn")){
				worldFile = new File("arena.yml");
				if(args.length > 2 || args.length < 2){
					ChatMessage.sendMessage(p, "&cFormat: /setspawn [teamType] [arenaID]");
				}
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("terrorists")){
						ArenaManager.setTerroristSpawnLocation(Integer.parseInt(args[1]), p.getLocation());
						Player player = (Player)sender;
						terroristsarenaConfig = YamlConfiguration.loadConfiguration(worldFile);
						
						int x = player.getLocation().getBlockX();
						int y = player.getLocation().getBlockY();
						int z = player.getLocation().getBlockZ();
						
						terroristsarenaConfig.set("Spawns.Terrorists.x", x);
						terroristsarenaConfig.set("Spawns.Terrorists.y", y);
						terroristsarenaConfig.set("Spawns.Terrorists.z", z);
						terroristsarenaConfig.set("Spawns.Terrorists.world", p.getWorld().getName());
						try {
							terroristsarenaConfig.save(worldFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						ChatMessage.sendMessage(p, "&aSuccessfully set the &4Terrorist &aspawn location!");
						
					}
					if(args[0].equalsIgnoreCase("counter-terrorists")){
						ArenaManager.setCounterTerroristSpawnLoc(Integer.parseInt(args[1]), p.getLocation());
						Player player = (Player)sender;
						counterterroristsarenaConfig = YamlConfiguration.loadConfiguration(worldFile);
						int x = player.getLocation().getBlockX();
						int y = player.getLocation().getBlockY();
						int z = player.getLocation().getBlockZ();

						
						counterterroristsarenaConfig.set("Spawns.Counter-Terrorists.x", x);
						counterterroristsarenaConfig.set("Spawns.Counter-Terrorists.y", y);
						counterterroristsarenaConfig.set("Spawns.Counter-Terrorists.z", z);
						counterterroristsarenaConfig.set("Spawns.Counter-Terrorists.world", p.getWorld().getName());

						try {
							counterterroristsarenaConfig.save(worldFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						ChatMessage.sendMessage(p, "&aSuccessfully set &4Counter-Terrorists &aspawn location!");
					}
					if(args[0].equalsIgnoreCase("spectators")){
						ArenaManager.setSpecLocation(Integer.parseInt(args[1]), p.getLocation());
						Player player = (Player)sender;
						SpectatearenaConfig = YamlConfiguration.loadConfiguration(worldFile);
						
						int x = player.getLocation().getBlockX();
						int y = player.getLocation().getBlockY();
						int z = player.getLocation().getBlockZ();

						
						SpectatearenaConfig.set("Spawns.Spectators.x", x);
						SpectatearenaConfig.set("Spawns.Spectators.y", y);
						SpectatearenaConfig.set("Spawns.Spectators.z", z);
						SpectatearenaConfig.set("Spawns.Spectators.world", p.getWorld().getName());

						try {
							SpectatearenaConfig.save(worldFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						ChatMessage.sendMessage(p, "&aSuccessfully set &4Spectator &aspawn location!");
				}
			}

		}
			if(label.equalsIgnoreCase("setexit")){
				worldFile = new File("arena.yml");
				p.sendMessage("cmd works..");
				if(args.length > 1 || args.length < 1){
					ChatMessage.sendMessage(p, "&cFormat: /setexit [arenaID]");
				}
				if(args.length == 1){
					ArenaManager.setEndLocation(Integer.parseInt(args[0]), p.getLocation());
					Player player = (Player)sender;
					
					endLocationarenaConfig = YamlConfiguration.loadConfiguration(worldFile);
					
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					
					endLocationarenaConfig.set("Spawns.endlocation.x", x);
					endLocationarenaConfig.set("Spawns.endlocation.y", y);
					endLocationarenaConfig.set("Spawns.endlocation.z", z);
					endLocationarenaConfig.set("Spawns.endlocation.world", p.getWorld().getName());

					try {
						endLocationarenaConfig.save(worldFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ChatMessage.sendMessage(p, "&aSuccessfully set &4Exit &blocation!");
				}
			}
			if(label.equalsIgnoreCase("setbombsite1")){
				worldFile = new File("arena.yml");
				if(args.length < 1 || args.length > 1){
					ChatMessage.sendMessage(p, "&cFormat: /setbombsite1 [arenaID]");
				}
				if(args.length == 1){
					ArenaManager.setBombSite(Integer.parseInt(args[0]), p.getLocation());
					Player player = (Player)sender;
					bombSite1arenaConfig = YamlConfiguration.loadConfiguration(worldFile);
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					
					bombSite1arenaConfig.set("Spawns.bombSite1.x", x);
					bombSite1arenaConfig.set("Spawns.bombSite1.y", y);
					bombSite1arenaConfig.set("Spawns.bombSite1.z", z);

					bombSite1arenaConfig.set("Spawns.bombSite1.world", p.getWorld().getName());

					try {
						bombSite1arenaConfig.save(worldFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ChatMessage.sendMessage(p, "&aSuccessfully set &4Bomb Site 1 &alocation!");
				}
				
			}
			if(label.equalsIgnoreCase("setbombsite2")){
				worldFile = new File("arena.yml");
				if(args.length < 1 || args.length > 1){
					ChatMessage.sendMessage(p, "&cFormat: /setbombsite2 [arenaID]");
				}
				if(args.length == 1){
					ArenaManager.setBombSite2(Integer.parseInt(args[0]), p.getLocation());
					Player player = (Player)sender;
					bombSite2arenaConfig = YamlConfiguration.loadConfiguration(worldFile);
					
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();

					
					bombSite2arenaConfig.set("Spawns.bombSite2.x", x);
					bombSite2arenaConfig.set("Spawns.bombSite2.y", y);
					bombSite2arenaConfig.set("Spawns.bombSite2.z", z);
					bombSite2arenaConfig.set("Spawns.bombSite2.world", p.getWorld().getName());

					try {
						bombSite2arenaConfig.save(worldFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ChatMessage.sendMessage(p, "&aSuccessfully set &4Bomb Site 2 &alocation!");
				}
				
			}
		return false;
	}
		return false;
	}

}
