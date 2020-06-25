package org.ctp.enchantmentsolution.utils.player;

import java.util.*;

import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;

public class ESPlayer {
	
	private final OfflinePlayer player;
	private RPGPlayer rpg;
	private Map<Enchantment, Long> cooldowns;
	private List<ItemStack> soulItems;
	
	public ESPlayer(OfflinePlayer player) {
		this.player = player;
		rpg = RPGUtils.getPlayer(player);
		cooldowns = new HashMap<Enchantment, Long>();
		removeSoulItems();
	}

	public OfflinePlayer getPlayer() {
		return player;
	}
	
	public long getCooldown(Enchantment enchant) {
		return cooldowns.containsKey(enchant) ? cooldowns.get(enchant) : 0;
	}
	
	public boolean setCooldown(Enchantment enchant) {
		cooldowns.put(enchant, System.currentTimeMillis());
		return cooldowns.containsKey(enchant);
	}

	public RPGPlayer getRPG() {
		return rpg;
	}

	public List<ItemStack> getSoulItems() {
		return soulItems;
	}
	
	public void setSoulItems(List<ItemStack> items) {
		soulItems = items;
	}

	public void removeSoulItems() {
		soulItems = new ArrayList<ItemStack>();
	}
	
}
