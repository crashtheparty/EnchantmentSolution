package org.ctp.enchantmentsolution.threads;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.ForceFeedEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ForceFeedThread extends EnchantmentThread {

	private static List<ForceFeedThread> FEED_THREADS = new ArrayList<ForceFeedThread>();

	public static ForceFeedThread createThread(Player player) {
		Iterator<ForceFeedThread> threads = FEED_THREADS.iterator();
		while (threads.hasNext()) {
			ForceFeedThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		ForceFeedThread newThread = new ForceFeedThread(EnchantmentSolution.getESPlayer(player));
		FEED_THREADS.add(newThread);
		return newThread;
	}

	private ForceFeedThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		ESPlayer player = getPlayer();
		Player p = player.getOnlinePlayer();
		List<ItemStack> items = player.getForceFeedItems();
		if (items.size() == 0) {
			remove();
			return;
		}
		double rand = Math.random();
		if (player.getForceFeedChance() > rand) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			int damage = DamageUtils.getDamage(item);
			if (damage > 0) {
				ForceFeedEvent forceFeed = new ForceFeedEvent(p, ItemUtils.getLevel(item, RegisterEnchantments.FORCE_FEED), item, 2);
				Bukkit.getPluginManager().callEvent(forceFeed);
				if (!forceFeed.isCancelled() && forceFeed.getRepair() > 0) {
					damage -= forceFeed.getRepair();
					if (damage < 0) damage = 0;
					DamageUtils.setDamage(item, damage);
					p.setExhaustion(p.getExhaustion() + forceFeed.getExhaust());
					AdvancementUtils.awardCriteria(p, ESAdvancement.YUMMY_REPAIRS, "repair");
				}
			}
		}
	}

	private void remove() {
		FEED_THREADS.remove(this);
		Bukkit.getScheduler().cancelTask(getScheduler());
	}
}
