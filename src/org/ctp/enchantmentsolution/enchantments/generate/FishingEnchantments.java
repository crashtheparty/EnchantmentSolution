package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.config.FishingConfiguration;

public class FishingEnchantments extends LootEnchantments{

	private EnchantmentList enchantmentList;
	
	private FishingEnchantments(Player player, ItemStack item, boolean treasure, List<EnchantmentLevel> fishing, double multiEnchant) {
		super(player, item, treasure);
		
		setEnchantmentList(new EnchantmentList(player, item.getType(), true, fishing, multiEnchant));
	}
	
	private FishingEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure) {
		super(player, item, bookshelves, treasure);
	}
	
	public static FishingEnchantments getFishingEnchantments(Player player, ItemStack item, int minBookshelves, boolean treasure) {
		int books = 16;
		if(ConfigUtils.isLevel50()) books = 24;
		int random = (int)(Math.random() * books) + minBookshelves;
		if(random >= books) random = books - 1;
		
		return new FishingEnchantments(player, item, books, treasure);
	}
	
	public static FishingEnchantments getMcMMOFishingEnchantments(Player player, ItemStack item, int tier, boolean treasure) {
		HashMap<String, Double> chanceMap = new HashMap<String, Double>();
		FishingConfiguration config = Configurations.getFishing();
		String location = ConfigUtils.isLevel50() ? "Enchantments_Rarity_50"
				: "Enchantments_Rarity_30";
		
		for (String s : config.getConfig().getConfigurationInfo(location)) {
			String type = s.substring(s.lastIndexOf(".") + 1);
			chanceMap.put(type, getTierChances(tier, type, ConfigUtils.isLevel50()));
		}
		
		double random = Math.random() * 100;
		List<EnchantmentLevel> fishing = null;
		for(Iterator<java.util.Map.Entry<String, Double>> it = chanceMap.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, Double> e = it.next();
			random -= e.getValue();
			if (random <= 0) {
				fishing = getFromConfig(config, e.getKey());
				break;
			}
		}
		
		if(fishing != null) {
			return new FishingEnchantments(player, item, treasure, fishing, config.getDouble(location + ".multiple_enchants_chance"));
		}
		
		return null;
	}

	private static double getTierChances(int tier, String type, boolean fifty){
		FishingConfiguration config = Configurations.getFishing();
		String location = "Enchantment_Drop_Rates_";
		if(fifty) location += "50";
		else location += "30";
		location += ".Tier_" + tier + "." + type;
				
		return config.getDouble(location);
	}
	
	private static List<EnchantmentLevel> getFromConfig(FishingConfiguration config, String type){		
		String location = "Enchantments_Rarity_";
		if(ConfigUtils.isLevel50()) location += "50";
		else location += "30";
		location += "." + type;
				
		List<String> configStrings = config.getStringList(location + ".enchants");
		
		List<EnchantmentLevel> fishing = new ArrayList<EnchantmentLevel>();
		
		for(String str : configStrings) {
			EnchantmentLevel level = new EnchantmentLevel(str);
			if(level != null) {
				fishing.add(level);
			}
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
