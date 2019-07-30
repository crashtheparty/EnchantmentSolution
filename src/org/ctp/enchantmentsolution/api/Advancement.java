package org.ctp.enchantmentsolution.api;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.ctp.enchantmentsolution.api.shared.ItemObject;
import org.ctp.enchantmentsolution.api.trigger.LocationTrigger;
import org.ctp.enchantmentsolution.api.trigger.Trigger;
import org.ctp.enchantmentsolution.api.util.AdvancementModificationResult;
import org.ctp.enchantmentsolution.api.util.JsonBuilder;
import org.ctp.enchantmentsolution.api.util.Validator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

/**
 * A configurable advancement which later may be saved to a file, loaded into the server.
 * Visit <a href="https://github.com/skylinerw/guides/blob/master/java/advancements.md">this link</a> for further information regarding advancements.
 */
public class Advancement {
	private final NamespacedKey id;
	private final ItemObject icon;
	private final TextComponent title;
	private final TextComponent description;
	private final Map<String, Trigger> triggers = new HashMap<>();
	private @Nullable Set<String[]> requirements = null;
	private @Nullable NamespacedKey parent = null;
	private @Nullable String background = null;
	private @Nullable Rewards rewards;
	private Frame frame = Frame.TASK;
	private boolean toast = true;
	private boolean announce = true;
	private boolean hidden = false;
	
	Advancement(NamespacedKey id, ItemObject icon, TextComponent title, TextComponent description) {
		Validate.notNull(id);
		Validate.notNull(icon);
		Validate.notNull(title);
		Validate.notNull(description);
		this.id = id;
		this.icon = icon;
		this.title = title;
		this.description = description;
	}
	
	public NamespacedKey getId() {
		return id;
	}
	
	public ItemObject getIcon() {
		return icon;
	}
	
	public TextComponent getTitle() {
		return title;
	}
	
	public TextComponent getDescription() {
		return description;
	}
	
	public Set<Map.Entry<String, Trigger>> getTriggers() {
		return Collections.unmodifiableSet(triggers.entrySet());
	}
	
	public Set<String[]> getRequirements() {
		return requirements == null ? Collections.emptySet() : Collections.unmodifiableSet(requirements);
	}
	
	public @Nullable NamespacedKey getParent() {
		return parent;
	}
	
	public @Nullable String getBackground() {
		return background;
	}
	
	public @Nullable Rewards getRewards() {
		return rewards;
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public boolean isToast() {
		return toast;
	}
	
	public boolean isAnnounce() {
		return announce;
	}

	public boolean isHidden() {
		return hidden;
	}

	public Advancement addTrigger(String id, Trigger trigger) {
		Validate.notNull(id);
		Validate.notNull(trigger);
		triggers.put(id, trigger);
		return this;
	}
	
	public Advancement removeTrigger(String id) {
		Validate.notNull(id);
		triggers.remove(id);
		return this;
	}
	
	public Advancement addRequirement(String... requirement) {
		Validate.notNull(requirement);
		if (requirements == null) {
			requirements = new HashSet<>();
		}
		requirements.add(requirement);
		return this;
	}
	
	public Advancement removeRequirement(String... requirement) {
		Validate.notNull(requirement);
		if (requirements == null) {
			return this;
		}
		for (Iterator<String[]> iterator = requirements.iterator(); iterator.hasNext(); ) {
			if (Arrays.equals(iterator.next(), requirement)) {
				iterator.remove();
				break;
			}
		}
		return this;
	}
	
	public Advancement setRewards(@Nullable Rewards rewards) {
		this.rewards = rewards;
		return this;
	}
	
	public Advancement setFrame(Frame frame) {
		Validate.notNull(frame);
		this.frame = frame;
		return this;
	}
	
	public Advancement setToast(boolean toast) {
		this.toast = toast;
		return this;
	}
	
	public Advancement setAnnounce(boolean announce) {
		this.announce = announce;
		return this;
	}
	
	public Advancement setHidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}
	
	public Advancement makeRoot(String background, boolean autoUnlock) {
		Validator.texture(background);
		parent = null;
		this.background = "minecraft:textures/" + background + ".png";
		if (autoUnlock) {
			triggers.put("auto", new LocationTrigger());
			announce = false;
			toast = false;
		}
		return this;
	}
	
	public Advancement makeChild(NamespacedKey parent) {
		Validate.notNull(parent);
		this.parent = parent;
		background = null;
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public static AdvancementModificationResult activate(boolean reload, NamespacedKey id, String json, JsonObject jsonObject) {
		File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(),
				String.join(File.separator, "datapacks", "bukkit", "data", id.getNamespace(), "advancements", id.getKey()) + ".json");
		if (!file.exists()) {
			try {
				//noinspection deprecation
				return new AdvancementModificationResult(Bukkit.getUnsafe().loadAdvancement(id, json) != null, true, "Loaded successfully.");
			} catch (Exception e) {
				Bukkit.getLogger().log(Level.SEVERE, "Error creating advancement file: " + id, e);
				return new AdvancementModificationResult(false, false, "Error creating advancement file: " + id);
			}
		}
		
		try {
			boolean changed = !getCurrentJSON(file).equals(jsonObject);
			Files.write(json, file, Charsets.UTF_8);
			
			if (reload) {
				Bukkit.reloadData();
				return new AdvancementModificationResult(Bukkit.getAdvancement(id) != null, changed, "Loaded successfully.");
			}
			return new AdvancementModificationResult(true, changed, "Loaded successfully.");
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error writing advancement file: " + id, e);
			return new AdvancementModificationResult(false, false, "Error writing advancement file: " + id);
		}
	}
	
