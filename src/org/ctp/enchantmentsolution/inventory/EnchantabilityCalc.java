package org.ctp.enchantmentsolution.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class EnchantabilityCalc implements InventoryData{

	private Player player;
	private Inventory inventory;
	private final int PAGING = 36;
	private String type = null;
	private boolean chat = false, opening = false, enchantments = false;
	private int page = 0, enchantabilityConstant, enchantabilityModifier;
	
	public EnchantabilityCalc(Player player) {
		this.player = player;
	}
	
	public void setInventory() {
		// TODO: update the inventory to use language.yml
		Inventory inv = Bukkit.createInventory(null, 54, "Enchantability Calculator");
		inv = open(inv);
		
		if(page < 1) page = 1;
		
		if(enchantments) {
			List<CustomEnchantment> enchantments = Enchantments.getEnchantmentsAlphabetical();
			for(int i = 0; i < PAGING; i++) {
				int num = PAGING * (page - 1) + i;
				if(enchantments.size() <= num) continue;
				CustomEnchantment enchant = enchantments.get(num);
				
				ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta enchantmentMeta = enchantment.getItemMeta();
				enchantmentMeta.setDisplayName(ChatColor.BLUE + enchant.getDisplayName());
				enchantmentMeta.setLore(Arrays.asList(ChatColor.GOLD + "Enchantability Constant: " + ChatColor.DARK_AQUA + enchant.enchantability(0),
						ChatColor.GOLD + "Enchantability Modifier: " + ChatColor.DARK_AQUA + (enchant.enchantability(1) - enchant.enchantability(0))));
				enchantment.setItemMeta(enchantmentMeta);
				inv.setItem(i, enchantment);
			}
			
			ItemStack goBack = new ItemStack(Material.ARROW);
			ItemMeta goBackMeta = goBack.getItemMeta();
			goBackMeta.setDisplayName(page == 1 ? ChatColor.BLUE + "Go Back" : ChatColor.BLUE + "Previous Page");
			goBack.setItemMeta(goBackMeta);
			inv.setItem(45, goBack);
			
			if(PAGING * page <= enchantments.size()) {
				ItemStack next = new ItemStack(Material.ARROW);
				ItemMeta nextMeta = next.getItemMeta();
				nextMeta.setDisplayName(ChatColor.BLUE + "Next Page");
				next.setItemMeta(nextMeta);
				inv.setItem(53, next);
			}
			return;
		}
		
		if(page == 1) {
			ItemStack constant = new ItemStack(Material.APPLE);
			ItemMeta constantMeta = constant.getItemMeta();
			constantMeta.setDisplayName(ChatColor.BLUE + "Enchantability Constant");
			constantMeta.setLore(Arrays.asList(ChatColor.GOLD + "Current Value: " + ChatColor.DARK_AQUA + enchantabilityConstant, 
					ChatColor.GRAY + "Left click to edit with anvil.", ChatColor.GRAY + "Right click to edit in chat."));
			constant.setItemMeta(constantMeta);
			inv.setItem(21, constant);
			
			ItemStack modifier = new ItemStack(Material.GOLDEN_APPLE);
			ItemMeta modifierMeta = modifier.getItemMeta();
			modifierMeta.setDisplayName(ChatColor.BLUE + "Enchantability Modifier");
			modifierMeta.setLore(Arrays.asList(ChatColor.GOLD + "Current Value: " + ChatColor.DARK_AQUA + enchantabilityModifier, 
					ChatColor.GRAY + "Left click to edit with anvil.", ChatColor.GRAY + "Right click to edit in chat."));
			modifier.setItemMeta(modifierMeta);
			inv.setItem(22, modifier);
			
			ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta enchantmentMeta = enchantment.getItemMeta();
			enchantmentMeta.setDisplayName(ChatColor.BLUE + "Select from Enchantment");
			enchantment.setItemMeta(enchantmentMeta);
			inv.setItem(23, enchantment);
			
			ItemStack next = new ItemStack(Material.ARROW);
			ItemMeta nextMeta = next.getItemMeta();
			nextMeta.setDisplayName(ChatColor.BLUE + "Calculate");
			next.setItemMeta(nextMeta);
			inv.setItem(53, next);
		} else if (page == 2) {
			// Set each type of enchantability with the max level for the given values, and a Back/Next Page format

			int[] enchantability = Enchantments.getEnchantabilityCalc(15, enchantabilityConstant, enchantabilityModifier);
			ItemStack woodenTools = new ItemStack(Material.WOODEN_PICKAXE);
			ItemMeta woodenToolsMeta = woodenTools.getItemMeta();
			woodenToolsMeta.setDisplayName(ChatColor.BLUE + "Wooden Tools and Weapons");
			woodenToolsMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "15", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			woodenTools.setItemMeta(woodenToolsMeta);
			inv.setItem(11, woodenTools);

			enchantability = Enchantments.getEnchantabilityCalc(5, enchantabilityConstant, enchantabilityModifier);
			ItemStack stoneTools = new ItemStack(Material.STONE_PICKAXE);
			ItemMeta stoneToolsMeta = stoneTools.getItemMeta();
			stoneToolsMeta.setDisplayName(ChatColor.BLUE + "Stone Tools and Weapons");
			stoneToolsMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "5", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			stoneTools.setItemMeta(stoneToolsMeta);
			inv.setItem(12, stoneTools);

			enchantability = Enchantments.getEnchantabilityCalc(22, enchantabilityConstant, enchantabilityModifier);
			ItemStack goldenTools = new ItemStack(Material.GOLDEN_PICKAXE);
			ItemMeta goldenToolsMeta = goldenTools.getItemMeta();
			goldenToolsMeta.setDisplayName(ChatColor.BLUE + "Golden Tools and Weapons");
			goldenToolsMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "22", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			goldenTools.setItemMeta(goldenToolsMeta);
			inv.setItem(13, goldenTools);

			enchantability = Enchantments.getEnchantabilityCalc(10, enchantabilityConstant, enchantabilityModifier);
			ItemStack ironTools = new ItemStack(Material.IRON_PICKAXE);
			ItemMeta ironToolsMeta = ironTools.getItemMeta();
			ironToolsMeta.setDisplayName(ChatColor.BLUE + "Iron Tools and Weapons");
			ironToolsMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "10", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			ironTools.setItemMeta(ironToolsMeta);
			inv.setItem(14, ironTools);

			enchantability = Enchantments.getEnchantabilityCalc(14, enchantabilityConstant, enchantabilityModifier);
			ItemStack diamondTools = new ItemStack(Material.DIAMOND_PICKAXE);
			ItemMeta diamondToolsMeta = diamondTools.getItemMeta();
			diamondToolsMeta.setDisplayName(ChatColor.BLUE + "Diamond Tools and Weapons");
			diamondToolsMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "14", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			diamondTools.setItemMeta(diamondToolsMeta);
			inv.setItem(15, diamondTools);

			enchantability = Enchantments.getEnchantabilityCalc(15, enchantabilityConstant, enchantabilityModifier);
			ItemStack leatherArmor = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemMeta leatherArmorMeta = leatherArmor.getItemMeta();
			leatherArmorMeta.setDisplayName(ChatColor.BLUE + "Leather Armor");
			leatherArmorMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "15", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			leatherArmor.setItemMeta(leatherArmorMeta);
			inv.setItem(20, leatherArmor);

			enchantability = Enchantments.getEnchantabilityCalc(25, enchantabilityConstant, enchantabilityModifier);
			ItemStack goldenArmor = new ItemStack(Material.GOLDEN_CHESTPLATE);
			ItemMeta goldenArmorMeta = goldenArmor.getItemMeta();
			goldenArmorMeta.setDisplayName(ChatColor.BLUE + "Golden Armor");
			goldenArmorMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "25", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			goldenArmor.setItemMeta(goldenArmorMeta);
			inv.setItem(21, goldenArmor);

			enchantability = Enchantments.getEnchantabilityCalc(12, enchantabilityConstant, enchantabilityModifier);
			ItemStack chainArmor = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			ItemMeta chainArmorMeta = chainArmor.getItemMeta();
			chainArmorMeta.setDisplayName(ChatColor.BLUE + "Chainmail Armor");
			chainArmorMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "12", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			chainArmor.setItemMeta(chainArmorMeta);
			inv.setItem(22, chainArmor);

			enchantability = Enchantments.getEnchantabilityCalc(9, enchantabilityConstant, enchantabilityModifier);
			ItemStack ironArmor = new ItemStack(Material.IRON_CHESTPLATE);
			ItemMeta ironArmorMeta = ironArmor.getItemMeta();
			ironArmorMeta.setDisplayName(ChatColor.BLUE + "Iron Armor");
			ironArmorMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "9", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			ironArmor.setItemMeta(ironArmorMeta);
			inv.setItem(23, ironArmor);

			enchantability = Enchantments.getEnchantabilityCalc(10, enchantabilityConstant, enchantabilityModifier);
			ItemStack diamondArmor = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemMeta diamondArmorMeta = diamondArmor.getItemMeta();
			diamondArmorMeta.setDisplayName(ChatColor.BLUE + "Diamond Armor");
			diamondArmorMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "10", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			diamondArmor.setItemMeta(diamondArmorMeta);
			inv.setItem(24, diamondArmor);

			enchantability = Enchantments.getEnchantabilityCalc(1, enchantabilityConstant, enchantabilityModifier);
			ItemStack booksMisc = new ItemStack(Material.BOOK);
			ItemMeta booksMiscMeta = booksMisc.getItemMeta();
			booksMiscMeta.setDisplayName(ChatColor.BLUE + "Books and Other Items");
			booksMiscMeta.setLore(Arrays.asList(ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + "1", 
					ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0], 
					ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ") + ChatColor.DARK_AQUA + enchantability[1]));
			booksMisc.setItemMeta(booksMiscMeta);
			inv.setItem(31, booksMisc);
			
			ItemStack goBack = new ItemStack(Material.ARROW);
			ItemMeta goBackMeta = goBack.getItemMeta();
			goBackMeta.setDisplayName(ChatColor.BLUE + "Go Back");
			goBack.setItemMeta(goBackMeta);
			inv.setItem(45, goBack);
			
			ItemStack next = new ItemStack(Material.ARROW);
			ItemMeta nextMeta = next.getItemMeta();
			nextMeta.setDisplayName(ChatColor.BLUE + "View Enchantability By Level");
			next.setItemMeta(nextMeta);
			inv.setItem(53, next);
		} else {
			// Show the enchantability for each level with the given constant/modifier
			
			if (enchantabilityModifier <= 0) {
				ItemStack level = new ItemStack(Material.PAPER);
				ItemMeta levelMeta = level.getItemMeta();
				levelMeta.setDisplayName(ChatColor.BLUE + "Level 1");
				levelMeta.setLore(Arrays.asList(ChatColor.GOLD + "Required Enchantability: " + ChatColor.DARK_AQUA + enchantabilityConstant + "+"));
				level.setItemMeta(levelMeta);
				inv.setItem(0, level);

				ItemStack goBack = new ItemStack(Material.ARROW);
				ItemMeta goBackMeta = goBack.getItemMeta();
				goBackMeta.setDisplayName(ChatColor.BLUE + "Go Back");
				goBack.setItemMeta(goBackMeta);
				inv.setItem(45, goBack);
			} else {
				for(int i = 0; i < PAGING; i++) {
					int level = PAGING * (page - 3) + i + 1;
					ItemStack levelItem = new ItemStack(Material.PAPER);
					ItemMeta levelItemMeta = levelItem.getItemMeta();
					levelItemMeta.setDisplayName(ChatColor.BLUE + "Level " + level);
					levelItemMeta.setLore(Arrays.asList(ChatColor.GOLD + "Required Enchantability: " + ChatColor.DARK_AQUA 
							+ (enchantabilityModifier * level + enchantabilityConstant) + "+"));
					levelItem.setItemMeta(levelItemMeta);
					inv.setItem(i, levelItem);
				}
				
				ItemStack goBack = new ItemStack(Material.ARROW);
				ItemMeta goBackMeta = goBack.getItemMeta();
				goBackMeta.setDisplayName(page == 3 ? ChatColor.BLUE + "Go Back" : ChatColor.BLUE + "Previous Page");
				goBack.setItemMeta(goBackMeta);
				inv.setItem(45, goBack);
				
				ItemStack next = new ItemStack(Material.ARROW);
				ItemMeta nextMeta = next.getItemMeta();
				nextMeta.setDisplayName(ChatColor.BLUE + "Next Page");
				next.setItemMeta(nextMeta);
				inv.setItem(53, next);
			}
		}
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public Block getBlock() {
		return null;
	}

	@Override
	public void close(boolean external) {
		if(!external) {
			player.closeInventory();
		}
		EnchantmentSolution.getPlugin().removeInventory(this);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		setInventory();
	}
	
	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public void setItemName(String name) {
		int num = 0;
		try {
			num = Integer.parseInt(name);
		} catch (NumberFormatException ex) {
			ChatUtils.sendMessage(player, "Invalid number, setting to 0.");
		}
		if(type.equals("constant")) {
			enchantabilityConstant = num;
		} else if (type.equals("modifier")) {
			enchantabilityModifier = num;
		}
		type = null;
		opening = false;
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}
	
	public void openChat(String type) {
		this.type = type;
		inventory = null;
		
		setChat(true);
		ChatUtils.sendMessage(player, "Enter the string message into the chat.");
		player.closeInventory();
	}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if(inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else {
			if(inv.getSize() == inventory.getSize()) {
				inv = player.getOpenInventory().getTopInventory();
				inventory = inv;
			} else {
				inventory = inv;
				player.openInventory(inv);
			}
		}
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		if(opening) {
			opening = false;
		}
		return inv;
	}

	public boolean isEnchantments() {
		return enchantments;
	}

	public void setEnchantments(boolean enchantments) {
		this.enchantments = enchantments;
	}

	public void setEnchantability(int slot) {
		List<CustomEnchantment> enchantments = Enchantments.getEnchantmentsAlphabetical();
		int num = PAGING * (page - 1) + slot;
		if(num < enchantments.size()) {
			CustomEnchantment enchantment = enchantments.get(num);
			enchantabilityConstant = enchantment.enchantability(0);
			enchantabilityModifier = enchantment.enchantability(1) - enchantment.enchantability(0);
		}
		this.enchantments = false;
		this.page = 1;
		setInventory();
	}

	public void openAnvil(String type) {
		this.type = type;
		this.opening = true;
		inventory = null;
		
		Anvil_GUI_NMS.createAnvil(player, this);
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

}
