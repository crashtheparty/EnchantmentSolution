package org.ctp.enchantmentsolution.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class EnchantInfo implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		if(args.length == 0) {
			List<String> enchantList = new ArrayList<String>();
			for(CustomEnchantment enchant : Enchantments.getEnchantments()) {
				enchantList.add(enchant.getName());
			}
			String message = "Enchants: " + StringUtils.join(enchantList.toArray(), ',');
			if(player != null) {
				ChatUtils.sendMessage(player, message);
			} else {
				sender.sendMessage(message.replaceAll(",", ", "));
			}
		}else if(args.length == 1) {
			CustomEnchantment enchantment = null;
			for(CustomEnchantment enchant : Enchantments.getEnchantments()) {
				if(enchant.getName().equalsIgnoreCase(args[0])) {
					enchantment = enchant;
					break;
				}
			}
			if(enchantment == null) {
				if(player != null) {
					ChatUtils.sendMessage(player, "Enchantment with name " + args[0] + " does not exist!");
				} else {
					sender.sendMessage("Enchantment with name " + args[0] + " does not exist!");
				}
			} else {
				String[] pages = enchantment.getPage();
				if(player != null) {
					ChatUtils.sendMessage(player, pages);
				} else {
					sender.sendMessage(pages);
				}
			}
		}
		return true;
	}

}
