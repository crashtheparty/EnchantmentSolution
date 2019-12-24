package org.ctp.enchantmentsolution.threads;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AdvancementThread implements Runnable {

	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			boolean hasTank = true;
			for(ItemStack item: player.getInventory().getArmorContents()) {
				if (item == null || !(ItemUtils.hasEnchantment(item, RegisterEnchantments.TANK)
				&& ItemUtils.getLevel(item, RegisterEnchantments.TANK) == RegisterEnchantments
				.getCustomEnchantment(RegisterEnchantments.TANK).getMaxLevel())) {
					hasTank = false;
				}
			}
			if (hasTank) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.PANZER_SOLDIER, "tank");
			}
			Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
			WalkerBlock walkerBlock = WalkerUtils.getWalker(block);
			if (player.getInventory().getBoots() != null) {
				ItemStack boots = player.getInventory().getBoots();
				if (ItemUtils.hasEnchantment(boots, RegisterEnchantments.MAGMA_WALKER) && walkerBlock != null
				&& walkerBlock.getEnchantment() == RegisterEnchantments.MAGMA_WALKER && player.getFireTicks() > 0) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.THIS_GIRL_IS_ON_FIRE, "lava");
				}

				if (ItemUtils.hasEnchantment(boots, RegisterEnchantments.VOID_WALKER) && walkerBlock != null
				&& walkerBlock.getEnchantment() == RegisterEnchantments.VOID_WALKER
				&& !LocationUtils.hasBlockBelow(block.getRelative(BlockFace.DOWN).getLocation())) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.MADE_FOR_WALKING, "boots");
				}
			}
			if(OverkillDeath.getDeaths(player.getUniqueId()) != null) {
				Iterator<OverkillDeath> iter = OverkillDeath.getDeaths(player.getUniqueId()).iterator();
				if(iter != null) {
					while(iter.hasNext()) {
						OverkillDeath death = iter.next();
						death.minus();
						if(death.getTicks() <= 0) {
							iter.remove();
						}
					}
					
					List<OverkillDeath> deaths = OverkillDeath.getDeaths(player.getUniqueId());
					if(deaths.size() >= 10) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.KILIMANJARO, "kills");
					}
				}
			}
		}
	}

}
