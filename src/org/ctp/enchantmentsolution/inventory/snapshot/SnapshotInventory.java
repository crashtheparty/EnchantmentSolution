package org.ctp.enchantmentsolution.inventory.snapshot;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class SnapshotInventory {

	private ItemStack[] items = new ItemStack[37];
	private ItemStack[] armor = new ItemStack[4];
	private OfflinePlayer player;

	public SnapshotInventory(Player player) {
		this.player = player;

		setInventory();
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public void setInventory() {
		if (player.isOnline()) {
			Player p = (Player) player;
			PlayerInventory inv = p.getInventory();
			for(int i = 0; i < 36; i++) {
				ItemStack item = inv.getItem(i);
				try {
					items[i] = checkItem(item, items[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in slot " + i + ": ");
					ex.printStackTrace();
				}
			}
			ItemStack offhand = inv.getItemInOffHand();
			try {
				items[36] = checkItem(offhand, items[36]);
			} catch (Exception ex) {
				ChatUtils.sendWarning("There was a problem trying to save item " + offhand + " in offhand slot: ");
				ex.printStackTrace();
			}
			for(int i = 0; i < 4; i++) {
				ItemStack item = inv.getArmorContents()[i];
				try {
					armor[i] = checkItem(item, armor[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in armor slot " + i + ": ");
					ex.printStackTrace();
				}
			}

		}
	}

	private ItemStack checkItem(ItemStack item, ItemStack previous) {
		if (item != null) {
			List<EnchantmentLevel> enchantMeta = getEnchantments(item);
			List<EnchantmentLevel> enchantLore = new ArrayList<EnchantmentLevel>();
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) for(String s: item.getItemMeta().getLore())
				if (StringUtils.isEnchantment(s)) {
					EnchantmentLevel enchant = StringUtils.getEnchantment(s);
					if (enchant != null) enchantLore.add(enchant);
				}
			boolean change = !(checkSimilar(enchantMeta, enchantLore) && checkSimilar(enchantLore, enchantMeta));
			if(change) {
				Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
				for(EnchantmentLevel enchant : enchantMeta) {
					int level = enchant.getLevel();
					Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
					if(enchants.containsKey(ench)) level = (level > enchants.get(ench) ? level : enchants.get(ench));
					enchants.put(ench, level);
				}
				for(EnchantmentLevel enchant : enchantLore) {
					int level = enchant.getLevel();
					Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
					if(enchants.containsKey(ench)) level = (level > enchants.get(ench) ? level : enchants.get(ench));
					enchants.put(ench, level);
				}
				if (enchants.size() > 0) {
					Iterator<Entry<Enchantment, Integer>> iter = enchants.entrySet().iterator();
					while(iter.hasNext()) {
						Entry<Enchantment, Integer> entry = iter.next();
						CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
						ItemUtils.removeEnchantmentFromItem(item, custom);
						ItemUtils.addEnchantmentToItem(item, custom, entry.getValue());
					}
				}
			}
			return item.clone();
		}
		return null;
	}
	
	private boolean checkSimilar(List<EnchantmentLevel> levels, List<EnchantmentLevel> levelsTwo) {
		boolean similar = levels.size() == levelsTwo.size();
		for(EnchantmentLevel level : levels) {
			EnchantmentLevel hasLevel = null;
			for(EnchantmentLevel levelTwo : levelsTwo)
				if(level.getEnchant().getName().equals(levelTwo.getEnchant().getName()) && !(level.getEnchant() instanceof SnapshotEnchantment ^ levelTwo.getEnchant() instanceof SnapshotEnchantment)) {
					hasLevel = level;
					break;
				}
			if(hasLevel == null) similar = false;
		}
		return similar;
	}
	
	private List<EnchantmentLevel> getEnchantments(ItemStack item) {
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if (item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				CustomEnchantment ench = RegisterEnchantments.getCustomEnchantment(e.getKey());
				if(ench == null) ench = new SnapshotEnchantment(e.getKey());
				levels.add(new EnchantmentLevel(ench, e.getValue()));
			}
		}
		return levels;
	}
	
	private class SnapshotEnchantment extends CustomEnchantment{

		private Enchantment relative;
		
		public SnapshotEnchantment(Enchantment relative) {
			super(relative.getKey().getKey(), 0, 0, 0, 0, 0, 0, 0, 0, Weight.NULL, "");
			this.relative = relative;
		}

		@Override
		public Enchantment getRelativeEnchantment() {
			return relative;
		}

		@Override
		protected List<Enchantment> getDefaultConflictingEnchantments() {
			return null;
		}

		@Override
		public String getName() {
			return relative.getKey().getKey();
		}

		@Override
		public List<ItemType> getDefaultEnchantmentItemTypes() {
			return null;
		}

		@Override
		public List<ItemType> getDefaultAnvilItemTypes() {
			return null;
		}

		@Override
		public List<EnchantmentLocation> getDefaultEnchantmentLocations() {
			return null;
		}
		
	}
}
