package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.custom.Angler;
import org.ctp.enchantmentsolution.enchantments.custom.Beheading;
import org.ctp.enchantmentsolution.enchantments.custom.Brine;
import org.ctp.enchantmentsolution.enchantments.custom.Drowned;
import org.ctp.enchantmentsolution.enchantments.custom.ExpShare;
import org.ctp.enchantmentsolution.enchantments.custom.FlowerGift;
import org.ctp.enchantmentsolution.enchantments.custom.FrequentFlyer;
import org.ctp.enchantmentsolution.enchantments.custom.Fried;
import org.ctp.enchantmentsolution.enchantments.custom.GoldDigger;
import org.ctp.enchantmentsolution.enchantments.custom.HardBounce;
import org.ctp.enchantmentsolution.enchantments.custom.HeightPlusPlus;
import org.ctp.enchantmentsolution.enchantments.custom.Icarus;
import org.ctp.enchantmentsolution.enchantments.custom.IronDefense;
import org.ctp.enchantmentsolution.enchantments.custom.KnockUp;
import org.ctp.enchantmentsolution.enchantments.custom.Life;
import org.ctp.enchantmentsolution.enchantments.custom.MagicGuard;
import org.ctp.enchantmentsolution.enchantments.custom.MagmaWalker;
import org.ctp.enchantmentsolution.enchantments.custom.NoRest;
import org.ctp.enchantmentsolution.enchantments.custom.Pillage;
import org.ctp.enchantmentsolution.enchantments.custom.Sacrifice;
import org.ctp.enchantmentsolution.enchantments.custom.SandVeil;
import org.ctp.enchantmentsolution.enchantments.custom.ShockAspect;
import org.ctp.enchantmentsolution.enchantments.custom.Smeltery;
import org.ctp.enchantmentsolution.enchantments.custom.Sniper;
import org.ctp.enchantmentsolution.enchantments.custom.SoulReaper;
import org.ctp.enchantmentsolution.enchantments.custom.Soulbound;
import org.ctp.enchantmentsolution.enchantments.custom.SplatterFest;
import org.ctp.enchantmentsolution.enchantments.custom.StoneThrow;
import org.ctp.enchantmentsolution.enchantments.custom.Tank;
import org.ctp.enchantmentsolution.enchantments.custom.Telepathy;
import org.ctp.enchantmentsolution.enchantments.custom.Transmutation;
import org.ctp.enchantmentsolution.enchantments.custom.Unrest;
import org.ctp.enchantmentsolution.enchantments.custom.VoidWalker;
import org.ctp.enchantmentsolution.enchantments.custom.Warp;
import org.ctp.enchantmentsolution.enchantments.custom.WidthPlusPlus;
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
import org.ctp.enchantmentsolution.enchantments.vanilla.Multishot;
import org.ctp.enchantmentsolution.enchantments.vanilla.Piercing;
import org.ctp.enchantmentsolution.enchantments.vanilla.Power;
import org.ctp.enchantmentsolution.enchantments.vanilla.ProjectileProtection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Protection;
import org.ctp.enchantmentsolution.enchantments.vanilla.Punch;
import org.ctp.enchantmentsolution.enchantments.vanilla.QuickCharge;
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
import org.ctp.enchantmentsolution.enchantments.wrappers.FlowerGiftWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.FrequentFlyerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.FriedWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.GoldDiggerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.HardBounceWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.HeightPlusPlusWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.IcarusWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.IronDefenseWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.KnockUpWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.LifeWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.MagicGuardWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.MagmaWalkerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.NoRestWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.PillageWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SacrificeWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SandVeilWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.ShockAspectWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SmelteryWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SniperWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SoulReaperWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SoulboundWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.SplatterFestWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.StoneThrowWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.TankWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.TelepathyWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.TransmutationWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.UnrestWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.VoidWalkerWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.WarpWrapper;
import org.ctp.enchantmentsolution.enchantments.wrappers.WidthPlusPlusWrapper;
import org.ctp.enchantmentsolution.utils.ChatUtils;
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
	public static final Enchantment UNREST = new UnrestWrapper();
	public static final Enchantment NO_REST = new NoRestWrapper();
	public static final Enchantment WIDTH_PLUS_PLUS = new WidthPlusPlusWrapper();
	public static final Enchantment HEIGHT_PLUS_PLUS = new HeightPlusPlusWrapper();
	public static final Enchantment VOID_WALKER = new VoidWalkerWrapper();
	public static final Enchantment ICARUS = new IcarusWrapper();
	public static final Enchantment IRON_DEFENSE = new IronDefenseWrapper();
	public static final Enchantment HARD_BOUNCE = new HardBounceWrapper();
	public static final Enchantment MAGIC_GUARD = new MagicGuardWrapper();
	public static final Enchantment SPLATTER_FEST = new SplatterFestWrapper();
	public static final Enchantment SAND_VEIL = new SandVeilWrapper();
	public static final Enchantment TRANSMUTATION = new TransmutationWrapper();
	public static final Enchantment GOLD_DIGGER = new GoldDiggerWrapper();
	public static final Enchantment FLOWER_GIFT = new FlowerGiftWrapper();
	public static final Enchantment STONE_THROW = new StoneThrowWrapper();
	public static final Enchantment PILLAGE = new PillageWrapper();

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
				YamlConfig language = ConfigFiles.getLanguageFile();
				String namespace = "default_enchantments";
				if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchantment.getName() + " (Display Name " + enchantment.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to set.");
						continue;
					}
					namespace = plugin.getName();
					ENCHANTMENTS.get(i).setDisplayName(advanced.getString(namespace+"."+enchantment.getName()+".display_name"));
				} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
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
				String description = StringEscapeUtils.unescapeJava(language.getString("enchantment.descriptions."+namespace+"."+enchantment.getName()));
				Weight weight = Weight.getWeight(advanced.getString(namespace+"."+enchantment.getName()+".weight"));
				List<String> conflictingEnchantmentsString = advanced.getStringList(namespace+"."+enchantment.getName()+".conflicting_enchantments");
				List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
				for(String s : conflictingEnchantmentsString) {
					CustomEnchantment enchant = getByName(s);
					if(enchant != null) {
						conflictingEnchantments.add(enchant.getRelativeEnchantment());
					}
				}
				List<String> disabledItemsString = advanced.getStringList(namespace+"."+enchantment.getName()+".disabled_items");
				List<Material> disabledItems = new ArrayList<Material>();
				for(String s : disabledItemsString) {
					Material mat = Material.getMaterial(s);
					if(mat != null) {
						disabledItems.add(mat);
					}
				}
				ENCHANTMENTS.get(i).setCustom(constant, modifier, maxConstant, startLevel, maxLevel, weight);
				ENCHANTMENTS.get(i).setConflictingEnchantments(conflictingEnchantments);
				ENCHANTMENTS.get(i).setDisabledItems(disabledItems);
				ENCHANTMENTS.get(i).setDescription(description);
			} else {
				CustomEnchantment enchantment = ENCHANTMENTS.get(i);
				YamlConfig config = ConfigFiles.getEnchantmentConfig();
				YamlConfig language = ConfigFiles.getLanguageFile();
				String description = "";
				if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchantment.getName() + " (Display Name " + enchantment.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to set.");
						continue;
					}
					String namespace = plugin.getName();
					if(Enchantments.addEnchantment(enchantment)) {
						if (config.getBoolean(namespace+"."+enchantment.getName()+".enabled")) {
							ENCHANTMENTS.get(i).setEnabled(true);
						} else {
							ENCHANTMENTS.get(i).setEnabled(false);
						}
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
					if (ConfigFiles.getEnchantmentConfig().getBoolean(namespace+"."+enchantment.getName()+".treasure")) {
						ENCHANTMENTS.get(i).setTreasure(true);
					}
					description = StringEscapeUtils.unescapeJava(language.getString("enchantment.descriptions."+namespace+"."+enchantment.getName()));
				} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
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
					description = StringEscapeUtils.unescapeJava(language.getString("enchantment.descriptions.custom_enchantments."+enchantment.getName()));
				} else {
					if(Enchantments.addEnchantment(enchantment)) {
						ENCHANTMENTS.get(i).setEnabled(true);
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
					description = StringEscapeUtils.unescapeJava(language.getString("enchantment.descriptions.default_enchantments."+enchantment.getName()));
				}
				if(ConfigFiles.useLevel50()) {
					ENCHANTMENTS.get(i).setLevelFifty();
				} else {
					ENCHANTMENTS.get(i).setLevelThirty();
				}
				ENCHANTMENTS.get(i).setConflictingEnchantments();
				ENCHANTMENTS.get(i).setDisabledItems(Arrays.asList());
				ENCHANTMENTS.get(i).setDescription(description);
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
		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new Multishot());
			DefaultEnchantments.addDefaultEnchantment(new Piercing());
			DefaultEnchantments.addDefaultEnchantment(new QuickCharge());
		}
		
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
		DefaultEnchantments.addDefaultEnchantment(new Unrest());
		DefaultEnchantments.addDefaultEnchantment(new NoRest());
		DefaultEnchantments.addDefaultEnchantment(new WidthPlusPlus());
		DefaultEnchantments.addDefaultEnchantment(new HeightPlusPlus());
		DefaultEnchantments.addDefaultEnchantment(new VoidWalker());
		DefaultEnchantments.addDefaultEnchantment(new Icarus());
		DefaultEnchantments.addDefaultEnchantment(new IronDefense());
		DefaultEnchantments.addDefaultEnchantment(new HardBounce());
		DefaultEnchantments.addDefaultEnchantment(new MagicGuard());
		DefaultEnchantments.addDefaultEnchantment(new SplatterFest());
		DefaultEnchantments.addDefaultEnchantment(new SandVeil());
		DefaultEnchantments.addDefaultEnchantment(new Transmutation());
		DefaultEnchantments.addDefaultEnchantment(new GoldDigger());
		DefaultEnchantments.addDefaultEnchantment(new FlowerGift());
		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new StoneThrow());
			DefaultEnchantments.addDefaultEnchantment(new Pillage());
		}
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
