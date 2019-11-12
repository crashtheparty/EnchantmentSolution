package org.ctp.enchantmentsolution.advancements.shared;

import com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.advancements.util.JsonBuilder;

/**
 * Specifies information about an enchantment applied to an item.
 * 
 * @see ItemObject
 */

class EnchantObject extends SharedObject {
	private @Nullable Enchant enchant = null;
	private @Nullable RangeObject levels = null;

	/**
	 * @return the enchant or null, if none was specified
	 */
	public @Nullable Enchant getEnchant() {
		return enchant;
	}

	/**
	 * @return the level of the enchantment or null, if none was specified
	 */
	public @Nullable RangeObject getLevels() {
		return levels;
	}

	/**
	 * @param enchant
	 *            the enchant or null, if it should be cleared
	 * @return the current block object for chaining
	 */
	public EnchantObject setEnchant(@Nullable Enchant enchant) {
		this.enchant = enchant;
		return this;
	}

	/**
	 * @param levels
	 *            the level of the enchantment or null, if it should be cleared
	 * @return the current block object for chaining
	 */
	public EnchantObject setLevels(@Nullable RangeObject levels) {
		this.levels = levels;
		return this;
	}

	/**
	 * @return the JSON representation of the enchant object
	 */
	@Override
	public JsonElement toJson() {
		return new JsonBuilder() // Empty JsonObject -> any enchantment
		.add("enchantment", enchant).add("levels", levels).build();
	}
}
