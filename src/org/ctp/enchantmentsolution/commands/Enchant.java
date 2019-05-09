package org.ctp.enchantmentsolution.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

import java.util.HashMap;

public class Enchant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("enchantmentsolution.command.enchant")) {
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
							if(level > enchant.getMaxLevel()) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%level%", level);
								codes.put("%maxLevel%", enchant.getMaxLevel());
								codes.put("%enchant%", enchant.getDisplayName());
								ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.level-too-high"));
								level = enchant.getMaxLevel();
							}
							ItemStack itemToEnchant = player.getInventory().getItemInMainHand();
							if(itemToEnchant != null){
								int maxEnchants = ConfigUtils.getMaxEnchantments();
								int itemEnchants = Enchantments.getTotalEnchantments(itemToEnchant);
								if(maxEnchants <= 0 || (itemEnchants < maxEnchants && maxEnchants > 0)) {
									if(enchant.canAnvilItem(itemToEnchant.getType()) || enchant.canEnchantItem(itemToEnchant.getType())){
										if(itemToEnchant.getType() == Material.BOOK && ConfigUtils.getEnchantedBook()) {
											itemToEnchant = Enchantments.convertToEnchantedBook(itemToEnchant);
										} else if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !ConfigUtils.getEnchantedBook()) {
											itemToEnchant = Enchantments.convertToRegularBook(itemToEnchant);
										}
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
								} else {
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%maxEnchants%", maxEnchants);
									ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.too-many-enchants"));
								}
							}else{
								ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-fail"));
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
