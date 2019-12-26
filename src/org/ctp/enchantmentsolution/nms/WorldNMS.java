package org.ctp.enchantmentsolution.nms;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.world.*;

public class WorldNMS {

	public static float[] getRegionalDifficulty(Block block) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return World_v1_13_R1.getRegionalDifficulty(block);
			case 2:
			case 3:
				return World_v1_13_R2.getRegionalDifficulty(block);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return World_v1_14_R1.getRegionalDifficulty(block);
			case 9:
			case 10:
				return World_v1_15_R1.getRegionalDifficulty(block);
		}
		return new float[2];
	}
}
