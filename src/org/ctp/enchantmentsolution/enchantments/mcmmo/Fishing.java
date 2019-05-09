package org.ctp.enchantmentsolution.enchantments.mcmmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;

public class Fishing {
	
	public static List<String> enchantmentDefaults(String type, boolean fifty){
		List<String> common = new ArrayList<String>();
		
		switch(type.toUpperCase()) {
		case "COMMON":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 1).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 1).toString());
			}
			break;
		case "UNCOMMON":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 1).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 2).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
			}
			break;
		case "RARE":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 2).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.UNREST), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 1).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FLOWER_GIFT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FROST_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 3).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.UNREST), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.VOID_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 1).toString());
			}
			break;
		case "EPIC":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FROST_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GUNG_HO), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_INFINITE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MULTISHOT), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 3).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 2).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SILK_TOUCH), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SPLATTER_FEST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 4).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.UNREST), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.VOID_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WAND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 2).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.CHANNELING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GUNG_HO), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 2).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MULTISHOT), 1).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 3).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 2).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SACRIFICE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SILK_TOUCH), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SPLATTER_FEST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 4).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WAND), 1).toString());
			}
			break;
		case "LEGENDARY":
			if(fifty) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.CHANNELING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_FIRE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FLOWER_GIFT), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FROST_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GUNG_HO), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_INFINITE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRENES_LASSO), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGIC_GUARD), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MENDING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MOISTURIZE), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MULTISHOT), 1).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 4).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 4).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 3).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SACRIFICE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SILK_TOUCH), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SPLATTER_FEST), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 5).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.UNREST), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.VOID_WALKER), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WAND), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 2).toString());
			} else {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FROST_WALKER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_INFINITE), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRENES_LASSO), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGIC_GUARD), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MENDING), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MOISTURIZE), 1).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 3).toString());
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 2).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 3).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SACRIFICE), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
				if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
					common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 5).toString());
				}
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TELEPATHY), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TRANSMUTATION), 1).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.VOID_WALKER), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WAND), 2).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 3).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 2).toString());
			}
			break;
		case "ANCIENT":
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ANGLER), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.WATER_WORKER), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ARTHROPODS), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BEHEADING), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_EXPLOSIONS), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.BRINE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.CHANNELING), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.BINDING_CURSE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.VANISHING_CURSE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DEPTH_STRIDER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.DROWNED), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DIG_SPEED), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.EXP_SHARE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FALL), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FIRE_ASPECT), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_FIRE), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_FIRE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FLOWER_GIFT), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_BLOCKS), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FREQUENT_FLYER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.FRIED), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.FROST_WALKER), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GOLD_DIGGER), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.GUNG_HO), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HARD_BOUNCE), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.HEIGHT_PLUS_PLUS), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.ICARUS), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.IMPALING), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_INFINITE), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRENES_LASSO), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.IRON_DEFENSE), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.KNOCKBACK), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.KNOCKUP), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LOYALTY), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LUCK), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.LURE), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGIC_GUARD), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MAGMA_WALKER), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MENDING), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.MOISTURIZE), 1).toString());
			if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.MULTISHOT), 1).toString());
			}
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.NO_REST), 1).toString());
			if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PIERCING), 5).toString());
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.PILLAGE), 5).toString());
			}
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_DAMAGE), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_PROJECTILE), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL), 4).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.ARROW_KNOCKBACK), 3).toString());
			if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.QUICK_CHARGE), 4).toString());
			}
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.OXYGEN), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.RIPTIDE), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SACRIFICE), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SAND_VEIL), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_ALL), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SHOCK_ASPECT), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SILK_TOUCH), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SMELTERY), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DAMAGE_UNDEAD), 6).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SNIPER), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOUL_REAPER), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SPLATTER_FEST), 1).toString());
			if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
				common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.STONE_THROW), 6).toString());
			}
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.SWEEPING_EDGE), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TELEPATHY), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.THORNS), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TRANSMUTATION), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(Enchantment.DURABILITY), 5).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.UNREST), 1).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.VOID_WALKER), 2).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WAND), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WARP), 3).toString());
			common.add(new FishingEnchanted(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.WIDTH_PLUS_PLUS), 3).toString());
		}
		
		return common;
	}
	
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
			if(enchantment.getEnchant().canAnvil(player, enchantment.getLevel()) && enchantment.getEnchant().canEnchantItem(item.getType()) && enchantment.getEnchant().isEnabled()){
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
