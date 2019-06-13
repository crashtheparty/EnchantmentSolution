package org.ctp.enchantmentsolution.nms.anvil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class InventoryAnvil implements AnvilInventory{
	
	private ItemStack[] items = new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};
	private int maximumRepairCost = -1;
	private int repairCost;
	private String renameText;
	private Player holder;
	private Location location;
	
	public InventoryAnvil(Player player, Block block) {
		holder = player;
		location = block.getLocation();
	}

	@Override
	public HashMap<Integer, ItemStack> addItem(ItemStack... arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException("This method is not compatible with Enchantment Solution's anvil GUI.");
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(Material arg0) throws IllegalArgumentException {
		throw new UnsupportedOperationException("This method is not compatible with Enchantment Solution's anvil GUI.");
	}

	@Override
	public HashMap<Integer, ? extends ItemStack> all(ItemStack arg0) {
		throw new UnsupportedOperationException("This method is not compatible with Enchantment Solution's anvil GUI.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("This method is not compatible with Enchantment Solution's anvil GUI.");
	}

	@Override
	public void clear(int arg0) {
		throw new UnsupportedOperationException("This method is not compatible with Enchantment Solution's anvil GUI.");
	}

	@Override
	public boolean contains(Material arg0) throws IllegalArgumentException {
		if(arg0 == null) throw new IllegalArgumentException("Material cannot be null!");
		for(int i = 0; i < items.length; i++) {
			if(items[i].getType() == arg0) return true;
		}
		return false;
	}

	@Override
	public boolean contains(ItemStack arg0) {
		for(int i = 0; i < items.length; i++) {
			if(items[i].isSimilar(arg0)) return true;
		}
		return false;
	}

	@Override
	public boolean contains(Material arg0, int arg1) throws IllegalArgumentException {
		if(arg0 == null) throw new IllegalArgumentException("Material cannot be null!");
		for(int i = 0; i < items.length; i++) {
			if(items[i].getType() == arg0 && items[i].getAmount() == arg1) return true;
		}
		return false;
	}

	@Override
	public boolean contains(ItemStack arg0, int arg1) {
		for(int i = 0; i < items.length; i++) {
			if(items[i].isSimilar(arg0) && items[i].getAmount() == arg1) return true;
		}
		return false;
	}

	@Override
	public boolean containsAtLeast(ItemStack arg0, int arg1) {
		int amount = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i].isSimilar(arg0) && items[i].getAmount() + amount >= arg1) return true;
			if(items[i].isSimilar(arg0)) amount += items[i].getAmount();
		}
		return false;
	}

	@Override
	public int first(Material arg0) throws IllegalArgumentException {
		if(arg0 == null) throw new IllegalArgumentException("Material cannot be null!");
		for(int i = 0; i < items.length; i++) {
			if(items[i].getType() == arg0) return i;
		}
		return -1;
	}

	@Override
	public int first(ItemStack arg0) {
		for(int i = 0; i < items.length; i++) {
			if(items[i].isSimilar(arg0)) return i;
		}
		return -1;
	}

	@Override
	public int firstEmpty() {
		for(int i = 0; i < items.length; i++) {
			if(items[i].getType() == Material.AIR) return i;
		}
		return -1;
	}

	@Override
	public ItemStack[] getContents() {
		return items;
	}

	@Override
	public InventoryHolder getHolder() {
		return holder;
	}

	@Override
	public ItemStack getItem(int arg0) {
		return items[arg0];
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public int getSize() {
		return items.length;
	}

	@Override
	public ItemStack[] getStorageContents() {
		return items;
	}

	@Override
	public InventoryType getType() {
		return InventoryType.ANVIL;
	}

	@Override
	public List<HumanEntity> getViewers() {
		return Arrays.asList(holder);
	}

	@Override
	public ListIterator<ItemStack> iterator() {
		return Arrays.asList(items).listIterator();
	}

	@Override
	public ListIterator<ItemStack> iterator(int arg0) {
		return Arrays.asList(items).listIterator(arg0);
	}

	@Override
	public void remove(Material arg0) throws IllegalArgumentException {
		if(arg0 == null) throw new IllegalArgumentException("Material cannot be null!");
		for(int i = 0; i < items.length; i++) {
			if(items[i].getType() == arg0) items[i] = new ItemStack(Material.AIR);
		}
	}

	@Override
	public void remove(ItemStack arg0) {
		for(int i = 0; i < items.length; i++) {
			if(items[i].isSimilar(arg0)) items[i] = new ItemStack(Material.AIR);
		}
	}

	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... arg0) throws IllegalArgumentException {
		HashMap<Integer, ItemStack> newItems = new HashMap<Integer, ItemStack>();
		for(int i = 0; i < items.length; i++) {
			for(ItemStack item : arg0) {
				if(items[i].isSimilar(item)) items[i] = new ItemStack(Material.AIR);
			}
		}
		return newItems;
	}

	@Override
	public void setContents(ItemStack[] arg0) throws IllegalArgumentException {
		if(arg0.length > items.length) throw new IllegalArgumentException("Too many items in array!");
		int i = 0;
		for(i = 0; i < arg0.length; i++) {
			items[i] = arg0[i];
		}
		for(int j = i; j < items.length; j++) {
			items[j] = new ItemStack(Material.AIR);
		}
		
	}

	@Override
	public void setItem(int arg0, ItemStack arg1) {
		items[arg0] = arg1;
	}

	@Override
	public void setMaxStackSize(int arg0) {
		
	}

	@Override
	public void setStorageContents(ItemStack[] arg0) throws IllegalArgumentException {
		if(arg0.length > items.length) throw new IllegalArgumentException("Too many items in array!");
		int i = 0;
		for(i = 0; i < arg0.length; i++) {
			items[i] = arg0[i];
		}
		for(int j = i; j < items.length; j++) {
			items[j] = new ItemStack(Material.AIR);
		}
	}

	@Override
	public int getMaximumRepairCost() {
		return maximumRepairCost == -1 ? ConfigUtils.getMaxRepairLevel() : maximumRepairCost;
	}

	@Override
	public String getRenameText() {
		return renameText;
	}

	@Override
	public int getRepairCost() {
		return repairCost;
	}

	@Override
	public void setMaximumRepairCost(int arg0) {
		maximumRepairCost = arg0;
	}

	@Override
	public void setRepairCost(int arg0) {
		repairCost = arg0;
	}

}
