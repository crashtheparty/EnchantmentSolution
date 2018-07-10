package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.level30.LevelThirtyEnchants;
import org.ctp.enchantmentsolution.enchantments.level50.LevelFiftyEnchants;
import org.ctp.enchantmentsolution.enchantments.wrappers.AnglerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.BeheadingWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.BrineWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.ExpShareWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.FrequentFlyerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.FriedWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.KnockUpWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.LifeWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.MagmaWalkerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SacrificeWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.ShockAspectWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SmelteryWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SniperWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SoulReaperWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SoulboundWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.TankWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.TelepathyWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.WarpWrapper;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class DefaultEnchantments {
	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	
	public static Enchantment SOULBOUND = new SoulboundWrapper(200);
	public static Enchantment SOUL_REAPER = new SoulReaperWrapper(201);
	public static Enchantment SHOCK_ASPECT = new ShockAspectWrapper(202);
	public static Enchantment LIFE = new LifeWrapper(204);
	public static Enchantment BEHEADING = new BeheadingWrapper(206);
	public static Enchantment KNOCKUP = new KnockUpWrapper(205);
	public static Enchantment WARP = new WarpWrapper(203);
	public static Enchantment EXP_SHARE = new ExpShareWrapper(207);
	public static Enchantment MAGMA_WALKER = new MagmaWalkerWrapper(208);
	public static Enchantment SNIPER = new SniperWrapper(209);
	public static Enchantment TELEPATHY = new TelepathyWrapper(210);
	public static Enchantment SMELTERY = new SmelteryWrapper(211);
	public static Enchantment SACRIFICE = new SacrificeWrapper(212);
	public static Enchantment ANGLER = new AnglerWrapper(214);
	public static Enchantment FRIED = new FriedWrapper(215);
	public static Enchantment FREQUENT_FLYER = new FrequentFlyerWrapper(216);
	public static Enchantment TANK = new TankWrapper(217);
	public static Enchantment BRINE = new BrineWrapper(218);

	public static List<CustomEnchantment> getEnchantments() {
		return ENCHANTMENTS;
	}
	public static void addDefaultEnchantment(CustomEnchantment enchant) {
		ENCHANTMENTS.add(enchant);
	}
	
	public static boolean isLevelFiftyEnchants() {
		if(ConfigFiles.MAIN_CONFIG == null) {
			return true;
		}
		return ConfigFiles.MAIN_CONFIG.getBoolean("level_50_enchants");
	}
	
	public static void addDefaultEnchantments() {
		if(isLevelFiftyEnchants()) {
			LevelFiftyEnchants.addDefaultEnchantments();
		} else {
			LevelThirtyEnchants.addDefaultEnchantments();
		}
	}
	
	public static void setEnchantments() {
		for (int i = 0; i < ENCHANTMENTS.size(); i++) {
			CustomEnchantment enchantment = ENCHANTMENTS.get(i);
			if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				if (ConfigFiles.MAIN_CONFIG.getBoolean("custom_enchantments."+enchantment.getName()+".enabled")) {
					Enchantments.addEnchantment(enchantment);
					ENCHANTMENTS.get(i).setEnabled(true);
				}
				if (ConfigFiles.MAIN_CONFIG.getBoolean("custom_enchantments."+enchantment.getName()+".treasure")) {
					ENCHANTMENTS.get(i).setTreasure(true);
				}
			} else {
				Enchantments.addEnchantment(enchantment);
			}
		}
	}
	
	public static boolean isEnabled(Enchantment enchant) {
		for(CustomEnchantment enchantment : ENCHANTMENTS) {
			if(enchant.equals(enchantment.getRelativeEnchantment())) {
				return enchantment.isEnabled();
			}
		}
		return false;
	}
}
