package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AsyncBlockController {

	private final Player player;
	private final ItemStack item;
	private final Block original;
	private Collection<Location> allBlocks;

	public AsyncBlockController(Player player, ItemStack item, Block original, Collection<Location> allBlocks) {
		this.player = player;
		this.item = item;
		this.original = original;
		this.allBlocks = allBlocks;

		List<Location> breaking = new ArrayList<Location>();
		for(Location loc: allBlocks)
			if (BlockUtils.isAdjacent(loc.getBlock(), original)) breaking.add(loc);
		breakingBlocks(breaking);
	}

	protected AsyncBlockController(Player player, ItemStack item, Block original) {
		this.player = player;
		this.item = item;
		this.original = original;
	}

	private void breakingBlocks(List<Location> breaking) {
		List<Location> adjacent = new ArrayList<Location>();
		int blocksBrokenTick = 0;
		boolean combine = false;
		Iterator<Location> iter = breaking.iterator();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);

		List<Location> breakNext = new ArrayList<Location>();
		if (esPlayer.canBreakBlock()) {
			while (iter.hasNext()) {
				Location b = iter.next();
				if (!esPlayer.canBreakBlock()) {
					combine = true;
					break;
				}
				if (!esPlayer.isInInventory(item) || item == null || MatData.isAir(item.getType())) {
					for(Location loc: allBlocks)
						BlockUtils.removeMultiBlockBreak(loc, RegisterEnchantments.GAIA);
					return;
				}
				if (BlockUtils.multiBreakBlock(player, item, b, RegisterEnchantments.HEIGHT_PLUS_PLUS)) blocksBrokenTick++;
				allBlocks.remove(b);
				for(Location loc: allBlocks)
					if (!breaking.contains(loc) && !adjacent.contains(loc) && BlockUtils.isAdjacent(loc.getBlock(), b.getBlock())) adjacent.add(loc);
				iter.remove();
			}
			if (combine) {
				breakNext.addAll(breaking);
				for(Location loc: adjacent)
					if (!breakNext.contains(loc)) breakNext.add(loc);
			} else
				breakNext.addAll(adjacent);
		} else
			breakNext = breaking;
		List<Location> finalBreakNext = breakNext;
		AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", blocksBrokenTick);
		if (finalBreakNext.size() > 0) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			breakingBlocks(finalBreakNext);
		}, 1l);
		else
			for(Location loc: allBlocks)
				BlockUtils.removeMultiBlockBreak(loc, RegisterEnchantments.HEIGHT_PLUS_PLUS);

	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public ItemStack getItem() {
		return item;
	}

	public Block getOriginal() {
		return original;
	}

	protected Collection<Location> getAllBlocks() {
		return allBlocks;
	}

	protected void setAllBlocks(Collection<Location> allBlocks) {
		this.allBlocks = allBlocks;
	}
}
