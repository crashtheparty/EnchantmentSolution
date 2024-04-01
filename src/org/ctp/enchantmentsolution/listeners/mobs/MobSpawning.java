package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.data.items.ItemSlot;
import org.ctp.crashapi.data.items.ItemSlotType;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MobSpawning implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (!ConfigString.USE_LOOT.getBoolean("mobs.use")) return;
		LivingEntity entity = event.getEntity();
		// dont do it for players, custom mobs, or for entities spawned with commands
		if (entity instanceof Player || event.getSpawnReason() == SpawnReason.DEFAULT || event.getSpawnReason() == SpawnReason.COMMAND) return;
		if (entity.getEquipment() != null) {
			ItemSlot[] slots = getEquippedAndType(entity);
			if (slots.length != 0) for (int i = 0; i < slots.length; i++) {
				ItemSlot slot = slots[i];
				ItemStack item = slot.getItem();
				// try to prevent causing issues with raids
				if (item != null && item.getType() == Material.WHITE_BANNER) {
					
				} else if (item != null && item.getItemMeta() != null && item.getItemMeta().hasEnchants()) slots[i] = new ItemSlot(GenerateUtils.generateMobSpawnLoot(item), slot.getType());
			}
			
			setEquippedAndType(entity, slots);
		}
	}
	
	public ItemSlot[] getEquippedAndType(LivingEntity entity) {
		ItemSlot[] equipped = new ItemSlot[0];
		if (entity.getEquipment() != null) {
			equipped = new ItemSlot[6];
			equipped[0] = new ItemSlot(entity.getEquipment().getHelmet(), ItemSlotType.HELMET);
			equipped[1] = new ItemSlot(entity.getEquipment().getChestplate(), ItemSlotType.CHESTPLATE);
			equipped[2] = new ItemSlot(entity.getEquipment().getLeggings(), ItemSlotType.LEGGINGS);
			equipped[3] = new ItemSlot(entity.getEquipment().getBoots(), ItemSlotType.BOOTS);
			equipped[4] = new ItemSlot(entity.getEquipment().getItemInMainHand(), ItemSlotType.MAIN_HAND);
			equipped[5] = new ItemSlot(entity.getEquipment().getItemInOffHand(), ItemSlotType.OFF_HAND);
		}
		return equipped;
	}
	
	public void setEquippedAndType(LivingEntity entity, ItemSlot[] equipped) {
		if (entity.getEquipment() != null) for (ItemSlot slot : equipped) {
			// try to prevent causing issues with raids
			if (slot.getItem().getType() == Material.WHITE_BANNER) continue;
			entity.getEquipment().setItem(slot.getType().getEquipmentSlot(), slot.getItem());
		}
	}
}
