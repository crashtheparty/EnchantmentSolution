package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.generate.MobLootEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MobSpawning implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (!ConfigString.USE_LOOT.getBoolean("mobs.use")) return;
		LivingEntity entity = event.getEntity();
		// dont do it for players or for entities spawned with commands
		if (entity instanceof Player || event.getSpawnReason() == SpawnReason.DEFAULT) return;
		if (entity.getEquipment() != null) {
			ItemStack[] armor = entity.getEquipment().getArmorContents();
			ItemStack holding = entity.getEquipment().getItemInMainHand();

			for(int i = 0; i < armor.length; i++)
				if (armor[i] != null && armor[i].getItemMeta() != null && armor[i].getItemMeta().hasEnchants()) armor[i] = MobLootEnchantments.generateMobLoot(armor[i]).getItem();

			if (holding != null && holding.getItemMeta() != null && holding.getItemMeta().hasEnchants()) holding = MobLootEnchantments.generateMobLoot(holding).getItem();
			entity.getEquipment().setArmorContents(armor);
			entity.getEquipment().setItemInMainHand(holding);
		}
	}
}
