package org.ctp.enchantmentsolution.interfaces.effects.interact;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ShootEntityEffect extends PlayerInteractEffect {

	private EntityType[] entityTypes;
	private double velocityPercent;
	private String velocityPercentLevel;
	private boolean allowInfinite, allowInfinity, setCooldown, allowOffhand, useVelocityPercent, useVelocityPercentLevel;

	public ShootEntityEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EntityType[] entityTypes, boolean allowInfinite, boolean allowInfinity, boolean setCooldown, boolean allowOffhand, double velocityPercent,
	String velocityPercentLevel, boolean useVelocityPercent, boolean useVelocityPercentLevel, InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new ActionIsTypeCondition(false, Action.LEFT_CLICK_AIR)));
		this.entityTypes = entityTypes;
		this.allowInfinite = allowInfinite;
		this.allowInfinity = allowInfinity;
		this.setCooldown = setCooldown;
		this.allowOffhand = allowOffhand;
		this.velocityPercent = velocityPercent;
		this.velocityPercentLevel = velocityPercentLevel;
		this.useVelocityPercent = useVelocityPercent;
		this.useVelocityPercentLevel = useVelocityPercentLevel;
	}

	@Override
	public ShootEntityResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);

		double multiply = 1;
		if (willUseVelocityPercent()) multiply = getVelocityPercent();
		if (willUseVelocityPercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getVelocityPercentLevel(), codes);
			multiply = MathUtils.eval(percent);
		}
		if (multiply == 0) multiply = 1;
		ItemSlot slot = null;
		if (entityTypes.length > 0) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
			slot = esPlayer.getProjectile(entityTypes);
		}
		return new ShootEntityResult(level, multiply, slot);
	}

	public EntityType[] getEntityTypes() {
		return entityTypes;
	}

	public void setEntityTypes(EntityType[] entityTypes) {
		this.entityTypes = entityTypes;
	}

	public double getVelocityPercent() {
		return velocityPercent;
	}

	public void setVelocityPercent(double velocityPercent) {
		this.velocityPercent = velocityPercent;
	}

	public String getVelocityPercentLevel() {
		return velocityPercentLevel;
	}

	public void setVelocityPercentLevel(String velocityPercentLevel) {
		this.velocityPercentLevel = velocityPercentLevel;
	}

	public boolean isAllowInfinite() {
		return allowInfinite;
	}

	public void setAllowInfinite(boolean allowInfinite) {
		this.allowInfinite = allowInfinite;
	}

	public boolean isAllowInfinity() {
		return allowInfinity;
	}

	public void setAllowInfinity(boolean allowInfinity) {
		this.allowInfinity = allowInfinity;
	}

	public boolean willSetCooldown() {
		return setCooldown;
	}

	public void setCooldown(boolean setCooldown) {
		this.setCooldown = setCooldown;
	}

	public boolean willUseVelocityPercent() {
		return useVelocityPercent;
	}

	public void setUseVelocityPercent(boolean useVelocityPercent) {
		this.useVelocityPercent = useVelocityPercent;
	}

	public boolean willUseVelocityPercentLevel() {
		return useVelocityPercentLevel;
	}

	public void setUseVelocityPercentLevel(boolean useVelocityPercentLevel) {
		this.useVelocityPercentLevel = useVelocityPercentLevel;
	}

	public boolean isAllowOffhand() {
		return allowOffhand;
	}

	public void setAllowOffhand(boolean allowOffhand) {
		this.allowOffhand = allowOffhand;
	}

	public class ShootEntityResult extends EffectResult {

		private final double multiply;
		private final ItemSlot slot;

		public ShootEntityResult(int level, double multiply, ItemSlot slot) {
			super(level);
			this.multiply = multiply;
			this.slot = slot;
		}

		public double getMultiply() {
			return multiply;
		}

		public ItemSlot getSlot() {
			return slot;
		}
	}

}
