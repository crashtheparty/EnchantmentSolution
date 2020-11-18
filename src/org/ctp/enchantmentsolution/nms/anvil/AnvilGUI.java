package org.ctp.enchantmentsolution.nms.anvil;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.nms.anvil.AnvilClickEvent;
import org.ctp.crashapi.nms.anvil.AnvilClickEventHandler;
import org.ctp.crashapi.nms.anvil.AnvilSlot;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public abstract class AnvilGUI {

	private Player player;

	private ESAnvilClickEventHandler handler;

	private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

	private Inventory inv;

	private Listener listener;

	private InventoryData data;

	public AnvilGUI(Player player, final ESAnvilClickEventHandler handler, InventoryData data) {
		this.player = player;
		setHandler(handler);
		setData(data);

		listener = new Listener() {
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onInventoryClick(InventoryClickEvent event) {
				if (event.getWhoClicked() instanceof Player) if (event.getInventory().equals(inv)) {
					event.setCancelled(true);
					if (data instanceof Anvil) {
						Anvil anvil = (Anvil) data;
						ItemStack item = event.getCurrentItem();
						int slot = event.getRawSlot();
						String name = "";

						if (item != null) if (item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();

							if (meta.hasDisplayName()) name = meta.getDisplayName();
						}

						AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, anvil);

						handler.onAnvilClick(clickEvent);

						if (clickEvent.getWillClose()) {
							event.getWhoClicked().closeInventory();
							anvil.setInventory();
						}

						if (clickEvent.getWillDestroy()) {
							LocationUtils.checkAnvilBreak(player, anvil.getBlock(), anvil, ConfigString.DAMAGE_ANVIL.getBoolean());
							destroy();
						}
					} else if (data instanceof ConfigInventory) {
						ConfigInventory configInv = (ConfigInventory) data;
						ItemStack item = event.getCurrentItem();
						int slot = event.getRawSlot();
						String name = "";

						if (item != null) if (item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();

							if (meta.hasDisplayName()) name = meta.getDisplayName();
						}

						AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, configInv);

						handler.onAnvilClick(clickEvent);

						if (clickEvent.getWillClose()) {
							event.getWhoClicked().closeInventory();
							configInv.reopenFromAnvil(false);
						}

						if (clickEvent.getWillDestroy()) destroy();
					} else {
						ItemStack item = event.getCurrentItem();
						int slot = event.getRawSlot();
						String name = "";

						if (item != null) if (item.hasItemMeta()) {
							ItemMeta meta = item.getItemMeta();

							if (meta.hasDisplayName()) name = meta.getDisplayName();
						}

						AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, data);

						handler.onAnvilClick(clickEvent);

						if (clickEvent.getWillClose()) {
							event.getWhoClicked().closeInventory();
							data.setInventory(data.getItems());
						}

						if (clickEvent.getWillDestroy()) destroy();
					}
				}
			}

			@EventHandler
			public void onInventoryClose(InventoryCloseEvent event) {
				if (event.getPlayer() instanceof Player) {
					Inventory inv = event.getInventory();

					if (inv.equals(AnvilGUI.this.inv)) {
						inv.clear();
						destroy();
						if (data instanceof Anvil) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> ((Anvil) data).setInventory(), 2l);
						else if (data instanceof ConfigInventory) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> ((ConfigInventory) data).reopenFromAnvil(true), 2l);
						else
							Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> data.setInventory(data.getItems()), 2l);
					}
				}
			}

			@EventHandler
			public void onPlayerQuit(PlayerQuitEvent event) {
				if (event.getPlayer().equals(getPlayer())) destroy();
			}
		};

		Bukkit.getPluginManager().registerEvents(listener, EnchantmentSolution.getPlugin());
	}

	public Player getPlayer() {
		return player;
	}

	public void setSlot(AnvilSlot slot, ItemStack item) {
		items.put(slot, item);
	}

	public abstract void open();

	public void destroy() {
		player = null;
		setHandler(null);
		items = null;

		HandlerList.unregisterAll(listener);

		listener = null;
	}

	public AnvilClickEventHandler getHandler() {
		return handler;
	}

	public void setHandler(ESAnvilClickEventHandler handler) {
		this.handler = handler;
	}

	public InventoryData getData() {
		return data;
	}

	public void setData(InventoryData anvil) {
		data = anvil;
	}

	public void setInventory(Inventory inv) {
		this.inv = inv;
	}

	protected ItemStack getItemStack() {
		if (data instanceof Anvil) {
			ItemStack item = data.getItems().get(0).clone();
			return AnvilNMS.setRepairCost(item, 0);
		}
		return new ItemStack(Material.NAME_TAG);
	}
}