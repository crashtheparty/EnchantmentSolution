package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.ctp.crashapi.resources.advancements.Advancement.Frame;
import org.ctp.crashapi.resources.advancements.CrashTrigger;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;

public class ESDiscoveryAdvancement {
	
	private final NamespacedKey namespace;
	private final Material icon = Material.ENCHANTED_BOOK;
	private List<CrashTrigger> triggers = new ArrayList<CrashTrigger>();
	private final Frame frame = Frame.GOAL;
	private boolean enabled;
	private final CustomEnchantment enchantment;
	
	public ESDiscoveryAdvancement(CustomEnchantment enchantment) {
		this.enchantment = enchantment;
		namespace = EnchantmentSolution.getKey("discovery/" + enchantment.getName().toLowerCase());
		triggers = Arrays.asList(new CrashTrigger("discovery"));
	}

	public Frame getFrame() {
		return frame;
	}

	public Material getIcon() {
		return icon;
	}

	public List<CrashTrigger> getTriggers() {
		return triggers;
	}

	public NamespacedKey getNamespace() {
		return namespace;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public CustomEnchantment getEnchantment() {
		return enchantment;
	}
}
