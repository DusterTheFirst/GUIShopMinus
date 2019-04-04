package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import com.dusterthefirst.guishopminus.GuiShopMinus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Item {
	public Material material;
	private String name;
	private int amount;
	private double price;
	private double sellprice;

	public ItemStack toItem(int item, int shop) {
		// Get the stack
		ItemStack stack = new ItemStack(this.material, this.amount);
		// Get the metadata
		ItemMeta meta = stack.getItemMeta();

		// Set the display name
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));

		// Add the description as lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Buy: " + ChatColor.RED + "$" + this.price);
		lore.add(ChatColor.GRAY + "Sell: " + ChatColor.GREEN + "$" + this.sellprice);
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "To sell, click the item in your inv.");
		meta.setLore(lore);

		// Save the metadata
		stack.setItemMeta(meta);

		// Set custom NBT tags
		NBTItem nbti = new NBTItem(stack);
		nbti.setInteger("STORE-ITEM", item);
		nbti.setInteger("STORE-ITEM-SHOP", shop);

		return nbti.getItem();
	}

	public void buy(boolean isStack, Player player) {
		// Get the economy
		Economy econ = GuiShopMinus.getEconomy();

		// Get the amount of items to boy
		int amount = isStack ? this.material.getMaxStackSize() : this.amount;

		// Calculate the price
		double price = this.price * amount;

		// Withdraw the money
		EconomyResponse r = econ.withdrawPlayer(player, price);

		// Check the status of the transaction
		if (r.transactionSuccess()) {
			// Tell the player of their current currency status
			player.sendMessage(
					String.format(
							ChatColor.GREEN + "You were charged " + ChatColor.YELLOW + "%s" + ChatColor.GREEN
									+ " and now have " + ChatColor.BLUE + "%s",
							econ.format(r.amount), econ.format(r.balance)));

			// Give them the items
			player.getInventory().addItem(new ItemStack(this.material, amount));
		} else {
			// Send error message if there was one
			player.sendMessage(
					String.format(ChatColor.DARK_RED + "An error occured: " + ChatColor.YELLOW + "%s", r.errorMessage));
		}
	}

	public void sell(ItemStack item, Player player, int index) {
		// Get the economy
		Economy econ = GuiShopMinus.getEconomy();

		// Get the amount of items to sell
		int amount = item.getAmount();

		// Calculate the price
		double price = this.sellprice * amount;

		// Deplosit the money
		EconomyResponse r = econ.depositPlayer(player, price);

		// Check the status of the transaction
		if (r.transactionSuccess()) {
			// Tell the player of their current currency status
			player.sendMessage(
					String.format(
							ChatColor.GREEN + "You were given " + ChatColor.YELLOW + "%s" + ChatColor.GREEN
									+ " and now have " + ChatColor.BLUE + "%s",
							econ.format(r.amount), econ.format(r.balance)));

			// Give them the items
			player.getInventory().setItem(index, new ItemStack(Material.AIR));
		} else {
			// Send error message if there was one
			player.sendMessage(
					String.format(ChatColor.DARK_RED + "An error occured: " + ChatColor.YELLOW + "%s", r.errorMessage));
		}
	}
}