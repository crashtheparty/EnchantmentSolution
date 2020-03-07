package org.ctp.enchantmentsolution.commands;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
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

public class RemoveEnchant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		Player removePlayer = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			removePlayer = player;
		}
		if (sender.hasPermission("enchantmentsolution.command.enchantremove")) {
			if (args.length > 0) {
				String enchantmentName = args[0];
				for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
					if (enchant.getName().equalsIgnoreCase(enchantmentName)) {
						if (args.length > 1) {
							String arg = args[1];
							if (!arg.equals("@p")) {
								removePlayer = Bukkit.getPlayer(arg);
								if (removePlayer == null) {
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%invalid_player%", args[1]);
									ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
									return false;
								} else if (removePlayer != null && !removePlayer.equals(sender) && !sender.hasPermission("enchantmentsolution.command.enchantremove.others")) {
									ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission-other"), Level.WARNING);
									return false;
								}
							}
						}
						if (removePlayer == null) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%player%", ChatUtils.getMessage(codes, "commands.invalid.console"));
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
							return true;
						}
						int slot = removePlayer.getInventory().getHeldItemSlot();
						if (args.length > 2) try {
							slot = Integer.parseInt(args[2]);
							if (slot < 0) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%slot%", slot);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.slot-too-low"), Level.WARNING);
								slot = removePlayer.getInventory().getHeldItemSlot();
							} else if (slot >= 45) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%slot%", slot);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.slot-too-high"), Level.WARNING);
								slot = removePlayer.getInventory().getHeldItemSlot();
							}
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%slot%", args[2]);
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-slot"), Level.WARNING);
							slot = removePlayer.getInventory().getHeldItemSlot();
						}
						ItemStack itemToEnchant = removePlayer.getInventory().getItem(slot);
						if (itemToEnchant != null) {
							boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
							if (itemToEnchant.getType() == Material.BOOK && useBooks) itemToEnchant = ItemUtils.convertToEnchantedBook(itemToEnchant);
							else if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !useBooks) itemToEnchant = ItemUtils.convertToRegularBook(itemToEnchant);
							itemToEnchant = ItemUtils.removeEnchantmentFromItem(itemToEnchant, enchant);
							if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !((EnchantmentStorageMeta) itemToEnchant.getItemMeta()).hasStoredEnchants()) itemToEnchant.setType(Material.BOOK);

							removePlayer.getInventory().setItem(slot, itemToEnchant);
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%enchant%", enchant.getDisplayName());
							codes.put("%player%", sender.getName());
							if (player != null) codes.put("%player%", player.getDisplayName());
							else
								codes.put("%player%", ChatUtils.getMessage(codes, "commands.console-player"));
							codes.put("%slot%", slot);
							codes.put("%remove_player%", removePlayer.getName());
							if (removePlayer.equals(player)) ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-removed"), Level.INFO);
							else {
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-removed-other"), Level.INFO);
								ChatUtils.sendMessage(sender, removePlayer, ChatUtils.getMessage(codes, "commands.other-removed-enchant"), Level.INFO);
							}
						} else
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-remove-from-item"), Level.WARNING);
						return true;
					}
				HashMap<String, Object> codes = ChatUtils.getCodes();
				codes.put("%enchant%", enchantmentName);
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-not-found"), Level.WARNING);
			} else
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-not-specified"), Level.WARNING);
		} else
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

}
