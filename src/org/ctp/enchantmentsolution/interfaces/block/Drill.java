package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.BlockEntityIgnoredCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.BlockGamemodeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.BlockIsBreakingCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.BlockRadiusMultiBreakEffect;
import org.ctp.enchantmentsolution.utils.BlockUtils;

public class Drill extends BlockRadiusMultiBreakEffect {

	public Drill() {
		super(RegisterEnchantments.WIDTH_PLUS_PLUS, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, "1", "1", "%level% * 4", true, true, true, false, true, new BlockCondition[] { new BlockGamemodeCondition(false, GameMode.SURVIVAL, GameMode.ADVENTURE), new BlockIsBreakingCondition(false), new BlockEntityIgnoredCondition(false) });
	}

	@Override
	public BlockRadiusMultiBreakResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		BlockRadiusMultiBreakResult result = super.run(player, items, brokenData, event);
		if (result.getLevel() == 0) return null;

		Location blockLoc = event.getBlock().getLocation().clone();
		Location playerLoc = player.getLocation().clone();
		float pitch = playerLoc.getPitch();
		float yaw = playerLoc.getYaw() % 360;
		int xt = 0;
		int yt = 0;
		int zt = 0;
		int width = result.getWidth();
		int height = result.getHeight();
		int depth = result.getDepth();
		int valueDepth = 0;
		int hasValue = 0;
		if (width > 0) hasValue ++;
		if (height > 0) hasValue ++;
		if (depth > 0) hasValue ++;
		hasValue = Math.max(hasValue, 1);
		double radius = (width + height + depth) / hasValue;

		while (yaw < 0)
			yaw += 360;
		if (pitch > 53 || pitch <= -53) {
			if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
				xt = width;
				yt = depth;
				zt = height;
				if (pitch > 53) valueDepth = -2;
				else
					valueDepth = 2;
			} else {
				xt = height;
				yt = depth;
				zt = width;
				if (pitch > 53) valueDepth = -2;
				else
					valueDepth = 2;
			}
		} else if (yaw <= 45 || yaw > 135 && yaw <= 225 || yaw > 315) {
			xt = width;
			yt = height;
			zt = depth;
			if (yaw > 45 && yaw <= 225) valueDepth = -3;
			else
				valueDepth = 3;
		} else {
			xt = depth;
			yt = height;
			zt = width;
			if (yaw > 45 && yaw <= 225) valueDepth = -1;
			else
				valueDepth = 1;
		}

		for(int x = -xt; x <= xt; x++)
			for(int y = -yt; y <= yt; y++)
				for(int z = -zt; z <= zt; z++) {
					if ((!isRounded() && (Math.abs(x) + Math.abs(y) + Math.abs(z) > radius)) || (x == 0 && y == 0 && z == 0)) continue;
					if (!isDepthCloser()) switch (valueDepth) {
						case 1:
							if (x < 0) continue;
							break;
						case 2:
							if (y < 0) continue;
							break;
						case 3:
							if (z < 0) continue;
							break;
						case -1:
							if (x > 0) continue;
							break;
						case -2:
							if (y > 0) continue;
							break;
						case -3:
							if (z > 0) continue;
							break;
					}
					Location l = blockLoc.clone().add(x, y, z);
					BlockUtils.addMultiBlockBreak(l, getEnchantment());
					BlockUtils.multiBreakBlock(player, items[0], l, getEnchantment());
				}

		return result;
	}

}
