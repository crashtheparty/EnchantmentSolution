package org.ctp.enchantmentsolution.nms;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.nms.chest.ChestPopulate_v1_13_R2;
import org.ctp.enchantmentsolution.nms.chest.ChestPopulate_v1_14_R1;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.chest.ChestPopulate_v1_13_R1;

public class ChestPopulateNMS {
	public static void populateChest(Player player, Block block) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				ChestPopulate_v1_13_R1.populateChest(player, block);
				break;
			case 2:
			case 3:
				ChestPopulate_v1_13_R2.populateChest(player, block);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				ChestPopulate_v1_14_R1.populateChest(player, block);
				break;
		}
	}

	public static boolean isLootChest(Block block) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return ChestPopulate_v1_13_R1.isLootChest(block);
			case 2:
			case 3:
				return ChestPopulate_v1_13_R2.isLootChest(block);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return ChestPopulate_v1_14_R1.isLootChest(block);
		}
		return false;
	}

	public static void populateCart(Player player, Entity e) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				ChestPopulate_v1_13_R1.populateCart(player, e);
				break;
			case 2:
			case 3:
				ChestPopulate_v1_13_R2.populateCart(player, e);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				ChestPopulate_v1_14_R1.populateCart(player, e);
				break;
		}
	}

	public static boolean isLootCart(Entity e) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return ChestPopulate_v1_13_R1.isLootCart(e);
			case 2:
			case 3:
				return ChestPopulate_v1_13_R2.isLootCart(e);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return ChestPopulate_v1_14_R1.isLootCart(e);
		}
		return false;
	}
}
