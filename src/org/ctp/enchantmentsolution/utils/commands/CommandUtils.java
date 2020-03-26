package org.ctp.enchantmentsolution.utils.commands;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.inventory.*;
import org.ctp.enchantmentsolution.listeners.VanishListener;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandUtils {

	public static boolean anvil(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
				if (inv == null) {
					inv = new Anvil(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
					inv.setInventory();
				} else if (!(inv instanceof Anvil)) {
					inv.close(true);
					inv = new Anvil(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
					inv.setInventory();
				}
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		ChatUtils.sendWarning("Console may not use this command.");
		return true;
	}
	
	public static boolean calc(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				EnchantabilityCalc inv = new EnchantabilityCalc(player);
				EnchantmentSolution.getPlugin().addInventory(inv);
				inv.setInventory();
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		ChatUtils.sendWarning("Console may not use this command.");
		return true;
	}
	
	public static boolean config(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				ConfigInventory inv = new ConfigInventory(player);
				EnchantmentSolution.getPlugin().addInventory(inv);
				inv.setInventory();
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		ChatUtils.sendWarning("Console may not use this command.");
		return true;
	}
	
	public static boolean debug(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		else if (sender.isOp()) {
			Configurations.generateDebug();
			ChatUtils.sendInfo(ChatUtils.getMessage(ChatUtils.getCodes(), "commands.debug"));
		}
		if (sender.hasPermission(details.getPermission())) {
			Configurations.generateDebug();
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.debug"), Level.INFO);
		} else
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}
	
	public static boolean grindstone(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
				if (inv == null) {
					inv = new Grindstone(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
					inv.setInventory();
				} else if (!(inv instanceof Grindstone)) {
					inv.close(true);
					inv = new Grindstone(player, null);
					EnchantmentSolution.getPlugin().addInventory(inv);
					inv.setInventory();
				}
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		ChatUtils.sendWarning("Console may not use this command.");
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean lore(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				if (args.length > 1) {
					String arg = args[1];
					switch (arg.toLowerCase()) {
						case "enchantment":
						case "enchant":
							if (args.length > 2) for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
								if (enchant.getName().equalsIgnoreCase(args[2])) {
									int level = 1;
									if (args.length > 3) try {
										level = Integer.parseInt(args[3]);
										if (level < 1) {
											HashMap<String, Object> codes = ChatUtils.getCodes();
											codes.put("%level%", level);
											ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.level-too-low"), Level.WARNING);
											level = 1;
										}
									} catch (NumberFormatException ex) {
										HashMap<String, Object> codes = ChatUtils.getCodes();
										codes.put("%level%", args[3]);
										ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.invalid-level"), Level.WARNING);
									}
									JSONArray json = new JSONArray();
									JSONObject name = new JSONObject();
									name.put("text", ChatUtils.getStarter());
									json.add(name);
									JSONObject obj = new JSONObject();
									obj.put("text", ChatColor.GREEN + "Click Here");
									HashMap<Object, Object> action = new HashMap<Object, Object>();
									action.put("action", "suggest_command");
									action.put("value", StringUtils.getEnchantmentString(new EnchantmentLevel(enchant, level)).replace(ChatColor.COLOR_CHAR, '&'));
									obj.put("clickEvent", action);
									json.add(obj);
									ChatUtils.sendRawMessage(player, json.toJSONString());
								}
							break;
						case "string":
						default:
							StringBuilder str = new StringBuilder();
							for(int i = 2; i < args.length; i++) {
								str.append(args[i]);
								if (i + 1 < args.length) str.append(" ");
							}
							JSONArray json = new JSONArray();
							JSONObject name = new JSONObject();
							name.put("text", ChatUtils.getStarter());
							json.add(name);
							JSONObject obj = new JSONObject();
							obj.put("text", ChatColor.GREEN + "Click Here");
							HashMap<Object, Object> action = new HashMap<Object, Object>();
							action.put("action", "suggest_command");
							action.put("value", str.toString());
							obj.put("clickEvent", action);
							json.add(obj);
							ChatUtils.sendRawMessage(player, json.toJSONString());
							break;
					}
				}
			} else
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		return true;
	}
	
	public static boolean reload(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (sender.hasPermission(details.getPermission())) {
			Configurations.reload();
			VanishListener.reload();
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.reload"), Level.INFO);
		} else
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}
	
	public static boolean reset(CommandSender sender, ESCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender; 
		if (sender.hasPermission(details.getPermission())) {
			EnchantmentSolution.getPlugin().resetInventories();
			TableEnchantments.removeAllTableEnchantments();
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.reset-inventory"), Level.INFO);
		} else
			ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

}
