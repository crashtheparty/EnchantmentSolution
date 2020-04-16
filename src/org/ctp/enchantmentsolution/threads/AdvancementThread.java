package org.ctp.enchantmentsolution.threads;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;

public class AdvancementThread implements Runnable {

	@Override
	public void run() {
		Iterator<Entry<UUID, List<OverkillDeath>>> iter = OverkillDeath.getIterator();
		while (iter.hasNext()) {
			Entry<UUID, List<OverkillDeath>> entry = iter.next();
			Player player = Bukkit.getPlayer(entry.getKey());
			if (player != null) {
				Iterator<OverkillDeath> iter2 = OverkillDeath.getDeaths(player.getUniqueId()).iterator();
				if (iter2 != null) {
					while (iter2.hasNext()) {
						OverkillDeath death = iter2.next();
						death.minus();
						if (death.getTicks() <= 0) iter2.remove();
					}

					List<OverkillDeath> deaths = OverkillDeath.getDeaths(player.getUniqueId());
					if (deaths.size() >= 10) AdvancementUtils.awardCriteria(player, ESAdvancement.KILIMANJARO, "kills");
				}
			}
		}
	}
}
