package org.ctp.enchantmentsolution.utils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.*;
import org.ctp.enchantmentsolution.inventory.ConfigInventory.Screen;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.inventory.rpg.RPGInventory;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class InventoryClickUtils {

	public static void setEnchantmentTableDetails(EnchantmentTable table, Player player, Inventory inv,
	Inventory clickedInv, int slot) {
		if (inv.getType() != InventoryType.CHEST) {
			ItemStack item = clickedInv.getItem(slot);
			if (EnchantmentUtils.isEnchantable(item)) {
				ItemStack replace = new ItemStack(Material.AIR);
				int original_amount = item.getAmount();
				if (original_amount > 1) {
					replace = item.clone();
					replace.setAmount(replace.getAmount() - 1);
					item.setAmount(1);
				}
				if (table.addItem(item)) {
					table.setInventory();
					player.getInventory().setItem(slot, replace);
				} else if (original_amount > 1) item.setAmount(original_amount);
			} else if (item != null && item.getType() == Material.LAPIS_LAZULI) {
				player.getInventory().setItem(slot, table.addToLapisStack(item));
				table.setInventory();
			}
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (table.getItems().contains(item)) {
				if (table.removeItem(item, slot)) {
					table.setInventory();
					ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
				}
			} else if (slot > 17 && slot % 9 >= 3 && slot % 9 <= 8 && item != null && item.getType() != Material.RED_STAINED_GLASS_PANE && item.getType() != Material.BLACK_STAINED_GLASS_PANE) {
				int itemSlot = (slot - 18) / 9;
				int itemLevel = slot % 9 - 3;
				table.enchantItem(itemSlot, itemLevel);
			} else if (slot == 10) {
				ItemStack lapisStack = table.removeFromLapisStack();
				if (lapisStack != null) ItemUtils.giveItemToPlayer(player, lapisStack, player.getLocation(), false);
				table.setInventory();
			}
		}
	}

	public static void setAnvilDetails(Anvil anvil, Player player, Inventory inv, Inventory clickedInv, int slot) {
		if (inv.getType() != InventoryType.CHEST) {
			if (inv.getType() == InventoryType.ANVIL || anvil.isInLegacy()) return;
			ItemStack item = clickedInv.getItem(slot);
			if (item == null || MatData.isAir(item.getType())) return;
			ItemStack replace = new ItemStack(Material.AIR);
			int original_amount = item.getAmount();
			if (original_amount > 1 && item.getType() == Material.BOOK && item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
				replace = item.clone();
				replace.setAmount(replace.getAmount() - 1);
				item.setAmount(1);
			}
			if (anvil.addItem(item)) {
				anvil.setInventory();
				player.getInventory().setItem(slot, replace);
			} else if (original_amount > 1) item.setAmount(original_amount);
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (slot == 4) {
				if (item.getType() == Material.LIME_STAINED_GLASS_PANE) {
					if (ConfigString.RENAME_FROM_ANVIL.getBoolean() || player.hasPermission("enchantmentsolution.anvil.rename")) Anvil_GUI_NMS.createAnvil(player, anvil);
					else
						Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "anvil.rename-requires-permission"));
				} else { /* placeholder */ }
			} else if (slot == 16) {
				anvil.combine();
				anvil.setInventory();
			} else if ((slot == 31 || slot == 30) && item.getType() == Material.ANVIL) {
				anvil.close(false);
				AnvilUtils.addLegacyAnvil(player);
				Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "anvil.legacy-gui-open"));
			} else if ((slot == 31 || slot == 32) && item.getType().equals(Material.SMOOTH_STONE)) {
				anvil.close(false);
				Grindstone stone = new Grindstone(player, anvil.getBlock());
				EnchantmentSolution.getPlugin().addInventory(stone);
				stone.setInventory();
			} else if (anvil.getItems().contains(item)) if (anvil.removeItem(slot)) {
				anvil.setInventory();
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			}
		}
	}

	public static void setGrindstoneDetails(Grindstone stone, Player player, Inventory inv, Inventory clickedInv,
	int slot) {
		if (inv.getType() != InventoryType.CHEST) {
			ItemStack item = clickedInv.getItem(slot);
			if (item == null || MatData.isAir(item.getType())) return;
			ItemStack replace = new ItemStack(Material.AIR);
			int original_amount = item.getAmount();
			if (original_amount > 1) {
				replace = item.clone();
				replace.setAmount(replace.getAmount() - 1);
				item.setAmount(1);
			}
			if (stone.addItem(item)) {
				stone.setInventory();
				player.getInventory().setItem(slot, replace);
			} else if (original_amount > 1) item.setAmount(original_amount);
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (slot == 7) {
				stone.combine();
				stone.setInventory();
			} else if (slot == 31 && item.getType() == Material.ANVIL) {
				stone.close(false);
				Anvil anvil = new Anvil(player, stone.getBlock());
				EnchantmentSolution.getPlugin().addInventory(anvil);
				anvil.setInventory();
			} else if (stone.getItems().contains(item)) if (stone.removeItem(slot)) {
				stone.setInventory();
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			}
		}
	}

	public static void setConfigInventoryDetails(ConfigInventory configInv, Player player, Inventory inv,
	Inventory clickedInv, int slot, ClickType click) {
		if (inv.getType() != InventoryType.CHEST) return;
		else {
			ItemStack item = clickedInv.getItem(slot);
			if (item == null) return;
			YamlConfigBackup config = configInv.getConfig();
			YamlConfigBackup backup = configInv.getBackup();
			String level = configInv.getLevel();
			String type = configInv.getType();
			int page = configInv.getPage();
			switch (configInv.getScreen()) {
				case LIST_FILES:
					Configurations c = Configurations.getConfigurations();
					switch (slot) {
						case 2:
							configInv.listConfigDetails(c.getConfig().getConfig(), null);
							break;
						case 3:
							configInv.listConfigDetails(c.getFishing().getConfig(), null);
							break;
						case 4:
							configInv.listConfigDetails(c.getAdvancements().getConfig(), null);
							break;
						case 5:
							configInv.listConfigDetails(c.getLanguage().getConfig(), null);
							break;
						case 6:
							configInv.listConfigDetails(c.getEnchantments().getConfig(), null);
							break;
						case 12:
							configInv.listConfigDetails(c.getMinigames().getConfig(), null);
							break;
						case 13:
							configInv.listConfigDetails(c.getRPG().getConfig(), null);
							break;
						case 14:
							configInv.listConfigDetails(c.getHardMode().getConfig(), null);
							break;
						case 21:
							if (item.getType() != Material.BARRIER) configInv.saveAll();
							break;
						case 23:
							if (item.getType() != Material.BARRIER) configInv.revert();
							break;
					}
					break;
				case LIST_DETAILS:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listConfigDetails(config, null, level, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listConfigDetails(config, null, level, page - 1);
							break;
						case 48:
							if (checkPagination(item) && (level == null || level.equals(""))) configInv.setInventory(Screen.LIST_FILES);
							else if (level.indexOf(".") > -1) configInv.listConfigDetails(config, null, level.substring(0, level.lastIndexOf(".")), page);
							else
								configInv.listConfigDetails(config, null, null, page);
							break;
						case 50:
							if (item.getType() == Material.FIREWORK_STAR) configInv.listBackup(config, 1);
							break;
					}
					break;
				case LIST_EDIT:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listDetails(config, null, level, type, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listDetails(config, null, level, type, page - 1);
							break;
						case 48:
							if (checkPagination(item)) if (level.indexOf(".") > -1) configInv.listConfigDetails(config, null, level.substring(0, level.lastIndexOf(".")));
							else
								configInv.listConfigDetails(config, null, null);
							break;
						case 50:
							if (item.getType() == Material.NAME_TAG) if (click == ClickType.LEFT) configInv.openAnvil(level, type);
							else if (click == ClickType.RIGHT) configInv.openChat(level, type);
							else {}
							else {}
							break;
					}
					break;
				case LIST_ENUM:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listEnumDetails(config, level, type, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listEnumDetails(config, level, type, page - 1);
							break;
						case 49:
							if (checkPagination(item)) if (level.indexOf(".") > -1) configInv.listConfigDetails(config, null, level.substring(0, level.lastIndexOf(".")));
							else
								configInv.listConfigDetails(config, null, null);
							else {}
							break;
					}
					break;
				case LIST_ENUM_LIST_SHOW:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listEnumListShow(config, level, type, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listEnumListShow(config, level, type, page - 1);
							break;
						case 48:
							if (checkPagination(item)) if (level.indexOf(".") > -1) configInv.listConfigDetails(config, null, level.substring(0, level.lastIndexOf(".")));
							else
								configInv.listConfigDetails(config, null, null);
							else {}
							break;
						case 50:
							if (item.getType() == Material.NAME_TAG) configInv.listEnumListEdit(config, level, type, 1);
							break;
					}
					break;
				case LIST_ENUM_LIST_EDIT:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listEnumListEdit(config, level, type, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listEnumListEdit(config, level, type, page - 1);
							break;
						case 49:
							if (checkPagination(item)) configInv.listEnumListShow(config, level, type, 1);
							break;
					}
					break;
				case LIST_BACKUP:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listConfigDetails(config, backup, level, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listConfigDetails(config, backup, level, page - 1);
							break;
						case 49:
							if (checkPagination(item)) configInv.setInventory(Screen.LIST_FILES);
							break;
					}
					break;

				case LIST_BACKUP_DETAILS:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listConfigDetails(config, backup, level, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listConfigDetails(config, backup, level, page - 1);
							break;
						case 48:
							if (checkPagination(item)) if (level == null || level.equals("")) configInv.listBackup(config, 1);
							else if (level.indexOf(".") > -1) configInv.listConfigDetails(config, backup, level.substring(0, level.lastIndexOf(".")), page);
							else
								configInv.listConfigDetails(config, backup, null, page);
							else {}
							break;
						case 50:
							if (item.getType() == Material.FIREWORK_STAR) {
								Chatable.get().sendMessage(player, "Reverting to backup. Saving...");
								config.setFromBackup(backup);
								configInv.setInventory(Screen.LIST_FILES);
							}
							break;

					}
					break;
				case LIST_BACKUP_LIST:
					switch (slot) {
						case 53:
							if (checkPagination(item)) configInv.listDetails(config, backup, level, type, page + 1);
							break;
						case 45:
							if (checkPagination(item)) configInv.listDetails(config, backup, level, type, page + 1);
							break;
						case 49:
							if (checkPagination(item)) if (level.indexOf(".") > -1) configInv.listConfigDetails(config, backup, level.substring(0, level.lastIndexOf(".")), 1);
							else
								configInv.listConfigDetails(config, backup, null, 1);
							break;
					}
					break;
				default:
			}
			if (configInv.getScreen() == Screen.LIST_DETAILS) {
				if (slot < 36 && item.hasItemMeta()) {
					List<String> lore = item.getItemMeta().getLore();
					if (lore != null) {
						String path = null;
						type = null;
						String value = null;
						for(String s: lore) {
							String l = ChatColor.stripColor(s);
							if (l.startsWith("Path: ")) path = l.replace("Path: ", "");
							if (l.startsWith("Type: ")) type = l.replace("Type: ", "");
							if (l.startsWith("Value: ")) value = l.replace("Value: ", "");
						}
						if (click == ClickType.LEFT) {
							if (type.equals("nested value")) configInv.listConfigDetails(config, null, path, 1);
							else if (type.equals("list")) configInv.listDetails(config, null, path, type, 1);
							else if (type.equals("enum_list")) configInv.listEnumListShow(config, path, type, 1);
							else if (type.equals("boolean")) {
								if (value != null && value.equals("true")) {
									Chatable.get().sendMessage(player, "Set " + path + " to false.");
									configInv.setPath(path, false);
								} else if (value != null) {
									Chatable.get().sendMessage(player, "Set " + path + " to true.");
									configInv.setPath(path, true);
								}
								configInv.listConfigDetails(config, null, level, page);
							} else if (type.equals("integer") || type.equals("double") || type.equals("string")) configInv.openAnvil(path, type);
							else if (type.equals("enum")) configInv.listEnumDetails(config, path, type, 1);
						} else if (click == ClickType.RIGHT) if (type.equals("string")) configInv.openChat(path, type);
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_EDIT)) {
				if (slot < 36 && item.hasItemMeta()) {
					configInv.removeFromList(slot);
					configInv.listDetails(config, null, level, type, page);
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM)) {
				if (slot < 36 && item.hasItemMeta()) {
					List<String> lore = item.getItemMeta().getLore();
					if (lore != null) {
						String value = null;
						for(String s: lore) {
							String l = ChatColor.stripColor(s);
							if (l.startsWith("Value: ")) value = l.replace("Value: ", "");
						}
						if (value != null) {
							configInv.setItemName(value);
							configInv.listEnumDetails(config, level, type, page);
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_BACKUP)) {
				if (slot < 36 && item.hasItemMeta()) {
					List<String> lore = item.getItemMeta().getLore();
					if (lore != null) {
						String num = null;
						for(String s: lore) {
							String l = ChatColor.stripColor(s);
							if (l.startsWith("Num: ")) num = l.replace("Num: ", "");
						}
						if (num != null) {
							backup = new YamlConfigBackup(null, null);
							backup.setFromBackup(EnchantmentSolution.getPlugin().getDb().getBackup(config, Integer.parseInt(num)));
							configInv.listConfigDetails(config, backup, level, 1);
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_BACKUP_DETAILS)) {
				if (slot < 36 && item.hasItemMeta()) {
					List<String> lore = item.getItemMeta().getLore();
					if (lore != null) {
						String path = null;
						type = null;
						for(String s: lore) {
							String l = ChatColor.stripColor(s);
							if (l.startsWith("Path: ")) path = s.replace("Path: ", "");
							if (s.startsWith("Type: ")) type = s.replace("Type: ", "");
						}
						if (click == ClickType.LEFT) if (type.equals("nested value")) configInv.listConfigDetails(config, backup, path, 1);
						else if (type.equals("list") || type.equals("enum_list")) configInv.listDetails(config, backup, path, type, 1);
						else {}
						else {}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM_LIST_SHOW)) {
				if (slot < 36 && item.hasItemMeta()) {
					configInv.removeFromList(slot);
					configInv.listEnumListShow(config, level, type, page);
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM_LIST_EDIT)) if (slot < 36) if (item.hasItemMeta()) {
				List<String> lore = item.getItemMeta().getLore();
				if (lore != null) {
					String value = null;
					for(String s: lore) {
						String l = ChatColor.stripColor(s);
						if (l.startsWith("Value: ")) value = l.replace("Value: ", "");
					}
					if (value != null) {
						configInv.setItemName(value);
						configInv.listEnumListEdit(config, level, type, page);
					}
				}
			}
		}
	}

	public static void setEnchantabilityCalc(EnchantabilityCalc enchantabilityCalc, Player player, Inventory inv,
	Inventory clickedInv, int slot, ClickType click) {
		if (inv.getType() != InventoryType.CHEST) return;
		else {
			ItemStack item = clickedInv.getItem(slot);
			if (item == null) return;
			if (enchantabilityCalc.isEnchantments()) {
				if (slot < 36) {
					enchantabilityCalc.setEnchantability(slot);
					return;
				}
			} else if (enchantabilityCalc.getPage() == 1) {
				String s = null;
				switch (slot) {
					case 21:
						s = "constant";
					case 22:
						if (s == null) s = "modifier";
						if (click == ClickType.LEFT) enchantabilityCalc.openAnvil(s);
						else if (click == ClickType.RIGHT) enchantabilityCalc.openChat(s);
						return;
					case 23:
						enchantabilityCalc.setPage(1);
						enchantabilityCalc.setEnchantments(true);
						enchantabilityCalc.setInventory();
						return;
				}
				if (slot == 23) return;
			}
			switch (slot) {
				case 53:
					if (checkPagination(item)) {
						enchantabilityCalc.setPage(enchantabilityCalc.getPage() + 1);
						enchantabilityCalc.setInventory();
					}
					break;
				case 45:
					if (checkPagination(item)) {
						enchantabilityCalc.setPage(enchantabilityCalc.getPage() - 1);
						if (enchantabilityCalc.getPage() == 0 && enchantabilityCalc.isEnchantments()) {
							enchantabilityCalc.setPage(1);
							enchantabilityCalc.setEnchantments(false);
						}
						enchantabilityCalc.setInventory();
					}
					break;
			}
		}
	}

	public static void setRPGInventory(RPGInventory rpgInventory, Player player, Inventory inv, Inventory clickedInv, int slot, ClickType click) {
		if (inv.getType() != InventoryType.CHEST) return;
		ItemStack item = clickedInv.getItem(slot);
		if (item == null) return;
		switch (slot) {
			case 53:
				if (checkPagination(item)) {
					rpgInventory.setPage(rpgInventory.getPage() + 1);
					rpgInventory.setInventory();
				}
				break;
			case 45:
				if (checkPagination(item)) {
					if (rpgInventory.getPage() > 1) rpgInventory.setPage(rpgInventory.getPage() - 1);
					rpgInventory.setInventory();
				}
				break;
		}
		switch (rpgInventory.getScreen()) {
			case LIST:
				if (rpgInventory.isEnchantment(slot)) rpgInventory.setEnchantment(slot);
				break;
			case ENCHANTMENT:
				if (slot == 0) rpgInventory.setList();
				if (rpgInventory.isEnchantment(slot) && item.getType() == Material.BOOK) {
					rpgInventory.buyEnchantmentLevel(slot);
					rpgInventory.setInventory();
				}
				break;
			case CONFIRM:
				if (slot == 2) rpgInventory.cancel();
				else if (slot == 6) rpgInventory.buy();
		}
	}

	public static void setMinigameInventory(Minigame minigameInventory, Player player, Inventory inv, Inventory clickedInv, int slot, ClickType click) {
		if (inv.getType() != InventoryType.CHEST) return;
		ItemStack item = clickedInv.getItem(slot);
		if (item == null) return;
		if (slot == 45 && checkPagination(item)) {
			minigameInventory.setPage(minigameInventory.getPage() - 1);
			minigameInventory.setInventory();
			return;
		}
		if (slot == 53 && checkPagination(item)) {
			minigameInventory.setPage(minigameInventory.getPage() + 1);
			minigameInventory.setInventory();
			return;
		}
		switch (minigameInventory.getScreen()) {
			case FAST:
				if (item.getType() != Material.BLACK_STAINED_GLASS_PANE) minigameInventory.addFastEnchantment(slot);
				break;
			case MONDAYS:
				if (item.getType() != Material.BLACK_STAINED_GLASS_PANE) minigameInventory.addMondaysEnchantment(slot);
				break;
			case CUSTOM:
				if (item.getType() != Material.BLACK_STAINED_GLASS_PANE) minigameInventory.addCustomEnchantment(slot);
				break;
			default:
				break;
		}
		minigameInventory.setInventory();
	}

	private static boolean checkPagination(ItemStack item) {
		return item.getType() == Material.ARROW;
	}
}
