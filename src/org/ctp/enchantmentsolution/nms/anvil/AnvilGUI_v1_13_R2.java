package org.ctp.enchantmentsolution.nms.anvil;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.ConfigInventory;
import org.ctp.enchantmentsolution.inventory.InventoryData;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ChatMessage;
import net.minecraft.server.v1_13_R2.ContainerAnvil;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.PacketPlayOutOpenWindow;

public class AnvilGUI_v1_13_R2 {
	private class AnvilContainer extends ContainerAnvil {
		public AnvilContainer(EntityHuman entity) {
			super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
		}

		@Override
		public boolean canUse(EntityHuman entityhuman) {
			return true;
		}
	}

	public enum AnvilSlot {
		INPUT_LEFT(0), INPUT_RIGHT(1), OUTPUT(2);

		private int slot;

		AnvilSlot(int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return slot;
		}

		public static AnvilSlot bySlot(int slot) {
			for(AnvilSlot anvilSlot: values()) {
				if (anvilSlot.getSlot() == slot) {
					return anvilSlot;
				}
			}

			return null;
		}
	}

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

	public interface AnvilClickEventHandler {
		void onAnvilClick(AnvilClickEvent event);
	}

	private Player player;

	private AnvilClickEventHandler handler;

	private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

	private Inventory inv;

	private Listener listener;

	private InventoryData data;

	public AnvilGUI_v1_13_R2(Player player, final AnvilClickEventHandler handler, InventoryData data) {
		this.player = player;
		this.setHandler(handler);
		this.setData(data);

		this.listener = new Listener(){
			@EventHandler
			public void onInventoryClick(InventoryClickEvent event) {
				if (event.getWhoClicked() instanceof Player) {

					if (event.getInventory().equals(inv)) {
						event.setCancelled(true);
						if (data instanceof Anvil) {
							Anvil anvil = (Anvil) data;
							ItemStack item = event.getCurrentItem();
							int slot = event.getRawSlot();
							String name = "";

							if (item != null) {
								if (item.hasItemMeta()) {
									ItemMeta meta = item.getItemMeta();

									if (meta.hasDisplayName()) {
										name = meta.getDisplayName();
									}
								}
							}

							AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, anvil);

							handler.onAnvilClick(clickEvent);

							if (clickEvent.getWillClose()) {
								event.getWhoClicked().closeInventory();
								anvil.setInventory();
							}

							if (clickEvent.getWillDestroy()) {
								anvil.checkAnvilBreak();
								destroy();
							}
						} else if (data instanceof ConfigInventory) {
							ConfigInventory configInv = (ConfigInventory) data;
							ItemStack item = event.getCurrentItem();
							int slot = event.getRawSlot();
							String name = "";

							if (item != null) {
								if (item.hasItemMeta()) {
									ItemMeta meta = item.getItemMeta();

									if (meta.hasDisplayName()) {
										name = meta.getDisplayName();
									}
								}
							}

							AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, configInv);

							handler.onAnvilClick(clickEvent);

							if (clickEvent.getWillClose()) {
								event.getWhoClicked().closeInventory();
								configInv.reopenFromAnvil(false);
							}

							if (clickEvent.getWillDestroy()) {
								destroy();
							}
						}
					}
				}
			}

			@EventHandler
			public void onInventoryClose(InventoryCloseEvent event) {
				if (event.getPlayer() instanceof Player) {
					Inventory inv = event.getInventory();

					if (inv.equals(AnvilGUI_v1_13_R2.this.inv)) {
						inv.clear();
						destroy();
						if (data instanceof Anvil) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), new Runnable() {

								@Override
								public void run() {
									((Anvil) data).setInventory();
								}
								
							}, 2l);
						} else if (data instanceof ConfigInventory) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), new Runnable() {

								@Override
								public void run() {
									((ConfigInventory) data).reopenFromAnvil(true);
								}
								
							}, 2l);
						}
					}
				}
			}

			@EventHandler
			public void onPlayerQuit(PlayerQuitEvent event) {
				if (event.getPlayer().equals(getPlayer())) {
					destroy();
				}
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

	public void open() {
		EntityPlayer p = ((CraftPlayer) player).getHandle();

		AnvilContainer container = new AnvilContainer(p);

		// Set the items to the items from the inventory given
		inv = container.getBukkitView().getTopInventory();

		for(AnvilSlot slot: items.keySet()) {
			inv.setItem(slot.getSlot(), items.get(slot));
		}

		inv.setItem(0, new ItemStack(Material.NAME_TAG));

		// Counter stuff that the game uses to keep track of inventories
		int c = p.nextContainerCounter();

		// Send the packet
		p.playerConnection
				.sendPacket(new PacketPlayOutOpenWindow(c, "minecraft:anvil", new ChatMessage("Repairing"), 0));
		// Set their active container to the container
		p.activeContainer = container;

		// Set their active container window id to that counter stuff
		p.activeContainer.windowId = c;

		// Add the slot listener
		p.activeContainer.addSlotListener(p);
	}

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

	public void setHandler(AnvilClickEventHandler handler) {
		this.handler = handler;
	}

	public InventoryData getData() {
		return data;
	}

	public void setData(InventoryData anvil) {
		this.data = anvil;
	}

	public static void createAnvil(Player player, InventoryData data) {
		AnvilClickEventHandler handler = new AnvilClickEventHandler(){
			public void onAnvilClick(AnvilClickEvent event) {
				if (event.getSlot() == null) {
					event.setWillClose(false);
					event.setWillDestroy(false);
					return;
				}
				if (event.getSlot().getSlot() != 2) {
					event.setWillClose(false);
					event.setWillDestroy(false);
					return;
				}
				if(event.getName().equals("")) {
					if(data instanceof Anvil) {
						Anvil anvil = (Anvil) data;
						ItemStack item = anvil.getItems().get(0);
						if(item != null) {
							if(!item.getItemMeta().hasDisplayName()) {
								event.setWillClose(false);
								event.setWillDestroy(false);
								return;
							}
						}
					} else {
						event.setWillClose(false);
						event.setWillDestroy(false);
						return;
					}
				}

				event.getData().setItemName(event.getName());
				if(player.getGameMode() != GameMode.CREATIVE) {
					player.setLevel(player.getLevel() - 1);
				}
			}
		};
		AnvilGUI_v1_13_R2 gui = new AnvilGUI_v1_13_R2(player, handler, data);
		gui.open();
	}

}
