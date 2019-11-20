package org.ctp.enchantmentsolution.events.modify;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ModifyActionEvent;
import org.ctp.enchantmentsolution.utils.abillityhelpers.ParticleEffect;

public class LagEvent extends ModifyActionEvent {

	private Location location;
	private List<ParticleEffect> effects;
	private Sound sound;
	private float volume, pitch;

	public LagEvent(Player player, Location location, List<ParticleEffect> effects) {
		this(player, location, effects, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
	}

	public LagEvent(Player player, Location location, List<ParticleEffect> effects, Sound sound) {
		this(player, location, effects, sound, 1, 1);
	}

	public LagEvent(Player player, Location location, List<ParticleEffect> effects, Sound sound, float volume,
	float pitch) {
		super(player);
		setLocation(location);
		setEffects(effects);
		setSound(sound);
		setVolume(volume);
		setPitch(pitch);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<ParticleEffect> getEffects() {
		return effects;
	}

	public void setEffects(List<ParticleEffect> effects) {
		this.effects = effects;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}