	private static JsonObject getCurrentJSON(File file) {
		JSONParser parser = new JSONParser();
		 
        try {
        	FileReader reader = new FileReader(file.getAbsolutePath());
 
            Object obj = parser.parse(reader);
        	reader.close();
 
            JsonObject gson = new JsonParser().parse(((JSONObject) obj).toJSONString()).getAsJsonObject();

            return gson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonObject();
	}
	
	public AdvancementModificationResult activate(boolean reload) {
		return activate(reload, id, toJson(), toJsonObject());
	}
	
	public static AdvancementModificationResult deactivate(boolean reload, NamespacedKey id) {
		File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(),
				String.join(File.separator, "datapacks", "bukkit", "data", id.getNamespace(), "advancements", id.getKey()) + ".json");
		if (file.exists()) {
			if(file.delete()) {
				if (reload) {
					Bukkit.reloadData();
					return new AdvancementModificationResult(Bukkit.getAdvancement(id) == null, true, "Unloaded successfully.");
				}
				return new AdvancementModificationResult(true, true, "Unloaded successfully.");
			}
			return new AdvancementModificationResult(true, false, "Could not delete file.");
		}
		return new AdvancementModificationResult(true, false, "Unloaded successfully.");
	}
	
	public JsonObject toJsonObject() {
		JsonObject json = new JsonObject();
		if (parent != null) {
			json.addProperty("parent", parent.toString());
		}
		
		Validate.notNull(icon.getItem());
		json.add("display", new JsonBuilder()
				.add("icon", icon.toJson())
				.add("title", title)
				.add("description", description)
				.add("background", background)
				.add("frame", frame.getValue())
				.addFalse("show_toast", toast)
				.addFalse("announce_to_chat", announce)
				.addTrue("hidden", hidden)
				.build());
		
		Validate.notEmpty(triggers, "All advancements must contain at least one trigger.");
		JsonObject criteria = new JsonObject();
		for (Map.Entry<String, Trigger> entry : triggers.entrySet()) {
			criteria.add(entry.getKey(), entry.getValue().toJson());
		}
		json.add("criteria", criteria);
		
		if (requirements != null && !requirements.isEmpty()) {
			for (String[] array : requirements) {
				for (String string : array) {
					Validate.isTrue(triggers.containsKey(string), "The " + string + " trigger doesn't exist in advancement: ", id);
				}
			}
			json.add("requirements", JsonBuilder.GSON.toJsonTree(requirements, new TypeToken<Set<String[]>>() {}.getType()));
		}
		
		if (rewards != null) {
			json.add("rewards", rewards.toJson());
		}
		return json;
	}
	
	public String toJson() {
		return JsonBuilder.GSON.toJson(toJsonObject());
	}
	
	@Override
	public String toString() {
		return "Advancement@" + id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, icon, title, description, triggers, requirements, parent, background, rewards, frame, toast, announce, hidden);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Advancement)) {
			return false;
		}
		
		Advancement adv = (Advancement)object;
		if (!(Objects.equals(adv.id, id) && Objects.equals(adv.icon, icon) && Objects.equals(adv.title.toLegacyText(), title.toLegacyText()) &&
				Objects.equals(adv.description.toLegacyText(), description.toLegacyText()) && Objects.equals(adv.triggers, triggers) &&
				Objects.equals(adv.parent, parent) && Objects.equals(adv.background, background) && Objects.equals(adv.rewards, rewards) &&
				Objects.equals(adv.frame, frame) && Objects.equals(adv.toast, toast) && Objects.equals(adv.announce, announce) &&
				Objects.equals(adv.hidden, hidden))) {
			return false;
		}
		
		if (requirements == null) {
			return adv.requirements == null;
		} else if (adv.requirements == null) {
			return false;
		} else if (requirements.size() != adv.requirements.size()) {
			return false;
		}
		
		Set<String[]> clone = new HashSet<>(requirements);
		for (String[] array : adv.requirements) {
			boolean removed = false;
			for (Iterator<String[]> iterator = clone.iterator(); iterator.hasNext(); ) {
				if (Arrays.equals(iterator.next(), array)) {
					iterator.remove();
					removed = true;
					break;
				}
			}
			if (!removed) {
				return false;
			}
		}
		return true;
	}
	
	public enum Frame {
		TASK,
		CHALLENGE,
		GOAL;
		
		public String getValue() {
			return name().toLowerCase();
		}
	}
}
