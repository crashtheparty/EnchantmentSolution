package org.ctp.enchantmentsolution.nms.anvil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.data.inventory.InventoryData;
import org.ctp.crashapi.nms.anvil.AnvilSlot;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.inventory.Anvil;

import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.World;

public class AnvilGUI_10 extends AnvilGUI {
	private class AnvilContainer extends ContainerAnvil {
		public AnvilContainer(EntityHuman entity, int windowId, World world) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			super(windowId, (PlayerInventory) returnAccessible(EntityHuman.class.getDeclaredMethod("gc"), entity), at(world, new BlockPosition(0, 0, 0)));
			this.checkReachable = false;
		}

		@Override
		public boolean a(EntityHuman entityhuman) {
			return true;
		}
	}

	private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

	public AnvilGUI_10(Player player, final ESAnvilClickEventHandler handler, InventoryData data) {
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
			w = (World) (Entity.class.getDeclaredMethod("dP").invoke(p));
		} catch (Exception ex) {
			Chatable.sendStackTrace(ex);
			return;
		}

		AnvilContainer container;
		try {
			container = new AnvilContainer(p, c, w);
			container.setTitle(IChatBaseComponent.a("Repairing"));
		} catch (Exception e) {
			Chatable.sendStackTrace(e);
			return;
		}

		// Set the items to the items from the inventory given
		Inventory inv = getInventory(container);

		for(AnvilSlot slot: items.keySet())
			inv.setItem(slot.getSlot(), items.get(slot));

		inv.setItem(0, getItemStack());

		// Send the packet
		PlayerConnection pc = null;
		MinecraftServer server = null;

		try {
			Class<?> clazz = EntityPlayer.class;
			Field f1 = clazz.getDeclaredField("c");
			Field f2 = clazz.getDeclaredField("d");
			if (f1.get(p) instanceof PlayerConnection) pc = (PlayerConnection) f1.get(p);
			else
				Chatable.get().sendInfo("Issue with Anvil NMS - Player Connection not found");
			if (f2.get(p) instanceof MinecraftServer) server = (MinecraftServer) f2.get(p);
			else
				Chatable.get().sendInfo("Issue with Anvil NMS - Server not found");
		} catch (Exception ex) {
			Chatable.sendStackTrace(ex);
		}
		try {
			Class<?> clazz = MinecraftServer.class;
			Method m = clazz.getDeclaredMethod("bc");
			Object o = m.invoke(server);
			if (!(o instanceof IRegistryCustom)) {
				Chatable.get().sendInfo("Issue with Anvil NMS - IRegistry not found");
				return;
			}
			TranslatableComponent t = new TranslatableComponent("container.repair");
			@SuppressWarnings("unchecked")
			PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(c, (Containers<ContainerAnvil>) (Container.class.getDeclaredMethod("a").invoke(container)), (IChatBaseComponent) ChatSerializer.class.getDeclaredMethod("a", String.class, HolderLookup.a.class).invoke(null, ComponentSerializer.toString(t), o));
			ServerPlayerConnection.class.getDeclaredMethod("b", Packet.class).invoke(pc, packet);
		} catch (Exception ex) {
			Chatable.sendStackTrace(ex);
		}

		// Set their active container to the container
		try {
			Field f = EntityHuman.class.getDeclaredField("cb");
			f.setAccessible(true);
			f.set(p, container);
			p.getClass().getDeclaredMethod("a", Container.class).invoke(p, container);
		} catch (Exception ex) {
			Chatable.sendStackTrace(ex);
		}

		setInventory(inv);
	}

	public static void createAnvil(Player player, InventoryData data) {
		ESAnvilClickEventHandler handler = ESAnvilClickEventHandler.getHandler(player, data);
		if (data instanceof Anvil) ((Anvil) data).setInLegacy(true);
		AnvilGUI_10 gui = new AnvilGUI_10(player, handler, data);
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