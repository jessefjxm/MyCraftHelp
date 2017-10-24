package com.mycraft.MyCraftJail;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class MyCraftJail extends JavaPlugin implements Listener {
	private ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();
	public Logger logger = Logger.getLogger("Minecraft");
	private PluginDescriptionFile pdfFile = getDescription();

	public static Permission permission = null;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		if (!setupPermissions()) {
			this.logger.info("[" + pdfFile.getName() + "] Disabled due to no Vault dependency found!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "MyCraftJail", new BungeeMessageListener());
		this.logger.info("[" + pdfFile.getName() + "] has been enabled.");
		super.onEnable();
	}

	public void onDisable() {
		this.logger.info("[" + pdfFile.getName() + "] has been disabled.");
		super.onDisable();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
//		String group = permission.getPrimaryGroup(player);
		Bukkit.dispatchCommand(consoleSender, "spawn " + player.getName());
	}
}