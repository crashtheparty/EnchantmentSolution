package org.ctp.enchantmentsolution.interfaces.effects.projectile;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileLaunchEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class ChangeVelocityEffect extends ProjectileLaunchEffect {

	private String velocityChangeX, velocityChangeY, velocityChangeZ, velocityPercentageX, velocityPercentageY, velocityPercentageZ;
	private boolean changeVelocityX, changeVelocityY, changeVelocityZ, addVelocityX, addVelocityY, addVelocityZ;
	private Entity entity;

	public ChangeVelocityEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	String velocityChangeX, String velocityChangeY, String velocityChangeZ, String velocityPercentageX, String velocityPercentageY, String velocityPercentageZ,
	boolean changeVelocityX, boolean changeVelocityY, boolean changeVelocityZ, boolean addVelocityX, boolean addVelocityY, boolean addVelocityZ,
	ProjectileLaunchCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		setVelocityChangeX(velocityChangeX);
		setVelocityChangeY(velocityChangeY);
		setVelocityChangeZ(velocityChangeZ);
		setVelocityPercentageX(velocityPercentageX);
		setVelocityPercentageY(velocityPercentageY);
		setVelocityPercentageZ(velocityPercentageZ);
		setChangeVelocityX(changeVelocityX);
		setChangeVelocityY(changeVelocityY);
		setChangeVelocityZ(changeVelocityZ);
		setAddVelocityX(addVelocityX);
		setAddVelocityY(addVelocityY);
		setAddVelocityZ(addVelocityZ);
	}

	@Override
	public ChangeVelocityResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event) {
		int level = getLevel(items);

		double x = entity.getVelocity().getX();
		double y = entity.getVelocity().getY();
		double z = entity.getVelocity().getZ();

		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);

		String velocityPercentX = Chatable.get().getMessage(getVelocityPercentageX(), codes);
		String velocityChangeX = Chatable.get().getMessage(getVelocityChangeX(), codes);
		String velocityPercentY = Chatable.get().getMessage(getVelocityPercentageY(), codes);
		String velocityChangeY = Chatable.get().getMessage(getVelocityChangeY(), codes);
		String velocityPercentZ = Chatable.get().getMessage(getVelocityPercentageZ(), codes);
		String velocityChangeZ = Chatable.get().getMessage(getVelocityChangeZ(), codes);

		if (changeVelocityX() && addVelocityX()) x += MathUtils.eval(velocityPercentX) + MathUtils.eval(velocityChangeX);
		else if (changeVelocityX() && !addVelocityX()) x *= MathUtils.eval(velocityPercentX) + MathUtils.eval(velocityChangeX);

		if (changeVelocityY() && addVelocityY()) y += MathUtils.eval(velocityPercentY) + MathUtils.eval(velocityChangeY);
		else if (changeVelocityY() && !addVelocityY()) y *= MathUtils.eval(velocityPercentY) + MathUtils.eval(velocityChangeY);

		if (changeVelocityZ() && addVelocityZ()) z += MathUtils.eval(velocityPercentZ) + MathUtils.eval(velocityChangeZ);
		else if (changeVelocityZ() && !addVelocityZ()) z *= MathUtils.eval(velocityPercentZ) + MathUtils.eval(velocityChangeZ);

		return new ChangeVelocityResult(level, x, y, z);
	}

	public String getVelocityChangeX() {
		return velocityChangeX;
	}

	public void setVelocityChangeX(String velocityChangeX) {
		this.velocityChangeX = velocityChangeX;
	}

	public String getVelocityChangeY() {
		return velocityChangeY;
	}

	public void setVelocityChangeY(String velocityChangeY) {
		this.velocityChangeY = velocityChangeY;
	}

	public String getVelocityChangeZ() {
		return velocityChangeZ;
	}

	public void setVelocityChangeZ(String velocityChangeZ) {
		this.velocityChangeZ = velocityChangeZ;
	}

	public String getVelocityPercentageX() {
		return velocityPercentageX;
	}

	public void setVelocityPercentageX(String velocityPercentageX) {
		this.velocityPercentageX = velocityPercentageX;
	}

	public String getVelocityPercentageY() {
		return velocityPercentageY;
	}

	public void setVelocityPercentageY(String velocityPercentageY) {
		this.velocityPercentageY = velocityPercentageY;
	}

	public String getVelocityPercentageZ() {
		return velocityPercentageZ;
	}

	public void setVelocityPercentageZ(String velocityPercentageZ) {
		this.velocityPercentageZ = velocityPercentageZ;
	}

	public boolean changeVelocityX() {
		return changeVelocityX;
	}

	public void setChangeVelocityX(boolean changeVelocityX) {
		this.changeVelocityX = changeVelocityX;
	}

	public boolean changeVelocityY() {
		return changeVelocityY;
	}

	public void setChangeVelocityY(boolean changeVelocityY) {
		this.changeVelocityY = changeVelocityY;
	}

	public boolean changeVelocityZ() {
		return changeVelocityZ;
	}

	public void setChangeVelocityZ(boolean changeVelocityZ) {
		this.changeVelocityZ = changeVelocityZ;
	}

	public boolean addVelocityX() {
		return addVelocityX;
	}

	public void setAddVelocityX(boolean addVelocityX) {
		this.addVelocityX = addVelocityX;
	}

	public boolean addVelocityY() {
		return addVelocityY;
	}

	public void setAddVelocityY(boolean addVelocityY) {
		this.addVelocityY = addVelocityY;
	}

	public boolean addVelocityZ() {
		return addVelocityZ;
	}

	public void setAddVelocityZ(boolean addVelocityZ) {
		this.addVelocityZ = addVelocityZ;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public class ChangeVelocityResult extends EffectResult {

		private final double x, y, z;

		public ChangeVelocityResult(int level, double x, double y, double z) {
			super(level);
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}
	}

}
