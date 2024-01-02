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
import org.ctp.enchantmentsolution.events.potion.PlyometricsEquipEvent;
import org.ctp.enchantmentsolution.events.potion.PlyometricsUnequipEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.InfinitePotionEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class Plyometrics extends InfinitePotionEffect {

	public Plyometrics() {
		super(RegisterEnchantments.PLYOMETRICS, EnchantmentMultipleType.STACK, EnchantmentItemLocation.WEARING, EventPriority.NORMAL, PotionEffectType.JUMP, true, new EquipCondition[0]);
	}

	@Override
	public InfinitePotionResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		InfinitePotionResult result = super.run(entity, items, oldLevel, event);
		int level = result.getLevel();

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			PlyometricsUnequipEvent unequip = new PlyometricsUnequipEvent((Player) entity, level);
			Bukkit.getPluginManager().callEvent(unequip);
			if (!unequip.isCancelled()) {
				((LivingEntity) entity).removePotionEffect(getEffectType());
				PlyometricsEquipEvent equip = new PlyometricsEquipEvent((Player) entity, level, result.getEffect());
				Bukkit.getPluginManager().callEvent(equip);
				if (!equip.isCancelled()) {
					PotionEffect e = new PotionEffect(equip.getType(), equip.getDuration(), equip.getAmplifier());
					if (e.apply((LivingEntity) entity)) return new InfinitePotionResult(level, equip.getPotionEffect());
				}
			}
		} else if (level > oldLevel) {
			PlyometricsEquipEvent plyometrics = new PlyometricsEquipEvent((Player) entity, level, result.getEffect());
			Bukkit.getPluginManager().callEvent(plyometrics);
			if (!plyometrics.isCancelled()) {
				PotionEffect e = new PotionEffect(plyometrics.getType(), plyometrics.getDuration(), plyometrics.getAmplifier());
				if (e.apply((LivingEntity) entity)) return new InfinitePotionResult(level, plyometrics.getPotionEffect());
			}
		} else if (oldLevel > 0 && level == 0) {
			PlyometricsUnequipEvent plyometrics = new PlyometricsUnequipEvent((Player) entity, level);
			Bukkit.getPluginManager().callEvent(plyometrics);
			if (!plyometrics.isCancelled()) {
				((LivingEntity) entity).removePotionEffect(getEffectType());
				return new InfinitePotionResult(level, new ConfigPotionEffect(getEffectType(), "0", "0"));
			}
		}

		return null;
	}

}
