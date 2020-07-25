package org.ctp.enchantmentsolution.crashapi.resources.shared;

import org.bukkit.Material;

/**
 * Specifies a potion's effect.
 */
@SuppressWarnings("unused")
enum Potion implements SharedEnum {
	/**
	 * Also known as the uncraftable potion. It is not actually empty: the texture
	 * does not equal {@link MaterialId#GLASS_BOTTLE}'s
	 */
	EMPTY, WATER, MUNDANE, THICK, AWKWARD, NIGHT_VISION, LONG_NIGHT_VISION, INVISIBILITY, LONG_INVISIBILITY, LEAPING,
	STRONG_LEAPING, LONG_LEAPING, FIRE_RESISTANCE, LONG_FIRE_RESISTANCE, SWIFTNESS, STRONG_SWIFTNESS, SLOWNESS,
	LONG_SLOWNESS, LONG_SWIFTNESS, WATER_BREATHING, LONG_WATER_BREATHING, HEALING, STRONG_HEALING, HARMING,
	STRONG_HARMING, POISON, STRONG_POISON, LONG_POISON, REGENERATION, STRONG_REGENERATION, LONG_REGENERATION, STRENGTH,
	STRONG_STRENGTH, LONG_STRENGTH, WEAKNESS, LONG_WEAKNESS, LUCK;

	@Override
	public String getValue() {
		return "minecraft:" + name().toLowerCase();
	}

	/**
	 * The type of the potion item.
	 */
	public enum Type {
		/**
		 * Consumable/drinkable.
		 */
		NORMAL(Material.POTION), SPLASH(Material.SPLASH_POTION), LINGERING(Material.LINGERING_POTION);

		private final Material item;

		Type(Material item) {
			this.item = item;
		}

		/**
		 * @return the item type associated with this potion type
		 */
		public Material getItem() {
			return item;
		}
	}
}
