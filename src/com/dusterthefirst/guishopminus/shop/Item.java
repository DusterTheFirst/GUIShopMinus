package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;

public class Item {
	public Material icon;
	public String name;
	public double price;
	public double sellprice;

	public Item(Material icon, String name, double price, double sellprice) {
		this.icon = icon;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.price = price;
		this.sellprice = sellprice;
	}
	
	public ItemStack toItem(int index) {
		// Get the stack
		ItemStack stack = new ItemStack(this.icon);
		// Get the metadata
		ItemMeta meta = stack.getItemMeta();

		// Set the display name
		meta.setDisplayName(this.name);

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
		nbti.setInteger("STORE-ITEM", index);

		return nbti.getItem();
	}
}