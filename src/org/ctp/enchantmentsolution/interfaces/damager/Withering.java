package org.ctp.enchantmentsolution.interfaces.damager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.potion.PotionAfflictEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerAfflictEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public class Withering extends DamagerAfflictEffect {

	public Withering() {
		super(RegisterEnchantments.WITHERING, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, true, new ConfigPotionEffect[] { new ConfigPotionEffect(PotionEffectType.WITHER, "%level% - 1", "160") }, new DamageCondition[0]);
	}

	@Override
	public DamagerAfflictResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerAfflictResult result = super.run(damager, damaged, items, event);

		int level = result.getLevel();
		if (level == 0) return null;
		PotionEffect[] effects = result.getEffects();
		LivingEntity living = (LivingEntity) damaged;
		LivingEntity attacker = (LivingEntity) damager;
		List<PotionEffect> finalEffects = new ArrayList<PotionEffect>();
		for(PotionEffect effect: effects) {
			PotionEffect previousEffect = null;
			PotionEffectType type = effect.getType();
			boolean override = false;
			for(PotionEffect activeEffect: living.getActivePotionEffects())
				if (activeEffect.getType() == type) {
					override = true;
					previousEffect = activeEffect;
					break;
				}

			PotionAfflictEvent potionAfflict = new PotionAfflictEvent(living, attacker, new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(getEnchantment()), level), type, effect.getDuration(), effect.getAmplifier(), previousEffect, override);
			Bukkit.getPluginManager().callEvent(potionAfflict);
			if (!potionAfflict.isCancelled()) {
				PotionEffect e = new PotionEffect(potionAfflict.getType(), potionAfflict.getLevel(), potionAfflict.getDuration());
				if (e.apply((LivingEntity) damaged)) finalEffects.add(e);
			}
		}

		PotionEffect[] finalEff = new PotionEffect[finalEffects.size()];
		int i = 0;
		for(PotionEffect eff: finalEffects) {
			finalEff[i] = eff;
			i++;
		}
		if (i == 0) return null;
		return new DamagerAfflictResult(level, finalEff);
	}

}
