package com.dusterthefirst.guishopminus.shop;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;

public class CloseItem {
    static ItemStack closeSubmenuItem() {
        // Get the stack
		ItemStack stack = new ItemStack(Material.BARRIER);
		// Get the metadata
		ItemMeta meta = stack.getItemMeta();

		// Set the display name
		meta.setDisplayName(ChatColor.RED + "Close");

		// Add the description as lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "Return back to the previous menu");
		meta.setLore(lore);

		// Save the metadata
		stack.setItemMeta(meta);

		// Set custom NBT tags
		NBTItem nbti = new NBTItem(stack);
        nbti.setBoolean("STORE-SUBMENU-CLOSE", true);
        // TODO: Set parent
        
        return nbti.getItem();
    }

    static ItemStack closeShopItem() {
        // Get the stack
		ItemStack stack = new ItemStack(Material.BARRIER);
		// Get the metadata
		ItemMeta meta = stack.getItemMeta();

		// Set the display name
		meta.setDisplayName(ChatColor.RED + "Close");

		// Add the description as lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "Close the current shop");
		meta.setLore(lore);

		// Save the metadata
		stack.setItemMeta(meta);

		// Set custom NBT tags
		NBTItem nbti = new NBTItem(stack);
        nbti.setBoolean("STORE-CLOSE", true);
        
        return nbti.getItem();
    }
}