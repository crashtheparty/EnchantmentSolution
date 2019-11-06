package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Configurations;

public class Debug implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.debug")) {
				Configurations.generateDebug();
				player.sendMessage(ChatUtils.getMessage(ChatUtils.getCodes(), "commands.debug"));
			} else {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			}
		} else if (sender.isOp()) {
			Configurations.generateDebug();
			ChatUtils.sendInfo(ChatUtils.getMessage(ChatUtils.getCodes(), "commands.debug"));
		}
		return true;
	}

}
