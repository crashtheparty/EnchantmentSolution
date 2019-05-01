package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class DepthStrider extends CustomEnchantment{
	
	public DepthStrider() {
		addDefaultDisplayName("Depth Strider");
		addDefaultDisplayName(Language.GERMAN, "Wasserläufer");
		setDefaultFiftyConstant(5);
		setDefaultThirtyConstant(0);
		setDefaultFiftyModifier(15);
		setDefaultThirtyModifier(10);
		setDefaultFiftyStartLevel(10);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(3);
		setDefaultWeight(Weight.RARE);
		addDefaultDescription("Increases underwater movement speed.");
		addDefaultDescription(Language.GERMAN, "Erhöht die Bewegungsgeschwindigkeit unter Wasser.");
	}

	@Override
	public String getName() {
		return "depth_strider";
	}

	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.DEPTH_STRIDER;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.BOOTS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList(Enchantment.FROST_WALKER, DefaultEnchantments.MAGMA_WALKER, DefaultEnchantments.VOID_WALKER);
	}
}
