package minefuse.classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EquipPlayer {
    
    public static void equipLeatherClass(Player player){
        PlayerInventory leatherinv = player.getInventory();
        player.getInventory().clear();
        ItemStack healmet = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        player.getInventory().setHelmet(healmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggins);
        player.getInventory().setBoots(boots);
        PlayerInventory i = player.getInventory();
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
    } 
    
    public static void equipIronClass(Player player){
        PlayerInventory ironinv = player.getInventory();
        player.getInventory().clear();
        ItemStack healmet = new ItemStack(Material.IRON_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack leggins = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
        player.getInventory().setHelmet(healmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggins);
        player.getInventory().setBoots(boots);
        PlayerInventory i = player.getInventory();
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
        
    }

}