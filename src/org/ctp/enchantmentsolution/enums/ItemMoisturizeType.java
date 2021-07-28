package org.ctp.enchantmentsolution.enums;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.CrashConfigUtils;

public enum ItemMoisturizeType {
	UNSMELT(), WATERLOG(), WET(), EXTINGUISH();

	public String getName() {
		return name();
	}

	public static ItemMoisturizeType getMoisturizeType(Material mat) {
		BlockData data = Bukkit.createBlockData(mat);
		String name = mat.name().toLowerCase();
		if (extinguish().contains(name)) return EXTINGUISH;
		if (data instanceof Waterlogged) return WATERLOG;
		if (unsmeltable().contains(name)) return UNSMELT;
		if (wet().contains(name)) return WET;

		return null;
	}

	public static Material fromMoisturize(Material mat) {
		MatData data = new MatData(getFromAbilities(mat.name().toLowerCase()));
		if (data.hasMaterial() && !MatData.isAir(data.getMaterial())) return data.getMaterial();
		return null;
	}

	private static List<String> extinguish() {
		return getFromFile("extinguish");
	}

	private static List<String> wet() {
		return getFromFile("wet");
	}

	private static List<String> unsmeltable() {
		return getFromFile("unsmelt");
	}

	private static List<String> getFromFile(String location) {
		File file = CrashConfigUtils.getTempFile(ItemMoisturizeType.WET.getClass(), "/resources/blocks/moisturize.yml");
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();
		List<String> values = config.getStringList(location);
		if (values == null) values = new ArrayList<String>();
		file.delete();
		return values;
	}

	private static String getFromAbilities(String location) {
		File file = CrashConfigUtils.getTempFile(ItemMoisturizeType.WET.getClass(), "/resources/abilities/moisturize.yml");
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();
		String value = config.getString(location);
		if (value == null) value = "AIR";
		file.delete();
		return value;
	}

}
