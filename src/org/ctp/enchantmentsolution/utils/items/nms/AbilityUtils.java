package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.Collection;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.Version;
import org.ctp.enchantmentsolution.utils.items.nms.fortune.Fortune_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.silktouch.SilkTouch_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.smeltery.Smeltery_v1_13;

public class AbilityUtils {
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		if(Version.VERSION_NUMBER < 4) {
			return Smeltery_v1_13.getSmelteryItem(block, item);
		}
		return null;
	}
	
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		if(Version.VERSION_NUMBER < 4) {
			return SilkTouch_v1_13.getSilkTouchItem(block, item);
		}
		return null;
	}

	public static Collection<ItemStack> getFortuneItems(ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems) {
		if(Version.VERSION_NUMBER < 4) {
			return Fortune_v1_13.getFortuneItems(item, brokenBlock, priorItems);
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
}
