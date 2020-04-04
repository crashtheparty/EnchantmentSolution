package org.ctp.enchantmentsolution.utils.compatibility;

import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enums.CustomItemType;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.enums.ItemType;

import net.Indyuce.mmoitems.api.Type;
import net.mmogroup.mmolib.api.item.NBTItem;

public class MMOUtils {
	
	private static boolean isEnabled() {
		return EnchantmentSolution.getPlugin().getMMOItems();
	}

	public static String getMMOTypeString(ItemStack item) {
		if (!isEnabled()) return null;

		NBTItem nbt = NBTItem.get(item);
		Type type = nbt.getType();
		if(type == null) return null;
		return type.getId();
	}

	public static String getMMOTypeSetString(ItemStack item) {
		if (!isEnabled()) return null;

		NBTItem nbt = NBTItem.get(item);
		Type type = nbt.getType();
		if(type == null) return null;
		return type.getItemSet().name();
	}

	public static boolean check(ItemData item, ItemType itemType, CustomItemType mmo) {
		if (!isEnabled()) return false;
		
		if(item.getMMOType() == null || !Type.isValid(item.getMMOType())) return false;
		Type type = Type.get(item.getMMOType());
		String customString = itemType.getCustomString().split(":")[2];
		
		switch(mmo.name().toUpperCase()) {
			case "TYPE":
				return type.getId().equalsIgnoreCase(customString);
			case "TYPE_SET":
				return type.getItemSet().name().equalsIgnoreCase(customString);
		}
		return false;
	}
}
