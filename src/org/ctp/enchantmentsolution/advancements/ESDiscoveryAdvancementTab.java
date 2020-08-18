package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.List;

import org.ctp.crashapi.resources.advancements.AdvancementFactory;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;

public class ESDiscoveryAdvancementTab {

	private static ESDiscoveryAdvancementTab TAB;
	private final AdvancementFactory factory;
	private List<ESDiscoveryAdvancement> advancements;
	private final String background = "block/bookshelf";

	private ESDiscoveryAdvancementTab() {
		this.factory = new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false);
		advancements = new ArrayList<ESDiscoveryAdvancement>();
		for(CustomEnchantment custom: RegisterEnchantments.getRegisteredEnchantmentsAlphabetical())
			advancements.add(new ESDiscoveryAdvancement(custom));
	}

	public static ESDiscoveryAdvancementTab getTab() {
		if (TAB == null) TAB = new ESDiscoveryAdvancementTab();
		return TAB;
	}

	public ESDiscoveryAdvancement getAdvancement(CustomEnchantment enchantment) {
		for(ESDiscoveryAdvancement adv: advancements)
			if (adv.getEnchantment().getRelativeEnchantment() == enchantment.getRelativeEnchantment()) return adv;
		return null;
	}

	public String getBackground() {
		return background;
	}

	public List<ESDiscoveryAdvancement> getAdvancements() {
		return advancements;
	}

	public AdvancementFactory getFactory() {
		return factory;
	}
}
