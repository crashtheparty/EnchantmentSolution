package org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.shared.EntityObject;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.util.JsonBuilder;

/**
 * Fires whenever the player tames an animal.
 */

public class TameAnimalTrigger extends Trigger {
	@Nullable EntityObject entity = null;
	
	public TameAnimalTrigger() {
		super(Type.TAME_ANIMAL);
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
	public TameAnimalTrigger setEntity(@Nullable EntityObject entity) {
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
