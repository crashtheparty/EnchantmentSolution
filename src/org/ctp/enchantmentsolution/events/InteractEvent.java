package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class InteractEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

    private boolean cancelled = false;
	private ItemStack item;
	private Block block;
	private Entity entity;
	private Type type;
	
	public InteractEvent(Player who, ItemStack item) {
		super(who);
		this.setItem(item);
		this.setType(Type.AIR);
	}
	
	public InteractEvent(Player who, ItemStack item, Block block) {
		super(who);
		this.setItem(item);
		this.setBlock(block);
		this.setType(Type.BLOCK);
	}
	
	public InteractEvent(Player who, ItemStack item, Entity entity) {
		super(who);
		this.setItem(item);
		this.setEntity(entity);
		this.setType(Type.ENTITY);
	}
	
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
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

	public enum Type{
		AIR(), BLOCK(), ENTITY();
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
