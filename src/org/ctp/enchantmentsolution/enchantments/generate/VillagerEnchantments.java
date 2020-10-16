package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class VillagerEnchantments extends LootEnchantments {

	private MerchantRecipe recipe;
	private List<EnchantmentLevel> enchantments;

	private VillagerEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure,
	MerchantRecipe original) {
		super(player, item, bookshelves, EnchantmentLocation.VILLAGER_TRADES);

		setMerchantRecipe(original);
	}

	public MerchantRecipe getMerchantRecipe() {
		return recipe;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	private void setMerchantRecipe(MerchantRecipe original) {
		int[] levelPrice = getLevelPrice(original.getResult().getType());
		List<EnchantmentList> enchantmentLists = new ArrayList<EnchantmentList>();
		for(int i = 0; i < getList().length; i++)
			if (getList()[i] != null) enchantmentLists.add(getList()[i]);

		if (enchantmentLists.size() <= levelPrice[0]) levelPrice[0] = enchantmentLists.size() - 1;

		enchantments = enchantmentLists.get(levelPrice[0]).getEnchantments();

		if (enchantments == null) throw new NullPointerException("EnchantmentLevel cannot be null.");
		Material mat = original.getResult().getType();
		ItemStack matItem = new ItemStack(mat == Material.ENCHANTED_BOOK ? Material.BOOK : mat);
		if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && mat == Material.ENCHANTED_BOOK) mat = Material.BOOK;
		else if (ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && mat == Material.BOOK) mat = Material.ENCHANTED_BOOK;

		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		EnchantmentLevel enchant = enchantments.get(0);
		if (mat == Material.BOOK || mat == Material.ENCHANTED_BOOK) {
			if (!enchant.getEnchant().getEnchantmentLocations().contains(EnchantmentLocation.TABLE)) levelPrice[1] *= 2;
			if (levelPrice[1] > 64) levelPrice[1] = 64;
			ItemStack priceItem = new ItemStack(Material.EMERALD, levelPrice[1]);
			ingredients.add(priceItem);
			ingredients.add(matItem);
		} else {
			ItemStack priceItem = new ItemStack(Material.EMERALD, levelPrice[1]);
			if (levelPrice[1] > 64) levelPrice[1] = 64;
			ingredients.add(priceItem);
		}

		recipe = new MerchantRecipe(ItemUtils.addEnchantmentToItem(new ItemStack(mat), enchant.getEnchant(), enchant.getLevel()), original.getUses(), original.getMaxUses(), original.hasExperienceReward());
		recipe.setIngredients(ingredients);
		if (VersionUtils.getBukkitVersionNumber() > 3) recipe.setVillagerExperience(original.getVillagerExperience());
	}

	public static VillagerEnchantments getVillagerEnchantments(ItemStack result, MerchantRecipe original) {
		int books = 16;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) books = 24;
		int random = bellCurve(0, books, 4);
		if (random >= books) random = books - 1;

		return new VillagerEnchantments(null, result, random, true, original);
	}

	private int[] getLevelPrice(Material type) {
		int level = 0;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) {
			if (type == Material.ENCHANTED_BOOK) level = bellCurve(0, 6, 4);
			else
				level = bellCurve(1, 4, 1);
		} else if (type == Material.ENCHANTED_BOOK) level = bellCurve(0, 3, 4);
		else
			level = bellCurve(0, 2, 1);
		return getLevelPrice(type, level);
	}

	private int[] getLevelPrice(Material type, int level) {
		int price = 0;
		if (type == Material.ENCHANTED_BOOK) {
			if (ConfigString.LEVEL_FIFTY.getBoolean()) price = level * 2 * level + (int) (Math.random() * (level * 3 + 7) + 3);
			else
				price = (level + 1) * level * 2 * level + (int) (Math.random() * (level * 3 + 15) + 3);
		} else
			switch (type.name()) {
				case "IRON_AXE":
				case "IRON_SWORD":
				case "IRON_PICKAXE":
				case "IRON_SHOVEL":
				case "FISHING_ROD":
				case "BOW":
				case "CROSSBOW":
					price = (int) (Math.random() * 16 + 7);
					break;
				case "DIAMOND_SWORD":
				case "DIAMOND_SHOVEL":
				case "DIAMOND_HELMET":
				case "DIAMOND_BOOTS":
					price = (int) (Math.random() * 17 + 11);
					break;
				case "DIAMOND_PICKAXE":
				case "DIAMOND_AXE":
				case "DIAMOND_LEGGINGS":
				case "DIAMOND_CHESTPLATE":
					price = (int) (Math.random() * 18 + 18);
					break;
				default:
					price = level * 2 * level + (int) (Math.random() * (level * 3 + 7) + 3);
					break;
			}

		return new int[] { level, price };
	}

	private static int bellCurve(int min, int max, int curve) {
		double num = min;
		for(int i = 0; i < curve; i++)
			num += Math.random() * ((double) max / (double) curve);
		return (int) num;
	}
}
