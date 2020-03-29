package org.ctp.enchantmentsolution.rpg;

import java.math.BigInteger;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class RPGUtils {
	
	private static List<RPGPlayer> PLAYERS = new ArrayList<RPGPlayer>();
	
	public static BigInteger getExperienceNextLevel(int level) {
		return new BigInteger((1000 + (level - 1) * 100) + "");
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

	public static void giveExperience(Player player, Enchantment enchantment, Material type) {
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
		rpg.addExperience(49);
	}
	
	public static RPGPlayer addRPGPlayer(Player player) {
		for(RPGPlayer p : PLAYERS)
			if(!p.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) return p;
		RPGPlayer p = new RPGPlayer(player);
		PLAYERS.add(p);
		return p;
	}
	
	public static void addRPGPlayer(String uuid, int level, String experience) {
		for(RPGPlayer p : PLAYERS)
			if(p.getPlayer().getUniqueId().toString().equals(uuid)) return;
		PLAYERS.add(new RPGPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), level, experience));
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
	
}
