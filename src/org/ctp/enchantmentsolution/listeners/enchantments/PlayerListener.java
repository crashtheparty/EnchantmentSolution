package org.ctp.enchantmentsolution.listeners.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.WalkerInterface;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;

@SuppressWarnings("unused")
public class PlayerListener extends Enchantmentable {

	@EventHandler
	public void onPlayerChangeCoords(PlayerChangeCoordsEvent event) {
		runMethod(this, "movementListener", event, PlayerChangeCoordsEvent.class);
	}

	private void movementListener(PlayerChangeCoordsEvent event) {
		Player player = event.getPlayer();
		if (player.isFlying() || player.isGliding() || player.isInsideVehicle()) return;
		ItemStack boots = player.getInventory().getBoots();

		if (WalkerInterface.hasWalkerInterface(boots)) WalkerUtils.updateBlocks(this, event, player, boots, event.getFrom(), event.getTo());
	}
}
