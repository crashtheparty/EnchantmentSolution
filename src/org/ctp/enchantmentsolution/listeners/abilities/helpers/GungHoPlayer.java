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

public class GungHoPlayer extends AbilityPlayer{

	public GungHoPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.GUNG_HO);
	}

	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"), "generic.maxHealth", 
					-1 * a.getDefaultValue() / 2, Operation.ADD_NUMBER);
			addModifier(a, modifier);
			if(getPlayer().getHealth() > a.getBaseValue()) {
				getPlayer().setHealth(a.getBaseValue());
			}
		}
	}

	protected void doUnequip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"), "generic.maxHealth", 
					-1 * a.getDefaultValue() / 2, Operation.ADD_NUMBER);
			removeModifier(a, modifier);
		}
	}
	
	@Override
	protected void doUnequip() {
		AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
		AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-ffff-ffff-ffff-000000000000"), "generic.maxHealth", 
				-1 * a.getDefaultValue() / 2, Operation.ADD_NUMBER);
		removeModifier(a, modifier);
	}

}
