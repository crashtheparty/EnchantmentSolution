package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class QuickStrikePlayer extends AbilityPlayer{

	public QuickStrikePlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.QUICK_STRIKE);
	}

	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, DefaultEnchantments.QUICK_STRIKE)){
			int level = Enchantments.getLevel(item, DefaultEnchantments.QUICK_STRIKE);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), "quick_strike_armor", 
					0.5 * level, Operation.ADD_SCALAR);
			addModifier(a, modifier);
		}
	}

	protected void doUnequip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, DefaultEnchantments.QUICK_STRIKE)){
			int level = Enchantments.getLevel(item, DefaultEnchantments.QUICK_STRIKE);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), "quick_strike_armor", 
					0.5 * level, Operation.ADD_SCALAR);
			removeModifier(a, modifier);
		}
	}
	
	@Override
	protected void doUnequip() {
		AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
		AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "quick_strike_armor", 
				2, Operation.ADD_NUMBER);
		removeModifier(a, modifier);
	}
}
