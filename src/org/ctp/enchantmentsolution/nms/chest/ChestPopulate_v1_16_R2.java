package org.ctp.enchantmentsolution.nms.chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

import net.minecraft.server.v1_16_R2.*;

@SuppressWarnings("resource")
public class ChestPopulate_v1_16_R2 {

	public static void populateChest(Player player, Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		TileEntityLootable te = (TileEntityLootable) nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		MinecraftKey lootChest = te.lootTable;
		if (lootChest != null) { // Lootchest
			String loot = lootChest.getKey();
			loot = loot.substring(loot.lastIndexOf('/') + 1);
			te.d((EntityHuman) null);
			for(int i = 0; i < te.getSize(); i++) {
				ItemStack item = te.getItem(i);
				MinecraftKey minecraftkey = IRegistry.ENCHANTMENT.getKey(Enchantments.SOUL_SPEED);
				NBTTagList nbttaglist = item.getEnchantments();

				boolean soulSpeed = false;
				for(int j = 0; j < nbttaglist.size(); ++j) {
					NBTTagCompound nbttagcompound = nbttaglist.getCompound(j);
					MinecraftKey minecraftkey1 = MinecraftKey.a(nbttagcompound.getString("id"));

					if (minecraftkey1 != null && minecraftkey1.equals(minecraftkey)) soulSpeed = true;
				}
				CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
				ItemStack newItem = null;
				if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && cItem.getType() == Material.ENCHANTED_BOOK) {
					cItem.setType(Material.BOOK);
					newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot));
				} else if (item.hasEnchantments()) newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot, soulSpeed ? EnchantmentLocation.PIGLIN_TRADES : EnchantmentLocation.CHEST_LOOT));
				if (newItem != null) te.setItem(i, newItem);
			}
		}
	}

	public static boolean isLootChest(Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		TileEntityLootable te = (TileEntityLootable) nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		return te.lootTable != null;
	}

	public static void populateCart(Player player, Entity e) {
		if (((CraftEntity) e).getHandle() instanceof EntityMinecartContainer) {
			EntityMinecartContainer c = (EntityMinecartContainer) ((CraftEntity) e).getHandle();
			MinecraftKey lootChest = c.lootTable;
			if (lootChest != null) { // Lootchest
				String loot = lootChest.getKey();
				loot = loot.substring(loot.lastIndexOf('/') + 1);
				c.d((EntityHuman) null);
				for(int i = 0; i < c.getSize(); i++) {
					ItemStack item = c.getItem(i);
					MinecraftKey minecraftkey = IRegistry.ENCHANTMENT.getKey(Enchantments.SOUL_SPEED);
					NBTTagList nbttaglist = item.getEnchantments();

					boolean soulSpeed = false;
					for(int j = 0; j < nbttaglist.size(); ++j) {
						NBTTagCompound nbttagcompound = nbttaglist.getCompound(j);
						MinecraftKey minecraftkey1 = MinecraftKey.a(nbttagcompound.getString("id"));

						if (minecraftkey1 != null && minecraftkey1.equals(minecraftkey)) soulSpeed = true;
					}
					CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
					ItemStack newItem = null;
					if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && cItem.getType() == Material.ENCHANTED_BOOK) {
						cItem.setType(Material.BOOK);
						newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot));
					} else if (item.hasEnchantments()) newItem = CraftItemStack.asNMSCopy(GenerateUtils.generateChestLoot(player, cItem, loot, soulSpeed ? EnchantmentLocation.PIGLIN_TRADES : EnchantmentLocation.CHEST_LOOT));
					if (newItem != null) c.setItem(i, newItem);
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
