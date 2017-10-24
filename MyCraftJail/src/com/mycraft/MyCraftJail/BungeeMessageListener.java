package com.mycraft.MyCraftJail;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class BungeeMessageListener implements PluginMessageListener{
	private ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();
	public Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
//		this.logger.info("[" + pdfFile.getName() + "] onPluginMessageReceived "+channel);
		if (!channel.equals("MyCraftJail")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		Bukkit.dispatchCommand(consoleSender, subchannel);
//		this.logger.info("[" + pdfFile.getName() + "] onPluginMessageReceived "+subchannel);
	}

}
