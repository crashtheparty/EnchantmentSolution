package org.ctp.enchantmentsolution.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.config.*;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.resources.advancements.CrashAdvancementProgress;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
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

	public static Configurations getConfigurations() {
		return CONFIGURATIONS;
	}

	@Override
	public void onEnable() {
		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();

		try {
			if (!dataFolder.exists()) dataFolder.mkdirs();
		} catch (final Exception e) {
			e.printStackTrace();
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
		LANGUAGE_FILES.add(new ESLanguageFile(dataFolder, Language.CHINA_SIMPLE));

		for(ESLanguageFile file: LANGUAGE_FILES)
			if (file.getLanguage() == lang) LANGUAGE = new LanguageConfiguration(dataFolder, languageFile, file, db, header);

		if (LANGUAGE == null) LANGUAGE = new LanguageConfiguration(dataFolder, languageFile, LANGUAGE_FILES.get(0), db, header);

		File extras = new File(dataFolder + "/extras");

		if (!extras.exists()) extras.mkdirs();

		DATA_FILE = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "data.yml", true);

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
		EnchantmentSolution.getPlugin().reEquipArmor();

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
		DataFile data = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "debug.yml", false);
		YamlConfig debug = data.getConfig();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z Z");
		debug.set("time", format.format(new Date()));
		debug.set("version.bukkit", CrashAPI.getPlugin().getBukkitVersion().getVersion());
		debug.set("version.bukkit_num", CrashAPI.getPlugin().getBukkitVersion().getVersionNumber());
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

		int i = 0;
		for(CrashAdvancementProgress progress: EnchantmentSolution.getAdvancementProgress()) {
			debug.set("data_file.advancement_progress." + i + ".advancement", progress.getAdvancement().name());
			debug.set("data_file.advancement_progress." + i + ".player", progress.getPlayer().getUniqueId());
			debug.set("data_file.advancement_progress." + i + ".criteria", progress.getCriteria());
			debug.set("data_file.advancement_progress." + i + ".current_amount", progress.getCurrentAmount());
			i++;
		}
		i = 0;
		List<WalkerBlock> blocks = WalkerUtils.getBlocks();
		if (blocks != null) for(WalkerBlock block: blocks) {
			Block loc = block.getBlock();
			CustomEnchantment enchantment = RegisterEnchantments.getCustomEnchantment(block.getEnchantment());
			debug.set("data_file.blocks." + i, enchantment.getName() + " " + loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + block.getReplaceType().name() + " " + block.getTick() + " " + block.getDamage().name());
			i++;
		}
		i = 0;
		try {
			for(AnimalMob animal: EnchantmentSolution.getAnimals()) {
				animal.setConfig(data, "data_file.animals.", i);
				i++;
			}
		} catch (NoClassDefFoundError ex) {
			ex.printStackTrace();
		}

		i = 0;
		try {
			for(TableEnchantments table: TableEnchantments.getAllTableEnchantments()) {
				table.setConfig(debug, "data_file.", i);
				i++;
			}
		} catch (NoClassDefFoundError ex) {
			ex.printStackTrace();
		}
		i = 0;
		List<RPGPlayer> players = RPGUtils.getPlayers();
		if (players != null) for(RPGPlayer player: players) {
			debug.set("data_file.rpg." + i + ".player", player.getPlayer().getUniqueId().toString());
			debug.set("data_file.rpg." + i + ".level", player.getLevel());
			debug.set("data_file.rpg." + i + ".experience", player.getExperience().toString());
			List<String> enchants = new ArrayList<String>();
			Iterator<Entry<Enchantment, Integer>> iterator = player.getEnchantmentList().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Enchantment, Integer> entry = iterator.next();
				EnchantmentLevel level = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), entry.getValue());
				enchants.add(level.toString());
			}
			debug.set("data_file.rpg." + i + ".enchants", enchants);
			i++;
		}

		YamlConfigBackup config = CONFIG.getConfig();
		YamlConfigBackup fishing = FISHING.getConfig();
		YamlConfigBackup language = LANGUAGE.getConfig();
		YamlConfigBackup enchantments = ENCHANTMENTS.getConfig();
		YamlConfigBackup advancements = ADVANCEMENTS.getConfig();
		YamlConfigBackup rpg = RPG.getConfig();
		YamlConfigBackup minigame = MINIGAME.getConfig();
		YamlConfigBackup hardMode = HARD_MODE.getConfig();

		for(String s: config.getAllEntryKeys())
			if (config.contains(s)) debug.set("config." + s, config.get(s));

		for(String s: fishing.getAllEntryKeys())
			if (fishing.contains(s)) debug.set("fishing." + s, fishing.get(s));

		for(String s: language.getAllEntryKeys())
			if (language.contains(s)) debug.set("language." + s, language.get(s));

		for(String s: advancements.getAllEntryKeys())
			if (advancements.contains(s)) debug.set("advancements." + s, advancements.get(s));

		for(String s: enchantments.getAllEntryKeys())
			if (enchantments.contains(s)) debug.set("enchantment." + s, enchantments.get(s));

		for(String s: rpg.getAllEntryKeys())
			if (rpg.contains(s)) debug.set("rpg." + s, rpg.get(s));

		for(String s: minigame.getAllEntryKeys())
			if (minigame.contains(s)) debug.set("minigame." + s, rpg.get(s));

		for(String s: hardMode.getAllEntryKeys())
			if (hardMode.contains(s)) debug.set("hard_mode." + s, rpg.get(s));

		debug.saveConfig();
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
