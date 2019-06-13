package org.ctp.enchantmentsolution.api.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.api.shared.EntityObject;
import org.ctp.enchantmentsolution.api.util.JsonBuilder;

/**
 * Fires whenever the player creates an entity. In the case of the ender dragon, all players able to view the boss bar fulfill the trigger.
 */

public class SummonedEntityTrigger extends Trigger {
	private @Nullable EntityObject entity = null;
	
	public SummonedEntityTrigger() {
		super(Type.SUMMONED_ENTITY);
	}
	
	
	
	/**
	 * @return information about the tamed entity or null, if none was specified
	 */
	public @Nullable EntityObject getEntity() {
		return entity;
	}
	
	/**
	 * @param entity information about the tamed entity or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public SummonedEntityTrigger setEntity(@Nullable EntityObject entity) {
		this.entity = entity;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return entity == null ? null : new JsonBuilder()
				.add("entity", entity)
				.build();
	}
}
