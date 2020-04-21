package org.ctp.enchantmentsolution.rpg;

import java.math.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.*;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.rpg.threads.RPGThread;
import org.ctp.enchantmentsolution.utils.PermissionUtils;

public class RPGPlayer {

	private OfflinePlayer player;
	private int level;
	private BigDecimal experience;
	private BossBar bar;
	private RPGThread run;
	private Map<Enchantment, Integer> enchantmentList, enchantments;

	public RPGPlayer(OfflinePlayer player) {
		this.player = player;
		level = 0;
		experience = new BigDecimal("0");
		enchantmentList = new HashMap<Enchantment, Integer>();
		getEnchantmentLevels();
	}

	public RPGPlayer(OfflinePlayer player, int level, String experience) {
		this.player = player;
		this.level = level;
		this.experience = new BigDecimal(experience).setScale(2, RoundingMode.DOWN);
		enchantmentList = new HashMap<Enchantment, Integer>();
		getEnchantmentLevels();
	}

	public void addExperience(double exp) {
		if (exp > 0) {
			experience = experience.add(BigDecimal.valueOf(exp));
			while (RPGUtils.getExperienceNextLevel(level + 1).compareTo(experience) <= 0) {
				level++;
				experience = experience.subtract(RPGUtils.getExperienceNextLevel(level));
			}
			addToBar();
		}
	}

	public void setExperience(double exp) {
		experience = new BigDecimal("" + exp);
	}

	public void resetExperience() {
		level = 0;
		experience = new BigDecimal("0");
	}

	public BigDecimal getExperience() {
		return experience.round(MathContext.UNLIMITED);
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public void addToBar() {
		if (bar == null) bar = Bukkit.createBossBar(ChatColor.GREEN + "ESRPG Level " + ChatColor.WHITE + level + ChatColor.GREEN + " Experience: " + ChatColor.WHITE + experience.toString(), BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);
		else
			bar.setTitle(ChatColor.GREEN + "ESRPG Level " + ChatColor.WHITE + level + ChatColor.GREEN + " Experience: " + ChatColor.WHITE + experience.setScale(2, RoundingMode.DOWN).toString());
		if (run == null) {
			run = new RPGThread(this);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), run, 1l, 1l);
			run.setId(id);
		} else
			run.setRun();
		BigDecimal decimal = experience.divide(RPGUtils.getExperienceNextLevel(level + 1), new MathContext(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.DOWN);
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
		return enchantments.containsKey(enchantment) && enchantments.get(enchantment).intValue() >= i;
	}

	public int getMaxLevel(Enchantment enchantment) {
		if (enchantments.containsKey(enchantment)) return enchantments.get(enchantment);
		return 0;
	}

	private Map<Enchantment, Integer> getEnchantmentLevels() {
		if (enchantments == null) {
			enchantments = new HashMap<Enchantment, Integer>();
			Iterator<Entry<Enchantment, Integer>> iter2 = RPGUtils.getFreeEnchantments().entrySet().iterator();
			while (iter2.hasNext()) {
				Entry<Enchantment, Integer> entry = iter2.next();
				enchantments.put(entry.getKey(), entry.getValue());
			}
			Iterator<Entry<Enchantment, Integer>> iter = enchantmentList.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Enchantment, Integer> entry = iter.next();
				enchantments.put(entry.getKey(), entry.getValue());
			}
		}
		return enchantments;
	}

	public int getLevel() {
		return level;
	}

	public Map<Enchantment, Integer> getEnchantmentList() {
		return enchantmentList;
	}

	public boolean giveEnchantment(String s) {
		EnchantmentLevel level = new EnchantmentLevel(s);
		return level.getEnchant() != null && level.getLevel() > 0 && giveEnchantment(level);
	}

	public boolean giveEnchantment(EnchantmentLevel level) {
		enchantments = null;
		enchantmentList.put(level.getEnchant().getRelativeEnchantment(), level.getLevel());
		getEnchantmentLevels();
		return true;
	}

	public int getPoints() {
		BigInteger points = RPGUtils.getPointsForLevel(level);
		Iterator<Entry<Enchantment, Integer>> iterator = enchantmentList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Enchantment, Integer> entry = iterator.next();
			int level = entry.getValue();
			while (level > 0) {
				BigInteger lowerPoints = RPGUtils.getPointsForEnchantment(player.getPlayer(), entry.getKey(), level);
				if(lowerPoints.intValue() < 0) continue;
				points = points.subtract(lowerPoints);
				level--;
			}
		}
		return points.intValue();
	}

	public int pointsToBuy(Enchantment key, int value) {
		if (RPGUtils.getFreeEnchantments().containsKey(key) && RPGUtils.getFreeEnchantments().get(key) <= value) return -1;
		Map<Enchantment, Integer> enchantments = getEnchantmentLevels();
		int minLevel = 0;
		if (enchantments.containsKey(key)) minLevel = enchantments.get(key);
		int points = 0;
		for(int i = minLevel + 1; i <= value; i++)
			points += RPGUtils.getPointsForEnchantment(player.getPlayer(), key, i).intValue();
		return points;
	}

	public boolean canBuy(Enchantment key, int value) {
		CustomEnchantment enchant = RegisterEnchantments.getCustomEnchantment(key);
		return getPoints() - pointsToBuy(key, value) >= 0 && enchant.isEnabled() && PermissionUtils.canEnchant(player.getPlayer(), enchant, value);
	}

}
