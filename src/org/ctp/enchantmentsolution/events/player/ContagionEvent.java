package org.ctp.enchantmentsolution.events.player;

import java.util.Collection;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class ContagionEvent extends ESPlayerEvent {

	private final ItemStack item;
	private CustomEnchantment curse;
	private int level;
	private Collection<Sound> sounds;
	private float volume, pitch;

	public ContagionEvent(Player who, ItemStack item, CustomEnchantment curse, int level, Collection<Sound> sounds) {
		this(who, item, curse, level, sounds, 1, 1);
	}

	public ContagionEvent(Player who, ItemStack item, CustomEnchantment curse, int level, Collection<Sound> sounds,
	float volume, float pitch) {
		super(who, new EnchantmentLevel(CERegister.CURSE_OF_CONTAGION, 1));
		this.item = item;
		setCurse(curse);
		setLevel(level);
		this.sounds = sounds;
		setVolume(volume);
		setPitch(pitch);
	}

	public ItemStack getItem() {
		return item;
	}

	public CustomEnchantment getCurse() {
		return curse;
	}

	public void setCurse(CustomEnchantment curse) {
		if(curse.isCurse()) this.curse = curse;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Collection<Sound> getSounds() {
		return sounds;
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
