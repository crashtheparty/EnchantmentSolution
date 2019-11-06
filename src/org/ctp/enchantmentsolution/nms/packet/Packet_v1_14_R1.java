package org.ctp.enchantmentsolution.nms.packet;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;

public class Packet_v1_14_R1 {
	public static int addParticle(Block block, int stage) {
		int rand = Math.abs(new Random().nextInt());
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(rand,
				new BlockPosition(block.getX(), block.getY(), block.getZ()), stage);
		for(Player player: Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		return rand;
	}

	public static int updateParticle(Block block, int stage, int id) {
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(id,
				new BlockPosition(block.getX(), block.getY(), block.getZ()), stage);
		for(Player player: Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		return id;
	}
}
