package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
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
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class DefaultEnchantments {

	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	
	public static final Enchantment SOULBOUND = new CustomEnchantmentWrapper("soulbound", "SOULBOUND");
	public static final Enchantment SOUL_REAPER = new CustomEnchantmentWrapper("soul_reaper", "SOUL_REAPER");
	public static final Enchantment SHOCK_ASPECT = new CustomEnchantmentWrapper("shock_aspect", "SHOCK_ASPECT");
	public static final Enchantment LIFE = new CustomEnchantmentWrapper("life", "LIFE");
	public static final Enchantment BEHEADING = new CustomEnchantmentWrapper("beheading", "BEHEADING");
	public static final Enchantment KNOCKUP = new CustomEnchantmentWrapper("knockip", "KNOCKUP");
	public static final Enchantment WARP = new CustomEnchantmentWrapper("warp", "WARP");
	public static final Enchantment EXP_SHARE = new CustomEnchantmentWrapper("exp_share", "EXP_SHARE");
	public static final Enchantment MAGMA_WALKER = new CustomEnchantmentWrapper("magma_walker", "MAGMA_WALKER");
	public static final Enchantment SNIPER = new CustomEnchantmentWrapper("sniper", "SNIPER");
	public static final Enchantment TELEPATHY = new CustomEnchantmentWrapper("telepathy", "TELEPATHY");
	public static final Enchantment SMELTERY = new CustomEnchantmentWrapper("smeltery", "SMELTERY");
	public static final Enchantment SACRIFICE = new CustomEnchantmentWrapper("sacrifice", "SACRIFICE");
	public static final Enchantment ANGLER = new CustomEnchantmentWrapper("angler", "ANGLER");
	public static final Enchantment FRIED = new CustomEnchantmentWrapper("fried", "FRIED");
	public static final Enchantment FREQUENT_FLYER = new CustomEnchantmentWrapper("frequent_flyer", "FREQUENT_FLYER");
	public static final Enchantment TANK = new CustomEnchantmentWrapper("tank", "TANK");
	public static final Enchantment BRINE = new CustomEnchantmentWrapper("brine", "BRINE");
	public static final Enchantment DROWNED = new CustomEnchantmentWrapper("drowned", "DROWNED");
	public static final Enchantment UNREST = new CustomEnchantmentWrapper("unrest", "UNREST");
	public static final Enchantment NO_REST = new CustomEnchantmentWrapper("no_rest", "NO_REST");
	public static final Enchantment WIDTH_PLUS_PLUS = new CustomEnchantmentWrapper("width_plus_plus", "WIDTH_PLUS_PLUS");
	public static final Enchantment HEIGHT_PLUS_PLUS = new CustomEnchantmentWrapper("height_plus_plus", "HEIGHT_PLUS_PLUS");
	public static final Enchantment VOID_WALKER = new CustomEnchantmentWrapper("void_walker", "VOID_WALKER");
	public static final Enchantment ICARUS = new CustomEnchantmentWrapper("icarus", "ICARUS");
	public static final Enchantment IRON_DEFENSE = new CustomEnchantmentWrapper("iron_defense", "IRON_DEFENSE");
	public static final Enchantment HARD_BOUNCE = new CustomEnchantmentWrapper("hard_bounce", "HARD_BOUNCE");
	public static final Enchantment MAGIC_GUARD = new CustomEnchantmentWrapper("magic_guard", "MAGIC_GUARD");
	public static final Enchantment SPLATTER_FEST = new CustomEnchantmentWrapper("splatter_fest", "SPLATTER_FEST");
	public static final Enchantment SAND_VEIL = new CustomEnchantmentWrapper("sand_veil", "SAND_VEIL");
	public static final Enchantment TRANSMUTATION = new CustomEnchantmentWrapper("transmutation", "TRANSMUTATION");
	public static final Enchantment GOLD_DIGGER = new CustomEnchantmentWrapper("gold_digger", "GOLD_DIGGER");
	public static final Enchantment FLOWER_GIFT = new CustomEnchantmentWrapper("flower_gift", "FLOWER_GIFT");
	public static final Enchantment STONE_THROW = new CustomEnchantmentWrapper("stone_throw", "STONE_THROW");
	public static final Enchantment PILLAGE = new CustomEnchantmentWrapper("pillage", "PILLAGE");
	public static final Enchantment GUNG_HO = new CustomEnchantmentWrapper("gung_ho", "GUNG_HO");
	public static final Enchantment WAND = new CustomEnchantmentWrapper("wand", "WAND");
	public static final Enchantment MOISTURIZE = new CustomEnchantmentWrapper("moisturize", "MOISTURIZE");
	public static final Enchantment IRENES_LASSO = new CustomEnchantmentWrapper("irenes_lasso", "LASSO_OF_IRENE");
	public static final Enchantment CURSE_OF_LAG = new CustomEnchantmentWrapper("lagging_curse", "LAGGING_CURSE");
	public static final Enchantment CURSE_OF_EXHAUSTION = new CustomEnchantmentWrapper("exhaustion_curse", "EXHAUSTION_CURSE");
	public static final Enchantment QUICK_STRIKE = new CustomEnchantmentWrapper("quick_strike", "QUICK_STRIKE");
	public static final Enchantment TOUGHNESS = new CustomEnchantmentWrapper("toughness", "TOUGHNESS");
	public static final Enchantment ARMORED = new CustomEnchantmentWrapper("armored", "ARMORED");

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
			CustomEnchantment enchantment = ENCHANTMENTS.get(i);
			YamlConfig config = null;
			boolean advanced = false;
			YamlConfig language = files.getLanguageFile();
			if(ConfigUtils.useAdvancedFile()) {
				config = files.getEnchantmentAdvancedConfig();
				advanced = true;
			} else {
				config = files.getEnchantmentConfig();
			}
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
			if(!advanced && !(enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper)) {
				if(Enchantments.addEnchantment(enchantment)) {
					ENCHANTMENTS.get(i).setEnabled(true);
				} else {
					ENCHANTMENTS.get(i).setEnabled(false);
				}
				String description = StringUtils.decodeString(language.getString("enchantment.descriptions.default_enchantments."+enchantment.getName()));
				ENCHANTMENTS.get(i).setDescription(description);
				if(ConfigUtils.isLevel50()) {
					ENCHANTMENTS.get(i).setLevelFifty();
				} else {
					ENCHANTMENTS.get(i).setLevelThirty();
				}
				continue;
			}
			
			if(Enchantments.addEnchantment(enchantment)) {
				if (config.getBoolean(namespace+"."+enchantment.getName()+".enabled")) {
					ENCHANTMENTS.get(i).setEnabled(true);
				} else {
					ENCHANTMENTS.get(i).setEnabled(false);
				}
			} else {
				ENCHANTMENTS.get(i).setEnabled(false);
			}
			if (config.getBoolean(namespace+"."+enchantment.getName()+".treasure")) {
				ENCHANTMENTS.get(i).setTreasure(true);
			} else {
				ENCHANTMENTS.get(i).setTreasure(false);
			}
			String displayName = StringUtils.decodeString(language.getString("enchantment.display_names."+namespace+"."+enchantment.getName()));
			String description = StringUtils.decodeString(language.getString("enchantment.descriptions."+namespace+"."+enchantment.getName()));
			if(advanced) {
				int constant = config.getInt(namespace+"."+enchantment.getName()+".enchantability_constant");
				int modifier = config.getInt(namespace+"."+enchantment.getName()+".enchantability_modifier");
				int startLevel = config.getInt(namespace+"."+enchantment.getName()+".enchantability_start_level");
				int maxLevel = config.getInt(namespace+"."+enchantment.getName()+".enchantability_max_level");
				Weight weight = Weight.getWeight(config.getString(namespace+"."+enchantment.getName()+".weight"));
				List<String> conflictingEnchantmentsString = config.getStringList(namespace+"."+enchantment.getName()+".conflicting_enchantments");
				List<Enchantment> conflictingEnchantments = new ArrayList<Enchantment>();
				if(conflictingEnchantmentsString != null) {
					for(String s : conflictingEnchantmentsString) {
						CustomEnchantment enchant = getByName(s);
						if(enchant != null) {
							conflictingEnchantments.add(enchant.getRelativeEnchantment());
						}
					}
				}
				List<String> disabledItemsString = config.getStringList(namespace+"."+enchantment.getName()+".disabled_items");
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
			} else {
				if(ConfigUtils.isLevel50()) {
					ENCHANTMENTS.get(i).setLevelFifty();
				} else {
					ENCHANTMENTS.get(i).setLevelThirty();
				}
			}
			if(!namespace.equals("default_enchantments")) {
				ENCHANTMENTS.get(i).setDisplayName(displayName);
			} else {
				ENCHANTMENTS.get(i).setDisplayName(ConfigUtils.getLanguage());
			}
			ENCHANTMENTS.get(i).setDescription(description);
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
		addDefaultEnchantment(new AquaAffinity());
		addDefaultEnchantment(new BaneOfArthropods());
		addDefaultEnchantment(new BlastProtection());
		addDefaultEnchantment(new Channeling());
		addDefaultEnchantment(new CurseOfBinding());
		addDefaultEnchantment(new CurseOfVanishing());
		addDefaultEnchantment(new DepthStrider());
		addDefaultEnchantment(new Efficiency());
		addDefaultEnchantment(new FeatherFalling());
		addDefaultEnchantment(new FireAspect());
		addDefaultEnchantment(new FireProtection());
		addDefaultEnchantment(new Flame());
		addDefaultEnchantment(new Fortune());
		addDefaultEnchantment(new FrostWalker());
		addDefaultEnchantment(new Impaling());
		addDefaultEnchantment(new Infinity());
		addDefaultEnchantment(new Knockback());
		addDefaultEnchantment(new Looting());
		addDefaultEnchantment(new Loyalty());
		addDefaultEnchantment(new LuckOfTheSea());
		addDefaultEnchantment(new Lure());
		addDefaultEnchantment(new Mending());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			addDefaultEnchantment(new Multishot());
			addDefaultEnchantment(new Piercing());
		}
		addDefaultEnchantment(new Power());
		addDefaultEnchantment(new ProjectileProtection());
		addDefaultEnchantment(new Protection());
		addDefaultEnchantment(new Punch());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			addDefaultEnchantment(new QuickCharge());
		}
		addDefaultEnchantment(new Respiration());
		addDefaultEnchantment(new Riptide());
		addDefaultEnchantment(new Sharpness());
		addDefaultEnchantment(new SilkTouch());
		addDefaultEnchantment(new Smite());
		addDefaultEnchantment(new SweepingEdge());
		addDefaultEnchantment(new Thorns());
		addDefaultEnchantment(new Unbreaking());

		addDefaultEnchantment(new Angler());
		addDefaultEnchantment(new Armored());
		addDefaultEnchantment(new Beheading());
		addDefaultEnchantment(new Brine());
		addDefaultEnchantment(new CurseOfExhaustion());
		addDefaultEnchantment(new CurseOfLag());
		addDefaultEnchantment(new Drowned());
		addDefaultEnchantment(new ExpShare());
		addDefaultEnchantment(new FlowerGift());
		addDefaultEnchantment(new FrequentFlyer());
		addDefaultEnchantment(new Fried());
		addDefaultEnchantment(new GoldDigger());
		addDefaultEnchantment(new GungHo());
		addDefaultEnchantment(new HardBounce());
		addDefaultEnchantment(new HeightPlusPlus());
		addDefaultEnchantment(new Icarus());
		addDefaultEnchantment(new IrenesLasso());
		addDefaultEnchantment(new IronDefense());
		addDefaultEnchantment(new KnockUp());
		addDefaultEnchantment(new Life());
		addDefaultEnchantment(new MagicGuard());
		addDefaultEnchantment(new MagmaWalker());
		addDefaultEnchantment(new Moisturize());
		addDefaultEnchantment(new NoRest());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			addDefaultEnchantment(new Pillage());
		}
		addDefaultEnchantment(new QuickStrike());
		addDefaultEnchantment(new Sacrifice());
		addDefaultEnchantment(new SandVeil());
		addDefaultEnchantment(new ShockAspect());
		addDefaultEnchantment(new Smeltery());
		addDefaultEnchantment(new Sniper());
		addDefaultEnchantment(new Soulbound());
		addDefaultEnchantment(new SoulReaper());
		addDefaultEnchantment(new SplatterFest());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			addDefaultEnchantment(new StoneThrow());
		}
		addDefaultEnchantment(new Tank());
		addDefaultEnchantment(new Telepathy());
		addDefaultEnchantment(new Toughness());
		addDefaultEnchantment(new Transmutation());
		addDefaultEnchantment(new Unrest());
		addDefaultEnchantment(new VoidWalker());
		addDefaultEnchantment(new Wand());
		addDefaultEnchantment(new Warp());
		addDefaultEnchantment(new WidthPlusPlus());
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
