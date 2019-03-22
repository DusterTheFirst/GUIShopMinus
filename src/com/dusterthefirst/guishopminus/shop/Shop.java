package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
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
	public final String name;
	/** The submenus in the shop */
	public ArrayList<Submenu> submenus;

	public Shop(String name) {
//		this.uuid = UUID.randomUUID();
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.submenus = new ArrayList<Submenu>();
		this.submenus.add(new Submenu(Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
		this.submenus.add(new Submenu(Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
		this.submenus.add(new Submenu(Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
		this.submenus.add(new Submenu(Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
		this.submenus.add(new Submenu(Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
	}

	/** Generate an inventory representation of this store */
	public Inventory asInventory(Player owner) {
		int inventoryX = 9;
		int inventoryY = (this.submenus.size() / inventoryX) + 1;
		int inventorySize = inventoryX * inventoryY;

//		int padding = this.submenus.size();
		
		// TODO: INVENTORY GENERATION (Padding + 
		
		System.out.printf("X:%#x Y:%#x Size:%#x", inventoryX, inventoryY, inventorySize);

		Inventory inventory = Bukkit.createInventory(owner, inventorySize, this.name);
		
//		for (int i = 0; i < this.submenus.size(); i++) {
//			Submenu sub = this.submenus.get(i);
//			// Get the stack
//			ItemStack stack = new ItemStack(sub.icon);
//			// Get the metadata
//			ItemMeta meta = stack.getItemMeta();
//
//			// Set the display name
//			meta.setDisplayName(sub.name);
//
//			// Add the description as lore
//			ArrayList<String> lore = new ArrayList<String>();
//			lore.add(sub.description);
//			meta.setLore(lore);
//
//			// Save the metadata
//			stack.setItemMeta(meta);
//
//			// Set custom NBT tags
//			NBTItem nbti = new NBTItem(stack);
//			nbti.setInteger("STORE-SUBMENU", i);
//
//			inventory.addItem(nbti.getItem());
//		}

		inventory.setMaxStackSize(Shop.SWEETSPOT);

		return inventory;
	}
}