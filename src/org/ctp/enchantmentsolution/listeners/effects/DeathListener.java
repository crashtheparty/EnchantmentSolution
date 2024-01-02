package org.ctp.enchantmentsolution.listeners.effects;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.events.soul.SoulboundEvent;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.DeathEffect;
import org.ctp.enchantmentsolution.interfaces.effects.SoulboundEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class DeathListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(EntityDeathEvent event) {
		handleDeath(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(EntityDeathEvent event) {
		handleDeath(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(EntityDeathEvent event) {
		handleDeath(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(EntityDeathEvent event) {
		handleDeath(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(EntityDeathEvent event) {
		handleDeath(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(EntityDeathEvent event) {
		handleDeath(event, EventPriority.MONITOR);
	}

	private void handleDeath(EntityDeathEvent event, EventPriority priority) {
		Entity killed = event.getEntity();
		Entity killer = null;
		if (killed.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) killed.getLastDamageCause();
			killer = ev.getDamager();
		}
		Collection<ItemStack> drops = event.getDrops();

		ItemStack item = null;
		if (killer instanceof Projectile) {
			Projectile projectile = (Projectile) killer;
			if (projectile.getShooter() instanceof Entity) {
				killer = (Entity) projectile.getShooter();
				if (projectile instanceof ThrowableProjectile) item = ((ThrowableProjectile) projectile).getItem();
			}
		}

		if ((killer == null || killer instanceof HumanEntity) && killed instanceof LivingEntity) {
			HumanEntity player = (HumanEntity) killer;
			HashMap<EnchantmentWrapper, List<DeathEffect>> effects = InterfaceRegistry.getKillerEffects();
			Iterator<Entry<EnchantmentWrapper, List<DeathEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<DeathEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || player instanceof Player && isDisabled((Player) player, entry.getKey())) continue;
				List<DeathEffect> effs = entry.getValue();
				for(DeathEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(player, killed, drops)) effect.run(player, killed, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType(), new ItemSlot(item, ItemSlotType.MAIN_HAND)), drops, event);
			}
		}

		if (killed instanceof HumanEntity && (killer == null || killer instanceof LivingEntity)) {
			HumanEntity player = (HumanEntity) killed;
			HashMap<EnchantmentWrapper, List<DeathEffect>> effects = InterfaceRegistry.getKilledEffects();
			Iterator<Entry<EnchantmentWrapper, List<DeathEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<DeathEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || player instanceof Player && isDisabled((Player) player, entry.getKey())) continue;
				List<DeathEffect> effs = entry.getValue();
				for(DeathEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(killer, player, drops)) effect.run(killer, player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType(), new ItemSlot(item, ItemSlotType.MAIN_HAND)), drops, event);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(SoulboundEvent event) {
		handleSoulbound(event, EventPriority.MONITOR);
	}

	private void handleSoulbound(SoulboundEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		Entity killer = event.getKiller();

		ItemStack item = null;
		if (killer instanceof Projectile) {
			Projectile projectile = (Projectile) killer;
			if (projectile.getShooter() instanceof Entity) {
				killer = (Entity) projectile.getShooter();
				if (projectile instanceof ThrowableProjectile) item = ((ThrowableProjectile) projectile).getItem();
			} else
				return;
		}

		if (killer instanceof HumanEntity) {
			HashMap<EnchantmentWrapper, List<SoulboundEffect>> effects = InterfaceRegistry.getSoulboundEffects();
			Iterator<Entry<EnchantmentWrapper, List<SoulboundEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<SoulboundEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || killer instanceof Player && isDisabled((Player) killer, entry.getKey())) continue;
				List<SoulboundEffect> effs = entry.getValue();
				for(SoulboundEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(killer, player, event.getSavedItems())) effect.run(killer, player, InterfaceUtils.getItems((HumanEntity) killer, effect.getEnchantment(), effect.getLocation(), effect.getType(), new ItemSlot(item, ItemSlotType.MAIN_HAND)), event.getSavedItems(), event);
			}
		}
	}
}
