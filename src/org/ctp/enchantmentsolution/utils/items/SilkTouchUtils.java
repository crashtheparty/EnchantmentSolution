package org.ctp.enchantmentsolution.utils.items;

import java.io.File;

import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.enums.ItemBreakType;

public class SilkTouchUtils {

	public static ItemStack getSilkTouchItem(Block block, ItemStack item) {
		MatData mat = getDrop(block, item);
		if (mat.hasMaterial() && !MatData.isAir(mat.getMaterial())) {
			switch (block.getType().name()) {
				case "BEE_NEST":
				case "BEEHIVE":
					ItemStack drop = new ItemStack(mat.getMaterial());
					BlockStateMeta im = (BlockStateMeta) drop;
					Beehive hive = (Beehive) block.getState();
					im.setBlockState(hive);
					drop.setItemMeta(im);
					return drop;
				default:
					break;
			}
			return new ItemStack(mat.getMaterial());
		}
		return null;
	}

	private static MatData getDrop(Block block, ItemStack item) {
		File file = CrashConfigUtils.getTempFile(new SilkTouchUtils().getClass(), "/resources/abilities/silk_touch.yml");
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();

		ItemBreakType type = ItemBreakType.getType(item.getType());
		if (type != null && type.getBreakTypes().contains(block.getType()) || type.getBasicTypes().contains(block.getType())) return new MatData(config.getString(block.getType().name().toLowerCase()));
		return new MatData("air");
	}
}
