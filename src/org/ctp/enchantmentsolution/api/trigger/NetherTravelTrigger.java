package org.ctp.enchantmentsolution.api.trigger;

import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.ctp.enchantmentsolution.api.shared.DistanceObject;
import org.ctp.enchantmentsolution.api.shared.LocationObject;
import org.ctp.enchantmentsolution.api.util.JsonBuilder;

/**
 * Fires whenever the player enters the nether and then returns to the overworld.
 */

public class NetherTravelTrigger extends Trigger {
	private @Nullable LocationObject entered = null;
	private @Nullable LocationObject exited = null;
	private @Nullable DistanceObject distance = null;
	
	public NetherTravelTrigger() {
		super(Type.NETHER_TRAVEL);
	}
	
	
	
	/**
	 * @return information about the location of the nether portal the player entered through or null, if none was specified
	 */
	public @Nullable LocationObject getEntered() {
		return entered;
	}
	
	/**
	 * @return information about the location of the nether portal the player arrived to in the overworld or null, if none was specified
	 */
	public @Nullable LocationObject getExited() {
		return exited;
	}
	
	/**
	 * @return information about the distance of the two nether portals in the overworld or null, if none was specified
	 */
	public @Nullable DistanceObject getDistance() {
		return distance;
	}
	
	
	
	/**
	 * @param entered information about the location of the nether portal the player entered through or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public NetherTravelTrigger setEntered(@Nullable LocationObject entered) {
		this.entered = entered;
		return this;
	}
	
	/**
	 * @param exited information about the location of the nether portal the player arrived to in the overworld or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public NetherTravelTrigger setExited(@Nullable LocationObject exited) {
		this.exited = exited;
		return this;
	}
	
	/**
	 * @param distance information about the distance of the two nether portals in the overworld or null, if it should be cleared
	 * @return the current trigger for chaining
	 */
	public NetherTravelTrigger setDistance(@Nullable DistanceObject distance) {
		this.distance = distance;
		return this;
	}
	
	
	
	@Override
	protected JsonObject getConditions() {
		return entered == null && exited == null && distance == null ? null : new JsonBuilder()
				.add("entered", entered)
				.add("exited", exited)
				.add("distance", distance)
				.build();
	}
}
