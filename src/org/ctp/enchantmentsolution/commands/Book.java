package org.ctp.enchantmentsolution.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import java.util.HashMap;

public class Book implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        if (sender instanceof ConsoleCommandSender) {
            if (args.length < 4) {
                return false;
            }
        }
        if (sender instanceof Player) {
            if (args.length <= 3) player = (Player) sender;
            if (!sender.hasPermission("enchantmentsolution.command.book")) {
                ChatUtils.sendMessage(sender, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
                return true;
            }
        }
        if (args.length > 3) {
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.getName().equalsIgnoreCase(args[3])) {
                    player = onlinePlayer;
                }
            }
        }
        if (player == null) {
            ChatUtils.sendMessage(sender, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.player-not-found"));
            return true;
        }
        if (args.length == 0) {
            return false;
        }
        String enchantmentName = args[0];
        for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments()) {
            if (enchant.getName().equalsIgnoreCase(enchantmentName)) {
                if (!enchant.isEnabled()) {
                    HashMap<String, Object> codes = ChatUtils.getCodes();
                    ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.enchant-disabled"));
                    return true;
                }
                int level = 1;
                if (args.length > 1) try {
                    level = Integer.parseInt(args[1]);
                    if (level < 1) {
                        HashMap<String, Object> codes = ChatUtils.getCodes();
                        codes.put("%level%", level);
                        ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.level-too-low"));
                        level = 1;
                    }
                } catch (NumberFormatException ex) {
                    HashMap<String, Object> codes = ChatUtils.getCodes();
                    codes.put("%level%", args[1]);
                    ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.invalid-level"));
                }
                if (level > enchant.getMaxLevel()) {
                    HashMap<String, Object> codes = ChatUtils.getCodes();
                    codes.put("%level%", level);
                    codes.put("%maxLevel%", enchant.getMaxLevel());
                    codes.put("%enchant%", enchant.getDisplayName());
                    ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.level-too-high"));
                    level = enchant.getMaxLevel();
                }
                boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
                int amount = 1;
                if (args.length > 2) try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {

                }
                ItemStack book;
                if (useBooks) {
                    book = new ItemStack(Material.ENCHANTED_BOOK);
                } else {
                    book = new ItemStack(Material.BOOK);
                }
                book = ItemUtils.addEnchantmentToItem(book, enchant, level);
                for (int i = 0; i < amount; i++) {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItem(player.getLocation(), book);
                    }
                    player.getInventory().addItem(book);
                }
                HashMap<String, Object> codes = ChatUtils.getCodes();
                codes.put("%level%", level);
                codes.put("%enchant%", enchant.getDisplayName());
                codes.put("%amount%", amount);
                codes.put("%player%", player.getDisplayName());
                if (amount == 1) {
                    if (player != sender) {
                        ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.give-book"));
                    }
                    ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.receive-book"));
                } else {
                    if (player != sender) {
                        ChatUtils.sendMessage(sender, ChatUtils.getMessage(codes, "commands.give-book-multiple"));
                    }
                    ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.receive-book-multiple"));
                }

            }
        }
        return true;
    }
}
