package minefuse.events;

import java.lang.reflect.Field;
import java.util.ArrayList;

import minefuse.Arena;
import minefuse.ArenaManager;
import minefuse.MineFuse;
import minefuse.Stats;
import minefuse.Team;
import minefuse.utils.ChatMessage;
import net.minecraft.server.v1_7_R4.EntityEnderDragon;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameListeners implements Listener{
	public MineFuse plugin;
	public GameListeners(MineFuse plugin){
		this.plugin = plugin;
	}
	/*
	 * 
	 * Methods that create an Enderdragon bar without BarAPI :)
	 * 
	 */
	
	public final static int id = 1337;
	public static void sendEnderdragon(Player p, String name, int health, int max){
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { 1337 }));
	    if (name == null) {
	      return;
	    }
	    EntityEnderDragon ender = new EntityEnderDragon(((CraftWorld)p.getWorld()).getHandle());
	    try
	    {
	      Field id = net.minecraft.server.v1_7_R4.Entity.class.getDeclaredField("id");
	      id.setAccessible(true);
	      id.set(ender, Integer.valueOf(1337));
	    }
	    catch (Exception localException) {}
	    ender.setHealth(health / max * 200.0F);
	    ender.setPosition(p.getLocation().getX(), -500.0D, p.getLocation().getZ());
	    ender.getDataWatcher().watch(10, name);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(ender));
	  }
	  
	  public static void removeBar(Player p)
	  {
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { 1337 }));
	  }
	
	@EventHandler
	public void onBombPlace(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType().equals(Material.TNT)){
				if(ArenaManager.getArena(p).inBombSite(e.getClickedBlock().getLocation()) == true){
					if(ArenaManager.getArena(1).isBombArmed() != true){
						e.setCancelled(true);
						ArenaManager.getArena(p).getArmedBombSite().getBlock().setType(Material.OBSIDIAN);
						ArenaManager.getArena(1).setBombArmed(true);
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4The Bomb has been set! &bIt will detonate in 45 seconds!"));
						//Location bombSite = e.getClickedBlock().getLocation();
						//ArenaManager.getArena(p).bombSiteWithBomb = bombSite;
						Bukkit.getServer().getPluginManager().callEvent(new BombArmedEvent(ArenaManager.getArena(p)));
					}else if(ArenaManager.getArena(p).isBombArmed() == true){
						e.setCancelled(true);
						ChatMessage.sendMessage(p, "&c&lOne Bomb is already armed!");
					}
				}else{
					if(ArenaManager.getArena(p).gameStarted() == true){
						e.setCancelled(true);
						ChatMessage.sendMessage(p, "&cThis isn't the bomb site!");
						p.updateInventory();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBombDefuse(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.OBSIDIAN)){
			e.getPlayer().sendMessage("obsidian block check");
			if(Team.getTeam(e.getPlayer()) != null){
				e.getPlayer().sendMessage("null team check");
				if(Team.getTeam(e.getPlayer()).getTeamType().equalsIgnoreCase("CounterTerrorist")){
					e.getPlayer().sendMessage("correct team check");
					if(e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
						e.getPlayer().sendMessage("pick check");
						if(ArenaManager.inBombSite(ArenaManager.getArena(e.getPlayer()).getID(), e.getBlock().getLocation())){
							e.getPlayer().sendMessage("in site check");
							if(ArenaManager.getArena(e.getPlayer()).isBombArmed() == true){
								e.getPlayer().sendMessage("bomb armed check");
								ArenaManager.getArena(e.getPlayer()).setBombArmed(false);
							}
							else{
								ChatMessage.sendMessage(e.getPlayer(), "&c&lThe bomb is not set! What are you trying to defuse?");
							}
						}
						else{
							ChatMessage.sendMessage(e.getPlayer(), "&c&lYou are not in the bomb site!");
						}
					}
					else{
						ChatMessage.sendMessage(e.getPlayer(), "&c&lYou can only defuse the bomb with your &bBomb Defuser! &c(&bDiamond Pickaxe&c)");
					}
				}
				else{
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou cannot defuse the bomb! You're a Terrorist!"));
				}
			}
			else{
				e.getPlayer().sendMessage("Your team returns null");
			}
		}else{
			if(ArenaManager.getArena(e.getPlayer()).gameStarted == false){
				e.getPlayer().sendMessage("ended up here");
			}
		}
	}
	
	@EventHandler
	public void onBombSet(BombArmedEvent e){
		Arena a = e.getArena();
		a.startBombTimer();
		for (Player p : a.players){
			p.setCompassTarget(a.getArmedBombSite());
			if(Team.getTeam(p).getTeamType().equalsIgnoreCase("counterterrorist")){
				ChatMessage.sendMessage(p, "&aYour compass has been updated to track the Bomb Site with the bomb");
			}
		}
	}
	
	@EventHandler
	public void onWin(WinEvent e){
		Arena arena = e.getArena();
		arena.currentRound++;
		int terroristWins = arena.getTerroristWins();
		int counterTerroristWins = arena.getCounterTerroristWins();

		if(arena.currentRound == 5){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bTeams have now switched!"));
			for(Player p : arena.players){
				if(Team.getTeam(p).getTeamType() == "Terrorist"){
					MineFuse.teamTerrorist.removePlayer(p);	
					MineFuse.teamCounterTerrorist.addPlayer(p);
					ChatMessage.sendMessage(p, "&6You are now a &4Counter-Terrorist&6! The teams have switched!");
				}
				else if(Team.getTeam(p).getTeamType() == "CounterTerrorist"){
					MineFuse.teamCounterTerrorist.removePlayer(p);
					MineFuse.teamTerrorist.addPlayer(p);
					ChatMessage.sendMessage(p, "&6You are now a &4Terrorist&6! The teams have switched!");
				}
			}
			arena.startNewRound();
		}
		else if(terroristWins == 30 || counterTerroristWins == 30){
			if(terroristWins == 30){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bThe &4Terrorists have won!"));
			}else if(counterTerroristWins == 30 ){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aThe game has ended! &bThe &4Counter-Terrorists have won!"));
			}
			arena.setGameStarted(false);
		}else{
			arena.startNewRound();
		}
	}
	
	@EventHandler
	public void onGameStart(GameStartedEvent e){
		Arena a = e.getArena();
		for(Player p : a.getTerrorists()){
			p.getInventory().clear();
			ItemStack tnt = new ItemStack(Material.TNT);
			ItemMeta tntim = tnt.getItemMeta();
			tntim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb"));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.AQUA + "Plant this at the bomb site!");
			lore.add(ChatColor.GREEN+"Follow your compass to find the bomb site! Place bomb on top of Diamond Block!");
			tntim.setLore(lore);
			tnt.setItemMeta(tntim);
			p.getInventory().addItem(tnt);
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta compassim = compass.getItemMeta();
			compassim.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBomb Site &a&oTracker"));
			compass.setItemMeta(compassim);
			p.getInventory().addItem(compass);
			p.updateInventory();
			ChatMessage.sendMessage(p, "&bYou are a &4Terrorist&b! Plant the bomb at the bomb site!");
		}
		for(Player p : a.getCounterTerrorists()){
			ChatMessage.sendMessage(p, "&bYou are a &4Counter Terrorist&b! Kill all Terrorists and try to defuse the bomb if they plant it!");
		}
	}
	
	@EventHandler
	public void onDie(PlayerDeathEvent e){
		Player killer = e.getEntity().getKiller();
		Player died = e.getEntity();
		e.getDrops().clear();
		if(Team.getTeam(died).getTeamType().equalsIgnoreCase("terrorist")){
			died.setHealth(20);
			died.setFoodLevel(20);
			died.teleport(ArenaManager.getArena(1).getSpecLocation());
			//Stats diedStats = MineFuse.getInstance().getStatsForPlayer(died);
			//diedStats.addDeath();
			e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&b[&4Terrorist&b] &a"+died.getName()+" &6was killed by &b[&4Counter-Terrorist&b] &a"+died.getKiller().getName()+"&6!"));
			ArenaManager.getArena(died).removeTerrorist();
		}
		else if(Team.getTeam(died).getTeamType().equalsIgnoreCase("counterterrorist")){
			died.setHealth(20);
			died.setFoodLevel(20);
			died.teleport(ArenaManager.getArena(1).getSpecLocation());
			//Stats diedStats = MineFuse.getInstance().getStatsForPlayer(died);
			//diedStats.addDeath();
			e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&b[&4Counter-Terroist&b] &a"+died.getName()+" &6was killed by &b[&4Terrorist&b] &a"+died.getKiller().getName()+"&6!"));
			ArenaManager.getArena(died).removeCounterTerrorist();
		}
		//Stats killerStats = MineFuse.getInstance().getStatsForPlayer(killer);
		//killerStats.addKill();
	}
	
	
}
