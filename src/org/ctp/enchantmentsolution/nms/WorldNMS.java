package org.ctp.enchantmentsolution.nms;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_14_R1.WorldServer;

public class WorldNMS {

	public static float[] getRegionalDifficulty(Block block) {
		WorldServer server = ((CraftWorld) block.getWorld()).getHandle();
		DifficultyDamageScaler scalar = server.getDamageScaler(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		return new float[] { scalar.b(), scalar.d() };
	}
}
