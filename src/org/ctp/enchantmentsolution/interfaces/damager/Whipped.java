package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.WhippedTameEvent;
import org.ctp.enchantmentsolution.events.damage.WhippedTortureEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.AnimalIsTameableCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.TameAnimalChanceEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Whipped extends TameAnimalChanceEffect {

	public Whipped() {
		super(RegisterEnchantments.WHIPPED, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 1, 1, 0, "0.08 * %level%", "0.12 * %level%", "%damaged_max_health% / 2", false, false, true, false, true, false, true, new DamageCondition[] { new DamagerIsTypeCondition(false, new MobData("PLAYER")), new AnimalIsTameableCondition(false) });
	}

	@Override
	public TameAnimalChanceResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		TameAnimalChanceResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;

		if (result.willTame()) {
			Tameable tame = (Tameable) damaged;
			WhippedTameEvent whipped = new WhippedTameEvent((LivingEntity) damaged, result.getLevel(), (Player) damager, event.getDamage());
			Bukkit.getPluginManager().callEvent(whipped);

			if (!whipped.isCancelled()) {
				tame.setOwner((Player) damager);
				tame.getWorld().spawnParticle(Particle.HEART, tame.getLocation(), 50, 0.5, 2, 0.5);
				AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.ANIMAL_TAMER, "tamed");
			}
		}

		if (result.willBrutalize()) {
			WhippedTortureEvent whipped = new WhippedTortureEvent((LivingEntity) damaged, result.getLevel(), (Player) damager, event.getDamage(), result.getDamage());
			Bukkit.getPluginManager().callEvent(whipped);
			if (!whipped.isCancelled()) {
				event.setDamage(whipped.getNewDamage());
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					if (damaged.isDead()) AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.ANIMAL_ABUSE, "death");
				}, 0l);
				DamageUtils.damageItem((Player) damager, items[0], 1, 2);
			} else
				event.setCancelled(true);

		} else
			event.setCancelled(true);
		return result;
	}
}
