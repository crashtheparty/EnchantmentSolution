package org.ctp.enchantmentsolution.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Reset implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
			if(player.hasPermission("enchantmentsolution.command.reset")) {
				EnchantmentSolution.getPlugin().resetInventories();
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.reset-inventory"));
			} else {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			}
		} else {
			EnchantmentSolution.getPlugin().resetInventories();
			ChatUtils.sendToConsole(Level.INFO, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.reset-inventory"));
		}
		return true;
	}
	
}
