package org.ctp.enchantmentsolution.events.soul;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.SoulEvent;

public class SoulReaperEvent extends SoulEvent {

	private final List<ItemStack> reapedItems;
	private final Player killer;

	public SoulReaperEvent(Player who, Player killer, int level, List<ItemStack> reapedItems) {
		super(who, new EnchantmentLevel(CERegister.SOUL_REAPER, level));
		this.killer = killer;
		this.reapedItems = reapedItems;
	}

	public List<ItemStack> getReapedItems() {
		return reapedItems;
	}

	public Player getKiller() {
		return killer;
	}
}
