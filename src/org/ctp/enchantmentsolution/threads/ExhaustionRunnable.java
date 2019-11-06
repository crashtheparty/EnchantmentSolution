package org.ctp.enchantmentsolution.threads;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.ExhaustionEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;

public class ExhaustionRunnable implements Runnable {

	private static Map<UUID, Float> EXHAUSTION = new HashMap<UUID, Float>();

	@Override
	public void run() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.CURSE_OF_EXHAUSTION)) {
			return;
		}
		for(Player player: Bukkit.getOnlinePlayers()) {
			if (!EXHAUSTION.containsKey(player.getUniqueId())) {
				try {
					EXHAUSTION.put(player.getUniqueId(), AbilityUtils.getExhaustion(player));
				} catch (Exception ex) {
				}
			}
		}
		Iterator<Entry<UUID, Float>> iterator = EXHAUSTION.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<UUID, Float> entry = iterator.next();
			Player player = Bukkit.getPlayer(entry.getKey());
			if (player != null && player.isOnline()) {
				int exhaustionCurse = AbilityUtils.getExhaustionCurse(player);
				float change = entry.getValue() - AbilityUtils.getExhaustion(player);
				if (exhaustionCurse > 0 && change > 0) {
					ExhaustionEvent event = new ExhaustionEvent(player, exhaustionCurse, change);
					Bukkit.getPluginManager().callEvent(event);

					if (!event.isCancelled() && event.getCurseLevel() > 0 && event.getExhaustionTick() > 0) {
						player.setExhaustion(
								player.getExhaustion() + event.getExhaustionTick() * event.getCurseLevel());
						if (event.getExhaustionTick() * event.getCurseLevel() / 4.0F > change) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.HIGH_METABOLISM, "exhaustion");
						}
					}
					EXHAUSTION.put(player.getUniqueId(), AbilityUtils.getExhaustion(player));
				}
			} else {
				iterator.remove();
			}
		}
	}
}
