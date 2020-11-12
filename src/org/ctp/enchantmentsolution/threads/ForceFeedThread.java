package org.ctp.enchantmentsolution.threads;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.ForceFeedEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
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
		List<ItemStack> i = player.getForceFeedItems();
		List<ItemStack> items = new ArrayList<ItemStack>();
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.FORCE_FEED)) {
			remove();
			return;
		}
		for(ItemStack item: i)
			if (DamageUtils.getDamage(item) > 0) items.add(item);
		if (items.size() == 0) return;
		if (isDisabled(p, RegisterEnchantments.FORCE_FEED)) return;
		Collections.shuffle(items);
		ItemStack item = items.get(0);
		int damage = DamageUtils.getDamage(item);
		double rand = Math.random();
		int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.FORCE_FEED);
		double chance = player.getForceFeedChance(level);
		if (chance > rand) {
			int newDamage = Math.max((int) (Math.random() * (level + 4) - (3 + level) / 2.0D), 1);
			ForceFeedEvent forceFeed = new ForceFeedEvent(p, level, item, 0.4f, newDamage);
			Bukkit.getPluginManager().callEvent(forceFeed);
			if (!forceFeed.isCancelled() && forceFeed.getRepair() > 0) {
				int repair = forceFeed.getRepair();
				damage -= repair;
				if (damage < 0) {
					repair += damage;
					damage = 0;
				}
				DamageUtils.setDamage(item, damage);
				p.setExhaustion(p.getExhaustion() + forceFeed.getExhaust() * repair);
				AdvancementUtils.awardCriteria(p, ESAdvancement.YUMMY_REPAIRS, "repair");
			}
		}
	}

	@Override
	protected void remove() {
		FEED_THREADS.remove(this);
		super.remove();
	}
}
