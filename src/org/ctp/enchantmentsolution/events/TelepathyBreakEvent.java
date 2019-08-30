package org.ctp.enchantmentsolution.events;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class TelepathyBreakEvent extends PlayerEvent implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    private boolean applyFortune, applySmeltery, applySilkTouch, cancelled;
    private ItemStack tool;
    private Block block;
    
	public TelepathyBreakEvent(Player who, ItemStack tool, Block block) {
		super(who);
		setTool(tool);
		setBlock(block);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean applyFortune() {
		return applyFortune;
	}

	public void setApplyFortune(boolean applyFortune) {
		this.applyFortune = applyFortune;
	}

	public boolean applySmeltery() {
		return applySmeltery;
	}

	public void setApplySmeltery(boolean applySmeltery) {
		this.applySmeltery = applySmeltery;
	}

	public boolean applySilkTouch() {
		return applySilkTouch;
	}

	public void setApplySilkTouch(boolean applySilkTouch) {
		this.applySilkTouch = applySilkTouch;
	}

	public ItemStack getTool() {
		return tool;
	}

	public void setTool(ItemStack tool) {
		this.tool = tool;
		applyFortune = Enchantments.hasEnchantment(tool, Enchantment.LOOT_BONUS_BLOCKS);
		applySmeltery = Enchantments.hasEnchantment(tool, DefaultEnchantments.SMELTERY);
		applySilkTouch = Enchantments.hasEnchantment(tool, Enchantment.SILK_TOUCH);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
