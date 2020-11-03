package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class IcarusThread extends EnchantmentThread {

	private static List<IcarusThread> ICARUS_THREADS = new ArrayList<IcarusThread>();

	public static IcarusThread createThread(Player player) {
		Iterator<IcarusThread> threads = ICARUS_THREADS.iterator();
		while (threads.hasNext()) {
			IcarusThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		IcarusThread newThread = new IcarusThread(EnchantmentSolution.getESPlayer(player));
		ICARUS_THREADS.add(newThread);
		return newThread;
	}

	private IcarusThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.ICARUS)) {
			remove();
			return;
		}
		ESPlayer player = getPlayer();

		if (!player.isOnline()) return;
		player.minusIcarusDelay();
		if (player.getIcarusDelay() <= 0) {
			Player p = player.getOnlinePlayer();
			p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 250, 2, 2, 2);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
			remove();
		}
	}

	@Override
	protected void remove() {
		ICARUS_THREADS.remove(this);
		super.remove();
	}

}
