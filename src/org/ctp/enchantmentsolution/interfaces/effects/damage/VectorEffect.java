package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class VectorEffect extends EntityDamageEffect {

	private Entity entity;
	private Vector entityVector, defaultVector;
	private String x, y, z, finalX, finalY, finalFlyingY, finalZ;

	public VectorEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, Vector defaultVector,
	String x, String y, String z, String finalX, String finalY, String finalFlyingY, String finalZ, DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		setDefaultVector(defaultVector);
		setX(x);
		setY(y);
		setZ(z);
		setFinalX(finalX);
		setFinalY(finalY);
		setFinalFlyingY(finalFlyingY);
		setFinalZ(finalZ);
	}

	@Override
	public VectorResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);
		double d0 = Math.sin(entity.getLocation().getYaw() * 0.017453292F);
		double d1 = -Math.cos(entity.getLocation().getYaw() * 0.017453292F);

		Vector looking = new Vector(d0, 0, d1);
		double d2 = Math.sqrt(looking.getX() * looking.getX() + looking.getY() * looking.getY() + looking.getZ() * looking.getZ());

		if (d2 < 1.0E-4D) looking = defaultVector;
		else {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%d2%", d2);
			codes.put("%level%", level);
			codes.put("%x%", looking.getX());
			codes.put("%y%", looking.getY());
			codes.put("%z%", looking.getZ());
			String x = Chatable.get().getMessage(this.x, codes);
			String y = Chatable.get().getMessage(this.y, codes);
			String z = Chatable.get().getMessage(this.z, codes);
			looking = new Vector(MathUtils.eval(x), MathUtils.eval(y), MathUtils.eval(z));
		}
 
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%d2%", d2);
		codes.put("%level%", level);
		codes.put("%entity_x%", entityVector.getX());
		codes.put("%entity_y%", entityVector.getY());
		codes.put("%entity_z%", entityVector.getZ());
		codes.put("%x%", looking.getX());
		codes.put("%y%", looking.getY());
		codes.put("%z%", looking.getZ());

		String x = Chatable.get().getMessage(finalX, codes);
		String y = null;
		if (entity.isOnGround()) y = Chatable.get().getMessage(finalY, codes);
		else
			y = Chatable.get().getMessage(finalFlyingY, codes);
		String z = Chatable.get().getMessage(finalZ, codes);

		return new VectorResult(level, defaultVector, new Vector(MathUtils.eval(x), MathUtils.eval(y), MathUtils.eval(z)));
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Vector getEntityVector() {
		return entityVector;
	}

	public void setEntityVector(Vector entityVector) {
		this.entityVector = entityVector;
	}

	public Vector getDefaultVector() {
		return defaultVector;
	}

	public void setDefaultVector(Vector defaultVector) {
		this.defaultVector = defaultVector;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getFinalX() {
		return finalX;
	}

	public void setFinalX(String finalX) {
		this.finalX = finalX;
	}

	public String getFinalY() {
		return finalY;
	}

	public void setFinalY(String finalY) {
		this.finalY = finalY;
	}

	public String getFinalFlyingY() {
		return finalFlyingY;
	}

	public void setFinalFlyingY(String finalFlyingY) {
		this.finalFlyingY = finalFlyingY;
	}

	public String getFinalZ() {
		return finalZ;
	}

	public void setFinalZ(String finalZ) {
		this.finalZ = finalZ;
	}

	public class VectorResult extends EffectResult {

		private final Vector original, ending;

		public VectorResult(int level, Vector original, Vector ending) {
			super(level);
			this.original = original;
			this.ending = ending;
		}

		public Vector getOriginal() {
			return original;
		}

		public Vector getEnding() {
			return ending;
		}
	}
}