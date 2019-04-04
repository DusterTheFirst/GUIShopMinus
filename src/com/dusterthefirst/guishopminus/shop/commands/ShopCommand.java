package com.dusterthefirst.guishopminus.shop.commands;

import com.dusterthefirst.guishopminus.GuiShopMinus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Economy econ = GuiShopMinus.getEconomy();
		
		if (sender instanceof Player) {
            Player player = (Player) sender;
            
            player.openInventory(GuiShopMinus.shop.asInventory(player));
            return true;
        } else {
        	sender.sendMessage(ChatColor.RED + "You must run this command as a player");
        	return true;
        }
		
//		return false;
	}
	
}
