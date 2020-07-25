package org.ctp.enchantmentsolution.crashapi.resources.recipes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.ctp.enchantmentsolution.crashapi.resources.util.RecipeModificationResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A configurable advancement which later may be saved to a file, loaded into
 * the server. Visit <a href=
 * "https://github.com/skylinerw/guides/blob/master/java/advancements.md">this
 * link</a> for further information regarding advancements.
 */
public interface CrashRecipe {

	public RecipeModificationResult activate(boolean reload);
	
	public RecipeModificationResult deactivate(boolean reload);

	default RecipeModificationResult activate(boolean reload, NamespacedKey id, String json,
	JsonObject jsonObject) {
		File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), String.join(File.separator, "datapacks", "bukkit", "data", id.getNamespace(), "recipes", id.getKey()) + ".json");
		if (!file.exists()) try {
			Files.createParentDirs(file);
			Files.write(json, file, Charsets.UTF_8);
			// noinspection deprecation
			return new RecipeModificationResult(true, true, "Loaded successfully.");
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error creating recipe file: " + id, e);
			return new RecipeModificationResult(false, false, "Error creating recipe file: " + id);
		}

		try {
			boolean changed = !getCurrentJSON(file).equals(jsonObject);
			Files.write(json, file, Charsets.UTF_8);

			if (reload) {
				Bukkit.reloadData();
				return new RecipeModificationResult(Bukkit.getAdvancement(id) != null, changed, "Loaded successfully.");
			}
			return new RecipeModificationResult(true, changed, "Loaded successfully.");
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error writing recipe file: " + id, e);
			return new RecipeModificationResult(false, false, "Error writing recipe file: " + id);
		}
	}

	default JsonObject getCurrentJSON(File file) {
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

	default RecipeModificationResult deactivate(boolean reload, NamespacedKey id) {
		File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), String.join(File.separator, "datapacks", "bukkit", "data", id.getNamespace(), "recipes", id.getKey()) + ".json");
		if (file.exists()) {
			if (file.delete()) {
				if (reload) {
					Bukkit.reloadData();
					Iterator<Recipe> iter = Bukkit.recipeIterator();
					while (iter.hasNext()) {
						Recipe r = iter.next();
						if (r instanceof Keyed && ((Keyed) r).getKey().equals(id)) return new RecipeModificationResult(true, true, "Unloaded successfully.");
					}
					return new RecipeModificationResult(true, false, "Could not unload.");
				}
				return new RecipeModificationResult(true, true, "Unloaded successfully.");
			}
			return new RecipeModificationResult(true, false, "Could not delete file.");
		}
		return new RecipeModificationResult(true, false, "Unloaded successfully.");
	}

	public JsonObject toJsonObject();

	public String toJson();
}
