package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class FrequentFlyerThread extends EnchantmentThread {

	private static List<FrequentFlyerThread> FLYER_THREADS = new ArrayList<FrequentFlyerThread>();

	public static FrequentFlyerThread createThread(Player player) {
		Iterator<FrequentFlyerThread> threads = FLYER_THREADS.iterator();
		while (threads.hasNext()) {
			FrequentFlyerThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		FrequentFlyerThread newThread = new FrequentFlyerThread(EnchantmentSolution.getESPlayer(player));
		FLYER_THREADS.add(newThread);
		return newThread;
	}

	private FrequentFlyerThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		ESPlayer player = getPlayer();
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.FREQUENT_FLYER) || isDisabled(player.getOnlinePlayer(), RegisterEnchantments.FREQUENT_FLYER)) {
			player.logoutFlyer();
			remove();
			return;
		}
		if (player.hasFrequentFlyer() || player.canFly(true)) {
			if (!player.isOnline()) {
				if (player.canFly(false)) player.logoutFlyer();
				remove();
				return;
			}
			Player p = player.getOnlinePlayer();
			player.setDidTick(false);
			player.setFrequentFlyer();
			if (player.canFly(true) && p.isFlying() && !p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) player.minus();
			return;
		} else if (!player.isOnline() && player.canFly(false)) player.logoutFlyer();
		remove();
	}

	@Override
	protected void remove() {
		FLYER_THREADS.remove(this);
		super.remove();
	}

}
