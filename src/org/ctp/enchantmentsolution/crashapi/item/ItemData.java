package org.ctp.enchantmentsolution.crashapi.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.crashapi.compatibility.MMOUtils;

public class ItemData {

	private final ItemStack item;
	private final Material material;
	private final String mmoType, mmoTypeSet;

	public ItemData(ItemStack item) {
		this.item = item;
		if (item != null) {
			material = item.getType();
			mmoType = MMOUtils.getMMOTypeString(item);
			mmoTypeSet = MMOUtils.getMMOTypeSetString(item);
		} else {
			material = Material.AIR;
			mmoType = null;
			mmoTypeSet = null;
		}
	}

	public ItemData(Material material, String mmoType, String mmoTypeSet) {
		this.material = material;
		this.mmoType = mmoType == null ? null : mmoType.equals("null") ? null : mmoType;
		this.mmoTypeSet = mmoTypeSet == null ? null : mmoTypeSet.equals("null") ? null : mmoTypeSet;
		item = null;
	}

	public ItemStack getItem() {
		return item;
	}

	public Material getMaterial() {
		return material;
	}

	public String getMMOType() {
		return mmoType;
	}

	public String getMMOTypeSet() {
		return mmoTypeSet;
	}

	@Override
	public String toString() {
		return getMaterial() + " " + getMMOType() + " " + getMMOTypeSet();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemData) {
			ItemData data = (ItemData) obj;
			if (data.getMMOType() == null && getMMOType() == null) return data.getMaterial() == getMaterial();
			if (data.getMMOType() != null && getMMOType() == null) return false;
			if (data.getMMOType() == null && getMMOType() != null) return false;
			if (data.getMMOTypeSet() == null && getMMOTypeSet() == null) return data.getMaterial() == getMaterial();
			if (data.getMMOTypeSet() != null && getMMOTypeSet() == null) return false;
			if (data.getMMOTypeSet() == null && getMMOTypeSet() != null) return false;

			return data.getMaterial() == getMaterial() && data.getMMOType().equals(mmoType) && data.getMMOTypeSet().equals(mmoTypeSet);
		}
		return false;
	}

	public static boolean contains(List<ItemData> data, Material material) {
		for(ItemData d: data)
			if (d.getMaterial() == material) return true;
		return false;
	}
}
