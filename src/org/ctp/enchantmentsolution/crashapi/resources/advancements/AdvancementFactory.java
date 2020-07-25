package org.ctp.enchantmentsolution.crashapi.resources.advancements;

import java.util.function.Function;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.plugin.Plugin;
import org.ctp.enchantmentsolution.crashapi.resources.shared.ItemObject;
import org.ctp.enchantmentsolution.crashapi.resources.trigger.*;
import org.ctp.enchantmentsolution.crashapi.resources.util.Validator;

import net.md_5.bungee.api.chat.TextComponent;

public class AdvancementFactory {
	private final Plugin plugin;
	private final boolean autoActivate;
	private final boolean autoReload;

	public AdvancementFactory(Plugin plugin, boolean autoActivate, boolean autoReload) {
		Validate.isTrue(!(!autoActivate && autoReload), "Auto reload doesn't't work without auto activation.");
		this.plugin = plugin;
		this.autoActivate = autoActivate;
		this.autoReload = autoReload;
	}

	public Advancement getSimple(String id, @Nullable Advancement parent, String title, String description,
	Material icon, String triggerId, Trigger trigger) {
		validate(id, title, description, icon);
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description)).addTrigger(triggerId, trigger);
		finalize(advancement, parent);
		return advancement;
	}

	public <T> Advancement getAll(String id, @Nullable Advancement parent, String title, String description,
	Material icon, T[] contents, Function<T, Trigger> triggerCreator) {
		validate(id, title, description, icon);
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description));
		for(int i = 0; i < contents.length; i++)
			advancement.addTrigger(String.valueOf(i), triggerCreator.apply(contents[i]));
		finalize(advancement, parent);
		return advancement;
	}

	public <T> Advancement getAny(String id, @Nullable Advancement parent, String title, String description,
	Material icon, T[] contents, Function<T, Trigger> triggerCreator) {
		validate(id, title, description, icon);
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description));
		String[] requirements = new String[contents.length];
		for(int i = 0; i < contents.length; i++) {
			String index = String.valueOf(i);
			requirements[i] = index;
			advancement.addTrigger(index, triggerCreator.apply(contents[i]));
		}
		advancement.addRequirement(requirements);
		finalize(advancement, parent);
		return advancement;
	}

	public Advancement getRoot(String id, String title, String description, Material icon, String background) {
		validate(id, title, description, icon);
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description)).makeRoot(background, true).addTrigger("item", new InventoryChangedTrigger().addItem(new ItemObject().setItem(Material.ENCHANTING_TABLE))).addTrigger("enchant", new EnchantedItemTrigger()).removeTrigger("auto").addRequirement("item", "enchant");
		finalize(advancement, null);
		return advancement;
	}

	public Advancement getImpossible(String id, @Nullable Advancement parent, String title, String description,
	Material icon, String... triggers) {
		validate(id, title, description, icon);
		Validate.notNull(triggers);
		Validate.isTrue(triggers.length > 0, "At least one trigger must be specified.");
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description));
		for(String trigger: triggers)
			advancement.addTrigger(trigger, new ImpossibleTrigger());
		finalize(advancement, parent);
		return advancement;
	}

	public Advancement getImpossible(String id, @Nullable Advancement parent, String title, String description,
	Material icon) {
		return getImpossible(id, parent, title, description, icon, "0");
	}

	public Advancement getCountedImpossible(String id, @Nullable Advancement parent, String title, String description,
	Material icon, int triggerCount) {
		validate(id, title, description, icon);
		Validate.isTrue(triggerCount > 0, "There must be at least one trigger.");
		Advancement advancement = new Advancement(new NamespacedKey(plugin, id), new ItemObject().setItem(icon), new TextComponent(title), new TextComponent(description));
		for(int i = 0; i < triggerCount; i++)
			advancement.addTrigger(String.valueOf(i), new ImpossibleTrigger());
		finalize(advancement, parent);
		return advancement;
	}

	private static void validate(String id, String title, String description, Material icon) {
		Validator.noNamespace(id);
		Validate.notNull(title);
		Validate.notNull(description);
		Validate.notNull(icon);
	}

	private void finalize(Advancement advancement, @Nullable Advancement parent) {
		if (parent != null) advancement.makeChild(parent.getId());
		if (autoActivate) advancement.activate(autoReload);
	}
}
