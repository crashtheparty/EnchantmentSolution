package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class MoisturizeEvent extends ESPlayerEvent {

	private final Block block;
	private final ItemMoisturizeType type;
	private Sound sound;

	public MoisturizeEvent(Player who, Block block, ItemMoisturizeType type, Sound sound) {
		super(who, new EnchantmentLevel(CERegister.MOISTURIZE, 1));
		this.block = block;
		this.type = type;
		setSound(sound);
	}

	public Block getBlock() {
		return block;
	}

	public ItemMoisturizeType getType() {
		return type;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

}
