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
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Configuration;
import org.ctp.enchantmentsolution.utils.config.EnchantmentsConfiguration;

public class EnchantabilityCalc implements InventoryData {

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

		if (page < 1) {
			page = 1;
		}

		if (enchantments) {
			List<CustomEnchantment> enchantments = RegisterEnchantments.getRegisteredEnchantmentsAlphabetical();
			for(int i = 0; i < PAGING; i++) {
				int num = PAGING * (page - 1) + i;
				if (enchantments.size() <= num) {
					continue;
				}
				CustomEnchantment enchant = enchantments.get(num);

				ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta enchantmentMeta = enchantment.getItemMeta();
				enchantmentMeta.setDisplayName(ChatColor.BLUE + enchant.getDisplayName());
				enchantmentMeta.setLore(Arrays.asList(
						ChatColor.GOLD + "Enchantability Constant: " + ChatColor.DARK_AQUA + enchant.enchantability(0),
						ChatColor.GOLD + "Enchantability Modifier: " + ChatColor.DARK_AQUA
								+ (enchant.enchantability(1) - enchant.enchantability(0))));
				enchantment.setItemMeta(enchantmentMeta);
				inv.setItem(i, enchantment);
			}

			ItemStack goBack = new ItemStack(Material.ARROW);
			ItemMeta goBackMeta = goBack.getItemMeta();
			goBackMeta.setDisplayName(page == 1 ? ChatColor.BLUE + "Go Back" : ChatColor.BLUE + "Previous Page");
			goBack.setItemMeta(goBackMeta);
			inv.setItem(45, goBack);

			if (PAGING * page <= enchantments.size()) {
				ItemStack next = new ItemStack(Material.ARROW);
				ItemMeta nextMeta = next.getItemMeta();
				nextMeta.setDisplayName(ChatColor.BLUE + "Next Page");
				next.setItemMeta(nextMeta);
				inv.setItem(53, next);
			}
			return;
		}

