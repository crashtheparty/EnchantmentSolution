package org.ctp.enchantmentsolution.listeners.fishing.mcmmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.mcmmo.Fishing;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingThread;
import org.ctp.enchantmentsolution.listeners.fishing.mcmmo.McMMOFishingListener;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.experience.XPGainReason;
import com.gmail.nossr50.datatypes.experience.XPGainSource;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.fishing.FishingManager;
import com.gmail.nossr50.util.ItemUtils;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;

public class McMMOOverhaulFishingListener extends McMMOFishingListener{
	
	public void onMcMMOPlayerFishingTreasure(McMMOPlayerFishingTreasureEvent event) {
		if(!ConfigUtils.getFishingLoot()) return;
		FishingManager manager = UserManager.getPlayer(event.getPlayer()).getFishingManager();
		int tier = manager.getLootTier();
		Player player = event.getPlayer();
		ItemStack treasure = event.getTreasure();

		if (Permissions.isSubSkillEnabled(player, SubSkillType.FISHING_MAGIC_HUNTER)
				&& ItemUtils.isEnchantable(treasure)) {
			List<EnchantmentLevel> enchantments = getEnchants(player, treasure, tier);
			event.setCancelled(true);
			treasure = event.getTreasure();
			int treasureXp = event.getXp();

			if (treasure != null) {
				boolean enchanted = false;

				if (!enchantments.isEmpty()) {
					treasure = Enchantments.addEnchantmentsToItem(treasure, enchantments);
					enchanted = true;
				}

				if (enchanted) {
					player.sendMessage(LocaleLoader.getString("Fishing.Ability.TH.MagicFound"));
				}
				add(player, treasure, treasureXp);
			}
		}
	}

	public void onPlayerFish(PlayerFishEvent event) {
		switch (event.getState()) {
		case CAUGHT_FISH:
			// TODO Update to new API once available! Waiting for case CAUGHT_TREASURE:
			Item fishingCatch = (Item) event.getCaught();
			McMMOFishingThread thread = null;
			for(McMMOFishingThread t : getPlayerItems()) {
				if(t.getPlayer().equals(event.getPlayer())) {
					int fishXp = ExperienceConfig.getInstance().getXp(PrimarySkillType.FISHING, fishingCatch.getItemStack().getType());
					fishingCatch.setItemStack(t.getItem());
					thread = t;

					FishingManager manager = UserManager.getPlayer(event.getPlayer()).getFishingManager();
					manager.applyXpGain(t.getXp() + fishXp, XPGainReason.PVE, XPGainSource.SELF);
				}
			}
			if(thread != null) {
				remove(thread);
			}
			return;
		default:
			return;
		}
	}

	private List<EnchantmentLevel> getEnchants(Player player, ItemStack treasure, int tier) {
		HashMap<String, Double> chanceMap = new HashMap<String, Double>();
		YamlConfig config = EnchantmentSolution.getPlugin().getConfigFiles().getFishingConfig();
		String location = ConfigUtils.isLevel50() ? "Enchantments_Rarity_50"
				: "Enchantments_Rarity_30";
		
		for (String s : config.getConfigurationInfo(location)) {
			String type = s.substring(s.lastIndexOf(".") + 1);
			chanceMap.put(type, Fishing.getTierChances(tier, type, ConfigUtils.isLevel50()));
		}

		double random = Math.random() * 100;
		for(Iterator<java.util.Map.Entry<String, Double>> it = chanceMap.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, Double> e = it.next();
			random -= e.getValue();
			if (random <= 0) {
				return Fishing.getEnchantsFromConfig(player, treasure, e.getKey(), ConfigUtils.isLevel50());
			}
		}
		return new ArrayList<EnchantmentLevel>();
	}

}
