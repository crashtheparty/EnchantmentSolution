package org.ctp.enchantmentsolution.utils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.inventory.Grindstone;
import org.ctp.enchantmentsolution.inventory.ConfigInventory.Screen;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.config.YamlInfo;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class InventoryClickUtils {

	public static void setEnchantmentTableDetails(EnchantmentTable table, Player player, Inventory inv, Inventory clickedInv, int slot) {
		if (!(inv.getType().equals(InventoryType.CHEST))) {
			ItemStack item = clickedInv.getItem(slot);
			if (Enchantments.isEnchantable(item)) {
				ItemStack replace = new ItemStack(Material.AIR);
				if(item.getAmount() > 1){
					replace = item.clone();
					replace.setAmount(replace.getAmount() - 1);
					item.setAmount(1);
				}
				if (table.addItem(item)) {
					table.setInventory();
					player.getInventory().setItem(slot, replace);
				}
			} else if(item != null && item.getType().equals(Material.LAPIS_LAZULI)) {
				player.getInventory().setItem(slot, table.addToLapisStack(item));
				table.setInventory();
			}
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (table.getItems().contains(item)) {
				if (table.removeItem(item, slot)) {
					table.setInventory();
					ItemUtils.giveItemToPlayer(player, item, player.getLocation());
				}
			}else if(slot > 17 && slot % 9 >= 3 && slot % 9 <= 8 && item != null && item.getType() != Material.RED_STAINED_GLASS_PANE && item.getType() != Material.BLACK_STAINED_GLASS_PANE){
				int itemSlot = (slot - 18) / 9;
				int itemLevel = (slot % 9) - 3;
				table.enchantItem(itemSlot, itemLevel);
			} else if (slot == 10) {
				ItemStack lapisStack = table.removeFromLapisStack();
				if(lapisStack != null) {
					ItemUtils.giveItemToPlayer(player, lapisStack, player.getLocation());
				}
				table.setInventory();
			}
		}
	}
	
	public static void setAnvilDetails(Anvil anvil, Player player, Inventory inv, Inventory clickedInv, int slot) {
		if (!(inv.getType().equals(InventoryType.CHEST))) {
			if(inv.getType().equals(InventoryType.ANVIL)) return;
			ItemStack item = clickedInv.getItem(slot);
			if(item == null || item.getType().equals(Material.AIR)) {
				return;
			}
			ItemStack replace = new ItemStack(Material.AIR);
			if(item.getAmount() > 1 && item.getType() == Material.BOOK){
				replace = item.clone();
				replace.setAmount(replace.getAmount() - 1);
				item.setAmount(1);
			}
			if (anvil.addItem(item)) {
				anvil.setInventory();
				player.getInventory().setItem(slot, replace);
			}
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (slot == 4) {
				if(item.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
					Anvil_GUI_NMS.createAnvil(player, anvil);
				}
			} else if (slot == 16) {
				anvil.combine();
				anvil.setInventory();
			} else if ((slot == 31 || slot == 30) && item.getType().equals(Material.ANVIL)) {
				anvil.close(false);
				AnvilUtils.addLegacyAnvil(player);
				ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "anvil.legacy-gui-open"));
			} else if ((slot == 31 || slot == 32) && item.getType().equals(Material.SMOOTH_STONE)) {
				anvil.close(false);
				Grindstone stone = new Grindstone(player, anvil.getBlock());
				EnchantmentSolution.getPlugin().addInventory(stone);
				stone.setInventory();
			} else if (anvil.getItems().contains(item)) {
				if (anvil.removeItem(slot)) {
					anvil.setInventory();
					ItemUtils.giveItemToPlayer(player, item, player.getLocation());
				}
			}
		}
	}
	
	public static void setGrindstoneDetails(Grindstone stone, Player player, Inventory inv, Inventory clickedInv, int slot) {
		if (!(inv.getType().equals(InventoryType.CHEST))) {
			ItemStack item = clickedInv.getItem(slot);
			if(item == null || item.getType().equals(Material.AIR)) {
				return;
			}
			ItemStack replace = new ItemStack(Material.AIR);
			if(item.getAmount() > 1){
				replace = item.clone();
				replace.setAmount(replace.getAmount() - 1);
				item.setAmount(1);
			}
			if (stone.addItem(item)) {
				stone.setInventory();
				player.getInventory().setItem(slot, replace);
			}
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if (slot == 7) {
				stone.combine();
				stone.setInventory();
			} else if (slot == 31 && item.getType().equals(Material.ANVIL)) {
				stone.close(false);
				Anvil anvil = new Anvil(player, stone.getBlock());
				EnchantmentSolution.getPlugin().addInventory(anvil);
				anvil.setInventory();
			} else if (stone.getItems().contains(item)) {
				if (stone.removeItem(slot)) {
					stone.setInventory();
					ItemUtils.giveItemToPlayer(player, item, player.getLocation());
				}
			}
		}
	}
	
	public static void setConfigInventoryDetails(ConfigInventory configInv, Player player, Inventory inv, Inventory clickedInv, int slot, ClickType click) {
		if (!(inv.getType().equals(InventoryType.CHEST))) {
			return;
		} else {
			ItemStack item = clickedInv.getItem(slot);
			if(item == null) return;
			ConfigFiles files = EnchantmentSolution.getPlugin().getConfigFiles();
			switch(configInv.getScreen()) {
			case LIST_FILES:
				switch(slot) {
				case 2:
					configInv.listConfigDetails(files.getDefaultConfig());
					break;
				case 3:
					configInv.listConfigDetails(files.getFishingConfig());
					break;
				case 4:
					configInv.listConfigDetails(files.getLanguageFile());
					break;
				case 5:
					configInv.listConfigDetails(files.getEnchantmentConfig());
					break;
				case 6:
					configInv.listConfigDetails(files.getEnchantmentAdvancedConfig());
					break;
				case 21:
					if(!item.getType().equals(Material.BARRIER)) {
						configInv.saveAll();
					}
					break;
				case 23:
					if(!item.getType().equals(Material.BARRIER)) {
						configInv.revert();
					}
					break;
				}
				break;
			case LIST_DETAILS:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listConfigDetails(configInv.getConfig(), configInv.getLevel(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listConfigDetails(configInv.getConfig(), configInv.getLevel(), configInv.getPage() - 1);
					}
					break;
				case 48:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level == null || level.equals("")) {
							configInv.setInventory(Screen.LIST_FILES);
						} else {
							if(level.indexOf(".") > -1) {
								configInv.listConfigDetails(configInv.getConfig(), level.substring(0, level.lastIndexOf(".")), configInv.getPage());
							} else {
								configInv.listConfigDetails(configInv.getConfig(), null, configInv.getPage());
							}
						}
					}
					break;
				case 50:
					if(item.getType().equals(Material.FIREWORK_STAR)) {
						configInv.listBackup(configInv.getConfig(), 1);
					}
					break;
				}
				break;
			case LIST_EDIT:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() - 1);
					}
					break;
				case 48:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level.indexOf(".") > -1) {
							configInv.listConfigDetails(configInv.getConfig(), level.substring(0, level.lastIndexOf(".")));
						} else {
							configInv.listConfigDetails(configInv.getConfig(), null);
						}
					}
					break;
				case 50:
					if(item.getType().equals(Material.NAME_TAG)) {
						if(click.equals(ClickType.LEFT)) {
							configInv.openAnvil(configInv.getLevel(), configInv.getType());
						} else if (click.equals(ClickType.RIGHT)) {
							configInv.openChat(configInv.getLevel(), configInv.getType());
						}
					}
					break;
				}
				break;
			case LIST_ENUM:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() - 1);
					}
					break;
				case 49:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level.indexOf(".") > -1) {
							configInv.listConfigDetails(configInv.getConfig(), level.substring(0, level.lastIndexOf(".")));
						} else {
							configInv.listConfigDetails(configInv.getConfig(), null);
						}
					}
					break;
				}
				break;
			case LIST_ENUM_LIST_SHOW:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumListShow(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumListShow(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() - 1);
					}
					break;
				case 48:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level.indexOf(".") > -1) {
							configInv.listConfigDetails(configInv.getConfig(), level.substring(0, level.lastIndexOf(".")));
						} else {
							configInv.listConfigDetails(configInv.getConfig(), null);
						}
					}
					break;
				case 50:
					if(item.getType().equals(Material.NAME_TAG)) {
						configInv.listEnumListEdit(configInv.getConfig(), configInv.getLevel(), configInv.getType(), 1);
					}
					break;
				}
				break;
			case LIST_ENUM_LIST_EDIT:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumListEdit(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumListEdit(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage() - 1);
					}
					break;
				case 49:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listEnumListShow(configInv.getConfig(), configInv.getLevel(), configInv.getType(), 1);
					}
					break;
				}
				break;
			case LIST_BACKUP:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getPage() - 1);
					}
					break;
				case 49:
					if(item.getType().equals(Material.ARROW)) {
						configInv.setInventory(Screen.LIST_FILES);
					}
					break;
				}
				break;

			case LIST_BACKUP_DETAILS:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getPage() - 1);
					}
					break;
				case 48:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level == null || level.equals("")) {
							configInv.listBackup(configInv.getConfig(), 1);
						} else {
							if(level.indexOf(".") > -1) {
								configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), level.substring(0, level.lastIndexOf(".")), configInv.getPage());
							} else {
								configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), null, configInv.getPage());
							}
						}
					}
					break;
				case 50:
					if(item.getType().equals(Material.FIREWORK_STAR)) {
						ChatUtils.sendMessage(player, "Reverting to backup. Saving...");
						configInv.getConfig().setFromBackup(configInv.getBackup());
						configInv.setInventory(Screen.LIST_FILES);
					}
					break;
					
				}
				break;
			case LIST_BACKUP_LIST:
				switch(slot) {
				case 53:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 45:
					if(item.getType().equals(Material.ARROW)) {
						configInv.listBackupDetails(configInv.getConfig(), configInv.getBackup(), configInv.getLevel(), configInv.getType(), configInv.getPage() + 1);
					}
					break;
				case 49:
					if(item.getType().equals(Material.ARROW)) {
						String level = configInv.getLevel();
						if(level.indexOf(".") > -1) {
							configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), level.substring(0, level.lastIndexOf(".")), 1);
						} else {
							configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), null, 1);
						}
					}
					break;
				}
				break;
			default:
			}
			if(configInv.getScreen().equals(Screen.LIST_DETAILS)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						List<String> lore = item.getItemMeta().getLore();
						if(lore != null) {
							String path = null;
							String type = null;
							String value = null;
							for(String s : lore) {
								if(s.startsWith(ChatColor.GRAY + "Path: " + ChatColor.WHITE)) {
									path = s.replace(ChatColor.GRAY + "Path: " + ChatColor.WHITE, "");
								}
								if(s.startsWith(ChatColor.GRAY + "Type: " + ChatColor.WHITE)) {
									type = s.replace(ChatColor.GRAY + "Type: " + ChatColor.WHITE, "");
								}
								if(s.startsWith(ChatColor.GRAY + "Value: " + ChatColor.WHITE)) {
									value = s.replace(ChatColor.GRAY + "Value: " + ChatColor.WHITE, "");
								}
							}
							if(click.equals(ClickType.LEFT)) {
								if(type.equals("nested value")) {
									configInv.listConfigDetails(configInv.getConfig(), path, 1);
								} else if (type.equals("list")) {
									configInv.listDetails(configInv.getConfig(), path, type, 1);
								} else if (type.equals("enum_list")) {
									configInv.listEnumListShow(configInv.getConfig(), path, type, 1);
								} else if (type.equals("boolean")) {
									if(value != null && value.equals("true")) {
										ChatUtils.sendMessage(player, "Set " + path + " to false.");
										configInv.setPath(path, false);
									} else if (value != null) {
										ChatUtils.sendMessage(player, "Set " + path + " to true.");
										configInv.setPath(path, true);
									}
									configInv.listConfigDetails(configInv.getConfig(), configInv.getLevel(), configInv.getPage());
								} else if (type.equals("integer") || type.equals("double") || type.equals("string")) {
									configInv.openAnvil(path, type);
								} else if (type.equals("enum")) {
									configInv.listEnumDetails(configInv.getConfig(), path, type, 1);
								}
							} else if (click.equals(ClickType.RIGHT)) {
								if (type.equals("string")) {
									configInv.openChat(path, type);
								}
							}
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_EDIT)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						configInv.removeFromList(slot);
						configInv.listDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage());
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						List<String> lore = item.getItemMeta().getLore();
						if(lore != null) {
							String value = null;
							for(String s : lore) {
								if(s.startsWith(ChatColor.GRAY + "Value: " + ChatColor.WHITE)) {
									value = s.replace(ChatColor.GRAY + "Value: " + ChatColor.WHITE, "");
								}
							}
							if(value != null) {
								configInv.setItemName(value);
								configInv.listEnumDetails(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage());
							}
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_BACKUP)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						List<String> lore = item.getItemMeta().getLore();
						if(lore != null) {
							String num = null;
							for(String s : lore) {
								if(s.startsWith(ChatColor.WHITE + "Num: " + ChatColor.GRAY)) {
									num = s.replace(ChatColor.WHITE + "Num: " + ChatColor.GRAY, "");
								}
							}
							if(num != null) {
								YamlConfigBackup backup = new YamlConfigBackup(null, null);
								List<YamlInfo> backupInfo = EnchantmentSolution.getPlugin().getDb().getBackup(configInv.getConfig(), Integer.parseInt(num));
								backup.setFromBackup(backupInfo);
								configInv.listBackupConfigDetails(configInv.getConfig(), backup, configInv.getLevel(), 1);
							}
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_BACKUP_DETAILS)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						List<String> lore = item.getItemMeta().getLore();
						if(lore != null) {
							String path = null;
							String type = null;
							for(String s : lore) {
								if(s.startsWith(ChatColor.GRAY + "Path: " + ChatColor.WHITE)) {
									path = s.replace(ChatColor.GRAY + "Path: " + ChatColor.WHITE, "");
								}
								if(s.startsWith(ChatColor.GRAY + "Type: " + ChatColor.WHITE)) {
									type = s.replace(ChatColor.GRAY + "Type: " + ChatColor.WHITE, "");
								}
							}
							if(click.equals(ClickType.LEFT)) {
								if(type.equals("nested value")) {
									configInv.listBackupConfigDetails(configInv.getConfig(), configInv.getBackup(), path, 1);
								} else if (type.equals("list") || type.equals("enum_list")) {
									configInv.listBackupDetails(configInv.getConfig(), configInv.getBackup(), path, type, 1);
								}
							}
						}
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM_LIST_SHOW)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						configInv.removeFromList(slot);
						configInv.listEnumListShow(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage());
					}
				}
			} else if (configInv.getScreen().equals(Screen.LIST_ENUM_LIST_EDIT)) {
				if(slot < 36) {
					if(item.hasItemMeta()) {
						List<String> lore = item.getItemMeta().getLore();
						if(lore != null) {
							String value = null;
							for(String s : lore) {
								if(s.startsWith(ChatColor.GRAY + "Value: " + ChatColor.WHITE)) {
									value = s.replace(ChatColor.GRAY + "Value: " + ChatColor.WHITE, "");
								}
							}
							if(value != null) {
								configInv.setItemName(value);
								configInv.listEnumListEdit(configInv.getConfig(), configInv.getLevel(), configInv.getType(), configInv.getPage());
							}
						}
					}
				}
			}
		}
	}
}
