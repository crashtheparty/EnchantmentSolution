package org.ctp.enchantmentsolution.nms;

import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.packet.*;

public class PacketNMS {

	public static int addParticle(Block block, int stage) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return Packet_v1_13_R1.addParticle(block, stage);
			case 2:
			case 3:
				return Packet_v1_13_R2.addParticle(block, stage);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return Packet_v1_14_R1.addParticle(block, stage);
			case 9:
				return Packet_v1_15_R1.addParticle(block, stage);
		}
		return 0;
	}
	
	public static int updateParticle(Block block, int stage, int id) {
		switch (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return Packet_v1_13_R1.updateParticle(block, stage, id);
			case 2:
			case 3:
				return Packet_v1_13_R2.updateParticle(block, stage, id);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return Packet_v1_14_R1.updateParticle(block, stage, id);
			case 9:
				return Packet_v1_15_R1.updateParticle(block, stage, id);
		}
		return 0;
	}
}
