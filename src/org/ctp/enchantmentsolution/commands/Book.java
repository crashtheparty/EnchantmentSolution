package org.ctp.enchantmentsolution.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class Book implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (!sender.hasPermission("enchantmentsolution.command.book")) {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
				return true;
			}
		}
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		Player givePlayer = player;

		if (args.length > 0) {
			String arg = args[0];
			if (!arg.equals("@p")) {
				givePlayer = Bukkit.getPlayer(arg);
				if (givePlayer == null) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%player%", arg);
					ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
					return true;
				} else if (givePlayer != null && !givePlayer.equals(player) && !sender.hasPermission("enchantmentsolution.command.book.others")) {
					ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission-other"), Level.WARNING);
					return true;
				}
			} else if (player == null) {
				HashMap<String, Object> codes = ChatUtils.getCodes();
				codes.put("%player%", ChatUtils.getMessage(codes, "commands.invalid.console"));
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
				return true;
			}
		} else {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%player%", ChatUtils.getMessage(codes, "commands.invalid.null-player"));
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-player"), Level.WARNING);
			return true;
		}

		if (args.length > 1) {
			String arg = args[1];
			if (arg.equals("RandomEnchant") || arg.equals("RandomMultiEnchant")) {
				boolean treasure = false;
				if (args.length > 1) treasure = Boolean.valueOf(args[1]);
				levels = GenerateUtils.generateBookLoot(givePlayer, new ItemStack(Material.BOOK), treasure);
				if (arg.equals("RandomEnchant")) for(int i = levels.size() - 1; i > 0; i--)
					levels.remove(i);
			} else
				for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
					if (enchant.getName().equalsIgnoreCase(args[1])) {
						if (!enchant.isEnabled()) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-disabled"), Level.WARNING);
							return true;
						}
						int level = 1;
						if (args.length > 2) try {
							level = Integer.parseInt(args[2]);
							if (level < 1) {
								HashMap<String, Object> codes = ChatUtils.getCodes();
								codes.put("%level%", level);
								ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.level-too-low"), Level.WARNING);
								level = 1;
							}
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", args[2]);
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-level"), Level.WARNING);
						}
						if (level > enchant.getMaxLevel()) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", level);
							codes.put("%maxLevel%", enchant.getMaxLevel());
							codes.put("%enchant%", enchant.getDisplayName());
							ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.level-too-high"), Level.WARNING);
							level = enchant.getMaxLevel();
						}
						levels.add(new EnchantmentLevel(enchant, level));
						break;
					}
			if (levels.size() == 0) {
				HashMap<String, Object> codes = ChatUtils.getCodes();
				codes.put("%enchant%", arg);
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.enchant-not-found"), Level.WARNING);
				return true;
			}
		} else {
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.enchant-not-specified"), Level.WARNING);
			return true;
		}

		int amount = 1;
		if (args.length > 3) try {
			amount = Integer.parseInt(args[3]);
		} catch (NumberFormatException ex) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%amount%", args[3]);
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-amount"), Level.WARNING);
		}

		boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
		ItemStack book = new ItemStack(Material.BOOK);
		if (useBooks) book = new ItemStack(Material.ENCHANTED_BOOK);
		book = ItemUtils.addEnchantmentsToItem(book, levels);

		for(int i = 0; i < amount; i++)
			ItemUtils.giveItemToPlayer(givePlayer, book, givePlayer.getLocation(), false);

		HashMap<String, Object> codes = ChatUtils.getCodes();
		if (player != null) codes.put("%player%", player.getDisplayName());
		else
			codes.put("%player%", ChatUtils.getMessage(codes, "commands.console-player"));
		codes.put("%give_player%", givePlayer.getDisplayName());

		if (amount == 1) {
			if (sender.equals(givePlayer)) ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.give-book"), Level.INFO);
			else {
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.give-book-other"), Level.INFO);
				ChatUtils.sendMessage(sender, givePlayer, ChatUtils.getMessage(codes, "commands.receive-book"), Level.INFO);
			}
			return true;
		} else {
			codes.put("%amount%", amount);
			if (sender.equals(givePlayer)) ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.give-book-multiple"), Level.INFO);
			else {
				ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.give-book-other-multiple"), Level.INFO);
				ChatUtils.sendMessage(sender, givePlayer, ChatUtils.getMessage(codes, "commands.receive-book-multiple"), Level.INFO);
			}
			return true;
		}
	}
}