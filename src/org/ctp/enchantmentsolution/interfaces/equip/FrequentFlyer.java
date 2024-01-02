package org.ctp.enchantmentsolution.interfaces.equip;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.crashapi.events.EquipEvent.EquipMethod;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.potion.FrequentFlyerEquipEvent;
import org.ctp.enchantmentsolution.events.potion.FrequentFlyerUnequipEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.equip.InfiniteCustomPotionEffect;
import org.ctp.enchantmentsolution.utils.player.ESEntity;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class FrequentFlyer extends InfiniteCustomPotionEffect {

	public FrequentFlyer() {
		super(RegisterEnchantments.FREQUENT_FLYER, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.WEARING, EventPriority.HIGHEST, CustomPotionEffectType.FLIGHT, true, new EquipCondition[0]);
	}
	
	@Override
	public InfiniteCustomPotionResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		InfiniteCustomPotionResult result = super.run(entity, items, oldLevel, event);
		int level = result.getLevel();
		
		ESEntity esEntity = EnchantmentSolution.getESEntity((LivingEntity) entity);

		if (oldLevel > level && level > 0 || event.getMethod() == EquipMethod.JOIN && level > 0) {
			FrequentFlyerUnequipEvent unequip = new FrequentFlyerUnequipEvent((Player) entity, level);
			Bukkit.getPluginManager().callEvent(unequip);
			if (!unequip.isCancelled()) {
				CustomPotionEffect e = new CustomPotionEffect((LivingEntity) entity, unequip.getType(), 0, 0, new EnchantmentLevel(CERegister.FREQUENT_FLYER, level));
				esEntity.removeCustomPotionEffect(e, level);
				FrequentFlyerEquipEvent equip = new FrequentFlyerEquipEvent((Player) entity, level, result.getEffect());
				Bukkit.getPluginManager().callEvent(equip);
				if (!equip.isCancelled()) {
					CustomPotionEffect e2 = new CustomPotionEffect((LivingEntity) entity, equip.getType(), equip.getAmplifier(), equip.getDuration(), new EnchantmentLevel(CERegister.FREQUENT_FLYER, level));
					if (esEntity.addCustomPotionEffect(e2, true) != null) return new InfiniteCustomPotionResult(level, equip.getPotionEffect());
				}
			}
		} else if (level > oldLevel) {
			FrequentFlyerEquipEvent flyer = new FrequentFlyerEquipEvent((Player) entity, level, result.getEffect());
			Bukkit.getPluginManager().callEvent(flyer);
			if (!flyer.isCancelled()) {
				CustomPotionEffect e = new CustomPotionEffect((LivingEntity) entity, flyer.getType(), flyer.getAmplifier(), flyer.getDuration(), new EnchantmentLevel(CERegister.FREQUENT_FLYER, level));
				if (esEntity.addCustomPotionEffect(e, true) != null) return new InfiniteCustomPotionResult(level, flyer.getPotionEffect());
			}
		} else if (oldLevel > 0 && level == 0) {
			FrequentFlyerUnequipEvent flyer = new FrequentFlyerUnequipEvent((Player) entity, level);
			Bukkit.getPluginManager().callEvent(flyer);
			if (!flyer.isCancelled()) {
				CustomPotionEffect e = new CustomPotionEffect((LivingEntity) entity, flyer.getType(), 0, 0, new EnchantmentLevel(CERegister.FREQUENT_FLYER, level));
				esEntity.removeCustomPotionEffect(e, level);
				return new InfiniteCustomPotionResult(level, new ConfigCustomPotionEffect(getEffectType(), "0", "0"));
			}
		}

		return null;
	}

}
