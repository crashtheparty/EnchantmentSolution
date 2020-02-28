package org.ctp.enchantmentsolution.inventory.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.inventory.InventoryData;

public class Minigame implements InventoryData {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private Block block;
	private boolean opening;

	public Minigame(Player player, Block block) {
		setPlayer(player);
		playerItems = new ArrayList<ItemStack>();
		this.block = block;
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public void close(boolean external) {}

	@Override
	public void setInventory(List<ItemStack> items) {}

	@Override
	public void setItemName(String name) {}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if (inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else if (inv.getSize() == inventory.getSize()) {
			inv = player.getOpenInventory().getTopInventory();
			inventory = inv;
		} else {
			inventory = inv;
			player.openInventory(inv);
		}
		for(int i = 0; i < inventory.getSize(); i++)
			inventory.setItem(i, new ItemStack(Material.AIR));
		if (opening) opening = false;
		return inv;
	}

	@Override
	public List<ItemStack> getItems() {
		return playerItems;
	}

}
