package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.crashapi.config.Language;
import org.ctp.crashapi.item.CustomItemType;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.StringUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDescription;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDisplayName;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;

public abstract class CustomEnchantment {

	protected boolean enabled = true;
	protected List<EnchantmentLocation> enchantmentLocations, defaultEnchantmentLocations;
	protected String displayName = null, description = "";
	protected List<EnchantmentDisplayName> defaultDisplayNames = new ArrayList<EnchantmentDisplayName>();
	protected List<EnchantmentDescription> defaultDescriptions = new ArrayList<EnchantmentDescription>();
	protected int defaultThirtyConstant = -1, defaultFiftyConstant = -1, constant = -1, defaultThirtyModifier = -1,
	defaultFiftyModifier = -1, modifier = -1, defaultThirtyStartLevel = -1, defaultFiftyStartLevel = -1,
	startLevel = -1, defaultThirtyMaxLevel = -1, defaultFiftyMaxLevel = -1, maxLevel = -1;
	protected Weight defaultWeight = Weight.NULL;
	protected Weight weight = Weight.NULL;
	protected boolean maxLevelOne = false, curse = false;
	protected List<Enchantment> conflictingEnchantments = null;
	protected List<ItemType> enchantmentTypes = null, anvilTypes = null;
	private final Type lang;

	public CustomEnchantment(String englishUSDisplayName, int fiftyConstant, int thirtyConstant, int fiftyModifier,
	int thirtyModifier, int fiftyStartLevel, int thirtyStartLevel, int fiftyMaxLevel, int thirtyMaxLevel, Weight weight,
	String englishUSDescription) {
		addDefaultDisplayName(englishUSDisplayName);
		setDefaultFiftyConstant(fiftyConstant);
		setDefaultThirtyConstant(thirtyConstant);
		setDefaultFiftyModifier(fiftyModifier);
		setDefaultThirtyModifier(thirtyModifier);
		setDefaultFiftyStartLevel(fiftyStartLevel);
		setDefaultThirtyStartLevel(thirtyStartLevel);
		setDefaultFiftyMaxLevel(fiftyMaxLevel);
		setDefaultThirtyMaxLevel(thirtyMaxLevel);
		setDefaultWeight(weight);
		addDefaultDescription(englishUSDescription);
		lang = Type.LANGUAGE;
	}

	public abstract Enchantment getRelativeEnchantment();

	public static boolean conflictsWith(CustomEnchantment enchOne, CustomEnchantment enchTwo) {
		if (enchOne.conflictsWith(enchTwo) || enchTwo.conflictsWith(enchOne)) return true;
		return false;
	}

	public abstract List<ItemType> getDefaultEnchantmentItemTypes();

	public abstract List<ItemType> getDefaultAnvilItemTypes();

	public List<ItemType> getEnchantmentItemTypes() {
		return enchantmentTypes;
	}

	public List<ItemType> getAnvilItemTypes() {
		return anvilTypes;
	}

	protected abstract List<Enchantment> getDefaultConflictingEnchantments();

	public List<Enchantment> getConflictingEnchantments() {
		if (conflictingEnchantments == null) setConflictingEnchantments();
		List<Enchantment> conflicting = new ArrayList<Enchantment>();
		conflicting.add(getRelativeEnchantment());
		conflicting.addAll(conflictingEnchantments);
		return conflicting;
	}

