package org.ctp.enchantmentsolution.rpg;

import java.math.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.nms.HotbarNMS;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
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
	private long lastLevelUp, lastLevelUpSound;

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
		if (exp >= 0) {
			experience = experience.add(BigDecimal.valueOf(exp));
			int oldLevel = level;
			boolean levelUp = false;
			while (RPGUtils.getExperienceNextLevel(level + 1).compareTo(experience) <= 0) {
				level++;
				levelUp = true;
				experience = experience.subtract(RPGUtils.getExperienceNextLevel(level));
			}
			int newLevel = level;
			if (levelUp) {
				levelUpPoints(oldLevel, newLevel);
				if ((lastLevelUpSound == 0 || lastLevelUpSound + 2000 < System.currentTimeMillis()) && player.getPlayer() != null) {
					Player p = player.getPlayer();
					p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.AMBIENT, 1.0F, 4.0F);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 1.0F, 4.0F);
					lastLevelUpSound = System.currentTimeMillis();
				}
				lastLevelUp = System.currentTimeMillis();
			}
			addToBar();
		}
	}

	public void setExperience(double exp) {
		experience = new BigDecimal("" + exp);
		addExperience(0);
	}

	public void setLevel(int level) {
		this.level = level;
		addExperience(0);
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
		HashMap<String, Object> codes = ChatUtils.getCodes();
		BigDecimal nextExperience = RPGUtils.getExperienceNextLevel(level + 1);
		codes.put("%player%", player.getName());
		codes.put("%level%", level);
		codes.put("%experience%", experience.setScale(2, RoundingMode.DOWN).toPlainString());
		codes.put("%next_experience%", nextExperience.setScale(2, RoundingMode.DOWN).toPlainString());
		String title = Chatable.get().getMessage(codes, "rpg.top_bar.title");

		if (lastLevelUp + 3000 > System.currentTimeMillis()) title = Chatable.get().getMessage(codes, "rpg.top_bar.title_level_up");
		if (bar == null) bar = Bukkit.createBossBar(title, BarColor.GREEN, BarStyle.SOLID, new BarFlag[0]);
		else
			bar.setTitle(title);
		if (run == null) {
			run = new RPGThread(this);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), run, 1l, 1l);
			run.setId(id);
		} else
			run.setRun();
		BigDecimal decimal = experience.divide(nextExperience, new MathContext(2, RoundingMode.DOWN)).setScale(2, RoundingMode.DOWN);
		bar.setProgress(decimal.doubleValue() % 1);
		bar.addPlayer(player.getPlayer());
		bar.setVisible(true);
	}

	public void levelUpPoints(int oldLevel, int newLevel) {
		BigInteger oldPoints = RPGUtils.getPointsForLevel(oldLevel);
		BigInteger newPoints = RPGUtils.getPointsForLevel(newLevel);
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%player%", player.getName());
		codes.put("%added_points%", newPoints.subtract(oldPoints).intValue());
		codes.put("%old_points%", oldPoints.intValue());
		codes.put("%new_points%", newPoints.intValue());

		HotbarNMS.sendHotBarMessage(player.getPlayer(), Chatable.get().getMessage(codes, "rpg.level_up_points"));
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
		if (player.isOnline() && player.getPlayer().hasPermission("enchantmentsolution.enchantments.rpg.all")) {
			enchantments = new HashMap<Enchantment, Integer>();
			for(CustomEnchantment ench: RegisterEnchantments.getEnchantments())
				enchantments.put(ench.getRelativeEnchantment(), Integer.MAX_VALUE);
			return enchantments;
		}
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

	public boolean giveEnchantment(String s, YamlConfig config) {
		EnchantmentLevel level = new EnchantmentLevel(s, config);
		return level.getEnchant() != null && level.getLevel() > 0 && giveEnchantment(level);
	}

	public boolean giveEnchantment(EnchantmentLevel level) {
		enchantments = null;
		if (level != null) enchantmentList.put(level.getEnchant().getRelativeEnchantment(), level.getLevel());
		getEnchantmentLevels();
		return true;
	}

	public boolean removeEnchantment(Enchantment enchantment) {
		enchantments = null;
		enchantmentList.remove(enchantment);
		getEnchantmentLevels();
		return true;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return getEnchantmentLevels();
	}

	public int getPoints() {
		BigInteger points = RPGUtils.getPointsForLevel(level);
		Iterator<Entry<Enchantment, Integer>> iterator = enchantmentList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Enchantment, Integer> entry = iterator.next();
			int level = entry.getValue();
			while (level > 0) {
				BigInteger lowerPoints = RPGUtils.getPointsForEnchantment(player.getPlayer(), entry.getKey(), level);
				if (lowerPoints.intValue() < 0) continue;
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
		return getPoints() >= 0 && pointsToBuy(key, value) >= 0 && getPoints() - pointsToBuy(key, value) >= 0 && enchant.isEnabled() && PermissionUtils.canEnchant(player.getPlayer(), enchant, value);
	}

}
