package minefuse.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuSetup {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     //add feature where can only use in lobby count down
		//add feature where can only use in 15 sec b4 game
	         if(cmd.getName().equalsIgnoreCase("menu")){
	             if(args.length == 1){
	                     Player player = (Player) sender;
	                     openPurchaseMenu(player);
	                  }
	        	      if (args.length >= 0)
	        	      {
	        	    	Player player = (Player) sender;
	        	        player.sendMessage(ChatColor.RED + "Invalid arguments.");
	        	        player.sendMessage(ChatColor.RED + "Type /help for help.");
	        	      }
	        	    }
	        	    return true;
	}

	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§2Defuse Speed")) {
			e.setCancelled(true);
			p.closeInventory();
			openBombMenu(p);
		}else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§2Classes")) {
			e.setCancelled(true);
			p.closeInventory();
			openKitMenu(p);
		}else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§2Leather Kit")) {
			e.setCancelled(true);
			p.closeInventory();
			EquipPlayer.equipLeatherClass(p);
		}else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§2Iron Kit")) {
			e.setCancelled(true);
			p.closeInventory();
			EquipPlayer.equipIronClass(p);
		}
	}
    
    public static void openPurchaseMenu(Player player){
        Inventory Purchasinv = Bukkit.createInventory(null, 9, "§aPurchase Menu");
        
        ItemStack kits = new ItemStack(Material.WOOD_SWORD, 1);
        ItemStack bomb = new ItemStack(Material.TNT, 1);
    
        ItemMeta bombMeta =  bomb.getItemMeta();
        ItemMeta kitsMeta =  kits.getItemMeta();
        
        bombMeta.setDisplayName("§2Defuse Speed");
        kitsMeta.setDisplayName("§2Classes");
        
        bomb.setItemMeta(bombMeta);
        kits.setItemMeta(kitsMeta);
        
        Purchasinv.setItem(2, bomb);
        Purchasinv.setItem(6, kits);
        
        player.openInventory(Purchasinv);
    }
    
    public static void openBombMenu(Player player){
        Inventory bombinv = Bukkit.createInventory(null, 9, "§3Bomb Menu");
        
        ItemStack fasterx1 = new ItemStack(Material.TNT, 1);
        ItemStack fasterx2 = new ItemStack(Material.TNT, 2);
        ItemStack fasterx3 = new ItemStack(Material.TNT, 3);
    
        ItemMeta fasterx1Meta =  fasterx1.getItemMeta();
        ItemMeta fasterx2Meta =  fasterx2.getItemMeta();
        ItemMeta fasterx3Meta =  fasterx3.getItemMeta();
        
        
        fasterx1Meta.setDisplayName("§2Bomb Defuse Speed x1");
        fasterx2Meta.setDisplayName("§2Bomb Defuse Speed x2");
        fasterx3Meta.setDisplayName("§2Bomb Defuse Speed x3");
        
        fasterx1.setItemMeta(fasterx1Meta);
        fasterx2.setItemMeta(fasterx2Meta);
        fasterx3.setItemMeta(fasterx3Meta);
        
        bombinv.setItem(0, fasterx1);
        bombinv.setItem(1, fasterx2);
        bombinv.setItem(2, fasterx3);

        
        player.openInventory(bombinv);
    }
    
    public static void openKitMenu(Player player){
        Inventory kitinv = Bukkit.createInventory(null, 9, "§3Class Menu");
        
        ItemStack leatherkit = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemStack ironkit = new ItemStack(Material.IRON_CHESTPLATE, 1);
    
        ItemMeta leatherkitMeta =  leatherkit.getItemMeta();
        ItemMeta ironkitMeta =  ironkit.getItemMeta();
        
        
        leatherkitMeta.setDisplayName("§2Leather Kit");
        ironkitMeta.setDisplayName("§2Iron Kit");
        
        leatherkit .setItemMeta(leatherkitMeta);
        ironkit.setItemMeta(ironkitMeta);
        
        kitinv.setItem(0, leatherkit );
        kitinv.setItem(1, ironkit);

        
        player.openInventory(kitinv);
    }

}