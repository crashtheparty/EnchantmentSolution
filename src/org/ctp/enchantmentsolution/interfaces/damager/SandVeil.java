package org.ctp.enchantmentsolution.interfaces.damager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerAfflictCustomEffect;
import org.ctp.enchantmentsolution.utils.player.ESEntity;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class SandVeil extends DamagerAfflictCustomEffect {

	public SandVeil() {
		super(RegisterEnchantments.SAND_VEIL, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, true, new ConfigCustomPotionEffect[] { new ConfigCustomPotionEffect(CustomPotionEffectType.SANDY_EYES, "%level% - 1", "160") }, new DamageCondition[0]);
	}

	@Override
	public DamagerAfflictCustomResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerAfflictCustomResult result = super.run(damager, damaged, items, event);
		int level = result.getLevel();
		if (level == 0) return null;
		CustomPotionEffect[] effects = result.getEffects();
		ESEntity entity = EnchantmentSolution.getESEntity((LivingEntity) damaged);
		List<CustomPotionEffect> finalEffects = new ArrayList<CustomPotionEffect>();
		for(CustomPotionEffect custom: effects)
			if (custom.applyPotionEffect(entity.getEntity(), isOverride())) finalEffects.add(custom);

		CustomPotionEffect[] finalEff = new CustomPotionEffect[finalEffects.size()];
		int i = 0;
		for(CustomPotionEffect eff: finalEffects) {
			finalEff[i] = eff;
			i++;
		}
		if (i == 0) return null;
		return new DamagerAfflictCustomResult(level, finalEff);
	}

}
