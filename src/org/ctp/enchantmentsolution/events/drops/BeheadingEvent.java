package org.ctp.enchantmentsolution.events.drops;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class BeheadingEvent extends OverrideDropsEvent {

	private boolean keepInventoryOverride;
	private final LivingEntity skullOwner;

	public BeheadingEvent(LivingEntity who, LivingEntity skullOwner, int level, Collection<ItemStack> collection, Collection<ItemStack> drops,
	boolean keepInventoryOverride) {
		super(who, new EnchantmentLevel(CERegister.BEHEADING, level), collection, drops);
		setKeepInventoryOverride(keepInventoryOverride);
		this.skullOwner = skullOwner;
	}

	public boolean isKeepInventoryOverride() {
		return keepInventoryOverride;
	}

	public void setKeepInventoryOverride(boolean keepInventoryOverride) {
		this.keepInventoryOverride = keepInventoryOverride;
	}

	public LivingEntity getSkullOwner() {
		return skullOwner;
	}

}
