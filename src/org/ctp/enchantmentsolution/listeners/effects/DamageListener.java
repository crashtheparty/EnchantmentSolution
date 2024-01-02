package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.interfaces.effects.EntityTakeDamageEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class DamageListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.NORMAL)
	public void normalEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void highEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highestEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void lowEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowestEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitorEntity(EntityDamageByEntityEvent event) {
		handleDamageEntity(event, EventPriority.MONITOR);
	}

	private void handleDamageEntity(EntityDamageByEntityEvent event, EventPriority priority) {
		Entity entity = event.getDamager();
		Entity damaged = event.getEntity();

		ItemStack item = null;
		if (entity instanceof Projectile) {
			Projectile projectile = (Projectile) entity;
			if (projectile.getShooter() instanceof Entity) {
				entity = (Entity) projectile.getShooter();
				if (projectile instanceof ThrowableProjectile) item = ((ThrowableProjectile) projectile).getItem();
			} else
				return;
		}

		if (entity instanceof HumanEntity && damaged instanceof LivingEntity) {
			HumanEntity player = (HumanEntity) entity;
			HashMap<EnchantmentWrapper, List<EntityDamageEffect>> effects = InterfaceRegistry.getDamagerEffects();
			Iterator<Entry<EnchantmentWrapper, List<EntityDamageEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<EntityDamageEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || player instanceof Player && isDisabled((Player) player, entry.getKey())) continue;
				List<EntityDamageEffect> effs = entry.getValue();
				for(EntityDamageEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(player, damaged, event)) effect.run(player, damaged, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType(), new ItemSlot(item, ItemSlotType.MAIN_HAND)), event);
			}
		}

		if (damaged instanceof HumanEntity && entity instanceof LivingEntity) {
			HumanEntity player = (HumanEntity) damaged;
			HashMap<EnchantmentWrapper, List<EntityDamageEffect>> effects = InterfaceRegistry.getDamagedEffects();
			Iterator<Entry<EnchantmentWrapper, List<EntityDamageEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<EntityDamageEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || player instanceof Player && isDisabled((Player) player, entry.getKey())) continue;
				List<EntityDamageEffect> effs = entry.getValue();
				for(EntityDamageEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(entity, player, event)) effect.run(entity, player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType(), new ItemSlot(item, ItemSlotType.MAIN_HAND)), event);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(EntityDamageEvent event) {
		handleDamage(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(EntityDamageEvent event) {
		handleDamage(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(EntityDamageEvent event) {
		handleDamage(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(EntityDamageEvent event) {
		handleDamage(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(EntityDamageEvent event) {
		handleDamage(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(EntityDamageEvent event) {
		handleDamage(event, EventPriority.MONITOR);
	}

	private void handleDamage(EntityDamageEvent event, EventPriority priority) {
		Entity damaged = event.getEntity();
		if (damaged instanceof HumanEntity) {
			HumanEntity player = (HumanEntity) damaged;
			HashMap<EnchantmentWrapper, List<EntityTakeDamageEffect>> effects = InterfaceRegistry.getTakeDamageEffects();
			Iterator<Entry<EnchantmentWrapper, List<EntityTakeDamageEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<EntityTakeDamageEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || player instanceof Player && isDisabled((Player) player, entry.getKey())) continue;
				List<EntityTakeDamageEffect> effs = entry.getValue();
				for(EntityTakeDamageEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(player, event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
			}
		}
	}
}
