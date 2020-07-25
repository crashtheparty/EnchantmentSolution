package org.ctp.enchantmentsolution.inventory.minigame;

import java.util.List;

import org.ctp.enchantmentsolution.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MinigameItem {

	private final MatData show, enchant;
	private final MinigameItemType type;
	private final int cost, extraCost, maxCost, minBooks, maxBooks, minLevels, maxLevels, slot;
	private final boolean increaseCost;
	private final List<EnchantmentLevel> levels;

	public MinigameItem(MatData show, MatData enchant, MinigameItemType type, int cost, int extraCost, int maxCost, int minBooks, int maxBooks, int minLevels,
	int maxLevels, int slot, boolean increaseCost, List<EnchantmentLevel> levels) {
		this.show = show;
		this.enchant = enchant;
		this.type = type;
		this.cost = cost;
		this.extraCost = extraCost;
		this.maxCost = maxCost;
		this.minBooks = minBooks;
		this.maxBooks = maxBooks;
		this.minLevels = minLevels;
		this.maxLevels = maxLevels;
		this.slot = slot;
		this.increaseCost = increaseCost;
		this.levels = levels;
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

	public int getCost() {
		return cost;
	}

	public int getExtraCost() {
		return extraCost;
	}

	public int getMaxCost() {
		return maxCost;
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

	public boolean willIncreaseCost() {
		return increaseCost;
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
