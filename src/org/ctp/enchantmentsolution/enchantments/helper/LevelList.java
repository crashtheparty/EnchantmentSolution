package org.ctp.enchantmentsolution.enchantments.helper;

import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class LevelList {

	private Level[] list = new Level[6];
	private int bookshelves;

	public LevelList(int bookshelves) {
		this.bookshelves = bookshelves;
		int bookThirty = bookshelves > 15 ? 15 : bookshelves;
		int bookFifty = bookshelves - 8;
		int addFifty = bookFifty - 7;

		boolean isLevelFifty = ConfigString.LEVEL_FIFTY.getBoolean();

		for(int i = 1; i <= 6; i++) {
			int x = (int) (Math.random() * 8 + 1);
			int b = (int) (Math.random() * bookThirty);
			int floor = bookThirty / 2;
			if (i >= 4) {
				b = (int) (Math.random() * bookFifty);
				floor = bookFifty / 2;
			}
			int base = x + b + floor;
			switch (i) {
				case 1:
					list[0] = new Level(0, Math.max(base / 3, 1));
					break;
				case 2:
					list[1] = new Level(1, base * 2 / 3 + 1);
					break;
				case 3:
					list[2] = new Level(2, Math.max(base, bookThirty * 2));
					break;
				case 4:
					if (isLevelFifty && bookThirty == 15) list[3] = new Level(3, base * 2 / 3 + 1 + (int) (addFifty * .75) + 20);
					else
						list[3] = new Level(3, -1);
					break;
				case 5:
					if (isLevelFifty && bookThirty == 15) list[4] = new Level(4, base * 2 / 3 + 1 + (int) (addFifty * .75) + 20);
					else
						list[4] = new Level(4, -1);
					break;
				case 6:
					if (isLevelFifty && bookThirty == 15) list[5] = new Level(5, Math.max(base, bookFifty * 2) + 20);
					else
						list[5] = new Level(5, -1);
					break;
			}
			if (!isLevelFifty && i >= 4) list[i - 1] = new Level(i - 1, -1);
		}
	}

	public Level[] getList() {
		return list;
	}

	public int getBookshelves() {
		return bookshelves;
	}

	public static LevelList fromConfig(YamlConfig config, int i, int bookshelves) {
		LevelList l = new LevelList(bookshelves);

		for(int k = 0; k < 6; k++)
			l.getList()[k].setLevel(config.getInt("enchanting_table." + i + ".levelList." + k + ".level"));

		return l;
	}

	public void setConfig(YamlConfig config, int i) {
		for(Level level: getList())
			config.set("enchanting_table." + i + ".levelList." + level.getSlot() + ".level", level.getLevel());
	}
}
