package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class MobSpawning implements Listener{

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if(!ConfigUtils.getMobLoot()) return;
		if(event.getEntity() instanceof LivingEntity) {
			// dont do it for players or for entities spawned with commands
			if(event.getEntity() instanceof Player || event.getSpawnReason() == SpawnReason.DEFAULT) return;
			LivingEntity entity = (LivingEntity) event.getEntity();
			if(entity.getEquipment() != null) {
				ItemStack[] armor = entity.getEquipment().getArmorContents();
				ItemStack holding = entity.getEquipment().getItemInMainHand();
				
				for(int i = 0; i < armor.length; i++) {
					if(armor[i] != null) {
						if(armor[i].getItemMeta() != null) {
							if(armor[i].getItemMeta().hasEnchants()) {
								armor[i] = ItemUtils.addNMSEnchantment(armor[i], "mobs");
							}
						}
					}
				}
				
				if(holding != null) {
					if(holding.getItemMeta() != null) {
						if(holding.getItemMeta().hasEnchants()) {
							holding = ItemUtils.addNMSEnchantment(holding, "mobs");
						}
					}
				}
				entity.getEquipment().setArmorContents(armor);
				entity.getEquipment().setItemInMainHand(holding);
			}
		}
	}
}
