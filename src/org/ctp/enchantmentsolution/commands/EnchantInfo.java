package org.ctp.enchantmentsolution.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.bukkit.ChatColor;

public class EnchantInfo implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		if(args.length == 0) {
			sendEnchantInfo(sender, 1);
		}else if(args.length == 1) {
			try {
				int page = Integer.parseInt(args[0]);
				sendEnchantInfo(sender, page);
				return true;
			} catch (NumberFormatException e) {
				
			}
			CustomEnchantment enchantment = null;
			for(CustomEnchantment enchant : Enchantments.getEnchantments()) {
				if(enchant.getName().equalsIgnoreCase(args[0])) {
					enchantment = enchant;
					break;
				}
			}
			if(enchantment == null) {
				if(player != null) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", args[0]);
					ChatUtils.sendMessage(player, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
				} else {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%enchant%", args[0]);
					ChatUtils.sendToConsole(Level.WARNING, ChatUtils.getMessage(codes, "commands.enchant-not-found"));
				}
			} else {
				String page = enchantment.getDetails();
				if(player != null) {
					ChatUtils.sendMessage(player, page);
				} else {
					sender.sendMessage(page);
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void sendEnchantInfo(CommandSender sender, int page) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		if(page < 1) {
			page = 1;
		}
		while(Enchantments.getEnchantments().size() < ((page - 1) * 10)) {
			if(page == 1) break;
			page -= 1;
		}
		if(player != null) {
			JSONArray json = new JSONArray();
			JSONObject first = new JSONObject();
			first.put("text", StringUtils.LF + ChatColor.DARK_BLUE + "******");
			JSONObject second = new JSONObject();
			if(page > 1) {
				second.put("text", ChatColor.GREEN + "<<<");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/enchantinfo " + (page - 1));
				second.put("clickEvent", action);
			} else {
				second.put("text", ChatColor.DARK_BLUE + "***");
			}
			JSONObject third = new JSONObject();
			third.put("text", ChatColor.DARK_BLUE + "******" + ChatColor.AQUA + " Enchantments Page " + page + ChatColor.DARK_BLUE + " ******");
			JSONObject fourth = new JSONObject();
			if(Enchantments.getEnchantments().size() > (page * 10)) {
				fourth.put("text", ChatColor.GREEN + ">>>");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/enchantinfo " + (page + 1));
				fourth.put("clickEvent", action);
			} else {
				fourth.put("text", ChatColor.DARK_BLUE + "***");
			}
			JSONObject fifth = new JSONObject();
			fifth.put("text", ChatColor.DARK_BLUE + "******" + StringUtils.LF);
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			List<CustomEnchantment> alphabetical = new ArrayList<CustomEnchantment>();
			for(CustomEnchantment enchantment : Enchantments.getEnchantments()) {
				alphabetical.add(enchantment);
			}
			Collections.sort(alphabetical, new Comparator<CustomEnchantment>(){
				@Override
				public int compare(CustomEnchantment o1, CustomEnchantment o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for(int i = 0; i < 10; i++) {
				int num = i + (page - 1) * 10;
				if(num >= Enchantments.getEnchantments().size()) {
					break;
				}
				CustomEnchantment enchant = alphabetical.get(num);
				JSONObject name = new JSONObject();
				JSONObject desc = new JSONObject();
				JSONObject action = new JSONObject();
				action.put("action", "run_command");
				action.put("value", "/enchantinfo " + enchant.getName());
				name.put("text", shrink(ChatColor.GOLD + enchant.getDisplayName()));
				name.put("clickEvent", action);
				json.add(name);
				desc.put("text", shrink(ChatColor.GOLD + enchant.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.WHITE + enchant.getDescription())
						.substring((ChatColor.GOLD + enchant.getDisplayName()).length()) + StringUtils.LF);
				json.add(desc);
			}
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			ChatUtils.sendRawMessage(player, json.toJSONString());
		} else {
			String message = StringUtils.LF + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "******"
					+ ChatColor.AQUA + " Enchantments Page " + page + ChatColor.DARK_BLUE + " ******" 
					+ (Enchantments.getEnchantments().size() < ((page - 1) * 10) ? ">>>" : "***") + "******" + StringUtils.LF;
			for(int i = 0; i < 10; i++) {
				int num = i + (page - 1) * 10;
				if(num >= Enchantments.getEnchantments().size()) {
					break;
				}
				CustomEnchantment enchant = Enchantments.getEnchantments().get(num);
				message += shrink(ChatColor.GOLD + enchant.getDisplayName() + ": " + ChatColor.WHITE + enchant.getDescription()) + StringUtils.LF;
			}
			message += StringUtils.LF + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "******"
					+ ChatColor.AQUA + " Enchantments Page " + page + ChatColor.DARK_BLUE + " ******" 
					+ (Enchantments.getEnchantments().size() < ((page - 1) * 10) ? ">>>" : "***") + "******" + StringUtils.LF;
			ChatUtils.sendToConsole(Level.INFO, message);
		}
	}
	
	private String shrink(String s) {
		if(s.length() > 60) {
			return s.substring(0, 58) + "...";
		}
		return s;
	}

}
