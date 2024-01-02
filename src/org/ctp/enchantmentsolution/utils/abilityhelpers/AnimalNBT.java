package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.ctp.crashapi.nms.MobNMS;

public class AnimalNBT {

	private EntityType type;
	private String nbtData;

	protected AnimalNBT(Entity entity) {
		this.type = entity.getType();
		nbtData = MobNMS.getNBTData(entity, true);
	}

	public Entity spawnEntity(Location loc) {
		Entity e = loc.getWorld().spawnEntity(loc, type);
		MobNMS.setNBTData(e, nbtData);

		return e;
	}

	public static Entity setHusbandry(Creature entity) {
		AnimalNBT animal = new AnimalNBT(entity);
		if (entity instanceof Ageable) {
			Entity e = animal.spawnEntity(entity.getLocation());
			((Ageable) e).setBaby();
			return e;
		}
		return null;
	}
}
