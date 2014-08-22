package minefuse.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HelpCommand implements CommandExecutor {
	
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender.hasPermission("minefuse.player.help"))
         if(cmd.getName().equalsIgnoreCase("help")){
             if(args.length == 1){
                     Player player = (Player) sender;
                     player.sendMessage(ChatColor.RED + "Minefuse Player:");
                     player.sendMessage(ChatColor.RED + "/Stats {Player} - Check Your/His Stats");
                     player.sendMessage(ChatColor.RED + "/Report {Player} - Report A Player");
                     player.sendMessage(ChatColor.RED + "/Menu - Opens Purchase Menu");
                     player.sendMessage(ChatColor.RED + "");
                  }
             if (sender.hasPermission("minefuse.admin.help"))
                 if(cmd.getName().equalsIgnoreCase("admin")){
                     if(args.length == 1){
                             Player player = (Player) sender;
                             player.sendMessage(ChatColor.RED + "Minefuse Admin:");
                             player.sendMessage(ChatColor.RED + "");
                             player.sendMessage(ChatColor.RED + "");
                             player.sendMessage(ChatColor.RED + "");
                             player.sendMessage(ChatColor.RED + "");
                          }
        	      if (args.length >= 2)
        	      {
        	    	Player player = (Player) sender;
        	        player.sendMessage(ChatColor.RED + "Invalid arguments.");
        	        player.sendMessage(ChatColor.RED + "Type /help for help.");
        	      }
        	    }
        	    return true;
        	  }
	return false;
	 }
}
