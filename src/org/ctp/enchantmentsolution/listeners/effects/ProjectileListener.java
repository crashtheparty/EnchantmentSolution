package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileHitDamagedEffect;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileHitDamagerEffect;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileLaunchEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class ProjectileListener extends Enchantmentable {
	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(ProjectileLaunchEvent event) {
		handleLaunch(event, EventPriority.MONITOR);
	}

	private void handleLaunch(ProjectileLaunchEvent event, EventPriority priority) {
		Projectile projectile = event.getEntity();
		LivingEntity living = null;
		if (projectile.getShooter() instanceof LivingEntity) living = (LivingEntity) projectile.getShooter();

		HashMap<EnchantmentWrapper, List<ProjectileLaunchEffect>> effects = InterfaceRegistry.getProjectileLaunchEffects();
		Iterator<Entry<EnchantmentWrapper, List<ProjectileLaunchEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<ProjectileLaunchEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || living instanceof Player && isDisabled((Player) living, entry.getKey())) continue;
			List<ProjectileLaunchEffect> effs = entry.getValue();
			for(ProjectileLaunchEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(living, event)) effect.run(living, projectile, InterfaceUtils.getItems(living, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(ProjectileHitEvent event) {
		handleHit(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(ProjectileHitEvent event) {
		handleHit(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(ProjectileHitEvent event) {
		handleHit(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(ProjectileHitEvent event) {
		handleHit(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(ProjectileHitEvent event) {
		handleHit(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(ProjectileHitEvent event) {
		handleHit(event, EventPriority.MONITOR);
	}

	private void handleHit(ProjectileHitEvent event, EventPriority priority) {
		Projectile projectile = event.getEntity();
		LivingEntity living = null;
		if (projectile.getShooter() instanceof LivingEntity) living = (LivingEntity) projectile.getShooter();
		LivingEntity damaged = null;
		if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) damaged = (LivingEntity) event.getHitEntity();

		HashMap<EnchantmentWrapper, List<ProjectileHitDamagerEffect>> effectsDamager = InterfaceRegistry.getProjectileHitDamagerEffects();
		Iterator<Entry<EnchantmentWrapper, List<ProjectileHitDamagerEffect>>> iter1 = effectsDamager.entrySet().iterator();
		while (iter1.hasNext()) {
			Entry<EnchantmentWrapper, List<ProjectileHitDamagerEffect>> entry = iter1.next();
			if (!canRun(entry.getKey(), event) || living instanceof Player && isDisabled((Player) living, entry.getKey())) continue;
			List<ProjectileHitDamagerEffect> effs = entry.getValue();
			for(ProjectileHitDamagerEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(living, damaged, event)) effect.run(living, damaged, projectile, InterfaceUtils.getItems(living, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
		}

		HashMap<EnchantmentWrapper, List<ProjectileHitDamagedEffect>> effectsDamaged = InterfaceRegistry.getProjectileHitDamagedEffects();
		Iterator<Entry<EnchantmentWrapper, List<ProjectileHitDamagedEffect>>> iter2 = effectsDamaged.entrySet().iterator();
		while (iter2.hasNext()) {
			Entry<EnchantmentWrapper, List<ProjectileHitDamagedEffect>> entry = iter2.next();
			if (!canRun(entry.getKey(), event) || living instanceof Player && isDisabled((Player) living, entry.getKey())) continue;
			List<ProjectileHitDamagedEffect> effs = entry.getValue();
			for(ProjectileHitDamagedEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(living, damaged, event)) effect.run(damaged, living, projectile, InterfaceUtils.getItems(damaged, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
		}
	}
}
