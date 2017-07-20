// Copyright 2017 Gabriel Keller. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package itsgjk.flashspy;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import itsgjk.flashspy.command.SpyCommand;
import itsgjk.flashspy.config.MyConfig;
import itsgjk.flashspy.config.MyConfigManager;
import itsgjk.flashspy.config.Settings;

public class FlashSpy extends JavaPlugin {
	
	public Logger log = Bukkit.getLogger();
	MyConfigManager manager;
	public MyConfig config;
	public Settings s;
	public SpyCommand sc;
	public CommandListener cl;
	public NotifierManagement nm;
	
	
	@Override
	public void onEnable(){
		//File Management
		manager = new MyConfigManager(this);
		config = manager.getNewConfig("config.yml", new String[] {"FlashPIN by ItsGJK","Configuration File"});
		
		//Initialization
		s = new Settings(this);
		sc = new SpyCommand(this);
		nm = new NotifierManagement(this);
		cl = new CommandListener(this);
		
		
		//Events
		PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(cl, this);
		
	}
	
}
