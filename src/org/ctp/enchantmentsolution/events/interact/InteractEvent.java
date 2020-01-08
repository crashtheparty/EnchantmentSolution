package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class InteractEvent extends ESPlayerEvent {

	private final ItemStack item;
	private final Block block;
	private final Entity entity;
	private final Type type;

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item) {
		super(who, enchantment);
		this.item = item;
		this.type = Type.AIR;
		this.block = null;
		this.entity = null;
	}

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item, Block block) {
		super(who, enchantment);
		this.item = item;
		this.block = block;
		this.entity = null;
		this.type = Type.BLOCK;
	}

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item, Entity entity) {
		super(who, enchantment);
		this.item = item;
		this.block = null;
		this.entity = entity;
		this.type = Type.ENTITY;
	}

	public ItemStack getItem() {
		return item;
	}

	public Block getBlock() {
		return block;
	}

	public Entity getEntity() {
		return entity;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		AIR(), BLOCK(), ENTITY();
	}

}
