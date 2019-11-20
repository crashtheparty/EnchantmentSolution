package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.EnchantabilityCalc;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class EnchantabilityCalculator implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.calculator")) {
				EnchantabilityCalc inv = new EnchantabilityCalc(player);
				EnchantmentSolution.getPlugin().addInventory(inv);
				inv.setInventory();
			} else {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			}
		}
		return true;
	}
}
