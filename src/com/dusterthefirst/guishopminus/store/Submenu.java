package com.dusterthefirst.guishopminus.store;

import java.util.ArrayList;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public class Submenu {
	public Material icon;
	public String name;
	public String description;
	public final Store parent;

	private ArrayList<Item> items;

	public Submenu(Store parent, Material icon, String name, String description, ArrayList<Item> items) {
		this.icon = icon;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.description = ChatColor.translateAlternateColorCodes('&', description);
		this.items = items;
		this.parent = parent;
	}
}