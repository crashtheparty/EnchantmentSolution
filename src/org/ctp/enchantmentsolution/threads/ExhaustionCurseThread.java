package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.ExhaustionEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ExhaustionCurseThread extends EnchantmentThread {

	private static List<ExhaustionCurseThread> EXHAUSTION_THREADS = new ArrayList<ExhaustionCurseThread>();

	public static ExhaustionCurseThread createThread(Player player) {
		Iterator<ExhaustionCurseThread> threads = EXHAUSTION_THREADS.iterator();
		while (threads.hasNext()) {
			ExhaustionCurseThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		ExhaustionCurseThread newThread = new ExhaustionCurseThread(EnchantmentSolution.getESPlayer(player));
		EXHAUSTION_THREADS.add(newThread);
		return newThread;
	}

	private ExhaustionCurseThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.CURSE_OF_EXHAUSTION)) {
			remove();
			return;
		}
		ESPlayer player = getPlayer();

		Player p = player.getOnlinePlayer();
		if (isDisabled(p, RegisterEnchantments.CURSE_OF_EXHAUSTION)) return;
		if (p != null && p.isOnline() && player.getExhaustion() > 0) {
			player.setCurrentExhaustion();
			float change = player.getPastExhaustion() - player.getCurrentExhaustion();
			int exhaustionCurse = player.getExhaustion();
			if (exhaustionCurse > 0 && change > 0) {
				ExhaustionEvent event = new ExhaustionEvent(p, exhaustionCurse, change);
				Bukkit.getPluginManager().callEvent(event);

				if (!event.isCancelled() && event.getMultiplier() > 0) {
					p.setExhaustion((float) (p.getExhaustion() + event.getExhaustionTick() * event.getMultiplier()));
					if (event.getMultiplier() >= 3.0D) AdvancementUtils.awardCriteria(p, ESAdvancement.HIGH_METABOLISM, "exhaustion");
				}
				player.setCurrentExhaustion();
			}
		} else if (player.getExhaustion() == 0) remove();
	}

	@Override
	protected void remove() {
		EXHAUSTION_THREADS.remove(this);
		super.remove();
	}

}
