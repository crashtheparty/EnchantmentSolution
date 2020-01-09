package org.ctp.enchantmentsolution.advancements;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.ctp.enchantmentsolution.advancements.util.JsonBuilder;
import org.ctp.enchantmentsolution.advancements.util.Validator;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Specifies rewards which are given when the advancement is completed.
 */
public class Rewards {
	private static final Type NAMESPACED_KEY_SET_TYPE = new TypeToken<Set<NamespacedKey>>() {}.getType();
	private @Nullable Set<NamespacedKey> recipes = null;
	private @Nullable Set<NamespacedKey> loots = null;
	private int experience = 0;
	private @Nullable NamespacedKey function = null;

	/**
	 * @return the id of the recipes which will unlock upon completion of the
	 *         advancement
	 */
	public Set<NamespacedKey> getRecipes() {
		return recipes == null ? Collections.emptySet() : Collections.unmodifiableSet(recipes);
	}

	/**
	 * @return the id of the loot tables from all of which items will be given upon
	 *         completion of the advancement
	 */
	public Set<NamespacedKey> getLoots() {
		return loots == null ? Collections.emptySet() : Collections.unmodifiableSet(loots);
	}

	/**
	 * @return the amount of experience which will be given upon completion of the
	 *         advancement
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @return the id of the function which will run upon completion of the
	 *         advancement
	 */
	public @Nullable NamespacedKey getFunction() {
		return function;
	}

	/**
	 * @param experience
	 *            the amount of experience which should be given upon completion of
	 *            the advancement
	 * @return the current rewards object for chaining
	 */
	public Rewards setExperience(int experience) {
		Validator.zeroToDisable(experience);
		this.experience = experience;
		return this;
	}

	/**
	 * @return the JSON representation of the reward object
	 */
	JsonObject toJson() {
		return new JsonBuilder().add("recipes", recipes, NAMESPACED_KEY_SET_TYPE).add("loot", loots, NAMESPACED_KEY_SET_TYPE).addPositive("experience", experience).add("function", function).build();
	}

	/**
	 * @return the hash code of this rewards object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(recipes, loots, experience, function);
	}

	/**
	 * @param object
	 *            the reference object with which to compare
	 * @return whether this object has the same content as the passed parameter
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Rewards)) return false;

		Rewards rew = (Rewards) object;
		return Objects.equals(rew.recipes, recipes) && Objects.equals(rew.loots, loots) && Objects.equals(rew.experience, experience) && Objects.equals(rew.function, function);
	}
}
