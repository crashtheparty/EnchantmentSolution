package org.ctp.enchantmentsolution.enchantments.mcmmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.config.SimpleConfig;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class Fishing {
	
	public static List<String> enchantmentDefaults(String type, boolean fifty){
		List<String> common = new ArrayList<String>();
		
		switch(type.toUpperCase()) {
		case "COMMON":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 1).toString());
			}
			break;
		case "UNCOMMON":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 2).toString());	
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
			}
			break;
		case "RARE":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BEHEADING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
			}
			break;
		case "EPIC":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FROST_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TANK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BEHEADING), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.CHANNELING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TANK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 2).toString());
			}
			break;
		case "LEGENDARY":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FROST_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.CHANNELING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.MENDING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TANK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TELEPATHY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BEHEADING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SACRIFICE), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_INFINITE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.CHANNELING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.MENDING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BEHEADING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TANK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SACRIFICE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TELEPATHY), 1).toString());
			}
			break;
		case "ANCIENT":
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DIG_SPEED), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DURABILITY), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.RIPTIDE), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LOYALTY), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.THORNS), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ALL), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_DAMAGE), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.IMPALING), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.KNOCKBACK), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LUCK), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.LURE), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FIRE_ASPECT), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.FROST_WALKER), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.OXYGEN), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.CHANNELING), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(Enchantment.MENDING), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.ANGLER), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.KNOCKUP), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.WARP), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.LIFE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TANK), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.TELEPATHY), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.BEHEADING), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getAddedCustomEnchantment(DefaultEnchantments.SACRIFICE), 2).toString());
		}
		
		return common;
	}
	
	public static double getTierChances(int tier, String type, boolean fifty){
		SimpleConfig config = ConfigFiles.getFishingConfig();
		String location = "Enchantment_Drop_Rates_";
		if(fifty) location += "50";
		else location += "30";
		location += ".Tier_" + tier + "." + type;
				
		return config.getDouble(location);
	}
	
	public static List<EnchantmentLevel> getEnchantsFromConfig(ItemStack item, String type, boolean fifty){
		SimpleConfig config = ConfigFiles.getFishingConfig();
		
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
			if(enchantment.getEnchant().canEnchantItem(item.getType()) && enchantment.getEnchant().isEnabled()){
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
						if(enchantment.getEnchant().conflictsWith(enchant.getEnchant())){
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
		int maxEnchants = Enchantments.getMaxEnchantments();
		if(maxEnchants > 0) {
			for(int i = enchants.size() - 1; i > maxEnchants; i--) {
				enchants.remove(i);
			}
		}
		
		return enchants;
	}

}
