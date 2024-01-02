package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect.EffectResult;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class InteractListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(PlayerInteractEvent event) {
		handleInteract(event, EventPriority.MONITOR);
	}

	private void handleInteract(PlayerInteractEvent event, EventPriority priority) {
		boolean handleCancelled = false;
		if (event.getAction() == Action.LEFT_CLICK_AIR) handleCancelled = true;
		Player player = event.getPlayer();
		HashMap<EnchantmentWrapper, List<PlayerInteractEffect>> effects = InterfaceRegistry.getInteractEffects();
		Iterator<Entry<EnchantmentWrapper, List<PlayerInteractEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<PlayerInteractEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), handleCancelled, event) || isDisabled(player, entry.getKey())) continue;
			List<PlayerInteractEffect> effs = entry.getValue();
			for(PlayerInteractEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event)) {
					EffectResult result = effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
					if (result != null && handleCancelled) event.setCancelled(false);
				}
		}
	}
}
