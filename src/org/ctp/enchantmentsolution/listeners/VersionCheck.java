package org.ctp.enchantmentsolution.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class VersionCheck implements Runnable{

	@Override
	public void run() {
		if(ConfigFiles.getDefaultConfig().getBoolean("get_latest_version")) {
	        String latestversion;
	        boolean isupdate = false;
	        try {
	            URL urlv = new URL("https://raw.githubusercontent.com/crashtheparty/EnchantmentSolution/master/Version");
	            BufferedReader in = new BufferedReader(new InputStreamReader(urlv.openStream()));
	            latestversion = in.readLine();
	            if(latestversion.equalsIgnoreCase(EnchantmentSolution.PLUGIN.getDescription().getVersion())){
	                isupdate = true;
	            }else{
	                isupdate = false;
	            }
	            in.close();
	        } catch (IOException e) {
	        	ChatUtils.sendToConsole(Level.WARNING, "Issue with finding newest version.");
	        }
	        if(isupdate){
	        	EnchantmentSolution.NEWEST_VERSION = true;
	        	ChatUtils.sendToConsole(Level.INFO, "Your version is up-to-date.");
	        }else{
	        	EnchantmentSolution.NEWEST_VERSION = false;
	        	ChatUtils.sendToConsole(Level.WARNING, "New version of EnchantmentSolution is available! Download it here: https://www.spigotmc.org/resources/enchantment-solution.59556/");
	        }
		}
	}

}
