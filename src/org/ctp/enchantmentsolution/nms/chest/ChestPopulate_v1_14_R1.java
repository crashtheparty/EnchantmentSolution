package org.ctp.enchantmentsolution.nms.chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.MainConfiguration;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.EntityHuman;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityMinecartContainer;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.MinecraftKey;
import net.minecraft.server.v1_14_R1.TileEntityLootable;
import net.minecraft.server.v1_14_R1.World;

@SuppressWarnings("resource")
public class ChestPopulate_v1_14_R1 {

	public static void populateChest(Player player, Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		TileEntityLootable te = (TileEntityLootable) nmsWorld
		.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		MinecraftKey lootChest = te.lootTable;
		if (lootChest != null) { // Lootchest
			String loot = lootChest.getKey();
			loot = loot.substring(loot.lastIndexOf('/') + 1);
			te.d((EntityHuman) null);
			for(int i = 0; i < te.getSize(); i++) {
				ItemStack item = te.getItem(i);
				CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
				if (!ConfigUtils.getBoolean(MainConfiguration.class, "use_enchanted_books")
				&& cItem.getType() == Material.ENCHANTED_BOOK) {
					cItem.setType(Material.BOOK);
				}
				ItemStack newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot));

				if (newItem != null) {
					te.setItem(i, newItem);
				}
			}
		}
	}

	public static boolean isLootChest(Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		TileEntityLootable te = (TileEntityLootable) nmsWorld
		.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		return te.lootTable != null;
	}

	public static void populateCart(Player player, Entity e) {
		if (((CraftEntity) e).getHandle() instanceof EntityMinecartContainer) {
			EntityMinecartContainer c = (EntityMinecartContainer) ((CraftEntity) e).getHandle();
			MinecraftKey lootChest = c.lootTable;
			if (lootChest != null) { // Lootchest
				String loot = lootChest.getKey();
				loot = loot.substring(loot.lastIndexOf('/') + 1);
				c.b((EntityLiving) null);
				for(int i = 0; i < c.getSize(); i++) {
					ItemStack item = c.getItem(i);
					CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
					if (!ConfigUtils.getBoolean(MainConfiguration.class, "use_enchanted_books")
					&& cItem.getType() == Material.ENCHANTED_BOOK) {
						cItem.setType(Material.BOOK);
					}
					ItemStack newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot));

					if (newItem != null) {
						c.setItem(i, newItem);
					}
				}
			}
		}
	}

	public static boolean isLootCart(Entity e) {
		if (((CraftEntity) e).getHandle() instanceof EntityMinecartContainer) {
			EntityMinecartContainer c = (EntityMinecartContainer) ((CraftEntity) e).getHandle();
			return c.lootTable != null;
		}
		return false;
	}
}
