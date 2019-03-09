package org.ctp.enchantmentsolution.enchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class Enchantments {

	private static List<CustomEnchantment> ENCHANTMENTS = new ArrayList<CustomEnchantment>();
	private static int LAPIS_CONSTANT = -1;
	private static int LAPIS_MODIFIER = 2;
	private static double MULTI_ENCHANT_DIVISOR = 75.0D;

	public static List<CustomEnchantment> getEnchantments() {
		return ENCHANTMENTS;
	}
	
	public static int getMaxEnchantments() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("max_enchantments");
	}
	
	public static boolean customEnchantsDisabled() {
		return !EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants");
	}
	
	public static int getLevelDivisor() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("level_divisor");
	}
	
	public static boolean getChestLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("chest_loot");
	}
	
	public static boolean getMobLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("mob_loot");
	}
	
	public static boolean getFishingLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("fishing_loot");
	}

	public static boolean addEnchantment(CustomEnchantment enchantment) {
		if(ENCHANTMENTS.contains(enchantment)) {
			return true;
		}
		ENCHANTMENTS.add(enchantment);
		boolean custom = enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper;
		String error_message = "Trouble adding the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment: ";
		String success_message = "Added the " + enchantment.getName() + (custom ? " custom" : "") + " enchantment.";
		if(!custom) {
			ChatUtils.sendToConsole(Level.INFO, success_message);
			return true;
		}
		try {
		    Field f = Enchantment.class.getDeclaredField("acceptingNew");
		    f.setAccessible(true);
		    f.set(null, true);
		    Enchantment.registerEnchantment(enchantment.getRelativeEnchantment());
		    ChatUtils.sendToConsole(Level.INFO, success_message);
			return true;
		} catch (Exception e) {
			ENCHANTMENTS.remove(enchantment);

			ChatUtils.sendToConsole(Level.WARNING, error_message);
		    e.printStackTrace();
		    return false;
		}
	}
	
	public static List<EnchantmentLevel> getEnchantmentLevels(ItemStack item){
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if(item.getItemMeta() != null && item.getItemMeta().getEnchants() != null && item.getItemMeta().getEnchants().size() > 0){
			for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = item.getItemMeta().getEnchants().entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Enchantment, Integer> e = it.next();
				levels.add(new EnchantmentLevel(DefaultEnchantments.getCustomEnchantment(e.getKey()), e.getValue()));
			}
		}
		return levels;
	}

	public static boolean isEnchantable(ItemStack item) {
		if (item == null) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		for(CustomEnchantment enchant : ENCHANTMENTS){
			if(meta.hasEnchant(enchant.getRelativeEnchantment())){
				return false;
			}
		}
		if (ItemType.ALL.getItemTypes().contains(item.getType())) {
			return true;
		}
		if(item.getType().equals(Material.BOOK)){
			return true;
		}
		return false;
	}

	public static List<EnchantmentLevel> generateEnchantments(Player player,
			Material material, int level, int lapis, boolean treasure) {
		List<EnchantmentLevel> enchants = new ArrayList<EnchantmentLevel>();
		int enchantability = getEnchantability(material, level, lapis);
		double multiEnchantDivisor = getMultiEnchantDivisor();
		int totalWeight = 0;
		List<CustomEnchantment> customEnchants = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment : ENCHANTMENTS){
			if(enchantment.isEnabled()) {
				if (treasure) {
					if(enchantment.canEnchant(player, enchantability, level) && enchantment.canEnchantItem(material)){
						totalWeight += enchantment.getWeight();
						customEnchants.add(enchantment);
					}
				} else {
					if(enchantment.canEnchant(player, enchantability, level) && enchantment.canEnchantItem(material) && !enchantment.isTreasure()){
						totalWeight += enchantment.getWeight();
						customEnchants.add(enchantment);
					}
				}
			}
		}
		int getWeight = (int)(Math.random() * totalWeight);
		for(CustomEnchantment customEnchant : customEnchants){
			getWeight -= customEnchant.getWeight();
			if(getWeight <= 0){
				enchants.add(new EnchantmentLevel(customEnchant, customEnchant.getEnchantLevel(player, enchantability)));
				break;
			}
		}
		int finalEnchantability = enchantability / 2;
		while(((finalEnchantability + 1) / multiEnchantDivisor) > Math.random()){
			totalWeight = 0;
			customEnchants = new ArrayList<CustomEnchantment>();
			for(CustomEnchantment enchantment : ENCHANTMENTS){
				if(enchantment.canEnchant(player, enchantability, level) && enchantment.canEnchantItem(material)){
					boolean canEnchant = true;
					for(EnchantmentLevel enchant : enchants){
						if(CustomEnchantment.conflictsWith(enchantment, enchant.getEnchant())){
							canEnchant = false;
						}
					}
					if(canEnchant){
						totalWeight += enchantment.getWeight();
						customEnchants.add(enchantment);
					}
				}
			}
			if(totalWeight == 0){
				break;
			}
			getWeight = (int)(Math.random() * totalWeight);
			for(CustomEnchantment customEnchant : customEnchants){
				getWeight -= customEnchant.getWeight();
				if(getWeight <= 0){
					enchants.add(new EnchantmentLevel(customEnchant, customEnchant.getEnchantLevel(player, enchantability)));
					break;
				}
			}
			finalEnchantability = finalEnchantability / 2;
		}
		int maxEnchants = getMaxEnchantments();
		if(maxEnchants > 0) {
			for(int i = enchants.size() - 1; i > maxEnchants; i--) {
				enchants.remove(i);
			}
		}
		return enchants;
	}

	public static int getBookshelves(Location loc) {
		int bookshelves = 0;
		for (int x = loc.getBlockX() - 2; x < loc.getBlockX() + 3; x++) {
			for (int y = loc.getBlockY(); y < loc.getBlockY() + 2; y++) {
				for (int z = loc.getBlockZ() - 2; z < loc.getBlockZ() + 3; z++) {
					if ((x == loc.getBlockX() - 2 || x == loc.getBlockX() + 2)
							|| (z == loc.getBlockZ() - 2 || z == loc
									.getBlockZ() + 2)) {
						Location bookshelf = new Location(loc.getWorld(), x, y,
								z);
						if (bookshelf.getBlock().getType()
								.equals(Material.BOOKSHELF)) {
							bookshelves++;
						}
					}
				}
			}
		}
		if (EnchantmentSolution.getPlugin().getConfigFiles().useLevel50()) {
			if (bookshelves > 23)
				bookshelves = 23;
		} else {
			if (bookshelves > 15)
				bookshelves = 15;
		}
		return bookshelves;
	}

	public static int getEnchantability(Material material, int level, int lapis) {
		int enchantability = 1;
		if (ItemType.WOODEN_TOOLS.getItemTypes().contains(material)) {
			enchantability = 15;
		} else if (ItemType.STONE_TOOLS.getItemTypes()
				.contains(material)) {
			enchantability = 5;
		} else if (ItemType.GOLDEN_TOOLS.getItemTypes()
				.contains(material)) {
			enchantability = 22;
		} else if (ItemType.IRON_TOOLS.getItemTypes()
				.contains(material)) {
			enchantability = 14;
		} else if (ItemType.DIAMOND_TOOLS.getItemTypes().contains(material)) {
			enchantability = 10;
		} else if (ItemType.LEATHER_ARMOR.getItemTypes().contains(material)) {
			enchantability = 15;
		} else if (ItemType.GOLDEN_ARMOR.getItemTypes().contains(material)) {
			enchantability = 25;
		} else if (ItemType.CHAINMAIL_ARMOR.getItemTypes().contains(material)) {
			enchantability = 12;
		} else if (ItemType.IRON_ARMOR.getItemTypes().contains(material)) {
			enchantability = 9;
		} else if (ItemType.DIAMOND_ARMOR.getItemTypes().contains(material)) {
			enchantability = 10;
		}

		int enchantability_2 = enchantability / 2;
		int rand_enchantability = 1 + randomInt(enchantability_2 / 2 + 1)
				+ randomInt(enchantability_2 / 2 + 1);

		int k = level + rand_enchantability + (lapis - getLapisConstant()) * getLapisModifier();
		float rand_bonus_percent = (float) (1 + (randomFloat() + randomFloat() - 1) * .15);
		return (int) (k * rand_bonus_percent + 0.5);
	}

	private static int randomInt(int num) {
		double random = Math.random();

		return (int) (random * num);
	}

	private static float randomFloat() {
		return (float) Math.random();
	}
	
	public static ItemStack addEnchantmentsToItem(ItemStack item, List<EnchantmentLevel> levels){
		if(levels == null) {
			return item;
		}
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null){
			lore = new ArrayList<String>();
		}
		for(EnchantmentLevel level : levels){
			meta.addEnchant(level.getEnchant().getRelativeEnchantment(), level.getLevel(), true);
			CustomEnchantment enchant = null;
			for(CustomEnchantment ench : Enchantments.getEnchantments()){
				if(ench.getName().equalsIgnoreCase(level.getEnchant().getName())){
					enchant = ench;
					break;
				}
			}
			if(enchant != null && enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper){
				String enchName = StringUtils.returnEnchantmentName(enchant, level.getLevel());
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + enchName);
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack addEnchantmentToItem(ItemStack item, CustomEnchantment enchantment, int level){
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null){
			lore = new ArrayList<String>();
		}
		if(Enchantments.hasEnchantment(item, enchantment.getRelativeEnchantment())){
			String enchName = ChatColor.RESET + "" + ChatColor.GRAY + StringUtils.returnEnchantmentName(enchantment, meta.getEnchantLevel(enchantment.getRelativeEnchantment()));
			meta.removeEnchant(enchantment.getRelativeEnchantment());
			while(lore.contains(enchName)) {
				lore.remove(enchName);
			}
		}
		meta.addEnchant(enchantment.getRelativeEnchantment(), level, true);
		CustomEnchantment enchant = null;
		for(CustomEnchantment ench : Enchantments.getEnchantments()){
			if(ench.getName().equalsIgnoreCase(enchantment.getName())){
				enchant = ench;
				break;
			}
		}
		if(enchant != null && enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper){
			String enchName = StringUtils.returnEnchantmentName(enchant, level);
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + enchName);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack removeEnchantmentFromItem(ItemStack item, CustomEnchantment enchantment){
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null){
			lore = new ArrayList<String>();
		}
		if(Enchantments.hasEnchantment(item, enchantment.getRelativeEnchantment())){
			String enchName = ChatColor.RESET + "" + ChatColor.GRAY + StringUtils.returnEnchantmentName(enchantment, meta.getEnchantLevel(enchantment.getRelativeEnchantment()));
			meta.removeEnchant(enchantment.getRelativeEnchantment());
			while(lore.contains(enchName)) {
				lore.remove(enchName);
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack removeAllEnchantments(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null){
			lore = new ArrayList<String>();
		}
		for(CustomEnchantment enchantment : DefaultEnchantments.getEnchantments()) {
			if(Enchantments.hasEnchantment(item, enchantment.getRelativeEnchantment())){
				String enchName = ChatColor.RESET + "" + ChatColor.GRAY + StringUtils.returnEnchantmentName(enchantment, meta.getEnchantLevel(enchantment.getRelativeEnchantment()));
				meta.removeEnchant(enchantment.getRelativeEnchantment());
				while(lore.contains(enchName)) {
					lore.remove(enchName);
				}
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static boolean hasEnchantment(ItemStack item, Enchantment enchant){
		if(item.getItemMeta() != null && item.getItemMeta().getEnchants() != null && item.getItemMeta().getEnchants().size() > 0){
			for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = item.getItemMeta().getEnchants().entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Enchantment, Integer> e = it.next();
				if(e.getKey().equals(enchant)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getTotalEnchantments(ItemStack item) {
		if(item.getItemMeta() != null && item.getItemMeta().getEnchants() != null && item.getItemMeta().getEnchants().size() > 0){
			return item.getItemMeta().getEnchants().size();
		}
		return 0;
	}
	
	public static int getLevel(ItemStack item, Enchantment enchant){
		if(item.getItemMeta() != null && item.getItemMeta().getEnchants() != null && item.getItemMeta().getEnchants().size() > 0){
			for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = item.getItemMeta().getEnchants().entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Enchantment, Integer> e = it.next();
				if(e.getKey().equals(enchant)){
					return e.getValue();
				}
			}
		}
		return 0;
	}
	
	public static boolean canAddEnchantment(CustomEnchantment customEnchant, ItemStack item) {
		ItemMeta meta = item.clone().getItemMeta();
		Map<Enchantment, Integer> enchants = meta.getEnchants();
		if(item.getType().equals(Material.ENCHANTED_BOOK)) {
			
		}else if(!customEnchant.canAnvilItem(item.getType())) {
			return false;
		}
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = enchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			for(CustomEnchantment custom : Enchantments.ENCHANTMENTS) {
				if(custom.getRelativeEnchantment().equals(enchant)) {
					if(CustomEnchantment.conflictsWith(customEnchant, custom) && !customEnchant.getName().equals(custom.getName())) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static int combineEnchantmentsLevel(ItemStack first, ItemStack second) {
		int cost = 0;
		ItemMeta firstMeta = first.clone().getItemMeta();
		Map<Enchantment, Integer> firstEnchants = firstMeta.getEnchants();
		ItemMeta secondMeta = second.clone().getItemMeta();
		Map<Enchantment, Integer> secondEnchants = secondMeta.getEnchants();
		if(second.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) secondMeta;
			secondEnchants = meta.getStoredEnchants();
		}
		List<EnchantmentLevel> secondLevels = new ArrayList<EnchantmentLevel>();
		List<EnchantmentLevel> firstLevels = new ArrayList<EnchantmentLevel>();
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = secondEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.ENCHANTMENTS) {
				if(isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) {
					secondLevels.add(new EnchantmentLevel(customEnchant, level));
				}
			}
		}
		
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = firstEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.ENCHANTMENTS) {
				if(isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) {
					firstLevels.add(new EnchantmentLevel(customEnchant, level));
				}
			}
		}
		
		for(EnchantmentLevel enchantTwo : secondLevels) {
			boolean conflict = false;
			boolean same = false;
			int levelCost = 0;
			for(EnchantmentLevel enchantOne : firstLevels) {
				if(enchantTwo.getEnchant().getRelativeEnchantment().equals(enchantOne.getEnchant().getRelativeEnchantment())) {
					same = true;
					if(enchantTwo.getLevel() == enchantOne.getLevel()) {
						if(enchantTwo.getLevel() == enchantTwo.getEnchant().getMaxLevel()) {
							levelCost = enchantTwo.getLevel();
						}else {
							levelCost = enchantTwo.getLevel() + 1;
						}
					}else if(enchantTwo.getLevel() > enchantOne.getLevel()) {
						levelCost = enchantTwo.getLevel();
					}else {
						levelCost = enchantOne.getLevel();
					}
				}else if(CustomEnchantment.conflictsWith(enchantOne.getEnchant(), enchantTwo.getEnchant())) {
					conflict = true;
				}
			}
			if(conflict) {
				cost += 1;
			}else if(same) {
				if(first.getType() == Material.ENCHANTED_BOOK || enchantTwo.getEnchant().canAnvilItem(first.getType())) {
					cost += levelCost * enchantTwo.getEnchant().multiplier(second.getType());
				}
			}else {
				if(first.getType() == Material.ENCHANTED_BOOK || enchantTwo.getEnchant().canAnvilItem(first.getType())) {
					cost += enchantTwo.getLevel() * enchantTwo.getEnchant().multiplier(second.getType());
				}
			}
		}
		return cost;
	}
	
	public static boolean isRepairable(CustomEnchantment enchant) {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("repairable")) {
			return true;
		}
		
		if(enchant.isEnabled()) {
			return true;
		}
		
		return false;
	}
	
	public static List<EnchantmentLevel> combineEnchants(Player player, ItemStack first, ItemStack second){
		ItemMeta firstMeta = first.clone().getItemMeta();
		Map<Enchantment, Integer> firstEnchants = firstMeta.getEnchants();
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) firstMeta;
			firstEnchants = meta.getStoredEnchants();
		}
		ItemMeta secondMeta = second.clone().getItemMeta();
		Map<Enchantment, Integer> secondEnchants = secondMeta.getEnchants();
		if(second.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) secondMeta;
			secondEnchants = meta.getStoredEnchants();
		}
		List<EnchantmentLevel> secondLevels = new ArrayList<EnchantmentLevel>();
		List<EnchantmentLevel> firstLevels = new ArrayList<EnchantmentLevel>();
		
		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = secondEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.ENCHANTMENTS) {
				if(isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) {
					secondLevels.add(new EnchantmentLevel(customEnchant, level));
				}
			}
		}
		
		for (Iterator<java.util.Map.Entry<Enchantment, Integer>> it = firstEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant : Enchantments.ENCHANTMENTS) {
				if(isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) {
					firstLevels.add(new EnchantmentLevel(customEnchant, level));
				}
			}
		}
		
		for(EnchantmentLevel enchantTwo : secondLevels) {
			boolean conflict = false;
			boolean same = false;
			int levelCost = 0;
			for(EnchantmentLevel enchantOne : firstLevels) {
				if(enchantTwo.getEnchant().getRelativeEnchantment().equals(enchantOne.getEnchant().getRelativeEnchantment())) {
					same = true;
					if(enchantTwo.getLevel() == enchantOne.getLevel()) {
						if(enchantTwo.getLevel() == enchantTwo.getEnchant().getMaxLevel()) {
							levelCost = enchantTwo.getLevel();
						}else {
							levelCost = enchantTwo.getLevel() + 1;
						}
					}else if(enchantTwo.getLevel() > enchantOne.getLevel()) {
						levelCost = enchantTwo.getLevel();
					}else {
						levelCost = enchantOne.getLevel();
					}
				}else if(CustomEnchantment.conflictsWith(enchantOne.getEnchant(), enchantTwo.getEnchant())) {
					conflict = true;
				}
			}
			if(same) {
				if(first.getType() == Material.ENCHANTED_BOOK || enchantTwo.getEnchant().canAnvilItem(first.getType())) {
					enchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), levelCost));
				}
			}else if(!conflict){
				if(first.getType() == Material.ENCHANTED_BOOK || enchantTwo.getEnchant().canAnvilItem(first.getType())) {
					enchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), enchantTwo.getLevel()));
				}
			}
		}
		
		for(EnchantmentLevel enchantOne : firstLevels) {
			boolean added = false;
			for(EnchantmentLevel enchantment : enchantments) {
				if(enchantOne.getEnchant().getRelativeEnchantment().equals(enchantment.getEnchant().getRelativeEnchantment())) {
					added = true; 
					break;
				}
			}
			if(!added) {
				enchantments.add(enchantOne);
			}
		}
		int maxEnchants = getMaxEnchantments();
		if(maxEnchants > 0) {
			for(int i = enchantments.size() - 1; i > maxEnchants; i--) {
				enchantments.remove(i);
			}
		}
		
		for(int i = enchantments.size() - 1; i >= 0; i--) {
			EnchantmentLevel enchant = enchantments.get(i);
			if(!enchant.getEnchant().canAnvil(player, enchant.getLevel())) {
				int level = enchant.getEnchant().getAnvilLevel(player, enchant.getLevel());
				if(level > 0) {
					enchantments.get(i).setLevel(level);
				} else {
					enchantments.remove(i);
				}
			}
		}
		
		return enchantments;
	}
	
	public static int getLapisConstant() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getInt("lapis_modifiers.constant");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 0;
		}
		return LAPIS_CONSTANT;
	}
	
	public static int getLapisModifier() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getInt("lapis_modifiers.modifier");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 0;
		}
		return LAPIS_MODIFIER;
	}
	
	public static double getMultiEnchantDivisor() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getDouble("multi_enchant_divisor");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 50.0D;
		}
		return MULTI_ENCHANT_DIVISOR;
	}

}
