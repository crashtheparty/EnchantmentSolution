package org.ctp.enchantmentsolution.utils.items.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum FortuneLeaves {
	ACACIA_LEAVES(), BIRCH_LEAVES(), DARK_OAK_LEAVES(), JUNGLE_LEAVES(), OAK_LEAVES(), SPRUCE_LEAVES();
	
	private List<Double> saplingChance = new ArrayList<Double>();
	private List<Double> appleChance = new ArrayList<Double>();
	private String sapling;
	
	FortuneLeaves(){
		switch(this.name()) {
			case "JUNGLE_LEAVES":
				sapling = "JUNGLE_SAPLING";
				saplingChance.add(1.0D / 40.0D);
				saplingChance.add(1.0D / 36.0D);
				saplingChance.add(1.0D / 32.0D);
				saplingChance.add(1.0D / 24.0D);
				saplingChance.add(1.0D / 16.0D);
				saplingChance.add(1.0D / 10.0D);
				break;
			case "OAK_LEAVES":
				if(sapling == null) {
					sapling = "OAK_SAPLING";
				}
			case "DARK_OAK_LEAVES":
				if(sapling == null) {
					sapling = "DARK_OAK_SAPLING";
				}
				appleChance.add(1.0D / 200.0D);
				appleChance.add(1.0D / 180.0D);
				appleChance.add(1.0D / 160.0D);
				appleChance.add(1.0D / 120.0D);
				appleChance.add(1.0D / 100.0D);
				appleChance.add(1.0D / 80.0D);
			case "ACACIA_LEAVES":
				if(sapling == null) {
					sapling = "ACACIA_SAPLING";
				}
			case "BIRCH_LEAVES":
				if(sapling == null) {
					sapling = "BIRCH_SAPLING";
				}
			case "SPRUCE_LEAVES":
				if(sapling == null) {
					sapling = "SPRUCE_SAPLING";
				}
				saplingChance.add(1.0D / 20.0D);
				saplingChance.add(1.0D / 16.0D);
				saplingChance.add(1.0D / 12.0D);
				saplingChance.add(1.0D / 10.0D);
				saplingChance.add(1.0D / 8.0D);
				saplingChance.add(1.0D / 5.0D);
				break;
			default:
				break;
			
			}
	}
	
	public Material getSapling() {
		try {
			return Material.valueOf(sapling);
		} catch (Exception ex) {
			
		}
		return null;
	}
	
	public double getSaplingChance(int level) {
		return saplingChance.size() > level ? saplingChance.get(level) : saplingChance.get(saplingChance.size() - 1);
	}
	
	public double getAppleChance(int level) {
		return appleChance.size() == 0 ? 0 : appleChance.size() > level ? appleChance.get(level) : appleChance.get(appleChance.size() - 1);
	}
}
