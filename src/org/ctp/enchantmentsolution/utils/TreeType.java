//class from https://bukkit.org/threads/get-sapling-from-leaf-block.373726/
package org.ctp.enchantmentsolution.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public enum TreeType {

    OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK;
   
    public static TreeType get(Material material, byte data) {
                //oak / acacia
                if (data == 0 || data == 4 || data == 8 || data == 12) return material.name().contains("2")  ? ACACIA : OAK;
                //spruce / dark oak
                if (data == 1 || data == 5 || data == 9 || data == 13) return material.name().contains("2") ? DARK_OAK : SPRUCE;
                //birch
                if (data == 2 || data == 6 || data == 10 || data == 14) return BIRCH;
                //jungle
                if (data == 3 || data == 7 || data == 11 || data == 15) return JUNGLE;
                return null;
    }
    @SuppressWarnings("deprecation")
    public static TreeType get(Block b) {
        return get(b.getType(), b.getData());
    }
   
    public ItemStack getSapling(int amount) {
        return new ItemStack(Material.SAPLING, amount, getSaplingData());
    }
   
    public ItemStack getLeaves(int amount) {
        Material type = (this == ACACIA || this == DARK_OAK) ? Material.LEAVES_2 : Material.LEAVES;
        return new ItemStack(type, amount, getLeavesData());
    }
   
    public ItemStack getLog(int amount) {
        Material type = (this == ACACIA || this == DARK_OAK) ? Material.LEAVES_2 : Material.LEAVES;
        return new ItemStack(type, amount, getLogData());
    }
   
    public byte getSaplingData() {
        return (byte) Arrays.asList(values()).indexOf(this);
    }
   
    public byte getLeavesData() {
        return (byte) Arrays.asList(values()).indexOf(this);
    }
   
    public byte getLogData() {
        if (this == ACACIA) return 0;
        if (this == DARK_OAK) return 1;
        return (byte) Arrays.asList(values()).indexOf(this);
    }
}