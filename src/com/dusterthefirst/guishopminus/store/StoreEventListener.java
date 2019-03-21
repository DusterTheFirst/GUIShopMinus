package com.dusterthefirst.guishopminus.store;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import de.tr7zw.itemnbtapi.NBTItem;

public class StoreEventListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (new NBTItem(event.getCurrentItem()).hasKey("STOREITEM")) {
			event.setCancelled(true);
			System.out.println("EEEE");
		}
	}
	
	@EventHandler // FIXME: BETTER DETECTION
	public void onInventoryMove(InventoryMoveItemEvent event) {
//		if (new NBTItem(event.getDestination().getContents()[0]).hasKey("STOREITEM")) {
//			event.setCancelled(true);
//			System.out.println("BBBB");
//		}
		// TODO:
	}
	
}
