package org.ctp.enchantmentsolution.interfaces.effects.movement;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.MovementCondition;
import org.ctp.enchantmentsolution.interfaces.effects.MovementEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public class ChangePlayerVelocityEffect extends MovementEffect {

	private String velocityAddX, velocityAddY, velocityAddZ, velocityMultX, velocityMultY, velocityMultZ;
	private boolean addVelocity, multVelocity;

	public ChangePlayerVelocityEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	String velocityAddX, String velocityAddY, String velocityAddZ, String velocityMultX, String velocityMultY, String velocityMultZ,
	boolean addVelocity, boolean multVelocity, MovementCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.setVelocityAddX(velocityAddX);
		this.setVelocityAddY(velocityAddY);
		this.setVelocityAddZ(velocityAddZ);
		this.setVelocityMultX(velocityMultX);
		this.setVelocityMultY(velocityMultY);
		this.setVelocityMultZ(velocityMultZ);
		this.setAddVelocity(addVelocity);
		this.setMultVelocity(multVelocity);
	}

	@Override
	public ChangePlayerVelocityResult run(Player player, ItemStack[] items, PlayerChangeCoordsEvent event) {
		int level = getLevel(items);

		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);

		double addX = MathUtils.eval(Chatable.get().getMessage(getVelocityAddX(), codes));
		double multX = MathUtils.eval(Chatable.get().getMessage(getVelocityMultX(), codes));
		double addY = MathUtils.eval(Chatable.get().getMessage(getVelocityAddY(), codes));
		double multY = MathUtils.eval(Chatable.get().getMessage(getVelocityMultY(), codes));
		double addZ = MathUtils.eval(Chatable.get().getMessage(getVelocityAddZ(), codes));
		double multZ = MathUtils.eval(Chatable.get().getMessage(getVelocityMultZ(), codes));

		return new ChangePlayerVelocityResult(level, addX, addY, addZ, multX, multY, multZ);
	}

	public String getVelocityAddX() {
		return velocityAddX;
	}

	public void setVelocityAddX(String velocityAddX) {
		this.velocityAddX = velocityAddX;
	}

	public String getVelocityAddY() {
		return velocityAddY;
	}

	public void setVelocityAddY(String velocityAddY) {
		this.velocityAddY = velocityAddY;
	}

	public String getVelocityAddZ() {
		return velocityAddZ;
	}

	public void setVelocityAddZ(String velocityAddZ) {
		this.velocityAddZ = velocityAddZ;
	}

	public String getVelocityMultX() {
		return velocityMultX;
	}

	public void setVelocityMultX(String velocityMultX) {
		this.velocityMultX = velocityMultX;
	}

	public String getVelocityMultY() {
		return velocityMultY;
	}

	public void setVelocityMultY(String velocityMultY) {
		this.velocityMultY = velocityMultY;
	}

	public String getVelocityMultZ() {
		return velocityMultZ;
	}

	public void setVelocityMultZ(String velocityMultZ) {
		this.velocityMultZ = velocityMultZ;
	}

	public boolean isAddVelocity() {
		return addVelocity;
	}

	public void setAddVelocity(boolean addVelocity) {
		this.addVelocity = addVelocity;
	}

	public boolean isMultVelocity() {
		return multVelocity;
	}

	public void setMultVelocity(boolean multVelocity) {
		this.multVelocity = multVelocity;
	}

	public class ChangePlayerVelocityResult extends EffectResult {

		private final double addX, addY, addZ, multX, multY, multZ;

		public ChangePlayerVelocityResult(int level, double addX, double addY, double addZ, double multX, double multY, double multZ) {
			super(level);
			this.addX = addX;
			this.addY = addY;
			this.addZ = addZ;
			this.multX = multX;
			this.multY = multY;
			this.multZ = multZ;
		}

		public double getAddX() {
			return addX;
		}

		public double getAddY() {
			return addY;
		}

		public double getAddZ() {
			return addZ;
		}

		public double getMultX() {
			return multX;
		}

		public double getMultY() {
			return multY;
		}

		public double getMultZ() {
			return multZ;
		}
	}

}
