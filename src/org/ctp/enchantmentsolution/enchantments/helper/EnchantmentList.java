package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

public class EnchantmentList {

	private Level level;
	private Player player;
	private Material material;
	private int enchantability;
	private List<EnchantmentLevel> enchantments;
	private boolean treasure;

	public EnchantmentList(Player player, Level level, Material material, boolean treasure) {
		this.level = level;
		this.player = player;
		this.material = material;
		this.treasure = treasure;
		setEnchantability();

		generate();
	}

	public EnchantmentList(Player player, Material material, boolean treasure, List<EnchantmentLevel> fishing,
	double multiEnchant) {
		this.player = player;
		this.material = material;
		this.treasure = treasure;

		generatePreselected(fishing, multiEnchant);
	}

	public void setEnchantability() {
		int enchantability = 1;
		if (ItemType.WOODEN_TOOLS.getItemTypes().contains(material)) {
			enchantability = 15;
		} else if (ItemType.STONE_TOOLS.getItemTypes().contains(material)) {
			enchantability = 5;
		} else if (ItemType.GOLDEN_TOOLS.getItemTypes().contains(material)) {
			enchantability = 22;
		} else if (ItemType.IRON_TOOLS.getItemTypes().contains(material)) {
			enchantability = 14;
		} else if (ItemType.DIAMOND_TOOLS.getItemTypes().contains(material)) {
			enchantability = 10;
		} else if (ItemType.LEATHER_ARMOR.getItemTypes().contains(material)) {
			enchantability = 15;
		} else if (ItemType.GOLDEN_ARMOR.getItemTypes().contains(material)) {
			enchantability = 25;
		} else if (ItemType.CHAINMAIL_ARMOR.getItemTypes().contains(material)) {
			enchantability = 12;
		} else if (ItemType.IRON_ARMOR.getItemTypes().contains(material)) {
			enchantability = 9;
		} else if (ItemType.DIAMOND_ARMOR.getItemTypes().contains(material)) {
			enchantability = 10;
		}

		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + randomInt(enchantability_2 / 2 + 1) + randomInt(enchantability_2 / 2 + 1);

		if (ConfigUtils.getAdvancedBoolean(ConfigString.USE_LAPIS_MODIFIERS, ConfigString.LEVEL_FIFTY.getBoolean())) {
			double lapisConstant = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_CONSTANT,
			ConfigString.LEVEL_FIFTY.getBoolean() ? -1 : 0);
			double lapisMultiplier = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_MULTIPLIER,
			ConfigString.LEVEL_FIFTY.getBoolean() ? 2 : 0);
			rand_enchantability += (level.getSlot() + lapisConstant) * lapisMultiplier;
		}

		int k = level.getLevel() + rand_enchantability;
		float rand_bonus_percent = (float) (1 + (randomFloat() + randomFloat() - 1) * .15);
		this.enchantability = (int) (k * rand_bonus_percent + 0.5);
	}

	private static int randomInt(int num) {
		double random = Math.random();

		return (int) (random * num);
	}

	private static float randomFloat() {
		return (float) Math.random();
	}

	public void generate() {
		double multiEnchantDivisor = ConfigUtils.getAdvancedDouble(ConfigString.MULTI_ENCHANT_DIVISOR,
		ConfigString.LEVEL_FIFTY.getBoolean() ? 75 : 50);
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		CustomEnchantment enchantment = getEnchantment(enchants);
		if (enchantment == null) {
			setEnchantments(enchants);
			return;
		}
		enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(player, enchantability)));
		int enchantability = this.enchantability;
		int finalEnchantability = enchantability / 2;
		while ((finalEnchantability + 1) / multiEnchantDivisor > Math.random()
		&& (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true
		: enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
			enchantment = getEnchantment(enchants);
			if (enchantment == null) {
				break;
			}
			if (ConfigUtils.getAdvancedBoolean(ConfigString.DECAY, false)) {
				enchants
				.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(player, finalEnchantability)));
			} else {
				enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(player, enchantability)));
			}
			finalEnchantability /= 2;
		}

		setEnchantments(enchants);
	}

	public void generatePreselected(List<EnchantmentLevel> selections, double multiEnchantDivisor) {
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		EnchantmentLevel enchantment = getPreselectedEnchantment(enchants, selections);
		if (enchantment == null) {
			setEnchantments(enchants);
			return;
		}
		enchants.add(enchantment);
		while (multiEnchantDivisor > Math.random() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true
		: enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
			enchantment = getPreselectedEnchantment(enchants, selections);
			if (enchantment == null) {
				break;
			}
			enchants.add(enchantment);
			multiEnchantDivisor /= 2;
		}

		setEnchantments(enchants);
	}

	private EnchantmentLevel getPreselectedEnchantment(List<EnchantmentLevel> previousLevels,
	List<EnchantmentLevel> selections) {
		int totalWeight = 0;
		List<EnchantmentLevel> customEnchants = new ArrayList<EnchantmentLevel>();
		for(EnchantmentLevel enchantment: selections) {
			if (enchantment.getEnchant() != null && canAddEnchantment(previousLevels, enchantment.getEnchant())) {
				totalWeight += enchantment.getEnchant().getWeight();
				customEnchants.add(enchantment);
			}
		}
		if (totalWeight == 0) {
			return null;
		}
		int getWeight = (int) (Math.random() * totalWeight);
		for(EnchantmentLevel customEnchant: customEnchants) {
			getWeight -= customEnchant.getEnchant().getWeight();
			if (getWeight <= 0) {
				return customEnchant;
			}
		}
		return null;
	}

	private CustomEnchantment getEnchantment(List<EnchantmentLevel> previousLevels) {
		int totalWeight = 0;
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getEnchantments();
		for(CustomEnchantment enchantment: registeredEnchantments) {
			if (canAddEnchantment(previousLevels, enchantment)) {
				totalWeight += enchantment.getWeight();
				customEnchants.add(enchantment);
			}
		}
		if (totalWeight == 0) {
			return null;
		}
		int getWeight = (int) (Math.random() * totalWeight);
		for(CustomEnchantment customEnchant: customEnchants) {
			getWeight -= customEnchant.getWeight();
			if (getWeight <= 0) {
				return customEnchant;
			}
		}
		return null;
	}

	private boolean canAddEnchantment(List<EnchantmentLevel> levels, CustomEnchantment enchantment) {
		if (levels != null) {
			for(EnchantmentLevel level: levels) {
				if (CustomEnchantment.conflictsWith(level.getEnchant(), enchantment)) {
					return false;
				}
			}
		}

		if (level == null) {
			if (enchantment.isEnabled() && enchantment.canEnchantItem(material)
			&& (treasure || !enchantment.isTreasure())) {
				return true;
			}
			return false;
		}

		if (enchantment.isEnabled() && enchantment.canEnchantItem(material) && (treasure || !enchantment.isTreasure())
		&& enchantment.canEnchant(player, enchantability, level.getLevel())) {
			return true;
		}
		return false;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<EnchantmentLevel> enchantments) {
		this.enchantments = enchantments;
	}
}
