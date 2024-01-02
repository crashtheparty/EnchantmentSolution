package org.ctp.enchantmentsolution.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.config.*;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.resources.advancements.CrashAdvancementProgress;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.Seed;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.config.*;
import org.ctp.enchantmentsolution.utils.files.ESLanguageFile;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class Configurations implements CrashConfigurations {

	private static boolean INITIALIZING = true, FIRST_SAVE = true;
	private final static Configurations CONFIGURATIONS = new Configurations();
	private MainConfiguration CONFIG;
	private EnchantingTableConfiguration ENCHANTING_TABLE;
	private AnvilConfiguration ANVIL;
	private GrindstoneConfiguration GRINDSTONE;
	private FishingConfiguration FISHING;
	private LanguageConfiguration LANGUAGE;
	private EnchantmentsConfiguration ENCHANTMENTS;
	private AdvancementsConfiguration ADVANCEMENTS;
	private RPGConfiguration RPG;
	private MinigameConfiguration MINIGAME;
	private HardModeConfiguration HARD_MODE;
	private LootsConfiguration LOOTS;
	private LootTypesConfiguration LOOT_TYPES;
	private List<Configuration> MAIN_CONFIGURATIONS;

	private List<ESLanguageFile> LANGUAGE_FILES = new ArrayList<ESLanguageFile>();
	private DataFile DATA_FILE;
	private DataFile DEBUG_FILE;
	private Map<CustomEnchantment, EnchantmentConfiguration> ENCHANTMENT_CONFIGURATIONS;

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
		ENCHANTING_TABLE = new EnchantingTableConfiguration(dataFolder, db, header);
		ANVIL = new AnvilConfiguration(dataFolder, db, header);
		GRINDSTONE = new GrindstoneConfiguration(dataFolder, db, header);
		FISHING = new FishingConfiguration(dataFolder, db, header);
		ENCHANTMENTS = new EnchantmentsConfiguration(dataFolder, db, header);
		ADVANCEMENTS = new AdvancementsConfiguration(dataFolder, db, header);
		RPG = new RPGConfiguration(dataFolder, db, header);
		MINIGAME = new MinigameConfiguration(dataFolder, db, header);
		HARD_MODE = new HardModeConfiguration(dataFolder, db, header);
		LOOTS = new LootsConfiguration(dataFolder, db, header);
		LOOT_TYPES = new LootTypesConfiguration(dataFolder, db, header);
		ENCHANTMENT_CONFIGURATIONS = new HashMap<CustomEnchantment, EnchantmentConfiguration>();

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

		MAIN_CONFIGURATIONS = Arrays.asList(CONFIG, LANGUAGE, ENCHANTING_TABLE, ANVIL, GRINDSTONE, FISHING, ENCHANTMENTS, ADVANCEMENTS, RPG, MINIGAME, HARD_MODE, LOOTS, LOOT_TYPES);
		DATA_FILE = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "data.yml", true, true);
		DEBUG_FILE = new DataFile(EnchantmentSolution.getPlugin(), dataFolder, "debug.yml", true, false);

		INITIALIZING = false;
		save();
	}

	public void revert() {
		for(Configuration c: MAIN_CONFIGURATIONS)
			c.revert();

		for(CustomEnchantment enchantment: RegisterEnchantments.getRegisteredEnchantments())
			if (ENCHANTMENT_CONFIGURATIONS.containsKey(enchantment)) {
				EnchantmentConfiguration config = ENCHANTMENT_CONFIGURATIONS.get(enchantment);
				config.revert();
			}
	}

	public void revert(Configuration config, int backup) {
		config.revert(backup);
	}

	@Override
	public void save() {
		boolean comments = ConfigString.USE_COMMENTS.getBoolean();
		for(Configuration c: MAIN_CONFIGURATIONS) {
			c.setComments(comments);
			c.save();
		}

		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();
		BackupDB db = EnchantmentSolution.getPlugin().getDb();
		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty", "Using Level " + (ConfigString.LEVEL_FIFTY.getBoolean() ? "Fifty" : "Thirty") + " System!", "Make sure to edit that system under ", "advanced.enchantability", "if you are modifying those values." };

		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments()) {
			if (ENCHANTMENT_CONFIGURATIONS.containsKey(enchantment)) {
				EnchantmentConfiguration config = ENCHANTMENT_CONFIGURATIONS.get(enchantment);
				config.setComments(comments);
				config.save();
				continue;
			}
			File folder = new File(dataFolder + "/enchantments/" + (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper ? ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin().getName().toLowerCase(Locale.ROOT) : enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper ? "enchantmentsolution" : "vanilla"));
			try {
				if (!folder.exists()) folder.mkdirs();
			} catch (final Exception e) {
				e.printStackTrace();
			}
			EnchantmentConfiguration config = new EnchantmentConfiguration(enchantment, new File(folder + "/" + enchantment.getName() + ".yml"), db, header);
			config.setComments(comments);
			config.save();
			ENCHANTMENT_CONFIGURATIONS.put(enchantment, config);
		}

