package org.ctp.enchantmentsolution.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public abstract class CustomEnchantment {

	private boolean enabled = true;
	private boolean treasure = false;
	private String displayName = "", defaultDisplayName = "";
	private int defaultThirtyConstant = -1, defaultFiftyConstant = -1, constant = -1, defaultThirtyModifier = -1,
			defaultFiftyModifier = -1, modifier = -1, defaultThirtyMaxConstant = -1, defaultFiftyMaxConstant = -1,
			maxConstant = -1, defaultThirtyStartLevel = -1, defaultFiftyStartLevel = -1, startLevel = -1,
			defaultThirtyMaxLevel = -1, defaultFiftyMaxLevel = -1, maxLevel = -1;
	private Weight defaultWeight = Weight.NULL;
	private Weight weight = Weight.NULL;
	private boolean maxLevelOne = false;

	public Enchantment getRelativeEnchantment() {
		return null;
	}
	
	public static boolean conflictsWith(CustomEnchantment enchOne, CustomEnchantment enchTwo) {
		if(enchOne.conflictsWith(enchTwo) || enchTwo.conflictsWith(enchOne)) {
			return true;
		}
		return false;
	}

	public boolean canEnchantItem(Material item) {
		return false;
	}

	public boolean canAnvilItem(Material item) {
		return false;
	}

	public boolean conflictsWith(CustomEnchantment ench) {
		return false;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public String getName() {
		return null;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public int getWeight() {
		return weight.getWeight();
	}
	
	public boolean canAnvil(Player player, int level) {
		if (PermissionUtils.canAnvil(player, this, level)) {
			return true;
		}

		return false;
	}
	
	public int getAnvilLevel(Player player, int level) {
		while(level > 0) {
			if(PermissionUtils.canAnvil(player, this, level)) {
				return level;
			}
			level --;
		}
		return 0;
	}

	public boolean canEnchant(Player player, int enchantability, int level) {
		if (ConfigFiles.useStartLevel() && level < getStartLevel()) {
			return false;
		}
		if(getEnchantLevel(player, enchantability) > 0) {
			return true;
		}

		return false;
	}

	public int getEnchantLevel(Player player, int enchantability) {
		for(int i = getMaxLevel(); i > 0; i--) {
			int[] levels = enchantability(i);
			if (PermissionUtils.canEnchant(player, this, i)) {
				if (enchantability >= levels[0] && enchantability <= levels[1]) {
					return i;
				}
			}
		}
		return 0;
	}

	public int[] enchantability(int level) {
		int[] levels = new int[2];
		levels[0] = modifier * level + constant;
		levels[1] = levels[0] + maxConstant;
		return levels;
	}

	public int multiplier(Material material) {
		if (!(material.equals(Material.BOOK) || material.equals(Material.ENCHANTED_BOOK))) {
			return weight.getBook();
		}
		return weight.getItem();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTreasure() {
		return treasure;
	}

	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}

	public String[] getPage() {
		return null;
	}

	public void setDisplayName(String name) {
		displayName = name;
	}

	private int getDefaultThirtyConstant() {
		return defaultThirtyConstant;
	}

	protected void setDefaultThirtyConstant(int constant) {
		defaultThirtyConstant = constant;
	}

	public int getDefaultFiftyConstant() {
		return defaultFiftyConstant;
	}

	protected void setDefaultFiftyConstant(int constant) {
		defaultFiftyConstant = constant;
	}

	private void setConstant(int constant) {
		this.constant = constant;
	}

	private int getDefaultThirtyModifier() {
		return defaultThirtyModifier;
	}

	protected void setDefaultThirtyModifier(int modifier) {
		defaultThirtyModifier = modifier;
	}

	public int getDefaultFiftyModifier() {
		return defaultFiftyModifier;
	}

	protected void setDefaultFiftyModifier(int modifier) {
		defaultFiftyModifier = modifier;
	}

	private void setModifier(int modifier) {
		this.modifier = modifier;
	}

	private int getDefaultThirtyMaxConstant() {
		return defaultThirtyMaxConstant;
	}

	protected void setDefaultThirtyMaxConstant(int constant) {
		defaultThirtyMaxConstant = constant;
	}

	public int getDefaultFiftyMaxConstant() {
		return defaultFiftyMaxConstant;
	}

	protected void setDefaultFiftyMaxConstant(int constant) {
		defaultFiftyMaxConstant = constant;
	}

	private void setMaxConstant(int maxConstant) {
		this.maxConstant = maxConstant;
	}

	private int getDefaultThirtyStartLevel() {
		return defaultThirtyStartLevel;
	}

	protected void setDefaultThirtyStartLevel(int startLevel) {
		defaultThirtyStartLevel = startLevel;
	}

	public int getDefaultFiftyStartLevel() {
		return defaultFiftyStartLevel;
	}

	protected void setDefaultFiftyStartLevel(int startLevel) {
		defaultFiftyStartLevel = startLevel;
	}

	private void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	private int getDefaultThirtyMaxLevel() {
		return defaultThirtyMaxLevel;
	}

	protected void setDefaultThirtyMaxLevel(int maxLevel) {
		defaultThirtyMaxLevel = maxLevel;
	}

	public int getDefaultFiftyMaxLevel() {
		return defaultFiftyMaxLevel;
	}

	protected void setDefaultFiftyMaxLevel(int maxLevel) {
		defaultFiftyMaxLevel = maxLevel;
	}

	private void setMaxLevel(int maxLevel) {
		if (isMaxLevelOne()) {
			this.maxLevel = 1;
		} else {
			this.maxLevel = maxLevel;
		}
	}

	public void setLevelFifty() {
		setConstant(getDefaultFiftyConstant());
		setModifier(getDefaultFiftyModifier());
		setMaxConstant(getDefaultFiftyMaxConstant());
		setStartLevel(getDefaultFiftyStartLevel());
		setMaxLevel(getDefaultFiftyMaxLevel());
		setWeight(null);
	}

	public void setLevelThirty() {
		setConstant(getDefaultThirtyConstant());
		setModifier(getDefaultThirtyModifier());
		setMaxConstant(getDefaultThirtyMaxConstant());
		setStartLevel(getDefaultThirtyStartLevel());
		setMaxLevel(getDefaultThirtyMaxLevel());
		setWeight(null);
	}

	public void setCustom(int constant, int modifier, int maxConstant, int startLevel, int maxLevel, Weight weight) {
		setConstant(constant);
		setModifier(modifier);
		setMaxConstant(maxConstant);
		setStartLevel(startLevel);
		setMaxLevel(maxLevel);
		setWeight(weight);
	}

	public boolean isMaxLevelOne() {
		return maxLevelOne;
	}

	public void setMaxLevelOne(boolean maxLevelOne) {
		this.maxLevelOne = maxLevelOne;
	}

	public int getDefaultWeight() {
		return defaultWeight.getWeight();
	}
	
	public String getDefaultWeightName() {
		return defaultWeight.getName();
	}

	public void setDefaultWeight(Weight defaultWeight) {
		this.defaultWeight = defaultWeight;
		this.weight = defaultWeight;
	}

	protected void setWeight(Weight weight) {
		if(weight != null) {
			this.weight = weight;
		} else {
			this.weight = this.defaultWeight;
		}
	}

	public String getDefaultDisplayName() {
		return defaultDisplayName;
	}

	protected void setDefaultDisplayName(String defaultDisplayName) {
		this.defaultDisplayName = defaultDisplayName;
		this.displayName = defaultDisplayName;
	}

}
