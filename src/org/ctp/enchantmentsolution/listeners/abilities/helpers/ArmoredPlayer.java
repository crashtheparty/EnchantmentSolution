package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class ArmoredPlayer extends AbilityPlayer{

	public ArmoredPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.ARMORED);
	}

	@Override
	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.ARMORED);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
					2 * level, Operation.ADD_NUMBER);
			if(!a.getModifiers().contains(modifier)) {
				a.addModifier(modifier);
				AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.ARMORED_EVOLUTION, "armored");
			}
		}
	}

	@Override
	protected void doUnequip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.ARMORED);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
					2 * level, Operation.ADD_NUMBER);
			if(a.getModifiers().contains(modifier)) {
				a.removeModifier(modifier);
			}
		}
	}

}
