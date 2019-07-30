package org.ctp.enchantmentsolution.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityUnequipEvent extends PlayerEvent{
	private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private ItemStack item;
    private ItemStack[] items;
    private Enchantment enchantment;
    private SlotType type;

	public AbilityUnequipEvent(Player who, ItemStack item, Enchantment enchantment, SlotType type) {
		super(who);
		this.item = item;
		this.enchantment = enchantment;
		this.type = type;
	}

	public AbilityUnequipEvent(Player who, ItemStack[] items, Enchantment enchantment) {
		super(who);
		this.items = items;
		this.enchantment = enchantment;
		this.type = SlotType.FULL_ARMOR;
	}

	public ItemStack getItem() {
		return item;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public SlotType getType() {
		return type;
	}
}