//		if (ConfigString.RESET_ON_RELOAD.getBoolean()) TableEnchantments.removeAllTableEnchantments();
		RegisterEnchantments.setEnchantments();
		EnchantmentSolution.getPlugin().reEquipItems();

		if (FIRST_SAVE) Chatable.get().sendInfo("Loading Loot Types...");
		Loots.createDefaults();
		Loots.createCustomLoot();
		if (FIRST_SAVE) Chatable.get().sendInfo("Loot Types Loaded!");

		if (!FIRST_SAVE) {
			EnchantmentSolution.getPlugin().setVersionCheck(ConfigString.LATEST_VERSION.getBoolean(), ConfigString.EXPERIMENTAL_VERSION.getBoolean());
			generateDataPack();
			EnchantmentSolution.getPlugin().getWiki().resetRunner();
		}
		Minigame.reset();
		FIRST_SAVE = false;
	}

	public static void generateDataPack() {
		boolean advancements = AdvancementUtils.createAdvancements();

		if (advancements) {
			Chatable.get().sendInfo("Reloading advancements...");
			Bukkit.reloadData();
			Chatable.get().sendInfo("Reloaded!");
		}
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
		for(EnchantmentWrapper enchantment: EnchantmentSolution.getLassoEnchantments())
			try {
				for(LassoMob lasso: EnchantmentSolution.getLassoMobs(enchantment)) {
					lasso.setConfig(data, "data_file." + lasso.getLocation(i), i);
					i++;
				}
			} catch (NoClassDefFoundError ex) {
				ex.printStackTrace();
			}

		i = 0;
		try {
			for(ESPlayer player: EnchantmentSolution.getAllESPlayers(false)) {
				debug.set("data_file.es_player." + i + ".uuid", player.getPlayer().getUniqueId());
				debug.set("data_file.es_player." + i + ".can_see_enchantments", player.canSeeEnchantments());
				debug.set("data_file.es_player." + i + ".will_see_enchantments", player.willSeeEnchantments());
				debug.set("data_file.es_player." + i + ".current_echo_shards", player.getCurrentEchoShards());
				debug.set("data_file.es_player." + i + ".next_echo_shards", player.getNextEchoShards());
				Iterator<Seed> iter = player.getEnchantmentSeeds().iterator();
				int j = 0;
				while (iter.hasNext()) {
					Seed seed = iter.next();
					debug.set("data_file.es_player." + i + ".seeds." + j + ".itemdata", seed.getData().toString());
					debug.set("data_file.es_player." + i + ".seeds." + j + ".seed", seed.getSeed());
					j++;
				}
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
			Iterator<Entry<EnchantmentWrapper, Integer>> iterator = player.getEnchantmentList().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<EnchantmentWrapper, Integer> entry = iterator.next();
				EnchantmentLevel level = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), entry.getValue());
				enchants.add(level.toString());
			}
			debug.set("data_file.rpg." + i + ".enchants", enchants);
			i++;
		}

		for(Configuration config: MAIN_CONFIGURATIONS) {
			YamlConfigBackup c = config.getConfig();
			@SuppressWarnings("unchecked")
			String s = ((Class<Configuration>) config.getClass()).getName().toLowerCase(Locale.ROOT);
			for(String key: c.getAllEntryKeys())
				if (c.contains(key)) debug.set(s + "." + key, c.get(key));
		}

		debug.saveConfig();
	}

	public void reload() {
		for(Configuration c: MAIN_CONFIGURATIONS)
			c.reload();

		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (ENCHANTMENT_CONFIGURATIONS.containsKey(enchantment)) ENCHANTMENT_CONFIGURATIONS.get(enchantment).reload();

		save();
	}

	public MainConfiguration getConfig() {
		return CONFIG;
	}

	public EnchantingTableConfiguration getEnchantingTable() {
		return ENCHANTING_TABLE;
	}

	public AnvilConfiguration getAnvil() {
		return ANVIL;
	}

	public GrindstoneConfiguration getGrindstone() {
		return GRINDSTONE;
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

	public LootsConfiguration getLoots() {
		return LOOTS;
	}

	public LootTypesConfiguration getLootTypes() {
		return LOOT_TYPES;
	}

	public static boolean isInitializing() {
		return INITIALIZING;
	}

	public EnchantmentConfiguration getEnchantmentConfig(CustomEnchantment enchantment) {
		if (ENCHANTMENT_CONFIGURATIONS.containsKey(enchantment)) return ENCHANTMENT_CONFIGURATIONS.get(enchantment);

		// If there's an issue getting the file, should create a new one
		Chatable.get().sendWarning("WARNING: Enchantment " + enchantment.getName() + " didn't load correctly. Check the code.");
		File dataFolder = EnchantmentSolution.getPlugin().getDataFolder();
		BackupDB db = EnchantmentSolution.getPlugin().getDb();
		String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty", "Using Level " + (ConfigString.LEVEL_FIFTY.getBoolean() ? "Fifty" : "Thirty") + " System!", "Make sure to edit that system under ", "advanced.enchantability", "if you are modifying those values." };

		File folder = new File(dataFolder + "/enchantments/" + (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper ? ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin().getName().toLowerCase(Locale.ROOT) : enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper ? "enchantmentsolution" : "vanilla"));
		try {
			if (!folder.exists()) folder.mkdirs();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		EnchantmentConfiguration config = new EnchantmentConfiguration(enchantment, new File(folder + "/" + enchantment.getName() + ".yml"), db, header);
		config.setComments(ConfigString.USE_COMMENTS.getBoolean());
		config.save();
		ENCHANTMENT_CONFIGURATIONS.put(enchantment, config);
		return config;
	}

	public List<Configuration> getMainConfigurations() {
		return MAIN_CONFIGURATIONS;
	}

	public Map<CustomEnchantment, EnchantmentConfiguration> getEnchantmentConfigurations() {
		return ENCHANTMENT_CONFIGURATIONS;
	}
}
