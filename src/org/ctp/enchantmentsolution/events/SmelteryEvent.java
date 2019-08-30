package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class SmelteryEvent extends PlayerEvent implements Cancellable, NaturalDropable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	private boolean cancelled, fromTelepathy, dropNaturally;
	private ItemStack tool, drop;
	private Block block;

	public SmelteryEvent(Player who, ItemStack tool, Block block, ItemStack drop, boolean fromTelepathy) {
		super(who);
		setTool(tool);
		setBlock(block);
		setDrop(drop);
		this.fromTelepathy = fromTelepathy;
		this.dropNaturally = ConfigUtils.dropNaturally();
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isFromTelepathy() {
		return fromTelepathy;
	}

	public ItemStack getTool() {
		return tool;
	}

	public void setTool(ItemStack tool) {
		this.tool = tool;
	}

	public ItemStack getDrop() {
		return drop;
	}

	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public boolean willDropNaturally() {
		return dropNaturally;
	}

	public void setDropNaturally(boolean dropNaturally) {
		this.dropNaturally = dropNaturally;
	}

}
