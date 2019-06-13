package org.ctp.enchantmentsolution.utils;

import org.bukkit.Bukkit;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.advancements.ESAdvancementProgress;
import org.ctp.enchantmentsolution.advancements.ESTrigger;
import org.ctp.enchantmentsolution.api.Advancement;
import org.ctp.enchantmentsolution.api.AdvancementFactory;

public class AdvancementUtils {
	
	public static void createAdvancements() {
		int version = EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber();
		AdvancementFactory factory = new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false);
		Advancement root = null;
		Advancement last = null;
		for(ESAdvancement advancement : ESAdvancement.values()) {
			if(advancement.getParent() == null) {
				if(ConfigUtils.isAdvancementActive(advancement.getNamespace().getKey())) {
					root = factory.getRoot(advancement.getNamespace().getKey(), ConfigUtils.getAdvancementName(advancement.getNamespace().getKey()), 
							ConfigUtils.getAdvancementDescription(advancement.getNamespace().getKey()), advancement.getIcon(), "block/bookshelf");
					root.setAnnounce(ConfigUtils.announceAdvancement(advancement.getNamespace().getKey()));
					root.setToast(ConfigUtils.toastAdvancement(advancement.getNamespace().getKey()));
					root.activate(false);
				} else {
					root = null;
					advancement.setEnabled(false);
					Advancement.deactivate(false, advancement.getNamespace());
				}
			} else {
				if(root != null && ConfigUtils.isAdvancementActive(advancement.getNamespace().getKey()) && advancement.getActivatedVersion() < version) {
					if(advancement.getParent() == ESAdvancement.ENCHANTMENT_SOLUTION) {
						last = root;
					}
					last = factory.getSimple(advancement.getNamespace().getKey(), last, ConfigUtils.getAdvancementName(advancement.getNamespace().getKey()), 
						ConfigUtils.getAdvancementDescription(advancement.getNamespace().getKey()), advancement.getIcon(), 
						advancement.getTriggers().get(0).getCriteria(), advancement.getTriggers().get(0).getTrigger());
					for(int i = 1; i < advancement.getTriggers().size(); i++) {
						ESTrigger trigger = advancement.getTriggers().get(i);
						if(version >= trigger.getVersionMinimum() && (trigger.getVersionMaximum() == 0 || version <= trigger.getVersionMaximum())) {
							last.addTrigger(advancement.getTriggers().get(i).getCriteria(), advancement.getTriggers().get(i).getTrigger());
						}
					}
					root.setAnnounce(ConfigUtils.announceAdvancement(advancement.getNamespace().getKey()));
					root.setToast(ConfigUtils.toastAdvancement(advancement.getNamespace().getKey()));
					last.setFrame(advancement.getFrame());
					last.setRewards(advancement.getRewards());
					last.activate(false);
					
					advancement.setEnabled(true);
				} else {
					advancement.setEnabled(false);
					
					Advancement.deactivate(false, advancement.getNamespace());
				}
			}
		}
		
		Bukkit.reloadData();
	}
	
	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria) {
		if(advancement.isEnabled()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if(progress.getRemainingCriteria().contains(criteria)) {
				progress.awardCriteria(criteria);
				return true;
			}
		}
		return false;
	}
	
	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria, int amount) {
		if(advancement.isEnabled()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if(progress.getRemainingCriteria().contains(criteria)) {
				ESAdvancementProgress esProgress = EnchantmentSolution.getAdvancementProgress(player, advancement, criteria);
				esProgress.setCurrentAmount(esProgress.getCurrentAmount() + amount);
				ESTrigger trigger = null;
				for(ESTrigger t : advancement.getTriggers()) {
					if(t.getCriteria().equals(criteria)) {
						trigger = t;
						break;
					}
				}
				if(trigger != null) {
					if(esProgress.getCurrentAmount() >= trigger.getMaxAmount()) {
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
