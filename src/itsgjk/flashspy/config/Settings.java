// Copyright 2017 Gabriel Keller. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package itsgjk.flashspy.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import itsgjk.flashspy.FlashSpy;

public class Settings {

	FlashSpy main;
	public boolean enablenopermissionmessage;
	public String nopermissionmessage;
	public String commandnotifiermessage;
	public  ArrayList<String> blacklistedcommands;
	public  ArrayList<String> hiddenplayers;
	public String spyon;
	public String spyoff;
	List<String> defaultBlacklist = new ArrayList<String>();
	List<String> defaulthiddenPlayers = new ArrayList<String>();
	public String spyonnotify;
	public String spyoffnotify;
	public String invalidplayer;
	public String spyonother;
	public String spyoffother;
	public boolean keeponlogout;
	
	
	
	
	public Settings(FlashSpy main){
		this.main = main;
		init();
	}
	
	public void init(){
		defaultBlacklist.add("/login");
		defaultBlacklist.add("/register");
		defaulthiddenPlayers.add("yourName");
		createConfig();
		loadConfig();
		main.log.info("FlashSpy SettingsManager loaded.");
	}
	
	public void createConfig(){

		if(main.config.get("blacklisted-commands")==null){
			main.config.set("blacklisted-commands", defaultBlacklist);
		}
		if(main.config.get("hidden-players")==null){
			main.config.set("hidden-players", defaulthiddenPlayers);
		}
		if(main.config.get("keep-on-log-out")==null){
			main.config.set("keep-on-log-out", false);
		}
		if(main.config.get("command-notifier-message")==null){
			main.config.set("command-notifier-message", "&6FS: &7%player% &6%message%");
		}
		if(main.config.get("enable-no-permission-message")==null){
			main.config.set("enable-no-permission-message", true);
		}
		if(main.config.get("no-permission-message")==null){
			main.config.set("no-permission-message", "&cYou don't have permission to use that command.");
		}
		if(main.config.get("spy-on-message")==null){
			main.config.set("spy-on-message", "&8&l[&6&lFlashSpy&8&l] &aEnabled command spy for %player%");
		}
		if(main.config.get("spy-off-message")==null){
			main.config.set("spy-off-message", "&8&l[&6&lFlashSpy&8&l] &cDisabled command spy for %player%");
		}
		if(main.config.get("spy-on-message-by-other")==null){
			main.config.set("spy-on-message-by-other", "&8&l[&6&lFlashSpy&8&l] &aCommand spy enabled by %player%");
		}
		if(main.config.get("spy-off-message-by-other")==null){
			main.config.set("spy-off-message-by-other", "&8&l[&6&lFlashSpy&8&l] &cCommand spy disabled by %player%");
		}
		
		if(main.config.get("spy-on-message-notifier")==null){
			main.config.set("spy-on-message-notifier", "&6FS: &7%player% enabled spy for %enabledplayer%");
		}
		if(main.config.get("spy-off-message-notifier")==null){
			main.config.set("spy-off-message-notifier", "&6FS: &7%player% disabled spy for %disabledplayer%");
		}
		if(main.config.get("invalid-player")==null){
			main.config.set("invalid-player", "&8&l[&6&lFlashSpy&8&l] &cThat player is currently not on the server");
		}
		main.config.saveConfig();
	}
	
	@SuppressWarnings("unchecked")
	public void loadConfig(){
		enablenopermissionmessage = main.config.getBoolean("enable-no-permission-message");
		nopermissionmessage = main.config.getString("no-permission-message");
		commandnotifiermessage = main.config.getString("command-notifier-message");
		try{
			blacklistedcommands = (ArrayList<String>) main.config.getList("blacklisted-commands");
		}
		catch(Exception e){
			main.log.info("Error while loading blacklisted commands configuration list. Using default values instead.");
			blacklistedcommands = (ArrayList<String>) defaultBlacklist;
		}
		
		spyon = main.config.getString("spy-on-message");
		spyoff = main.config.getString("spy-off-message");
		spyonnotify = main.config.getString("spy-on-message-notifier");
		spyoffnotify = main.config.getString("spy-off-message-notifier");
		invalidplayer = main.config.getString("invalid-player");
		spyonother = main.config.getString("spy-on-message-by-other");
		spyoffother = main.config.getString("spy-off-message-by-other");
		try{
			hiddenplayers = (ArrayList<String>) main.config.getList("hidden-players");
		}
		catch(Exception e){
			main.log.info("Error while loading hidden players configuration list. Using default values instead.");
			hiddenplayers = (ArrayList<String>) defaulthiddenPlayers;
		}
		keeponlogout = main.config.getBoolean("keep-on-log-out");
		
	}
	
	public boolean playerKeep(UUID uuid){
		try{
			return (main.config.getString(uuid.toString() + "keep")=="KEEP-ON-LOG-OUT") ? true : false;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public void enabledSpy(UUID uuid){
		if(keeponlogout){
			if(!playerKeep(uuid)){
				main.config.set(uuid.toString() + "keep", "KEEP-ON-LOG-OUT");
				main.config.saveConfig();
			}
		}
	}
	
	public void disabledSpy(UUID uuid){
		if(keeponlogout){
			if(playerKeep(uuid)){
				main.config.removeKey(uuid.toString() + "keep");
				main.config.saveConfig();
			}
		}
	}
	
}
