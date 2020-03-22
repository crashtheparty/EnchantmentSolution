package org.ctp.enchantmentsolution.rpg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class RPGUtils {
	
	private static List<RPGPlayer> PLAYERS = new ArrayList<RPGPlayer>();
	
	public static BigInteger getExperienceNextLevel(int level) {
		return new BigInteger((1000 + (level - 1) * 100) + "");
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
	
	public static void addRPGPlayer(Player player) {
		for(RPGPlayer p : PLAYERS)
			if(!p.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) return;
		PLAYERS.add(new RPGPlayer(player));
	}
	
	public static void addRPGPlayer(String uuid, int level, String experience) {
		for(RPGPlayer p : PLAYERS)
			if(p.getPlayer().getUniqueId().toString().equals(uuid)) return;
		PLAYERS.add(new RPGPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), level, experience));
	}
	
}
