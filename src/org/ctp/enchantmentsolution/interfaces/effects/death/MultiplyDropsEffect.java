package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.*;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.DropTypeCondition;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class MultiplyDropsEffect extends EntityKillerEffect {

	private final DropTypeCondition types;
	private double amount;
	private String level, randomMultiply, randomMultiplyLevel;
	private boolean useAmount, useLevel, useRandomMultiply, useRandomMultiplyLevel;

	public MultiplyDropsEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DropTypeCondition types, double amount, String level, String randomMultiply, String randomMultiplyLevel, boolean useAmount, boolean useLevel,
	boolean useRandomMultiply, boolean useRandomMultiplyLevel, DeathCondition[] conditions) {
		super(enchantment, type, location, priority, setConditions(conditions, types));
		this.types = types;
		setAmount(amount);
		setLevel(level);
		setRandomMultiply(randomMultiply);
		setRandomMultiplyLevel(randomMultiplyLevel);
		setUseAmount(useAmount);
		setUseLevel(useLevel);
		setUseRandomMultiply(useRandomMultiply);
		setUseRandomMultiplyLevel(useRandomMultiplyLevel);
	}

	@Override
	public MultiplyDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		Collection<ItemStack> populated = new ArrayList<ItemStack>();
		Collection<ItemStack> original = new ArrayList<ItemStack>();
		int level = getLevel(items);
		boolean modified = false;

		for(ItemStack drop: drops)
			if (types.metCondition(killer, killed, Arrays.asList(drop))) {
				modified = true;
				ItemStack extraDrops = new ItemStack(drop.getType());
				double num = drop.getAmount();
				if (willUseLevel()) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%level%", level);
					String percent = Chatable.get().getMessage(getLevel(), codes);
					num += MathUtils.eval(percent);
				}
				if (willUseAmount()) num += getAmount();
				if (willUseRandomMultiplyLevel()) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%amount%", num);
					codes.put("%level%", level);
					codes.put("%random%", Math.random());
					String percent = Chatable.get().getMessage(getRandomMultiplyLevel(), codes);
					num += MathUtils.eval(percent);
				}
				if (willUseRandomMultiply()) {
					HashMap<String, Object> codes = ChatUtils.getCodes();
					codes.put("%amount%", num);
					codes.put("%level%", level);
					codes.put("%random%", Math.random());
					String percent = Chatable.get().getMessage(getRandomMultiply(), codes);
					num += MathUtils.eval(percent);
				}
				extraDrops.setAmount((int) num);
				populated.add(extraDrops);
			} else
				original.add(drop);

		return new MultiplyDropsResult(level, populated, original, modified);
	}

	private static DeathCondition[] setConditions(DeathCondition[] conditions, DropTypeCondition condition) {
		DeathCondition[] newConditions = new DeathCondition[conditions.length + 1];
		for(int i = 0; i < conditions.length; i++)
			newConditions[i] = conditions[i];
		newConditions[conditions.length] = condition;

		return newConditions;
	}

	public DropTypeCondition getTypes() {
		return types;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRandomMultiply() {
		return randomMultiply;
	}

	public void setRandomMultiply(String randomMultiply) {
		this.randomMultiply = randomMultiply;
	}

	public String getRandomMultiplyLevel() {
		return randomMultiplyLevel;
	}

	public void setRandomMultiplyLevel(String randomMultiplyLevel) {
		this.randomMultiplyLevel = randomMultiplyLevel;
	}

	public boolean willUseAmount() {
		return useAmount;
	}

	public void setUseAmount(boolean useAmount) {
		this.useAmount = useAmount;
	}

	public boolean willUseLevel() {
		return useLevel;
	}

	public void setUseLevel(boolean useLevel) {
		this.useLevel = useLevel;
	}

	public boolean willUseRandomMultiply() {
		return useRandomMultiply;
	}

	public void setUseRandomMultiply(boolean useRandomMultiply) {
		this.useRandomMultiply = useRandomMultiply;
	}

	public boolean willUseRandomMultiplyLevel() {
		return useRandomMultiplyLevel;
	}

	public void setUseRandomMultiplyLevel(boolean useRandomMultiplyLevel) {
		this.useRandomMultiplyLevel = useRandomMultiplyLevel;
	}

	public class MultiplyDropsResult extends EffectResult {

		private final Collection<ItemStack> populated, original;
		private final boolean modified;

		public MultiplyDropsResult(int level, Collection<ItemStack> populated, Collection<ItemStack> original, boolean modified) {
			super(level);
			this.populated = populated;
			this.original = original;
			this.modified = modified;
		}

		public boolean isModified() {
			return modified;
		}

		public Collection<ItemStack> getPopulated() {
			return populated;
		}

		public Collection<ItemStack> getOriginal() {
			return original;
		}

	}

}
