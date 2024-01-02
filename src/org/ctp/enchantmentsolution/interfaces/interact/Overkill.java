package org.ctp.enchantmentsolution.interfaces.interact;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.EntityItemType;
import org.ctp.enchantmentsolution.events.interact.OverkillEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.ShootEntityEffect;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class Overkill extends ShootEntityEffect {

	public Overkill() {
		super(RegisterEnchantments.OVERKILL, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new EntityType[] { EntityType.ARROW, EntityType.SPECTRAL_ARROW }, false, true, true, true, 0.25, "1", true, false, new InteractCondition[0]);
	}

	@Override
	public ShootEntityResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		ShootEntityResult result = super.run(player, items, event);
		ItemSlot slot = result.getSlot();
		boolean infinity = isAllowInfinity() && getLevel(items, Enchantment.ARROW_INFINITE) > 0;
		boolean takeItem = !isAllowInfinite() && player.getGameMode() != GameMode.CREATIVE && !infinity;
		boolean hasItem = slot != null && slot.getItem() != null;

		if (result.getLevel() == 0 || takeItem && !hasItem) return null;
		ItemStack item = items[0];
		OverkillEvent overkill = new OverkillEvent(player, item, takeItem, hasItem, result.getMultiply());
		Bukkit.getPluginManager().callEvent(overkill);

		if (!overkill.isCancelled() && !overkill.willCancel()) {
			ItemStack i = slot.getItem();
			EntityItemType itemType = EntityItemType.fromItem(i);
			if (itemType == null) itemType = EntityItemType.fromEntityType(getEntityTypes()[0]);
			if (itemType != null && (isAllowOffhand() || slot.getType() != ItemSlotType.OFF_HAND)) {
				player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				Projectile projectile = player.launchProjectile(itemType.getClazz());
				if (itemType.getMaterial() == Material.TIPPED_ARROW) {
					PotionMeta meta = (PotionMeta) i.getItemMeta();
					((Arrow) projectile).setBasePotionData(meta.getBasePotionData());
				}
				projectile.setMetadata(getEnchantment().getKey().getKey(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
				if (!overkill.takeArrow()) projectile.setMetadata("no_pickup", new FixedMetadataValue(EnchantmentSolution.getPlugin(), true));
				DamageUtils.damageItem(player, item);
				projectile.setVelocity(projectile.getVelocity().multiply(overkill.getSpeed()));
				player.getWorld().playSound(player.getLocation(), itemType.getSound(), 1, 1);

				ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
				if (willSetCooldown()) esPlayer.setCooldown(RegisterEnchantments.OVERKILL, overkill.getCooldownTicks());
				if (overkill.takeArrow()) {
					i.setAmount(i.getAmount() - 1);
					if (i.getAmount() == 0) i.setType(Material.AIR);
					esPlayer.setItem(slot.getType(), i);
				}
				return new ShootEntityResult(result.getLevel(), overkill.getSpeed(), result.getSlot());
			}
		}
		return null;
	}

}
