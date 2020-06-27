package org.ctp.enchantmentsolution.utils.player;

import java.util.*;

import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.ServerNMS;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ESPlayer {

	private final OfflinePlayer player;
	private RPGPlayer rpg;
	private Map<Enchantment, Long> cooldowns;
	private List<ItemStack> soulItems;
	private Map<Integer, Integer> blocksBroken;
	private static Map<Integer, Integer> GLOBAL_BLOCKS = new HashMap<Integer, Integer>();

	public ESPlayer(OfflinePlayer player) {
		this.player = player;
		rpg = RPGUtils.getPlayer(player);
		cooldowns = new HashMap<Enchantment, Long>();
		blocksBroken = new HashMap<Integer, Integer>();
		removeSoulItems();
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public long getCooldown(Enchantment enchant) {
		return cooldowns.containsKey(enchant) ? cooldowns.get(enchant) : 0;
	}

	public boolean setCooldown(Enchantment enchant) {
		cooldowns.put(enchant, System.currentTimeMillis());
		return cooldowns.containsKey(enchant);
	}

	public RPGPlayer getRPG() {
		return rpg;
	}

	public List<ItemStack> getSoulItems() {
		return soulItems;
	}

	public void setSoulItems(List<ItemStack> items) {
		soulItems = items;
	}

	public void removeSoulItems() {
		soulItems = new ArrayList<ItemStack>();
	}

	public boolean canBreakBlock() {
		int tick = ServerNMS.getCurrentTick();
		if (GLOBAL_BLOCKS.containsKey(tick) && GLOBAL_BLOCKS.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_GLOBAL.getInt()) return false;
		if (blocksBroken.containsKey(tick) && blocksBroken.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_PLAYER.getInt()) return false;
		return true;
	}

	public void breakBlock() {
		int tick = ServerNMS.getCurrentTick();
		int blocks = 1;
		if (blocksBroken.containsKey(tick)) blocks += blocksBroken.get(tick);
		blocksBroken.put(tick, blocks);
		int globalBlocks = 1;
		if (GLOBAL_BLOCKS.containsKey(tick)) globalBlocks += GLOBAL_BLOCKS.get(tick);
		GLOBAL_BLOCKS.put(tick, globalBlocks);
	}

}
