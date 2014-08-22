package minefuse;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Logger;

import minefuse.commands.ArenaCommands;
import minefuse.commands.ReportCommand;
import minefuse.events.GameListeners;
import minefuse.listeners.DisconnectMid;
import minefuse.listeners.PlayerJoin;
import minefuse.utils.MySQLInfo;
import minefuse.utils.SQLHelper;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MineFuse extends JavaPlugin implements Listener {
	
	public static Team teamTerrorist;
	public static Team teamCounterTerrorist;
	public JavaPlugin jp;
	Plugin pl;
	public static MineFuse plugin;
	
	public static Economy econ = null;
	boolean economy = true;
	public File arenaConfigFile;
	public YamlConfiguration arenaConfig;
	public FileConfiguration config;
	public HashSet<Stats> stats;
	public SQLHelper sqlhelp;
	public final static Logger logger = Logger.getLogger("Minecraft");
	public static Integer lobbySec = 45;
	
	public void onEnable() {
		jp = this;
		Arena a = new Arena(1);
		plugin = this;
		this.pl = this;
		registerListeners();
		this.saveConfig();
		teamTerrorist = new Team("Terrorist");
		teamCounterTerrorist = new Team("CounterTerrorist");
		
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("createarena").setExecutor(new ArenaCommands());
		getCommand("setexit").setExecutor(new ArenaCommands());
		getCommand("setspawn").setExecutor(new ArenaCommands());
		getCommand("setbombsite1").setExecutor(new ArenaCommands());
		getCommand("setbombsite2").setExecutor(new ArenaCommands());
		getCommand("teststart").setExecutor(new ArenaCommands());
		/*
		try{
			ConfigManager.load(this, "arenas.yml");
			getServer().getPluginManager().registerEvents(this, this);
			
			sqlhelp = new SQLHelper(config.getBoolean("useMySQL"), getMySQLInfo());
			getLogger().info("Getting SQL Stats!");
			stats = sqlhelp.getStats();
			
			registerParty();
			if (economy) {
				if (!setupEconomy()) {
					getLogger().severe(String.format("[%s] - No iConomy dependency found! Disabling Economy.", getDescription().getName()));
					economy = false;
				}
			
		}} catch (Exception e){
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
        */
		logger.info("[MINEFUSE] MineFuse v1.0 by BuzzyOG and MoeMix has been enabled!");
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
 
 
	
	public void onDisable(){
		logger.info("MineFuse shutting down!");
		this.saveConfig();
		sqlhelp.sendStats(stats);
		logger.info("MineFuse disabled!");
		
	}
	
	private MySQLInfo getMySQLInfo() {
		if(config.getBoolean("useMySQL")) 
			return new MySQLInfo(
					config.getString("MySQL.address"), 
					config.getString("MySQL.database"), 
					config.getString("MySQL.username"), 
					config.getString("MySQL.password"), 
					config.getString("MySQL.port"));
		else return new MySQLInfo();
	}


	public Stats getStatsForPlayer(String player){
		for(Stats s : stats)
			if(s.getPlayerName().equals(player))
				return s;
		Stats stat = new Stats(player);
		stats.add(stat);
		return stat;
	}
	
	void registerParty() {
	}

	public Stats getStatsForPlayer(Player player){
		return getStatsForPlayer(player.getName());
	}
	
	public void registerListeners(){
		Bukkit.getServer().getPluginManager().registerEvents(new GameListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DisconnectMid(), this);
	}
	
	public static MineFuse getInstance() {
		return plugin;
	}
} 
