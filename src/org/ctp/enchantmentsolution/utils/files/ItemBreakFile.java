package org.ctp.enchantmentsolution.utils.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Material;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.enums.ItemBreakType;

public class ItemBreakFile {

	private static List<ItemBreakFile> FILES = new ArrayList<ItemBreakFile>();

	private final ItemBreakType type;
	private final ItemBreakFileType fileType;
	private final List<ItemBreakSubFile> subFiles;

	public ItemBreakFile(ItemBreakFileType fileType, ItemBreakType type) {
		this.fileType = fileType;
		this.type = type;
		List<ItemBreakSubFile> files = new ArrayList<ItemBreakSubFile>();
		File file = getFile(fileType.getFileName());
		do {
			String typeName = type.name().toLowerCase(Locale.ROOT);
			files.add(new ItemBreakSubFile(typeName, getValues(file, typeName)));
			type = type.getSubType();
		} while (type != null);
		file.deleteOnExit();
		subFiles = files;
	}

	public List<ItemBreakSubFile> getSubFiles() {
		return subFiles;
	}

	public ItemBreakType getType() {
		return type;
	}

	public List<Material> getMaterials(boolean first) {
		List<Material> materials = new ArrayList<Material>();
		if (first) for(String s: subFiles.get(0).getValues()) {
			MatData data = new MatData(s);
			if (data.hasMaterial()) materials.add(data.getMaterial());
		}
		else
			for(ItemBreakSubFile file: subFiles)
				for(String s: file.getValues()) {
					MatData data = new MatData(s);
					if (data.hasMaterial()) materials.add(data.getMaterial());
				}
		return materials;
	}

	public static List<Material> getAllMaterials() {
		List<Material> materials = new ArrayList<Material>();
		for(ItemBreakFile file: FILES)
			for(Material m: file.getMaterials(true))
				if (!materials.contains(m)) materials.add(m);
		return materials;
	}

	public static ItemBreakFile getFile(ItemBreakFileType fileType, ItemBreakType type) {
		for(ItemBreakFile file: FILES)
			if (file.getFileType() == fileType && file.getType() == type) return file;
		return null;
	}

	public static void setFiles() {
		if (FILES.size() > 0) return;
		for(ItemBreakType type: ItemBreakType.values())
			for(ItemBreakFileType fileType: ItemBreakFileType.values())
				FILES.add(new ItemBreakFile(fileType, type));
	}

	private File getFile(String fileName) {
		return CrashConfigUtils.getTempFile(getClass(), "/resources/blocks/" + fileName);
	}

	private List<String> getValues(File file, String section) {
		YamlConfig config = new YamlConfig(file, new String[] {});
		config.getFromConfig();
		List<String> values = config.getStringList(section);
		if (values == null) values = new ArrayList<String>();
		return values;
	}

	public ItemBreakFileType getFileType() {
		return fileType;
	}

	public enum ItemBreakFileType {
		BREAK("break.yml"), SILK_TOUCH("silk_touch.yml"), FORTUNE("fortune.yml");

		private final String fileName;

		ItemBreakFileType(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

}
