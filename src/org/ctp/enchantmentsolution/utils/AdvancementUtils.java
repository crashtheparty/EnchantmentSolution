package org.ctp.enchantmentsolution.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.*;

public class AdvancementUtils {

	public static void createAdvancements() {
		int version = EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber();
		AdvancementFactory factory = new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false);
		Advancement root = null;
		Advancement last = null;
		boolean reload = false;
		for(ESAdvancement advancement: ESAdvancement.values()) {
			String namespace = advancement.getNamespace().getKey();
			boolean announce = ConfigUtils.announceAdvancement(namespace);
			boolean toast = ConfigUtils.toastAdvancement(namespace);
			if (advancement.getParent() == null) {
				if (ConfigUtils.isAdvancementActive(namespace)) {
					root = factory.getRoot(namespace, ConfigUtils.getAdvancementName(namespace),
							ConfigUtils.getAdvancementDescription(namespace), advancement.getIcon(), "block/bookshelf");
					root.setAnnounce(announce);
					root.setToast(toast);
					if (root.activate(false).isChanged()) {
						reload = true;
					}
				} else {
					root = null;
					advancement.setEnabled(false);
					if (Advancement.deactivate(false, advancement.getNamespace()).isChanged()) {
						reload = true;
					}
				}
			} else {
				if (root != null && ConfigUtils.isAdvancementActive(namespace)
						&& advancement.getActivatedVersion() < version) {
					if (advancement.getParent() == ESAdvancement.ENCHANTMENT_SOLUTION) {
						last = root;
					}
					List<ESTrigger> triggers = advancement.getTriggers();
					last = factory.getSimple(namespace, last, ConfigUtils.getAdvancementName(namespace),
							ConfigUtils.getAdvancementDescription(namespace), advancement.getIcon(),
							triggers.get(0).getCriteria(), triggers.get(0).getTrigger());
					for(int i = 1; i < triggers.size(); i++) {
						ESTrigger trigger = triggers.get(i);
						if (version >= trigger.getVersionMinimum()
								&& (trigger.getVersionMaximum() == 0 || version <= trigger.getVersionMaximum())) {
							last.addTrigger(trigger.getCriteria(), trigger.getTrigger());
						}
					}
					last.setAnnounce(announce);
					last.setToast(toast);
					last.setFrame(advancement.getFrame());
					last.setRewards(advancement.getRewards());
					if (last.activate(false).isChanged()) {
						reload = true;
					}

					advancement.setEnabled(true);
				} else {
					advancement.setEnabled(false);

					if (Advancement.deactivate(false, advancement.getNamespace()).isChanged()) {
						reload = true;
					}
				}
			}
		}
		if (reload) {
			ChatUtils.sendInfo("Reloading recipes and advancements...");
			Bukkit.reloadData();
			ChatUtils.sendInfo("Reloaded!");
		}
	}

	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria) {
		if (advancement.isEnabled()) {
			AdvancementProgress progress = player
					.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if (progress.getRemainingCriteria().contains(criteria)) {
				progress.awardCriteria(criteria);
				return true;
			}
		}
		return false;
	}

	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria, int amount) {
		if (advancement.isEnabled()) {
			AdvancementProgress progress = player
					.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if (progress.getRemainingCriteria().contains(criteria)) {
				ESAdvancementProgress esProgress = EnchantmentSolution.getAdvancementProgress(player, advancement,
						criteria);
				esProgress.setCurrentAmount(esProgress.getCurrentAmount() + amount);
				ESTrigger trigger = null;
				for(ESTrigger t: advancement.getTriggers()) {
					if (t.getCriteria().equals(criteria)) {
						trigger = t;
						break;
					}
				}
				if (trigger != null) {
					if (esProgress.getCurrentAmount() >= trigger.getMaxAmount()) {
						progress.awardCriteria(criteria);
						EnchantmentSolution.completed(esProgress);
						return true;
					}
				}
			}
		}
		return false;
	}
}
