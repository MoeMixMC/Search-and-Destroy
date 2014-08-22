package minefuse;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class Team {
	
	public String teamType;
	public static HashMap<String, Team> playerTeam = new HashMap<String, Team>();
	public static ArrayList<Team> teams = new ArrayList<Team>();
	
	public Team(String team){
		teamType = team;
		teams.add(this);
	}
	
	public void addPlayer(Player p){
		playerTeam.put(p.getName(), this);
		if(getTeamType().equalsIgnoreCase("terrorist")){
			Arena.terrorists.add(p);
		}
		else if(getTeamType().equalsIgnoreCase("counterterrorist")){
			Arena.counterTerrorists.add(p);
		}
	}
	
	public void removePlayer(Player p){
		if(getTeamType().equalsIgnoreCase("terrorist")){
			Arena.terrorists.remove(p);
		}
		else if(getTeamType().equalsIgnoreCase("counterterrorist")){
			Arena.counterTerrorists.remove(p);
		}
		playerTeam.remove(p.getName());
	}
	
	public static Team getTeam(Player p){
		if(hasTeam(p) == true){
			return playerTeam.get(p.getName());
		}
		return null;
	}
	
	
	// check if player has a team
	public static boolean hasTeam(Player p){
		return playerTeam.containsKey(p.getName());
	}
	
	// Get the type of team (terrorist, counter terrorist)
	public String getTeamType(){
		return teamType;
	}
}
