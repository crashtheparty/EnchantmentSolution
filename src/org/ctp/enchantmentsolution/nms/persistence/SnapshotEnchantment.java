package org.ctp.enchantmentsolution.nms.persistence;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.crashapi.item.ItemType;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public class SnapshotEnchantment extends CustomEnchantment {

	private Enchantment relative;

	public SnapshotEnchantment(Enchantment relative) {
		super(relative.getKey().getKey(), 0, 0, 0, 0, 0, 0, 0, 0, Weight.NULL, "");
		this.relative = relative;
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return relative;
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return null;
	}

	@Override
	public String getName() {
		return relative.getKey().getKey();
	}

	@Override
	public List<ItemType> getDefaultEnchantmentItemTypes() {
		return null;
	}

	@Override
	public List<ItemType> getDefaultAnvilItemTypes() {
		return null;
	}

	@Override
	public List<EnchantmentLocation> getDefaultEnchantmentLocations() {
		return null;
	}

}