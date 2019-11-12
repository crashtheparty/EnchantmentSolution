package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ItemSlotType;

public final class ItemEquipEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	private boolean cancel = false;
	private final HandMethod equipType;
	private ItemStack oldItem, newItem;
	private ItemSlotType slot;

	/**
	 * Constructor for the ItemEquipEvent.
	 *
	 * @param player
	 *            The player who put on / removed the armor.
	 * @param oldItem
	 *            The ItemStack of the item removed.
	 * @param newItem
	 *            The ItemStack of the item added.
	 */
	public ItemEquipEvent(final Player player, final HandMethod equipType, final ItemSlotType slot,
	final ItemStack oldItem, final ItemStack newItem) {
		super(player);
		this.equipType = equipType;
		this.slot = slot;
		this.newItem = newItem;
		this.oldItem = oldItem;
	}

	public final void setCancelled(final boolean cancel) {
		this.cancel = cancel;
	}

	public final boolean isCancelled() {
		return cancel;
	}

	public final ItemStack getOldItem() {
		return oldItem;
	}

	public final void setOldItem(final ItemStack oldArmorPiece) {
		oldItem = oldArmorPiece;
	}

	public final ItemStack getNewItem() {
		return newItem;
	}

	public final void setNewItem(final ItemStack newArmorPiece) {
		newItem = newArmorPiece;
	}

	public HandMethod getMethod() {
		return equipType;
	}

	public ItemSlotType getSlot() {
		return slot;
	}

	public void setSlot(ItemSlotType slot) {
		this.slot = slot;
	}

	public enum HandMethod {
		HELD_SWITCH, CRAFTED, PICK_UP, DROP, HOT_BAR, HELD_SWAP,
		/**
		 * When in range of a dispenser that shoots an armor piece to equip.
		 */
		DISPENSER,
		/**
		 * When an armor piece breaks to unequip
		 */
		BROKE,
		/**
		 * When you die causing all armor to unequip
		 */
		DEATH,;
	}
}