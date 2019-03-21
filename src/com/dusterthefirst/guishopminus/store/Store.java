package com.dusterthefirst.guishopminus.store;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class Store {
	public final UUID uuid;
	public final String name;
	public ArrayList<Submenu> submenus;

	public Store(String name) {
		this.uuid = UUID.randomUUID();
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.submenus = new ArrayList<Submenu>();
		this.submenus.add(new Submenu(this, Material.BIRCH_FENCE, "Bicth", "Lasagna", new ArrayList<Item>() {
			{
				add(new Item(Material.STICK, "dill", "dough", 69.420));
			}
		}));
		this.submenus.add(new Submenu(this, Material.ACACIA_DOOR, "FBI", "Open Up", new ArrayList<Item>() {
			{
				add(new Item(Material.GOLDEN_SWORD, "dull", "ee", 45.0));
			}
		}));
	}

	public Inventory asInventory(Player owner) {
		Inventory inventory = Bukkit.createInventory(owner, InventoryType.CHEST, this.name);

		for (Submenu sub : this.submenus) {
			// Get the stack
			ItemStack stack = new ItemStack(sub.icon);
			// Get the metadata
			ItemMeta meta = stack.getItemMeta();

			// Set the display name
			meta.setDisplayName(sub.name);

			// Add the description as lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(sub.description);
			meta.setLore(lore);

			// Save the metadata
			stack.setItemMeta(meta);

			// Set custom NBT tags
			NBTItem nbti = new NBTItem(stack);
			nbti.setBoolean("STOREITEM", true);
			
			inventory.addItem(nbti.getItem());
		}

		return inventory;
	}
}