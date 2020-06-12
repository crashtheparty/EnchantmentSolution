package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.config.EnchantmentDetails;
import org.ctp.enchantmentsolution.enchantments.config.EnchantmentDetails.DetailsType;
import org.ctp.enchantmentsolution.enums.vanilla.MobData;
import org.ctp.enchantmentsolution.events.damage.ConfigDamageEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.CustomEnchantmentUtils;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ConfigEnchantsListener extends Enchantmentable {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		List<EnchantmentDetails> details = new ArrayList<EnchantmentDetails>();
		Entity damager = event.getDamager();
		Entity damaged = event.getEntity();
		ChatUtils.sendInfo("Is in entity damage by entity event");
		if (damager instanceof HumanEntity && damaged instanceof LivingEntity) {
			ChatUtils.sendInfo("Damaged is living and damager is human");
			HumanEntity human = (HumanEntity) damager;
			LivingEntity living = (LivingEntity) damaged;
			boolean player = human instanceof Player;
			for (EnchantmentDetails d : CustomEnchantmentUtils.getEnchantmentDetails()) {
				ChatUtils.sendInfo("Checking all registered details");
				ChatUtils.sendInfo("Details Type: " + d.getDetailsType().name() + " Player: " + player + " Players Only: " + d.isPlayersOnly());
				if (d.getDetailsType() == DetailsType.DAMAGE && (!(player ^ d.isPlayersOnly()) || player)) {
					details.add(d);
					ChatUtils.sendInfo("Adding " + d.getEnchantment().getName() + " to the list of config enchantments to look for.");
				}
			}
			for (EnchantmentDetails d : details){
				ChatUtils.sendInfo("Checking " + d.getEnchantment().getName() + "...");
				List<EntityType> include = new ArrayList<EntityType>();
				List<EntityType> exclude = new ArrayList<EntityType>();
				boolean all = false;
				for (String s : d.getMobs())
					if (s.equalsIgnoreCase("all")) all = true;
					else if (s.startsWith("-")) {
						MobData data = new MobData(s.substring(1));
						if (data.hasEntity()) exclude.add(data.getEntity());
					} else {
						MobData data = new MobData(s);
						if (data.hasEntity()) include.add(data.getEntity());
					}
				boolean containsMob = (all || include.contains(living.getType())) && !(exclude.contains(living.getType()));
				ItemStack item = human.getInventory().getItemInMainHand();
				Enchantment ench = d.getEnchantment().getRelativeEnchantment();
				boolean containsEnchantment = item != null && ItemUtils.hasEnchantment(item, ench);
				ChatUtils.sendInfo("Contains Mob: " + containsMob + " Contains Enchantment: " + containsEnchantment);
				if (containsMob && containsEnchantment) {
					ChatUtils.sendInfo("Send out the event!");
					double originalDamage = event.getDamage();
					HashMap<String, Object> codes = ChatUtils.getCodes();
					int level = ItemUtils.getLevel(item, ench);
					codes.put("%level%", level);
					String eval = ChatUtils.getMessage(d.getEval(), codes);
					ChatUtils.sendInfo("First Eval: " + d.getEval() + " Level: " + level + " Eval: " + eval + " Math Eval: " + MathUtils.eval(eval));
					double finalDamage = originalDamage;
					if (d.isFlatScale()) finalDamage -= MathUtils.eval(eval);
					else
						finalDamage *= MathUtils.eval(eval);
					if (finalDamage < 0) finalDamage = 0;
					ChatUtils.sendInfo("Final Damage: " + finalDamage + " Original Damage: " + originalDamage);
					ConfigDamageEvent configDamage = new ConfigDamageEvent(living, d.getEnchantment(), level, human, originalDamage, finalDamage);
					
					Bukkit.getPluginManager().callEvent(configDamage);
					if (!configDamage.isCancelled()) event.setDamage(configDamage.getNewDamage());
				}
			}
		}
	}
}
