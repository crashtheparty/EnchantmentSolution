package org.ctp.enchantmentsolution.nms.anvil;

import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI.AnvilSlot;

public class AnvilClickEvent {
	private AnvilSlot slot;

	private String name;

	private InventoryData data;

	private boolean close = true;
	private boolean destroy = true;

	public AnvilClickEvent(AnvilSlot slot, String name, InventoryData data) {
		this.slot = slot;
		this.name = name;
		this.data = data;
	}

	public AnvilSlot getSlot() {
		return slot;
	}

	public InventoryData getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public boolean getWillClose() {
		return close;
	}

	public void setWillClose(boolean close) {
		this.close = close;
	}

	public boolean getWillDestroy() {
		return destroy;
	}

	public void setWillDestroy(boolean destroy) {
		this.destroy = destroy;
	}
}