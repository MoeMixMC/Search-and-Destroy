package minefuse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
public class BombTimer extends BukkitRunnable {
 public Arena arena;
    private final JavaPlugin plugin;
 
    private int counter;
 
    public BombTimer(JavaPlugin plugin, int counter, Arena arena) {
        this.plugin = plugin;
        if (counter < 1) {
            throw new IllegalArgumentException("counter must be greater than 1");
        } else {
            this.counter = counter;
        }
        this.arena = arena;
    }
 
    @Override
    public void run() {
    	if(counter > 0){
    		counter--;
    		
    	}
        if (counter > 0 && arena.isBombArmed() == true) { 
            if(counter == 30 || counter == 15){
            	Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lMineFuse > &bThe &4Bomb &bis detonating in &4"+counter+"&4 seconds!"));
            }
            if(counter <= 10){
            	Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lMineFuse > &bThe &4Bomb &bis detonating in &4"+counter+"&4 seconds!"));
            }
        }
        if(counter > 0 && arena.isBombArmed() == false){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bThe Counter Terrorists have disarmed the Bomb! Round win to Counter Terrorists!"));
			arena.addWinToCounterTerrorists();
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Terrorist Wins: &b" + arena.getTerroristWins()));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Counter Terrorist Wins: &b"+ arena.getCounterTerroristWins()));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Total Rounds: &b30"));
			this.cancel();
        }
        if(counter == 0){
        	if(arena.getTerroristWins() == 29){
        		arena.setGameStarted(false);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bThe &4Terrorists have won!"));
        	}

			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bThe Bomb has detonated! Round win to Terrorists!"));
			arena.addWinToTerrorists();			
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Terrorist Wins: &b" + arena.getTerroristWins()));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Counter Terrorist Wins: &b"+ arena.getCounterTerroristWins()));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4Total Rounds: &b30"));
			this.cancel();
        }
        	
        }
    }
 

