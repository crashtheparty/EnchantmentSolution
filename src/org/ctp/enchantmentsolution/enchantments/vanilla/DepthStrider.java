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
		super("Depth Strider", 5, 0, 15, 10, 10, 1, 3, 3, Weight.RARE, "Increases underwater movement speed.");
		addDefaultDisplayName(Language.GERMAN, "Wasserläufer");
		addDefaultDescription(Language.GERMAN, "Erhöht die Bewegungsgeschwindigkeit unter Wasser.");
		addDefaultDisplayName(Language.CHINA_SIMPLE, "深海探索者");
		addDefaultDescription(Language.CHINA_SIMPLE, "增加水下移速.");
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
