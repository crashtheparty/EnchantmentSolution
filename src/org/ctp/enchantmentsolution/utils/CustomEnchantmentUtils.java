package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.config.ConfigEnchantment;
import org.ctp.enchantmentsolution.enchantments.config.EnchantmentDetails;
import org.ctp.enchantmentsolution.enchantments.config.EnchantmentDetails.DetailsType;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDescription;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDisplayName;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.*;
import org.ctp.enchantmentsolution.utils.config.CustomEnchantmentsConfiguration;

public class CustomEnchantmentUtils {

	public static List<CustomEnchantmentWrapper> CONFIG_ENCHANTMENTS = new ArrayList<CustomEnchantmentWrapper>();
	private static List<EnchantmentDetails> REGISTERED = new ArrayList<EnchantmentDetails>();

	public static void registerEnchantments() {
		CustomEnchantmentsConfiguration config = Configurations.getCustomEnchantments();
		List<String> registerEnchantments = config.getStringList("register_enchantments");
		if (registerEnchantments != null) start: for(String name: registerEnchantments) {
			Iterator<EnchantmentDetails> iter = REGISTERED.iterator();
			while(iter.hasNext()) {
				EnchantmentDetails details = iter.next();
				if (details.getEnchantment().getName().equals(name)) {
					details.update();
					try {
						details.isValid();
					} catch (RuntimeException ex) {
						ex.printStackTrace();
						iter.remove();
						CONFIG_ENCHANTMENTS.remove(details.getEnchantment().getRelativeEnchantment());
					}
					continue start;
				}
			}

			CustomEnchantment ench = generateNewEnchantment(name);
			if (ench != null) RegisterEnchantments.addDefaultEnchantment(ench);
		}
	}

	private static CustomEnchantment generateNewEnchantment(String name) {
		CustomEnchantmentsConfiguration config = Configurations.getCustomEnchantments();
		String displayName = config.getString(name + ".display-names.en_us");
		if (displayName == null) throw new NullPointerException("The value for '" + name + ".display-names.en_us' cannot be null or not a string.");
		String description = config.getString(name + ".descriptions.en_us");
		if (description == null) throw new NullPointerException("The value for '" + name + ".description.en_us' cannot be null or not a string.");
		List<EnchantmentDisplayName> displayNames = new ArrayList<EnchantmentDisplayName>();
		for(String s: config.getKeys(name + ".display-names"))
			if (!s.equals("en_us")) {
				Language lang = Language.getLanguage(s);
				if (lang != null) displayNames.add(new EnchantmentDisplayName(lang, config.getString(name + ".display-names." + s)));
			}
		List<EnchantmentDescription> descriptions = new ArrayList<EnchantmentDescription>();
		for(String s: config.getKeys(name + ".descriptions"))
			if (!s.equals("en_us")) {
				Language lang = Language.getLanguage(s);
				if (lang != null) descriptions.add(new EnchantmentDescription(lang, config.getString(name + ".descriptions." + s)));
			}
		List<String> eits = config.getStringList(name + ".enchantment-item-types");
		List<String> aits = config.getStringList(name + ".anvil-item-types");
		List<ItemType> enchantmentTypes = new ArrayList<ItemType>();
		List<ItemType> anvilTypes = new ArrayList<ItemType>();
		if (eits != null) for(String s: eits) {
			ItemType type = null;
			if (s.contains(":")) type = ItemType.getCustomType(VanillaItemType.get(s.toUpperCase()), s.toUpperCase());
			else
				type = ItemType.getItemType(s.toUpperCase());
			if (type != null) enchantmentTypes.add(type);
		}
		if (aits != null) for(String s: aits) {
			ItemType type = null;
			if (s.contains(":")) type = ItemType.getCustomType(VanillaItemType.get(s.toUpperCase()), s.toUpperCase());
			else
				type = ItemType.getItemType(s.toUpperCase());
			if (type != null) anvilTypes.add(type);
		}
		Weight weight = Weight.getWeight(config.getString(name + ".weight"));
		if (weight == null) throw new NullPointerException("The value for '" + name + ".weight' must be a valid weight: " + Weight.strings() + ".");
		List<String> conflicting = config.getStringList(name + ".conflicting-enchantments");
		List<String> locations = config.getStringList(name + ".enchantment-locations");
		List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
		List<EnchantmentLocation> enchantmentLocations = new ArrayList<EnchantmentLocation>();
		if (conflicting != null) for(String s: conflicting) {
			CustomEnchantment custom = RegisterEnchantments.getByName(s);
			if (custom == null) continue;
			Enchantment ench = custom.getRelativeEnchantment();
			if (ench != null) conflictingEnchantments.add(ench);
		}
		if (locations != null) for(String s: locations) {
			EnchantmentLocation loc = EnchantmentLocation.getLocation(s);
			if (loc != null) enchantmentLocations.add(loc);
		}
		int maxLevel30 = config.getInt(name + ".level-30.max-level");
		int maxLevel50 = config.getInt(name + ".level-50.max-level");
		int starting30 = config.getInt(name + ".level-30.starting-level");
		int starting50 = config.getInt(name + ".level-50.starting-level");
		int modifier30 = config.getInt(name + ".level-30.enchantability-modifier");
		int modifier50 = config.getInt(name + ".level-50.enchantability-modifier");
		int constant30 = config.getInt(name + ".level-30.enchantability-constant");
		int constant50 = config.getInt(name + ".level-50.enchantability-constant");
		CustomEnchantmentWrapper wrapper = new CustomEnchantmentWrapper(EnchantmentSolution.getPlugin(), name, displayName);
		ConfigEnchantment ench = new ConfigEnchantment(name, displayName, description, wrapper, new int[] { constant50, constant30, modifier50, modifier30, starting50, starting30, maxLevel50, maxLevel30 }, weight, displayNames, descriptions, enchantmentTypes, anvilTypes, conflictingEnchantments, false, false, enchantmentLocations);
		String type = config.getString(name + ".data.type");
		if (type == null) throw new NullPointerException("The value for '" + name + ".data.type' must contain a data type: damage.");
		type = type.toLowerCase();
		DetailsType dataType = DetailsType.getType(type);
		if (dataType == null) throw new NullPointerException("The value for '" + name + ".data.type' must be a valid data type: damage.");
		boolean playersOnly = config.getBoolean(name + ".data." + type + ".players-only");
		boolean flatScale = config.getBoolean(name + ".data." + type + ".flat-scale");
		List<String> mobs = config.getStringList(name + ".data." + type + ".mobs");
		EnchantmentDetails details = getDetails(ench, dataType, playersOnly, flatScale, mobs);
		if (details != null) {
			REGISTERED.add(details);
			CONFIG_ENCHANTMENTS.add(wrapper);
			return ench;
		}
		return null;
	}

	private static EnchantmentDetails getDetails(ConfigEnchantment ench, DetailsType type, boolean playersOnly, boolean flatScale, List<String> mobs) {
		EnchantmentDetails details = new EnchantmentDetails(ench, type, playersOnly, flatScale, mobs);
		if (details.isValid()) return details;
		throw new RuntimeException("The expression for " + ench.getName() + " will not function correctly: caused exception.");
	}

	public static List<EnchantmentDetails> getEnchantmentDetails() {
		return REGISTERED;
	}
}
