package org.ctp.enchantmentsolution.commands;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigLore implements CommandExecutor {

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("enchantmentsolution.command.configlore")) {
				if (args.length > 0) {
					String arg = args[0];
					switch (arg.toLowerCase()) {
						case "enchantment":
						case "enchant":
							if (args.length > 1) for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
								if (enchant.getName().equalsIgnoreCase(args[1])) {
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
							for(int i = 1; i < args.length; i++) {
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
}
