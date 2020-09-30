package org.ctp.enchantmentsolution.utils;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.resources.advancements.*;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.*;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;

public class AdvancementUtils {

	public static void createAdvancements() {
		int version = CrashAPI.getPlugin().getBukkitVersion().getVersionNumber();
		boolean reload = false;

		for(ESAdvancementTab tab: ESAdvancementTab.getAllTabs()) {
			AdvancementFactory factory = tab.getFactory();
			for(ESAdvancement advancement: tab.getAdvancements()) {
				String namespace = advancement.getNamespace().getKey();
				Advancement adv = null;
				boolean announce = ConfigUtils.announceAdvancement(namespace);
				boolean toast = ConfigUtils.toastAdvancement(namespace);
				if (advancement.getParent() == null) {
					if (ConfigUtils.isAdvancementActive(namespace)) {
						adv = factory.getRoot(namespace, ConfigUtils.getAdvancementName(namespace), ConfigUtils.getAdvancementDescription(namespace), advancement.getIcon(), tab.getBackground());
						adv.setAnnounce(announce);
						adv.setToast(toast);
						if (adv.activate(false).isChanged()) reload = true;
						advancement.setEnabled(true);
					} else {
						advancement.setEnabled(false);
						if (Advancement.deactivate(false, advancement.getNamespace()).isChanged()) reload = true;
						continue;
					}
				} else {
					ESAdvancement last = advancement.getParent();
					Advancement parent = null;
					while (parent == null && last != null) {
						parent = tab.getRegistered(last);
						last = last.getParent();
					}
					if (parent != null && ConfigUtils.isAdvancementActive(namespace) && advancement.getActivatedVersion() < version) {
						List<CrashTrigger> triggers = advancement.getTriggers();
						adv = factory.getSimple(namespace, parent, ConfigUtils.getAdvancementName(namespace), ConfigUtils.getAdvancementDescription(namespace), advancement.getIcon(), triggers.get(0).getCriteria(), triggers.get(0).getTrigger());
						for(int i = 1; i < triggers.size(); i++) {
							CrashTrigger trigger = triggers.get(i);
							if (version >= trigger.getVersionMinimum() && (trigger.getVersionMaximum() == 0 || version <= trigger.getVersionMaximum())) adv.addTrigger(trigger.getCriteria(), trigger.getTrigger());
						}
						adv.setAnnounce(announce);
						adv.setToast(toast);
						adv.setFrame(advancement.getFrame());
						adv.setRewards(advancement.getRewards());
						if (adv.activate(false).isChanged()) reload = true;
						advancement.setEnabled(true);
					} else {
						advancement.setEnabled(false);

						if (Advancement.deactivate(false, advancement.getNamespace()).isChanged()) reload = true;
					}
				}
				if (adv != null) tab.register(advancement, adv);
			}
		}
		ESDiscoveryAdvancementTab discoveryTab = ESDiscoveryAdvancementTab.getTab();
		AdvancementFactory factory = discoveryTab.getFactory();
		Advancement discoveryRoot = ESDiscoveryAdvancementTab.createRoot(factory);
		if (ConfigString.DISCOVERY_ADVANCEMENTS.getBoolean() && discoveryRoot.activate(false).isChanged()) reload = true;
		else if (!ConfigString.DISCOVERY_ADVANCEMENTS.getBoolean() && Advancement.deactivate(false, EnchantmentSolution.getKey("discovery/root")).isChanged()) reload = true;
		int i = 0;
		Advancement last = null;
		for(ESDiscoveryAdvancement advancement: discoveryTab.getAdvancements()) {
			String namespace = advancement.getNamespace().getKey();
			List<CrashTrigger> triggers = advancement.getTriggers();
			ChatUtils utils = Chatable.get();
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%enchantment%", advancement.getEnchantment().getDisplayName());
			Advancement adv = factory.getSimple(namespace, i == 0 ? discoveryRoot : last, utils.getMessage(codes, "advancements.discovery.enchantments"), advancement.getEnchantment().getAdvancementDescription(), advancement.getIcon(), triggers.get(0).getCriteria(), triggers.get(0).getTrigger());
			adv.setAnnounce(false);
			adv.setToast(true);
			adv.setHidden(true);
			if (ConfigString.DISCOVERY_ADVANCEMENTS.getBoolean()) {
				advancement.setEnabled(advancement.getEnchantment().isEnabled());
				if (adv.activate(false).isChanged()) reload = true;
			} else {
				advancement.setEnabled(false);
				if (Advancement.deactivate(false, adv.getId()).isChanged()) reload = true;
			}
			i = (i + 1) % 8;
			last = adv;
		}

		if (reload) {
			Chatable.get().sendInfo("Reloading recipes and advancements...");
			Bukkit.reloadData();
			Chatable.get().sendInfo("Reloaded!");
		}
	}

	public static boolean awardCriteria(Player player, CustomEnchantment enchantment) {
		ESDiscoveryAdvancement advancement = ESDiscoveryAdvancementTab.getTab().getAdvancement(enchantment);
		if (advancement != null && advancement.isEnabled()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if (progress.getRemainingCriteria().contains("discovery")) {
				progress.awardCriteria("discovery");
				return true;
			}
		}
		return false;
	}

	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria) {
		if (advancement.isEnabled()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if (progress.getRemainingCriteria().contains(criteria)) {
				progress.awardCriteria(criteria);
				return true;
			}
		}
		return false;
	}

	public static boolean awardCriteria(Player player, ESAdvancement advancement, String criteria, int amount) {
		if (advancement.isEnabled()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancement.getNamespace()));
			if (progress.getRemainingCriteria().contains(criteria)) {
				CrashAdvancementProgress esProgress = EnchantmentSolution.getAdvancementProgress(player, advancement, criteria);
				esProgress.setCurrentAmount(esProgress.getCurrentAmount() + amount);
				CrashTrigger trigger = null;
				for(CrashTrigger t: advancement.getTriggers())
					if (t.getCriteria().equals(criteria)) {
						trigger = t;
						break;
					}
				if (trigger != null) if (esProgress.getCurrentAmount() >= trigger.getMaxAmount()) {
					progress.awardCriteria(criteria);
					EnchantmentSolution.completed(esProgress);
					return true;
				}
			}
		}
		return false;
	}
}
