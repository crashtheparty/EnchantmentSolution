package org.ctp.enchantmentsolution.utils.commands;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.commands.CrashCommand;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.crashapi.utils.StringUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.commands.EnchantmentSolutionCommand;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.inventory.*;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.inventory.rpg.RPGInventory;
import org.ctp.enchantmentsolution.listeners.VanishListener;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandUtils {

	public static boolean anvil(CommandSender sender, CrashCommand details, String[] args) {
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
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		Chatable.get().sendWarning("Console may not use this command.");
		return true;
	}

	public static boolean calc(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				EnchantabilityCalc inv = new EnchantabilityCalc(player);
				EnchantmentSolution.getPlugin().addInventory(inv);
				inv.setInventory();
			} else
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		Chatable.get().sendWarning("Console may not use this command.");
		return true;
	}

	public static boolean config(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				ConfigInventory inv = new ConfigInventory(player);
				EnchantmentSolution.getPlugin().addInventory(inv);
				inv.setInventory();
			} else
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		Chatable.get().sendWarning("Console may not use this command.");
		return true;
	}

	public static boolean debug(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		Configurations c = Configurations.getConfigurations();
		if (sender instanceof Player) player = (Player) sender;
		else if (sender.isOp()) {
			c.generateDebug();
			Chatable.get().sendInfo(Chatable.get().getMessage(ChatUtils.getCodes(), "commands.debug"));
		}
		if (sender.hasPermission(details.getPermission())) {
			c.generateDebug();
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.debug"), Level.INFO);
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	public static boolean fix(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		Player fixPlayer = null;
		if (sender instanceof Player) player = (Player) sender;

		if (sender.hasPermission(details.getPermission())) {
			fixPlayer = player;
			if (args.length > 1) {
				String arg = args[1];
				if (!arg.equals("@p")) {
					fixPlayer = Bukkit.getPlayer(arg);
					if (fixPlayer != null && !fixPlayer.equals(sender) && !sender.hasPermission(details.getPermission() + ".others")) {
						Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission-other"), Level.WARNING);
						return false;
					} else if (fixPlayer == null) {
						HashMap<String, Object> codes = ChatUtils.getCodes();
						codes.put("%invalid_player%", args[1]);
						Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-player"), Level.WARNING);
						return false;
					}
				}
			}

			int items = 0;
			int fixedItems = 0;
			ItemStack[] playerItems = EnchantmentSolution.getESPlayer(fixPlayer).getInventoryItems();
			Map<Integer, ItemStack> changedItems = new HashMap<Integer, ItemStack>();

			for(int i = 0; i < playerItems.length; i++) {
				ItemStack item = playerItems[i];
				if (item != null) {
					items++;
					int changed = VanishListener.checkEnchants(fixPlayer, item);
					if (changed == 1) {
						changedItems.put(i, item);
						fixedItems++;
					}
				}
			}
			String fIString = "";
			if (!changedItems.isEmpty()) {
				Iterator<Entry<Integer, ItemStack>> iter = changedItems.entrySet().iterator();
				while (iter.hasNext()) {
					HashMap<String, Object> fICodes = ChatUtils.getCodes();
					Entry<Integer, ItemStack> entry = iter.next();
					fICodes.put("%slot%", entry.getKey());
					fICodes.put("%material%", entry.getValue().getType());
					fIString += "\n" + Chatable.get().getMessage(fICodes, "commands.fixed-item-string");
				}
			}

			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%player%", player.getName());
			codes.put("%fix_player%", fixPlayer.getName());
			codes.put("%total_items%", items);
			codes.put("%fixed_items%", fixedItems);
			codes.put("%fixed_item_string%", fIString);
			if (fixPlayer.equals(player)) Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.fix-enchants"), Level.INFO);
			else {
				Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.fix-enchants-other"), Level.INFO);
				Chatable.get().sendMessage(sender, fixPlayer, Chatable.get().getMessage(codes, "commands.other-fixed-enchants"), Level.INFO);
			}
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	public static boolean grindstone(CommandSender sender, CrashCommand details, String[] args) {
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
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"));
			return true;
		}
		Chatable.get().sendWarning("Console may not use this command.");
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean lore(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission(details.getPermission())) {
				if (args.length > 1) {
					String arg = args[1];
					switch (arg.toLowerCase(Locale.ROOT)) {
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
											Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.level-too-low"), Level.WARNING);
											level = 1;
										}
									} catch (NumberFormatException ex) {
										HashMap<String, Object> codes = ChatUtils.getCodes();
										codes.put("%level%", args[3]);
										Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-level"), Level.WARNING);
									}
									JSONArray json = new JSONArray();
									JSONObject name = new JSONObject();
									name.put("text", Chatable.get().getStarter());
									json.add(name);
									JSONObject obj = new JSONObject();
									obj.put("text", ChatColor.GREEN + "Click Here");
									HashMap<Object, Object> action = new HashMap<Object, Object>();
									action.put("action", "suggest_command");
									action.put("value", PersistenceNMS.getEnchantmentString(new EnchantmentLevel(enchant, level)).replace(ChatColor.COLOR_CHAR, '&'));
									obj.put("clickEvent", action);
									json.add(obj);
									Chatable.get().sendRawMessage(player, json.toJSONString());
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
							name.put("text", Chatable.get().getStarter());
							json.add(name);
							JSONObject obj = new JSONObject();
							obj.put("text", ChatColor.GREEN + "Click Here");
							HashMap<Object, Object> action = new HashMap<Object, Object>();
							action.put("action", "suggest_command");
							action.put("value", str.toString());
							obj.put("clickEvent", action);
							json.add(obj);
							Chatable.get().sendRawMessage(player, json.toJSONString());
							break;
					}
				}
			} else
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"));
		}
		return true;
	}

	public static boolean reload(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (sender.hasPermission(details.getPermission())) {
			Configurations.getConfigurations().reload();
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.reload"), Level.INFO);
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	public static boolean reset(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (sender.hasPermission(details.getPermission())) {
			EnchantmentSolution.getPlugin().closeInventories(null);
			EnchantmentSolution.getPlugin().reEquipArmor();
			TableEnchantments.removeAllTableEnchantments();
			Minigame.reset();
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.reset-inventory"), Level.INFO);
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	public static boolean rpg(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player && sender.hasPermission(details.getPermission())) {
			player = (Player) sender;
			RPGInventory rpg = new RPGInventory(player);
			EnchantmentSolution.getPlugin().addInventory(rpg);
			rpg.setInventory();
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean rpgStats(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player && sender.hasPermission(details.getPermission())) {
			player = (Player) sender;
			OfflinePlayer checkPlayer = player;
			if (args.length > 1) {
				checkPlayer = Bukkit.getOfflinePlayer(args[1]);
				if (checkPlayer != null && !checkPlayer.equals(player) && !player.hasPermission(details.getPermission() + ".others")) {
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission-other"), Level.WARNING);
					return false;
				} else if (checkPlayer == null) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%invalid_player%", args[1]);
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-player"), Level.WARNING);
					return false;
				}
			}
			RPGPlayer rpg = RPGUtils.getPlayer(checkPlayer);
			HashMap<String, Object> codes = ChatUtils.getCodes();
			StringBuilder sb = new StringBuilder();
			codes.put("%player%", checkPlayer.getName());
			if (rpg != null) {
				codes.put("%level%", rpg.getLevel());
				codes.put("%experience%", rpg.getExperience());
			} else {
				codes.put("%level%", 0);
				codes.put("%experience%", 0);
			}
			sb.append(Chatable.get().getMessage(codes, "rpg.stats.exp"));
			List<EnchantmentLevel> levels = EnchantmentLevel.fromMap(rpg == null ? RPGUtils.getFreeEnchantments() : rpg.getEnchantments());
			for(EnchantmentLevel l: levels) {
				HashMap<String, Object> enchCodes = ChatUtils.getCodes();
				enchCodes.put("%enchantment%", l.getEnchant().getDisplayName());
				enchCodes.put("%level%", l.getLevel());
				sb.append("\n" + Chatable.get().getMessage(enchCodes, "rpg.stats.enchantment"));
			}
			Chatable.get().sendMessage(sender, player, sb.toString(), Level.INFO);
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean rpgTop(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;

		List<RPGPlayer> players = RPGUtils.getPlayers();
		players.sort((rpgOne, rpgTwo) -> {
			if (rpgOne.getLevel() > rpgTwo.getLevel()) return 1;
			else if (rpgOne.getLevel() < rpgTwo.getLevel()) return -1;
			else if (rpgOne.getExperience().doubleValue() > rpgTwo.getExperience().doubleValue()) return 1;
			else if (rpgOne.getExperience().doubleValue() < rpgTwo.getExperience().doubleValue()) return -1;
			return 0;
		});

		int page = 1;
		if (args.length > 1) try {
			page = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {}

		while (page * 10 > players.size())
			page--;
		if (page < 1) page = 1;

		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%page%", page);
		String rpgPage = Chatable.get().getMessage(codes, "rpg.top.page");
		if (player != null) {
			JSONArray json = new JSONArray();
			JSONObject first = new JSONObject();
			first.put("text", "\n" + ChatColor.DARK_BLUE + "******");
			JSONObject second = new JSONObject();
			if (page > 1) {
				second.put("text", ChatColor.GREEN + "<<<");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/es rpgtop " + (page - 1));
				second.put("clickEvent", action);
			} else
				second.put("text", ChatColor.DARK_BLUE + "***");
			JSONObject third = new JSONObject();
			third.put("text", ChatColor.DARK_BLUE + "****** " + rpgPage + ChatColor.DARK_BLUE + " ******");
			JSONObject fourth = new JSONObject();
			if (players.size() > page * 10) {
				fourth.put("text", ChatColor.GREEN + ">>>");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/es rpgtop " + (page + 1));
				fourth.put("clickEvent", action);
			} else
				fourth.put("text", ChatColor.DARK_BLUE + "***");
			JSONObject fifth = new JSONObject();
			fifth.put("text", ChatColor.DARK_BLUE + "******" + "\n\n");
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			for(int i = 0; i < 10; i++) {
				int num = i + (page - 1) * 10;
				if (num >= players.size()) break;
				RPGPlayer rpg = players.get(num);
				JSONObject name = new JSONObject();
				HashMap<String, Object> playerCodes = ChatUtils.getCodes();
				playerCodes.put("%player%", rpg.getPlayer().getName());
				playerCodes.put("%rank%", num + 1);
				playerCodes.put("%level%", rpg.getLevel());
				name.put("text", Chatable.get().getMessage(playerCodes, "rpg.top.player_stats"));
				json.add(name);
			}
			json.add("\n");
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			Chatable.get().sendRawMessage(player, json.toJSONString());
		} else {
			String message = "\n" + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "****** " + rpgPage + ChatColor.DARK_BLUE + " ******" + (players.size() > page * 10 ? ">>>" : "***") + "******" + "\n";
			for(int i = 0; i < 10; i++) {
				int num = i + (page - 1) * 10;
				if (num >= players.size()) break;
				RPGPlayer rpg = players.get(num);
				HashMap<String, Object> playerCodes = ChatUtils.getCodes();
				playerCodes.put("%player%", rpg.getPlayer().getName());
				playerCodes.put("%rank%", num + 1);
				playerCodes.put("%level%", rpg.getLevel());
				message += "\n" + Chatable.get().getMessage(playerCodes, "rpg.top.player_stats");
			}
			message += "\n" + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "****** " + rpgPage + ChatColor.DARK_BLUE + " ******" + (players.size() > page * 10 ? ">>>" : "***") + "******" + "\n";
			Chatable.get().sendToConsole(Level.INFO, message);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static boolean rpgEdit(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player && sender.hasPermission(details.getPermission())) {
			player = (Player) sender;
			OfflinePlayer editPlayer = player;
			if (args.length > 1) {
				editPlayer = Bukkit.getOfflinePlayer(args[1]);
				if (editPlayer == null) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%invalid_player%", args[1]);
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-player"), Level.WARNING);
					return false;
				}
			}
			RPGPlayer rpg = RPGUtils.getPlayer(editPlayer);

			if (args.length > 2) switch (args[2]) {
				case "add_level":
				case "set_level":
				case "remove_level":
					int level = rpg.getLevel();
					if (args.length > 3) {
						try {
							int argLevel = Integer.parseInt(args[3]);
							switch (args[2]) {
								case "add_level":
									level += argLevel;
									break;
								case "remove_level":
									level -= argLevel;
									break;
								case "set_level":
									level = argLevel;
									break;
							}
							if (level < 0) level = 0;
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%level%", args[3]);
							Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-level-positive-integer"), Level.WARNING);
							return false;
						}
						HashMap<String, Object> codes = ChatUtils.getCodes();
						codes.put("%level%", level);
						codes.put("%player%", editPlayer.getName());
						rpg.setLevel(level);
						Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.rpg-set-level"), Level.INFO);
						return true;
					}
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.specify-level-integer"), Level.WARNING);
					return false;
				case "add_experience":
				case "set_experience":
				case "remove_experience":
					double experience = rpg.getExperience().doubleValue();
					if (args.length > 3) {
						try {
							double argExp = Integer.parseInt(args[3]);
							switch (args[2]) {
								case "add_experience":
									experience += argExp;
									break;
								case "remove_experience":
									experience -= argExp;
									break;
								case "set_experience":
									experience = argExp;
									break;
							}
							if (experience < 0) experience = 0;
						} catch (NumberFormatException ex) {
							HashMap<String, Object> codes = ChatUtils.getCodes();
							codes.put("%experience%", args[3]);
							Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-experience-positive-double"), Level.WARNING);
							return false;
						}
						HashMap<String, Object> codes = ChatUtils.getCodes();
						codes.put("%experience%", experience);
						codes.put("%player%", editPlayer.getName());
						rpg.setExperience(experience);
						Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.rpg-set-experience"), Level.INFO);
						return true;
					}
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.specify-experience-double"), Level.WARNING);
					return false;
				case "set_enchantment_level":
					if (args.length > 3) {
						String enchantmentName = args[3];
						for(CustomEnchantment enchant: RegisterEnchantments.getRegisteredEnchantments())
							if (enchant.getName().equalsIgnoreCase(enchantmentName)) {
								if (!enchant.isEnabled()) {
									HashMap<String, Object> codes = ChatUtils.getCodes();
									Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.enchant-disabled"), Level.WARNING);
									return true;
								}
								int enchLevel = 1;
								if (args.length > 4) try {
									enchLevel = Integer.parseInt(args[4]);
									if (enchLevel < 0) {
										HashMap<String, Object> codes = ChatUtils.getCodes();
										codes.put("%level%", enchLevel);
										Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.rpg-level-too-low"), Level.WARNING);
										enchLevel = 0;
									}
								} catch (NumberFormatException ex) {
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%level%", args[4]);
									Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.invalid-level"), Level.WARNING);
								}
								if (enchLevel < 1) {
									rpg.removeEnchantment(enchant.getRelativeEnchantment());
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%player%", editPlayer.getName());
									codes.put("%enchantment%", enchant.getDisplayName());
									Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.remove-rpg-enchantment"), Level.INFO);
									return true;
								} else {
									rpg.giveEnchantment(new EnchantmentLevel(enchant, enchLevel));
									HashMap<String, Object> codes = ChatUtils.getCodes();
									codes.put("%player%", editPlayer.getName());
									codes.put("%enchantment%", enchant.getDisplayName());
									codes.put("%level%", enchLevel);
									Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.set-rpg-enchantment"), Level.INFO);
									return true;
								}
							}
						HashMap<String, Object> codes = ChatUtils.getCodes();
						codes.put("%enchant%", enchantmentName);
						Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.enchant-not-found"), Level.WARNING);
						return true;
					}
					return false;
				default:
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%command%", args[2]);
					Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.rpg-invalid-edit"), Level.WARNING);
					return false;
			}
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

	public static boolean printHelp(CommandSender sender, String label) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		for(ESCommand command: EnchantmentSolutionCommand.getCommands())
			if (sender.hasPermission(command.getPermission()) && EnchantmentSolutionCommand.containsCommand(command, label)) {
				Chatable.get().sendMessage(sender, player, StringUtils.decodeString("\n" + command.getFullUsage()), Level.INFO);
				return true;
			}
		return printHelp(sender, 1);
	}

	@SuppressWarnings("unchecked")
	public static boolean printHelp(CommandSender sender, int page) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;

		List<ESCommand> playerCommands = new ArrayList<ESCommand>();
		for(ESCommand command: EnchantmentSolutionCommand.getCommands())
			if (sender.hasPermission(command.getPermission())) playerCommands.add(command);

		if ((page - 1) * 5 > playerCommands.size()) return printHelp(sender, page - 1);

		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%page%", page);
		String commandsPage = Chatable.get().getMessage(codes, "commands.help.commands_page");
		if (player != null) {
			JSONArray json = new JSONArray();
			JSONObject first = new JSONObject();
			first.put("text", "\n" + ChatColor.DARK_BLUE + "******");
			JSONObject second = new JSONObject();
			if (page > 1) {
				second.put("text", ChatColor.GREEN + "<<<");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/es help " + (page - 1));
				second.put("clickEvent", action);
			} else
				second.put("text", ChatColor.DARK_BLUE + "***");
			JSONObject third = new JSONObject();
			third.put("text", ChatColor.DARK_BLUE + "****** " + commandsPage + ChatColor.DARK_BLUE + " ******");
			JSONObject fourth = new JSONObject();
			if (playerCommands.size() > page * 5) {
				fourth.put("text", ChatColor.GREEN + ">>>");
				HashMap<Object, Object> action = new HashMap<Object, Object>();
				action.put("action", "run_command");
				action.put("value", "/es help " + (page + 1));
				fourth.put("clickEvent", action);
			} else
				fourth.put("text", ChatColor.DARK_BLUE + "***");
			JSONObject fifth = new JSONObject();
			fifth.put("text", ChatColor.DARK_BLUE + "******" + "\n");
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			for(int i = 0; i < 5; i++) {
				int num = i + (page - 1) * 5;
				if (num >= playerCommands.size()) break;
				CrashCommand c = playerCommands.get(num);
				JSONObject name = new JSONObject();
				JSONObject desc = new JSONObject();
				JSONObject action = new JSONObject();
				action.put("action", "run_command");
				action.put("value", "/es help " + c.getCommand());
				name.put("text", ChatColor.GOLD + c.getCommand());
				name.put("clickEvent", action);
				json.add(name);
				HashMap<String, Object> descCodes = new HashMap<String, Object>();
				descCodes.put("%description%", Chatable.get().getMessage(ChatUtils.getCodes(), c.getDescriptionPath()));
				desc.put("text", shrink(Chatable.get().getMessage(descCodes, "commands.help.commands_info_shrink")) + "\n");
				json.add(desc);
			}
			json.add(first);
			json.add(second);
			json.add(third);
			json.add(fourth);
			json.add(fifth);
			Chatable.get().sendRawMessage(player, json.toJSONString());
		} else {
			String message = "\n" + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "****** " + commandsPage + ChatColor.DARK_BLUE + " ******" + (playerCommands.size() > page * 5 ? ">>>" : "***") + "******" + "\n";
			for(int i = 0; i < 5; i++) {
				int num = i + (page - 1) * 5;
				if (num >= playerCommands.size()) break;
				CrashCommand c = playerCommands.get(num);
				HashMap<String, Object> descCodes = new HashMap<String, Object>();
				descCodes.put("%command%", c.getCommand());
				descCodes.put("%description%", Chatable.get().getMessage(ChatUtils.getCodes(), c.getDescriptionPath()));
				message += shrink(Chatable.get().getMessage(descCodes, "commands.help.commands_info_shrink")) + "\n";
			}
			message += "\n" + ChatColor.DARK_BLUE + "******" + (page > 1 ? "<<<" : "***") + "****** " + commandsPage + ChatColor.DARK_BLUE + " ******" + (playerCommands.size() > page * 5 ? ">>>" : "***") + "******" + "\n";
			Chatable.get().sendToConsole(Level.INFO, message);
		}

		return true;
	}

	private static String shrink(String s) {
		if (s.length() > 60) return s.substring(0, 58) + "...";
		return s;
	}

	@SuppressWarnings("unchecked")
	public static boolean test(CommandSender sender, CrashCommand details, String[] args) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		if (sender.hasPermission(details.getPermission())) {
			if (args.length > 1) switch (args[1]) {
				case "item":
					if (sender instanceof Player) {
						ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
						if (item == null) break;
						JSONArray json = new JSONArray();
						JSONObject name = new JSONObject();
						name.put("text", Chatable.get().getStarter());
						json.add(name);
						JSONObject obj = new JSONObject();
						HashMap<String, Object> codes = ChatUtils.getCodes();
						codes.put("%item%", item.toString().replace(ChatColor.COLOR_CHAR, '&'));
						obj.put("text", Chatable.get().getMessage(codes, "commands.item-test"));
						HashMap<Object, Object> action = new HashMap<Object, Object>();
						action.put("action", "copy_to_clipboard");
						action.put("value", item.toString());
						obj.put("clickEvent", action);
						json.add(obj);
						Chatable.get().sendRawMessage(player, json.toJSONString());
					}
					break;
				default:
					return false;
			}
			else
				return false;
		} else
			Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(ChatUtils.getCodes(), "commands.no-permission"), Level.WARNING);
		return true;
	}

}
