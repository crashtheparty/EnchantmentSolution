package org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.shared.Dimension;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.util.JsonBuilder;

/**
 * Fires whenever the player switches to another dimension.
 */

public class ChangedDimensionTrigger extends Trigger {
	@Nullable Dimension to = null;
	@Nullable Dimension from = null;
	
	public ChangedDimensionTrigger() {
		super(Type.CHANGED_DIMENSION);
	}
	
	
	
	/**
	 * @return the dimension the player has just entered or null, if none was specified
	 */
	public @Nullable Dimension getTo() {
		return to;
	}
	
	/**
	 * @return the dimension the player has just left or null, if none was specified
	 */
	public @Nullable Dimension getFrom() {
		return from;
	}
	
	
	
	/**
	 * @param to the dimension the player has just entered or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public ChangedDimensionTrigger setTo(@Nullable Dimension to) {
		this.to = to;
		return this;
	}
	
	/**
	 * @param from the dimension the player has just left or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public ChangedDimensionTrigger setFrom(@Nullable Dimension from) {
		this.from = from;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return to == null && from == null ? null : new JsonBuilder()
				.add("to", to)
				.add("from", from)
				.build();
	}
}
