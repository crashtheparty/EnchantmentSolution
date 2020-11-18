package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.ctp.crashapi.resources.advancements.Advancement;
import org.ctp.crashapi.resources.advancements.AdvancementFactory;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;

public class ESDiscoveryAdvancementTab {

	private static ESDiscoveryAdvancementTab TAB;
	private final AdvancementFactory factory;
	private List<ESDiscoveryAdvancement> advancements;
	private static final String BACKGROUND = "block/bookshelf";

	private ESDiscoveryAdvancementTab() {
		factory = new AdvancementFactory(EnchantmentSolution.getPlugin(), false, false);
		advancements = new ArrayList<ESDiscoveryAdvancement>();
		for(CustomEnchantment custom: RegisterEnchantments.getRegisteredEnchantmentsAlphabetical())
			advancements.add(new ESDiscoveryAdvancement(custom));
	}

	public static ESDiscoveryAdvancementTab getTab() {
		if (TAB == null) TAB = new ESDiscoveryAdvancementTab();
		return TAB;
	}

	public ESDiscoveryAdvancement getAdvancement(CustomEnchantment enchantment) {
		if (notNull(enchantment) && notNull(enchantment.getRelativeEnchantment())) for(ESDiscoveryAdvancement adv: advancements)
			if (notNull(adv) && notNull(adv.getEnchantment()) && adv.getEnchantment().getRelativeEnchantment() == enchantment.getRelativeEnchantment()) return adv;
		return null;
	}

	public String getBackground() {
		return BACKGROUND;
	}

	public List<ESDiscoveryAdvancement> getAdvancements() {
		return advancements;
	}

	public AdvancementFactory getFactory() {
		return factory;
	}

	private boolean notNull(Object obj) {
		return obj != null;
	}

	public static Advancement createRoot(AdvancementFactory factory) {
		ChatUtils utils = Chatable.get();
		return factory.getRoot("discovery/root", utils.getMessage(ChatUtils.getCodes(), "advancements.discovery.name"), utils.getMessage(ChatUtils.getCodes(), "advancements.discovery.description"), Material.ENCHANTED_BOOK, BACKGROUND);
	}
}
