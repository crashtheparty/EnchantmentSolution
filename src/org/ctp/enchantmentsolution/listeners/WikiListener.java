package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class WikiListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		if (ConfigString.WIKI_ON_LOGIN.getBoolean()) ChatUtils.sendMessage(event.getPlayer(), ChatUtils.getMessage(ChatUtils.getCodes(), "wiki.message"), ConfigString.WIKI_URL.getString());
	}

}
