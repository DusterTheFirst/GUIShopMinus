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
	public final String name;
	/** The submenus in the shop */
	public ArrayList<Submenu> submenus;

	public Shop(String name) {
//		this.uuid = UUID.randomUUID();
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.submenus = new ArrayList<Submenu>();
		
		ArrayList<Item> submenuchildren = new ArrayList<Item>(Arrays.asList(new Item[] {
			new Item(Material.STICK, "&6Test Item", 10, 69.420, 50),
			new Item(Material.BLUE_SHULKER_BOX, "&6Test Item 2", 1, 150, 75)
		}));
		
		this.submenus.add(new Submenu(Material.RED_BANNER, 			"&cRed"    , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.ORANGE_WOOL, 		"&6Orange" , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.YELLOW_BED, 			"&eYellow" , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.GRASS, 				"&aGreen"  , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.BLUE_STAINED_GLASS, 	"&9Blue"   , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.PURPLE_SHULKER_BOX, 	"&5Purple" , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.PINK_DYE, 			"&dPink"   , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.COAL_BLOCK, 			"&0Black"  , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.BONE_MEAL, 			"&fWhite"  , "&8&lOptional &rDescription", submenuchildren));
		this.submenus.add(new Submenu(Material.LAPIS_LAZULI, 		"&cg&6a&ay", "&8&lOptional &rDescription", submenuchildren));
	}

	/** Generate an inventory representation of this store */
	public Inventory asInventory(Player owner) {
		int inventoryX = 9;
		int inventoryY = ((this.submenus.size() + 1) / inventoryX) + 1;
		int inventorySize = inventoryX * inventoryY;

//		int padding = this.submenus.size();
		
		System.out.printf("X:%#x Y:%#x Size:%#x", inventoryX, inventoryY, inventorySize);

		Inventory inventory = Bukkit.createInventory(owner, inventorySize, this.name);
		
		for (int i = 0; i < this.submenus.size(); i++) {
			Submenu sub = this.submenus.get(i);
			inventory.addItem(sub.toItem(i));
		}

		inventory.setItem(inventory.getSize() - 1, CloseItem.closeShopItem());

		inventory.setMaxStackSize(Shop.SWEETSPOT);

		return inventory;
	}
}