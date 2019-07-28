package org.ctp.enchantmentsolution.enchantments.mcmmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;

public class Fishing {
	
	public static double getTierChances(int tier, String type, boolean fifty){
		YamlConfig config = EnchantmentSolution.getPlugin().getConfigFiles().getFishingConfig();
		String location = "Enchantment_Drop_Rates_";
		if(fifty) location += "50";
		else location += "30";
		location += ".Tier_" + tier + "." + type;
				
		return config.getDouble(location);
	}
	
	public static List<EnchantmentLevel> getEnchantsFromConfig(Player player, ItemStack item, String type, boolean fifty){
		YamlConfig config = EnchantmentSolution.getPlugin().getConfigFiles().getFishingConfig();
		
		String location = "Enchantments_Rarity_";
		if(fifty) location += "50";
		else location += "30";
		location += "." + type;
		
		double chance = config.getDouble(location + ".multiple_enchants_chance");
		
		List<String> configStrings = config.getStringList(location + ".enchants");
		
		List<FishingEnchanted> fishing = new ArrayList<FishingEnchanted>();
		
		for(String str : configStrings) {
			fishing.add(new FishingEnchanted(str));
		}
		
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		List<FishingEnchanted> fishingWeight = new ArrayList<FishingEnchanted>();
		int totalWeight = 0;
		for(FishingEnchanted enchantment : fishing){
			CustomEnchantment e = enchantment.getEnchant();
			if(e.canAnvil(player, enchantment.getLevel()) && e.canEnchantItem(item.getType()) && e.isEnabled()){
				totalWeight += enchantment.getEnchant().getWeight();
				fishingWeight.add(enchantment);
			}
		}

		int getWeight = (int)(Math.random() * totalWeight);
		for(FishingEnchanted enchantment : fishingWeight){
			getWeight -= enchantment.getEnchant().getWeight();
			if(getWeight <= 0){
				enchants.add(new EnchantmentLevel(enchantment.getEnchant(), enchantment.getLevel()));
				break;
			}
		}
		while(chance > Math.random()){
			totalWeight = 0;
			fishingWeight = new ArrayList<FishingEnchanted>();
			for(FishingEnchanted enchantment : fishing){
				if(enchantment.getEnchant().canEnchantItem(item.getType()) && enchantment.getEnchant().isEnabled()){
					boolean canEnchant = true;
					for(EnchantmentLevel enchant : enchants){
						if(CustomEnchantment.conflictsWith(enchantment.getEnchant(), enchant.getEnchant())){
							canEnchant = false;
						}
					}
					if(canEnchant){
						totalWeight += enchantment.getEnchant().getWeight();
						fishingWeight.add(enchantment);
					}
				}
			}
			getWeight = (int)(Math.random() * totalWeight);
			for(FishingEnchanted enchantment : fishingWeight){
				getWeight -= enchantment.getEnchant().getWeight();
				if(getWeight <= 0){
					enchants.add(new EnchantmentLevel(enchantment.getEnchant(), enchantment.getLevel()));
					break;
				}
			}
			chance /= 2;
		}
		int maxEnchants = ConfigUtils.getMaxEnchantments();
		if(maxEnchants > 0) {
			for(int i = enchants.size() - 1; i > maxEnchants; i--) {
				enchants.remove(i);
			}
		}
		
		for(int i = enchants.size() - 1; i >= 0; i--) {
			EnchantmentLevel enchant = enchants.get(i);
			if(!enchant.getEnchant().canAnvil(player, enchant.getLevel())) {
				int level = enchant.getEnchant().getAnvilLevel(player, enchant.getLevel());
				if(level > 0) {
					enchants.get(i).setLevel(level);
				} else {
					enchants.remove(i);
				}
			}
		}
		
		return enchants;
	}

}
