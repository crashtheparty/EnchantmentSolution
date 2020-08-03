package org.ctp.enchantmentsolution.mcmmo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.skills.SecondaryAbility;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.SkillManager;
import com.gmail.nossr50.skills.fishing.FishingManager;
import com.gmail.nossr50.util.player.UserManager;

public class McMMOClassicFishing extends McMMOFishing {

	public void onMcMMOPlayerFishingTreasure(McMMOPlayerFishingTreasureEvent event) {
		if (!ConfigString.USE_LOOT.getBoolean("fishing.use")) return;
		Player player = event.getPlayer();
		ItemStack treasure = event.getTreasure();

		if (getSecondaryAbility(player, treasure, SecondaryAbility.MAGIC_HUNTER) && com.gmail.nossr50.util.ItemUtils.isEnchantable(treasure)) {
			List<EnchantmentLevel> enchantments = McMMOHandler.getEnchants(player, treasure);
			event.setCancelled(true);
			treasure = event.getTreasure();
			int treasureXp = event.getXp();

			if (treasure != null && enchantments != null) {
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
						int fishXp = getExperience(SkillType.FISHING, fishingCatch.getItemStack().getType());
						fishingCatch.setItemStack(t.getItem());
						thread = t;

						applyXp(event.getPlayer(), t.getXp() + fishXp);
					}
				if (thread != null) remove(thread);
				return;
			default:
				return;
		}
	}

	private boolean getSecondaryAbility(Player player, ItemStack treasure, SecondaryAbility ability) {
		Class<?> permissions = null;
		try {
			permissions = Class.forName("com.gmail.nossr50.util.Permissions");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (permissions != null) try {
			Method method = permissions.getDeclaredMethod("secondaryAbilityEnabled", Permissible.class, SecondaryAbility.class);
			Object returnType = method.invoke(null, player, ability);
			return returnType instanceof Boolean && ((Boolean) returnType).booleanValue();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return false;
	}

	private int getExperience(SkillType type, Material material) {
		Class<?> expConfig = null;
		try {
			expConfig = Class.forName("com.gmail.nossr50.config.experience.ExperienceConfig");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (expConfig != null) try {
			Method method = expConfig.getDeclaredMethod("getXp", SkillType.class, Material.class);
			Object returnType = method.invoke(ExperienceConfig.getInstance(), type, material);
			if (returnType instanceof Integer) return ((Integer) returnType).intValue();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private void applyXp(Player player, int exp) {
		FishingManager manager = UserManager.getPlayer(player).getFishingManager();
		try {
			Method method = SkillManager.class.getDeclaredMethod("applyXpGain", float.class, XPGainReason.class);
			method.invoke(manager, exp, XPGainReason.PVE);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
