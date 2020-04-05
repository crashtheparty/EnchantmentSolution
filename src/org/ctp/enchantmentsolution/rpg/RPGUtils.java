package org.ctp.enchantmentsolution.rpg;

import java.math.*;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class RPGUtils {
	
	private static List<RPGPlayer> PLAYERS = new ArrayList<RPGPlayer>();
	
	public static BigDecimal getExperienceNextLevel(int level) {
		Double base = ConfigString.RPG_BASE.getDouble();
		Double multiply = ConfigString.RPG_MULTIPLY.getDouble();
		return new BigDecimal(((base + (level - 1) * multiply)) + "", new MathContext(2, RoundingMode.HALF_UP));
	}
	
	public static BigInteger getPointsForLevel(int level) {
		Double levelZero = ConfigString.RPG_LEVELS_0.getDouble();
		Double base = ConfigString.RPG_LEVELS_BASE.getDouble();
		Double add = ConfigString.RPG_LEVELS_ADD.getDouble();
		Double addPower = ConfigString.RPG_LEVELS_POWER.getDouble();
		return new BigInteger((int) ((levelZero + base * level + Math.pow(add * level, addPower))) + "");
	}
	
	public static Map<Enchantment, Integer> getFreeEnchantments(){
		Map<Enchantment, Integer> free = new HashMap<Enchantment, Integer>();
		
		List<String> freeStrings = Configurations.getRPG().getStringList("free_enchantments");
		for(String s : freeStrings) {
			String[] split = s.split("@");
			String[] key = split[0].split("\\+");
			try {
				free.put(RegisterEnchantments.getByName(key[1]).getRelativeEnchantment(), Integer.parseInt(split[1]));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return free;
	}

	public static void giveExperience(Player player, EnchantmentLevel enchantment) {
		RPGPlayer rpg = null;
		for(RPGPlayer p : PLAYERS)
			if(p.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
				rpg = p;
				break;
			}
		if (rpg == null) {
			rpg = new RPGPlayer(player);
			PLAYERS.add(rpg);
		}
		double exp = 0;
		int level = enchantment.getLevel();
		switch(enchantment.getEnchant().getName()) {
			case "efficiency":
				exp = 0.9 * level;
				break;
			case "silk_touch":
				exp = 5;
				break;
			case "fortune":
				exp = 1.3 * level;
				break;
			case "aqua_affinity":
				exp = 3.8;
				break;
			case "protection":
				exp = 0.04 * level;
				break;
			case "projectile_protection":
				exp = 0.08 * level;
				break;
			case "feather_falling":
				exp = 0.01 * level;
				break;
			case "fire_protection":
				exp = 0.01 * level;
				break;
			case "blast_protection":
				exp = 0.1 * level;
				break;
			case "sharpness":
				exp = 0.8 * level;
				break;
			case "smite":
				exp = 1.05 * level;
				break;
			case "bane_of_arthropods":
				exp = 1.2 * level;
				break;
			case "sweeping_edge":
				exp = 1.6 * level;
				break;
			case "flame":
				exp = 3.5;
				break;
			case "power":
				exp = 0.8 * level;
				break;
			case "fire_aspect":
				exp = 2.2 * level;
				break;
			case "piercing":
				exp = 0.65 * level;
				break;
			case "punch":
				exp = 0.7 * level;
				break;
			case "knockback":
				exp = 0.7 * level;
				break;
			case "thorns":
				exp = 1 * level;
				break;
			case "channeling":
				exp = 5.5 * level;
				break;
			case "frost_walker":
				exp = 0.004 * level;
				break;
			case "impaling":
				exp = 0.8 * level;
				break;
			case "infinity":
				exp = 2.5;
				break;
			case "loyalty":
				exp = 1.1 * level;
				break;
			case "multishot":
				exp = 1;
				break;
			case "riptide":
				exp = 1.2 * level;
				break;
			case "mending":
				exp = 0.5;
				break;
			case "quick_charge":
				exp = 0.2;
				break;
			case "luck_of_the_sea":
				exp = 1.4;
				break;
			case "lure":
				exp = 0.9;
				break;
			case "looting":
				exp = 1.25;
				break;
			case "respiration":
				exp = 0.0001;
				break;
			case "depth_strider":
				exp = 0.0001;
				break;
		}
		rpg.addExperience(exp);
	}
	
	public static RPGPlayer addRPGPlayer(Player player) {
		for(RPGPlayer p : PLAYERS)
			if(!p.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) return p;
		RPGPlayer p = new RPGPlayer(player);
		PLAYERS.add(p);
		return p;
	}
	
	public static RPGPlayer addRPGPlayer(String uuid, int level, String experience) {
		for(RPGPlayer p : PLAYERS)
			if(p.getPlayer().getUniqueId().toString().equals(uuid)) return p;
		RPGPlayer p = new RPGPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), level, experience);
		PLAYERS.add(p);
		return p;
	}
	
	public static RPGPlayer getPlayer(Player player) {
		for (RPGPlayer rpg : PLAYERS)
			if(rpg.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) return rpg;
		return null;
	}

	public static boolean canEnchant(Player player, CustomEnchantment customEnchantment, int i) {
		if (!ConfigString.RPG.getBoolean()) return true;
		
		RPGPlayer rpg = getPlayer(player);
		if(rpg == null) rpg = addRPGPlayer(player);
		return rpg.hasEnchantment(customEnchantment.getRelativeEnchantment(), i);
	}

	public static List<RPGPlayer> getPlayers() {
		return PLAYERS;
	}
	
}
