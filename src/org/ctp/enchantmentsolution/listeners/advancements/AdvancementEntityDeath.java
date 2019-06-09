package org.ctp.enchantmentsolution.listeners.advancements;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class AdvancementEntityDeath implements Listener{
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof LivingEntity) {
			LivingEntity killed = (LivingEntity) entity;
			Player killer = killed.getKiller();
			if(killer != null) {
				// doesn't work
				ChatUtils.sendInfo(killed.getLastDamageCause().getCause().name());
			}
		}
	}

}
