package org.ctp.enchantmentsolution.nms.anvil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.nms.anvil.AnvilSlot;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.inventory.Anvil;

import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.World;

public class AnvilGUI_6 extends AnvilGUI {
	private class AnvilContainer extends ContainerAnvil {
		public AnvilContainer(EntityHuman entity, int windowId, World world) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			super(windowId, (PlayerInventory) returnAccessible(EntityHuman.class.getDeclaredMethod("fJ"), entity), at(world, new BlockPosition(0, 0, 0)));
		}

		@Override
		public boolean a(EntityHuman entityhuman) {
			return true;
		}
	}

	private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

	public AnvilGUI_6(Player player, final ESAnvilClickEventHandler handler, InventoryData data) {
		super(player, handler, data);
	}

	@Override
	public void setSlot(AnvilSlot slot, ItemStack item) {
		items.put(slot, item);
	}

	@SuppressWarnings("resource")
	@Override
	public void open() {
		EntityPlayer p = (EntityPlayer) getCraftBukkitEntity(getPlayer());

		// Counter stuff that the game uses to keep track of inventories
		int c = p.nextContainerCounter();
		World w = null;
		try {
			w = (World) (Entity.class.getDeclaredMethod("Y").invoke(p));
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		AnvilContainer container;
		try {
			container = new AnvilContainer(p, c, w);
			container.setTitle(IChatBaseComponent.a("Repairing"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Set the items to the items from the inventory given
		Inventory inv = getInventory(container);

		for(AnvilSlot slot: items.keySet())
			inv.setItem(slot.getSlot(), items.get(slot));

		inv.setItem(0, getItemStack());

		setInventory(inv);
		// Send the packet
		PlayerConnection pc = null;

		try {
			Class<?> clazz = EntityPlayer.class;
			Field f = clazz.getDeclaredField("b");
			if (f.get(p) instanceof PlayerConnection) pc = (PlayerConnection) f.get(p);
			else
				Chatable.get().sendInfo("Issue with Anvil NMS - Player Connection not found");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			TranslatableComponent t = new TranslatableComponent("container.repair");
			@SuppressWarnings("unchecked")
			PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(c, (Containers<ContainerAnvil>) (Container.class.getDeclaredMethod("a").invoke(container)), ChatSerializer.a(ComponentSerializer.toString(t)));
			pc.getClass().getDeclaredMethod("a", Packet.class).invoke(pc, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Set their active container to the container
		try {
			p.getClass().getDeclaredMethod("a", Container.class).invoke(p, container);
			Field f = EntityHuman.class.getDeclaredField("bP");
			f.setAccessible(true);
			f.set(p, container);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void createAnvil(Player player, InventoryData data) {
		ESAnvilClickEventHandler handler = ESAnvilClickEventHandler.getHandler(player, data);
		if (data instanceof Anvil) ((Anvil) data).setInLegacy(true);
		AnvilGUI_6 gui = new AnvilGUI_6(player, handler, data);
		gui.open();
	}

	static ContainerAccess at(final World world, final BlockPosition blockposition) {
		return new ContainerAccess() {
			// CraftBukkit start
			@Override
			public World getWorld() {
				return world;
			}

			@Override
			public BlockPosition getPosition() {
				return blockposition;
			}
			// CraftBukkit end

			@Override
			public <T> Optional<T> a(BiFunction<World, BlockPosition, T> bifunction) {
				return Optional.of(bifunction.apply(world, blockposition));
			}
		};
	}

}