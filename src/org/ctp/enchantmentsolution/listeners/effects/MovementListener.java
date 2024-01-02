package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.MovementEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class MovementListener extends Enchantmentable {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(PlayerChangeCoordsEvent event) {
		handleMovement(event, EventPriority.MONITOR);
	}

	private void handleMovement(PlayerChangeCoordsEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		HashMap<EnchantmentWrapper, List<MovementEffect>> effects = InterfaceRegistry.getMovementEffects();
		Iterator<Entry<EnchantmentWrapper, List<MovementEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<MovementEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<MovementEffect> effs = entry.getValue();
			for(MovementEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
		}
	}
}
