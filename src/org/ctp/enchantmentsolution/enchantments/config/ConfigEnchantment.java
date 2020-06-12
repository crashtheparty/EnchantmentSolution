package org.ctp.enchantmentsolution.enchantments.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDescription;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentDisplayName;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ConfigEnchantment extends CustomEnchantment {

	private final Enchantment relative;
	private List<ItemType> enchantmentItems, anvilItems;
	private List<Enchantment> conflictingEnchantments;
	private String name;

	public ConfigEnchantment(String name, String englishDisplay, String englishDescription, Enchantment relative,
	int[] enchantability, Weight weight, List<EnchantmentDisplayName> names, List<EnchantmentDescription> descriptions,
	List<ItemType> enchantmentItems, List<ItemType> anvilItems, List<Enchantment> conflictingEnchantments, List<EnchantmentLocation> locations) {
		this(name, englishDisplay, englishDescription, relative, enchantability, weight, names, descriptions, enchantmentItems, anvilItems, conflictingEnchantments, false, false, locations);
	}

	public ConfigEnchantment(String name, String englishDisplay, String englishDescription, Enchantment relative,
	int[] enchantability, Weight weight, List<EnchantmentDisplayName> names, List<EnchantmentDescription> descriptions,
	List<ItemType> enchantmentItems, List<ItemType> anvilItems, List<Enchantment> conflictingEnchantments,
	boolean curse, boolean maxLevelOne, List<EnchantmentLocation> locations) {
		super(englishDisplay, enchantability[0], enchantability[1], enchantability[2], enchantability[3], enchantability[4], enchantability[5], enchantability[6], enchantability[7], weight, englishDescription);
		this.name = name;
		this.relative = relative;
		this.enchantmentItems = enchantmentItems;
		this.anvilItems = anvilItems;
		this.conflictingEnchantments = conflictingEnchantments;
		for(EnchantmentDisplayName n: names)
			addDefaultDisplayName(n);
		for(EnchantmentDescription d: descriptions)
			addDefaultDescription(d);
		setCurse(curse);
		setMaxLevelOne(maxLevelOne);
		defaultEnchantmentLocations = locations;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return relative;
	}

	@Override
	public List<ItemType> getDefaultEnchantmentItemTypes() {
		return enchantmentItems;
	}

	@Override
	public List<ItemType> getDefaultAnvilItemTypes() {
		return anvilItems;
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		if (ConfigString.PROTECTION_CONFLICTS.getBoolean()) {
			List<Enchantment> enchants = RegisterEnchantments.getProtectionEnchantments();
			List<Enchantment> enchantments = new ArrayList<Enchantment>();
			if (enchants.contains(getRelativeEnchantment())) {
				for(Enchantment enchant: conflictingEnchantments)
					if (!enchantments.contains(enchant)) enchantments.add(enchant);
				for(Enchantment enchant: enchants)
					if (!enchantments.contains(enchant)) enchantments.add(enchant);
				return enchantments;
			}
		}
		return conflictingEnchantments;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<EnchantmentLocation> getDefaultEnchantmentLocations() {
		return defaultEnchantmentLocations;
	}
}
