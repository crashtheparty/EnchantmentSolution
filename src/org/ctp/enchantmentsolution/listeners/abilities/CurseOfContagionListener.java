package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.custom.CurseOfLag;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class CurseOfContagionListener implements Runnable{

	@Override
	public void run() {
		double chance = 0.0005;
		List<CustomEnchantment> enchantments = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment : Enchantments.getEnchantments()) {
			if(enchantment instanceof CurseOfLag) continue;
			if(enchantment.isCurse()) enchantments.add(enchantment);
		}
		if(enchantments.size() > 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				double random = Math.random();
				double playerChance = 0;
				List<ItemStack> items = new ArrayList<ItemStack>();
				for(ItemStack item : player.getInventory().getContents()) {
					if(item != null && ItemType.ALL.getItemTypes().contains(item.getType())) {
						items.add(item);
						if(Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG)) {
							playerChance += chance;
						}
					}
				}
				if(playerChance > random) {
					if(items.size() > 0) {
						int randomItemInt = (int)(Math.random() * items.size());
						ItemStack randomItem = items.get(randomItemInt);
						if(Math.random() >= 0.5 && randomItem != null && !Enchantments.hasEnchantment(randomItem, DefaultEnchantments.CURSE_OF_LAG)) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1, 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
							Enchantments.addEnchantmentToItem(randomItem, DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.CURSE_OF_LAG), 1);
							continue;
						}
						List<CustomEnchantment> curses = new ArrayList<CustomEnchantment>();
						curses.addAll(enchantments);
						for(int i = curses.size() - 1; i >= 0; i--) {
							CustomEnchantment curse = curses.get(i);
							if(!Enchantments.canAddEnchantment(curse, randomItem)) {
								curses.remove(i);
							}
						}
						while(curses.size() > 0) {
							int randomCursesInt = (int)(Math.random() * curses.size());
							CustomEnchantment curse = curses.get(randomCursesInt);
							if(randomItem != null && !Enchantments.hasEnchantment(randomItem, curse.getRelativeEnchantment())) {
								Enchantments.addEnchantmentToItem(randomItem, curse, 1);
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1, 1);
								player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
								break;
							}
							curses.remove(randomCursesInt);
						}
					}
				}
			}
		}
	}

}
