package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class AsyncHWDController {

	private final Player player;
	private final ItemStack item;
	private final Block original;
	private List<Location> allBlocks;
	private List<Location> breaking;
	private boolean remove = false;

	public AsyncHWDController(Player player, ItemStack item, Block original, List<Location> allBlocks) {
		this.player = player;
		this.item = item;
		this.original = original;
		this.allBlocks = allBlocks;

		breaking = new ArrayList<Location>();
		for(Location loc: allBlocks)
			if (BlockUtils.isNextTo(loc.getBlock(), original)) breaking.add(loc);
	}

	protected AsyncHWDController(Player player, ItemStack item, Block original) {
		this.player = player;
		this.item = item;
		this.original = original;
	}

	public void addBlocks(Block original, List<Location> blocks) {
		remove = false;
		for(Location loc: blocks) {
			allBlocks.add(loc);
			if (BlockUtils.isNextTo(loc.getBlock(), original)) breaking.add(loc);
		}
	}

	public void breakingBlocks() {
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (breaking == null || breaking.size() == 0) {
			remove = true;
			return;
		}
		List<Location> adjacent = new ArrayList<Location>();
		int blocksBrokenTick = 0;
		boolean combine = false;
		Iterator<Location> iter = breaking.iterator();

		List<Location> breakNext = new ArrayList<Location>();
		if (esPlayer.canBreakBlock()) {
			while (iter.hasNext()) {
				Location b = iter.next();
				if (!esPlayer.canBreakBlock()) {
					combine = true;
					break;
				}
				if (!esPlayer.isInInventory(item) || item == null || MatData.isAir(item.getType())) {
					remove = true;
					return;
				}
				if (BlockUtils.multiBreakBlock(player, item, b, RegisterEnchantments.HEIGHT_PLUS_PLUS)) blocksBrokenTick++;
				allBlocks.remove(b);
				for(Location loc: allBlocks)
					if (!breaking.contains(loc) && !adjacent.contains(loc) && BlockUtils.isNextTo(loc.getBlock(), b.getBlock())) adjacent.add(loc);
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
		breaking = breakNext;
		AdvancementUtils.awardCriteria(player, ESAdvancement.OVER_9000, "stone", blocksBrokenTick);
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

	protected List<Location> getAllBlocks() {
		return allBlocks;
	}

	protected void setAllBlocks(List<Location> allBlocks) {
		this.allBlocks = allBlocks;
	}

	public void removeBlocks() {
		for(Location loc: allBlocks)
			BlockUtils.removeMultiBlockBreak(loc, RegisterEnchantments.HEIGHT_PLUS_PLUS);
	}
	
	public boolean willRemove() {
		return remove;
	}
}
