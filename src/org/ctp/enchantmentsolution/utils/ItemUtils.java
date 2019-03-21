package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.PlayerLevels;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class ItemUtils {

	private static HashMap<String, List<Material>> ITEM_TYPES = setItemTypes();
	private static HashMap<Material, List<Material>> REPAIR_TYPES = setRepairTypes();
	private static List<Material> REPAIR_MATERIALS = Arrays.asList(Material.DIAMOND, Material.IRON_INGOT, Material.GOLD_INGOT, Material.COBBLESTONE, 
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, 
			Material.LEATHER, Material.PHANTOM_MEMBRANE, Material.STRING);
	private static List<Material> SHULKER_BOXES = Arrays.asList(Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX,
			Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX,
			Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX,
			Material.LIGHT_GRAY_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.SHULKER_BOX);
	
	private static HashMap<String, List<Material>> setItemTypes() {
		HashMap<String, List<Material>> itemTypes = new HashMap<String, List<Material>>();
		List<Material> helmets = new ArrayList<Material>();
		helmets.addAll(Arrays.asList(Material.DIAMOND_HELMET,
				Material.CHAINMAIL_HELMET, Material.GOLDEN_HELMET,
				Material.IRON_HELMET, Material.LEATHER_HELMET));
		itemTypes.put("helmets", helmets);

		List<Material> chestplates = new ArrayList<Material>();
		chestplates.addAll(Arrays.asList(Material.DIAMOND_CHESTPLATE,
				Material.CHAINMAIL_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
				Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE));
		itemTypes.put("chestplates", chestplates);

		List<Material> leggings = new ArrayList<Material>();
		leggings.addAll(Arrays.asList(Material.DIAMOND_LEGGINGS,
				Material.CHAINMAIL_LEGGINGS, Material.GOLDEN_LEGGINGS,
				Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS));
		itemTypes.put("leggings", leggings);

		List<Material> boots = new ArrayList<Material>();
		boots.addAll(Arrays.asList(Material.DIAMOND_BOOTS,
				Material.CHAINMAIL_BOOTS, Material.GOLDEN_BOOTS,
				Material.IRON_BOOTS, Material.LEATHER_BOOTS));
		itemTypes.put("boots", boots);

		List<Material> swords = new ArrayList<Material>();
		swords.addAll(Arrays.asList(Material.DIAMOND_SWORD,
				Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD,
				Material.WOODEN_SWORD));
		itemTypes.put("swords", swords);

		List<Material> pickaxes = new ArrayList<Material>();
		pickaxes.addAll(Arrays.asList(Material.DIAMOND_PICKAXE,
				Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
				Material.STONE_PICKAXE, Material.WOODEN_PICKAXE));
		itemTypes.put("pickaxes", pickaxes);

		List<Material> shovels = new ArrayList<Material>();
		shovels.addAll(Arrays.asList(Material.DIAMOND_SHOVEL,
				Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.STONE_SHOVEL,
				Material.WOODEN_SHOVEL));
		itemTypes.put("shovels", shovels);

		List<Material> axes = new ArrayList<Material>();
		axes.addAll(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE,
				Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE));
		itemTypes.put("axes", axes);

		List<Material> hoes = new ArrayList<Material>();
		hoes.addAll(Arrays.asList(Material.DIAMOND_HOE, Material.GOLDEN_HOE,
				Material.IRON_HOE, Material.STONE_HOE, Material.WOODEN_HOE));
		itemTypes.put("hoes", hoes);

		List<Material> bow = new ArrayList<Material>();
		bow.add(Material.BOW);
		itemTypes.put("bow", bow);

		List<Material> shield = new ArrayList<Material>();
		shield.add(Material.SHIELD);
		itemTypes.put("shield", shield);

		List<Material> fishing = new ArrayList<Material>();
		fishing.add(Material.FISHING_ROD);
		itemTypes.put("fishing", fishing);

		List<Material> shears = new ArrayList<Material>();
		shears.add(Material.SHEARS);
		itemTypes.put("shears", shears);

		List<Material> flintSteel = new ArrayList<Material>();
		flintSteel.add(Material.FLINT_AND_STEEL);
		itemTypes.put("flintSteel", flintSteel);

		List<Material> carrotStick = new ArrayList<Material>();
		carrotStick.add(Material.CARROT_ON_A_STICK);
		itemTypes.put("carrotStick", carrotStick);

		List<Material> elytra = new ArrayList<Material>();
		elytra.add(Material.ELYTRA);
		itemTypes.put("elytra", elytra);
		
		List<Material> trident = new ArrayList<Material>();
		trident.add(Material.TRIDENT);
		itemTypes.put("trident", trident);

		List<Material> armor = new ArrayList<Material>();
		armor.addAll(helmets);
		armor.addAll(chestplates);
		armor.addAll(leggings);
		armor.addAll(boots);
		itemTypes.put("armor", armor);

		List<Material> tools = new ArrayList<Material>();
		tools.addAll(pickaxes);
		tools.addAll(axes);
		tools.addAll(shovels);
		itemTypes.put("tools", tools);

		List<Material> weapons = new ArrayList<Material>();
		weapons.addAll(axes);
		weapons.addAll(swords);
		itemTypes.put("weapons", weapons);

		List<Material> misc = new ArrayList<Material>();
		misc.addAll(shield);
		misc.addAll(fishing);
		misc.addAll(flintSteel);
		misc.addAll(carrotStick);
		misc.addAll(elytra);
		misc.addAll(hoes);
		misc.addAll(trident);

		List<Material> all = new ArrayList<Material>();
		all.addAll(armor);
		all.addAll(tools);
		all.addAll(weapons);
		all.addAll(bow);
		all.addAll(misc);
		itemTypes.put("all", all);

		List<Material> woodTools = new ArrayList<Material>();
		woodTools.addAll(Arrays.asList(Material.WOODEN_AXE, Material.WOODEN_SWORD,
				Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE));
		itemTypes.put("wood_tools", woodTools);

		List<Material> stoneTools = new ArrayList<Material>();
		stoneTools.addAll(Arrays.asList(Material.STONE_AXE,
				Material.STONE_SWORD, Material.STONE_SHOVEL,
				Material.STONE_PICKAXE));
		itemTypes.put("stone_tools", stoneTools);

		List<Material> goldTools = new ArrayList<Material>();
		goldTools.addAll(Arrays.asList(Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
				Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE));
		itemTypes.put("gold_tools", goldTools);

		List<Material> ironTools = new ArrayList<Material>();
		ironTools.addAll(Arrays.asList(Material.IRON_AXE, Material.IRON_SWORD,
				Material.IRON_SHOVEL, Material.IRON_PICKAXE));
		itemTypes.put("iron_tools", ironTools);

		List<Material> diamondTools = new ArrayList<Material>();
		diamondTools.addAll(Arrays.asList(Material.DIAMOND_AXE,
				Material.DIAMOND_SWORD, Material.DIAMOND_SHOVEL,
				Material.DIAMOND_PICKAXE));
		itemTypes.put("diamond_tools", diamondTools);

		List<Material> leatherArmor = new ArrayList<Material>();
		leatherArmor.addAll(Arrays.asList(Material.LEATHER_HELMET,
				Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
				Material.LEATHER_BOOTS));
		itemTypes.put("leather_armor", leatherArmor);

		List<Material> goldArmor = new ArrayList<Material>();
		goldArmor.addAll(Arrays.asList(Material.GOLDEN_HELMET,
				Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS,
				Material.GOLDEN_BOOTS));
		itemTypes.put("gold_armor", goldArmor);

		List<Material> chainArmor = new ArrayList<Material>();
		chainArmor.addAll(Arrays.asList(Material.CHAINMAIL_HELMET,
				Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS,
				Material.CHAINMAIL_BOOTS));
		itemTypes.put("chain_armor", chainArmor);

		List<Material> ironArmor = new ArrayList<Material>();
		ironArmor.addAll(Arrays.asList(Material.IRON_HELMET,
				Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS,
				Material.IRON_BOOTS));
		itemTypes.put("iron_armor", ironArmor);

		List<Material> diamondArmor = new ArrayList<Material>();
		diamondArmor.addAll(Arrays.asList(Material.DIAMOND_HELMET,
				Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
				Material.DIAMOND_BOOTS));
		itemTypes.put("diamond_armor", diamondArmor);
		
		return itemTypes;
	}
	
	public static HashMap<String, List<Material>> getItemTypes() {
		return ITEM_TYPES;
	}
	
	private static HashMap<Material, List<Material>> setRepairTypes(){
		HashMap<Material, List<Material>> repairTypes = new HashMap<Material, List<Material>>();
		repairTypes.put(Material.DIAMOND_AXE, Arrays.asList(Material.DIAMOND_AXE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_SWORD, Arrays.asList(Material.DIAMOND_SWORD, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_SHOVEL, Arrays.asList(Material.DIAMOND_SHOVEL, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_PICKAXE, Arrays.asList(Material.DIAMOND_PICKAXE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_HOE, Arrays.asList(Material.DIAMOND_HOE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_HELMET, Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_CHESTPLATE, Arrays.asList(Material.DIAMOND_CHESTPLATE, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_LEGGINGS, Arrays.asList(Material.DIAMOND_LEGGINGS, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.DIAMOND_BOOTS, Arrays.asList(Material.DIAMOND_BOOTS, Material.DIAMOND, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_AXE, Arrays.asList(Material.IRON_AXE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_SWORD, Arrays.asList(Material.IRON_SWORD, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_SHOVEL, Arrays.asList(Material.IRON_SHOVEL, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_PICKAXE, Arrays.asList(Material.IRON_PICKAXE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_HOE, Arrays.asList(Material.IRON_HOE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_HELMET, Arrays.asList(Material.IRON_HELMET, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_CHESTPLATE, Arrays.asList(Material.IRON_CHESTPLATE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.IRON_LEGGINGS, Arrays.asList(Material.IRON_LEGGINGS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_BOOTS, Arrays.asList(Material.GOLDEN_BOOTS, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_AXE, Arrays.asList(Material.GOLDEN_AXE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_SWORD, Arrays.asList(Material.GOLDEN_SWORD, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_SHOVEL, Arrays.asList(Material.GOLDEN_SHOVEL, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_PICKAXE, Arrays.asList(Material.GOLDEN_PICKAXE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_HOE, Arrays.asList(Material.GOLDEN_HOE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_HELMET, Arrays.asList(Material.GOLDEN_HELMET, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_CHESTPLATE, Arrays.asList(Material.GOLDEN_CHESTPLATE, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_LEGGINGS, Arrays.asList(Material.GOLDEN_LEGGINGS, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.GOLDEN_BOOTS, Arrays.asList(Material.GOLDEN_BOOTS, Material.GOLD_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.STONE_AXE, Arrays.asList(Material.STONE_AXE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.STONE_SWORD, Arrays.asList(Material.STONE_SWORD, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.STONE_SHOVEL, Arrays.asList(Material.STONE_SHOVEL, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.STONE_PICKAXE, Arrays.asList(Material.STONE_PICKAXE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.STONE_HOE, Arrays.asList(Material.STONE_HOE, Material.COBBLESTONE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.CHAINMAIL_HELMET, Arrays.asList(Material.CHAINMAIL_HELMET, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.CHAINMAIL_CHESTPLATE, Arrays.asList(Material.CHAINMAIL_CHESTPLATE, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.CHAINMAIL_LEGGINGS, Arrays.asList(Material.CHAINMAIL_LEGGINGS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.CHAINMAIL_BOOTS, Arrays.asList(Material.CHAINMAIL_BOOTS, Material.IRON_INGOT, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.WOODEN_AXE, Arrays.asList(Material.WOODEN_AXE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.WOODEN_SWORD, Arrays.asList(Material.WOODEN_SWORD, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.WOODEN_SHOVEL, Arrays.asList(Material.WOODEN_SHOVEL, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.WOODEN_PICKAXE, Arrays.asList(Material.WOODEN_PICKAXE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.WOODEN_HOE, Arrays.asList(Material.WOODEN_HOE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.LEATHER_HELMET, Arrays.asList(Material.LEATHER_HELMET, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.LEATHER_CHESTPLATE, Arrays.asList(Material.LEATHER_CHESTPLATE, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.LEATHER_LEGGINGS, Arrays.asList(Material.LEATHER_LEGGINGS, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.LEATHER_BOOTS, Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.ELYTRA, Arrays.asList(Material.ELYTRA, Material.PHANTOM_MEMBRANE, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.BOW, Arrays.asList(Material.BOW, Material.STRING, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.FISHING_ROD, Arrays.asList(Material.FISHING_ROD, Material.STRING, Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.BOOK, Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.ENCHANTED_BOOK, Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
		repairTypes.put(Material.TRIDENT, Arrays.asList(Material.BOOK, Material.TRIDENT));
		return repairTypes;
	}
	
	public static HashMap<Material, List<Material>> getRepairTypes(){
		return REPAIR_TYPES;
	}
	
	public static List<Material> getRepairMaterials() {
		return REPAIR_MATERIALS;
	}
	
	public static int repairItem(ItemStack first, ItemStack second) {
		int amount = second.getAmount();
		if(amount > 4) amount = 4;
		int durPerItem = first.getType().getMaxDurability() / 4;
		ItemStack clone = first.clone();
		DamageUtils.setDamage(clone, DamageUtils.getDamage(first.getItemMeta()));
		int level = 0;
		while(DamageUtils.getDamage(clone.getItemMeta()) > 0) {
			level++;
			DamageUtils.setDamage(clone, (DamageUtils.getDamage(clone.getItemMeta()) - durPerItem));
		}
		return level;
	}
	
	public static ItemStack repairItem(ItemStack combined, ItemStack first, ItemStack second) {
		int amount = second.getAmount();
		if(amount > 4) amount = 4;
		int durPerItem = first.getType().getMaxDurability() / 4;
		while(DamageUtils.getDamage(combined.getItemMeta()) > 0 && amount > 0) {
			amount--;
			DamageUtils.setDamage(combined, (DamageUtils.getDamage(combined.getItemMeta()) - durPerItem));
		}
		
		if(DamageUtils.getDamage(combined.getItemMeta()) < 0) {
			DamageUtils.setDamage(combined, 0);
		}
		return combined;
	}
	
	public static ItemStack combineItems(Player player, ItemStack first, ItemStack second) {
		ItemStack combined = new ItemStack(first.getType());
		if(first.getType().equals(Material.ENCHANTED_BOOK)) {
			combined = new ItemStack(Material.BOOK);
		}
		DamageUtils.setDamage(combined, DamageUtils.getDamage(first.getItemMeta()));
		RepairType repairType = RepairType.getRepairType(first, second);
		if(repairType == null) {
			return null;
		}
				
		if(repairType.equals(RepairType.REPAIR)) {
			combined = repairItem(combined, first, second);
		}else if(second.getType().equals(first.getType())) {
			int extraDurability = second.getType().getMaxDurability() - DamageUtils.getDamage(second.getItemMeta()) + (int) (second.getType().getMaxDurability() * .12);
			DamageUtils.setDamage(combined, DamageUtils.getDamage(combined.getItemMeta()) - extraDurability);
			if(DamageUtils.getDamage(combined.getItemMeta()) < 0) {
				DamageUtils.setDamage(combined, 0);
			}
		}
		
		List<EnchantmentLevel> enchantments = Enchantments.combineEnchants(player, first, second);
		
		ItemMeta firstMeta = first.getItemMeta();
		ItemMeta combinedMeta = combined.getItemMeta();
		
		if(firstMeta instanceof LeatherArmorMeta && combinedMeta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) combinedMeta).setColor(((LeatherArmorMeta) firstMeta).getColor());
		}
		
		combinedMeta.setDisplayName(firstMeta.getDisplayName());
		combinedMeta.setLocalizedName(firstMeta.getLocalizedName());
		
		combined.setItemMeta(combinedMeta);
		
		combined = Enchantments.addEnchantmentsToItem(combined, enchantments);
		
		return combined;
	}
	
	public static void giveItemToPlayer(Player player, ItemStack item, Location fallback) {
		HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
		leftOver.putAll((player.getInventory().addItem(item)));
		if (!leftOver.isEmpty()) {
			for (Iterator<java.util.Map.Entry<Integer, ItemStack>> it = leftOver.entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Integer, ItemStack> e = it.next();
				fallback.add(0.5, 0.5, 0.5);
				Item droppedItem = player.getWorld().dropItem(
						fallback,
						e.getValue());
				droppedItem.setVelocity(new Vector(0,0,0));
				droppedItem.teleport(fallback);
			}
		}
	}
	
	public static ItemStack addNMSEnchantment(ItemStack item, String type) {
		ItemStack returnItem = new ItemStack(item.getType());
		ItemStack duplicate = item.clone();
		ItemMeta returnItemMeta = returnItem.getItemMeta();
		ItemMeta duplicateMeta = duplicate.getItemMeta();
		
		returnItemMeta.setDisplayName(duplicateMeta.getDisplayName());
		returnItem.setItemMeta(returnItemMeta);
		DamageUtils.setDamage(returnItem, DamageUtils.getDamage(duplicateMeta));
		
		List<EnchantmentLevel> enchants = null;
		while(enchants == null) {
			int bookshelves = ConfigFiles.getBookshelvesFromType(type);
			boolean treasure = ConfigFiles.includeTreasureFromType(type);
			PlayerLevels levels = PlayerLevels.generateFakePlayerLevels(returnItem.getType(), bookshelves, treasure);
			int i = 0;
			while(i < 3) {
				int random = (int)(Math.random() * levels.getEnchants().size() + ConfigFiles.getLevelFromType(type));
				if(random > levels.getEnchants().size() - 1) random = levels.getEnchants().size() - 1;
				if(levels.getEnchants().get(random).size() > 0) {
					enchants = levels.getEnchants().get(random);
					break;
				}
				i++;
			}
			if(i >= 3) break;
		}
		
		returnItem = Enchantments.addEnchantmentsToItem(returnItem, enchants);
		
		return returnItem;
	}

	public static List<Material> getShulkerBoxes() {
		return SHULKER_BOXES;
	}
}
