package org.ctp.enchantmentsolution.inventory;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
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
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

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
		Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.getMessage(getCodes(), "calc.name"));
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
				HashMap<String, Object> enchCodes = getCodes();
				enchCodes.put("%name%", enchant.getDisplayName());
				enchantmentMeta.setDisplayName(ChatUtils.getMessage(enchCodes, "calc.enchantments"));
				HashMap<String, Object> enchLoreCodes = getCodes();
				enchLoreCodes.put("%constant%", enchant.enchantability(0));
				enchLoreCodes.put("%modifier%", enchant.enchantability(1) - enchant.enchantability(0));
				enchantmentMeta.setLore(ChatUtils.getMessages(enchLoreCodes, "calc.enchantments_lore"));
				enchantment.setItemMeta(enchantmentMeta);
				inv.setItem(i, enchantment);
			}

			ItemStack goBack = new ItemStack(Material.ARROW);
			ItemMeta goBackMeta = goBack.getItemMeta();
			goBackMeta.setDisplayName(page == 1 ? ChatUtils.getMessage(getCodes(), "calc.pagination.go_back")
			: ChatUtils.getMessage(getCodes(), "calc.pagination.previous_page"));
			goBack.setItemMeta(goBackMeta);
			inv.setItem(45, goBack);

			if (PAGING * page <= enchantments.size()) {
				ItemStack next = new ItemStack(Material.ARROW);
				ItemMeta nextMeta = next.getItemMeta();
				nextMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.pagination.next_page"));
				next.setItemMeta(nextMeta);
				inv.setItem(53, next);
			}
			return;
		}

		if (page == 1) {
			ItemStack constant = new ItemStack(Material.APPLE);
			ItemMeta constantMeta = constant.getItemMeta();
			HashMap<String, Object> constantCodes = getCodes();
			constantCodes.put("%constant%", enchantabilityConstant);
			constantMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.info.constant"));
			constantMeta.setLore(ChatUtils.getMessages(constantCodes, "calc.info.constant_lore"));
			constant.setItemMeta(constantMeta);
			inv.setItem(21, constant);

			ItemStack modifier = new ItemStack(Material.GOLDEN_APPLE);
			ItemMeta modifierMeta = modifier.getItemMeta();
			HashMap<String, Object> modifierCodes = getCodes();
			modifierCodes.put("%modifier%", enchantabilityModifier);
			modifierMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.info.modifier"));
			modifierMeta.setLore(ChatUtils.getMessages(modifierCodes, "calc.info.modifier_lore"));
			modifier.setItemMeta(modifierMeta);
			inv.setItem(22, modifier);

			ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta enchantmentMeta = enchantment.getItemMeta();
			enchantmentMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.info.select_enchantment"));
			enchantment.setItemMeta(enchantmentMeta);
			inv.setItem(23, enchantment);

			ItemStack next = new ItemStack(Material.ARROW);
			ItemMeta nextMeta = next.getItemMeta();
			nextMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.info.calculate"));
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
				HashMap<String, Object> enchantabilityCodes = getCodes();
				enchantabilityCodes.put("%resource%", value.getEnchantability());
				enchantabilityCodes.put("%max_level%", enchantability[0]);
				enchantabilityCodes.put("%level%", (ConfigString.LEVEL_FIFTY.getBoolean() ? "50: " : "30: "));
				enchantabilityCodes.put("%max_enchantability%", enchantability[1]);
				itemMeta.setLore(ChatUtils.getMessages(enchantabilityCodes, "calc.enchantability_lore"));
				item.setItemMeta(itemMeta);
				inv.setItem(slots[slot], item);
				slot++;
			}

			ItemStack goBack = new ItemStack(Material.ARROW);
			ItemMeta goBackMeta = goBack.getItemMeta();
			goBackMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.pagination.go_back"));
			goBack.setItemMeta(goBackMeta);
			inv.setItem(45, goBack);

			ItemStack next = new ItemStack(Material.ARROW);
			ItemMeta nextMeta = next.getItemMeta();
			nextMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.pagination.enchantability_by_level"));
			next.setItemMeta(nextMeta);
			inv.setItem(53, next);
		} else {
			// Show the enchantability for each level with the given constant/modifier

			if (enchantabilityModifier <= 0) {
				ItemStack level = new ItemStack(Material.PAPER);
				ItemMeta levelMeta = level.getItemMeta();
				HashMap<String, Object> levelCodes = getCodes();
				levelCodes.put("%level%", 1);
				levelMeta.setDisplayName(ChatUtils.getMessage(levelCodes, "calc.by_level"));
				HashMap<String, Object> levelLoreCodes = getCodes();
				levelLoreCodes.put("%constant%", enchantabilityConstant);
				levelMeta.setLore(ChatUtils.getMessages(levelLoreCodes, "calc.by_level_lore"));
				level.setItemMeta(levelMeta);
				inv.setItem(0, level);

				ItemStack goBack = new ItemStack(Material.ARROW);
				ItemMeta goBackMeta = goBack.getItemMeta();
				goBackMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.pagination.go_back"));
				goBack.setItemMeta(goBackMeta);
				inv.setItem(45, goBack);
			} else {
				for(int i = 0; i < PAGING; i++) {
					int level = PAGING * (page - 3) + i + 1;
					ItemStack levelItem = new ItemStack(Material.PAPER);
					ItemMeta levelItemMeta = levelItem.getItemMeta();
					HashMap<String, Object> levelCodes = getCodes();
					levelCodes.put("%level%", level);
					levelItemMeta.setDisplayName(ChatUtils.getMessage(levelCodes, "calc.by_level"));
					HashMap<String, Object> levelLoreCodes = getCodes();
					levelLoreCodes.put("%constant%", (enchantabilityModifier * level + enchantabilityConstant));
					levelItemMeta.setLore(ChatUtils.getMessages(levelLoreCodes, "calc.by_level_lore"));
					levelItem.setItemMeta(levelItemMeta);
					inv.setItem(i, levelItem);
				}

				ItemStack goBack = new ItemStack(Material.ARROW);
				ItemMeta goBackMeta = goBack.getItemMeta();
				goBackMeta.setDisplayName(page == 3 ? ChatUtils.getMessage(getCodes(), "calc.pagination.go_back")
				: ChatUtils.getMessage(getCodes(), "calc.pagination.previous_page"));
				goBack.setItemMeta(goBackMeta);
				inv.setItem(45, goBack);

				ItemStack next = new ItemStack(Material.ARROW);
				ItemMeta nextMeta = next.getItemMeta();
				nextMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "calc.pagination.next_page"));
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
			ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "calc.invalid_number"));
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
		ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "calc.enter_string"));
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
		WOODEN_TOOLS(15, Material.WOODEN_PICKAXE, "calc.enchantability_values.wooden_tools"),
		STONE_TOOLS(5, Material.STONE_PICKAXE, "calc.enchantability_values.stone_tools"),
		GOLDEN_TOOLS(22, Material.GOLDEN_PICKAXE, "calc.enchantability_values.golden_tools"),
		IRON_TOOLS(10, Material.IRON_PICKAXE, "calc.enchantability_values.iron_tools"),
		DIAMOND_TOOLS(14, Material.DIAMOND_PICKAXE, "calc.enchantability_values.diamond_tools"),
		LEATHER_ARMOR(15, Material.LEATHER_CHESTPLATE, "calc.enchantability_values.leather_armor"),
		CHAINMAIL_ARMOR(5, Material.CHAINMAIL_CHESTPLATE, "calc.enchantability_values.chainmail_armor"),
		GOLDEN_ARMOR(22, Material.GOLDEN_CHESTPLATE, "calc.enchantability_values.golden_armor"),
		IRON_ARMOR(10, Material.IRON_CHESTPLATE, "calc.enchantability_values.iron_armor"),
		DIAMOND_ARMOR(14, Material.DIAMOND_CHESTPLATE, "calc.enchantability_values.diamond_armor"),
		OTHER(1, Material.BOOK, "calc.enchantability_values.other");

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
			return ChatUtils.getMessage(ChatUtils.getCodes(), display);
		}
	}

	public int[] getEnchantabilityCalc(int enchantability, int constant, int modifier) {
		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + enchantability_2;

		int level = 1;

		int max = (ConfigString.LEVEL_FIFTY.getBoolean() ? 50 : 30) + rand_enchantability;
		if (ConfigUtils.getAdvancedBoolean(ConfigString.USE_LAPIS_MODIFIERS, ConfigString.LEVEL_FIFTY.getBoolean())) {
			double lapisConstant = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_CONSTANT,
			ConfigString.LEVEL_FIFTY.getBoolean() ? -1 : 0);
			double lapisMultiplier = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_MULTIPLIER,
			ConfigString.LEVEL_FIFTY.getBoolean() ? 2 : 0);
			rand_enchantability += ((ConfigString.LEVEL_FIFTY.getBoolean() ? 6 : 3) + lapisConstant) * lapisMultiplier;
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
