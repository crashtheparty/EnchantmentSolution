package org.ctp.enchantmentsolution.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import java.util.HashMap;

public class RemoveEnchant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.enchantremove")) {
				if (args.length > 0) {
					String enchantmentName = args[0];
					for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
						if (enchant.getName().equalsIgnoreCase(enchantmentName)) {
							ItemStack itemToEnchant = player.getInventory().getItemInMainHand();
							if (itemToEnchant != null) {
								boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
								if (itemToEnchant.getType() == Material.BOOK && useBooks) itemToEnchant = ItemUtils.convertToEnchantedBook(itemToEnchant);
								else if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !useBooks) itemToEnchant = ItemUtils.convertToRegularBook(itemToEnchant);
								itemToEnchant = ItemUtils.removeEnchantmentFromItem(itemToEnchant, enchant);
								if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !((EnchantmentStorageMeta) itemToEnchant.getItemMeta()).hasStoredEnchants()) itemToEnchant.setType(Material.BOOK);
								player.getInventory().setItemInMainHand(itemToEnchant);
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%enchant%", enchant.getDisplayName());
								ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-removed"));
							} else
								ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-remove-from-item"));
							return true;
						}
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", enchantmentName);
					ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
				} else
					ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-not-specified"));
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		return true;
	}

}
