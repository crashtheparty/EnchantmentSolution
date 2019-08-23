package org.ctp.enchantmentsolution.nms.itemname;

import org.bukkit.Material;

public enum GermanNames {
	BOOK("Buch"), BOW("Bogen"), CARROT_ON_A_STICK("Karottenrute"), CHAINMAIL_BOOTS("Kettenstiefel"), CHAINMAIL_CHESTPLATE("Kettenhemd"), 
	CHAINMAIL_HELMET("Kettenhaube"), CHAINMAIL_LEGGINGS("Kettenhose"), CROSSBOW("Armbrust"), DIAMOND_AXE("Diamantaxt"), DIAMOND_BOOTS("Diamantstiefel"), 
	DIAMOND_CHESTPLATE("Diamantharnisch"), DIAMOND_HELMET("Diamanthelm"), DIAMOND_HOE("Diamanthacke"), DIAMOND_LEGGINGS("Diamantbeinschutz"), 
	DIAMOND_PICKAXE("Diamantspitzhacke"), DIAMOND_SHOVEL("Diamantschaufel"), DIAMOND_SWORD("Diamantschwert"), ELYTRA("Elytren"), ENCHANTED_BOOK("Verzaubertes Buch"),
	FISHING_ROD("Angel"), FLINT_AND_STEEL("Feuerzeug"), GOLDEN_AXE("Goldaxt"), GOLDEN_BOOTS("Goldstiefel"), GOLDEN_CHESTPLATE("Goldharnisch"), 
	GOLDEN_HELMET("Goldhelm"), GOLDEN_HOE("Goldhacke"), GOLDEN_LEGGINGS("Goldbeinschutz"), GOLDEN_PICKAXE("Goldspitzhacke"), GOLDEN_SHOVEL("Goldschaufel"), 
	GOLDEN_SWORD("Goldschwert"), IRON_AXE("Eisenaxt"), IRON_BOOTS("Eisenstiefel"), IRON_CHESTPLATE("Eisenharnisch"), IRON_HELMET("Eisenhelm"), IRON_HOE("Eisenhacke"), 
	IRON_LEGGINGS("Eisenbeinschutz"), IRON_PICKAXE("Eisenspitzhacke"), IRON_SHOVEL("Eisenschaufel"), IRON_SWORD("Eisenschwert"), LEATHER_BOOTS("Lederstiefel"), 
	LEATHER_CHESTPLATE("Lederjacke"), LEATHER_HELMET("Lederkappe"), LEATHER_LEGGINGS("Lederhose"), SHEARS("Schere"), SHIELD("Schild"), STONE_AXE("Steinaxt"), 
	STONE_HOE("Steinhacke"), STONE_PICKAXE("Steinspitzhacke"),STONE_SHOVEL("Steinschaufel"), STONE_SWORD("Steinschwert"), TRIDENT("Dreizack"), 
	TURTLE_HELMET("Schildkrötenpanzer"), WOODEN_AXE("Holzaxt"), WOODEN_HOE("Holzhacke"), WOODEN_PICKAXE("Holzspitzhacke"), WOODEN_SHOVEL("Holzschaufel"), 
	WOODEN_SWORD("Holzschwert");
	
	private String name;
	
	GermanNames(String name) {
		this.name = name;
	}
	
	public static String getName(Material mat) {
		for(GermanNames name : values()) {
			Material m = name.getMaterial();
			if(m != null && m == mat) {
				return name.getName();
			}
		}
		return null;
	}

	public Material getMaterial() {
		try {
			return Material.valueOf(name());
		} catch (Exception ex) {
			
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
