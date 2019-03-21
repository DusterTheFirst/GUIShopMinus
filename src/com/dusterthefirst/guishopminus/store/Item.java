package com.dusterthefirst.guishopminus.store;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public class Item {
	public Material icon;
	public String name;
	public String description;
	public double price;

	public Item(Material icon, String name, String description, double price) {
		this.icon = icon;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.description = ChatColor.translateAlternateColorCodes('&', description);
		this.price = price;
	}
}