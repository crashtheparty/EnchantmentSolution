package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;

import org.bukkit.ChatColor;

public class RemoveEnchant  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("enchantmentsolution.command.enchantremove")) {
				if(args.length > 0){
					String enchantmentName = args[0];
					for(CustomEnchantment enchant : Enchantments.getEnchantments()){
						if(enchant.getName().equalsIgnoreCase(enchantmentName)){
							ItemStack itemToEnchant = player.getInventory().getItemInMainHand();
							if(itemToEnchant != null){
								itemToEnchant = Enchantments.removeEnchantmentFromItem(itemToEnchant, enchant);
								player.getInventory().setItemInMainHand(itemToEnchant);
								ChatUtils.sendMessage(player, "Enchantment with name " + enchant.getDisplayName() + " has been removed from the item.");
							}else{
								ChatUtils.sendMessage(player, "You must remove an enchant from an item.");
							}
							return true;
						}
					}
					ChatUtils.sendMessage(player, "Enchantment with name " + enchantmentName + " not found.");
				}else{
					ChatUtils.sendMessage(player, "You must specify an enchantment.");
				}
			}else {
				ChatUtils.sendMessage(player, ChatColor.RED + "You do not have permission to use this command!");
			}
		}
		return true;
	}

}
