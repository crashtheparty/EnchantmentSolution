package org.ctp.enchantmentsolution.threads;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageResult;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.InstabilityEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class InstabilityCurseThread extends EnchantmentThread {

	private static List<InstabilityCurseThread> UNSTABLE_THREADS = new ArrayList<InstabilityCurseThread>();

	public static InstabilityCurseThread createThread(Player player) {
		Iterator<InstabilityCurseThread> threads = UNSTABLE_THREADS.iterator();
		while (threads.hasNext()) {
			InstabilityCurseThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		InstabilityCurseThread newThread = new InstabilityCurseThread(EnchantmentSolution.getESPlayer(player));
		UNSTABLE_THREADS.add(newThread);
		return newThread;
	}

	private InstabilityCurseThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		ESPlayer player = getPlayer();
		if (!player.isOnline()) {
			remove();
			return;
		}
		Player p = player.getOnlinePlayer();
		List<ItemStack> items = player.getUnstableItems();
		if (items.size() == 0) {
			remove();
			return;
		}
		if (isDisabled(p, RegisterEnchantments.CURSE_OF_INSTABILITY)) return;

		double chance = 1 / 50.0;
		double random = Math.random();
		if (chance > random) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			InstabilityEvent event = new InstabilityEvent(p, item, 0, 1);
			Bukkit.getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				DamageResult damage = DamageUtils.damageItem(p, item, 1);
				if (damage.isBroken()) AdvancementUtils.awardCriteria(p, ESAdvancement.BROKEN_DREAMS, "unstable");
			}
		}
	}

	@Override
	protected void remove() {
		UNSTABLE_THREADS.remove(this);
		super.remove();
	}

}
