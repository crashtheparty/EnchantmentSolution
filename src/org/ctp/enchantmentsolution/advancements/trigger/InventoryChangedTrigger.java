package org.ctp.enchantmentsolution.advancements.trigger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.advancements.shared.ItemObject;
import org.ctp.enchantmentsolution.advancements.shared.RangeObject;
import org.ctp.enchantmentsolution.advancements.util.JsonBuilder;
import org.apache.commons.lang.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InventoryChangedTrigger extends Trigger {
	private @Nullable RangeObject occupied = null;
	private @Nullable RangeObject full = null;
	private @Nullable RangeObject empty = null;
	private @Nullable Set<ItemObject> items = null;

	public InventoryChangedTrigger() {
		super(Type.INVENTORY_CHANGED);
	}

	public @Nullable RangeObject getOccupied() {
		return occupied;
	}

	public @Nullable RangeObject getFull() {
		return full;
	}

	public @Nullable RangeObject getEmpty() {
		return empty;
	}

	public Set<ItemObject> getItems() {
		return items == null ? Collections.emptySet() : Collections.unmodifiableSet(items);
	}

	public InventoryChangedTrigger setOccupied(@Nullable RangeObject occupied) {
		this.occupied = occupied;
		return this;
	}

	public InventoryChangedTrigger setFull(@Nullable RangeObject full) {
		this.full = full;
		return this;
	}

	public InventoryChangedTrigger setEmpty(@Nullable RangeObject empty) {
		this.empty = empty;
		return this;
	}

	public InventoryChangedTrigger addItem(ItemObject item) {
		Validate.notNull(item);
		if (items == null) items = new HashSet<>();
		items.add(item);
		return this;
	}

	public InventoryChangedTrigger removeItem(ItemObject item) {
		Validate.notNull(item);
		if (items != null) items.remove(item);
		return this;
	}

	@Override
	protected JsonObject getConditions() {
		if (occupied == null && full == null && empty == null && (items == null || items.isEmpty())) return null;
		JsonObject json = new JsonObject();
		if (occupied != null || full != null || empty != null) json.add("slots", new JsonBuilder().add("occupied", occupied).add("full", full).add("empty", empty).build());
		if (items != null && !items.isEmpty()) {
			JsonArray items = new JsonArray();
			for(ItemObject item: this.items)
				items.add(item.toJson());
			json.add("items", items);
		}
		return json;
	}
}
