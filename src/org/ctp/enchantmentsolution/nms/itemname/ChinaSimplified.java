package org.ctp.enchantmentsolution.nms.itemname;

import org.bukkit.Material;

public enum ChinaSimplified {
	BOOK("书"), BOW("弓"), CARROT_ON_A_STICK("萝卜钓竿"), CHAINMAIL_BOOTS("锁链鞋子"), CHAINMAIL_CHESTPLATE("锁链胸甲"), CHAINMAIL_HELMET("锁链头盔"), CROSSBOW("弓"), 
	CHAINMAIL_LEGGINGS("锁链护腿"), DIAMOND_AXE("钻石斧头"), DIAMOND_BOOTS("钻石鞋子"), DIAMOND_CHESTPLATE("钻石胸甲"), DIAMOND_HELMET("钻石头盔"), DIAMOND_HOE("钻石锄头"), 
	DIAMOND_LEGGINGS("钻石护腿"), DIAMOND_PICKAXE("钻石镐"), DIAMOND_SHOVEL("钻石铲"), DIAMOND_SWORD("钻石剑"), ELYTRA("鞘翅"), ENCHANTED_BOOK("附魔书"), 
	FISHING_ROD("钓鱼竿"), FLINT_AND_STEEL("打火石"), GOLDEN_AXE("金斧头"), GOLDEN_BOOTS("金靴子"), GOLDEN_CHESTPLATE("金胸甲"), GOLDEN_HELMET("金头盔"), 
	GOLDEN_HOE("金锄头"), GOLDEN_LEGGINGS("金护腿"), GOLDEN_PICKAXE("金镐"), GOLDEN_SHOVEL("金铲"), GOLDEN_SWORD("金剑"), IRON_AXE("铁斧头"), IRON_BOOTS("铁鞋子"), 
	IRON_CHESTPLATE("铁胸甲"), IRON_HELMET("铁头盔"), IRON_HOE("铁锄头"), IRON_LEGGINGS("铁护腿"), IRON_PICKAXE("铁镐"), IRON_SHOVEL("铁铲"), IRON_SWORD("铁剑"), 
	LEATHER_BOOTS("皮革鞋子"), LEATHER_CHESTPLATE("皮革上衣"), LEATHER_HELMET("皮革帽子"), LEATHER_LEGGINGS("皮革裤子"), SHEARS("剪刀"), SHIELD("盾牌"), STONE_AXE("石斧头"), 
	STONE_HOE("石锄"), STONE_PICKAXE("石镐"),STONE_SHOVEL("石铲"), STONE_SWORD("石剑"), TRIDENT("三叉戟"), TURTLE_HELMET("龟壳"), WOODEN_AXE("木斧头"), 
	WOODEN_HOE("木锄头"), WOODEN_PICKAXE("木镐"), WOODEN_SHOVEL("木铲"), WOODEN_SWORD("木剑");
	
	private String name;
	
	ChinaSimplified(String name) {
		this.name = name;
	}
	
	public static String getName(Material mat) {
		for(ChinaSimplified name : values()) {
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
