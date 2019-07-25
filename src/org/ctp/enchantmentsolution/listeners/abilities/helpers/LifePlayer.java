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

public class LifePlayer extends AbilityPlayer{

	public LifePlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.LIFE);
	}

	protected void doEquip(ItemStack item) {
		if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.LIFE);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "life_health", 
					4 * level, Operation.ADD_NUMBER);
			AttributeModifier legacyModifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "generic.maxHealth", 
					4 * level, Operation.ADD_NUMBER);
			if(a.getModifiers().contains(legacyModifier)) {
				a.removeModifier(legacyModifier);
			}
			if(!a.getModifiers().contains(modifier)) {
				a.addModifier(modifier);
			}
		}
	}

	protected void doUnequip(ItemStack item) {
		if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.LIFE);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "life_health", 
					4 * level, Operation.ADD_NUMBER);
			AttributeModifier legacyModifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "generic.maxHealth", 
					4 * level, Operation.ADD_NUMBER);
			if(a.getModifiers().contains(modifier)) {
				a.removeModifier(modifier);
			}
			if(a.getModifiers().contains(legacyModifier)) {
				a.removeModifier(legacyModifier);
			}
			if(getPlayer().getHealth() > a.getBaseValue()) {
				getPlayer().setHealth(a.getBaseValue());
			}
		}
	}

}
