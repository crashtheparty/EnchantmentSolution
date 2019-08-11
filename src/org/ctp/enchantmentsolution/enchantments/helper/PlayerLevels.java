package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class PlayerLevels {
	
	private int books;
	private Player player;
	private Material material;
	public static List<PlayerLevels> PLAYER_LEVELS = new ArrayList<PlayerLevels>();
	private List<List<EnchantmentLevel>> enchants = new ArrayList<List<EnchantmentLevel>>();
	private List<Integer> levelList;
	
	public PlayerLevels(int books, Player player, Material material){
		this.books = books;
		this.player = player;
		this.material = material;
		
		levelList = getIntList(player, books);
		
		if(levelList == null){
			if(ConfigUtils.isLevel50()) {
				levelList = generateFiftyLevels();
			} else {
				levelList = generateThirtyLevels();
			}
		}
		
		generateEnchants(levelList, false);
		
		PLAYER_LEVELS.add(this);
	}
	
	private PlayerLevels(int books, Material material, boolean treasure) {
		this.books = books;
		this.player = null;
		this.material = material;
				
		if(ConfigUtils.isLevel50()) {
			levelList = generateFiftyLevels();
		} else {
			levelList = generateThirtyLevels();
		}
		
		generateEnchants(levelList, treasure);
	}
	
	
	public int getBooks() {
		return books;
	}
	public Player getPlayer() {
		return player;
	}
	public Material getMaterial() {
		return material;
	}
	
	public boolean compare(int books, Player player, Material material){
		if(this.books == books && player.getUniqueId().toString().equals(this.player.getUniqueId().toString()) && material.equals(this.material)){
			return true;
		}
		return false;
	}
	
	private List<Integer> generateThirtyLevels(){
		List<Integer> levelArray = new ArrayList<Integer>();
		
		int bookThirty = books > 15 ? 15 : books;
		
		for(int i = 1; i <= 3; i++){
			int x = (int) (Math.random() * 8 + 1);
			int b = (int) (Math.random() * bookThirty);
			int floor = bookThirty / 2;
			int base = x + b + floor;
			switch(i){
			case 1:
				levelArray.add(Math.max(base / 3, 1));
				break;
			case 2:
				levelArray.add((base * 2) / 3 + 1);
				break;
			case 3:
				levelArray.add(Math.max(base, bookThirty * 2));
				break;
			}
		}
		
		return levelArray;
	}
	
	private List<Integer> generateFiftyLevels(){
		List<Integer> levelArray = new ArrayList<Integer>();
		
		int bookThirty = books > 15 ? 15 : books;
		int bookFifty = books - 8;
		int addFifty = bookFifty - 7;
		
		for(int i = 1; i <= 6; i++){
			int x = (int) (Math.random() * 8 + 1);
			int b = (int) (Math.random() * bookThirty);
			int floor = bookThirty / 2;
			if(i >= 4){
				b = (int) (Math.random() * bookFifty);
				floor = bookFifty / 2;
			}
			int base = x + b + floor;
			switch(i){
			case 1:
				levelArray.add(Math.max(base / 3, 1));
				break;
			case 2:
				levelArray.add((base * 2) / 3 + 1);
				break;
			case 3:
				levelArray.add(Math.max(base, bookThirty * 2));
				break;
			case 4:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(Math.max(base / 3, 1) + (int)(addFifty * .75) + 20);
				}
				break;
			case 5:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(((base * 2) / 3 + 1 + (int)(addFifty * .75)) + 20);
				}
				break;
			case 6:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(Math.max(base, bookFifty * 2) + 20);
				}
				break;
			}
		}
		
		return levelArray;
	}


	public List<List<EnchantmentLevel>> getEnchants() {
		return enchants;
	}


	public void generateEnchants(List<Integer> intList, boolean treasure) {
		if(enchants.size() > 0) return;
		for(int i = 0; i < intList.size(); i++){
			if(intList.get(i) == -1){
				enchants.add(new ArrayList<EnchantmentLevel>());
			}else{
				enchants.add(Enchantments.generateEnchantments(player, getMaterial(), intList.get(i), i + 1, treasure));
			}
		}
	}
	
	public static List<Integer> getIntList(Player player, int books){
		if(player == null) return null;
		List<Integer> levelList = null;
		
		for(PlayerLevels levels : PLAYER_LEVELS) {
			if(levels.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString()) && levels.getBooks() == books){
				levelList = levels.getLevelList();
				break;
			}
		}
		
		return levelList;
	}
	
	public static PlayerLevels getPlayerLevels(int books, Player player, Material material){
		for(PlayerLevels levels : PLAYER_LEVELS) {
			if(levels.compare(books, player, material)){
				return levels;
			}
		}
		return null;
	}
	
	public static void resetPlayerLevels() {
		PLAYER_LEVELS = new ArrayList<PlayerLevels>();
	}
	
	public static void removePlayerLevels(Player player){
		for(int i = PLAYER_LEVELS.size() - 1; i >= 0; i--) {
			PlayerLevels levels = PLAYER_LEVELS.get(i);
			if(levels.getPlayer().equals(player)) {
				PLAYER_LEVELS.remove(i);
			}
		}
	}
	
	public static PlayerLevels generateFakePlayerLevels(Material material, int minBookshelves, boolean treasure) {
		return generateFakePlayerLevels(material, minBookshelves, treasure, 0);
	}
	
	public static PlayerLevels generateFakePlayerLevels(Material material, int minBookshelves, boolean treasure, int mobLevel) {
		int books = 16;
		if(ConfigUtils.isLevel50()) books = 24;
		int random = (int)(Math.random() * books) + minBookshelves;
		if(random >= books) random = books - 1;
		if(books < getMinBooks(mobLevel)) books = getMinBooks(mobLevel);
		PlayerLevels levels = new PlayerLevels(random, material, treasure);
		
		if(ConfigUtils.isLevel50() && random < 15) {
			while(levels.enchants.size() > 3) {
				levels.enchants.remove(3);
			}
		}
		
		return levels;
	}
	
	private static int getMinBooks(int mobLevel) {
		if(mobLevel > 30) {
			return 15;
		} else if (mobLevel > 18) {
			return 10;
		} else if (mobLevel > 0) {
			return 4;
		}
		return 0;
	}
	
	public static MerchantRecipe generateVillagerTrade(MerchantRecipe original) {
		int books = 16;
		if(ConfigUtils.isLevel50()) books = 24;
		int random = bellCurve(0, books, 4);
		if(random >= books) random = books - 1;
		PlayerLevels levels = new PlayerLevels(random, original.getResult().getType(), true);
		
		int[] levelPrice = getLevelPrice(original.getResult().getType());
		
		
		List<EnchantmentLevel> enchantmentLevel = levels.getEnchants().get(levelPrice[0]);
		int tries = 0;
		while(enchantmentLevel.size() == 0) {
			tries++;
			levelPrice[0] --;
			if(levelPrice[0] < 0) {
				levelPrice = getLevelPrice(original.getResult().getType());
			} else {
				levelPrice = getLevelPrice(original.getResult().getType(), levelPrice[0]);
			}
			enchantmentLevel = levels.getEnchants().get(levelPrice[0]);
			if(tries > 1000) return null;
		}
		
		Material mat = original.getResult().getType();
		if(!ConfigUtils.getEnchantedBook() && mat == Material.ENCHANTED_BOOK) {
			mat = Material.BOOK;
		}

		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		EnchantmentLevel enchant = enchantmentLevel.get(0);
		if(mat == Material.BOOK || mat == Material.ENCHANTED_BOOK) {
			if(enchant.getEnchant().isTreasure()) levelPrice[1] *= 2;
			if(levelPrice[1] > 64) levelPrice[1] = 64;
			ItemStack priceItem = new ItemStack(Material.EMERALD, levelPrice[1]);
			ingredients.add(new ItemStack(Material.BOOK));
			ingredients.add(priceItem);
		} else {
			ItemStack priceItem = new ItemStack(Material.EMERALD, levelPrice[1]);
			if(levelPrice[1] > 64) levelPrice[1] = 64;
			ingredients.add(priceItem);
		}
		
		MerchantRecipe recipe = new MerchantRecipe(Enchantments.addEnchantmentToItem(new ItemStack(mat), enchant.getEnchant(), enchant.getLevel()), 
				original.getUses(), original.getMaxUses(), original.hasExperienceReward());
		recipe.setIngredients(ingredients);
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			recipe.setVillagerExperience(original.getVillagerExperience());
		}
		return recipe;
	}

	private static int[] getLevelPrice(Material type) {
		int level = 0;
		int price = 0;
		if(ConfigUtils.isLevel50()) {
			if(type == Material.ENCHANTED_BOOK) {
				level = bellCurve(0, 6, 4);
				price = (level * 2) * (level) + (int)((Math.random() * (level * 3 + 7)) + 3);
			} else {
				level = bellCurve(1, 4, 1);
			}
		} else {
			if(type == Material.ENCHANTED_BOOK) {
				level = bellCurve(0, 3, 4);
				price = (level + 1) * (level * 2) * (level) + (int)((Math.random() * (level * 3 + 15)) + 3);
			} else {
				level = bellCurve(0, 2, 1);
			}
		}
		if(type != Material.ENCHANTED_BOOK) {
			switch(type) {
			case IRON_AXE:
			case IRON_SWORD:
			case IRON_PICKAXE:
			case IRON_SHOVEL:
			case FISHING_ROD:
			case BOW:
				price = (int) (Math.random() * 16 + 7);
				break;
			case DIAMOND_SWORD:
			case DIAMOND_SHOVEL:
			case DIAMOND_HELMET:
			case DIAMOND_BOOTS:
				price = (int) (Math.random() * 17 + 11);
				break;
			case DIAMOND_PICKAXE:
			case DIAMOND_AXE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_CHESTPLATE:
				price = (int) (Math.random() * 18 + 18);
				break;
			default:
				price = (level * 2) * (level) + (int)((Math.random() * (level * 3 + 7)) + 3);
				break;
			}
			if(ItemType.CROSSBOW.getItemTypes().contains(type)) {
				price = (int) (Math.random() * 16 + 7);
			}
		}
		return new int[] {level, price};
	}
	
	private static int[] getLevelPrice(Material type, int level) {
		int price = 0;
		if(type == Material.ENCHANTED_BOOK) {
			if(ConfigUtils.isLevel50()) {
				price = (level * 2) * (level) + (int)((Math.random() * (level * 3 + 7)) + 3);
			} else {
				price = (level + 1) * (level * 2) * (level) + (int)((Math.random() * (level * 3 + 15)) + 3);
			}
		} else {
			switch(type) {
			case IRON_AXE:
			case IRON_SWORD:
			case IRON_PICKAXE:
			case IRON_SHOVEL:
			case FISHING_ROD:
			case BOW:
				price = (int) (Math.random() * 16 + 7);
				break;
			case DIAMOND_SWORD:
			case DIAMOND_SHOVEL:
			case DIAMOND_HELMET:
			case DIAMOND_BOOTS:
				price = (int) (Math.random() * 17 + 11);
				break;
			case DIAMOND_PICKAXE:
			case DIAMOND_AXE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_CHESTPLATE:
				price = (int) (Math.random() * 18 + 18);
				break;
			default:
				price = (level * 2) * (level) + (int)((Math.random() * (level * 3 + 7)) + 3);
				break;
			}
			if(ItemType.CROSSBOW.getItemTypes().contains(type)) {
				price = (int) (Math.random() * 16 + 7);
			}
		}
		
		return new int[] {level, price};
	}
	
	private static int bellCurve(int min, int max, int curve) {
		double num = min;
	    for (int i = 0; i < curve; i++) {
	        num += Math.random() * ((double) max / (double) curve);
	    }
	    return (int) num;
	}
	
	public List<Integer> getLevelList(){
		return Arrays.asList(levelList.toArray(new Integer[] {}));
	}
}
