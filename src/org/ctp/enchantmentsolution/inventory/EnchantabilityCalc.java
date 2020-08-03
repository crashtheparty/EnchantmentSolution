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
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.inventory.Pageable;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantabilityMaterial;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

public class EnchantabilityCalc implements InventoryData, Pageable {

	private Player player;
	private Inventory inventory;
	private final int PAGING = 36;
	private String type = null;
	private boolean chat = false, opening = false, enchantments = false;
	private int page = 0, enchantabilityConstant, enchantabilityModifier;

	public EnchantabilityCalc(Player player) {
		this.player = player;
	}

	@Override
	public void setInventory() {
		Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "calc.name"));
		inv = open(inv);

		if (page < 1) page = 1;

		if (enchantments) {
			List<CustomEnchantment> enchantments = RegisterEnchantments.getRegisteredEnchantmentsAlphabetical();
			for(int i = 0; i < PAGING; i++) {
				int num = PAGING * (page - 1) + i;
				if (enchantments.size() <= num) continue;
				CustomEnchantment enchant = enchantments.get(num);

				ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta enchantmentMeta = enchantment.getItemMeta();
				HashMap<String, Object> enchCodes = getCodes();
				enchCodes.put("%name%", enchant.getDisplayName());
				enchantmentMeta.setDisplayName(Chatable.get().getMessage(enchCodes, "calc.enchantments"));
				HashMap<String, Object> enchLoreCodes = getCodes();
				enchLoreCodes.put("%constant%", enchant.enchantability(0));
				enchLoreCodes.put("%modifier%", enchant.enchantability(1) - enchant.enchantability(0));
				enchantmentMeta.setLore(Chatable.get().getMessages(enchLoreCodes, "calc.enchantments_lore"));
				enchantment.setItemMeta(enchantmentMeta);
				inv.setItem(i, enchantment);
			}

			inv.setItem(45, goBack());

			if (PAGING * page <= enchantments.size()) inv.setItem(53, nextPage());
			return;
		}

		if (page == 1) {
			ItemStack constant = new ItemStack(Material.APPLE);
			ItemMeta constantMeta = constant.getItemMeta();
			HashMap<String, Object> constantCodes = getCodes();
			constantCodes.put("%constant%", enchantabilityConstant);
			constantMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "calc.info.constant"));
			constantMeta.setLore(Chatable.get().getMessages(constantCodes, "calc.info.constant_lore"));
			constant.setItemMeta(constantMeta);
			inv.setItem(21, constant);

			ItemStack modifier = new ItemStack(Material.GOLDEN_APPLE);
			ItemMeta modifierMeta = modifier.getItemMeta();
			HashMap<String, Object> modifierCodes = getCodes();
			modifierCodes.put("%modifier%", enchantabilityModifier);
			modifierMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "calc.info.modifier"));
			modifierMeta.setLore(Chatable.get().getMessages(modifierCodes, "calc.info.modifier_lore"));
			modifier.setItemMeta(modifierMeta);
			inv.setItem(22, modifier);

			ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta enchantmentMeta = enchantment.getItemMeta();
			enchantmentMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "calc.info.select_enchantment"));
			enchantment.setItemMeta(enchantmentMeta);
			inv.setItem(23, enchantment);

			inv.setItem(53, nextPage());
		} else if (page == 2) {
			int[] slots = new int[] { 11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 30, 31, 32 };
			int slot = 0;
			for(EnchantabilityMaterial value: EnchantabilityMaterial.values()) {
				if (value.getMaterial() == null) {
					slot++;
					continue;
				}
				int[] enchantability = getEnchantabilityCalc(value.getEnchantability(), enchantabilityConstant, enchantabilityModifier);
				ItemStack item = new ItemStack(value.getMaterial());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(value.getDisplay());
				HashMap<String, Object> enchantabilityCodes = getCodes();
				enchantabilityCodes.put("%resource%", value.getEnchantability());
				enchantabilityCodes.put("%max_level%", enchantability[0]);
				enchantabilityCodes.put("%level%", ConfigString.LEVEL_FIFTY.getBoolean() ? "50: " : "30: ");
				enchantabilityCodes.put("%max_enchantability%", enchantability[1]);
				itemMeta.setLore(Chatable.get().getMessages(enchantabilityCodes, "calc.enchantability_lore"));
				item.setItemMeta(itemMeta);
				inv.setItem(slots[slot], item);
				slot++;
			}

			inv.setItem(45, goBack());

			inv.setItem(53, pagination("calc.pagination.enchantability_by_level"));
		} else if (enchantabilityModifier <= 0) {
			ItemStack level = new ItemStack(Material.PAPER);
			ItemMeta levelMeta = level.getItemMeta();
			HashMap<String, Object> levelCodes = getCodes();
			levelCodes.put("%level%", 1);
			levelMeta.setDisplayName(Chatable.get().getMessage(levelCodes, "calc.by_level"));
			HashMap<String, Object> levelLoreCodes = getCodes();
			levelLoreCodes.put("%constant%", enchantabilityConstant);
			levelMeta.setLore(Chatable.get().getMessages(levelLoreCodes, "calc.by_level_lore"));
			level.setItemMeta(levelMeta);
			inv.setItem(0, level);

			inv.setItem(45, goBack());
		} else {
			for(int i = 0; i < PAGING; i++) {
				int level = PAGING * (page - 3) + i + 1;
				ItemStack levelItem = new ItemStack(Material.PAPER);
				ItemMeta levelItemMeta = levelItem.getItemMeta();
				HashMap<String, Object> levelCodes = getCodes();
				levelCodes.put("%level%", level);
				levelItemMeta.setDisplayName(Chatable.get().getMessage(levelCodes, "calc.by_level"));
				HashMap<String, Object> levelLoreCodes = getCodes();
				levelLoreCodes.put("%constant%", enchantabilityModifier * level + enchantabilityConstant);
				levelItemMeta.setLore(Chatable.get().getMessages(levelLoreCodes, "calc.by_level_lore"));
				levelItem.setItemMeta(levelItemMeta);
				inv.setItem(i, levelItem);
			}

			inv.setItem(45, goBack());

			inv.setItem(53, nextPage());
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
		if (!external) player.closeInventory();
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

	@Override
	public int getPage() {
		return page;
	}

	@Override
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
			Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "calc.invalid_number"));
		}
		if (type.equals("constant")) enchantabilityConstant = num;
		else if (type.equals("modifier")) enchantabilityModifier = num;
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
		Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "calc.enter_string"));
		player.closeInventory();
	}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if (inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else if (inv.getSize() == inventory.getSize()) {
			inv = player.getOpenInventory().getTopInventory();
			inventory = inv;
		} else {
			inventory = inv;
			player.openInventory(inv);
		}
		for(int i = 0; i < inventory.getSize(); i++)
			inventory.setItem(i, new ItemStack(Material.AIR));
		if (opening) opening = false;
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

	public int[] getEnchantabilityCalc(int enchantability, int constant, int modifier) {
		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + enchantability_2;

		int level = 1;

		if (ConfigUtils.getAdvancedBoolean(ConfigString.USE_LAPIS_MODIFIERS, ConfigString.LEVEL_FIFTY.getBoolean())) {
			double lapisConstant = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_CONSTANT, ConfigString.LEVEL_FIFTY.getBoolean() ? -1 : 0);
			double lapisMultiplier = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_MULTIPLIER, ConfigString.LEVEL_FIFTY.getBoolean() ? 2 : 0);
			rand_enchantability += ((ConfigString.LEVEL_FIFTY.getBoolean() ? 6 : 3) + lapisConstant) * lapisMultiplier;
		}
		int max = (ConfigString.LEVEL_FIFTY.getBoolean() ? 50 : 30) + rand_enchantability;
		max = (int) (max * 1.15 + 0.5);

		if (modifier <= 0) return new int[] { 1, max };

		int maxEnchantability = modifier * level + constant;
		while (max > maxEnchantability + modifier) {
			level++;
			maxEnchantability = modifier * level + constant;
		}

		return new int[] { level--, max };
	}

	@Override
	public ChatUtils getChat() {
		return Chatable.get();
	}

}
