package org.ctp.enchantmentsolution.events.player;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class InteractEvent extends ESPlayerEvent {

	private final ItemStack item;
	private Block block;
	private Entity entity;
	private Type type;

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item) {
		super(who, enchantment);
		this.item = item;
		setType(Type.AIR);
	}

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item, Block block) {
		super(who, enchantment);
		this.item = item;
		setBlock(block);
		setType(Type.BLOCK);
	}

	public InteractEvent(Player who, EnchantmentLevel enchantment, ItemStack item, Entity entity) {
		super(who, enchantment);
		this.item = item;
		setEntity(entity);
		setType(Type.ENTITY);
	}

	public ItemStack getItem() {
		return item;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		AIR(), BLOCK(), ENTITY();
	}

}
