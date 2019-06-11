package org.ctp.enchantmentsolution.listeners.advancements;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class AdvancementThread implements Runnable{

	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			boolean hasTank = true;
			for(ItemStack item : player.getInventory().getArmorContents()) {
				if(item == null || !(Enchantments.hasEnchantment(item, DefaultEnchantments.TANK) && 
						Enchantments.getLevel(item, DefaultEnchantments.TANK) == DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK).getMaxLevel())) {
					hasTank = false;
				}
			}
			if(hasTank) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.PANZER_SOLDIER, "tank");
			}
			if(player.getFireTicks() != 0 && player.getInventory().getBoots() != null) {
				ItemStack boots = player.getInventory().getBoots();
				if(Enchantments.hasEnchantment(boots, DefaultEnchantments.MAGMA_WALKER)) {
					if(MagmaWalkerListener.BLOCKS.contains(player.getLocation().getBlock().getRelative(BlockFace.DOWN))) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.THIS_GIRL_IS_ON_FIRE, "lava");
					}
				}
			}
		}
	}

}
