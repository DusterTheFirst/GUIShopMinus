package com.dusterthefirst.guishopminus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;

import com.dusterthefirst.guishopminus.GuiShopMinus;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

@org.bukkit.plugin.java.annotation.command.Command(name = "showinv", desc = "Show a test inventory", permission = "test.inv", permissionMessage = "You do not have permission!", usage = "Usage: /<command>")
@Permission(name = "test.inv", desc = "Allows showinv command", defaultValue = PermissionDefault.OP)
public class ShowInv implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Economy econ = GuiShopMinus.getEconomy();
		
		if (sender instanceof Player) {
            Player player = (Player) sender;
            
            // Lets give the player 1.05 currency (note that SOME economic plugins require rounding!)
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player))));
            EconomyResponse r = econ.depositPlayer(player, 1.05);
            if(r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            
            player.openInventory(Bukkit.createInventory(player, 9, ChatColor.RED + "GUI " + ChatColor.YELLOW + "SHOP"));
            return true;
        } else {
        	sender.sendMessage(ChatColor.RED + "You must run this command as a player");
        	return true;
        }
		
//		return false;
	}
	
}
