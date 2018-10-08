package org.ctp.enchantmentsolution.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

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
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", args[0]);
					ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
				} else {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", args[0]);
					ChatUtils.sendToConsole(Level.WARNING, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
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
