package com.dusterthefirst.guishopminus.shop.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.dusterthefirst.guishopminus.GuiShopMinus;
import com.dusterthefirst.guishopminus.shop.Shop;

import de.tr7zw.itemnbtapi.NBTItem;

public class ShopEventListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() == null || event.getCurrentItem() == null) return;
		
		
		if (event.getInventory().getMaxStackSize() == Shop.SWEETSPOT) {
			event.setCancelled(true);
			System.out.println("F");
		}
		
		NBTItem item = new NBTItem(event.getCurrentItem());
		
		if (item.hasKey("STORE-SUBMENU")) {
			System.out.println(item.getInteger("STORE-SUBMENU"));
			
			Player player = (Player) event.getWhoClicked();
			
			player.openInventory(GuiShopMinus.shop.submenus.get(item.getInteger("STORE-SUBMENU")).asInventory(player));
		} 
	}
}
