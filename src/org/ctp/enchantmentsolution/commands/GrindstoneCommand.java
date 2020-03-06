package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Grindstone;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class GrindstoneCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.grindstone")) {
				InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
				if (inv == null) {
					inv = new Grindstone(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
				} else if (!(inv instanceof Grindstone)) {
					inv.close(true);
					inv = new Grindstone(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
				}
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		return true;
	}

}
