package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.ServerNMS;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.Cooldownable;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class FlowerGiftEvent extends InteractEvent implements Cooldownable {

	private ItemStack flower;
	private Location dropLocation;
	private boolean cooldown;
	private int cooldownTicks;

	public FlowerGiftEvent(Player who, ItemStack item, Block block, ItemStack flower, Location dropLocation) {
		super(who, new EnchantmentLevel(CERegister.FLOWER_GIFT, 1), item, block);
		setFlower(flower);
		setDropLocation(dropLocation);
		cooldown = true;
		cooldownTicks = 20;
	}

	public ItemStack getFlower() {
		return flower;
	}

	public void setFlower(ItemStack flower) {
		this.flower = flower;
	}

	public Location getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(Location dropLocation) {
		this.dropLocation = dropLocation;
	}

	@Override
	public boolean useCooldown() {
		return cooldown;
	}

	@Override
	public void setCooldown(boolean cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getCooldownTicks() {
		return cooldownTicks;
	}

	@Override
	public void setCooldownTicks(int cooldownTicks) {
		this.cooldownTicks = cooldownTicks;
	}

	public boolean overCooldown() {
		ESPlayer player = EnchantmentSolution.getESPlayer(getPlayer());
		long cooldown = player.getCooldown(getEnchantment().getEnchant().getRelativeEnchantment());
		Chatable.get().sendInfo("Cooldown: " + cooldown + " Cooldown Ticks: " + getCooldownTicks() + " ServerNMS Tick: " + ServerNMS.getCurrentTick() + " Has Overrun: " + !ServerNMS.hasOverrun(cooldown));
		Chatable.get().sendInfo("Over Cooldown: " + !(cooldown + getCooldownTicks() > ServerNMS.getCurrentTick() && !ServerNMS.hasOverrun(cooldown)));
		return !this.cooldown || !(cooldown + getCooldownTicks() > ServerNMS.getCurrentTick() && !ServerNMS.hasOverrun(cooldown));
	}

}
