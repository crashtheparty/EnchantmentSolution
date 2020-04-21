package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.LegacyAnvil;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class AnvilUtils {

	private static List<Player> OPEN_LEGACY = new ArrayList<Player>();

	public static void addLegacyAnvil(Player player) {
		OPEN_LEGACY.add(player);
	}

	public static boolean hasLegacyAnvil(Player player) {
		for(Player p: OPEN_LEGACY)
			if (p.equals(player)) return true;
		return false;
	}

	public static void removeLegacyAnvil(LegacyAnvil anvil) {
		OPEN_LEGACY.remove(anvil.getPlayer());
	}

	public static void checkAnvilBreak(Player player, Block block, Anvil anvil) {
		if (block == null) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			return;
		}
		if (!ConfigString.DAMAGE_ANVIL.getBoolean() || player.getGameMode().equals(GameMode.CREATIVE)) {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			return;
		}
		double chance = .12;
		double roll = Math.random();
		if (chance > roll) {
			Material material = Material.AIR;
			switch (block.getType()) {
				case ANVIL:
					material = Material.CHIPPED_ANVIL;
					break;
				case CHIPPED_ANVIL:
					material = Material.DAMAGED_ANVIL;
					break;
				default:

			}
			if (material == Material.AIR) {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
				if (anvil != null) anvil.close(false);
				block.setType(material);
			} else {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				BlockFace facing = ((Directional) block.getBlockData()).getFacing();
				block.setType(material);
				Directional d = (Directional) block.getBlockData();
				d.setFacing(facing);
				block.setBlockData(d);
			}
		} else
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
	}
}
