package org.ctp.enchantmentsolution.api.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.api.shared.ItemObject;
import org.ctp.enchantmentsolution.api.shared.RangeObject;
import org.ctp.enchantmentsolution.api.util.JsonBuilder;

/**
 * Fires whenever the player enchants an item. The item doesn't have to be taken out of the enchanting table.
 */

public class EnchantedItemTrigger extends Trigger {
	private @Nullable ItemObject item = null;
	private @Nullable RangeObject levels = null;
	
	public EnchantedItemTrigger() {
		super(Type.ENCHANTED_ITEM);
	}
	
	
	
	/**
	 * @return the item after it has been enchanted or null, if none was specified
	 */
	public @Nullable ItemObject getItem() {
		return item;
	}
	
	/**
	 * @return the number of levels the player used up to enchant the item or null, if none was specified
	 */
	public @Nullable RangeObject getLevels() {
		return levels;
	}
	
	
	
	/**
	 * @param item the item after it has been enchanted or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public EnchantedItemTrigger setItem(@Nullable ItemObject item) {
		this.item = item;
		return this;
	}
	
	/**
	 * @param levels the number of levels the player used up to enchant the item or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public EnchantedItemTrigger setLevels(@Nullable RangeObject levels) {
		this.levels = levels;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return item == null && levels == null ? null : new JsonBuilder()
				.add("item", item)
				.add("levels", levels)
				.build();
	}
}
