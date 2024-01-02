package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.FishingEffect;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class FishingListener extends Enchantmentable {
	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(PlayerFishEvent event) {
		handlePlayerFish(event, EventPriority.MONITOR);
	}

	private void handlePlayerFish(PlayerFishEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		
		HashMap<EnchantmentWrapper, List<FishingEffect>> effects = InterfaceRegistry.getFishingEffects();
		Iterator<Entry<EnchantmentWrapper, List<FishingEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<FishingEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<FishingEffect> effs = entry.getValue();
			for(FishingEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
		}
	}
}
