package org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.shared.ItemObject;
import org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.util.JsonBuilder;

/**
 * Fires whenever the player eats food, drinks milk or a potion/water bottle.
 */

public class ConsumeItemTrigger extends Trigger {
	private @Nullable ItemObject item = null;
	
	public ConsumeItemTrigger() {
		super(Type.CONSUME_ITEM);
	}
	
	
	
	/**
	 * @return information about the item before it was consumed or null, if none was specified
	 */
	public @Nullable ItemObject getItem() {
		return item;
	}
	
	/**
	 * @param item information about the item before it was consumed or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public ConsumeItemTrigger setItem(@Nullable ItemObject item) {
		this.item = item;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return item == null ? null : new JsonBuilder()
				.add("item", item)
				.build();
	}
}
