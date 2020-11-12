package org.ctp.enchantmentsolution.threads;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AdvancementThread implements Runnable {

	@Override
	public void run() {
		kilimanjaro();
		worldRecord();
	}

	private void kilimanjaro() {
		if (!ESAdvancement.KILIMANJARO.isEnabled()) return;
		Iterator<ESPlayer> iter = EnchantmentSolution.getOverkillDeathPlayers().iterator();
		while (iter.hasNext()) {
			ESPlayer esPlayer = iter.next();
			Iterator<OverkillDeath> deaths = esPlayer.getOverkillDeaths().iterator();
			while (deaths.hasNext()) {
				OverkillDeath death = deaths.next();
				death.minus();
				if (death.getTicks() <= 0) deaths.remove();
			}
			if (esPlayer.getOverkillDeaths().size() >= 10 && esPlayer.isOnline()) {
				Player player = esPlayer.getOnlinePlayer();
				AdvancementUtils.awardCriteria(player, ESAdvancement.KILIMANJARO, "kills");
			}
		}
	}

	private void worldRecord() {
		if (!ESAdvancement.WORLD_RECORD.isEnabled()) return;
		Iterator<ESPlayer> iter = EnchantmentSolution.getAllESPlayers(true).iterator();
		while (iter.hasNext()) {
			ESPlayer esPlayer = iter.next();
			if (esPlayer.getOnlinePlayer().getEyeLocation().getBlock().isLiquid()) esPlayer.addUnderwaterTick();
			else
				esPlayer.resetUnderwaterTick();
		}
	}
}
