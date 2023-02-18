package org.ctp.enchantmentsolution.listeners.chestloot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Hopper;
import org.ctp.enchantmentsolution.utils.loot.LootUtils;

public class ChestLoot {

	private Block startingBlock;
	private List<Block> lootToCheck = new ArrayList<Block>();
	private List<Block> checkedBlocks = new ArrayList<Block>();
	private boolean checkBlocks = false;

	protected ChestLoot(Block b) {
		startingBlock = b;
	}

	protected void checkBlocks() {
		checkBlocks(startingBlock);
		checkBlocks = true;
	}

	private void checkBlocks(Block block) {
		if (checkedBlocks.contains(block)) return;
		checkedBlocks.add(block);
		if (block.getType() == Material.HOPPER) {
			Hopper hopper = (Hopper) block.getBlockData();
			Block[] possibleChest = new Block[3];
			possibleChest[0] = block.getRelative(hopper.getFacing());
			possibleChest[1] = block.getRelative(BlockFace.DOWN);
			possibleChest[2] = block.getRelative(BlockFace.UP);
			for(int i = 0; i < possibleChest.length; i++)
				checkBlocks(possibleChest[i]);
		}
		if (block.getType() == Material.CHEST) checkChest(block);
	}

	private void checkChest(Block block) {
		if (LootUtils.isActiveLootChest(block)) lootToCheck.add(block);
	}

	protected List<Block> getLootToCheck() throws ChestLootException {
		if (checkBlocks == false) throw new ChestLootException("Property has yet to be defined.", new Throwable("Property has yet to be defined."));
		return lootToCheck;
	}
}
