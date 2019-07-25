package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemRepairType;

public class ItemUtils {
	
	private static List<Material> REPAIR_MATERIALS = Arrays.asList(Material.DIAMOND, Material.IRON_INGOT, Material.GOLD_INGOT, Material.COBBLESTONE, 
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, 
			Material.LEATHER, Material.PHANTOM_MEMBRANE, Material.STRING);
	
	private static List<Material> SHULKER_BOXES = Arrays.asList(Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX,
			Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX,
			Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX,
			Material.LIGHT_GRAY_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.SHULKER_BOX);

	private static List<PotionEffectType> BAD_POTIONS = Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM,
			PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.UNLUCK,
			PotionEffectType.WEAKNESS, PotionEffectType.WITHER);
	
	public static List<Material> getRepairMaterials() {
		return REPAIR_MATERIALS;
	}
	
	public static List<PotionEffectType> getBadPotions(){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			PotionEffectType[] types = BAD_POTIONS.toArray(new PotionEffectType[BAD_POTIONS.size() + 1]);
			types[types.length - 1] = PotionEffectType.BAD_OMEN;
			return Arrays.asList(types);
		}
		return BAD_POTIONS;
	}
	
	public static List<String> getRepairMaterialsStrings(){
		List<String> names = new ArrayList<String>();
		for(ItemRepairType type : ItemRepairType.getValues()) {
			for(Material m : type.getRepairTypes()) {
				if(!names.contains(m.name())) {
					names.add(m.name());
				}
			}
		}
		return names;
	}
	
	public static void giveItemToPlayer(Player player, ItemStack item, Location fallback, boolean statistic) {
		HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
		int amount = item.getAmount();
		leftOver.putAll((player.getInventory().addItem(item)));
		if (!leftOver.isEmpty()) {
			for (Iterator<java.util.Map.Entry<Integer, ItemStack>> it = leftOver.entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry<Integer, ItemStack> e = it.next();
				amount -= e.getValue().getAmount();
				fallback.add(0.5, 0.5, 0.5);
				Item droppedItem = player.getWorld().dropItem(
						fallback,
						e.getValue());
				droppedItem.setVelocity(new Vector(0,0,0));
				droppedItem.teleport(fallback);
			}
		}
		if(amount > 0 && statistic) {
			player.incrementStatistic(Statistic.PICKUP, item.getType(), amount);
		}
	}
	
	public static void dropItem(ItemStack item, Location loc) {
		Location location = loc.clone();
		Item droppedItem = location.getWorld().dropItem(
				location,
				item);
		droppedItem.setVelocity(new Vector(0,0,0));
		droppedItem.teleport(location);
	}
	
	public static ItemStack addNMSEnchantment(ItemStack item, String type) {
		ItemStack duplicate = item.clone();
		ItemMeta duplicateMeta = duplicate.getItemMeta();
		
		duplicateMeta.setDisplayName(duplicateMeta.getDisplayName());
		duplicate.setItemMeta(duplicateMeta);
		DamageUtils.setDamage(duplicate, DamageUtils.getDamage(duplicateMeta));
		
		List<EnchantmentLevel> enchants = null;
		while(enchants == null) {
			PlayerLevels levels = PlayerLevels.generateFakePlayerLevels(duplicate.getType(), 
					ConfigUtils.getBookshelvesFromType(type), ConfigUtils.includeTreasureFromType(type));
			int i = 0;
			while(i < 3) {
				int size = levels.getEnchants().size();
				int random = (int)(Math.random() * size + ConfigUtils.getLevelFromType(type));
				if(random > size - 1) random = size - 1;
				if(levels.getEnchants().get(random).size() > 0) {
					enchants = levels.getEnchants().get(random);
					break;
				}
				i++;
			}
			if(i >= 3) break;
		}
		
		duplicate = Enchantments.addEnchantmentsToItem(duplicate, enchants);
		
		return duplicate;
	}

	public static List<Material> getShulkerBoxes() {
		return SHULKER_BOXES;
	}
}
