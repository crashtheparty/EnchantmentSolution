package org.ctp.enchantmentsolution.enchantments.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.logging.log4j.util.Strings;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.item.VanillaItemType;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public class ConfigEnchantmentRegister {

	private static ConfigEnchantmentRegister REGISTER;
	private static List<ConfigEnchantmentWrapper> WRAPPERS = new ArrayList<ConfigEnchantmentWrapper>();
	private static List<ConfigEnchantment> ENCHANTMENTS = new ArrayList<ConfigEnchantment>();
	private static List<String> OLD_NAMES = new ArrayList<String>();

	public static ConfigEnchantmentRegister createRegister() {
		if (REGISTER == null) REGISTER = new ConfigEnchantmentRegister();
		return REGISTER;
	}

	public static List<ConfigEnchantmentWrapper> getEnchantments() {
		return WRAPPERS;
	}

	public static ConfigEnchantmentWrapper getEnchantment(String name) {
		for(ConfigEnchantmentWrapper w: WRAPPERS)
			if (w.getName().equals(name)) return w;
		return null;
	}

	public static List<ConfigEnchantment> getCustomEnchantments() {
		return ENCHANTMENTS;
	}

	public static ConfigEnchantment getCustomEnchantment(String name) {
		for(ConfigEnchantment e: ENCHANTMENTS)
			if (e.getName().equals(name)) return e;
		return null;
	}

	public static ConfigEnchantmentWrapper getCustomEnchantmentByKey(EnchantmentWrapper enchantment) {
		for(ConfigEnchantmentWrapper w: WRAPPERS)
			if (w == enchantment) return w;
		return null;
	}

	public void updateRegister() {
		OLD_NAMES = new ArrayList<String>();
		for(ConfigEnchantmentWrapper enchantment: WRAPPERS)
			OLD_NAMES.add(enchantment.getName());
		WRAPPERS = new ArrayList<ConfigEnchantmentWrapper>();
		ENCHANTMENTS = new ArrayList<ConfigEnchantment>();
		File registry = new File(EnchantmentSolution.getPlugin().getDataFolder() + "/enchantments/config/");
		try {
			if (!registry.exists()) registry.mkdirs();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		String[] contents = registry.list();
		List<File> files = new ArrayList<File>();
		List<String> errors = new ArrayList<String>();
		start: for(String content: contents)
			if (content.endsWith(".yml")) {
				String s = content.replace(".yml", "");
				for(char c: s.toCharArray())
					if (c != '_' && (!Character.isLetter(c) || Character.isUpperCase(c))) {
						errors.add(ConfigEnchantmentError.BAD_NAME.getMessage().replace("%s%", content));
						continue start;
					}
				files.add(new File(registry + content));
			}

		for(File f: files)
			setEnchantment(f, errors);

		for(String s: errors)
			Chatable.get().sendWarning(s);

		for(ConfigEnchantment ench: ENCHANTMENTS)
			RegisterEnchantments.addConfigEnchantment(ench);

		for(String s: OLD_NAMES) {
			boolean exists = false;
			for(ConfigEnchantmentWrapper enchantment: WRAPPERS)
				if (enchantment.getName().equals(s)) exists = true;
			if (!exists) removeConfigEnchantment(s);
		}
	}

	public void setEnchantment(File file, List<String> errors) {
		String fileName = file.getName();
		String enchName = fileName.substring(0, fileName.indexOf('.'));
		ConfigEnchantmentWrapper wrapper = new ConfigEnchantmentWrapper(enchName, enchName.toUpperCase(Locale.ROOT));
		YamlConfig config = new YamlConfig(file, null);
		config.getFromConfig();
		try {
			String displayNameUS = config.getString("defaults.display_names.en_us");
			String descriptionUS = config.getString("defaults.descriptions.en_us");
			int[] mewl = new int[8];
			mewl[0] = config.getInt("defaults.level_fifty.constant");
			mewl[1] = config.getInt("defaults.level_thirty.constant");
			mewl[2] = config.getInt("defaults.level_fifty.modifier");
			mewl[3] = config.getInt("defaults.level_thirty.modifier");
			mewl[4] = config.getInt("defaults.level_fifty.minimum_level");
			mewl[5] = config.getInt("defaults.level_thirty.minimum_level");
			mewl[6] = config.getInt("defaults.level_fifty.max_level");
			mewl[7] = config.getInt("defaults.level_thirty.max_level");
			Weight weight = Weight.getWeight(config.getString("defaults.weight"));
			EnchantmentLocation[] locations = getEnchantmentLocations(config);
			List<ItemType> enchantmentTypes = getTypes(config, "defaults.enchantment_item_types");
			List<ItemType> anvilTypes = getTypes(config, "defaults.anvil_item_types");
			List<String> conflictingEnchantments = config.getStringList("defaults.conflicting_enchantments");

			ConfigEnchantment enchantment = new ConfigEnchantment(wrapper, enchantmentTypes, anvilTypes, conflictingEnchantments, locations, displayNameUS, mewl, weight, descriptionUS);
			WRAPPERS.add(wrapper);
			ENCHANTMENTS.add(enchantment);
		} catch (Exception ex) {
			List<String> stackTrace = new ArrayList<String>();
			for(StackTraceElement ste: ex.getStackTrace())
				stackTrace.add(ste.getClassName() + " (" + ste.getFileName() + ") with method " + ste.getMethodName() + " at line number " + ste.getLineNumber());

			errors.add(ConfigEnchantmentError.EXCEPTION.getMessage().replace("%name%", enchName).replace("%exception%", ex.getLocalizedMessage()).replace("%exception_list%", Strings.join(stackTrace, '\n')));
		}

	}

	private EnchantmentLocation[] getEnchantmentLocations(YamlConfig config) {
		List<EnchantmentLocation> locations = new ArrayList<EnchantmentLocation>();
		if (config.getBoolean("defaults.locations.enchanting_table")) locations.add(EnchantmentLocation.TABLE);
		if (config.getBoolean("defaults.locations.chests")) locations.add(EnchantmentLocation.CHEST_LOOT);
		if (config.getBoolean("defaults.locations.mobs")) locations.add(EnchantmentLocation.MOB_LOOT);
		if (config.getBoolean("defaults.locations.fishing")) locations.add(EnchantmentLocation.FISHING_LOOT);
		if (config.getBoolean("defaults.locations.villager")) locations.add(EnchantmentLocation.VILLAGER);
		if (config.getBoolean("defaults.locations.piglin")) locations.add(EnchantmentLocation.PIGLIN);
		if (config.getBoolean("defaults.locations.end_city")) locations.add(EnchantmentLocation.END_CITY);
		if (config.getBoolean("defaults.locations.deep_dark")) locations.add(EnchantmentLocation.DEEP_DARK);
		EnchantmentLocation[] l = new EnchantmentLocation[locations.size()];
		for (int i = 0; i < l.length; i++)
			l[i] = locations.get(i);
		return l;
	}

	private List<ItemType> getTypes(YamlConfig config, String path) {
		List<String> itemTypes = config.getStringList(path);
		List<ItemType> types = new ArrayList<ItemType>();
		if (itemTypes != null) for(String s: itemTypes) {
			ItemType type = null;
			if (s.contains(":")) type = ItemType.getCustomType(VanillaItemType.get(s.toUpperCase(Locale.ROOT)), s.toUpperCase(Locale.ROOT));
			else
				type = ItemType.getItemType(s.toUpperCase(Locale.ROOT));
			if (type != null) types.add(type);
		}
		return types;
	}

	private void removeConfigEnchantment(String s) {
		NamespacedKey key = new NamespacedKey(EnchantmentSolution.getPlugin(), s);
		try {
			Field keyField = Enchantment.class.getDeclaredField("byKey");

			keyField.setAccessible(true);
			@SuppressWarnings("unchecked")
			HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

			if (byKey.containsKey(key)) byKey.remove(key);
			Field nameField = Enchantment.class.getDeclaredField("byName");

			nameField.setAccessible(true);
			@SuppressWarnings("unchecked")
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

			if (byName.containsKey(s)) byName.remove(s);
		} catch (Exception ignored) {}
	}

	public enum ConfigEnchantmentError {
		BAD_NAME("Config Enchantment Names may only contain lower case letters and underscores! Found name was %s%."),
		EXCEPTION("Exception with adding Enchantment %name%: %exception% \n%exception_list%");

		private String msg;

		ConfigEnchantmentError(String msg) {
			this.msg = msg;
		}

		public String getMessage() {
			return msg;
		}
	}
}
