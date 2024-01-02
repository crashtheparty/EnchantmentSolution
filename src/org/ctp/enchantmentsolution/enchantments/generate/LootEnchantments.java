package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.*;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.enums.Loots.LootType;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public abstract class LootEnchantments extends GenerateEnchantments {

	private Loots loot;
	private Location loc;
	private int random;

	public LootEnchantments(Player player, ItemStack item, EnchantmentLocation location, Loots loot) {
		super(player, item, location);

		this.loot = loot;
	}

	public LootEnchantments(Player player, ItemStack item, EnchantmentLocation location, Loots loot, Location loc) {
		this(player, item, location, loot);

		this.loc = loc;
	}

	public List<EnchantmentLevel> getEnchantments() {
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		if (loot.getType() == LootType.ENCHANTING_TABLE || loot.getType() == LootType.ENCHANTING_TABLE_LOCATION) {
			int bookshelves = 0;
			if (loot.getType() == LootType.ENCHANTING_TABLE_LOCATION) bookshelves = EnchantmentUtils.getBookshelves(loc);
			else
				bookshelves = MathUtils.normalize(loot.getEnchantingBookshelvesMin(), loot.getEnchantingBookshelvesMax(), loot.getEnchantingBookshelvesNormalize());
			if (bookshelves < 0) bookshelves = 0;
			LevelList levelList = new LevelList(bookshelves, new Random());

			EnchantmentList[] list = new EnchantmentList[levelList.getList().length];
			ESPlayer p = null;
			Random rand = new Random();
			if (getPlayer() != null) p = EnchantmentSolution.getESPlayer(getPlayer());
			for(int i = 0; i < list.length; i++) {
				Level level = levelList.getList()[i];
				if (level.getLevel() > -1) list[i] = new EnchantmentList(p, level, new ItemData(getItem()), getLocation(), loot, rand);
			}
			int slot = MathUtils.normalize(loot.getSlotMin(), loot.getSlotMax(), loot.getSlotNormalize());
			if (slot > 5) slot = 5;
			if (slot > 2 && list[slot] == null) slot = 2;
			if (slot < 0) slot = 0;
			enchants = list[slot].getEnchantments();
			random = slot;
		} else if (loot.getType() == LootType.ENCHANTABILITY || loot.getType() == LootType.LEVELS) {
			int enchantability = 0;
			int level = 0;
			if (loot.getType() == LootType.ENCHANTABILITY) {
				enchantability = MathUtils.normalize(loot.getEnchantabilityMin(), loot.getEnchantabilityMax(), loot.getEnchantabilityNormalize());
				level = (int) (enchantability * 0.85);
			} else {
				level = MathUtils.normalize(loot.getLevelsMin(), loot.getLevelsMax(), loot.getLevelsNormalize());
				enchantability = getEnchantability(new ItemData(getItem()), level);
			}
			if (level < 1) level = 1;
			if (level > (ConfigString.LEVEL_FIFTY.getBoolean() ? 50 : 30)) level = ConfigString.LEVEL_FIFTY.getBoolean() ? 50 : 30;
			double multiEnchantDivisor = ConfigString.LEVEL_FIFTY.getBoolean() ? ConfigString.MULTI_ENCHANT_DIVISOR_FIFTY.getDouble() : ConfigString.MULTI_ENCHANT_DIVISOR_THIRTY.getDouble();
			if (!loot.chanceFromDefault()) multiEnchantDivisor = loot.getChance();
			CustomEnchantment enchantment = getEnchantment(enchants, level, enchantability);
			if (enchantment != null) {
				Player p = getPlayer() == null ? null : getPlayer().getPlayer();
				enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
				if (loot.allowMultiple()) {
					int finalEnchantability = (int) (loot != null ? loot.lowerChances() ? enchantability * loot.getLowerBy() : enchantability : enchantability / 2);
					double chance = loot == null || loot.chanceFromDefault() ? (finalEnchantability + 1) / multiEnchantDivisor : loot.getChance();
					while (chance > Math.random() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
						enchantment = getEnchantment(enchants, level, enchantability);
						if (enchantment == null) break;
						if (ConfigString.DECAY.getBoolean()) enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, finalEnchantability)));
						else
							enchants.add(new EnchantmentLevel(enchantment, enchantment.getEnchantLevel(p, enchantability)));
						finalEnchantability = (int) (loot != null ? loot.lowerChances() ? finalEnchantability * loot.getLowerBy() : finalEnchantability : finalEnchantability / 2);
						chance = loot == null || loot.chanceFromDefault() ? (finalEnchantability + 1) / multiEnchantDivisor : loot.lowerChances() ? chance * loot.getLowerBy() : chance;
					}
				}
			}
			random = level;
		} else if (loot.getType() == LootType.FROM_SELECTION || loot.getType() == LootType.RANDOM) {
			List<CustomEnchantment> enchantments = new ArrayList<CustomEnchantment>();
			if (loot.getType() == LootType.FROM_SELECTION) enchantments.addAll(loot.getEnchantments());
			else {
				enchantments.addAll(RegisterEnchantments.getRegisteredEnchantments());
				enchantments.removeAll(loot.getBlacklistEnchantments());
			}

			CustomEnchantment enchantment = getEnchantment(enchants, enchantments);
			if (enchantment != null) {
				Player p = getPlayer() == null ? null : getPlayer().getPlayer();
				int level = MathUtils.normalize(1, enchantment.getMaxLevel(p), loot.getLevelsNormalize());
				enchants.add(new EnchantmentLevel(enchantment, level));
				if (loot.allowMultiple()) {
					double chance = loot.chanceFromDefault() ? 0.75 : loot.getChance();
					while (!loot.chanceFromDefault() && chance > Math.random() && (ConfigString.MAX_ENCHANTMENTS.getInt() == 0 ? true : enchants.size() < ConfigString.MAX_ENCHANTMENTS.getInt())) {
						enchantment = getEnchantment(enchants, enchantments);
						if (enchantment == null) break;
						level = MathUtils.normalize(1, enchantment.getMaxLevel(p), loot.getLevelsNormalize());
						enchants.add(new EnchantmentLevel(enchantment, level));
						if (loot.lowerChances()) chance *= loot.getLowerBy();
					}
				}
			}
		}
		return enchants;
	}

	private int getEnchantability(ItemData item, int level) {
		int enchantability = 1;
		for(EnchantabilityMaterial eMaterial: EnchantabilityMaterial.values())
			if (eMaterial.containsType(item.getMaterial())) {
				enchantability = eMaterial.getEnchantability();
				break;
			}

		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + MathUtils.randomInt(enchantability_2 / 2 + 1) + MathUtils.randomInt(enchantability_2 / 2 + 1);

		int k = level + rand_enchantability;
		float rand_bonus_percent = (float) (1 + (MathUtils.randomFloat(1) + MathUtils.randomFloat(1) - 1) * .15);
		return (int) (k * rand_bonus_percent + 0.5);
	}

	private CustomEnchantment getEnchantment(List<EnchantmentLevel> previousLevels, List<CustomEnchantment> selection) {
		int totalWeight = 0;
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment: selection)
			if (canAddEnchantment(previousLevels, enchantment)) {
				if (loot.useWeights()) totalWeight += enchantment.getWeight();
				else
					totalWeight++;
				customEnchants.add(enchantment);
			}
		if (totalWeight == 0) return null;
		int getWeight = (int) (Math.random() * totalWeight);
		for(CustomEnchantment customEnchant: customEnchants) {
			if (loot.useWeights()) getWeight -= customEnchant.getWeight();
			else
				getWeight--;
			if (getWeight <= 0) return customEnchant;
		}
		return null;
	}

	private CustomEnchantment getEnchantment(List<EnchantmentLevel> previousLevels, int level, int enchantability) {
		int totalWeight = 0;
		Player p = getPlayer() == null ? null : getPlayer().getPlayer();
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getRegisteredEnchantments();
		for(CustomEnchantment enchantment: registeredEnchantments)
			if (canAddEnchantment(previousLevels, enchantment, level, enchantability) && enchantment.getEnchantLevel(p, enchantability) > 0) {
				if (loot.useWeights()) totalWeight += enchantment.getWeight();
				else
					totalWeight++;
				customEnchants.add(enchantment);
			}
		if (totalWeight == 0) return null;
		int getWeight = (int) (Math.random() * totalWeight);
		for(CustomEnchantment customEnchant: customEnchants) {
			if (loot.useWeights()) getWeight -= customEnchant.getWeight();
			else
				getWeight--;
			if (getWeight <= 0) return customEnchant;
		}
		return null;
	}

	private boolean canAddEnchantment(List<EnchantmentLevel> levels, CustomEnchantment enchantment) {
		if (loot != null && loot.getBlacklistEnchantments().contains(enchantment)) return false;
		List<EnchantmentLocation> locations = enchantment.getEnchantmentLocations();
		if (levels != null) for(EnchantmentLevel l: levels)
			if (CustomEnchantment.conflictsWith(l.getEnchant(), enchantment)) return false;
		ItemData item = new ItemData(getItem());
		if (enchantment.isEnabled() && enchantment.canEnchantItem(item) && (locations.contains(getLocation()) || getLocation() == EnchantmentLocation.NONE) && (getPlayer() == null || getPlayer().getPlayer() == null || enchantment.getMaxLevel(getPlayer().getPlayer()) > 0)) return true;
		return false;
	}

	private boolean canAddEnchantment(List<EnchantmentLevel> levels, CustomEnchantment enchantment, int level, int enchantability) {
		if (loot != null && loot.getBlacklistEnchantments().contains(enchantment)) return false;
		List<EnchantmentLocation> locations = enchantment.getEnchantmentLocations();
		if (levels != null) for(EnchantmentLevel l: levels)
			if (CustomEnchantment.conflictsWith(l.getEnchant(), enchantment)) return false;
		ItemData item = new ItemData(getItem());
		if (enchantment.isEnabled() && enchantment.canEnchantItem(item) && (locations.contains(getLocation()) || getLocation() == EnchantmentLocation.NONE) && (getPlayer() == null || getPlayer().getPlayer() == null || enchantment.canEnchant(getPlayer().getPlayer(), enchantability, level))) return true;
		return false;
	}

	public int getRandom() {
		return random;
	}
}
