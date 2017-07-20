// Copyright 2017 Gabriel Keller. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package itsgjk.flashspy.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import itsgjk.flashspy.FlashSpy;

public class SpyCommand implements Listener {

	public FlashSpy main;
	
	
	public SpyCommand(FlashSpy main){
		this.main = main;
	}
	
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		
		if(e.getMessage().equalsIgnoreCase("flashspy")){
			if(p.hasPermission("flashspy.toggle")){
				
			}
		}
				

	}
	
}
