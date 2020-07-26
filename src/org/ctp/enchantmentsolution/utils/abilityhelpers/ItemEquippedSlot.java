package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.enchantments.Enchantment;
import org.ctp.crashapi.item.ItemSlotType;

public class ItemEquippedSlot {

	public final static String ARMORED_ID_BASE = "cccccccc-fefe-fefe-fefe-00000000";
	public final static String GUNG_HO_ID_BASE = "eeeeeeee-ffff-ffff-ffff-00000000";
	private final ItemSlotType type;
	private final String name;
	private final UUID uuid;

	public ItemEquippedSlot(ItemSlotType type, String name, UUID uuid) {
		this.type = type;
		this.name = name;
		this.uuid = uuid;
	}

	public ItemSlotType getType() {
		return type;
	}

	public UUID getUuid() {
		return uuid;
	}

	public static List<ItemEquippedSlot> getArmorTypes(Enchantment enchant, UUID one, UUID two, UUID three, UUID four) {
		List<ItemEquippedSlot> uuids = new ArrayList<ItemEquippedSlot>();
		uuids.add(new ItemEquippedSlot(ItemSlotType.HELMET, "helmet_" + enchant.getKey().getKey(), one));
		uuids.add(new ItemEquippedSlot(ItemSlotType.CHESTPLATE, "chestplate_" + enchant.getKey().getKey(), two));
		uuids.add(new ItemEquippedSlot(ItemSlotType.LEGGINGS, "leggings_" + enchant.getKey().getKey(), three));
		uuids.add(new ItemEquippedSlot(ItemSlotType.BOOTS, "boots_" + enchant.getKey().getKey(), four));
		return uuids;
	}

	public static List<ItemEquippedSlot> getArmorTypes(String name, String uuidBase) {
		List<ItemEquippedSlot> uuids = new ArrayList<ItemEquippedSlot>();
		uuids.add(new ItemEquippedSlot(ItemSlotType.HELMET, name, UUID.fromString(uuidBase + "1000")));
		uuids.add(new ItemEquippedSlot(ItemSlotType.CHESTPLATE, name, UUID.fromString(uuidBase + "1100")));
		uuids.add(new ItemEquippedSlot(ItemSlotType.LEGGINGS, name, UUID.fromString(uuidBase + "1110")));
		uuids.add(new ItemEquippedSlot(ItemSlotType.BOOTS, name, UUID.fromString(uuidBase + "1111")));
		return uuids;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ItemEquippedSlot)) return false;
		ItemEquippedSlot slot = (ItemEquippedSlot) object;
		return getType() == slot.getType() && getUuid().equals(slot.getUuid()) && getName().equals(slot.getName());
	}
}
