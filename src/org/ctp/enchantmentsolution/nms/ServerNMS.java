package org.ctp.enchantmentsolution.nms;

import org.ctp.enchantmentsolution.EnchantmentSolution;

public class ServerNMS {

	public static int getCurrentTick() {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return net.minecraft.server.v1_13_R1.MinecraftServer.currentTick;
			case 2:
			case 3:
				return net.minecraft.server.v1_13_R2.MinecraftServer.currentTick;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return net.minecraft.server.v1_14_R1.MinecraftServer.currentTick;
			case 9:
			case 10:
			case 11:
				return net.minecraft.server.v1_15_R1.MinecraftServer.currentTick;
			case 12:
				return net.minecraft.server.v1_16_R1.MinecraftServer.currentTick;
			case 13:
			case 14:
				return net.minecraft.server.v1_16_R2.MinecraftServer.currentTick;
		}
		return (int) System.currentTimeMillis() / 50;
	}

}
