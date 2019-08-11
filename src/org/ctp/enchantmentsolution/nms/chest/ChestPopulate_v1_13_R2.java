package org.ctp.enchantmentsolution.nms.chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.EntityMinecartContainer;
import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.TileEntityLootable;
import net.minecraft.server.v1_13_R2.World;

public class ChestPopulate_v1_13_R2 {

	public static void populateChest(Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
        TileEntityLootable te = (TileEntityLootable) nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		MinecraftKey lootChest = te.getLootTable();
		if(lootChest != null){ //Lootchest
			String loot = lootChest.getKey();
			loot = loot.substring(loot.lastIndexOf('/') + 1);
        	te.d((EntityHuman) null);
            for(int i = 0; i < te.getSize(); i++) {
            	ItemStack item = te.getItem(i);
            	ItemStack newItem = null;
            	CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
            	if((cItem.getType().equals(Material.ENCHANTED_BOOK))) {
            		newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(new org.bukkit.inventory.ItemStack(Material.BOOK), loot));
            	} else if (item.hasEnchantments()) {
            		newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(CraftItemStack.asBukkitCopy(item), loot));
            	}
            	if(newItem != null) {
            		te.setItem(i, newItem);
            	}
            }
        }
	}
	
	public static boolean isLootChest(Block block) {
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
        TileEntityLootable te = (TileEntityLootable) nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        return te.getLootTable() != null;
	}
	
	public static void populateCart(Entity e) {
		if(((CraftEntity)e).getHandle() instanceof EntityMinecartContainer) {
			EntityMinecartContainer c = (EntityMinecartContainer) (((CraftEntity)e).getHandle());
			MinecraftKey lootChest = c.getLootTable();
			if(lootChest != null){ //Lootchest
				String loot = lootChest.getKey();
				loot = loot.substring(loot.lastIndexOf('/') + 1);
	            c.b((EntityLiving) null);
	            for(int i = 0; i < c.getSize(); i++) {
	            	ItemStack item = c.getItem(i);
	            	ItemStack newItem = null;
	            	CraftItemStack cItem = CraftItemStack.asCraftMirror(item);
	            	if((cItem.getType().equals(Material.ENCHANTED_BOOK))) {
	            		newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(new org.bukkit.inventory.ItemStack(Material.BOOK), loot));
	            	} else if (item.hasEnchantments()) {
	            		newItem = CraftItemStack.asNMSCopy(ItemUtils.addNMSEnchantment(CraftItemStack.asBukkitCopy(item), loot));
	            	}
	            	if(newItem != null) {
	            		c.setItem(i, newItem);
	            	}
	            }
	        }
		}
	}
	
	public static boolean isLootCart(Entity e) {
		if(((CraftEntity)e).getHandle() instanceof EntityMinecartContainer) {
			EntityMinecartContainer c = (EntityMinecartContainer) (((CraftEntity)e).getHandle());
			return c.getLootTable() != null;
		}
		return false;
	}
}
