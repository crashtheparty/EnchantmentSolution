package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.nms.fortune.Fortune_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.fortune.Fortune_v1_14;
import org.ctp.enchantmentsolution.utils.items.nms.silktouch.SilkTouch_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.silktouch.SilkTouch_v1_14;
import org.ctp.enchantmentsolution.utils.items.nms.smeltery.Smeltery_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.smeltery.Smeltery_v1_14;

public class AbilityUtils {
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Smeltery_v1_13.getSmelteryItem(block, item);
		case 4:
		case 5:
			return Smeltery_v1_14.getSmelteryItem(block, item);
		}
		return null;
	}
	
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return SilkTouch_v1_13.getSilkTouchItem(block, item);
		case 4:
		case 5:
			return SilkTouch_v1_14.getSilkTouchItem(block, item);
		}
		return null;
	}

	public static Collection<ItemStack> getFortuneItems(ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Fortune_v1_13.getFortuneItems(item, brokenBlock, priorItems);
		case 4:
		case 5:
			return Fortune_v1_14.getFortuneItems(item, brokenBlock, priorItems);
		}
		return null;
	}
	
	public static ItemStack getGoldDiggerItems(ItemStack item,
			Block brokenBlock) {
		
		if(brokenBlock.getState().getData() instanceof Crops) {
			Crops c = (Crops) brokenBlock.getState().getData();
            if(!c.getState().equals(CropState.RIPE)) {
            	return null;
            }
		} else if(brokenBlock.getState().getData() instanceof NetherWarts) {
			NetherWarts c = (NetherWarts) brokenBlock.getState().getData();
            if(!c.getState().equals(NetherWartsState.RIPE)) {
            	return null;
            }
		} else {
			return null;
		}
		int level = Enchantments.getLevel(item,
				DefaultEnchantments.GOLD_DIGGER);
		int amount = 0;
		while(level > 0) {
			double random = Math.random();
			double chance = 1.0 / 6.0;
			if(chance > random) {
				amount ++;
			}
			level --;
		}
		if(amount > 0) {
			return (new ItemStack(Material.GOLD_NUGGET, amount));
		}
		
		return null;
	}
	
	public static void dropExperience(Location loc, int amount) {
		if(amount > 0) {
			((ExperienceOrb)loc.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(amount);
		}
	}
	
	public static void giveExperience(Player player, int amount) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		PlayerInventory playerInv = player.getInventory();
		for(ItemStack i : playerInv.getArmorContents()) {
			if(i != null && Enchantments.hasEnchantment(i, Enchantment.MENDING)) {
				items.add(i);
			}
		}
		if(playerInv.getItemInMainHand() != null && Enchantments.hasEnchantment(playerInv.getItemInMainHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInMainHand());
		}
		if(playerInv.getItemInOffHand() != null && Enchantments.hasEnchantment(playerInv.getItemInOffHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInOffHand());
		}
		
		if(items.size() > 0) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			int durability = DamageUtils.getDamage(item.getItemMeta());
			while(amount > 0 && durability > 0) {
				durability -= 2;
				amount--;
			}
			if(durability < 0) durability = 0;
			DamageUtils.setDamage(item, durability);
			if(amount > 0) {
				player.giveExp(amount);
			}
		} else {
			player.giveExp(amount);
		}
	}
}
