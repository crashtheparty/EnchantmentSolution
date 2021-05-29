package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.ctp.crashapi.utils.LocationUtils;

public class HWDModel {

	private final Location lowBound, highBound;
	private final Block startingPoint;
	private final NamespacedKey key;
	private final OfflinePlayer player;
	private final ItemStack possibleItem;
	private final Material startingPointMaterial;
	private List<Location> used = new ArrayList<Location>();
	private List<Block> current = new ArrayList<Block>();

	public HWDModel(Block startingPoint, Location lowBound, Location highBound, OfflinePlayer player, NamespacedKey key, ItemStack possibleItem) {
		this.startingPoint = startingPoint;
		startingPointMaterial = startingPoint.getType();
		this.lowBound = lowBound;
		this.highBound = highBound;
		this.key = key;
		this.player = player;
		this.possibleItem = possibleItem;
		used.add(startingPoint.getLocation());
		aroundBlock(startingPoint);
	}

	public Location getLowBound() {
		return lowBound;
	}

	public Location getHighBound() {
		return highBound;
	}

	public Block getStartingPoint() {
		return startingPoint;
	}

	public ItemStack getPossibleItem() {
		return possibleItem;
	}

	public boolean hasItem() {
		return getItem() != null;
	}

	public ItemStack getItem() {
		if (player.isOnline()) {
			Player p = player.getPlayer();
			for(ItemStack item: p.getInventory().getContents()) {
				if (item == null || item.getItemMeta() == null) continue;
				if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return item;
			}
		}
		return null;
	}

	public void aroundBlock(Block b) {
		for(int x = -1; x <= 1; x++)
			for(int y = -1; y <= 1; y++)
				for(int z = -1; z <= 1; z++) {
					if (x == 0 && y == 0 && z == 0) continue;
					Block block = b.getRelative(x, y, z);
					if (LocationUtils.getIntersecting(lowBound, highBound, block.getLocation(), block.getLocation()) && !used.contains(block.getLocation())) {
						current.add(block);
						used.add(block.getLocation());
					}
				}
	}

	public List<Block> getCurrent() {
		return current;
	}

	public void setUsed(Block block) {
		current.remove(block);
		used.add(block.getLocation());
	}

	public NamespacedKey getKey() {
		return key;
	}

	public Material getStartingPointMaterial() {
		return startingPointMaterial;
	}
}
