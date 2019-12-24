package org.ctp.enchantmentsolution.advancements.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.advancements.shared.ItemObject;
import org.ctp.enchantmentsolution.advancements.shared.RangeObject;
import org.ctp.enchantmentsolution.advancements.util.JsonBuilder;

public class EnchantedItemTrigger extends Trigger {
	private @Nullable ItemObject item = null;
	private @Nullable RangeObject levels = null;

	public EnchantedItemTrigger() {
		super(Type.ENCHANTED_ITEM);
	}

	public @Nullable ItemObject getItem() {
		return item;
	}

	public @Nullable RangeObject getLevels() {
		return levels;
	}

	public EnchantedItemTrigger setItem(@Nullable ItemObject item) {
		this.item = item;
		return this;
	}

	public EnchantedItemTrigger setLevels(@Nullable RangeObject levels) {
		this.levels = levels;
		return this;
	}

	@Override
	protected JsonObject getConditions() {
		return item == null && levels == null ? null
		: new JsonBuilder().add("item", item).add("levels", levels).build();
	}
}
