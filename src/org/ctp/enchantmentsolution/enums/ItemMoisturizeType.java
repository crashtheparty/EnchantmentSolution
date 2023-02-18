package org.ctp.enchantmentsolution.enums;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile;
import org.ctp.enchantmentsolution.utils.files.ItemSpecialBreakFile.ItemSpecialBreakFileType;

public enum ItemMoisturizeType {
	UNSMELT(), WATERLOG(), WET(), EXTINGUISH();

	public String getName() {
		return name();
	}

	public static ItemMoisturizeType getMoisturizeType(Material m) {
		BlockData data = Bukkit.createBlockData(m);
		String name = m.name().toLowerCase(Locale.ROOT);
		if (name.contains("campfire")) return EXTINGUISH;
		if (data instanceof Waterlogged) return WATERLOG;
		if (getFromFile(m, ItemSpecialBreakFileType.UNSMELT) != null) return UNSMELT;
		if (getFromFile(m, ItemSpecialBreakFileType.WET) != null) return WET;

		return null;
	}

	public static Material fromMoisturize(Material m, ItemMoisturizeType type) {
		switch (type) {
			case UNSMELT:
				return getFromFile(m, ItemSpecialBreakFileType.UNSMELT);
			case WET:
				return getFromFile(m, ItemSpecialBreakFileType.WET);
			default:
				break;
		}
		return null;
	}

	private static Material getFromFile(Material m, ItemSpecialBreakFileType type) {
		ItemSpecialBreakFile file = ItemSpecialBreakFile.getFile(type);
		return file.getValues().get(m);
	}
}
