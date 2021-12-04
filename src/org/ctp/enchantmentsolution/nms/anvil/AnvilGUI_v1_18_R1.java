package org.ctp.enchantmentsolution.nms.anvil;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.nms.anvil.AnvilSlot;
import org.ctp.enchantmentsolution.inventory.Anvil;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.level.World;

public class AnvilGUI_v1_18_R1 extends AnvilGUI {
	private class AnvilContainer extends ContainerAnvil {
		public AnvilContainer(EntityHuman entity, int windowId, World world) {
			super(windowId, entity.fq(), at(world, new BlockPosition(0, 0, 0)));
			setTitle(new ChatMessage("container.anvil"));
		}

		@Override
		public boolean a(EntityHuman entityhuman) {
			return true;
		}
	}

	private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

	public AnvilGUI_v1_18_R1(Player player, final ESAnvilClickEventHandler handler, InventoryData data) {
		super(player, handler, data);
	}

	@Override
	public void setSlot(AnvilSlot slot, ItemStack item) {
		items.put(slot, item);
	}

	@SuppressWarnings("resource")
	@Override
	public void open() {
		EntityPlayer p = ((CraftPlayer) getPlayer()).getHandle();

		// Counter stuff that the game uses to keep track of inventories
		int c = p.nextContainerCounter();
		World w = p.W();

		AnvilContainer container = new AnvilContainer(p, c, w);

		// Set the items to the items from the inventory given
		Inventory inv = container.getBukkitView().getTopInventory();

		for(AnvilSlot slot: items.keySet())
			inv.setItem(slot.getSlot(), items.get(slot));

		inv.setItem(0, getItemStack());

		setInventory(container.getBukkitView().getTopInventory());

		// Send the packet
		p.b.a(new PacketPlayOutOpenWindow(c, container.a(), new ChatMessage("Repairing")));

		// Set their active container to the container
		p.a(container);
		p.bW = container;
	}

	public static void createAnvil(Player player, InventoryData data) {
		ESAnvilClickEventHandler handler = ESAnvilClickEventHandler.getHandler(player, data);
		if (data instanceof Anvil) ((Anvil) data).setInLegacy(true);
		AnvilGUI_v1_18_R1 gui = new AnvilGUI_v1_18_R1(player, handler, data);
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