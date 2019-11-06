package org.ctp.enchantmentsolution.nms.anvil;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.nms.anvil.AnvilGUI.AnvilClickEvent;

public interface AnvilClickEventHandler {
	void onAnvilClick(AnvilClickEvent event);

	public static AnvilClickEventHandler getHandler(Player player, InventoryData data) {
		return new AnvilClickEventHandler() {
			public void onAnvilClick(AnvilClickEvent event) {
				if (event.getSlot() == null || event.getSlot().getSlot() != 2) {
					event.setWillClose(false);
					event.setWillDestroy(false);
					return;
				}
				if (player.getLevel() < 1 && player.getGameMode() != GameMode.CREATIVE && data instanceof Anvil) {
					event.setWillClose(true);
					event.setWillDestroy(true);
					return;
				}
				if (event.getName().equals("")) {
					ItemStack item = null;
					if (data instanceof Anvil) {
						Anvil anvil = (Anvil) data;
						item = anvil.getItems().get(0);
						if (item != null) {
							if (!item.getItemMeta().hasDisplayName()) {
								item = null;
							}
						}
					}
					if (item == null) {
						event.setWillClose(false);
						event.setWillDestroy(false);
						return;
					}
				}

				event.getData().setItemName(event.getName());
				if (player.getGameMode() != GameMode.CREATIVE && data instanceof Anvil) {
					player.setLevel(player.getLevel() - 1);
				}
			}
		};
	}
}