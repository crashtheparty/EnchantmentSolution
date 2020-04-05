package org.ctp.enchantmentsolution.rpg;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.rpg.threads.RPGThread;

public class RPGPlayer {

	private OfflinePlayer player;
	private int level;
	private BigDecimal experience;
	private BossBar bar;
	private RPGThread run;
	private Map<Enchantment, Integer> enchantmentList;

	public RPGPlayer(OfflinePlayer player) {
		this.player = player;
		this.level = 0;
		this.experience = new BigDecimal("0");
		enchantmentList = new HashMap<Enchantment, Integer>();
	}

	public RPGPlayer(OfflinePlayer player, int level, String experience) {
		this.player = player;
		this.level = level;
		this.experience = new BigDecimal(experience);
		enchantmentList = new HashMap<Enchantment, Integer>();
	}

	public void addExperience(double exp) {
		if(exp > 0) {
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
			bar.setTitle(ChatColor.GREEN + "ESRPG Level " + ChatColor.WHITE + level + ChatColor.GREEN + " Experience: " + ChatColor.WHITE + experience.setScale(2, RoundingMode.HALF_DOWN).toString());
		if (run == null) {
			run = new RPGThread(this);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), run, 1l, 1l);
			run.setId(id);
		} else
			run.setRun();
		BigDecimal decimal = experience.divide(RPGUtils.getExperienceNextLevel(level + 1), new MathContext(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_DOWN);
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
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
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
		return enchantments.containsKey(enchantment) && enchantments.get(enchantment).intValue() >= i;
	}

	public int getLevel() {
		return level;
	}

	public Map<Enchantment, Integer> getEnchantmentList() {
		return enchantmentList;
	}

	public boolean giveEnchantment(String s) {
		String[] split = s.split("\\@");
		if (split.length == 2) {
			String[] enchString = split[0].split(":");
			if (enchString.length == 2) try {
				Enchantment enchant = RegisterEnchantments.getByKey(new NamespacedKey(Bukkit.getPluginManager().getPlugin(enchString[0]), enchString[1]));
				if (enchant != null) {
					int level = Integer.parseInt(split[1]);

					return giveEnchantment(enchant, level);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public boolean giveEnchantment(Enchantment enchant, int level) {
		enchantmentList.put(enchant, level);
		return true;
	}

}
