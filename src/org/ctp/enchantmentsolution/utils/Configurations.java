package org.ctp.enchantmentsolution.utils;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.config.*;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.utils.config.*;
import org.ctp.enchantmentsolution.utils.files.ESLanguageFile;

public class Configurations implements CrashConfigurations {

	private static boolean INITIALIZING = true, FIRST_SAVE = true;
	private final static Configurations CONFIGURATIONS = new Configurations();
	private MainConfiguration CONFIG;
	private FishingConfiguration FISHING;
	private LanguageConfiguration LANGUAGE;
	private EnchantmentsConfiguration ENCHANTMENTS;
	private AdvancementsConfiguration ADVANCEMENTS;
	private RPGConfiguration RPG;
	private MinigameConfiguration MINIGAME;
	private HardModeConfiguration HARD_MODE;

	private List<ESLanguageFile> LANGUAGE_FILES = new ArrayList<ESLanguageFile>();
	private DataFile DATA_FILE;
	private DataFile DEBUG_FILE;

	public static Configurations getConfigurations() {
		return CONFIGURATIONS;
	}

	@Override
	public void onEnable() {
		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();

		try {
			if (!dataFolder.exists()) dataFolder.mkdirs();
		} catch (final Exception e) {
			Chatable.sendStackTrace(e);
		}

		BackupDB db = EnchantmentSolution.getPlugin().getDb();
		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };

		CONFIG = new MainConfiguration(dataFolder, db, header);
		FISHING = new FishingConfiguration(dataFolder, db, header);
		ENCHANTMENTS = new EnchantmentsConfiguration(dataFolder, db, header);
		ADVANCEMENTS = new AdvancementsConfiguration(dataFolder, db, header);
		RPG = new RPGConfiguration(dataFolder, db, header);
		MINIGAME = new MinigameConfiguration(dataFolder, db, header);
		HARD_MODE = new HardModeConfiguration(dataFolder, db, header);

		String languageFile = CONFIG.getString("language_file");
		Language lang = Language.getLanguage(CONFIG.getString("language"));
		if (!lang.getLocale().equals(CONFIG.getString("language"))) CONFIG.updatePath("language", lang.getLocale());

		File languages = new File(dataFolder + "/language");

		if (!languages.exists()) languages.mkdirs();

		LANGUAGE_FILES.add(new ESLanguageFile(dataFolder, Language.US));
		LANGUAGE_FILES.add(new ESLanguageFile(dataFolder, Language.GERMAN));
		LANGUAGE_FILES.add(new ESLanguageFile(dataFolder, Language.SPANISH));
		LANGUAGE_FILES.add(new ESLanguageFile(dataFolder, Language.CHINA_SIMPLE));

		for(ESLanguageFile file: LANGUAGE_FILES)
			if (file.getLanguage() == lang) LANGUAGE = new LanguageConfiguration(dataFolder, languageFile, file, db, header);

		if (LANGUAGE == null) LANGUAGE = new LanguageConfiguration(dataFolder, languageFile, LANGUAGE_FILES.get(0), db, header);

		File extras = new File(dataFolder + "/extras");
		File dataBackups = new File(dataFolder + "/extras/backups-data");

		if (!extras.exists()) extras.mkdirs();
		if (!dataBackups.exists()) dataBackups.mkdirs();

