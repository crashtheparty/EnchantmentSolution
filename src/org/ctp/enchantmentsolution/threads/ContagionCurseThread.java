package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.player.ContagionEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ContagionCurseThread extends EnchantmentThread {

	private static List<ContagionCurseThread> CONTAGION_THREADS = new ArrayList<ContagionCurseThread>();

	public static ContagionCurseThread createThread(Player player) {
		Iterator<ContagionCurseThread> threads = CONTAGION_THREADS.iterator();
		while (threads.hasNext()) {
			ContagionCurseThread thread = threads.next();
			if (thread.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId())) return thread;
		}
		ContagionCurseThread newThread = new ContagionCurseThread(EnchantmentSolution.getESPlayer(player));
		CONTAGION_THREADS.add(newThread);
		return newThread;
	}

	private ContagionCurseThread(ESPlayer player) {
		super(player);
	}

	@Override
	public void run() {
		ESPlayer player = getPlayer();
		List<CustomEnchantment> enchantments = RegisterEnchantments.getCurseEnchantments();
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.CURSE_OF_CONTAGION) || enchantments.size() == 0 || player.getContagionChance() == 0) {
			remove();
			return;
		}
		Player p = player.getOnlinePlayer();
		if (isDisabled(p, RegisterEnchantments.CURSE_OF_CONTAGION)) return;
		List<ItemStack> items = player.getCurseableItems();
		double random = Math.random();
		if (player.getContagionChance() > random) {
			int randomItemInt = (int) (Math.random() * items.size());
			ItemStack randomItem = items.get(randomItemInt);
			if (Math.random() >= 0.5 && randomItem != null && !EnchantmentUtils.hasEnchantment(randomItem, RegisterEnchantments.CURSE_OF_CONTAGION)) {
				callContagionCurse(p, randomItem, RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.CURSE_OF_CONTAGION));
				return;
			}
			List<CustomEnchantment> curses = new ArrayList<CustomEnchantment>();
			curses.addAll(enchantments);
			for(int i = curses.size() - 1; i >= 0; i--) {
				CustomEnchantment curse = curses.get(i);
				if (!EnchantmentUtils.canAddEnchantment(curse, randomItem)) curses.remove(i);
			}
			while (curses.size() > 0) {
				int randomCursesInt = (int) (Math.random() * curses.size());
				CustomEnchantment curse = curses.get(randomCursesInt);
				if (randomItem != null && !EnchantmentUtils.hasEnchantment(randomItem, curse.getRelativeEnchantment())) {
					callContagionCurse(p, randomItem, curse);
					break;
				}
				curses.remove(randomCursesInt);
			}
		}
	}

	private void callContagionCurse(Player player, ItemStack item, CustomEnchantment curse) {
		List<Sound> sounds = new ArrayList<Sound>();
		sounds.add(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT);
		sounds.add(Sound.BLOCK_ENCHANTMENT_TABLE_USE);
		ContagionEvent event = new ContagionEvent(player, item, curse, 1, sounds);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			EnchantmentUtils.addEnchantmentToItem(event.getItem(), event.getCurse(), event.getLevel());
			if (event.getCurse() == CERegister.CURSE_OF_CONTAGION) AdvancementUtils.awardCriteria(player, ESAdvancement.PLAGUE_INC, "contagion");
			if (hasAllCurses(item)) AdvancementUtils.awardCriteria(player, ESAdvancement.EXTERMINATION, "contagion");
			for(Sound s: event.getSounds())
				player.getWorld().playSound(player.getLocation(), s, event.getVolume(), event.getPitch());
		}
	}

	private boolean hasAllCurses(ItemStack item) {
		boolean noCurse = true;
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (enchantment.isCurse() && EnchantmentUtils.canAddEnchantment(enchantment, item) && !EnchantmentUtils.hasEnchantment(item, enchantment.getRelativeEnchantment())) {
				noCurse = false;
				break;
			}
		return noCurse;
	}

	@Override
	protected void remove() {
		CONTAGION_THREADS.remove(this);
		super.remove();
	}

}
