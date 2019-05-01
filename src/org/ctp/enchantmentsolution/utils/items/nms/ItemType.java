package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public enum ItemType{
	HELMETS("helmets"), CHESTPLATES("chestplates"), LEGGINGS("leggings"), BOOTS("boots"), SWORDS("swords"), PICKAXES("pickaxes"), SHOVELS("shovels"), AXES("axes"), 
	HOES("hoes"), BOW("bow"), SHIELD("shield"), FISHING_ROD("fishing_rod"), SHEARS("shears"), FLINT_AND_STEEL("flint_and_steel"), 
	CARROT_ON_A_STICK("carrot_on_a_stick"), ELYTRA("elytra"), TRIDENT("trident"), RANGED("ranged"), ARMOR("armor"), TOOLS("tools"), MELEE("melee"), MISC("misc"), 
	WOODEN_TOOLS("wooden_tools"), STONE_TOOLS("stone_tools"), IRON_TOOLS("iron_tools"), GOLDEN_TOOLS("golden_tools"), DIAMOND_TOOLS("diamond_tools"), 
	LEATHER_ARMOR("leather_armor"), GOLDEN_ARMOR("golden_armor"), CHAINMAIL_ARMOR("chainmail_armor"), IRON_ARMOR("iron_armor"), DIAMOND_ARMOR("diamond_armor"), 
	CROSSBOW("crossbow"), BOOK("book"), ALL("all"), ENCHANTABLE("enchantable"), TURTLE_HELMET("turtle_helmet");
	
	private String type, display;
	private List<Material> itemTypes;
	
	ItemType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public List<Material> getItemTypes() {
		if(itemTypes == null) {
			itemTypes = getItemTypes(getType());
		}
		return itemTypes;
	}
	
	public String getDisplayName() {
		if(display == null) {
			display = getDisplayType(getType());
		}
		return display;
	}
	
	public static String getUnlocalizedName(Material material) {
		return (material.isBlock() ? "block" : "item" ) + ".minecraft." + material.name().toLowerCase();
	}
	
	public Map<Material, String> getUnlocalizedNames() {
		Map<Material, String> names = new HashMap<Material, String>();
		for(Material material : getItemTypes()) {
			names.put(material, getUnlocalizedName(material));
		}
		return names;
	}
	
	public static boolean hasItemType(Material mat) {
		for(ItemType type : values()) {
			if(type.getItemTypes().contains(mat)) {
				return true;
			}
		}
		return false;
	}
	
	private String getDisplayType(String type) {
		if (ALL.getType().equals(type)) {
			return "All";
		} else if (ARMOR.getType().equals(type)) {
			return "Armor";
		} else if (AXES.getType().equals(type)) {
			return "Axes";
		} else if (BOOK.getType().equals(type)) {
			return "Books";
		} else if (BOOTS.getType().equals(type)) {
			return "Boots";
		} else if (BOW.getType().equals(type)) {
			return "Bow";
		} else if (CARROT_ON_A_STICK.getType().equals(type)) {
			return "Carrot on a Stick";
		} else if (CHAINMAIL_ARMOR.getType().equals(type)) {
			return "Chainmail Armor";
		} else if (CHESTPLATES.getType().equals(type)) {
			return "Chestplates";
		} else if (CROSSBOW.getType().equals(type)) {
			return "Crossbow";
		} else if (DIAMOND_ARMOR.getType().equals(type)) {
			return "Diamond Armor";
		} else if (DIAMOND_TOOLS.getType().equals(type)) {
			return "Diamond Tools";
		} else if (ELYTRA.getType().equals(type)) {
			return "Elytra";
		} else if (ENCHANTABLE.getType().equals(type)) {
			return "Enchantable";
		} else if (FISHING_ROD.getType().equals(type)) {
			return "Fishing Rod";
		} else if (FLINT_AND_STEEL.getType().equals(type)) {
			return "Flint and Steel";
		} else if (GOLDEN_ARMOR.getType().equals(type)) {
			return "Golden Armor";
		} else if (GOLDEN_TOOLS.getType().equals(type)) {
			return "Golden Tools";
		} else if (HELMETS.getType().equals(type)) {
			return "Helmets";
		} else if (HOES.getType().equals(type)) {
			return "Hoes";
		} else if (IRON_ARMOR.getType().equals(type)) {
			return "Iron Armor";
		} else if (IRON_TOOLS.getType().equals(type)) {
			return "Iron Tools";
		} else if (LEATHER_ARMOR.getType().equals(type)) {
			return "Leather Armor";
		} else if (LEGGINGS.getType().equals(type)) {
			return "Leggings";
		} else if (MELEE.getType().equals(type)) {
			return "Melee Weapons";
		} else if (MISC.getType().equals(type)) {
			return "Misc";
		} else if (PICKAXES.getType().equals(type)) {
			return "Pickaxes";
		} else if (RANGED.getType().equals(type)) {
			return "Ranged Weapons";
		} else if (SHEARS.getType().equals(type)) {
			return "Shears";
		} else if (SHIELD.getType().equals(type)) {
			return "Shield";
		} else if (SHOVELS.getType().equals(type)) {
			return "Shovels";
		} else if (STONE_TOOLS.getType().equals(type)) {
			return "Stone Tools";
		} else if (SWORDS.getType().equals(type)) {
			return "Swords";
		} else if (TOOLS.getType().equals(type)) {
			return "Tools";
		} else if (TRIDENT.getType().equals(type)) {
			return "Trident";
		} else if (TURTLE_HELMET.getType().equals(type)) {
			return "Turtle Helmet";
		} else if (WOODEN_TOOLS.getType().equals(type)) {
			return "Wooden Tools";
		}
		return null;
	}
	
	private List<Material> getItemTypes(String type){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return ItemType_v1_13.getItemTypes(type);
		case 4:
			return ItemType_v1_14.getItemTypes(type);
		}
		return null;
	}
	
	private static class ItemType_v1_13{
		public static List<Material> getItemTypes(String type){
			List<Material> itemTypes = new ArrayList<Material>();
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
			if (ALL.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(ARMOR.getType()));
				itemTypes.addAll(getItemTypes(TOOLS.getType()));
				itemTypes.addAll(getItemTypes(MELEE.getType()));
				itemTypes.addAll(getItemTypes(RANGED.getType()));
				itemTypes.addAll(getItemTypes(MISC.getType()));
				return itemTypes;
			} else if (ARMOR.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(HELMETS.getType()));
				itemTypes.addAll(getItemTypes(CHESTPLATES.getType()));
				itemTypes.addAll(getItemTypes(LEGGINGS.getType()));
				itemTypes.addAll(getItemTypes(BOOTS.getType()));
				return itemTypes;
			} else if (AXES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE,
						Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE));
				return itemTypes;
			} else if (BOOK.getType().equals(type)) {
				return itemTypes;
			} else if (BOOTS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_BOOTS,
						Material.CHAINMAIL_BOOTS, Material.GOLDEN_BOOTS,
						Material.IRON_BOOTS, Material.LEATHER_BOOTS));
				return itemTypes;
			} else if (BOW.getType().equals(type)) {
				itemTypes.add(Material.BOW);
				return itemTypes;
			} else if (CARROT_ON_A_STICK.getType().equals(type)) {
				itemTypes.add(Material.CARROT_ON_A_STICK);
				return itemTypes;
			} else if (CHAINMAIL_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_HELMET,
						Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS,
						Material.CHAINMAIL_BOOTS));
				return itemTypes;
			} else if (CHESTPLATES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_CHESTPLATE,
						Material.CHAINMAIL_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
						Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE));
				return itemTypes;
			} else if (CROSSBOW.getType().equals(type)) {
				return itemTypes;
			} else if (DIAMOND_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HELMET,
						Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
						Material.DIAMOND_BOOTS));
				return itemTypes;
			} else if (DIAMOND_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_AXE,
						Material.DIAMOND_SWORD, Material.DIAMOND_SHOVEL,
						Material.DIAMOND_PICKAXE));
				return itemTypes;
			} else if (ELYTRA.getType().equals(type)) {
				itemTypes.add(Material.ELYTRA);
				return itemTypes;
			} else if (ENCHANTABLE.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(ARMOR.getType()));
				itemTypes.addAll(getItemTypes(TOOLS.getType()));
				itemTypes.addAll(getItemTypes(MELEE.getType()));
				itemTypes.addAll(getItemTypes(RANGED.getType()));
				itemTypes.addAll(getItemTypes(SHIELD.getType()));
				itemTypes.addAll(getItemTypes(FISHING_ROD.getType()));
				itemTypes.addAll(getItemTypes(ELYTRA.getType()));
				return itemTypes;
			} else if (FISHING_ROD.getType().equals(type)) {
				itemTypes.add(Material.FISHING_ROD);
				return itemTypes;
			} else if (FLINT_AND_STEEL.getType().equals(type)) {
				itemTypes.add(Material.FLINT_AND_STEEL);
				return itemTypes;
			} else if (GOLDEN_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.GOLDEN_HELMET,
						Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS,
						Material.GOLDEN_BOOTS));
				return itemTypes;
			} else if (GOLDEN_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
						Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE));
				return itemTypes;
			} else if (HELMETS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HELMET,
						Material.CHAINMAIL_HELMET, Material.GOLDEN_HELMET,
						Material.IRON_HELMET, Material.LEATHER_HELMET, Material.TURTLE_HELMET));
				return itemTypes;
			} else if (HOES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HOE, Material.GOLDEN_HOE,
						Material.IRON_HOE, Material.STONE_HOE, Material.WOODEN_HOE));
				return itemTypes;
			} else if (IRON_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.IRON_HELMET,
						Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS,
						Material.IRON_BOOTS));
				return itemTypes;
			} else if (IRON_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.IRON_AXE, Material.IRON_SWORD,
						Material.IRON_SHOVEL, Material.IRON_PICKAXE));
				return itemTypes;
			} else if (LEATHER_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.LEATHER_HELMET,
						Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
						Material.LEATHER_BOOTS));
				return itemTypes;
			} else if (LEGGINGS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_LEGGINGS,
						Material.CHAINMAIL_LEGGINGS, Material.GOLDEN_LEGGINGS,
						Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS));
				return itemTypes;
			} else if (MELEE.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(AXES.getType()));
				itemTypes.addAll(getItemTypes(SWORDS.getType()));
				return itemTypes;
			} else if (MISC.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(SHIELD.getType()));
				itemTypes.addAll(getItemTypes(FISHING_ROD.getType()));
				itemTypes.addAll(getItemTypes(FLINT_AND_STEEL.getType()));
				itemTypes.addAll(getItemTypes(CARROT_ON_A_STICK.getType()));
				itemTypes.addAll(getItemTypes(ELYTRA.getType()));
				itemTypes.addAll(getItemTypes(SHEARS.getType()));
				itemTypes.addAll(getItemTypes(HOES.getType()));
				return itemTypes;
			} else if (PICKAXES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_PICKAXE,
						Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
						Material.STONE_PICKAXE, Material.WOODEN_PICKAXE));
				return itemTypes;
			} else if (RANGED.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(BOW.getType()));
				itemTypes.addAll(getItemTypes(CROSSBOW.getType()));
				itemTypes.addAll(getItemTypes(TRIDENT.getType()));
				return itemTypes;
			} else if (SHEARS.getType().equals(type)) {
				itemTypes.add(Material.SHEARS);
				return itemTypes;
			} else if (SHIELD.getType().equals(type)) {
				itemTypes.add(Material.SHIELD);
				return itemTypes;
			} else if (SHOVELS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_SHOVEL,
						Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.STONE_SHOVEL,
						Material.WOODEN_SHOVEL));
				return itemTypes;
			} else if (STONE_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.STONE_AXE,
						Material.STONE_SWORD, Material.STONE_SHOVEL,
						Material.STONE_PICKAXE));
				return itemTypes;
			} else if (SWORDS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_SWORD,
						Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD,
						Material.WOODEN_SWORD));
				return itemTypes;
			} else if (TOOLS.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(PICKAXES.getType()));
				itemTypes.addAll(getItemTypes(AXES.getType()));
				itemTypes.addAll(getItemTypes(SHOVELS.getType()));
				return itemTypes;
			} else if (TRIDENT.getType().equals(type)) {
				itemTypes.add(Material.TRIDENT);
				return itemTypes;
			} else if (TURTLE_HELMET.getType().equals(type)) {
				itemTypes.add(Material.TURTLE_HELMET);
				return itemTypes;
			} else if (WOODEN_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.WOODEN_AXE, Material.WOODEN_SWORD,
						Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE));
				return itemTypes;
			}
			return null;
		}
	}
	
	private static class ItemType_v1_14{
		public static List<Material> getItemTypes(String type){
			List<Material> itemTypes = new ArrayList<Material>();
			itemTypes.addAll(Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK));
			if (ALL.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(ARMOR.getType()));
				itemTypes.addAll(getItemTypes(TOOLS.getType()));
				itemTypes.addAll(getItemTypes(MELEE.getType()));
				itemTypes.addAll(getItemTypes(RANGED.getType()));
				itemTypes.addAll(getItemTypes(MISC.getType()));
				return itemTypes;
			} else if (ARMOR.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(HELMETS.getType()));
				itemTypes.addAll(getItemTypes(CHESTPLATES.getType()));
				itemTypes.addAll(getItemTypes(LEGGINGS.getType()));
				itemTypes.addAll(getItemTypes(BOOTS.getType()));
				return itemTypes;
			} else if (AXES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_AXE, Material.GOLDEN_AXE,
						Material.IRON_AXE, Material.STONE_AXE, Material.WOODEN_AXE));
				return itemTypes;
			} else if (BOOK.getType().equals(type)) {
				return itemTypes;
			} else if (BOOTS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_BOOTS,
						Material.CHAINMAIL_BOOTS, Material.GOLDEN_BOOTS,
						Material.IRON_BOOTS, Material.LEATHER_BOOTS));
				return itemTypes;
			} else if (BOW.getType().equals(type)) {
				itemTypes.add(Material.BOW);
				return itemTypes;
			} else if (CARROT_ON_A_STICK.getType().equals(type)) {
				itemTypes.add(Material.CARROT_ON_A_STICK);
				return itemTypes;
			} else if (CHAINMAIL_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.CHAINMAIL_HELMET,
						Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS,
						Material.CHAINMAIL_BOOTS));
				return itemTypes;
			} else if (CHESTPLATES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_CHESTPLATE,
						Material.CHAINMAIL_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
						Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE));
				return itemTypes;
			} else if (CROSSBOW.getType().equals(type)) {
				itemTypes.add(Material.CROSSBOW);
				return itemTypes;
			} else if (DIAMOND_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HELMET,
						Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
						Material.DIAMOND_BOOTS));
				return itemTypes;
			} else if (DIAMOND_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_AXE,
						Material.DIAMOND_SWORD, Material.DIAMOND_SHOVEL,
						Material.DIAMOND_PICKAXE));
				return itemTypes;
			} else if (ELYTRA.getType().equals(type)) {
				itemTypes.add(Material.ELYTRA);
				return itemTypes;
			} else if (ENCHANTABLE.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(ARMOR.getType()));
				itemTypes.addAll(getItemTypes(TOOLS.getType()));
				itemTypes.addAll(getItemTypes(MELEE.getType()));
				itemTypes.addAll(getItemTypes(RANGED.getType()));
				itemTypes.addAll(getItemTypes(SHIELD.getType()));
				itemTypes.addAll(getItemTypes(FISHING_ROD.getType()));
				itemTypes.addAll(getItemTypes(ELYTRA.getType()));
				return itemTypes;
			} else if (FISHING_ROD.getType().equals(type)) {
				itemTypes.add(Material.FISHING_ROD);
				return itemTypes;
			} else if (FLINT_AND_STEEL.getType().equals(type)) {
				itemTypes.add(Material.FLINT_AND_STEEL);
				return itemTypes;
			} else if (GOLDEN_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.GOLDEN_HELMET,
						Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS,
						Material.GOLDEN_BOOTS));
				return itemTypes;
			} else if (GOLDEN_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.GOLDEN_AXE, Material.GOLDEN_SWORD,
						Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE));
				return itemTypes;
			} else if (HELMETS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HELMET,
						Material.CHAINMAIL_HELMET, Material.GOLDEN_HELMET,
						Material.IRON_HELMET, Material.LEATHER_HELMET, Material.TURTLE_HELMET));
				return itemTypes;
			} else if (HOES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_HOE, Material.GOLDEN_HOE,
						Material.IRON_HOE, Material.STONE_HOE, Material.WOODEN_HOE));
				return itemTypes;
			} else if (IRON_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.IRON_HELMET,
						Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS,
						Material.IRON_BOOTS));
				return itemTypes;
			} else if (IRON_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.IRON_AXE, Material.IRON_SWORD,
						Material.IRON_SHOVEL, Material.IRON_PICKAXE));
				return itemTypes;
			} else if (LEATHER_ARMOR.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.LEATHER_HELMET,
						Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
						Material.LEATHER_BOOTS));
				return itemTypes;
			} else if (LEGGINGS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_LEGGINGS,
						Material.CHAINMAIL_LEGGINGS, Material.GOLDEN_LEGGINGS,
						Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS));
				return itemTypes;
			} else if (MELEE.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(AXES.getType()));
				itemTypes.addAll(getItemTypes(SWORDS.getType()));
				return itemTypes;
			} else if (MISC.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(SHIELD.getType()));
				itemTypes.addAll(getItemTypes(FISHING_ROD.getType()));
				itemTypes.addAll(getItemTypes(FLINT_AND_STEEL.getType()));
				itemTypes.addAll(getItemTypes(CARROT_ON_A_STICK.getType()));
				itemTypes.addAll(getItemTypes(ELYTRA.getType()));
				itemTypes.addAll(getItemTypes(SHEARS.getType()));
				itemTypes.addAll(getItemTypes(HOES.getType()));
				return itemTypes;
			} else if (PICKAXES.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_PICKAXE,
						Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
						Material.STONE_PICKAXE, Material.WOODEN_PICKAXE));
				return itemTypes;
			} else if (RANGED.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(BOW.getType()));
				itemTypes.addAll(getItemTypes(CROSSBOW.getType()));
				itemTypes.addAll(getItemTypes(TRIDENT.getType()));
				return itemTypes;
			} else if (SHEARS.getType().equals(type)) {
				itemTypes.add(Material.SHEARS);
				return itemTypes;
			} else if (SHIELD.getType().equals(type)) {
				itemTypes.add(Material.SHIELD);
				return itemTypes;
			} else if (SHOVELS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_SHOVEL,
						Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.STONE_SHOVEL,
						Material.WOODEN_SHOVEL));
				return itemTypes;
			} else if (STONE_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.STONE_AXE,
						Material.STONE_SWORD, Material.STONE_SHOVEL,
						Material.STONE_PICKAXE));
				return itemTypes;
			} else if (SWORDS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.DIAMOND_SWORD,
						Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD,
						Material.WOODEN_SWORD));
				return itemTypes;
			} else if (TOOLS.getType().equals(type)) {
				itemTypes.addAll(getItemTypes(PICKAXES.getType()));
				itemTypes.addAll(getItemTypes(AXES.getType()));
				itemTypes.addAll(getItemTypes(SHOVELS.getType()));
				return itemTypes;
			} else if (TRIDENT.getType().equals(type)) {
				itemTypes.add(Material.TRIDENT);
				return itemTypes;
			} else if (TURTLE_HELMET.getType().equals(type)) {
				itemTypes.add(Material.TURTLE_HELMET);
				return itemTypes;
			} else if (WOODEN_TOOLS.getType().equals(type)) {
				itemTypes.addAll(Arrays.asList(Material.WOODEN_AXE, Material.WOODEN_SWORD,
						Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE));
				return itemTypes;
			}
			return null;
		}
	}
	
}
