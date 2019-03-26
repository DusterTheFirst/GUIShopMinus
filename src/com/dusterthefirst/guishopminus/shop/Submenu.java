package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.dusterthefirst.guishopminus.GuiShopMinus;
import com.dusterthefirst.guishopminus.shop.Item;

import net.md_5.bungee.api.ChatColor;
import de.tr7zw.itemnbtapi.NBTItem;

public class Submenu {
	public Material icon;
	public String name;
	public String description;

	private ArrayList<Item> items;

	public Submenu(Material icon, String name, String description, ArrayList<Item> items) {
		this.icon = icon;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.description = ChatColor.translateAlternateColorCodes('&', description);
		this.items = items;
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
		lore.add(this.description);
		meta.setLore(lore);

		// Save the metadata
		stack.setItemMeta(meta);

		// Set custom NBT tags
		NBTItem nbti = new NBTItem(stack);
		nbti.setInteger("STORE-SUBMENU", index);

		return nbti.getItem();
	}
	
	/** Generate an inventory representation of this submenu */
	public Inventory asInventory(/*Shop parent, */Player owner) {
		int inventoryX = 9;
		int inventoryY = (this.items.size() / inventoryX) + 1;
		int inventorySize = inventoryX * inventoryY;
		
		System.out.printf("X:%#x Y:%#x Size:%#x", inventoryX, inventoryY, inventorySize);

		Inventory inventory = Bukkit.createInventory(owner, inventorySize, GuiShopMinus.shop.name + ChatColor.DARK_GRAY + " > " + ChatColor.RESET + this.name);
		
		for (int i = 0; i < this.items.size(); i++) {
			Item item = this.items.get(i);
			inventory.addItem(item.toItem(i));
		}

		inventory.setMaxStackSize(Shop.SWEETSPOT);

		return inventory;
	}
}