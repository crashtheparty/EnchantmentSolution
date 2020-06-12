package org.ctp.enchantmentsolution.enums.vanilla;

import org.bukkit.entity.EntityType;

public class MobData {
	private EntityType entity;
	private String entityName;

	public MobData(String name) {
		entityName = name.toUpperCase();
		try {
			entity = EntityType.valueOf(entityName);
		} catch (Exception ex) {}
	}

	public EntityType getEntity() {
		return entity;
	}

	public String getEntityName() {
		return entityName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MobData) {
			MobData data = (MobData) obj;
			return data.entity != null && data.entity == entity;
		}
		return false;
	}

	public boolean hasEntity() {
		return entity != null;
	}
}
