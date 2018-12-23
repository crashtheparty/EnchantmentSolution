package org.ctp.enchantmentsolution.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;

import java.util.HashMap;

public class UnsafeEnchant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("enchantmentsolution.command.enchantunsafe")) {
				if(args.length > 0){
					String enchantmentName = args[0];
					for(CustomEnchantment enchant : Enchantments.getEnchantments()){
						if(enchant.getName().equalsIgnoreCase(enchantmentName)){
							if(!enchant.isEnabled()) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-disabled"));
								return true;
							}
							int level = 1;
							if(args.length > 1){
								try{
									level = Integer.parseInt(args[1]);
									if (level < 1) {
										HashMap<String, Object> codes = ChatUtils.getCodes();
										codes.put("%level%", level);
										ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.level-too-low"));
										level = 1;
									}
								}catch(NumberFormatException ex){
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%level%", args[1]);
									ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.invalid-level"));
								}
							}
							ItemStack itemToEnchant = player.getInventory().getItemInMainHand();
							if(itemToEnchant != null){
								itemToEnchant = Enchantments.addEnchantmentToItem(itemToEnchant, enchant, level);
								player.getInventory().setItemInMainHand(itemToEnchant);
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%level%", level);
								codes.put("%enchant%", enchant.getDisplayName());
								ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.add-enchant"));
							}else{
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%enchant%", enchant.getDisplayName());
								ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.cannot-enchant-item"));
							}
							return true;
						}
					}
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", enchantmentName);
					ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
				}else{
					ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-not-specified"));
				}
			}else {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			}
		}
		return true;
	}
}
