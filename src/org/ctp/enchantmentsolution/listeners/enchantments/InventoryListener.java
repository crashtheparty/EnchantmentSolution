package org.ctp.enchantmentsolution.listeners.enchantments;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.threads.MiscRunnable;

@SuppressWarnings("unused")
public class InventoryListener extends Enchantmentable {

	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent event) {
		runMethod(this, "contagionCurse", event, InventoryInteractEvent.class);
	}

	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		runMethod(this, "contagionCurse", event, EntityPickupItemEvent.class);
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		runMethod(this, "contagionCurse", event, InventoryOpenEvent.class);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		runMethod(this, "contagionCurse", event, InventoryCloseEvent.class);
	}

	private void contagionCurse(InventoryInteractEvent event) {
		checkContagion(event.getWhoClicked());
	}

	private void contagionCurse(EntityPickupItemEvent event) {
		checkContagion(event.getEntity());
	}

	private void contagionCurse(InventoryOpenEvent event) {
		checkContagion(event.getPlayer());
	}

	private void contagionCurse(InventoryCloseEvent event) {
		checkContagion(event.getPlayer());
	}

	private void checkContagion(Entity entity) {
		if (entity instanceof Player) MiscRunnable.addContagion((Player) entity);
	}
}
