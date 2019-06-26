package org.ctp.enchantmentsolution.api.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.api.shared.ItemObject;
import org.ctp.enchantmentsolution.api.shared.RangeObject;
import org.ctp.enchantmentsolution.api.util.JsonBuilder;

/**
 * Fires whenever any item's durability in the player's inventory changes.
 */

public class ItemDurabilityChangedTrigger extends Trigger {
	private @Nullable ItemObject item = null;
	private @Nullable RangeObject durability = null;
	private @Nullable RangeObject delta = null;
	
	public ItemDurabilityChangedTrigger() {
		super(Type.ITEM_DURABILITY_CHANGED);
	}
	
	
	
	/**
	 * @return the item which's durability changed or null, if none was specified
	 */
	public @Nullable ItemObject getItem() {
		return item;
	}
	
	/**
	 * @return the durability or null, if none was specified
	 */
	public @Nullable RangeObject getDurability() {
		return durability;
	}
	
	/**
	 * @return the durability change or null, if none was specified. The durability change is calculated by {@code new - old}
	 */
	public @Nullable RangeObject getDelta() {
		return delta;
	}
	
	
	
	/**
	 * @param item the item which's durability changed or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public ItemDurabilityChangedTrigger setItem(@Nullable ItemObject item) {
		this.item = item;
		return this;
	}
	
	/**
	 * @param durability the new durability or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public ItemDurabilityChangedTrigger setDurability(@Nullable RangeObject durability) {
		this.durability = durability;
		return this;
	}
	
	/**
	 * @param delta the durability change or null, if it should be cleared. The durability change is calculated by {@code new - old}
	 * @return the current trigger for chaining
	 */
	public ItemDurabilityChangedTrigger setDelta(@Nullable RangeObject delta) {
		this.delta = delta;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return item == null && durability == null && delta == null ? null : new JsonBuilder()
				.add("item", item)
				.add("durability", durability)
				.add("delta", delta)
				.build();
	}
}
