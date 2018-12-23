package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.custom.Angler;
import org.ctp.enchantmentsolution.enchantments.custom.Beheading;
import org.ctp.enchantmentsolution.enchantments.custom.Brine;
import org.ctp.enchantmentsolution.enchantments.custom.Drowned;
import org.ctp.enchantmentsolution.enchantments.custom.ExpShare;
import org.ctp.enchantmentsolution.enchantments.custom.FrequentFlyer;
import org.ctp.enchantmentsolution.enchantments.custom.Fried;
import org.ctp.enchantmentsolution.enchantments.custom.KnockUp;
import org.ctp.enchantmentsolution.enchantments.custom.Life;
import org.ctp.enchantmentsolution.enchantments.custom.MagmaWalker;
import org.ctp.enchantmentsolution.enchantments.custom.Sacrifice;
import org.ctp.enchantmentsolution.enchantments.custom.ShockAspect;
import org.ctp.enchantmentsolution.enchantments.custom.Smeltery;
import org.ctp.enchantmentsolution.enchantments.custom.Sniper;
import org.ctp.enchantmentsolution.enchantments.custom.SoulReaper;
import org.ctp.enchantmentsolution.enchantments.custom.Soulbound;
import org.ctp.enchantmentsolution.enchantments.custom.Tank;
import org.ctp.enchantmentsolution.enchantments.custom.Telepathy;
import org.ctp.enchantmentsolution.enchantments.custom.Warp;
import org.ctp.enchantmentsolution.enchantments.vanilla.AquaAffinity;
import org.ctp.enchantmentsolution.enchantments.vanilla.BaneOfArthropods;
import org.ctp.enchantmentsolution.enchantments.vanilla.BlastProtection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Channeling;
import org.ctp.enchantmentsolution.enchantments.vanilla.CurseOfBinding;
import org.ctp.enchantmentsolution.enchantments.vanilla.CurseOfVanishing;
import org.ctp.enchantmentsolution.enchantments.vanilla.DepthStrider;
import org.ctp.enchantmentsolution.enchantments.vanilla.Efficiency;
import org.ctp.enchantmentsolution.enchantments.vanilla.FeatherFalling;
import org.ctp.enchantmentsolution.enchantments.vanilla.FireAspect;
import org.ctp.enchantmentsolution.enchantments.vanilla.FireProtection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Flame;
import org.ctp.enchantmentsolution.enchantments.vanilla.Fortune;
import org.ctp.enchantmentsolution.enchantments.vanilla.FrostWalker;
import org.ctp.enchantmentsolution.enchantments.vanilla.Impaling;
import org.ctp.enchantmentsolution.enchantments.vanilla.Infinity;
import org.ctp.enchantmentsolution.enchantments.vanilla.Knockback;
import org.ctp.enchantmentsolution.enchantments.vanilla.Looting;
import org.ctp.enchantmentsolution.enchantments.vanilla.Loyalty;
import org.ctp.enchantmentsolution.enchantments.vanilla.LuckOfTheSea;
import org.ctp.enchantmentsolution.enchantments.vanilla.Lure;
import org.ctp.enchantmentsolution.enchantments.vanilla.Mending;
import org.ctp.enchantmentsolution.enchantments.vanilla.Power;
import org.ctp.enchantmentsolution.enchantments.vanilla.ProjectileProtection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Protection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Punch;
import org.ctp.enchantmentsolution.enchantments.vanilla.Respiration;
import org.ctp.enchantmentsolution.enchantments.vanilla.Riptide;
import org.ctp.enchantmentsolution.enchantments.vanilla.Sharpness;
import org.ctp.enchantmentsolution.enchantments.vanilla.SilkTouch;
import org.ctp.enchantmentsolution.enchantments.vanilla.Smite;
import org.ctp.enchantmentsolution.enchantments.vanilla.SweepingEdge;
import org.ctp.enchantmentsolution.enchantments.vanilla.Thorns;
import org.ctp.enchantmentsolution.enchantments.vanilla.Unbreaking;
import org.ctp.enchantmentsolution.enchantments.wrappers.AnglerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.BeheadingWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.BrineWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.DrownedWrapper;
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
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class DefaultEnchantments {
	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	
	public static Enchantment SOULBOUND = new SoulboundWrapper();
	public static Enchantment SOUL_REAPER = new SoulReaperWrapper();
	public static Enchantment SHOCK_ASPECT = new ShockAspectWrapper();
	public static Enchantment LIFE = new LifeWrapper();
	public static Enchantment BEHEADING = new BeheadingWrapper();
	public static Enchantment KNOCKUP = new KnockUpWrapper();
	public static Enchantment WARP = new WarpWrapper();
	public static Enchantment EXP_SHARE = new ExpShareWrapper();
	public static Enchantment MAGMA_WALKER = new MagmaWalkerWrapper();
	public static Enchantment SNIPER = new SniperWrapper();
	public static Enchantment TELEPATHY = new TelepathyWrapper();
	public static Enchantment SMELTERY = new SmelteryWrapper();
	public static Enchantment SACRIFICE = new SacrificeWrapper();
	public static Enchantment ANGLER = new AnglerWrapper();
	public static Enchantment FRIED = new FriedWrapper();
	public static Enchantment FREQUENT_FLYER = new FrequentFlyerWrapper();
	public static Enchantment TANK = new TankWrapper();
	public static Enchantment BRINE = new BrineWrapper();
	public static Enchantment DROWNED = new DrownedWrapper();

	public static List<CustomEnchantment> getEnchantments() {
		return ENCHANTMENTS;
	}
	
	public static CustomEnchantment getCustomEnchantment(Enchantment enchant) {
		for(CustomEnchantment enchantment : ENCHANTMENTS) {
			if(enchant.equals(enchantment.getRelativeEnchantment())) {
				if(!enchantment.isEnabled()) {
					return null;
				}
				return enchantment;
			}
		}
		return null;
	}
	
	public static void addDefaultEnchantment(CustomEnchantment enchant) {
		ENCHANTMENTS.add(enchant);
	}
	
	public static void setEnchantments(boolean startup) {
		for (int i = 0; i < ENCHANTMENTS.size(); i++) {
			if(ConfigFiles.getDefaultConfig().getBoolean("use_advanced_file")) {
				CustomEnchantment enchantment = ENCHANTMENTS.get(i);
				YamlConfig advanced = ConfigFiles.getEnchantmentAdvancedConfig();
				String namespace = "default_enchantments";
				if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					namespace = "custom_enchantments";
					ENCHANTMENTS.get(i).setDisplayName(advanced.getString(namespace+"."+enchantment.getName()+".display_name"));
				} else {
					ENCHANTMENTS.get(i).setDisplayName(ENCHANTMENTS.get(i).getDefaultDisplayName());
				}
				if(Enchantments.addEnchantment(enchantment, startup)) {
					if (advanced.getBoolean(namespace+"."+enchantment.getName()+".enabled")) {
						ENCHANTMENTS.get(i).setEnabled(true);
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
				} else {
					ENCHANTMENTS.get(i).setEnabled(false);
				}
				if (advanced.getBoolean(namespace+"."+enchantment.getName()+".treasure")) {
					ENCHANTMENTS.get(i).setTreasure(true);
				} else {
					ENCHANTMENTS.get(i).setTreasure(false);
				}
				int constant = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_constant");
				int modifier = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_modifier");
				int maxConstant = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_max_constant");
				int startLevel = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_start_level");
				int maxLevel = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_max_level");
				Weight weight = Weight.getWeight(advanced.getString(namespace+"."+enchantment.getName()+".weight"));
				ENCHANTMENTS.get(i).setCustom(constant, modifier, maxConstant, startLevel, maxLevel, weight);
			} else {
				CustomEnchantment enchantment = ENCHANTMENTS.get(i);
				if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					YamlConfig config = ConfigFiles.getEnchantmentConfig();
					if(Enchantments.addEnchantment(enchantment, startup)) {
						if (config.getBoolean("custom_enchantments."+enchantment.getName()+".enabled")) {
							ENCHANTMENTS.get(i).setEnabled(true);
						} else {
							ENCHANTMENTS.get(i).setEnabled(false);
						}
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
					if (ConfigFiles.getEnchantmentConfig().getBoolean("custom_enchantments."+enchantment.getName()+".treasure")) {
						ENCHANTMENTS.get(i).setTreasure(true);
					}
				} else {
					if(Enchantments.addEnchantment(enchantment, startup)) {
						ENCHANTMENTS.get(i).setEnabled(true);
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
				}
				if(ConfigFiles.useLevel50()) {
					ENCHANTMENTS.get(i).setLevelFifty();
				} else {
					ENCHANTMENTS.get(i).setLevelThirty();
				}
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
	
	public static void addDefaultEnchantments() {
		if(getEnchantments().size() > 0) return;
		DefaultEnchantments.addDefaultEnchantment(new AquaAffinity());
		DefaultEnchantments.addDefaultEnchantment(new BaneOfArthropods());
		DefaultEnchantments.addDefaultEnchantment(new BlastProtection());
		DefaultEnchantments.addDefaultEnchantment(new Channeling());
		DefaultEnchantments.addDefaultEnchantment(new CurseOfBinding());
		DefaultEnchantments.addDefaultEnchantment(new CurseOfVanishing());
		DefaultEnchantments.addDefaultEnchantment(new DepthStrider());
		DefaultEnchantments.addDefaultEnchantment(new Efficiency());
		DefaultEnchantments.addDefaultEnchantment(new FeatherFalling());
		DefaultEnchantments.addDefaultEnchantment(new FireAspect());
		DefaultEnchantments.addDefaultEnchantment(new FireProtection());
		DefaultEnchantments.addDefaultEnchantment(new Flame());
		DefaultEnchantments.addDefaultEnchantment(new Fortune());
		DefaultEnchantments.addDefaultEnchantment(new FrostWalker());
		DefaultEnchantments.addDefaultEnchantment(new Impaling());
		DefaultEnchantments.addDefaultEnchantment(new Infinity());
		DefaultEnchantments.addDefaultEnchantment(new Knockback());
		DefaultEnchantments.addDefaultEnchantment(new Looting());
		DefaultEnchantments.addDefaultEnchantment(new Loyalty());
		DefaultEnchantments.addDefaultEnchantment(new LuckOfTheSea());
		DefaultEnchantments.addDefaultEnchantment(new Lure());
		DefaultEnchantments.addDefaultEnchantment(new Mending());
		DefaultEnchantments.addDefaultEnchantment(new Power());
		DefaultEnchantments.addDefaultEnchantment(new ProjectileProtection());
		DefaultEnchantments.addDefaultEnchantment(new Protection());
		DefaultEnchantments.addDefaultEnchantment(new Punch());
		DefaultEnchantments.addDefaultEnchantment(new Respiration());
		DefaultEnchantments.addDefaultEnchantment(new Riptide());
		DefaultEnchantments.addDefaultEnchantment(new Sharpness());
		DefaultEnchantments.addDefaultEnchantment(new SilkTouch());
		DefaultEnchantments.addDefaultEnchantment(new Smite());
		DefaultEnchantments.addDefaultEnchantment(new SweepingEdge());
		DefaultEnchantments.addDefaultEnchantment(new Thorns());
		DefaultEnchantments.addDefaultEnchantment(new Unbreaking());
		
		DefaultEnchantments.addDefaultEnchantment(new Soulbound());
		DefaultEnchantments.addDefaultEnchantment(new SoulReaper());
		DefaultEnchantments.addDefaultEnchantment(new ShockAspect());
		DefaultEnchantments.addDefaultEnchantment(new Beheading());
		DefaultEnchantments.addDefaultEnchantment(new KnockUp());
		DefaultEnchantments.addDefaultEnchantment(new Life());
		DefaultEnchantments.addDefaultEnchantment(new Warp());
		DefaultEnchantments.addDefaultEnchantment(new ExpShare());
		DefaultEnchantments.addDefaultEnchantment(new Sniper());
		DefaultEnchantments.addDefaultEnchantment(new Telepathy());
		DefaultEnchantments.addDefaultEnchantment(new Smeltery());
		DefaultEnchantments.addDefaultEnchantment(new Sacrifice());
		DefaultEnchantments.addDefaultEnchantment(new Angler());
		DefaultEnchantments.addDefaultEnchantment(new Fried());
		DefaultEnchantments.addDefaultEnchantment(new FrequentFlyer());
		DefaultEnchantments.addDefaultEnchantment(new Tank());
		DefaultEnchantments.addDefaultEnchantment(new Brine());
		DefaultEnchantments.addDefaultEnchantment(new MagmaWalker());
		DefaultEnchantments.addDefaultEnchantment(new Drowned());
	}
}
