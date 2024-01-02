package org.ctp.enchantmentsolution.utils.items;

import java.io.File;

import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.utils.CrashConfigUtils;

public class EnchantmentDrops {

	private final String fileName;
	private final boolean custom;
	private final Interactions[] drops;

	public EnchantmentDrops(String fileName, boolean custom) {
		this.fileName = fileName;
		this.custom = custom;
		File f = CrashConfigUtils.getTempFile(getClass(), fileName);
		YamlConfig config = new YamlConfig(f, new String[0]);
		config.getFromConfig();
		this.drops = Interactions.fromConfig(config, custom);
		f.delete();
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isCustom() {
		return custom;
	}

	public Interactions[] getDrops() {
		return drops;
	}

}
