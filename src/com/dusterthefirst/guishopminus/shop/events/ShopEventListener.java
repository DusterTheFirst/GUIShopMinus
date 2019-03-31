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
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class ShopEventListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getInventory() == null || event.getCurrentItem() == null || player == null) return;
		
		
		if (event.getInventory().getMaxStackSize() == Shop.SWEETSPOT) {
			event.setCancelled(true);
			System.out.println("F");
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
			int amount = event.isShiftClick() ? item.getItem().getMaxStackSize() : 1;

			Submenu sub = GuiShopMinus.shop.submenus.get(item.getInteger("STORE-ITEM-SHOP"));
			Item storeitem = sub.items.get(item.getInteger("STORE-ITEM"));

			double price = storeitem.price * amount;

			Economy econ = GuiShopMinus.getEconomy();

            EconomyResponse r = econ.withdrawPlayer(player, price);
            if(r.transactionSuccess()) {
                player.sendMessage(String.format("You were charged %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                player.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }

			player.getInventory().addItem(new ItemStack(item.getItem().getType(), amount));
		}
	}
}
