package org.ctp.enchantmentsolution.mcmmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.generate.FishingEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

import com.gmail.nossr50.mcMMO;

public class McMMOHandler {

	public static void handleBlockDrops(BlockDropItemEvent event, ItemStack item, Enchantment enchantment) {
		if (EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled")) return;

		McMMOBlockDrops.handleBlockDrops(event, item, enchantment);
	}

	public static void customName(Entity e) {
		if (e.hasMetadata(mcMMO.customNameKey)) {
			String oldName = e.getMetadata(mcMMO.customNameKey).get(0).asString();
			e.setCustomName(oldName);
		}
		if (e.hasMetadata(mcMMO.customVisibleKey)) {
			boolean oldNameVisible = e.getMetadata(mcMMO.customVisibleKey).get(0).asBoolean();
			e.setCustomNameVisible(oldNameVisible);
		}
	}

	public static List<EnchantmentLevel> getEnchants(Player player, ItemStack treasure) {
		FishingEnchantments ench = FishingEnchantments.getFishingEnchantments(player, treasure);
		if (ench != null) return ench.getEnchantmentList().getEnchantments();
		return new ArrayList<EnchantmentLevel>();
	}
}
