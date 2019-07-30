package org.ctp.enchantmentsolution.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityEquipEvent extends PlayerEvent implements Cancellable{
	
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
    private ItemStack[] items;
    private Enchantment enchantment;
    private SlotType type;

	public AbilityEquipEvent(Player who, ItemStack item, Enchantment enchantment, SlotType type) {
		super(who);
		setItem(item);
		this.enchantment = enchantment;
		this.type = type;
	}

	public AbilityEquipEvent(Player who, ItemStack[] items, Enchantment enchantment) {
		super(who);
		setItems(items);
		this.enchantment = enchantment;
		this.type = SlotType.FULL_ARMOR;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public void setItems(ItemStack[] items) {
		this.items = items;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public SlotType getType() {
		return type;
	}
}
