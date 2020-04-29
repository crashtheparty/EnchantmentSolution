package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ForceFeedPlayer {

	private final Player player;
	private static double CHANCE = 0.005;

	public ForceFeedPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public double getChance() {
		return CHANCE;
	}

	public List<ItemStack> getForceFeedItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item: player.getInventory().getArmorContents())
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.FORCE_FEED)) items.add(item);
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if (mainHand != null && ItemUtils.hasEnchantment(mainHand, RegisterEnchantments.FORCE_FEED)) items.add(mainHand);
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (offHand != null && ItemUtils.hasEnchantment(offHand, RegisterEnchantments.FORCE_FEED)) items.add(offHand);
		return items;
	}
}
