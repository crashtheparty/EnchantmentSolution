package org.ctp.enchantmentsolution.inventory;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class LegacyAnvil implements InventoryData{
	
	private Player player;
	private Block block;
	private Inventory inventory;
	
	public LegacyAnvil(Player player, Block block, Inventory inv) {
		this.player = player;
		this.block = block;
		this.inventory = inv;
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
		if(EnchantmentSolution.getPlugin().hasInventory(this)) {
			ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "anvil.legacy-gui-close"));
			AnvilUtils.removeLegacyAnvil(this);
			EnchantmentSolution.getPlugin().removeInventory(this);
			if(!external) {
				player.closeInventory();
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {
	}

	@Override
	public void setItemName(String name) {
		return;
	}

}
