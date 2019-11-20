package org.ctp.enchantmentsolution.nms.world;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.DifficultyDamageScaler;
import net.minecraft.server.v1_13_R2.WorldServer;

@SuppressWarnings("resource")
public class World_v1_13_R2 {
	public static float[] getRegionalDifficulty(Block block) {
		WorldServer server = ((CraftWorld) block.getWorld()).getHandle();
		DifficultyDamageScaler scalar = server
		.getDamageScaler(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		return new float[] { scalar.b(), scalar.d() };
	}
}
