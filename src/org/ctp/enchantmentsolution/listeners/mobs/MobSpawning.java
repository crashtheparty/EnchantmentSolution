package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public class MobSpawning implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (!ConfigString.USE_ALL_MOB_LOOT.getBoolean()) return;
		LivingEntity entity = event.getEntity();
		// dont do it for players, custom mobs, or for entities spawned with commands
		if (entity instanceof Player || event.getSpawnReason() == SpawnReason.DEFAULT || event.getSpawnReason() == SpawnReason.COMMAND) return;
		ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
		if (entity.getEquipment() != null && entity instanceof InventoryHolder) {
			ItemSlot[] slots = esEntity.getEquippedAndType();
			if (slots.length != 0) for(int i = 0; i < slots.length; i++) {
				ItemSlot slot = slots[i];
				if (slot == null) continue;
				ItemStack item = slot.getItem();
				// try to prevent causing issues with raids
				if (item != null && item.getType() == Material.WHITE_BANNER) {

				} else if (item != null && item.getItemMeta() != null && item.getItemMeta().hasEnchants()) slots[i] = new ItemSlot(GenerateUtils.generateMobSpawnLoot(item, entity), slot.getType());
			}

			esEntity.setEquippedAndType(slots);
		}
	}
}
