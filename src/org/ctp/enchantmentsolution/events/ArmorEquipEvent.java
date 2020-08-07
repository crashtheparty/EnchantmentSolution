package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enums.ItemSlotType;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public final class ArmorEquipEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	private boolean cancel = false;
	private final EquipMethod equipType;
	private final ItemSlotType type;
	private final ItemStack oldArmorPiece, newArmorPiece;

	public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ItemSlotType type,
	final ItemStack oldArmorPiece, final ItemStack newArmorPiece) {
		super(player);
		this.equipType = equipType;
		this.type = type;
		this.oldArmorPiece = oldArmorPiece;
		this.newArmorPiece = newArmorPiece;
	}

	@Override
	public final void setCancelled(final boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public final boolean isCancelled() {
		return cancel;
	}

	public final ItemSlotType getType() {
		return type;
	}

	public final ItemStack getOldArmorPiece() {
		return oldArmorPiece;
	}

	public final ItemStack getNewArmorPiece() {
		return newArmorPiece;
	}

	/**
	 * Gets the method used to either equip or unequip an armor piece.
	 */
	public EquipMethod getMethod() {
		return equipType;
	}

	public enum EquipMethod {// These have got to be the worst documentations ever.
		/**
		 * When you shift click an armor piece to equip or unequip
		 */
		SHIFT_CLICK,
		/**
		 * When you drag and drop the item to equip or unequip
		 */
		DRAG,
		/**
		 * When you manually equip or unequip the item. Use to be DRAG
		 */
		PICK_DROP,
		/**
		 * When you right click an armor piece in the hotbar without the inventory open
		 * to equip.
		 */
		HOTBAR,
		/**
		 * When you press the hotbar slot number while hovering over the armor slot to
		 * equip or unequip
		 */
		HOTBAR_SWAP,
		/**
		 * When in range of a dispenser that shoots an armor piece to equip.<br>
		 * Requires the spigot version to have
		 * {@link org.bukkit.event.block.BlockDispenseArmorEvent} implemented.
		 */
		DISPENSER,
		/**
		 * When an armor piece is removed due to it losing all durability.
		 */
		BROKE,
		/**
		 * When you die causing all armor to unequip
		 */
		DEATH,
		/**
		 * When you join the server.
		 */
		JOIN,
		/**
		 * When equipping an item from commands/other
		 */
		COMMAND;
	}
}