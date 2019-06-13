package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.PlayerLevels;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.items.nms.ItemRepairType;

import com.google.common.collect.Multimap;

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
	
	public static void getSuspiciousStew(Player player, ItemStack item, PotionMeta meta) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if(item.getType().equals(Material.SUSPICIOUS_STEW)) {
				if(meta.hasCustomEffects()) {
					for(PotionEffect effect : meta.getCustomEffects()) {
						if(getBadPotions().contains(effect.getType())) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.THAT_FOOD_IS_FINE, "food");
							break;
						}
					}
				}
			}
		}
		return;
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
		if(first.getType() == Material.BOOK || first.getType() == Material.ENCHANTED_BOOK) {
			if(ConfigUtils.getEnchantedBook()) {
				combined = new ItemStack(Material.ENCHANTED_BOOK);
			} else {
				combined = new ItemStack(Material.BOOK);
			}
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
		combinedMeta.setLore(firstMeta.getLore());
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 1) {
			if(firstMeta.getAttributeModifiers() != null && !firstMeta.getAttributeModifiers().isEmpty()) {
				Iterator<Map.Entry<Attribute, AttributeModifier>> iterator = firstMeta.getAttributeModifiers().entries().iterator();
				while(iterator.hasNext()) {
					Entry<Attribute, AttributeModifier> next = iterator.next();
					
					if (next.getKey() == null || next.getValue() == null) {
					    iterator.remove();
					    continue;
					}
					Attribute attribute = Attribute.valueOf(next.getKey().name());
					UUID uuid = UUID.randomUUID();
					int tries = 0;
					while(containsAttribute(uuid, combinedMeta.getAttributeModifiers()) && tries <= 100) {
						uuid = UUID.randomUUID();
						tries++;
					}
					AttributeModifier modifier = new AttributeModifier(uuid, 
							next.getValue().getName(), next.getValue().getAmount(), next.getValue().getOperation(), next.getValue().getSlot());
					try {
						combinedMeta.addAttributeModifier(attribute, modifier);
					} catch (IllegalArgumentException ex) {
						if(tries <= 100) {
							ChatUtils.sendWarning("This shouldn't happen - It found a unique ID??");
							ChatUtils.sendWarning("Illegal Argument Exception when processing Attributes: ");
							ChatUtils.sendWarning("Issue with adding " + next.getKey().name() + " with modifier " + next.getValue().toString() + " to item.");
							Multimap<Attribute, AttributeModifier> modifiers = combinedMeta.getAttributeModifiers();
							Iterator<Entry<Attribute, AttributeModifier>> i = modifiers.entries().iterator();
							while(i.hasNext()) {
								Entry<Attribute, AttributeModifier> entry = i.next();
								ChatUtils.sendWarning("Possible conflict: " + entry.getKey().name() + " with modifier " + entry.getValue().toString() + ".");
							}
						}
					}
				}
			}
		}
		
		combined.setItemMeta(combinedMeta);
		
		combined = Enchantments.addEnchantmentsToItem(combined, enchantments);
		
		return combined;
	}
	
	private static boolean containsAttribute(UUID uuid, Multimap<Attribute, AttributeModifier> modifiers) {
		if(modifiers == null || modifiers.size() == 0 || modifiers.entries() == null) return false;
		Iterator<Entry<Attribute, AttributeModifier>> iterator = modifiers.entries().iterator();
		
		while(iterator.hasNext()) {
			Entry<Attribute, AttributeModifier> next = iterator.next();
			if(next.getValue().getUniqueId().equals(uuid)) {
				return true;
			}
		}
		
		return false;
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
		ItemStack returnItem = new ItemStack(item.getType());
		ItemStack duplicate = item.clone();
		ItemMeta returnItemMeta = returnItem.getItemMeta();
		ItemMeta duplicateMeta = duplicate.getItemMeta();
		
		returnItemMeta.setDisplayName(duplicateMeta.getDisplayName());
		returnItem.setItemMeta(returnItemMeta);
		DamageUtils.setDamage(returnItem, DamageUtils.getDamage(duplicateMeta));
		
		List<EnchantmentLevel> enchants = null;
		while(enchants == null) {
			int bookshelves = ConfigUtils.getBookshelvesFromType(type);
			boolean treasure = ConfigUtils.includeTreasureFromType(type);
			PlayerLevels levels = PlayerLevels.generateFakePlayerLevels(returnItem.getType(), bookshelves, treasure);
			int i = 0;
			while(i < 3) {
				int random = (int)(Math.random() * levels.getEnchants().size() + ConfigUtils.getLevelFromType(type));
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
