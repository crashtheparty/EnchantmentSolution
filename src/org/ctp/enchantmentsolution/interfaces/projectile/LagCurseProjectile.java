package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.entity.LagEntityEvent;
import org.ctp.enchantmentsolution.events.modify.player.LagPlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.LagProjectileEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;

public class LagCurseProjectile extends LagProjectileEffect {

	public LagCurseProjectile() {
		super(RegisterEnchantments.CURSE_OF_LAG, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 2, 5, 11, 400, new Particle[0], new Particle[0], new ProjectileLaunchCondition[0]);
	}

	@Override
	public LagResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event) {
		setLoc(entity.getLocation());
		LagResult result = super.run(entity, projectile, items, event);
		if (result.getLevel() == 0) return null;
		Location location = result.getLocation();
		ParticleEffect[] effects = result.getParticles();

		if (entity instanceof Player) {
			LagPlayerEvent lag = new LagPlayerEvent((Player) entity, location, effects);
			Bukkit.getPluginManager().callEvent(lag);
			if (!lag.isCancelled() && lag.getEffects().length > 0) {
				Location loc = lag.getLocation();
				for(ParticleEffect effect: lag.getEffects())
					loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
				if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
				AdvancementUtils.awardCriteria((Player) entity, ESAdvancement.LAAAGGGGGG, "lag");
			}
		} else {
			LagEntityEvent lag = new LagEntityEvent(entity, location, effects);
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