	public List<String> conflictingDefaultList() {
		List<String> names = new ArrayList<String>();
		if (getDefaultConflictingEnchantments() == null) return names;
		for(Enchantment enchant: getDefaultConflictingEnchantments()) {
			CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchant);
			if (custom != null) names.add(custom.getName());
		}
		return names;
	}

	protected void setConflictingEnchantments() {
		List<Enchantment> enchantments = new ArrayList<Enchantment>();
		enchantments.addAll(getDefaultConflictingEnchantments());
		if (getRelativeEnchantment() != null && enchantments.contains(getRelativeEnchantment())) enchantments.remove(getRelativeEnchantment());
		conflictingEnchantments = enchantments;
	}

	public void setConflictingEnchantments(List<Enchantment> conflictingEnchantments) {
		List<Enchantment> enchantments = new ArrayList<Enchantment>();
		enchantments.addAll(conflictingEnchantments);
		if (getRelativeEnchantment() != null && enchantments.contains(getRelativeEnchantment())) enchantments.remove(getRelativeEnchantment());
		this.conflictingEnchantments = conflictingEnchantments;
	}

	public String getDetails() {
		String page = "\n" + "\n" + Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.name") + getDisplayName() + "\n\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.description") + getDescription() + "\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.max-level") + getMaxLevel() + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.weight") + getWeightName() + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.start-level") + getStartLevel() + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.enchantable-items");
		if (getEnchantmentItemTypes().size() > 0) {
			if (getEnchantmentItemTypes().get(0).equals(ItemType.ALL)) page += getEnchantmentItemTypes().get(0).getDisplayName() + ".\n";
			else {
				boolean includesBooks = false;
				for(ItemType type: getEnchantmentItemTypes()) {
					page += type.getDisplayName() + ", ";
					if (type == ItemType.BOOK) includesBooks = true;
				}
				if (!includesBooks) page += ItemType.BOOK.getDisplayName() + ".\n";
				else
					page = page.substring(0, page.lastIndexOf(", ")) + ".\n";
			}
		} else
			page += ItemType.NONE + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.anvilable-items");
		if (getAnvilItemTypes().size() > 0) {
			if (getAnvilItemTypes().get(0).equals(ItemType.ALL)) page += getAnvilItemTypes().get(0).getDisplayName() + ".\n";
			else {
				boolean includesBooks = false;
				for(ItemType type: getAnvilItemTypes()) {
					page += type.getDisplayName() + ", ";
					if (type == ItemType.BOOK) includesBooks = true;
				}
				if (!includesBooks) page += ItemType.BOOK.getDisplayName() + ".\n";
				else
					page = page.substring(0, page.lastIndexOf(", ")) + ".\n";
			}
		} else
			page += ItemType.NONE + ".\n";

		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.conflicting-enchantments");
		if (getConflictingEnchantments().size() > 0) {
			List<String> names = new ArrayList<String>();
			for(int i = 0; i < getConflictingEnchantments().size(); i++) {
				Enchantment enchant = getConflictingEnchantments().get(i);
				CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchant);
				if (custom != null && !custom.getRelativeEnchantment().equals(getRelativeEnchantment())) names.add(custom.getDisplayName());
			}

			if (names.isEmpty()) page += ConfigUtils.getString(lang, "misc.no_conflicting_enchantments");
			else
				page += StringUtils.join(names, ", ");
			page += ".\n";
		} else
			page += ConfigUtils.getString(lang, "misc.no_conflicting_enchantments") + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.enabled") + ConfigUtils.getString(lang, "misc." + isEnabled()) + ".\n";
		if (getEnchantmentLocations().size() > 0) {
			List<String> names = new ArrayList<String>();
			for(int i = 0; i < getEnchantmentLocations().size(); i++) {
				EnchantmentLocation enchant = getEnchantmentLocations().get(i);
				names.add(Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.locations." + enchant.name().toLowerCase(Locale.ROOT)));
			}

			if (names.isEmpty()) page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.locations_string") + ConfigUtils.getString(lang, "misc.no_enchantment_locations");
			else
				page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.locations_string") + StringUtils.join(names, ", ");
			page += ".\n";
		} else
			page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.locations_string") + ConfigUtils.getString(lang, "misc.no_enchantment_locations") + ".\n";
		return page;
	}

	public boolean canEnchantItem(ItemData item) {
		for(ItemType type: getEnchantmentItemTypes()) {
			if (type instanceof CustomItemType) if (ItemUtils.checkItemType(item, (CustomItemType) type)) return true;
			if (type.getEnchantMaterials() != null && ItemData.contains(type.getEnchantMaterials(), item.getMaterial())) return true;
		}
		return false;
	}

	public boolean canAnvilItem(ItemData item) {
		if (item.getMaterial() == Material.ENCHANTED_BOOK) return true;
		for(ItemType type: getAnvilItemTypes()) {
			if (type instanceof CustomItemType && ItemUtils.checkItemType(item, (CustomItemType) type)) return true;
			if (type.getEnchantMaterials() != null && ItemData.contains(type.getEnchantMaterials(), item.getMaterial())) return true;
		}
		return false;
	}

	protected boolean conflictsWith(CustomEnchantment ench) {
		for(Enchantment enchantment: getConflictingEnchantments())
			if (enchantment.equals(ench.getRelativeEnchantment())) return true;
		return false;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public abstract String getName();

	public String getDisplayName() {
		if (displayName == null) displayName = getDefaultDisplayName(ConfigUtils.getLanguage());
		return displayName;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public int getWeight() {
		return weight.getWeight();
	}

	public String getWeightName() {
		return weight.getName();
	}

	public boolean canAnvil(Player player, int level) {
		if (PermissionUtils.canAnvil(player, this, level)) return true;

		return false;
	}

	public int getAnvilLevel(Player player, int level) {
		while (level > 0) {
			if (PermissionUtils.canAnvil(player, this, level)) return level;
			level--;
		}
		return 0;
	}

	public boolean canEnchant(Player player, int enchantability, int level) {
		if (ConfigUtils.getAdvancedBoolean(ConfigString.STARTING_LEVEL, ConfigString.LEVEL_FIFTY.getBoolean()) && level < getStartLevel()) return false;
		if (getEnchantLevel(player, enchantability) > 0) return true;

		return false;
	}

	public int getEnchantLevel(Player player, int enchantability) {
		for(int i = getMaxLevel(); i > 0; i--) {
			int level = enchantability(i);
			if (PermissionUtils.canEnchant(player, this, i) && RPGUtils.canEnchant(player, this, i) && enchantability >= level) return i;
		}
		return 0;
	}

	public int getMaxLevel(Player player) {
		for(int i = getMaxLevel(); i > 0; i--)
			if (PermissionUtils.canEnchant(player, this, i) && RPGUtils.canEnchant(player, this, i)) return i;
		return 0;
	}

	public int enchantability(int level) {
		return modifier * level + constant;
	}

	public int multiplier(Material material) {
		if (!(material.equals(Material.BOOK) || material.equals(Material.ENCHANTED_BOOK))) return weight.getBook();
		return weight.getItem();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setDisplayName(String name) {
		displayName = name;
	}

	public void setDisplayName(Language lang) {
		displayName = getDefaultDisplayName(lang);
	}

	protected int getDefaultThirtyConstant() {
		return defaultThirtyConstant;
	}

	protected void setDefaultThirtyConstant(int constant) {
		defaultThirtyConstant = constant;
	}

	protected int getDefaultFiftyConstant() {
		return defaultFiftyConstant;
	}

	protected void setDefaultFiftyConstant(int constant) {
		defaultFiftyConstant = constant;
	}

	public int getDefaultConstant() {
		if (ConfigString.LEVEL_FIFTY.getBoolean()) return defaultFiftyConstant;
		return defaultThirtyConstant;
	}

	protected void setConstant(int constant) {
		this.constant = constant;
	}

	protected int getDefaultThirtyModifier() {
		return defaultThirtyModifier;
	}

	protected void setDefaultThirtyModifier(int modifier) {
		defaultThirtyModifier = modifier;
	}

	protected int getDefaultFiftyModifier() {
		return defaultFiftyModifier;
	}

	protected void setDefaultFiftyModifier(int modifier) {
		defaultFiftyModifier = modifier;
	}

	public int getDefaultModifier() {
		if (ConfigString.LEVEL_FIFTY.getBoolean()) return defaultFiftyModifier;
		return defaultThirtyModifier;
	}

	protected void setModifier(int modifier) {
		this.modifier = modifier;
	}

	protected int getDefaultThirtyStartLevel() {
		return defaultThirtyStartLevel;
	}

	protected void setDefaultThirtyStartLevel(int startLevel) {
		defaultThirtyStartLevel = startLevel;
	}

	protected int getDefaultFiftyStartLevel() {
		return defaultFiftyStartLevel;
	}

	protected void setDefaultFiftyStartLevel(int startLevel) {
		defaultFiftyStartLevel = startLevel;
	}

	public int getDefaultStartLevel() {
		if (ConfigString.LEVEL_FIFTY.getBoolean()) return defaultFiftyStartLevel;
		return defaultThirtyStartLevel;
	}

	protected void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	protected int getDefaultThirtyMaxLevel() {
		return defaultThirtyMaxLevel;
	}

	protected void setDefaultThirtyMaxLevel(int maxLevel) {
		defaultThirtyMaxLevel = maxLevel;
	}

	protected int getDefaultFiftyMaxLevel() {
		return defaultFiftyMaxLevel;
	}

	protected void setDefaultFiftyMaxLevel(int maxLevel) {
		defaultFiftyMaxLevel = maxLevel;
	}

	public int getDefaultMaxLevel() {
		if (ConfigString.LEVEL_FIFTY.getBoolean()) return defaultFiftyMaxLevel;
		return defaultThirtyMaxLevel;
	}

	protected void setMaxLevel(int maxLevel) {
		if (isMaxLevelOne()) this.maxLevel = 1;
		else
			this.maxLevel = maxLevel;
	}

	void setLevelFifty(List<ItemType> enchantmentTypes, List<ItemType> anvilTypes, List<EnchantmentLocation> locations) {
		setConstant(getDefaultFiftyConstant());
		setModifier(getDefaultFiftyModifier());
		setStartLevel(getDefaultFiftyStartLevel());
		setMaxLevel(getDefaultFiftyMaxLevel());
		setWeight(null);
		setEnchantmentItemTypes(enchantmentTypes);
		setAnvilItemTypes(anvilTypes);
		setEnchantmentLocations(locations);
	}

	void setLevelThirty(List<ItemType> enchantmentTypes, List<ItemType> anvilTypes, List<EnchantmentLocation> locations) {
		setConstant(getDefaultThirtyConstant());
		setModifier(getDefaultThirtyModifier());
		setStartLevel(getDefaultThirtyStartLevel());
		setMaxLevel(getDefaultThirtyMaxLevel());
		setWeight(null);
		setEnchantmentItemTypes(enchantmentTypes);
		setAnvilItemTypes(anvilTypes);
		setEnchantmentLocations(locations);
	}

	void setCustom(int constant, int modifier, int startLevel, int maxLevel, Weight weight, List<ItemType> enchantmentTypes, List<ItemType> anvilTypes,
	List<EnchantmentLocation> locations) {
		setConstant(constant);
		setModifier(modifier);
		setStartLevel(startLevel);
		setMaxLevel(maxLevel);
		setWeight(weight);
		setEnchantmentItemTypes(enchantmentTypes);
		setAnvilItemTypes(anvilTypes);
		setEnchantmentLocations(locations);
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
		weight = defaultWeight;
	}

	protected void setWeight(Weight weight) {
		if (weight != null) this.weight = weight;
		else
			this.weight = defaultWeight;
	}

	public String getDefaultDisplayName(Language lang) {
		String english = null;
		for(EnchantmentDisplayName d: defaultDisplayNames) {
			if (lang == d.getLanguage()) return d.getDisplayName();
			if (d.getLanguage() == Language.US) english = d.getDisplayName();
		}
		return english;
	}

	protected void addDefaultDisplayName(EnchantmentDisplayName defaultDisplayName) {
		for(EnchantmentDisplayName displayName: defaultDisplayNames)
			if (displayName.getLanguage() == defaultDisplayName.getLanguage()) {
				displayName.setDescription(defaultDisplayName.getDisplayName());
				return;
			}
		defaultDisplayNames.add(defaultDisplayName);
	}

	protected void addDefaultDisplayName(Language lang, String name) {
		addDefaultDisplayName(new EnchantmentDisplayName(lang, name));
	}

	protected void addDefaultDisplayName(String name) {
		addDefaultDisplayName(new EnchantmentDisplayName(Language.US, name));
	}

	public boolean isCurse() {
		return curse;
	}

	protected void setCurse(boolean curse) {
		this.curse = curse;
	}

	public String getDescription() {
		if (description == null) return getDefaultDescription(ConfigUtils.getLanguage());
		return description;
	}

	public String getAdvancementDescription() {
		String page = Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.advancement-description") + getDescription() + "\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.advancement-max-level") + getMaxLevel() + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.advancement-weight") + getWeightName() + ".\n";
		page += Chatable.get().getMessage(ChatUtils.getCodes(), "enchantment.advancement-conflicting-enchantments");
		if (getConflictingEnchantments().size() > 0) {
			List<String> names = new ArrayList<String>();
			for(int i = 0; i < getConflictingEnchantments().size(); i++) {
				Enchantment enchant = getConflictingEnchantments().get(i);
				CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchant);
				if (custom != null && !custom.getRelativeEnchantment().equals(getRelativeEnchantment())) names.add(custom.getDisplayName());
			}

			if (names.isEmpty()) page += ConfigUtils.getString(lang, "misc.no_conflicting_enchantments");
			else
				page += StringUtils.join(names, ", ");
			page += ".";
		} else
			page += ConfigUtils.getString(lang, "misc.no_conflicting_enchantments") + ".";
		return page;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultDescription(Language lang) {
		String english = null;
		for(EnchantmentDescription d: defaultDescriptions) {
			if (lang == d.getLanguage()) return d.getDescription();
			if (d.getLanguage() == Language.US) english = d.getDescription();
		}
		return english;
	}

	protected void addDefaultDescription(Language lang, String desc) {
		addDefaultDescription(new EnchantmentDescription(lang, desc));
	}

	protected void addDefaultDescription(String desc) {
		addDefaultDescription(new EnchantmentDescription(Language.US, desc));
	}

	protected void addDefaultDescription(EnchantmentDescription defaultDescription) {
		for(EnchantmentDescription description: defaultDescriptions)
			if (description.getLanguage().equals(defaultDescription.getLanguage())) {
				description.setDescription(defaultDescription.getDescription());
				return;
			}
		defaultDescriptions.add(defaultDescription);
	}

	private void setEnchantmentItemTypes(List<ItemType> types) {
		enchantmentTypes = types;
	}

	private void setAnvilItemTypes(List<ItemType> types) {
		anvilTypes = types;
	}

	public List<EnchantmentLocation> getEnchantmentLocations() {
		return enchantmentLocations;
	}

	public void setEnchantmentLocations(List<EnchantmentLocation> locations) {
		enchantmentLocations = locations;
	}

	public abstract List<EnchantmentLocation> getDefaultEnchantmentLocations();

}
