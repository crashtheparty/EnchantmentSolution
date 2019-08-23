package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.events.AbilityEquipEvent;
import org.ctp.enchantmentsolution.events.AbilityUnequipEvent;
import org.ctp.enchantmentsolution.events.SlotType;

public class ArmoredPlayer extends AbilityPlayer{

	public ArmoredPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.ARMORED);
	}

	@Override
	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			AbilityEquipEvent event = new AbilityEquipEvent(getPlayer(), item, getEnchantment(), SlotType.CHESTPLATE);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) return;
			if(!Enchantments.hasEnchantment(event.getItem(), getEnchantment()) || Enchantments.getLevel(event.getItem(), getEnchantment()) < 1) return;
			int level = Enchantments.getLevel(event.getItem(), getEnchantment());
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
					2 * level, Operation.ADD_NUMBER);
			addModifier(a, modifier);
		}
	}

	@Override
	protected void doUnequip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			AbilityUnequipEvent event = new AbilityUnequipEvent(getPlayer(), item, getEnchantment(), SlotType.CHESTPLATE);
			Bukkit.getPluginManager().callEvent(event);
			int level = Enchantments.getLevel(event.getItem(), getEnchantment());
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
			AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
					2 * level, Operation.ADD_NUMBER);
			removeModifier(a, modifier);
		}
	}
	
	@Override
	protected void doUnequip() {
		AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
		AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
				2, Operation.ADD_NUMBER);
		removeModifier(a, modifier);
	}

}
