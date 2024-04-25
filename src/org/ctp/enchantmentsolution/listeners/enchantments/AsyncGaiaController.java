package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.crashapi.data.items.MatData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.threads.GaiaItemThread;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaTrees;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AsyncGaiaController {

	private final GaiaTrees tree;

	private final Player player;
	private final ItemStack item;
	private final Block original;
	private List<Location> allBlocks;
	private List<Location> breaking;
	private int tick = 19;
	private boolean remove = false;

	public AsyncGaiaController(Player player, ItemStack item, Block original, List<Location> allBlocks, GaiaTrees tree) {
		this.player = player;
		this.item = item;
		this.original = original;
		this.allBlocks = allBlocks;
		this.tree = tree;

		breaking = new ArrayList<Location>();
		for(int i = 0; i < 4; i++)
			if (allBlocks.size() > i) breaking.add(allBlocks.get(i));
	}

	public void addBlocks(Block original, List<Location> blocks) {
		remove = false;
		for(Location loc: blocks) {
			allBlocks.add(loc);
			if (BlockUtils.isNextTo(loc.getBlock(), original)) breaking.add(loc);
		}
	}

	public void breakingBlocks() {
		tick++;
		tick = tick % 20;
		if (tick != 0) return;
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (breaking == null || breaking.size() == 0) {
			remove = true;
			return;
		}
		Iterator<Location> iter = breaking.iterator();

		List<Location> breakNext = new ArrayList<Location>();
		if (esPlayer.canBreakBlock()) while (iter.hasNext()) {
			Location b = iter.next();
			if (!esPlayer.canBreakBlock()) break;
			if (!esPlayer.isInInventory(item) || item == null || MatData.isAir(item.getType())) {
				remove = true;
				return;
			}
			BlockUtils.multiBreakBlock(player, item, b, RegisterEnchantments.GAIA);
			AdvancementUtils.awardCriteria(player, ESAdvancement.DEFORESTATION, "tree", 1);
			allBlocks.remove(b);
			for(Location loc: BlockUtils.getNextTo(b, 3)) {
				boolean contains = false;
				for(MatData leaf: tree.getLeaves())
					if (loc.getBlock().getType() == leaf.getMaterial()) contains = true;
				if (contains) {
					BlockUtils.multiBreakBlock(player, null, loc, RegisterEnchantments.GAIA);
					double chance = 0.02;
					if (tree == GaiaTrees.MANGROVE) chance = 0.001;
					if (Math.random() < chance) {
						ItemStack sapling = new ItemStack(tree.getSapling().getMaterial());
						Item i = loc.getWorld().dropItemNaturally(loc, sapling);
						i.setPickupDelay(Integer.MAX_VALUE);
						i.setMetadata("sapling_gaia", new FixedMetadataValue(EnchantmentSolution.getPlugin(), UUID.randomUUID().toString()));
						GaiaItemThread thread = new GaiaItemThread(EnchantmentSolution.getESPlayer(player), i, tree);
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				}
			}
			iter.remove();
		}
		int j = breaking.size();
		for(int i = j; i < 4; i++)
			if (allBlocks.size() > i) breakNext.add(allBlocks.get(i));
		breaking = breakNext;

	}

	public GaiaTrees getTree() {
		return tree;
	}

	public int getBlockBreakAmount() {
		return EnchantmentUtils.getLevel(item, RegisterEnchantments.GAIA) + 1;
	}

	public Player getPlayer() {
		return player;
	}

	public ItemStack getItem() {
		return item;
	}

	public Block getOriginal() {
		return original;
	}

	public void removeBlocks() {
		for(Location loc: allBlocks)
			BlockUtils.removeMultiBlockBreak(loc, RegisterEnchantments.GAIA);
	}

	public boolean willRemove() {
		return remove;
	}

}
