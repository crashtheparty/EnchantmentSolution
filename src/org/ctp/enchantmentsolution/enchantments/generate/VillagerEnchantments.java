package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class VillagerEnchantments extends LootEnchantments {

	private MerchantRecipe recipe;
	private final String price;
	private List<EnchantmentLevel> enchantments;

	private VillagerEnchantments(Player player, ItemStack item, MerchantRecipe original, Loots loot, String price) {
		super(player, item, EnchantmentLocation.VILLAGER, loot);
		this.price = price;

		setMerchantRecipe(original);
	}

	public MerchantRecipe getMerchantRecipe() {
		return recipe;
	}

	@Override
	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	private void setMerchantRecipe(MerchantRecipe original) {
		int[] levelPrice = getLevelPrice(original.getResult().getType());

		enchantments = super.getEnchantments();

		if (enchantments == null) throw new NullPointerException("EnchantmentLevel cannot be null.");
		Material mat = original.getResult().getType();
		ItemStack matItem = new ItemStack(mat == Material.ENCHANTED_BOOK ? Material.BOOK : mat);
		if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && mat == Material.ENCHANTED_BOOK) mat = Material.BOOK;
		else if (ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && mat == Material.BOOK) mat = Material.ENCHANTED_BOOK;
		

		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		if (enchantments.size() > 0) {
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

			recipe = new MerchantRecipe(EnchantmentUtils.addEnchantmentToItem(new ItemStack(mat), enchant.getEnchant(), enchant.getLevel()), original.getUses(), original.getMaxUses(), original.hasExperienceReward(), original.getVillagerExperience(), original.getPriceMultiplier() / 2);
			recipe.setIngredients(ingredients);
			recipe.setVillagerExperience(original.getVillagerExperience());
		} else {
			recipe = new MerchantRecipe(new ItemStack(matItem), original.getUses(), original.getMaxUses(), original.hasExperienceReward(), original.getVillagerExperience(), original.getPriceMultiplier() / 2);
			ingredients.add(new ItemStack(Material.EMERALD, levelPrice[1] / 4));
			recipe.setIngredients(ingredients);
			recipe.setVillagerExperience(original.getVillagerExperience());
		}
	}

	public static VillagerEnchantments getVillagerEnchantments(ItemStack item, MerchantRecipe original, String type, Loots defaultLoot, String price) {
		if (item == null) return null;
		Loots loot = Loots.getLoot(type);
		if (loot == null) loot = defaultLoot;

		return new VillagerEnchantments(null, item, original, loot, price);
	}

	private int[] getLevelPrice(Material type) {
		int level = 0;
		if (ConfigString.LEVEL_FIFTY.getBoolean()) {
			if (type == Material.ENCHANTED_BOOK) level = MathUtils.normalize(0, 6, 4);
			else
				level = MathUtils.normalize(1, 4, 1);
		} else if (type == Material.ENCHANTED_BOOK) level = MathUtils.normalize(0, 3, 4);
		else
			level = MathUtils.normalize(0, 2, 1);
		return getLevelPrice(type, level);
	}

	private int[] getLevelPrice(Material type, int level) {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%random%", Math.random());
		codes.put("%level%", level);
		return new int[] { level, (int) MathUtils.eval(Chatable.get().getMessage(price, codes)) };
	}

	public String getPrice() {
		return price;
	}
}
