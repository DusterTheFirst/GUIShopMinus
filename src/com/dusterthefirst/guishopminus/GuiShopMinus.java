package com.dusterthefirst.guishopminus;

import java.util.logging.Logger;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import com.dusterthefirst.guishopminus.shop.Shop;
import com.dusterthefirst.guishopminus.shop.commands.ShopCommand;
import com.dusterthefirst.guishopminus.shop.events.ShopEventListener;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

@Plugin(name = "GuiShopMinus", version = "0.1")
@Description("A FOSS Alternitive to GuiShopPlus")
@LoadOrder(PluginLoadOrder.STARTUP)
@Author("DusterTheFirst")
@Website("dusterthefirst.com")
@LogPrefix("GSM")
@Dependency("Vault")
@org.bukkit.plugin.java.annotation.permission.Permission(name = "test.*", desc = "Wildcard permission", defaultValue = PermissionDefault.OP, children = {
		@ChildPermission(name = "test.foo") })
@ApiVersion(ApiVersion.Target.v1_13)
public class GuiShopMinus extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	private static Economy econ = null;
	private static Permission perms = null;
	public static Shop store;

	// Fired when plugin is first enabled
	@Override
	public void onEnable() {
//    	getServer().getPluginManager().registerEvents(new TestCommands(), this);

		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Economy dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupPermissions();
		
		getCommand("shop").setExecutor(new ShopCommand());
		
		getServer().getPluginManager().registerEvents(new ShopEventListener(), this);
		
		GuiShopMinus.store = new Shop("&aCool shop");
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

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
	
	public static Economy getEconomy() {
        return econ;
    }
    
    public static Permission getPermissions() {
        return perms;
    }
}