		if (page == 1) {
			ItemStack constant = new ItemStack(Material.APPLE);
			ItemMeta constantMeta = constant.getItemMeta();
			constantMeta.setDisplayName(ChatColor.BLUE + "Enchantability Constant");
			constantMeta.setLore(
					Arrays.asList(ChatColor.GOLD + "Current Value: " + ChatColor.DARK_AQUA + enchantabilityConstant,
							ChatColor.GRAY + "Left click to edit with anvil.",
							ChatColor.GRAY + "Right click to edit in chat."));
			constant.setItemMeta(constantMeta);
			inv.setItem(21, constant);

			ItemStack modifier = new ItemStack(Material.GOLDEN_APPLE);
			ItemMeta modifierMeta = modifier.getItemMeta();
			modifierMeta.setDisplayName(ChatColor.BLUE + "Enchantability Modifier");
			modifierMeta.setLore(
					Arrays.asList(ChatColor.GOLD + "Current Value: " + ChatColor.DARK_AQUA + enchantabilityModifier,
							ChatColor.GRAY + "Left click to edit with anvil.",
							ChatColor.GRAY + "Right click to edit in chat."));
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
			int[] slots = new int[] { 11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 31 };
			int slot = 0;
			for(EnchantabilityValues value: EnchantabilityValues.values()) {
				int[] enchantability = getEnchantabilityCalc(value.getEnchantability(), enchantabilityConstant,
						enchantabilityModifier);
				ItemStack item = new ItemStack(value.getMaterial());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(value.getDisplay());
				itemMeta.setLore(Arrays.asList(
						ChatColor.GOLD + "Resource Enchantability: " + ChatColor.DARK_AQUA + value.getEnchantability(),
						ChatColor.GOLD + "Max Level From Enchantability: " + ChatColor.DARK_AQUA + enchantability[0],
						ChatColor.GOLD + "Max Enchantability at Level " + (ConfigUtils.isLevel50() ? "50: " : "30: ")
								+ ChatColor.DARK_AQUA + enchantability[1]));
				item.setItemMeta(itemMeta);
				inv.setItem(slots[slot], item);
				slot++;
			}

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
				levelMeta.setLore(Arrays.asList(ChatColor.GOLD + "Required Enchantability: " + ChatColor.DARK_AQUA
						+ enchantabilityConstant + "+"));
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
					levelItemMeta.setLore(Arrays.asList(ChatColor.GOLD + "Required Enchantability: "
							+ ChatColor.DARK_AQUA + (enchantabilityModifier * level + enchantabilityConstant) + "+"));
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
		if (!external) {
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
		if (type.equals("constant")) {
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
		if (inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else {
			if (inv.getSize() == inventory.getSize()) {
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
		if (opening) {
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
		List<CustomEnchantment> enchantments = RegisterEnchantments.getRegisteredEnchantmentsAlphabetical();
		int num = PAGING * (page - 1) + slot;
		if (num < enchantments.size()) {
			CustomEnchantment enchantment = enchantments.get(num);
			enchantabilityConstant = enchantment.enchantability(0);
			enchantabilityModifier = enchantment.enchantability(1) - enchantment.enchantability(0);
		}
		this.enchantments = false;
		page = 1;
		setInventory();
	}

	public void openAnvil(String type) {
		this.type = type;
		opening = true;
		inventory = null;

		Anvil_GUI_NMS.createAnvil(player, this);
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

	public static enum EnchantabilityValues {
		WOODEN_TOOLS(15, Material.WOODEN_PICKAXE, ChatColor.BLUE + "Wooden Tools and Weapons"), STONE_TOOLS(5,
				Material.STONE_PICKAXE, ChatColor.BLUE + "Stone Tools and Weapons"), GOLDEN_TOOLS(22,
						Material.GOLDEN_PICKAXE, ChatColor.BLUE + "Golden Tools and Weapons"), IRON_TOOLS(10,
								Material.IRON_PICKAXE, ChatColor.BLUE + "Iron Tools and Weapons"), DIAMOND_TOOLS(14,
										Material.DIAMOND_PICKAXE,
										ChatColor.BLUE + "Diamond Tools and Weapons"), LEATHER_ARMOR(15,
												Material.LEATHER_CHESTPLATE,
												ChatColor.BLUE + "Leather Armor"), CHAINMAIL_ARMOR(5,
														Material.CHAINMAIL_CHESTPLATE,
														ChatColor.BLUE + "Chainmail Armor"), GOLDEN_ARMOR(22,
																Material.GOLDEN_CHESTPLATE,
																ChatColor.BLUE + "Golden Armor"), IRON_ARMOR(10,
																		Material.IRON_CHESTPLATE,
																		ChatColor.BLUE + "Iron Armor"), DIAMOND_ARMOR(
																				14, Material.DIAMOND_CHESTPLATE,
																				ChatColor.BLUE
																						+ "Diamond Armor"), OTHER(1,
																								Material.BOOK,
																								ChatColor.BLUE
																										+ "Books and Other Items");

		private int enchantability;
		private Material material;
		private String display;

		EnchantabilityValues(int enchantability, Material material, String display) {
			this.enchantability = enchantability;
			this.material = material;
			this.display = display;
		}

		public int getEnchantability() {
			return enchantability;
		}

		public Material getMaterial() {
			return material;
		}

		public String getDisplay() {
			return display;
		}
	}

	public int[] getEnchantabilityCalc(int enchantability, int constant, int modifier) {
		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + enchantability_2;

		int level = 1;

		int max = ((ConfigUtils.isLevel50() ? 50 : 30) + rand_enchantability);
		Class<? extends Configuration> enchantmentsClazz = EnchantmentsConfiguration.class;
		if (ConfigUtils.getBoolean(enchantmentsClazz, "use_lapis_modifier")) {
			rand_enchantability += ((ConfigUtils.isLevel50() ? 6 : 3)
					- ConfigUtils.getDouble(enchantmentsClazz, "lapis_modifiers.constant"))
					* ConfigUtils.getDouble(enchantmentsClazz, "lapis_modifiers.modifier");
		}
		max = (int) (max * 1.15 + 0.5);

		if (modifier <= 0) {
			return new int[] { 1, max };
		}

		int maxEnchantability = modifier * level + constant;
		while (max > maxEnchantability + modifier) {
			level++;
			maxEnchantability = modifier * level + constant;
		}

		return new int[] { level--, max };
	}

}
