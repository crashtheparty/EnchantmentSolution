package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.enchantments.custom.*;
import org.ctp.enchantmentsolution.enchantments.vanilla.*;
import org.ctp.enchantmentsolution.enchantments.wrappers.*;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class DefaultEnchantments {

	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	
	public static final Enchantment SOULBOUND = new SoulboundWrapper();
	public static final Enchantment SOUL_REAPER = new SoulReaperWrapper();
	public static final Enchantment SHOCK_ASPECT = new ShockAspectWrapper();
	public static final Enchantment LIFE = new LifeWrapper();
	public static final Enchantment BEHEADING = new BeheadingWrapper();
	public static final Enchantment KNOCKUP = new KnockUpWrapper();
	public static final Enchantment WARP = new WarpWrapper();
	public static final Enchantment EXP_SHARE = new ExpShareWrapper();
	public static final Enchantment MAGMA_WALKER = new MagmaWalkerWrapper();
	public static final Enchantment SNIPER = new SniperWrapper();
	public static final Enchantment TELEPATHY = new TelepathyWrapper();
	public static final Enchantment SMELTERY = new SmelteryWrapper();
	public static final Enchantment SACRIFICE = new SacrificeWrapper();
	public static final Enchantment ANGLER = new AnglerWrapper();
	public static final Enchantment FRIED = new FriedWrapper();
	public static final Enchantment FREQUENT_FLYER = new FrequentFlyerWrapper();
	public static final Enchantment TANK = new TankWrapper();
	public static final Enchantment BRINE = new BrineWrapper();
	public static final Enchantment DROWNED = new DrownedWrapper();
	public static final Enchantment CURSE_OF_LAG = new CurseOfLagWrapper();

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
	
	public static void setEnchantments() {
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
				if(Enchantments.addEnchantment(enchantment)) {
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
					if(Enchantments.addEnchantment(enchantment)) {
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
					if(Enchantments.addEnchantment(enchantment)) {
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

		DefaultEnchantments.addDefaultEnchantment(new Angler());
		DefaultEnchantments.addDefaultEnchantment(new Beheading());
		DefaultEnchantments.addDefaultEnchantment(new Brine());
		DefaultEnchantments.addDefaultEnchantment(new Drowned());
		DefaultEnchantments.addDefaultEnchantment(new ExpShare());
		DefaultEnchantments.addDefaultEnchantment(new FrequentFlyer());
		DefaultEnchantments.addDefaultEnchantment(new Fried());
		DefaultEnchantments.addDefaultEnchantment(new KnockUp());
		DefaultEnchantments.addDefaultEnchantment(new Life());
		DefaultEnchantments.addDefaultEnchantment(new MagmaWalker());
		DefaultEnchantments.addDefaultEnchantment(new Sacrifice());
		DefaultEnchantments.addDefaultEnchantment(new ShockAspect());
		DefaultEnchantments.addDefaultEnchantment(new Smeltery());
		DefaultEnchantments.addDefaultEnchantment(new Sniper());
		DefaultEnchantments.addDefaultEnchantment(new Soulbound());
		DefaultEnchantments.addDefaultEnchantment(new SoulReaper());
		DefaultEnchantments.addDefaultEnchantment(new Tank());
		DefaultEnchantments.addDefaultEnchantment(new Telepathy());
		DefaultEnchantments.addDefaultEnchantment(new Warp());
	}
	
	public static List<String> getEnchantmentNames(){
		List<String> names = new ArrayList<String>();
		for(CustomEnchantment enchant : getEnchantments()) {
			names.add(enchant.getName());
		}
		return names;
	}
	
	public static CustomEnchantment getByName(String name) {
		for(CustomEnchantment enchant : getEnchantments()) {
			if(enchant.getName().equalsIgnoreCase(name)) {
				return enchant;
			}
		}
		return null;
	}
}
