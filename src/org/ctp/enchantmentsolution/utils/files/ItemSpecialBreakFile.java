package org.ctp.enchantmentsolution.utils.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.CrashConfigUtils;

public class ItemSpecialBreakFile {
	private static List<ItemSpecialBreakFile> FILES = new ArrayList<ItemSpecialBreakFile>();

	private final ItemSpecialBreakFileType fileType;
	private final YamlConfig config;
	private HashMap<Material, Material> breakBlocks = new HashMap<Material, Material>();

	public ItemSpecialBreakFile(ItemSpecialBreakFileType fileType) {
		this.fileType = fileType;
		File file = getFile(fileType.getFileName());
		config = new YamlConfig(file, new String[0]);
		config.getFromConfig();
		for(String s: config.getAllEntryKeys()) {
			MatData m = new MatData(s);
			if (m.hasMaterial()) {
				MatData value = new MatData(config.getString(s));
				if (value.hasMaterial()) breakBlocks.put(m.getMaterial(), value.getMaterial());
			}
		}
		file.delete();
	}

	public static ItemSpecialBreakFile getFile(ItemSpecialBreakFileType fileType) {
		for(ItemSpecialBreakFile file: FILES)
			if (file.getFileType() == fileType) return file;
		return null;
	}

	public static void setFiles() {
		if (FILES.size() > 0) return;
		for(ItemSpecialBreakFileType fileType: ItemSpecialBreakFileType.values())
			FILES.add(new ItemSpecialBreakFile(fileType));
	}

	private File getFile(String fileName) {
		return CrashConfigUtils.getTempFile(getClass(), "/resources/abilities/" + fileName);
	}

	public HashMap<Material, Material> getValues() {
		return breakBlocks;
	}

	public ItemSpecialBreakFileType getFileType() {
		return fileType;
	}

	public enum ItemSpecialBreakFileType {
		SILK_TOUCH("silk_touch.yml"), SMELTERY("smeltery.yml"), UNSMELT("unsmelt.yml"), WET("wet.yml");

		private final String fileName;

		ItemSpecialBreakFileType(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}
}
