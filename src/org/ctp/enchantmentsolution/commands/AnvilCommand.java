package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class AnvilCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.anvil")) {
				InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
				if (inv == null) {
					inv = new Anvil(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
				} else if (!(inv instanceof Anvil)) {
					inv.close(true);
					inv = new Anvil(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
				}
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		return true;
	}

}
