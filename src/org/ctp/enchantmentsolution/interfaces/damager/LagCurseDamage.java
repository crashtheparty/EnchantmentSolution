package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.entity.LagEntityEvent;
import org.ctp.enchantmentsolution.events.modify.player.LagPlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.LagDamageEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;

public class LagCurseDamage extends LagDamageEffect {

	public LagCurseDamage() {
		super(RegisterEnchantments.CURSE_OF_LAG, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 2, 5, 11, 400, new Particle[0], new Particle[0], new DamageCondition[0]);
	}

	@Override
	public LagResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		setLoc(damager.getLocation());
		LagResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;
		Location location = result.getLocation();
		ParticleEffect[] effects = result.getParticles();

		if (damager instanceof Player) {
			LagPlayerEvent lag = new LagPlayerEvent((Player) damager, location, effects);
			Bukkit.getPluginManager().callEvent(lag);
			if (!lag.isCancelled() && lag.getEffects().length > 0) {
				Location loc = lag.getLocation();
				for(ParticleEffect effect: lag.getEffects())
					loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
				if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
				AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.LAAAGGGGGG, "lag");
			}
		} else {
			LagEntityEvent lag = new LagEntityEvent(damager, location, effects);
			Bukkit.getPluginManager().callEvent(lag);
			if (!lag.isCancelled() && lag.getEffects().length > 0) {
				Location loc = lag.getLocation();
				for(ParticleEffect effect: lag.getEffects())
					loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
				if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
			}
		}
		return null;
	}
}
