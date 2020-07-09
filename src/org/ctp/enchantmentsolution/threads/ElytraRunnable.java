package org.ctp.enchantmentsolution.threads;

import java.util.Iterator;

import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class ElytraRunnable implements Runnable, Reflectionable {

	private int run;

	@Override
	public void run() {
		runMethod(this, "frequentFlyer");
		if (run % 20 == 0) {
			runMethod(this, "icarus");
			run = 0;
		}
		run++;
	}

	private void frequentFlyer() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.FREQUENT_FLYER)) return;

		Iterator<ESPlayer> iterator = EnchantmentSolution.getAllESPlayers().iterator();
		while (iterator.hasNext()) {
			ESPlayer ffPlayer = iterator.next();
			if (!ffPlayer.isOnline()) continue;
			Player player = ffPlayer.getOnlinePlayer();
			ffPlayer.setDidTick(false);
			ffPlayer.setFrequentFlyer();
			if (ffPlayer.hasFrequentFlyer() && player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) ffPlayer.minus();
		}
	}

	private void icarus() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.ICARUS)) return;
		Iterator<ESPlayer> iter = EnchantmentSolution.getIcarusPlayers().iterator();
		while (iter.hasNext()) {
			ESPlayer esPlayer = iter.next();
			if (!esPlayer.isOnline()) continue;
			esPlayer.minusIcarusDelay();
			if (esPlayer.getIcarusDelay() <= 0) {
				Player player = esPlayer.getOnlinePlayer();
				player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 250, 2, 2, 2);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
			}
		}
	}

	public static boolean didTick(Player player) {
		for(ESPlayer ffPlayer: EnchantmentSolution.getAllESPlayers())
			if (ffPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return ffPlayer.didTick();
		return false;
	}
}
