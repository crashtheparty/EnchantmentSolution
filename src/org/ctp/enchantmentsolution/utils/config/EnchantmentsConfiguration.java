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
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class EnchantmentsConfiguration extends Configuration {

	public EnchantmentsConfiguration(File dataFolder) {
		super(new File(dataFolder + "/enchantments.yml"));

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Loading enchantment configuration...");

		YamlConfigBackup config = getConfig();

		config.getFromConfig();

		config.addDefault("extra_enchantables", Arrays.asList("COMPASS"), new String[] { "Additional items that should be allowed to be enchanted in the enchanting table.", "Will not add any enchantments for these items." });
		config.addDefault("advanced_options.use", false, new String[] { "Use the advanced customization options for the plugin", "** Only modify if you understand what you're doing! **" });
		config.addDefault("advanced_options.enchantability_decay", false, new String[] { "Adds the 1.14-1.14.2 feature of additional enchantments getting lower levels" });
		config.addDefault("advanced_options.starting_level", true, new String[] { "Enchantments will not be available unless the enchanting level is the set value or above" });
		config.addDefault("advanced_options.lapis_modifiers.use", true, new String[] { "Enchanting with higher amounts of lapis give higher enchantability" });
		config.addDefault("advanced_options.lapis_modifiers.constant", -1, new String[] { "Extra enchantability: (lapis + constant) * multiplier" });
		config.addDefault("advanced_options.lapis_modifiers.multiplier", 2);
		config.addDefault("advanced_options.multi_enchant_divisor", 75.0D, new String[] { "Chance of multiple enchantments on one item. Lower value = more enchantments." });
		config.addDefault("advanced_options.use_permissions", false, new String[] { "Use the permission system per player for all enchantments.", "Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"", "enchant_name: Enchantment name as used below", "type: either table (for enchanting items) or anvil (for combining items)", "int: the enchantment level", "Override permission: enchantmentsolution.permissions.ignore" });

		config.writeDefaults();

		if (EnchantmentSolution.getPlugin().isInitializing()) ChatUtils.sendInfo("Enchantment configuration initialized!");
	}

	@Override
	public void save() {
		super.save();
		setEnchantmentInformation();
		super.save();
	}

	public void setEnchantmentInformation() {
		YamlConfigBackup config = getConfig();
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments()) {
			String namespace = "default_enchantments";
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if (plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")" + " does not have a JavaPlugin set. Refusing to set config defaults.");
					continue;
				}
				namespace = plugin.getName().toLowerCase();
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) namespace = "custom_enchantments";
			String start = namespace + "." + enchant.getName().toLowerCase();
			config.addDefault(start + ".enabled", true);
			config.addDefault(start + ".enchantment_locations", enchant.getDefaultEnchantmentLocations());
			config.addDefault(start + ".enchantment_item_types", ItemType.itemTypesToStrings(enchant.getDefaultEnchantmentItemTypes()));
			config.addDefault(start + ".anvil_item_types", ItemType.itemTypesToStrings(enchant.getDefaultAnvilItemTypes()));
			start += ".advanced";
			config.addDefault(start + ".weight", enchant.getDefaultWeightName());
			config.addEnum(start + ".weight", Arrays.asList(Weight.LEGENDARY.getName(), Weight.EPIC.getName(), Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
			config.addDefault(start + ".enchantability_constant", enchant.getDefaultConstant());
			config.addDefault(start + ".enchantability_modifier", enchant.getDefaultModifier());
			config.addDefault(start + ".enchantability_start_level", enchant.getDefaultStartLevel());
			config.addDefault(start + ".enchantability_max_level", enchant.getDefaultMaxLevel());
			config.addDefault(start + ".conflicting_enchantments", enchant.conflictingDefaultList());
			config.addEnum(start + ".conflicting_enchantments", RegisterEnchantments.getEnchantmentNames());
			for(int i = 1; i <= enchant.getMaxLevel(); i++) {
				PermissionUtils.removePermissions(new EnchantmentLevel(enchant, i));
				config.addDefault(start + ".permissions.table.level" + i, false);
				config.addDefault(start + ".permissions.anvil.level" + i, false);
				PermissionUtils.addPermissions(new EnchantmentLevel(enchant, i));
			}
		}
	}

	public void updateExternal(JavaPlugin plugin) {
		YamlConfigBackup config = getConfig();
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments())
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) if (plugin.equals(((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin())) {
				String namespace = plugin.getName().toLowerCase() + "." + enchant.getName().toLowerCase();
				config.addDefault(namespace + ".enabled", true);
				config.addDefault(namespace + ".enchantment_locations", enchant.getDefaultEnchantmentLocations());
				config.addDefault(namespace + ".enchantment_item_types", ItemType.itemTypesToStrings(enchant.getDefaultEnchantmentItemTypes()));
				config.addDefault(namespace + ".anvil_item_types", ItemType.itemTypesToStrings(enchant.getDefaultAnvilItemTypes()));
				namespace += ".advanced";
				config.addDefault(namespace + ".weight", enchant.getDefaultWeightName());
				config.addEnum(namespace + ".weight", Arrays.asList(Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
				config.addDefault(namespace + ".enchantability_constant", enchant.getDefaultConstant());
				config.addDefault(namespace + ".enchantability_modifier", enchant.getDefaultModifier());
				config.addDefault(namespace + ".enchantability_start_level", enchant.getDefaultStartLevel());
				config.addDefault(namespace + ".enchantability_max_level", enchant.getDefaultMaxLevel());
				config.addDefault(namespace + ".conflicting_enchantments", enchant.conflictingDefaultList());
				config.addEnum(namespace + ".conflicting_enchantments", RegisterEnchantments.getEnchantmentNames());
			}

		config.writeDefaults();
	}

	@Override
	public void migrateVersion() {
		if (Configurations.getConfig().getString("enchanting_table.enchanting_type") != null) {
			getConfig().set("advanced_options.use", Configurations.getConfig().getString("enchanting_table.enchanting_type").contains("custom"));
			Configurations.getConfig().getConfig().removeKey("enchanting_table.enchanting_type");
			Configurations.getConfig().save();
		}
		if (Configurations.getConfig().getConfig().getBooleanValue("enchantability_decay") != null) {
			getConfig().set("advanced_options.decay", Configurations.getConfig().getBoolean("enchantability_decay"));
			Configurations.getConfig().getConfig().removeKey("enchantability_decay");
			Configurations.getConfig().save();
		}
	}

}
