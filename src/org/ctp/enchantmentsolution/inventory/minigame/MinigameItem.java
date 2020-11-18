package org.ctp.enchantmentsolution.inventory.minigame;

import java.util.List;

import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MinigameItem {

	private final MatData show, enchant;
	private final MinigameItemType type;
	private final int lvlCost, lvlExtraCost, lvlMaxCost, lapisCost, lapisExtraCost, lapisMaxCost, minBooks, maxBooks, minLevels, maxLevels, slot;
	private final double economyCost, economyExtraCost, economyMaxCost;
	private final boolean increaseLevelCost, increaseLapisCost, increaseEconomyCost;
	private final List<EnchantmentLevel> levels;
	private final List<String> costs;

	public MinigameItem(MatData show, MatData enchant, MinigameItemType type, int lvlCost, int lvlExtraCost, int lvlMaxCost, int lapisCost, int lapisExtraCost,
	int lapisMaxCost, double economyCost, double economyExtraCost, double economyMaxCost, List<String> costs, int minBooks, int maxBooks, int minLevels,
	int maxLevels, int slot, boolean increaseLevelCost, boolean increaseLapisCost, boolean increaseEconomyCost, List<EnchantmentLevel> levels) {
		this.show = show;
		this.enchant = enchant;
		this.type = type;
		this.lvlCost = lvlCost;
		this.lvlExtraCost = lvlExtraCost;
		this.lvlMaxCost = lvlMaxCost;
		this.lapisCost = lapisCost;
		this.lapisExtraCost = lapisExtraCost;
		this.lapisMaxCost = lapisMaxCost;
		this.economyCost = economyCost;
		this.economyExtraCost = economyExtraCost;
		this.economyMaxCost = economyMaxCost;
		this.minBooks = minBooks;
		this.maxBooks = maxBooks;
		this.minLevels = minLevels;
		this.maxLevels = maxLevels;
		this.slot = slot;
		this.increaseLevelCost = increaseLevelCost;
		this.increaseLapisCost = increaseLapisCost;
		this.increaseEconomyCost = increaseEconomyCost;
		this.levels = levels;
		this.costs = costs;
	}

	public MatData getShow() {
		return show;
	}

	public MatData getEnchant() {
		return enchant;
	}

	public MinigameItemType getType() {
		return type;
	}

	public int getMinBooks() {
		return minBooks;
	}

	public int getMaxBooks() {
		return maxBooks;
	}

	public int getSlot() {
		return slot;
	}

	public List<EnchantmentLevel> getLevels() {
		return levels;
	}

	public int getMinLevels() {
		return minLevels;
	}

	public int getMaxLevels() {
		return maxLevels;
	}

	public int getLvlCost() {
		return lvlCost;
	}

	public int getLvlExtraCost() {
		return lvlExtraCost;
	}

	public int getLvlMaxCost() {
		return lvlMaxCost;
	}

	public int getLapisCost() {
		return lapisCost;
	}

	public int getLapisExtraCost() {
		return lapisExtraCost;
	}

	public int getLapisMaxCost() {
		return lapisMaxCost;
	}

	public double getEconomyCost() {
		return economyCost;
	}

	public double getEconomyExtraCost() {
		return economyExtraCost;
	}

	public double getEconomyMaxCost() {
		return economyMaxCost;
	}

	public List<String> getCosts() {
		return costs;
	}

	public boolean useLvlCost() {
		for(String s: costs)
			if (s.equalsIgnoreCase("level")) return true;
		return false;
	}

	public boolean useLapisCost() {
		for(String s: costs)
			if (s.equalsIgnoreCase("lapis")) return true;
		return false;
	}

	public boolean useEconomyCost() {
		if (!CrashAPI.hasEconomy()) return false;
		for(String s: costs)
			if (s.equalsIgnoreCase("economy")) return true;
		return false;
	}

	public boolean willIncreaseLevelCost() {
		return increaseLevelCost;
	}

	public boolean willIncreaseLapisCost() {
		return increaseLapisCost;
	}

	public boolean willIncreaseEconomyCost() {
		return increaseEconomyCost;
	}

	public enum MinigameItemType {
		ENCHANTMENT(false), RANDOM(false), RANDOM_MULTI(true), RANDOM_BOOKSHELF(false), RANDOM_MULTI_BOOKSHELF(true);

		private final boolean multiple;

		MinigameItemType(boolean multiple) {
			this.multiple = multiple;
		}

		public boolean isMultiple() {
			return multiple;
		}

		public boolean fromLocation() {
			return this == RANDOM_BOOKSHELF || this == RANDOM_MULTI_BOOKSHELF;
		}
	}
}
