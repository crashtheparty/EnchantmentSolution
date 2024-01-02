package org.ctp.enchantmentsolution.interfaces.conditions.interact;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;

public class HandPacketCondition implements InteractCondition {

	private final boolean opposite;
	private final EquipmentSlot[] slots;

	public HandPacketCondition(boolean opposite, EquipmentSlot... slots) {
		this.opposite = opposite;
		this.slots = slots;
	}

	@Override
	public boolean metCondition(Player player, PlayerInteractEvent event) {
		for(EquipmentSlot s: slots)
			if (event.getHand() == s) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public EquipmentSlot[] getSlots() {
		return slots;
	}

}
