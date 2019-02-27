package org.ctp.enchantmentsolution.utils.save;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.nms.ItemNameNMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class LanguageFiles {
	
	private File languageFile, englishUSFile, germanFile;
	private YamlConfig englishUS, german;
	private Language defaultLanguage;
	private YamlConfigBackup language;
	
	public LanguageFiles(File langFile, Language lang) {
		languageFile = langFile;
		defaultLanguage = lang;
		
		createDefaultFiles();
		
		save(true);
	}
	
	public void addDefault(String path, CustomEnchantment enchantment, String type) {
		switch(type) {
		case "display_name":
			englishUS.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDisplayName(Language.US)));
			german.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDisplayName(Language.GERMAN)));
			break;
		case "description":
			englishUS.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDescription(Language.US)));
			german.addDefault(path, StringUtils.encodeString(enchantment.getDefaultDescription(Language.GERMAN)));
			break;
		}
	}
	
	private void save(boolean reload) {
		if(reload) {
			language = new YamlConfigBackup(languageFile, null);
			
			language.getFromConfig();
			YamlConfig main = EnchantmentSolution.getConfigFiles().getDefaultConfig();
			if(main.getBoolean("reset_language")) {
				language.resetConfig();
				main.set("reset_language", false);
				main.saveConfig();
			}
			language.copyDefaults(getLanguageFile());
		}
		
		language.saveConfig();
	}

	public YamlConfigBackup getLanguageConfig() {
		return language;
	}

	public void setLanguageConfig(YamlConfigBackup language) {
		this.language = language;
	}
	
	public void setLanguage(File langFile, Language lang) {
		languageFile = langFile;
		createDefaultFiles();
		save(lang != defaultLanguage);
		defaultLanguage = lang;
	}
	
	private YamlConfig getLanguageFile() {
		switch(defaultLanguage) {
		case US:
			return englishUS;
		case GERMAN:
			return german;
		}
		return englishUS;
	}
	
	private void createDefaultFiles() {
		File dataFolder = EnchantmentSolution.PLUGIN.getDataFolder();
		
		try {
			File langs = new File(dataFolder + "/languages/");
			if (!langs.exists()) {
				langs.mkdirs();
			}
			englishUSFile = new File(dataFolder + "/languages/en_us.yml");
			YamlConfiguration.loadConfiguration(englishUSFile);
			germanFile = new File(dataFolder + "/languages/de_de.yml");
			YamlConfiguration.loadConfiguration(germanFile);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		defaultenglishUSFile();
		defaultGermanFile();
	}
	
	private void defaultenglishUSFile() {
		englishUS = new YamlConfigBackup(englishUSFile, new String[0]);
		
		englishUS.getFromConfig();
		
		englishUS.addDefault("anvil.name", (ChatColor.BLUE + "Anvil").replace("§", "&"));
		englishUS.addDefault("anvil.mirror", (ChatColor.WHITE + "").replace("§", "&"));
		englishUS.addDefault("anvil.rename", (ChatColor.GREEN + "Rename Items").replace("§", "&"));
		englishUS.addDefault("anvil.repair-cost", (ChatColor.GREEN + "Level Cost: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		englishUS.addDefault("anvil.repair-cost-high", (ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		englishUS.addDefault("anvil.cannot-repair", (ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "Cannot Repair This Item").replace("§", "&"));
		englishUS.addDefault("anvil.combine", (ChatColor.GREEN + "Combine Items").replace("§", "&"));
		englishUS.addDefault("anvil.cannot-combine", (ChatColor.RED + "Can't Combine Items").replace("§", "&"));
		englishUS.addDefault("anvil.cannot-rename", (ChatColor.RED + "Can't Rename Items").replace("§", "&"));
		englishUS.addDefault("anvil.message-cannot-combine", "You may not combine these items!");
		englishUS.addDefault("anvil.legacy-gui", (ChatColor.WHITE + "Open Minecraft Anvil GUI").replace("§", "&"));
		englishUS.addDefault("anvil.legacy-gui-warning", (
				Arrays.asList((ChatColor.RED + "Custom enchantments will not work in this anvil and may be lost.").replace("§", "&"), 
						(ChatColor.RED + "Other custom features will also not work.").replace("§", "&"),
						(ChatColor.RED + "This should only be used for custom recipes.").replace("§", "&"))));
		englishUS.addDefault("anvil.legacy-gui-open", (ChatColor.WHITE + "Minecraft anvil GUI will open on next anvil click.").replace("§", "&"));
		englishUS.addDefault("anvil.legacy-gui-close", (ChatColor.WHITE + "Minecraft anvil GUI closed.").replace("§", "&"));
		
		englishUS.addDefault("table.name", (ChatColor.BLUE + "Enchantment Table").replace("§", "&"));
		englishUS.addDefault("table.black-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		englishUS.addDefault("table.red-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		englishUS.addDefault("table.blue-mirror", (ChatColor.DARK_BLUE + "Add Lapis").replace("§", "&"));
		englishUS.addDefault("table.blue-mirror-lore", Arrays.asList(
				(ChatColor.BLUE + "Select lapis in your inventory to add it to this slot.").replace("§", "&")));
		englishUS.addDefault("table.instructions-title", ("Enchantment Instructions.").replace("§", "&"));
		englishUS.addDefault("table.instructions", Arrays.asList(
				"Click items to put them on the left.",
				"You will see a list of books with the level",
				" and lapis needed to enchant.", "Select a book to enchant.",
				"Select the item again to remove.",
				"You may see up to 4 items at a time."));
		englishUS.addDefault("table.generate-enchants-error", ("There was an error generating enchantments.").replace("§", "&"));
		englishUS.addDefault("table.enchant-level", ("Level %level% Enchant.").replace("§", "&"));
		englishUS.addDefault("table.enchant-level-lore", Arrays.asList(
				"Lvl Req: %levelReq%.", "Lvl Cost: %level%."));
		englishUS.addDefault("table.level-fifty-disabled", ("Level 50 enchantments disabled.").replace("§", "&"));
		englishUS.addDefault("table.level-fifty-lack", ("Requires 15 bookshelves around table.").replace("§", "&"));
		englishUS.addDefault("table.lapis-cost-okay", (ChatColor.GREEN + "Lapis Cost: %cost%").replace("§", "&"));
		englishUS.addDefault("table.lapis-cost-lack", (ChatColor.RED + "Lapis Cost: %cost%").replace("§", "&"));
		englishUS.addDefault("table.level-cost-okay", (ChatColor.GREEN + "Level Req: %levelReq%").replace("§", "&"));
		englishUS.addDefault("table.level-cost-lack", (ChatColor.RED + "Level Req: %levelReq%").replace("§", "&"));
		englishUS.addDefault("table.item-enchant-name", ("%name% Level %level% Enchants").replace("§", "&"));
		englishUS.addDefault("table.enchant-name", ("%enchant%...").replace("§", "&"));
		englishUS.addDefault("table.lack-reqs", ("You do not meet the requirements to enchant this item.").replace("§", "&"));
		englishUS.addDefault("table.lack-enchants", ("This item does not have enchantments generated.").replace("§", "&"));
		
		englishUS.addDefault("commands.no-permission", (ChatColor.RED + "You do not have permission to use this command!").replace("§", "&"));
		englishUS.addDefault("commands.invalid-level", ("%level% is not a valid level. Setting level to 1.").replace("§", "&"));
		englishUS.addDefault("commands.level-too-low", ("Cannot set a negative or 0 level. Setting level to 1.").replace("§", "&"));
		englishUS.addDefault("commands.level-too-high", ("%level% is too high of a level. Setting level to %maxLevel%.").replace("§", "&"));
		englishUS.addDefault("commands.add-enchant", ("Enchantment with name %enchant% with level %level% has been added to the item.").replace("§", "&"));
		englishUS.addDefault("commands.cannot-enchant-item", ("Enchantment does not work with this item.").replace("§", "&"));
		englishUS.addDefault("commands.too-many-enchants", ("This item has too many enchantments already.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-fail", ("You must try to enchant an item.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-not-found", ("Enchantment with name %enchant% not found.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-not-specified", ("You must specify an enchantment.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-removed", ("Enchantment with name %enchant% has been removed from the item.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-remove-from-item", ("You must specify an enchantment.").replace("§", "&"));
		englishUS.addDefault("commands.reload", ("Config files have been reloaded.").replace("§", "&"));
		englishUS.addDefault("commands.enchant-disabled", ("Cannot enchant item with a disabled enchantment.").replace("§", "&"));
		englishUS.addDefault("commands.reset-inventory", ("Closed all custom inventories.").replace("§", "&"));
		
		englishUS.addDefault("grindstone.legacy-open", (ChatColor.GREEN + "Open the Grindstone").replace("§", "&"));
		englishUS.addDefault("grindstone.name", (ChatColor.BLUE + "Grindstone").replace("§", "&"));
		englishUS.addDefault("grindstone.mirror", (ChatColor.WHITE + "").replace("§", "&"));
		englishUS.addDefault("grindstone.combine-lore", (ChatColor.WHITE + "Repair the Items and Remove Enchantments").replace("§", "&"));
		englishUS.addDefault("grindstone.combine", (ChatColor.GREEN + "Combine").replace("§", "&"));
		englishUS.addDefault("grindstone.cannot-combine", (ChatColor.RED + "Cannot Combine").replace("§", "&"));
		englishUS.addDefault("grindstone.remove-enchants", (ChatColor.GREEN + "Remove Enchantments").replace("§", "&"));
		englishUS.addDefault("grindstone.no-items", (ChatColor.WHITE + "No Items").replace("§", "&"));
		englishUS.addDefault("grindstone.no-items-lore", (ChatColor.WHITE + "Add items by selecting them from your inventory").replace("§", "&"));
		englishUS.addDefault("grindstone.anvil", (ChatColor.GREEN + "Open the Anvil").replace("§", "&"));
		englishUS.addDefault("grindstone.switch-to-anvil", (ChatColor.WHITE + "Go back to the Anvil inventory").replace("§", "&"));
		englishUS.addDefault("grindstone.message-cannot-combine", (ChatColor.RED + "Cannot combine these items.").replace("§", "&"));
		
		englishUS.addDefault("enchantment.name", (ChatColor.GOLD + "Display Name: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.description", (ChatColor.GOLD + "Description: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.max-level", (ChatColor.GOLD + "Max Level: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.weight", (ChatColor.GOLD + "Weight: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.start-level", (ChatColor.GOLD + "Start Level: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.enchantable-items", (ChatColor.GOLD + "Enchantable Items: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.anvilable-items", (ChatColor.GOLD + "Anvilable Items: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.conflicting-enchantments", (ChatColor.GOLD + "Conflicting Enchantments: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.enabled", (ChatColor.GOLD + "Enchantment Enabled: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.treasure", (ChatColor.GOLD + "Treasure Enchantment: " + ChatColor.WHITE).replace("§", "&"));
		englishUS.addDefault("enchantment.disabled-items", (ChatColor.GOLD + "Disabled Items: " + ChatColor.WHITE).replace("§", "&"));
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			String enchantmentDescription = enchant.getDefaultDescription(Language.US);
			if(enchantmentDescription == null) {
				enchantmentDescription = "No description specified";
			}
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set language defaults.");
					continue;
				}
				englishUS.addDefault("enchantment.descriptions." + plugin.getName() + "." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				englishUS.addDefault("enchantment.descriptions." + "custom_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else {
				englishUS.addDefault("enchantment.descriptions." + "default_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			}
		}
		
		for (Iterator<java.util.Map.Entry<Material, String>> it = ItemType.ALL.getUnlocalizedNames().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Material, String> e = it.next();
			englishUS.addDefault("vanilla." + e.getValue(), ItemNameNMS.returnLocalizedItemName(Language.US, e.getKey()));
		}
		
		englishUS.saveConfig();
	}

	private void defaultGermanFile() {
		german = new YamlConfigBackup(germanFile, new String[0]);
		
		german.getFromConfig();
		
		german.addDefault("anvil.name", (ChatColor.BLUE + "Amboss").replace("§", "&"));
		german.addDefault("anvil.mirror", (ChatColor.WHITE + "").replace("§", "&"));
		german.addDefault("anvil.rename", (ChatColor.GREEN + "Items benennen").replace("§", "&"));
		german.addDefault("anvil.repair-cost", (ChatColor.GREEN + "Kosten: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		german.addDefault("anvil.repair-cost-high", (ChatColor.RED + "Kosten: " + ChatColor.BLUE + "%repairCost%").replace("§", "&"));
		german.addDefault("anvil.cannot-repair", (ChatColor.RED + "Kosten: " + ChatColor.BLUE + "Kann nicht repariert werden").replace("§", "&"));
		german.addDefault("anvil.combine", (ChatColor.GREEN + "Items kombinieren").replace("§", "&"));
		german.addDefault("anvil.cannot-combine", (ChatColor.RED + "Nicht kombinierbar").replace("§", "&"));
		german.addDefault("anvil.cannot-rename", (ChatColor.RED + "Kann nicht benannt werden").replace("§", "&"));
		german.addDefault("anvil.message-cannot-combine", "Diese Items können nicht kombiniert werden!");
		german.addDefault("anvil.legacy-gui", (ChatColor.WHITE + "Öffne den Vanilla-Amboss").replace("§", "&"));
		german.addDefault("anvil.legacy-gui-warning", (
				Arrays.asList((ChatColor.RED + "Manche Verzauberungen gehen in diesem Amboss verloren!").replace("§", "&"), 
						(ChatColor.RED + "Viele Eigenschaften der Items gehen ebenfalls verloren.").replace("§", "&"),
						(ChatColor.RED + "Das sollte nur für alternative Rezepte verwendet werden.").replace("§", "&"))));
		german.addDefault("anvil.legacy-gui-open", (ChatColor.WHITE + "Vanilla-Amboss-GUI wird beim nächsten klick geöffnet.").replace("§", "&"));
		german.addDefault("anvil.legacy-gui-close", (ChatColor.WHITE + "Vanilla-Amboss-GUI wurde geschlossen.").replace("§", "&"));
		
		german.addDefault("table.name", (ChatColor.BLUE + "Verzauberungstisch").replace("§", "&"));
		german.addDefault("table.black-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		german.addDefault("table.red-mirror", (ChatColor.WHITE + "").replace("§", "&"));
		german.addDefault("table.blue-mirror", (ChatColor.DARK_BLUE + "Lapis zugeben").replace("§", "&"));
		german.addDefault("table.blue-mirror-lore", Arrays.asList(
				(ChatColor.BLUE + "Wähle Lapis in deinem Inventar um ihn diesem Slot zuzufügen.").replace("§", "&")));
		german.addDefault("table.instructions-title", ("Anleitung fürs verzaubern.").replace("§", "&"));
		german.addDefault("table.instructions", Arrays.asList(
				"Klicke auf ein Item um es links zuzufügen.",
				"Du siehst dann eine Liste mit Büchern",
				" und den Lapiskosten für eine Verzauberung.", "Wähle ein Buch zum verzaubern.",
				"Klicke das Item erneut um es zu entfernen.",
				"Du kannst 4 Items gleichzeitig sehen."));
		german.addDefault("table.generate-enchants-error", ("Beim generieren der Verzauberungen ist ein Fehler aufgetreten.").replace("§", "&"));
		german.addDefault("table.enchant-level", ("Level %level% Verzauberung.").replace("§", "&"));
		german.addDefault("table.enchant-level-lore", Arrays.asList(
				"Lvl benötigt: %levelReq%.", "Lvl Kosten: %level%."));
		german.addDefault("table.level-fifty-disabled", ("Level 50 Verzauberungen sind ausgeschaltet.").replace("§", "&"));
		german.addDefault("table.level-fifty-lack", ("Benötigt 15 Bücherregale um den Tisch herum.").replace("§", "&"));
		german.addDefault("table.lapis-cost-okay", (ChatColor.GREEN + "Lapiskosten: %cost%").replace("§", "&"));
		german.addDefault("table.lapis-cost-lack", (ChatColor.RED + "Lapiskosten: %cost%").replace("§", "&"));
		german.addDefault("table.level-cost-okay", (ChatColor.GREEN + "Levelanforderung: %levelReq%").replace("§", "&"));
		german.addDefault("table.level-cost-lack", (ChatColor.RED + "Levelanforderung: %levelReq%").replace("§", "&"));
		german.addDefault("table.item-enchant-name", ("%name% Level %level% Verzauberung").replace("§", "&"));
		german.addDefault("table.enchant-name", ("%enchant%...").replace("§", "&"));
		german.addDefault("table.lack-reqs", ("Du erfüllst nicht alle Anforderungen um dieses Item zu verzaubern.").replace("§", "&"));
		german.addDefault("table.lack-enchants", ("Dieses Item hat keine Verzauberungen generiert.").replace("§", "&"));
		
		german.addDefault("commands.no-permission", (ChatColor.RED + "Dafür hast du keine Berechtigung!").replace("§", "&"));
		german.addDefault("commands.invalid-level", ("%level% ist kein erlaubtes Level. Level 1 wurde angenommen.").replace("§", "&"));
		german.addDefault("commands.level-too-low", ("Das Level kann nicht 0 oder weniger sein. Level 1 wurde angenommen.").replace("§", "&"));
		german.addDefault("commands.level-too-high", ("%level% ist ein zu hohes Level. Level %maxLevel% wurde angenommen.").replace("§", "&"));
		german.addDefault("commands.add-enchant", ("Verzauberung %enchant% Level %level% wurde zum Item zugefügt.").replace("§", "&"));
		german.addDefault("commands.cannot-enchant-item", ("Verzauberungen wirken auf diesem Item nicht.").replace("§", "&"));
		german.addDefault("commands.too-many-enchants", ("Das Item hat bereits zu viele Verzauberungen.").replace("§", "&"));
		german.addDefault("commands.enchant-fail", ("Versuche es nochmal.").replace("§", "&"));
		german.addDefault("commands.enchant-not-found", ("Verzauberung %enchant% wurde nicht gefunden.").replace("§", "&"));
		german.addDefault("commands.enchant-not-specified", ("Du musst eine Verzauberung angeben.").replace("§", "&"));
		german.addDefault("commands.enchant-removed", ("Verzauberung %enchant% wurde vom Item entfernt.").replace("§", "&"));
		german.addDefault("commands.enchant-remove-from-item", ("Du musst eine Verzauberung angeben.").replace("§", "&"));
		german.addDefault("commands.reload", ("Konfigurationsdateien wurden neu geladen.").replace("§", "&"));
		german.addDefault("commands.enchant-disabled", ("Diese Verzauberung wurde abgeschaltet.").replace("§", "&"));
		german.addDefault("commands.reset-inventory", ("Alle Spezialinventare geschlossen.").replace("§", "&"));
		
		german.addDefault("grindstone.legacy-open", (ChatColor.GREEN + "Öffne den Schleifstein").replace("§", "&"));
		german.addDefault("grindstone.name", (ChatColor.BLUE + "Schleifstein").replace("§", "&"));
		german.addDefault("grindstone.mirror", (ChatColor.WHITE + "").replace("§", "&"));
		german.addDefault("grindstone.combine-lore", (ChatColor.WHITE + "Repariere die Items und entferne Verzauberungen").replace("§", "&"));
		german.addDefault("grindstone.combine", (ChatColor.GREEN + "Kombinieren").replace("§", "&"));
		german.addDefault("grindstone.cannot-combine", (ChatColor.RED + "Kann nicht kombinieren").replace("§", "&"));
		german.addDefault("grindstone.remove-enchants", (ChatColor.GREEN + "Entferne Verzauberungen").replace("§", "&"));
		german.addDefault("grindstone.no-items", (ChatColor.WHITE + "Keine Items").replace("§", "&"));
		german.addDefault("grindstone.no-items-lore", (ChatColor.WHITE + "Fügen Sie Artikel hinzu, indem Sie sie aus Ihrem Inventar auswählen").replace("§", "&"));
		german.addDefault("grindstone.anvil", (ChatColor.GREEN + "Öffne den Amboss").replace("§", "&"));
		german.addDefault("grindstone.switch-to-anvil", (ChatColor.WHITE + "Gehe zurück zum Amboss-Inventar").replace("§", "&"));
		german.addDefault("grindstone.message-cannot-combine", (ChatColor.RED + "Nicht kombinierbar.").replace("§", "&"));
		
		german.addDefault("enchantment.name", (ChatColor.GOLD + "Anzeigename: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.description", (ChatColor.GOLD + "Beschreibung: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.max-level", (ChatColor.GOLD + "Maximales Level: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.weight", (ChatColor.GOLD + "Bedeutung: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.start-level", (ChatColor.GOLD + "Start Level: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.enchantable-items", (ChatColor.GOLD + "Bezaubernde Items: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.anvilable-items", (ChatColor.GOLD + "Amboßfähige Items: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.conflicting-enchantments", (ChatColor.GOLD + "Widersprüchliche Verzauberungen: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.enabled", (ChatColor.GOLD + "Verzauberung aktiviert: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.treasure", (ChatColor.GOLD + "Schatzzauber: " + ChatColor.WHITE).replace("§", "&"));
		german.addDefault("enchantment.disabled-items", (ChatColor.GOLD + "Deaktivierte Items: " + ChatColor.WHITE).replace("§", "&"));
		
		for(CustomEnchantment enchant: DefaultEnchantments.getEnchantments()) {
			String enchantmentDescription = enchant.getDefaultDescription(Language.GERMAN);
			if(enchantmentDescription == null) {
				enchantmentDescription = "Keine Beschreibung angegeben";
			}
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) {
				JavaPlugin plugin = ((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin();
				if(plugin == null) {
					ChatUtils.sendToConsole(Level.WARNING, "Enchantment " + enchant.getName() + " (Display Name " + enchant.getDisplayName() + ")"
							+ " does not have a JavaPlugin set. Refusing to set language defaults.");
					continue;
				}
				german.addDefault("enchantment.descriptions." + plugin.getName() + "." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else if (enchant.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				german.addDefault("enchantment.descriptions." + "custom_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			} else {
				german.addDefault("enchantment.descriptions." + "default_enchantments." + enchant.getName(), StringUtils.encodeString(enchantmentDescription));
			}
		}
		
		for (Iterator<java.util.Map.Entry<Material, String>> it = ItemType.ALL.getUnlocalizedNames().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Material, String> e = it.next();
			german.addDefault("vanilla." + e.getValue(), ItemNameNMS.returnLocalizedItemName(Language.GERMAN, e.getKey()));
		}
		
		german.saveConfig();
	}
}
