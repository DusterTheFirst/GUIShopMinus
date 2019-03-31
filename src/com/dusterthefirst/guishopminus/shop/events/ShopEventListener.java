package com.dusterthefirst.guishopminus.shop.events;

import com.dusterthefirst.guishopminus.GuiShopMinus;
import com.dusterthefirst.guishopminus.shop.Item;
import com.dusterthefirst.guishopminus.shop.Shop;
import com.dusterthefirst.guishopminus.shop.Submenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.itemnbtapi.NBTItem;

public class ShopEventListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getInventory() == null || event.getCurrentItem() == null || player == null) return;
		
		
		if (event.getInventory().getMaxStackSize() == Shop.SWEETSPOT) {
			event.setCancelled(true);
			System.out.println("F");
		} else {
			return;
		}
		
		NBTItem item = new NBTItem(event.getCurrentItem());
		
		if (item.hasKey("STORE-SUBMENU")) {
			System.out.println(item.getInteger("STORE-SUBMENU"));
			
			player.openInventory(GuiShopMinus.shop.submenus.get(item.getInteger("STORE-SUBMENU")).asInventory(player, item.getInteger("STORE-SUBMENU")));
		} if (item.hasKey("STORE-SUBMENU-CLOSE")) {
			player.openInventory(GuiShopMinus.shop.asInventory(player));
		} if (item.hasKey("STORE-CLOSE")) {
			player.closeInventory();
		} if (item.hasKey("STORE-ITEM")) {
			Submenu sub = GuiShopMinus.shop.submenus.get(item.getInteger("STORE-ITEM-SHOP"));
			Item storeitem = sub.items.get(item.getInteger("STORE-ITEM"));

			storeitem.buy(event.isShiftClick(), player);
		} else {
			ItemStack clicked = event.getCurrentItem();
			for (Submenu sub : GuiShopMinus.shop.submenus) {
				for (Item storeitem : sub.items) {
					if (storeitem.material == clicked.getType()) {
						storeitem.sell(clicked, player, event.getSlot());
						return;
					}
				}
			}
		}
	}
}
