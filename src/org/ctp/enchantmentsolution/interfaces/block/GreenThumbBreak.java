package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.AgeableWithinAgeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.BlockIsAgeableCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.PreventBlockBreakEffect;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Crop;

public class GreenThumbBreak extends PreventBlockBreakEffect {

	public GreenThumbBreak() {
		super(RegisterEnchantments.GREEN_THUMB, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, new BlockCondition[] { new BlockIsAgeableCondition(false), new AgeableWithinAgeCondition(false, 0, 0) });
	}

	@Override
	public PreventBlockBreakResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		PreventBlockBreakResult result = super.run(player, items, brokenData, event);
		if (result.getLevel() == 0) return null;

		Ageable age = (Ageable) brokenData;
		Material mat = event.getBlock().getType();
		if (Crop.hasBlock(mat) && age.getAge() == 0) {
			event.setCancelled(true);

			return new PreventBlockBreakResult(result.getLevel());
		}

		return null;
	}

}
