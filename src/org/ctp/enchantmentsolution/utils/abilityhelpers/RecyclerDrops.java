package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Material;
import org.ctp.crashapi.item.MatData;

public enum RecyclerDrops {
	LEATHER(0, 2), BEEF(-1, 1), COOKED_BEEF(0, 2), STRING(0, 1), FEATHER(0, 1), CHICKEN(-1, 1), COOKED_CHICKEN(0, 2), COD(-2, 1),
	COOKED_COD(-1, 1), BAMBOO(-1, 1), PORKCHOP(-1, 1), COOKED_PORKCHOP(0, 2), SALMON(-2, 1), COOKED_SALMON(-1, 1), RABBIT(-1, 1),
	COOKED_RABBIT(0, 2), PUFFERFISH(-1, 1), RABBIT_HIDE(0, 1), MUTTON(-1, 1), COOKED_MUTTON(0, 2), BONE(0, 2), INK_SAC(0, 3),
	TROPICAL_FISH(-1, 1), ROTTEN_FLESH(0, 2), BLAZE_ROD(1, 3), SPIDER_EYE(0, 2), GUNPOWDER(0, 2), PRISMARINE_SHARD(-1, 2),
	PRISMARINE_CRYSTALS(-1, 2), ENDER_PEARL(1, 3), GHAST_TEAR(1, 4), MAGMA_CREAM(1, 3), PHANTOM_MEMBRANE(1, 3), ARROW(-1, 1),
	SHULKER_SHELL(2, 4), SLIME_BALL(0, 1), EMERALD(0, 3), GLASS_BOTTLE(-2, 1), GLOWSTONE_DUST(-1, 2), REDSTONE(0, 2), SUGAR(0, 1), COAL(0, 2),
	GOLD_NUGGET(0, 1);

	private MatData matData;
	private int minExp, maxExp;

	private RecyclerDrops(int minExp, int maxExp) {
		matData = new MatData(name());
		this.minExp = minExp;
		this.maxExp = maxExp;
	}

	public String getMaterialString() {
		return matData.getMaterialName();
	}

	public Material getMaterial() {
		return matData.getMaterial();
	}

	public int getMinExp() {
		return minExp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public int getRandomExp() {
		int rand = (int) (Math.random() * (maxExp - minExp + 1) + minExp);
		return rand > 0 ? rand : 0;
	}

	public static boolean isRecycleable(Material material) {
		for(RecyclerDrops value: values())
			if (value.getMaterial() == material) return true;
		return false;
	}

	public static int getExperience(Material material) {
		for(RecyclerDrops value: values())
			if (value.getMaterial() == material) return value.getRandomExp();
		return 0;
	}
}
