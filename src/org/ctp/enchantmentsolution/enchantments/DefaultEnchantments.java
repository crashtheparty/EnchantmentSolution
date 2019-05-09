package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.custom.*;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enchantments.vanilla.*;
import org.ctp.enchantmentsolution.enchantments.wrappers.*;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
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
	public static final Enchantment GUNG_HO = new GungHoWrapper();
	public static final Enchantment WAND = new WandWrapper();
	public static final Enchantment MOISTURIZE = new MoisturizeWrapper();
	public static final Enchantment IRENES_LASSO = new IrenesLassoWrapper();
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
		ConfigFiles files = EnchantmentSolution.getPlugin().getConfigFiles();
		for (int i = 0; i < ENCHANTMENTS.size(); i++) {
			if(files.getDefaultConfig().getBoolean("use_advanced_file")) {
				CustomEnchantment enchantment = ENCHANTMENTS.get(i);
				YamlConfig advanced = files.getEnchantmentAdvancedConfig();
				YamlConfig language = files.getLanguageFile();
				String namespace = "default_enchantments";
				if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchantment.getName() + " (Display Name " + enchantment.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to set.");
						continue;
					}
					namespace = plugin.getName().toLowerCase();
				} else if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
					namespace = "custom_enchantments";
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
				int startLevel = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_start_level");
				int maxLevel = advanced.getInt(namespace+"."+enchantment.getName()+".enchantability_max_level");
				String displayName = StringUtils.decodeString(language.getString("enchantment.display_names."+namespace+"."+enchantment.getName()));
				String description = StringUtils.decodeString(language.getString("enchantment.descriptions."+namespace+"."+enchantment.getName()));
				Weight weight = Weight.getWeight(advanced.getString(namespace+"."+enchantment.getName()+".weight"));
				List<String> conflictingEnchantmentsString = advanced.getStringList(namespace+"."+enchantment.getName()+".conflicting_enchantments");
				List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
				if(conflictingEnchantmentsString != null) {
					for(String s : conflictingEnchantmentsString) {
						CustomEnchantment enchant = getByName(s);
						if(enchant != null) {
							conflictingEnchantments.add(enchant.getRelativeEnchantment());
						}
					}
				}
				List<String> disabledItemsString = advanced.getStringList(namespace+"."+enchantment.getName()+".disabled_items");
				List<Material> disabledItems = new ArrayList<Material>();
				if(disabledItemsString != null) {
					for(String s : disabledItemsString) {
						Material mat = Material.getMaterial(s);
						if(mat != null) {
							disabledItems.add(mat);
						}
					}
				}
				ENCHANTMENTS.get(i).setCustom(constant, modifier, startLevel, maxLevel, weight);
				ENCHANTMENTS.get(i).setConflictingEnchantments(conflictingEnchantments);
				ENCHANTMENTS.get(i).setDisabledItems(disabledItems);
				if(!namespace.equals("default_enchantments")) {
					ENCHANTMENTS.get(i).setDisplayName(displayName);
				} else {
					ENCHANTMENTS.get(i).setDisplayName(ConfigUtils.getLanguage());
				}
				ENCHANTMENTS.get(i).setDescription(description);
			} else {
				CustomEnchantment enchantment = ENCHANTMENTS.get(i);
				YamlConfig config = files.getEnchantmentConfig();
				YamlConfig language = files.getLanguageFile();
				String description = "", displayName = null;
				if (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
					JavaPlugin plugin = ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin();
					if(plugin == null) {
						ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchantment.getName() + " (Display Name " + enchantment.getDisplayName() + ")"
								+ " does not have a JavaPlugin set. Refusing to set.");
						continue;
					}
					String namespace = plugin.getName().toLowerCase();
					if(Enchantments.addEnchantment(enchantment)) {
						if (config.getBoolean(namespace+"."+enchantment.getName()+".enabled")) {
							ENCHANTMENTS.get(i).setEnabled(true);
						} else {
							ENCHANTMENTS.get(i).setEnabled(false);
						}
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
					if (files.getEnchantmentConfig().getBoolean(namespace+"."+enchantment.getName()+".treasure")) {
						ENCHANTMENTS.get(i).setTreasure(true);
					}
					displayName = StringUtils.decodeString(language.getString("enchantment.display_names."+namespace+"."+enchantment.getName()));
					description = StringUtils.decodeString(language.getString("enchantment.descriptions."+namespace+"."+enchantment.getName()));
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
					if (files.getEnchantmentConfig().getBoolean("custom_enchantments."+enchantment.getName()+".treasure")) {
						ENCHANTMENTS.get(i).setTreasure(true);
					}
					displayName = StringUtils.decodeString(language.getString("enchantment.display_names.custom_enchantments."+enchantment.getName()));
					description = StringUtils.decodeString(language.getString("enchantment.descriptions.custom_enchantments."+enchantment.getName()));
				} else {
					if(Enchantments.addEnchantment(enchantment)) {
						ENCHANTMENTS.get(i).setEnabled(true);
					} else {
						ENCHANTMENTS.get(i).setEnabled(false);
					}
					description = StringUtils.decodeString(language.getString("enchantment.descriptions.default_enchantments."+enchantment.getName()));
				}
				if(ConfigUtils.useLevel50()) {
					ENCHANTMENTS.get(i).setLevelFifty();
				} else {
					ENCHANTMENTS.get(i).setLevelThirty();
				}
				ENCHANTMENTS.get(i).setConflictingEnchantments();
				ENCHANTMENTS.get(i).setDisabledItems(Arrays.asList());
				if(displayName != null) {
					ENCHANTMENTS.get(i).setDisplayName(displayName);
				} else {
					ENCHANTMENTS.get(i).setDisplayName(ConfigUtils.getLanguage());
				}
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
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new Multishot());
			DefaultEnchantments.addDefaultEnchantment(new Piercing());
		}
		DefaultEnchantments.addDefaultEnchantment(new Power());
		DefaultEnchantments.addDefaultEnchantment(new ProjectileProtection());
		DefaultEnchantments.addDefaultEnchantment(new Protection());
		DefaultEnchantments.addDefaultEnchantment(new Punch());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new QuickCharge());
		}
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
		DefaultEnchantments.addDefaultEnchantment(new CurseOfLag());
		DefaultEnchantments.addDefaultEnchantment(new Drowned());
		DefaultEnchantments.addDefaultEnchantment(new ExpShare());
		DefaultEnchantments.addDefaultEnchantment(new FlowerGift());
		DefaultEnchantments.addDefaultEnchantment(new FrequentFlyer());
		DefaultEnchantments.addDefaultEnchantment(new Fried());
		DefaultEnchantments.addDefaultEnchantment(new GoldDigger());
		DefaultEnchantments.addDefaultEnchantment(new GungHo());
		DefaultEnchantments.addDefaultEnchantment(new HardBounce());
		DefaultEnchantments.addDefaultEnchantment(new HeightPlusPlus());
		DefaultEnchantments.addDefaultEnchantment(new Icarus());
		DefaultEnchantments.addDefaultEnchantment(new IrenesLasso());
		DefaultEnchantments.addDefaultEnchantment(new IronDefense());
		DefaultEnchantments.addDefaultEnchantment(new KnockUp());
		DefaultEnchantments.addDefaultEnchantment(new Life());
		DefaultEnchantments.addDefaultEnchantment(new MagicGuard());
		DefaultEnchantments.addDefaultEnchantment(new MagmaWalker());
		DefaultEnchantments.addDefaultEnchantment(new Moisturize());
		DefaultEnchantments.addDefaultEnchantment(new NoRest());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new Pillage());
		}
		DefaultEnchantments.addDefaultEnchantment(new Sacrifice());
		DefaultEnchantments.addDefaultEnchantment(new SandVeil());
		DefaultEnchantments.addDefaultEnchantment(new ShockAspect());
		DefaultEnchantments.addDefaultEnchantment(new Smeltery());
		DefaultEnchantments.addDefaultEnchantment(new Sniper());
		DefaultEnchantments.addDefaultEnchantment(new Soulbound());
		DefaultEnchantments.addDefaultEnchantment(new SoulReaper());
		DefaultEnchantments.addDefaultEnchantment(new SplatterFest());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			DefaultEnchantments.addDefaultEnchantment(new StoneThrow());
		}
		DefaultEnchantments.addDefaultEnchantment(new Tank());
		DefaultEnchantments.addDefaultEnchantment(new Telepathy());
		DefaultEnchantments.addDefaultEnchantment(new Transmutation());
		DefaultEnchantments.addDefaultEnchantment(new Unrest());
		DefaultEnchantments.addDefaultEnchantment(new VoidWalker());
		DefaultEnchantments.addDefaultEnchantment(new Wand());
		DefaultEnchantments.addDefaultEnchantment(new Warp());
		DefaultEnchantments.addDefaultEnchantment(new WidthPlusPlus());
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
