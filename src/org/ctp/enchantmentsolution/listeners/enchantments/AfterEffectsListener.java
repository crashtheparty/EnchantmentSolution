package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Iterator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.DrownedEntity;

@SuppressWarnings("unused")
public class AfterEffectsListener extends Enchantmentable {
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		runMethod(this, "drowned", event, EntityDeathEvent.class);
	}
	
	private void drowned(EntityDeathEvent event) {
		if(!canRun(RegisterEnchantments.DROWNED, event)) return;
		Iterator<DrownedEntity> entities = EnchantmentSolution.getDrowned().iterator();
		while(entities.hasNext()) {
			DrownedEntity entity = entities.next();
			if(entity.getHurtEntity().equals(event.getEntity())) {
				entities.remove();
				break;
			}
		}
	}
}
