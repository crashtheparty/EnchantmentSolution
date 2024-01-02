package org.ctp.enchantmentsolution.enchantments.config;

import java.util.ArrayList;
import java.util.List;

import org.ctp.crashapi.item.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public class ConfigEnchantment extends CustomEnchantment {

	private final List<ItemType> enchantmentTypes, anvilTypes;
	private final ConfigEnchantmentWrapper relativeEnchantment;
	private final List<String> conflictingEnchantments;
	private final EnchantmentLocation[] defaultEnchantmentLocations;

	public ConfigEnchantment(ConfigEnchantmentWrapper wrapper, List<ItemType> enchantmentTypes, List<ItemType> anvilTypes,
	List<String> conflictingEnchantments, EnchantmentLocation[] enchantmentLocations, String englishUSDisplayName, int[] mewl, Weight weight,
	String englishUSDescription) {
		super(englishUSDisplayName, mewl[0], mewl[1], mewl[2], mewl[3], mewl[4], mewl[5], mewl[6], mewl[7], weight, englishUSDescription);
		relativeEnchantment = wrapper;
		this.enchantmentTypes = enchantmentTypes;
		this.anvilTypes = anvilTypes;
		this.conflictingEnchantments = conflictingEnchantments;
		defaultEnchantmentLocations = enchantmentLocations;
	}

	@Override
	public ConfigEnchantmentWrapper getRelativeEnchantment() {
		return relativeEnchantment;
	}

	@Override
	public List<ItemType> getDefaultEnchantmentItemTypes() {
		return enchantmentTypes;
	}

	@Override
	public List<ItemType> getDefaultAnvilItemTypes() {
		return anvilTypes;
	}

	public List<String> getDefaultConflictingEnchantmentsStrings() {
		return conflictingEnchantments;
	}

	@Override
	protected List<EnchantmentWrapper> getDefaultConflictingEnchantments() {
		List<EnchantmentWrapper> enchantments = new ArrayList<EnchantmentWrapper>();
		for(String s: conflictingEnchantments) {
			CustomEnchantment e = RegisterEnchantments.getByName(s);
			if (e != null) enchantments.add(e.getRelativeEnchantment());
		}
		return enchantments;
	}

	@Override
	public String getName() {
		return relativeEnchantment.getName();
	}

	@Override
	public EnchantmentLocation[] getDefaultEnchantmentLocations() {
		return defaultEnchantmentLocations;
	}

}
