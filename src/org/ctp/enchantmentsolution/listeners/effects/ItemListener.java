package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.EntityEquipEffect;
import org.ctp.enchantmentsolution.interfaces.effects.ItemDamageEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class ItemListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(EquipEvent event) {
		handleEquip(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(EquipEvent event) {
		handleEquip(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(EquipEvent event) {
		handleEquip(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(EquipEvent event) {
		handleEquip(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(EquipEvent event) {
		handleEquip(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(EquipEvent event) {
		handleEquip(event, EventPriority.MONITOR);
	}

	private void handleEquip(EquipEvent event, EventPriority priority) {
		HumanEntity human = event.getEntity();
		HashMap<EnchantmentWrapper, List<EntityEquipEffect>> effects = InterfaceRegistry.getEquipEffects();
		Iterator<Entry<EnchantmentWrapper, List<EntityEquipEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<EntityEquipEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || human instanceof Player && isDisabled((Player) human, entry.getKey())) continue;
			List<EntityEquipEffect> effs = entry.getValue();
			for(EntityEquipEffect effect: effs) {
				int oldLevel = effect.getLevel(InterfaceUtils.getItems(human, effect.getEnchantment(), effect.getLocation(), effect.getType()));
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					if (effect.getPriority() == priority && effect.willRun(human)) effect.run(human, InterfaceUtils.getItems(human, effect.getEnchantment(), effect.getLocation(), effect.getType()), oldLevel, event);
				}, 0l);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(PlayerItemDamageEvent event) {
		handlePlayerItemDamage(event, EventPriority.MONITOR);
	}

	private void handlePlayerItemDamage(PlayerItemDamageEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		HashMap<EnchantmentWrapper, List<ItemDamageEffect>> effects = InterfaceRegistry.getItemDamageEffects();
		Iterator<Entry<EnchantmentWrapper, List<ItemDamageEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<ItemDamageEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<ItemDamageEffect> effs = entry.getValue();
			for(ItemDamageEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event.getItem(), event)) effect.run(player, event.getItem(), event);
		}
	}
}
