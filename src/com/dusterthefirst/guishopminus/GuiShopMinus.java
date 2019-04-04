package com.dusterthefirst.guishopminus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import com.dusterthefirst.guishopminus.shop.Item;
import com.dusterthefirst.guishopminus.shop.Shop;
import com.dusterthefirst.guishopminus.shop.Submenu;
import com.dusterthefirst.guishopminus.shop.commands.ShopCommand;
import com.dusterthefirst.guishopminus.shop.events.ShopEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;

import net.milkbowl.vault.economy.Economy;

public class GuiShopMinus extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	private static Economy econ = null;
	public static Shop shop;
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

	// Fired when plugin is first enabled
	@Override
	public void onEnable() {
		if (!setupEconomy()) {
			log.severe(
					String.format("[%s] - Disabled due to no Economy dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		getCommand("shop").setExecutor(new ShopCommand());

		getServer().getPluginManager().registerEvents(new ShopEventListener(), this);

		File configfile = getShopConfig();

		if (!configfile.exists()) {
			log.severe("[GSM] No shop configuration provided, disabling");
			log.severe("[GSM] Visit https://whsmc.github.io/GUIShopMinus for instructions and to generate your shop");

			this.getPluginLoader().disablePlugin(this);
			return;
		}

		try {
			GuiShopMinus.shop = loadShop(IOUtils.toString(new FileInputStream(configfile), "utf-8"));
		} catch (Exception e) {
			log.severe(e.getMessage());

			this.getPluginLoader().disablePlugin(this);
			return;
		}
	}

	public Shop loadShop(String json) {
		return GSON.fromJson(json, Shop.class);
	}

	public File getShopConfig() {
		if (getDataFolder().exists() == false) {
			getDataFolder().mkdirs();
		}
		try {
			exportResource("LICENSE");
			exportResource("README.md");
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return new File(getDataFolder(), "shop.json");
	}

	public void exportResource(String name) throws IOException {
		File out = new File(getDataFolder(), name);
		if (out.exists())
			return;

		out.createNewFile();

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream in = classLoader.getResourceAsStream(name);
		OutputStream outs = new FileOutputStream(out);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) > 0) {
			outs.write(buffer, 0, length);
		}

		in.close();
		outs.close();
	}

	// Fired when plugin is disabled
	@Override
	public void onDisable() {
		log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			log.severe("A");
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			log.severe("B");
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Economy getEconomy() {
		return econ;
	}
}
