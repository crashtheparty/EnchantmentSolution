package org.ctp.enchantmentsolution.listeners.hard;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.ctp.crashapi.nms.WorldNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class HardModeListener implements Listener {

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if (!ConfigString.GAMETYPES.getStringList().contains("HARD")) return;
		LivingEntity entity = event.getEntity();
		// dont do it for players or for entities spawned with commands
		if (entity instanceof Player || event.getSpawnReason() == SpawnReason.DEFAULT) return;
		if (ConfigString.HARD_HEALTH_INCREASE.getBoolean()) {
			double rand = Math.random();
			double chance = WorldNMS.getRegionalDifficulty(event.getEntity().getLocation().getBlock())[0] / 25.0D;
			if (chance > rand) {
				AttributeInstance a = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				double healthIncrease = a.getBaseValue() * (Math.random() / 2 + 0.1);
				AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.maxHealth", healthIncrease, Operation.ADD_NUMBER);
				a.addModifier(modifier);
				entity.setHealth(a.getValue());
			}
		}
	}
}