		DATA_FILE = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "data.yml", true, true);
		DEBUG_FILE = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "debug.yml", true, false);

		INITIALIZING = false;
		save();
	}

	public void revert() {
		CONFIG.revert();
		FISHING.revert();
		LANGUAGE.revert();
		ENCHANTMENTS.revert();
		ADVANCEMENTS.revert();
		RPG.revert();
		MINIGAME.revert();
		HARD_MODE.revert();
	}

	public void revert(Configuration config, int backup) {
		config.revert(backup);
	}

	@Override
	public void save() {
		CONFIG.setComments(ConfigString.USE_COMMENTS.getBoolean());
		FISHING.setComments(ConfigString.USE_COMMENTS.getBoolean());
		LANGUAGE.setComments(ConfigString.USE_COMMENTS.getBoolean());
		ENCHANTMENTS.setComments(ConfigString.USE_COMMENTS.getBoolean());
		ADVANCEMENTS.setComments(ConfigString.USE_COMMENTS.getBoolean());
		RPG.setComments(ConfigString.USE_COMMENTS.getBoolean());
		MINIGAME.setComments(ConfigString.USE_COMMENTS.getBoolean());
		HARD_MODE.setComments(ConfigString.USE_COMMENTS.getBoolean());

		CONFIG.save();
		FISHING.save();
		LANGUAGE.save();
		ENCHANTMENTS.save();
		ADVANCEMENTS.save();
		RPG.save();
		MINIGAME.save();
		HARD_MODE.save();

		if (ConfigString.RESET_ON_RELOAD.getBoolean()) TableEnchantments.removeAllTableEnchantments();
		RegisterEnchantments.setEnchantments();
		EnchantmentSolution.getPlugin().reEquipItems();

		if (!FIRST_SAVE) {
			EnchantmentSolution.getPlugin().setVersionCheck(ConfigString.LATEST_VERSION.getBoolean(), ConfigString.EXPERIMENTAL_VERSION.getBoolean());
			AdvancementUtils.createAdvancements();
			EnchantmentSolution.getPlugin().getWiki().resetRunner();
		}
		Minigame.reset();
		FIRST_SAVE = false;
	}

	public void generateDebug() {
		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();
		DataFile data = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "debug.yml", false, false);
		YamlConfig debug = data.getConfig();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z Z");
		debug.set("time", format.format(new Date()));
		debug.set("version.bukkit", VersionUtils.getMinecraftVersion());
		debug.set("version.plugin", EnchantmentSolution.getPlugin().getPluginVersion().getCurrent().getVersionName());
		debug.set("plugins.jobs_reborn", EnchantmentSolution.getPlugin().isJobsEnabled());
		debug.set("plugins.mcmmo", EnchantmentSolution.getPlugin().getMcMMOType());
		debug.set("plugins.mcmmo_version", EnchantmentSolution.getPlugin().getMcMMOVersion());
		debug.set("plugins.mmo_items", CrashAPIPlugin.getMMOItems());
		debug.set("plugins.vein_miner", EnchantmentSolution.getPlugin().getVeinMiner());
		List<String> allPlugins = new ArrayList<String>();
		for(Plugin pl: Bukkit.getPluginManager().getPlugins())
			if (pl.isEnabled()) allPlugins.add(pl.getDescription().getName() + " " + pl.getDescription().getVersion());
		debug.set("plugins.all_plugins", allPlugins);

		debug.copy(DEBUG_FILE.getConfig());
		debug.saveConfig();
		
		try {
			FileOutputStream fos = new FileOutputStream(Paths.get(dataFolder.toURI()).toAbsolutePath() + "/debug.zip");
	        try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
				List<String> files = Arrays.asList(CONFIG.getFile().getPath(), FISHING.getFile().getPath(), LANGUAGE.getFile().getPath(), 
				ENCHANTMENTS.getFile().getPath(), ADVANCEMENTS.getFile().getPath(), RPG.getFile().getPath(), MINIGAME.getFile().getPath(), 
				HARD_MODE.getFile().getPath(), DATA_FILE.getFile().getPath(), data.getFile().getPath());
				
				for (String srcFile : files) {
				    File fileToZip = new File(srcFile);
				    FileInputStream fis = new FileInputStream(fileToZip);
				    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				    zipOut.putNextEntry(zipEntry);

				    byte[] bytes = new byte[1024];
				    int length;
				    while((length = fis.read(bytes)) >= 0)
						zipOut.write(bytes, 0, length);
				    fis.close();
				}
			}
		} catch (IOException e) {
			Chatable.sendStackTrace(e);
		}
	}

	public void reload() {
		CONFIG.reload();
		FISHING.reload();
		LANGUAGE.reload();
		ENCHANTMENTS.reload();
		ADVANCEMENTS.reload();
		RPG.reload();
		MINIGAME.reload();
		HARD_MODE.reload();

		save();
	}

	public MainConfiguration getConfig() {
		return CONFIG;
	}

	public FishingConfiguration getFishing() {
		return FISHING;
	}

	public LanguageConfiguration getLanguage() {
		return LANGUAGE;
	}

	public EnchantmentsConfiguration getEnchantments() {
		return ENCHANTMENTS;
	}

	public AdvancementsConfiguration getAdvancements() {
		return ADVANCEMENTS;
	}

	public RPGConfiguration getRPG() {
		return RPG;
	}

	public List<ESLanguageFile> getLanguageFiles() {
		return LANGUAGE_FILES;
	}

	public DataFile getDataFile() {
		return DATA_FILE;
	}

	public DataFile getDebugFile() {
		return DEBUG_FILE;
	}

	public MinigameConfiguration getMinigames() {
		return MINIGAME;
	}

	public HardModeConfiguration getHardMode() {
		return HARD_MODE;
	}

	public static boolean isInitializing() {
		return INITIALIZING;
	}
}
