package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;

public class LagPlayerEvent extends ModifyPlayerActionEvent {

	private Location location;
	private ParticleEffect[] effects;
	private Sound sound;
	private float volume, pitch;

	public LagPlayerEvent(Player player, Location location, ParticleEffect[] effects) {
		this(player, location, effects, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
	}

	public LagPlayerEvent(Player player, Location location, ParticleEffect[] effects, Sound sound) {
		this(player, location, effects, sound, 1, 1);
	}

	public LagPlayerEvent(Player player, Location location, ParticleEffect[] effects, Sound sound, float volume,
	float pitch) {
		super(player, new EnchantmentLevel(CERegister.CURSE_OF_LAG, 1));
		setLocation(location);
		this.effects = effects;
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

	public ParticleEffect[] getEffects() {
		return effects;
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
