package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.ctp.crashapi.data.items.MatData;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.utils.files.ItemBreakFile.ItemBreakFileType;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile.ItemSpecialBreakFileType;

public class SilkTouchUtils {

	public static ItemStack getSilkTouchItem(Block block, ItemStack item) {
		Material mat = getDrop(block, item);
		if (!MatData.isAir(mat)) {
			switch (block.getType().name()) {
				case "BEE_NEST":
				case "BEEHIVE":
					ItemStack drop = new ItemStack(mat);
					BlockStateMeta im = (BlockStateMeta) drop;
					Beehive hive = (Beehive) block.getState();
					im.setBlockState(hive);
					drop.setItemMeta(im);
					return drop;
				default:
					break;
			}
			return new ItemStack(mat);
		}
		return null;
	}

	private static Material getDrop(Block block, ItemStack item) {
		ItemSpecialBreakFile file = ItemSpecialBreakFile.getFile(ItemSpecialBreakFileType.SILK_TOUCH);

		ItemBreakType type = ItemBreakType.getType(item.getType());
		if (type != null && type.getBreakTypes().contains(block.getType()) || ItemBreakType.getBasicTypes(ItemBreakFileType.BREAK).contains(block.getType())) 
			return file.getValues().get(block.getType());
		return Material.AIR;
	}
}
