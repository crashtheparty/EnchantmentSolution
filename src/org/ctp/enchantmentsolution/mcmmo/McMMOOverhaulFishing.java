package org.ctp.enchantmentsolution.mcmmo;

import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.experience.XPGainReason;
import com.gmail.nossr50.datatypes.experience.XPGainSource;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.fishing.FishingManager;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;

public class McMMOOverhaulFishing extends McMMOFishing {

	public void onMcMMOPlayerFishingTreasure(McMMOPlayerFishingTreasureEvent event) {
		if (!ConfigString.USE_LOOT.getBoolean("fishing.use")) return;
		Player player = event.getPlayer();
		ItemStack treasure = event.getTreasure();

		if (Permissions.isSubSkillEnabled(player, SubSkillType.FISHING_MAGIC_HUNTER) && EnchantmentUtils.isEnchantable(treasure)) {
			List<EnchantmentLevel> enchantments = McMMOHandler.getEnchants(player, treasure);
			event.setCancelled(true);
			treasure = event.getTreasure();
			int treasureXp = event.getXp();

			if (treasure != null) {
				boolean enchanted = false;

				if (!enchantments.isEmpty()) {
					treasure = EnchantmentUtils.addEnchantmentsToItem(treasure, enchantments);
					enchanted = true;
				}

				if (enchanted) player.sendMessage(LocaleLoader.getString("Fishing.Ability.TH.MagicFound"));
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
				for(McMMOFishingThread t: getPlayerItems())
					if (t.getPlayer().equals(event.getPlayer())) {
						int fishXp = ExperienceConfig.getInstance().getXp(PrimarySkillType.FISHING, fishingCatch.getItemStack().getType());
						fishingCatch.setItemStack(t.getItem());
						thread = t;

						FishingManager manager = UserManager.getPlayer(event.getPlayer()).getFishingManager();
						manager.applyXpGain(t.getXp() + fishXp, XPGainReason.PVE, XPGainSource.SELF);
					}
				if (thread != null) remove(thread);
				return;
			default:
				return;
		}
	}

}
