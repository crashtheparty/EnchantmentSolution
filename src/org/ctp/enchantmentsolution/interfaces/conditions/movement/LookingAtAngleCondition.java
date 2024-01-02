package org.ctp.enchantmentsolution.interfaces.conditions.movement;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.MovementCondition;

public class LookingAtAngleCondition implements MovementCondition {

	private boolean above, equal;
	private float angle;

	public LookingAtAngleCondition(float angle, boolean above, boolean equal) {
		this.setAngle(angle);
		this.setAbove(above);
		this.setEqual(equal);
	}

	@Override
	public boolean metCondition(Player player, PlayerChangeCoordsEvent event) {
		float playerAngle = player.getLocation().getPitch();
		return above && playerAngle < angle || equal && playerAngle == angle || !above && angle < playerAngle;
	}

	public boolean isAbove() {
		return above;
	}

	public void setAbove(boolean above) {
		this.above = above;
	}

	public boolean isEqual() {
		return equal;
	}

	public void setEqual(boolean equal) {
		this.equal = equal;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

}
