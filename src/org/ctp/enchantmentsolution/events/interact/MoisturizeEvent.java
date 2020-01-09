package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;

public class MoisturizeEvent extends InteractEvent {

	private final ItemMoisturizeType type;
	private Sound sound;

	public MoisturizeEvent(Player who, ItemStack item, Block block, ItemMoisturizeType type, Sound sound) {
		super(who, new EnchantmentLevel(CERegister.MOISTURIZE, 1), item, block);
		this.type = type;
		setSound(sound);
	}

	public ItemMoisturizeType getMoisturizeType() {
		return type;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

}
