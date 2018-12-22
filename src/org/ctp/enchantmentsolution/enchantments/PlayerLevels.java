package org.ctp.enchantmentsolution.enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class PlayerLevels {
	
	private int books;
	private Player player;
	private Material material;
	public static HashMap<PlayerLevels, List<Integer>> PLAYER_LEVELS = new HashMap<PlayerLevels, List<Integer>>();
	private List<List<EnchantmentLevel>> enchants = new ArrayList<List<EnchantmentLevel>>();
	
	public PlayerLevels(int books, Player player, Material material){
		this.books = books;
		this.player = player;
		this.material = material;
		
		List<Integer> levelList = getIntList(player, books);
		
		if(levelList == null){
			if(ConfigFiles.useLevel50()) {
				levelList = generateFiftyLevels();
			} else {
				levelList = generateThirtyLevels();
			}
		}
		
		generateEnchants(levelList, false);
		
		PLAYER_LEVELS.put(this, levelList);
	}
	
	private PlayerLevels(int books, Material material) {
		this.books = books;
		this.player = null;
		this.material = material;
		
		List<Integer> levelList = getIntList(player, books);
		
		if(levelList == null){
			if(ConfigFiles.useLevel50()) {
				levelList = generateFiftyLevels();
			} else {
				levelList = generateThirtyLevels();
			}
		}
		
		generateEnchants(levelList, true);
	}
	
	
	public int getBooks() {
		return books;
	}
	public Player getPlayer() {
		return player;
	}
	public Material getMaterial() {
		return material;
	}
	
	public boolean compare(int books, Player player, Material material){
		if(this.books == books && player.getUniqueId().toString().equals(this.player.getUniqueId().toString()) && material.equals(this.material)){
			return true;
		}
		return false;
	}
	
	private List<Integer> generateThirtyLevels(){
		List<Integer> levelArray = new ArrayList<Integer>();
		
		int bookThirty = books > 15 ? 15 : books;
		
		for(int i = 1; i <= 3; i++){
			int x = (int) (Math.random() * 8 + 1);
			int b = (int) (Math.random() * bookThirty);
			int floor = bookThirty / 2;
			int base = x + b + floor;
			switch(i){
			case 1:
				levelArray.add(Math.max(base / 3, 1));
				break;
			case 2:
				levelArray.add((base * 2) / 3 + 1);
				break;
			case 3:
				levelArray.add(Math.max(base, bookThirty * 2));
				break;
			}
		}
		
		return levelArray;
	}
	
	private List<Integer> generateFiftyLevels(){
		List<Integer> levelArray = new ArrayList<Integer>();
		
		int bookThirty = books > 15 ? 15 : books;
		int bookFifty = books - 8;
		int addFifty = bookFifty - 7;
		
		for(int i = 1; i <= 6; i++){
			int x = (int) (Math.random() * 8 + 1);
			int b = (int) (Math.random() * bookThirty);
			int floor = bookThirty / 2;
			if(i >= 4){
				b = (int) (Math.random() * bookFifty);
				floor = bookFifty / 2;
			}
			int base = x + b + floor;
			switch(i){
			case 1:
				levelArray.add(Math.max(base / 3, 1));
				break;
			case 2:
				levelArray.add((base * 2) / 3 + 1);
				break;
			case 3:
				levelArray.add(Math.max(base, bookThirty * 2));
				break;
			case 4:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(Math.max(base / 3, 1) + (int)(addFifty * .75) + 20);
				}
				break;
			case 5:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(((base * 2) / 3 + 1 + (int)(addFifty * .75)) + 20);
				}
				break;
			case 6:
				if(bookThirty != 15){
					levelArray.add(-1);
				}else{
					levelArray.add(Math.max(base, bookFifty * 2) + 20);
				}
				break;
			}
		}
		
		return levelArray;
	}


	public List<List<EnchantmentLevel>> getEnchants() {
		return enchants;
	}


	public void generateEnchants(List<Integer> intList, boolean treasure) {
		if(enchants.size() > 0) return;
		for(int i = 0; i < intList.size(); i++){
			if(intList.get(i) == -1){
				enchants.add(new ArrayList<EnchantmentLevel>());
			}else{
				enchants.add(Enchantments.generateEnchantments(player, getMaterial(), intList.get(i), i + 1, treasure));
			}
		}
	}
	
	public static List<Integer> getIntList(Player player, int books){
		if(player == null) return null;
		List<Integer> levelList = null;
		
		for (Iterator<java.util.Map.Entry<PlayerLevels, List<Integer>>> it = PLAYER_LEVELS.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<PlayerLevels, List<Integer>> e = it.next();
			PlayerLevels levels = e.getKey();
			List<Integer> intList = e.getValue();
			if(levels.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString()) && levels.getBooks() == books){
				levelList = intList;
				break;
			}
		}
		
		return levelList;
	}
	
	public static PlayerLevels getPlayerLevels(int books, Player player, Material material){
		for (Iterator<java.util.Map.Entry<PlayerLevels, List<Integer>>> it = PLAYER_LEVELS.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<PlayerLevels, List<Integer>> e = it.next();
			PlayerLevels levels = e.getKey();
			if(levels.compare(books, player, material)){
				return levels;
			}
		}
		return null;
	}
	
	public static void resetPlayerLevels() {
		PLAYER_LEVELS = new HashMap<PlayerLevels, List<Integer>>();
	}
	
	public static void removePlayerLevels(Player player){
		HashMap<PlayerLevels, List<Integer>> playerLevels = new HashMap<PlayerLevels, List<Integer>>();
		for (Iterator<java.util.Map.Entry<PlayerLevels, List<Integer>>> it = PLAYER_LEVELS.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<PlayerLevels, List<Integer>> e = it.next();
			PlayerLevels levels = e.getKey();
			if(!levels.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())){
				playerLevels.put(levels, e.getValue());
			}
		}
		PLAYER_LEVELS = playerLevels;
	}
	
	public static PlayerLevels generateFakePlayerLevels(Material material) {
		int random = (int)(Math.random() * 23) + 1;
		PlayerLevels levels = new PlayerLevels(random, material);
		
		return levels;
	}

}
