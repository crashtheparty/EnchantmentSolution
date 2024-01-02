package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class EnchantmentList {

	private Level level;
	private ESPlayer player;
	private ItemData item;
	private int enchantability;
	private List<EnchantmentLevel> enchantments;
	private EnchantmentLocation location;
	private Loots loot;
	private final Random random;

	public EnchantmentList(ESPlayer player, Level level, ItemData item, EnchantmentLocation location, Random random) {
		this.level = level;
		this.player = player;
		this.item = item;
		this.location = location;
		this.random = random;
		setEnchantability();

		generate();
	}

	public EnchantmentList(ESPlayer player, Level level, ItemData item, EnchantmentLocation location, Loots loot, Random random) {
		this.level = level;
		this.player = player;
		this.item = item;
		this.location = location;
		this.random = random;
		this.loot = loot;
		setEnchantability();

		generate();
	}

	public EnchantmentList(ESPlayer player, ItemData item, EnchantmentLocation location, List<EnchantmentLevel> fishing,
	double multiEnchant) {
		this.player = player;
		this.item = item;
		this.location = location;
		random = new Random();

		generatePreselected(fishing, multiEnchant);
	}

	public void setEnchantability() {
		int enchantability = 1;
		for(EnchantabilityMaterial eMaterial: EnchantabilityMaterial.values())
			if (eMaterial.containsType(item.getMaterial())) {
				enchantability = eMaterial.getEnchantability();
				break;
			}

		int enchantability_2 = enchantability / 2;
		int rand1 = MathUtils.randomIntFromSeed(random, enchantability_2 / 2 + 1);
		int rand2 = MathUtils.randomIntFromSeed(random, enchantability_2 / 2 + 1);
		float rand3 = MathUtils.randomFloatFromSeed(random);
		float rand4 = MathUtils.randomFloatFromSeed(random);
		int rand_enchantability = 1 + rand1 + rand2;

		int k = level.getLevel() + rand_enchantability;
		float rand_bonus_percent = (float) (1 + (rand3 + rand4 - 1) * .15);
		this.enchantability = (int) (k * rand_bonus_percent + 0.5) + (player == null ? 0 : player.getCurrentEchoShards());
	}

	public void generate() {
		double multiEnchantDivisor = ConfigString.LEVEL_FIFTY.getBoolean() ? ConfigString.MULTI_ENCHANT_DIVISOR_FIFTY.getDouble() : ConfigString.MULTI_ENCHANT_DIVISOR_THIRTY.getDouble();
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		CustomEnchantment enchantment = getEnchantment(enchants);
		if (enchantment != null) {
			Player p = player == null ? null : player.getOnlinePlayer();
			enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
			if (loot == null || loot.allowMultiple()) {
				int enchantability = this.enchantability;
				int finalEnchantability = (int) (loot != null ? loot.lowerChances() ? enchantability * loot.getLowerBy() : enchantability : enchantability / 2);
				double chance = loot == null || loot.chanceFromDefault() ? (finalEnchantability + 1) / multiEnchantDivisor : loot.getChance();
				while (chance > MathUtils.randomFloatFromSeed(random) && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
					enchantment = getEnchantment(enchants);
					if (enchantment == null) break;
					if (ConfigString.DECAY.getBoolean()) enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, finalEnchantability)));
					else
						enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
					finalEnchantability = (int) (loot != null ? loot.lowerChances() ? finalEnchantability * loot.getLowerBy() : finalEnchantability : finalEnchantability / 2);
					chance = loot == null || loot.chanceFromDefault() ? (finalEnchantability + 1) / multiEnchantDivisor : loot.lowerChances() ? chance * loot.getLowerBy() : chance;
				}
			}
		}

		setEnchantments(enchants);
	}

	public void generatePreselected(List<EnchantmentLevel> selections, double multiEnchantDivisor) {
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		EnchantmentLevel enchantment = getPreselectedEnchantment(enchants, selections);
		if (enchantment != null) {
			enchants.add(enchantment);
			while (multiEnchantDivisor > random.nextFloat() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
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
		int getWeight = (int) (random.nextFloat() * totalWeight);
		for(EnchantmentLevel customEnchant: customEnchants) {
			if (getWeight <= 0) return customEnchant;
			getWeight -= customEnchant.getEnchant().getWeight();
		}
		return null;
	}

	private CustomEnchantment getEnchantment(List<EnchantmentLevel> previousLevels) {
		int totalWeight = 0;
		Player p = player == null ? null : player.getOnlinePlayer();
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getRegisteredEnchantments();
		for(CustomEnchantment enchantment: registeredEnchantments)
			if (canAddEnchantment(previousLevels, enchantment) && enchantment.getEnchantLevel(p, enchantability) > 0) {
				if (loot == null || loot.useWeights()) totalWeight += enchantment.getWeight();
				else
					totalWeight++;
				customEnchants.add(enchantment);
			}
		if (totalWeight == 0) return null;
		int getWeight = (int) (MathUtils.randomFloatFromSeed(random) * totalWeight);
		for(CustomEnchantment customEnchant: customEnchants) {
			if (loot == null || loot.useWeights()) getWeight -= customEnchant.getWeight();
			else
				getWeight--;
			if (getWeight <= 0) return customEnchant;
		}
		return null;
	}

	private boolean canAddEnchantment(List<EnchantmentLevel> levels, CustomEnchantment enchantment) {
		if (loot != null && loot.getBlacklistEnchantments().contains(enchantment)) return false;
		List<EnchantmentLocation> locations = enchantment.getEnchantmentLocations();
		if (levels != null) for(EnchantmentLevel level: levels)
			if (CustomEnchantment.conflictsWith(level.getEnchant(), enchantment)) return false;

		if (level == null) {
			if (enchantment.isEnabled() && enchantment.canEnchantItem(item) && locations.contains(location)) return true;
			return false;
		}
		if (enchantment.isEnabled() && enchantment.canEnchantItem(item) && (locations.contains(location) || location == EnchantmentLocation.NONE) && (player == null || player.getPlayer() == null || enchantment.canEnchant(player.getOnlinePlayer(), enchantability, level.getLevel()))) return true;
		return false;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<EnchantmentLevel> enchantments) {
		this.enchantments = enchantments;
	}

	public int getEnchantability() {
		return enchantability;
	}
}
