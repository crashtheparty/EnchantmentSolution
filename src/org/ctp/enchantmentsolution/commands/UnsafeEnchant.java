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
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.MiscUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class UnsafeEnchant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		Player givePlayer = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			givePlayer = player;
		}

		if (sender.hasPermission("enchantmentsolution.command.enchant")) {
			if (args.length > 0) {
				String enchantmentName = args[0];
				for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
					if (enchant.getName().equalsIgnoreCase(enchantmentName)) {
						if (!enchant.isEnabled()) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-disabled"), Level.WARNING);
							return true;
						}
						int level = 1;
						if (args.length > 1) try {
							level = Integer.parseInt(args[1]);
							if (level < 1) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%level%", level);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.level-too-low"), Level.WARNING);
								level = 1;
							}
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", args[1]);
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-level"), Level.WARNING);
						}
						if (args.length > 2) {
							String arg = args[2];
							if (!arg.equals("@p")) {
								givePlayer = Bukkit.getPlayer(arg);
								if (givePlayer == null) {
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%invalid_player%", args[2]);
									ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
									return false;
								} else if (givePlayer != null && !givePlayer.equals(sender) && !sender.hasPermission("enchantmentsolution.command.enchantunsafe.others")) {
									ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission-other"), Level.WARNING);
									return false;
								}
							}
						}
						if (givePlayer == null) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%player%", ChatUtils.getMessage(codes, "commands.invalid.console"));
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
							return true;
						}
						int slot = givePlayer.getInventory().getHeldItemSlot();
						if (args.length > 3) try {
							slot = Integer.parseInt(args[3]);
							if (slot < 0) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%slot%", slot);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.slot-too-low"), Level.WARNING);
								slot = givePlayer.getInventory().getHeldItemSlot();
							} else if (slot >= 45) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%slot%", slot);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.slot-too-high"), Level.WARNING);
								slot = givePlayer.getInventory().getHeldItemSlot();
							}
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%slot%", args[3]);
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-slot"), Level.WARNING);
							slot = givePlayer.getInventory().getHeldItemSlot();
						}
						ItemStack itemToEnchant = givePlayer.getInventory().getItem(slot);
						if (itemToEnchant != null) {
							boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
							if (itemToEnchant.getType() == Material.BOOK && useBooks) itemToEnchant = ItemUtils.convertToEnchantedBook(itemToEnchant);
							else if (itemToEnchant.getType() == Material.ENCHANTED_BOOK && !useBooks) itemToEnchant = ItemUtils.convertToRegularBook(itemToEnchant);
							itemToEnchant = ItemUtils.addEnchantmentToItem(itemToEnchant, enchant, level);
							givePlayer.getInventory().setItem(slot, itemToEnchant);
							MiscUtils.updateAbilities(givePlayer);
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", level);
							codes.put("%slot%", slot);
							codes.put("%enchant%", enchant.getDisplayName());
							if (player != null) codes.put("%player%", player.getDisplayName());
							else
								codes.put("%player%", ChatUtils.getMessage(codes, "commands.console-player"));
							codes.put("%give_player%", givePlayer.getName());
							if (givePlayer.equals(player)) ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.add-enchant"), Level.INFO);
							else {
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.add-enchant-other"), Level.INFO);
								ChatUtils.sendMessage(sender, givePlayer, ChatUtils.getMessage(codes, "commands.other-added-enchant"), Level.INFO);
							}
						} else {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%enchant%", enchant.getDisplayName());
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.cannot-enchant-item"), Level.WARNING);
						}
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
