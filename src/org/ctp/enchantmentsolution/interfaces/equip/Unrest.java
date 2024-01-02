package org.ctp.enchantmentsolution.interfaces.equip;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.crashapi.events.EquipEvent.EquipMethod;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.potion.UnrestEquipEvent;
import org.ctp.enchantmentsolution.events.potion.UnrestUnequipEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.InfinitePotionEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class Unrest extends InfinitePotionEffect {

	public Unrest() {
		super(RegisterEnchantments.UNREST, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.WEARING, EventPriority.NORMAL, PotionEffectType.NIGHT_VISION, false, new EquipCondition[0]);
	}

	@Override
	public InfinitePotionResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		InfinitePotionResult result = super.run(entity, items, oldLevel, event);
		int level = result.getLevel();

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			UnrestUnequipEvent unequip = new UnrestUnequipEvent((Player) entity);
			Bukkit.getPluginManager().callEvent(unequip);
			if (!unequip.isCancelled()) {
				((LivingEntity) entity).removePotionEffect(getEffectType());
				UnrestEquipEvent equip = new UnrestEquipEvent((Player) entity, result.getEffect());
				Bukkit.getPluginManager().callEvent(equip);
				if (!equip.isCancelled()) {
					PotionEffect e = new PotionEffect(equip.getType(), equip.getDuration(), equip.getAmplifier());
					if (e.apply((LivingEntity) entity)) return new InfinitePotionResult(level, equip.getPotionEffect());
				}
			}
		} else if (level > oldLevel) {
			UnrestEquipEvent unrest = new UnrestEquipEvent((Player) entity, result.getEffect());
			Bukkit.getPluginManager().callEvent(unrest);
			if (!unrest.isCancelled()) {
				PotionEffect e = new PotionEffect(unrest.getType(), unrest.getDuration(), unrest.getAmplifier());
				if (e.apply((LivingEntity) entity)) return new InfinitePotionResult(level, unrest.getPotionEffect());
			}
		} else if (oldLevel > 0 && level == 0) {
			UnrestUnequipEvent unrest = new UnrestUnequipEvent((Player) entity);
			Bukkit.getPluginManager().callEvent(unrest);
			if (!unrest.isCancelled()) {
				((LivingEntity) entity).removePotionEffect(getEffectType());
				return new InfinitePotionResult(level, new ConfigPotionEffect(getEffectType(), "0", "0"));
			}
		}

		return null;
	}
}