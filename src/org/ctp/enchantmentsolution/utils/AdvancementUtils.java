package org.ctp.enchantmentsolution.utils;

import org.bukkit.Bukkit;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.advancements.ESTrigger;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.Advancement;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.AdvancementFactory;

public class AdvancementUtils {
	
	public AdvancementUtils() {
		int version = EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber();
		AdvancementFactory factory = new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false);
		Advancement root = null;
		Advancement last = null;
		for(ESAdvancement advancement : ESAdvancement.values()) {
			if(advancement.getParent() == null) {
				root = factory.getRoot(advancement.getNamespace().getKey(), ConfigUtils.getAdvancementName(advancement.getNamespace().getKey()), 
						ConfigUtils.getAdvancementDescription(advancement.getNamespace().getKey()), advancement.getIcon(), "block/bookshelf");
				root.activate(false);
			} else {
				if(advancement.getActivatedVersion() < version) {
					if(advancement.getParent() == ESAdvancement.ENCHANTMENT_SOLUTION) {
						last = root;
					}
					last = factory.getSimple(advancement.getNamespace().getKey(), last, ConfigUtils.getAdvancementName(advancement.getNamespace().getKey()), 
						ConfigUtils.getAdvancementDescription(advancement.getNamespace().getKey()), advancement.getIcon(), -1, 
						advancement.getTriggers().get(0).getCriteria(), advancement.getTriggers().get(0).getTrigger());
					for(int i = 1; i < advancement.getTriggers().size(); i++) {
						ESTrigger trigger = advancement.getTriggers().get(i);
						if(version >= trigger.getVersionMinimum() && (trigger.getVersionMaximum() == 0 || version <= trigger.getVersionMaximum())) {
							last.addTrigger(advancement.getTriggers().get(i).getCriteria(), advancement.getTriggers().get(i).getTrigger());
						}
					}
					last.setFrame(advancement.getFrame());
					last.setRewards(advancement.getRewards());
					last.activate(false);
				}
			}
		}
		
		Bukkit.reloadData();
	}
}
