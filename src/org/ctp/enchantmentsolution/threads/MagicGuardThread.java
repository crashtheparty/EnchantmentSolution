package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class MagicGuardThread extends EnchantmentThread {

	private static List<MagicGuardThread> MAGIC_THREADS = new ArrayList<MagicGuardThread>();

	public static MagicGuardThread createThread(Player player) {
		Iterator<MagicGuardThread> threads = MAGIC_THREADS.iterator();
		while (threads.hasNext()) {
			MagicGuardThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		MagicGuardThread newThread = new MagicGuardThread(EnchantmentSolution.getESPlayer(player));
		MAGIC_THREADS.add(newThread);
		return newThread;
	}

	private MagicGuardThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		ESPlayer player = getPlayer();
		if (isDisabled(player.getOnlinePlayer(), RegisterEnchantments.MAGIC_GUARD)) return;
		if (!player.isOnline() || !RegisterEnchantments.isEnabled(RegisterEnchantments.MAGIC_GUARD)) {
			remove();
			return;
		}
		ItemStack shield = player.getOffhand();
		if (shield == null || MatData.isAir(shield.getType()) || !EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
			remove();
			return;
		}
		Player p = player.getOnlinePlayer();
		for(PotionEffect effect: p.getActivePotionEffects())
			if (ESArrays.getBadPotions().contains(effect.getType())) {
				MagicGuardPotionEvent event = new MagicGuardPotionEvent(p, effect.getType());
				Bukkit.getPluginManager().callEvent(event);

				if (!event.isCancelled()) p.removePotionEffect(effect.getType());
			}
	}

	@Override
	protected void remove() {
		MAGIC_THREADS.remove(this);
		super.remove();
	}
}
