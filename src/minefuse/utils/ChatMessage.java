package minefuse.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ChatMessage {
	
	// Send color messages without the ChatColor.BLAH bull
	public static void sendMessage(Player p, String message){
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}
