package org.ctp.enchantmentsolution.listeners.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class MobSpawning implements Listener{

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent event) {
		if(!Enchantments.getMobLoot()) return;
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if(entity.getEquipment() != null) {
				ItemStack[] armor = entity.getEquipment().getArmorContents();
				ItemStack holding = entity.getEquipment().getItemInMainHand();
								
				for(int i = 1; i < armor.length; i++) {
					if(armor[i] != null) {
						if(armor[i].getItemMeta() != null) {
							if(armor[i].getItemMeta().hasEnchants()) {
								armor[i] = ItemUtils.addNMSEnchantment(armor[i]);
							}
						}
					}
				}
				
				if(holding != null) {
					if(holding.getItemMeta() != null) {
						if(holding.getItemMeta().hasEnchants()) {
							holding = ItemUtils.addNMSEnchantment(holding);
						}
					}
				}
				entity.getEquipment().setArmorContents(armor);
				entity.getEquipment().setItemInMainHand(holding);
			}
		}
	}
}
