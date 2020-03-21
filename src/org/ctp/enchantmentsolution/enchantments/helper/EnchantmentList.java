package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;

public class EnchantmentList {

	private Level level;
	private OfflinePlayer player;
	private Material material;
	private int enchantability;
	private List<EnchantmentLevel> enchantments;
	private boolean treasure;

	public EnchantmentList(OfflinePlayer player, Level level, Material material, boolean treasure) {
		this.level = level;
		this.player = player;
		this.material = material;
		this.treasure = treasure;
		setEnchantability();

		generate();
	}

	private EnchantmentList(OfflinePlayer player, Level level, Material material, boolean treasure, int enchantability, List<EnchantmentLevel> generated) {
		this.level = level;
		this.player = player;
		this.material = material;
		this.treasure = treasure;
		this.enchantability = enchantability;
		enchantments = generated;
	}

	public EnchantmentList(OfflinePlayer player, Material material, boolean treasure, List<EnchantmentLevel> fishing,
	double multiEnchant) {
		this.player = player;
		this.material = material;
		this.treasure = treasure;

		generatePreselected(fishing, multiEnchant);
	}

	public void setEnchantability() {
		int enchantability = 1;
		for(EnchantabilityMaterial eMaterial: EnchantabilityMaterial.values())
			if (eMaterial.containsType(material)) {
				enchantability = eMaterial.getEnchantability();
				break;
			}

		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + randomInt(enchantability_2 / 2 + 1) + randomInt(enchantability_2 / 2 + 1);

		if (ConfigUtils.getAdvancedBoolean(ConfigString.USE_LAPIS_MODIFIERS, ConfigString.LEVEL_FIFTY.getBoolean())) {
			double lapisConstant = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_CONSTANT, ConfigString.LEVEL_FIFTY.getBoolean() ? -1 : 0);
			double lapisMultiplier = ConfigUtils.getAdvancedDouble(ConfigString.LAPIS_MULTIPLIER, ConfigString.LEVEL_FIFTY.getBoolean() ? 2 : 0);
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
		double multiEnchantDivisor = ConfigUtils.getAdvancedDouble(ConfigString.MULTI_ENCHANT_DIVISOR, ConfigString.LEVEL_FIFTY.getBoolean() ? 75 : 50);
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		CustomEnchantment enchantment = getEnchantment(enchants);
		if (enchantment != null) {
			Player p = player == null ? null : player.getPlayer();
			enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
			int enchantability = this.enchantability;
			int finalEnchantability = enchantability / 2;
			while ((finalEnchantability + 1) / multiEnchantDivisor > Math.random() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
				enchantment = getEnchantment(enchants);
				if (enchantment == null) break;
				if (ConfigUtils.getAdvancedBoolean(ConfigString.DECAY, false)) enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, finalEnchantability)));
				else
					enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
				finalEnchantability /= 2;
			}
		}

		setEnchantments(enchants);
	}

	public void generatePreselected(List<EnchantmentLevel> selections, double multiEnchantDivisor) {
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		EnchantmentLevel enchantment = getPreselectedEnchantment(enchants, selections);
		if (enchantment != null) {
			enchants.add(enchantment);
			while (multiEnchantDivisor > Math.random() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
				enchantment = getPreselectedEnchantment(enchants, selections);
				if (enchantment == null) break;
				enchants.add(enchantment);
				multiEnchantDivisor /= 2;
			}
		}

		setEnchantments(enchants);
	}

	private EnchantmentLevel getPreselectedEnchantment(List<EnchantmentLevel> previousLevels,
	List<EnchantmentLevel> selections) {
		int totalWeight = 0;
		List<EnchantmentLevel> customEnchants = new ArrayList<EnchantmentLevel>();
		for(EnchantmentLevel enchantment: selections)
			if (enchantment.getEnchant() != null && canAddEnchantment(previousLevels, enchantment.getEnchant())) {
				totalWeight += enchantment.getEnchant().getWeight();
				customEnchants.add(enchantment);
			}
		if (totalWeight == 0) return null;
		int getWeight = (int) (Math.random() * totalWeight);
		for(EnchantmentLevel customEnchant: customEnchants) {
			getWeight -= customEnchant.getEnchant().getWeight();
			if (getWeight <= 0) return customEnchant;
		}
		return null;
	}

	private CustomEnchantment getEnchantment(List<EnchantmentLevel> previousLevels) {
		int totalWeight = 0;
		Player p = player == null ? null : player.getPlayer();
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getEnchantments();
		for(CustomEnchantment enchantment: registeredEnchantments)
			if (canAddEnchantment(previousLevels, enchantment) && enchantment.getEnchantLevel(p, enchantability) > 0) {
				totalWeight += enchantment.getWeight();
				customEnchants.add(enchantment);
			}
		if (totalWeight == 0) return null;
		int getWeight = (int) (Math.random() * totalWeight);
		for(CustomEnchantment customEnchant: customEnchants) {
			getWeight -= customEnchant.getWeight();
			if (getWeight <= 0) return customEnchant;
		}
		return null;
	}

	private boolean canAddEnchantment(List<EnchantmentLevel> levels, CustomEnchantment enchantment) {
		if (levels != null) for(EnchantmentLevel level: levels)
			if (CustomEnchantment.conflictsWith(level.getEnchant(), enchantment)) return false;

		if (level == null) {
			if (enchantment.isEnabled() && enchantment.canEnchantItem(material) && (treasure || !enchantment.isTreasure())) return true;
			return false;
		}

		if (enchantment.isEnabled() && enchantment.canEnchantItem(material) && (treasure || !enchantment.isTreasure()) && (player == null || player.getPlayer() == null || enchantment.canEnchant(player.getPlayer(), enchantability, level.getLevel()))) return true;
		return false;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<EnchantmentLevel> enchantments) {
		this.enchantments = enchantments;
	}

	public static EnchantmentList fromConfig(YamlConfig config, int i, String key, int k, OfflinePlayer player, Level level, Material m, boolean treasure) {
		List<String> enchants = config.getStringList("enchanting_table." + i + ".enchantmentList." + key + "." + k + ".enchants");
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		for(String enchant: enchants)
			levels.add(new EnchantmentLevel(enchant));
		int enchantability = config.getInt("enchanting_table." + i + ".enchantmentList." + key + "." + k + ".enchantability");

		return new EnchantmentList(player, level, m, treasure, enchantability, levels);
	}

	public void setConfig(YamlConfig config, int i, Material m) {
		String path = "enchanting_table." + i + ".enchantmentList." + m.name() + "." + level.getSlot() + ".";
		List<String> enchants = new ArrayList<String>();
		for(EnchantmentLevel level: getEnchantments())
			enchants.add(level.toString());
		config.set(path + "enchants", enchants);
		config.set(path + "enchantability", enchantability);
	}
}
