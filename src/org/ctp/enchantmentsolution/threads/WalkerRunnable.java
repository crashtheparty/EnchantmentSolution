package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.utils.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class WalkerRunnable implements Runnable {

	private int run;

	@Override
	public void run() {
		if (run == 0) {
			WalkerUtils.updateBlocks(RegisterEnchantments.MAGMA_WALKER, "MagmaWalker", Arrays.asList(Material.LAVA),
			Material.MAGMA_BLOCK);
			WalkerUtils.updateBlocks(RegisterEnchantments.VOID_WALKER, "VoidWalker",
			Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR), Material.MAGMA_BLOCK);
		}
		try {
			Enchantment enchantment = RegisterEnchantments.VOID_WALKER;
			for(Player player: Bukkit.getOnlinePlayers()) {
				ItemStack boots = player.getInventory().getBoots();
				if (boots != null && ItemUtils.hasEnchantment(boots, enchantment)) {
					if (WalkerUtils.getDelays().containsKey(player)) {
						if (WalkerUtils.getDelays().get(player) == null
						|| WalkerUtils.getDelays().get(player) - 1 <= 0) {
							WalkerUtils.getDelays().remove(player);
						} else {
							WalkerUtils.getDelays().put(player, WalkerUtils.getDelays().get(player) - 1);
						}
					} else if (player.isSneaking()) {
						WalkerUtils.getDelays().put(player, 10);
						Iterator<Block> iterator = WalkerUtils.getBlocks().get(enchantment).iterator();
						while(iterator.hasNext()) {
							Block block = iterator.next();
							if (block.getLocation().getBlockY() > 1) {
								List<MetadataValue> values = block.getMetadata("VoidWalker");
								if (values != null) {
									int radius = 1 + ItemUtils.getLevel(boots, enchantment);
									List<Block> bs = new ArrayList<Block>();
									for(int x = -radius; x <= radius; x++) {
										for(int z = -radius; z <= radius; z++) {
											if (Math.abs(x) + Math.abs(z) > radius + 1) {
												continue;
											}
											bs.add(player.getLocation().getBlock().getRelative(x, -1, z));
										}
									}
									if (bs.contains(block)) {
										WalkerUtils.removeBlockMetadata(block, enchantment, "VoidWalker");
										block.setType(Material.AIR);
										iterator.remove();
										Block lower = block.getRelative(BlockFace.DOWN);
										if (lower.getType().equals(Material.AIR)) {
											WalkerUtils.addBlockMetadata(lower, enchantment, "VoidWalker",
											DamageState.getDefaultMeta());
											lower.setType(Material.OBSIDIAN);
											WalkerUtils.getBlocks().get(enchantment).add(lower);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		run++;
		if (run / 20 != 0) {
			run = 0;
		}
	}

}
