package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class EnchantmentsConfiguration extends Configuration {

	public EnchantmentsConfiguration(File dataFolder) {
		super(new File(dataFolder + "/enchantments.yml"));

		migrateVersion();
		if (getConfig() != null) {
			getConfig().writeDefaults();
		}
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Loading enchantment configuration...");
		}

		YamlConfigBackup config = getConfig();

		if (!config.getBoolean("enchanting_table.reset_enchantments_advanced")) {
			config.getFromConfig();
		} else {
			config.resetInfo();
		}

		config.addDefault("use_starting_level", true, new String[] {
		"Enchantments will not be available unless the enchanting level is the set value or above" });
		config.addDefault("use_lapis_modifier", true,
		new String[] { "Enchanting with higher amounts of lapis give higher enchantability" });
		config.addDefault("lapis_modifiers.constant", -1,
		new String[] { "Extra enchantability: (lapis + constant) * modifier" });
		config.addDefault("lapis_modifiers.modifier", 2);
		config.addDefault("multi_enchant_divisor", 75.0D,
		new String[] { "Chance of multiple enchantments on one item. Lower value = more enchantments." });
		config.addDefault("use_permissions", false,
		new String[] { "Use the permission system per player for all enchantments.",
		"Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"",
		"enchant_name: Enchantment name as used below",
		"type: either table (for enchanting items) or anvil (for combining items)",
		"int: the enchantment level", "Override permission: enchantmentsolution.permissions.ignore" });
		config.addDefault("advanced_settings", false, new String[] {
		"Shows the Weight, Enchantability Values, Conflicting Enchantments, Permissions, etc." });

		config.writeDefaults();

		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			String namespace = "default_enchantments";
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING,
					"Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
					+ " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				namespace = plugin.getName().toLowerCase();
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				namespace = "custom_enchantments";
			}
			String start = namespace + "." + enchant.getName();
			config.addDefault(start + ".enabled", true);
			config.addDefault(start + ".treasure", enchant.isTreasure());
			if (config.getBoolean("advanced_settings")) {
				config.addDefault(start + ".weight", enchant.getDefaultWeightName());
				config.addEnum(start + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(),
				Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				config.addDefault(start + ".enchantability_constant", enchant.getDefaultConstant());
				config.addDefault(start + ".enchantability_modifier", enchant.getDefaultModifier());
				config.addDefault(start + ".enchantability_start_level", enchant.getDefaultStartLevel());
				config.addDefault(start + ".enchantability_max_level", enchant.getDefaultMaxLevel());
				config.addDefault(start + ".conflicting_enchantments", enchant.conflictingDefaultList());
				config.addEnum(start + ".conflicting_enchantments", RegisterEnchantments.getEnchantmentNames());
				config.addDefault(start + ".disabled_items", enchant.getDisabledItemsStrings());
				config.addEnum(start + ".disabled_items", ESArrays.getRepairMaterialsStrings());
			}
		}

		if (EnchantmentSolution.getPlugin().isInitializing()) {
			ChatUtils.sendInfo("Enchantment configuration initialized!");
		}
	}

	public void updateExternal(JavaPlugin plugin) {
		YamlConfigBackup config = getConfig();
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				if (plugin.equals(((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin())) {
					String namespace = plugin.getName().toLowerCase() + "." + enchant.getName().toLowerCase();
					config.addDefault(namespace + "." + enchant.getName() + ".enabled", true);
					config.addDefault(namespace + "." + enchant.getName() + ".treasure", enchant.isTreasure());
					if (config.getBoolean("advanced_settings")) {
						config.addDefault(namespace + ".weight", enchant.getDefaultWeightName());
						config.addEnum(namespace + ".weight",
						Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(),
						Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
						config.addDefault(namespace + ".enchantability_constant", enchant.getDefaultConstant());
						config.addDefault(namespace + ".enchantability_modifier", enchant.getDefaultModifier());
						config.addDefault(namespace + ".enchantability_start_level", enchant.getDefaultStartLevel());
						config.addDefault(namespace + ".enchantability_max_level", enchant.getDefaultMaxLevel());
						config.addDefault(namespace + ".conflicting_enchantments", enchant.conflictingDefaultList());
						config.addEnum(namespace + ".conflicting_enchantments",
						RegisterEnchantments.getEnchantmentNames());
						config.addDefault(namespace + ".disabled_items", enchant.getDisabledItemsStrings());
						config.addEnum(namespace + ".disabled_items", ESArrays.getRepairMaterialsStrings());
					}
				}
			}
		}
	}

	@Override
	public void migrateVersion() {
	}

}
