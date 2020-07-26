package org.ctp.enchantmentsolution.inventory;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.AnvilUtils;

public class LegacyAnvil implements InventoryData {

	private Player player;
	private Block block;
	private Inventory inventory;

	public LegacyAnvil(Player player, Block block, Inventory inv) {
		this.player = player;
		this.block = block;
		inventory = inv;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public void close(boolean external) {
		if (EnchantmentSolution.getPlugin().hasInventory(this)) {
			Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "anvil.legacy-gui-close"));
			AnvilUtils.removeLegacyAnvil(this);
			EnchantmentSolution.getPlugin().removeInventory(this);
			if (!external) player.closeInventory();
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory() {}

	@Override
	public void setInventory(List<ItemStack> items) {}

	@Override
	public void setItemName(String name) {
		return;
	}

	@Override
	public Inventory open(Inventory inv) {
		return inv;
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

}
