package org.ctp.enchantmentsolution.utils.potion;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.DamageNMS;
import org.ctp.crashapi.utils.ServerUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.damage.DrownDamageEvent;
import org.ctp.enchantmentsolution.events.potion.CustomPotionAfflictEvent;
import org.ctp.enchantmentsolution.events.potion.DrownedEvent;
import org.ctp.enchantmentsolution.events.potion.SandVeilEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESEntity;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class CustomPotionEffect {

	private CustomPotionEffectType type;
	private int amplifier, duration;
	private final EnchantmentLevel level;
	private final long tick;
	private final int ticksPerDamage;
	private final LivingEntity afflicter;

	public CustomPotionEffect(LivingEntity afflicter, CustomPotionEffectType type, int amplifier, int duration, EnchantmentLevel level) {
		this(afflicter, type, amplifier, duration, level, 20);
	}

	public CustomPotionEffect(LivingEntity afflicter, CustomPotionEffectType type, int amplifier, int duration, EnchantmentLevel level, int ticksPerDamage) {
		this.afflicter = afflicter;
		setType(type);
		setAmplifier(amplifier);
		setDuration(duration);
		this.level = level;
		this.ticksPerDamage = ticksPerDamage;
		tick = ServerUtils.getCurrentTick();
	}

	public CustomPotionEffectType getType() {
		return type;
	}

	public void setType(CustomPotionEffectType type) {
		this.type = type;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void run() {
		duration--;
	}

	public void runDamage(LivingEntity entity) {
		long currentTick = ServerUtils.getCurrentTick();
		int tickMultiple = (int) (currentTick - tick);
		if (entity == null || entity.isDead()) return;
		else if (entity instanceof Player && !((Player) entity).isOnline()) return;
		switch (type) {
			case DROWNED:
				entity.setRemainingAir(0);
				if (tickMultiple % ticksPerDamage == 0) {
					int level = 0;
					if (entity instanceof HumanEntity) {
						HumanEntity hEntity = (HumanEntity) entity;
						ItemStack helmet = hEntity.getInventory().getHelmet();

						if (helmet != null) level = EnchantmentUtils.getLevel(helmet, Enchantment.OXYGEN);
					}
					double chance = level / ((double) level + 1);
					double random = Math.random();
					DrownDamageEvent event = new DrownDamageEvent(entity, this.level.getLevel(), 2, chance <= random ? 2 : 0);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled() && event.getNewDamage() > 0) {
						DamageNMS.damageEntity(entity, "drown", (float) event.getNewDamage());
						if (entity.isDead() && entity instanceof Player && afflicter instanceof Player) AdvancementUtils.awardCriteria((Player) afflicter, ESAdvancement.HEX_BAG, "player");
					}
				}
				break;
			case FLIGHT:
				if (entity instanceof OfflinePlayer) {
					int level = getAmplifier() + 1;
					ESPlayer player = EnchantmentSolution.getESPlayer((OfflinePlayer) entity);
					double flight = 0.016 * (level);
					player.setFlightSpeed(flight);
					player.setCanFly(level);
				}
				break;
			case SANDY_EYES:
				break;
			case WARPING:
				break;
		}
	}
	
	public boolean applyPotionEffect(LivingEntity entity) {
		return applyPotionEffect(entity, true);
	}

	public boolean applyPotionEffect(LivingEntity entity, boolean override) {
		CustomPotionAfflictEvent event = null;
		switch (type) {
			case DROWNED:
				event = new DrownedEvent(entity, afflicter, level.getLevel(), type, duration, override);
				break;
			case FLIGHT:
				break;
			case SANDY_EYES:
				event = new SandVeilEvent(entity, afflicter, level.getLevel(), type, amplifier, duration, override);
				break;
			case WARPING:
				break;
		}
		if (event != null) {
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
				if (esEntity != null) {
					esEntity.addCustomPotionEffect(this, event.isOverride());
					return true;
				}
			}
		} else {
			ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
			if (esEntity != null) {
				esEntity.addCustomPotionEffect(this, override);
				return true;
			}
		}
		return false;
	}

	public boolean removePotionEffect(LivingEntity entity) {
		switch (type) {
			case DROWNED:
				break;
			case FLIGHT:
				if (entity instanceof OfflinePlayer) {
					ESPlayer player = EnchantmentSolution.getESPlayer((OfflinePlayer) entity);
					player.setCanFly(0);
					player.resetFlightSpeed();
					return true;
				}
				break;
			case SANDY_EYES:
				break;
			case WARPING:
				break;
		}
		return false;
	}

	public void addDuration(CustomPotionEffect effect) {
		duration += effect.getDuration();
	}

}
