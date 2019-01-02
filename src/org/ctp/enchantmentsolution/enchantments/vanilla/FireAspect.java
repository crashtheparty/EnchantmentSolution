package org.ctp.enchantmentsolution.enchantments.vanilla;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Weight;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class FireAspect extends CustomEnchantment{
	
	public FireAspect() {
		setDefaultDisplayName("Fire Aspect");
		setDefaultFiftyConstant(-25);
		setDefaultThirtyConstant(-10);
		setDefaultFiftyModifier(30);
		setDefaultThirtyModifier(20);
		setDefaultFiftyMaxConstant(50);
		setDefaultThirtyMaxConstant(50);
		setDefaultFiftyStartLevel(5);
		setDefaultThirtyStartLevel(1);
		setDefaultFiftyMaxLevel(3);
		setDefaultThirtyMaxLevel(2);
		setDefaultWeight(Weight.RARE);
		setDefaultDescription("Sets the target on fire." + 
				StringUtils.LF + 
				"Fire Aspect adds 80 fireticks (4 seconds of burning) per level to the target.");
	}

	@Override
	public String getName() {
		return "fire_aspect";
	}
	
	@Override
	public Enchantment getRelativeEnchantment() {
		return Enchantment.FIRE_ASPECT;
	}
	
	@Override
	protected List<ItemType> getEnchantmentItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<ItemType> getAnvilItemTypes() {
		return Arrays.asList(ItemType.SWORDS);
	}

	@Override
	protected List<Enchantment> getDefaultConflictingEnchantments() {
		return Arrays.asList();
	}
}
