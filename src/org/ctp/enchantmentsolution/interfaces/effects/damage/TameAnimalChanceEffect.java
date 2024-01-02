package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.bukkit.attribute.Attribute;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class TameAnimalChanceEffect extends EntityDamageEffect {

	private double tamePercent = 1, brutalizePercent = 1, damage = 0;
	private boolean multipleResult, useTamePercent, useTamePercentLevel, useBrutalizePercent, useBrutalizePercentLevel, useDamage, useDamageLevel;
	private String tamePercentLevel = "0", brutalizePercentLevel = "1", damageLevel = "0";

	public TameAnimalChanceEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double tamePercent, double brutalizePercent, double damage, String tamePercentLevel, String brutalizePercentLevel, String damageLevel,
	boolean multipleResult, boolean useTamePercent, boolean useTamePercentLevel, boolean useBrutalizePercent, boolean useBrutalizePercentLevel,
	boolean useDamage, boolean useDamageLevel, DamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.tamePercent = tamePercent;
		this.brutalizePercent = brutalizePercent;
		this.damage = damage;
		this.tamePercentLevel = tamePercentLevel;
		this.brutalizePercentLevel = brutalizePercentLevel;
		this.damageLevel = damageLevel;
		this.multipleResult = multipleResult;
		this.useTamePercent = useTamePercent;
		this.useTamePercentLevel = useTamePercentLevel;
		this.useBrutalizePercent = useBrutalizePercent;
		this.useBrutalizePercentLevel = useBrutalizePercentLevel;
		this.useDamage = useDamage;
		this.useDamageLevel = useDamageLevel;
	}

	@Override
	public TameAnimalChanceResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		for(ItemStack item: items)
			if (item == null) continue;
		int level = getLevel(items);

		double originalDamage = event.getDamage();
		double damage = 0;
		double maxHealth = ((LivingEntity) damaged).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		if (willUseDamage()) damage = originalDamage + getDamage();
		if (willUseDamageLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			codes.put("%original_damage%", originalDamage);
			codes.put("%damaged_max_health%", maxHealth);
			String percent = Chatable.get().getMessage(getDamageLevel(), codes);
			damage = MathUtils.eval(percent);
		}
		damage = Math.max(damage, 0);

		double tameChance = 1;
		if (willUseTamePercent()) tameChance = getTamePercent();
		if (willUseTamePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getTamePercentLevel(), codes);
			tameChance = MathUtils.eval(percent);
		}

		double brutalizeChance = 1;
		if (willUseBrutalizePercent()) brutalizeChance = getTamePercent();
		if (willUseBrutalizePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getBrutalizePercentLevel(), codes);
			brutalizeChance = MathUtils.eval(percent);
		}

		boolean tame = tameChance > Math.random();
		boolean brutalize = brutalizeChance > Math.random();
		if (tame && !multipleResult) brutalize = false;

		return new TameAnimalChanceResult(level, tame, brutalize, damage);
	}

	public double getTamePercent() {
		return tamePercent;
	}

	public void setTamePercent(double tamePercent) {
		this.tamePercent = tamePercent;
	}

	public double getBrutalizePercent() {
		return brutalizePercent;
	}

	public void setBrutalizePercent(double brutalizePercent) {
		this.brutalizePercent = brutalizePercent;
	}

	public boolean useMultipleResult() {
		return multipleResult;
	}

	public void setMultipleResult(boolean multipleResult) {
		this.multipleResult = multipleResult;
	}

	public boolean willUseTamePercent() {
		return useTamePercent;
	}

	public void setUseTamePercent(boolean useTamePercent) {
		this.useTamePercent = useTamePercent;
	}

	public boolean willUseTamePercentLevel() {
		return useTamePercentLevel;
	}

	public void setUseTamePercentLevel(boolean useTamePercentLevel) {
		this.useTamePercentLevel = useTamePercentLevel;
	}

	public boolean willUseBrutalizePercent() {
		return useBrutalizePercent;
	}

	public void setUseBrutalizePercent(boolean useBrutalizePercent) {
		this.useBrutalizePercent = useBrutalizePercent;
	}

	public boolean willUseBrutalizePercentLevel() {
		return useBrutalizePercentLevel;
	}

	public void setUseBrutalizePercentLevel(boolean useBrutalizePercentLevel) {
		this.useBrutalizePercentLevel = useBrutalizePercentLevel;
	}

	public String getTamePercentLevel() {
		return tamePercentLevel;
	}

	public void setTamePercentLevel(String tamePercentLevel) {
		this.tamePercentLevel = tamePercentLevel;
	}

	public String getBrutalizePercentLevel() {
		return brutalizePercentLevel;
	}

	public void setBrutalizePercentLevel(String brutalizePercentLevel) {
		this.brutalizePercentLevel = brutalizePercentLevel;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public String getDamageLevel() {
		return damageLevel;
	}

	public void setDamageLevel(String damageLevel) {
		this.damageLevel = damageLevel;
	}

	public boolean willUseDamage() {
		return useDamage;
	}

	public void setUseDamage(boolean useDamage) {
		this.useDamage = useDamage;
	}

	public boolean willUseDamageLevel() {
		return useDamageLevel;
	}

	public void setUseDamageLevel(boolean useDamageLevel) {
		this.useDamageLevel = useDamageLevel;
	}

	public class TameAnimalChanceResult extends EffectResult {

		private final boolean tame, brutalize;
		private final double damage;

		public TameAnimalChanceResult(int level, boolean tame, boolean brutalize, double damage) {
			super(level);
			this.tame = tame;
			this.brutalize = brutalize;
			this.damage = damage;
		}

		public double getDamage() {
			return damage;
		}

		public boolean willTame() {
			return tame;
		}

		public boolean willBrutalize() {
			return brutalize;
		}
	}

}
