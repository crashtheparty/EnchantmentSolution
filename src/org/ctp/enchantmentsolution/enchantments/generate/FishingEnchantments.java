package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.FishingConfiguration;

import com.gmail.nossr50.skills.fishing.FishingManager;
import com.gmail.nossr50.util.player.UserManager;

public class FishingEnchantments extends LootEnchantments {

	private EnchantmentList enchantmentList;

	private FishingEnchantments(Player player, ItemStack item, boolean treasure, List<EnchantmentLevel> fishing,
	double multiEnchant) {
		super(player, item, treasure);

		setEnchantmentList(new EnchantmentList(player, new ItemData(item), true, fishing, multiEnchant));
	}

	private FishingEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure) {
		super(player, item, bookshelves, treasure);
	}

	public static FishingEnchantments getFishingEnchantments(Player player, ItemStack item, int minBookshelves,
	boolean treasure) {
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int random = (int) (Math.random() * books) + minBookshelves;
		if (random >= books) random = books - 1;

		return new FishingEnchantments(player, item, books, treasure);
	}

	public static FishingEnchantments getFishingEnchantments(Player player, ItemStack item) {
		HashMap<String, Double> chanceMap = new HashMap<String, Double>();
		FishingConfiguration config = Configurations.getFishing();
		String location = ConfigString.LEVEL_FIFTY.getBoolean() ? "Enchantments_Rarity_50" : "Enchantments_Rarity_30";
		FishingManager manager = UserManager.getPlayer(player).getFishingManager();
		for(String s: config.getConfig().getConfigurationInfo(location)) {
			String type = s.substring(s.lastIndexOf(".") + 1);
			double d = getTierChances(manager.getLootTier(), type, ConfigString.LEVEL_FIFTY.getBoolean());
			chanceMap.put(type, d);
		}

		double random = Math.random() * 100;
		List<EnchantmentLevel> fishing = null;
		String tier = null;
		for(Iterator<java.util.Map.Entry<String, Double>> it = chanceMap.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, Double> e = it.next();
			random -= e.getValue();
			if (random <= 0) {
				fishing = getFromConfig(config, e.getKey());
				tier = e.getKey();
				break;
			}
		}

		if (fishing != null) return new FishingEnchantments(player, item, ConfigString.LOOT_TREASURE.getBoolean("fishing.treasure"), fishing, config.getDouble(location + "." + tier + ".multiple_enchants_chance"));

		return null;
	}

	private static double getTierChances(int tier, String type, boolean fifty) {
		FishingConfiguration config = Configurations.getFishing();
		String location = "Enchantment_Drop_Rates_";
		if (fifty) location += "50";
		else
			location += "30";
		location += ".Tier_" + tier + "." + type;

		return config.getDouble(location);
	}

	private static List<EnchantmentLevel> getFromConfig(FishingConfiguration config, String type) {
		String location = "Enchantments_Rarity_";
		if (ConfigString.LEVEL_FIFTY.getBoolean()) location += "50";
		else
			location += "30";
		location += "." + type;

		List<String> configStrings = config.getStringList(location + ".enchants");

		List<EnchantmentLevel> fishing = new ArrayList<EnchantmentLevel>();

		for(String str: configStrings) {
			EnchantmentLevel level = new EnchantmentLevel(str);
			if (level != null) fishing.add(level);
		}

		return fishing;
	}

	public EnchantmentList getEnchantmentList() {
		return enchantmentList;
	}

	public void setEnchantmentList(EnchantmentList enchantmentList) {
		this.enchantmentList = enchantmentList;
	}

}
