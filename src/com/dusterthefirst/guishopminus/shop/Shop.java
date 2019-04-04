package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

public class Shop {
	/**
	 * The max stack size set on the inventory for tracking if this inv is the one
	 * open
	 */
	public static final int SWEETSPOT = 69;
	/** The UUID of the shop */
//	public final UUID uuid;
	/** The name of the shop */
	public String name;
	/** The submenus in the shop */
	public ArrayList<Submenu> submenus;

	/** Generate an inventory representation of this store */
	public Inventory asInventory(Player owner) {
		int inventoryX = 9;
		int inventoryY = ((this.submenus.size() + 1) / inventoryX) + 1;
		int inventorySize = inventoryX * inventoryY;

		Inventory inventory = Bukkit.createInventory(owner, inventorySize, ChatColor.translateAlternateColorCodes('&', this.name));
		
		for (int i = 0; i < this.submenus.size(); i++) {
			Submenu sub = this.submenus.get(i);
			inventory.addItem(sub.toItem(i));
		}

		inventory.setItem(inventory.getSize() - 1, CloseItem.closeShopItem());

		inventory.setMaxStackSize(Shop.SWEETSPOT);

		return inventory;
	}
}