package org.ctp.enchantmentsolution.rpg;

import java.math.*;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.*;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.rpg.threads.RPGThread;

public class RPGPlayer {

	private OfflinePlayer player;
	private int level;
	private BigInteger experience;
	private BossBar bar;
	private RPGThread run;
	private Map<Enchantment, Integer> enchantmentList;
	
	public RPGPlayer(OfflinePlayer player) {
		this.player = player;
		this.level = 0;
		this.experience = new BigInteger("0");
		enchantmentList = new HashMap<Enchantment, Integer>();
	}
	
	public RPGPlayer(OfflinePlayer player, int level, String experience) {
		this.player = player;
		this.level = level;
		this.experience = new BigInteger(experience);
		enchantmentList = new HashMap<Enchantment, Integer>();
	}
	
	public void addExperience(int exp) {
		experience = experience.add(BigInteger.valueOf(exp));
		while(RPGUtils.getExperienceNextLevel(level + 1).compareTo(experience) <= 0){
			level ++;
			experience = experience.subtract(RPGUtils.getExperienceNextLevel(level));
		}
		addToBar();
	}
	
	public void setExperience(int exp) {
		experience = new BigInteger("" + exp);
	}
	
	public void resetExperience() {
		level = 0;
		experience = new BigInteger("0");
	}
	
	public BigInteger getExperience() {
		return experience;
	}
	
	public OfflinePlayer getPlayer() {
		return player;
	}
	
	public void addToBar() {
		if(bar == null) bar = Bukkit.createBossBar(ChatColor.GREEN + "ESRPG Level " + ChatColor.WHITE + level + ChatColor.GREEN + " Experience: " + ChatColor.WHITE + experience.toString(), BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);
		else 
			bar.setTitle(ChatColor.GREEN + "ESRPG Level " + ChatColor.WHITE + level + ChatColor.GREEN + " Experience: " + ChatColor.WHITE + experience.toString());
		if(run == null) {
			run = new RPGThread(this);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), run, 1l, 1l);
			run.setId(id);
		} else
			run.setRun();
		BigDecimal decimal = new BigDecimal(experience).divide(new BigDecimal(RPGUtils.getExperienceNextLevel(level)), new MathContext(2, RoundingMode.HALF_UP));
		bar.setProgress(decimal.doubleValue() % 1);
		bar.addPlayer(player.getPlayer());
		bar.setVisible(true);
	}
	
	public void removeFromBar() {
		Bukkit.getScheduler().cancelTask(run.getId());
		bar.setVisible(false);
		run = null;
	}
	
	public boolean hasEnchantment(Enchantment enchantment, int i) {
		return enchantmentList.containsKey(enchantment) && enchantmentList.get(enchantment).intValue() >= i;
	}
	
}
