package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import com.dusterthefirst.guishopminus.GuiShopMinus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;

public class Submenu {
	public Material icon;
	public String name;
	public String description;

	public ArrayList<Item> items;

	public ItemStack toItem(int index) {
		// Get the stack
		ItemStack stack = new ItemStack(this.icon);
		// Get the metadata
		ItemMeta meta = stack.getItemMeta();

		// Set the display name
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));

		// Add the description as lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', this.description));
		meta.setLore(lore);

		// Save the metadata
		stack.setItemMeta(meta);

		// Set custom NBT tags
		NBTItem nbti = new NBTItem(stack);
		nbti.setInteger("STORE-SUBMENU", index);

		return nbti.getItem();
	}

	/** Generate an inventory representation of this submenu */
	public Inventory asInventory(/* Shop parent, */Player owner, int index) {
		int inventoryX = 9;
		int inventoryY = ((this.items.size() + 1) / inventoryX) + 1;
		int inventorySize = inventoryX * inventoryY;

		Inventory inventory = Bukkit.createInventory(owner, inventorySize,
				ChatColor.translateAlternateColorCodes('&', GuiShopMinus.shop.name) + ChatColor.DARK_GRAY + " > "
						+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', this.name));

		for (int i = 0; i < this.items.size(); i++) {
			Item item = this.items.get(i);
			inventory.addItem(item.toItem(i, index));
		}

		inventory.setItem(inventory.getSize() - 1, CloseItem.closeSubmenuItem());

		inventory.setMaxStackSize(Shop.SWEETSPOT);

		return inventory;
	}
}