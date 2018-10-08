package org.ctp.enchantmentsolution.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;

public class PlayerChatTabComplete implements TabCompleter{
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args) {
		List<String> autoComplete = new ArrayList<String>();
		if(args.length == 1) {
			for(CustomEnchantment enchantment : DefaultEnchantments.getEnchantments()) {
				String argument = args[0];
				if(argument.trim().equals("") || enchantment.getName().startsWith(argument)) {
					autoComplete.add(enchantment.getName());
				}
			}
		}
		return autoComplete;
	}

}
