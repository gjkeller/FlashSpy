// Copyright 2017 Gabriel Keller. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package itsgjk.flashspy;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import itsgjk.flashspy.util.StringUtil;

public class NotifierManagement {

	FlashSpy main;
	public ArrayList<UUID> listening = new ArrayList<UUID>();
	
	
	
	public NotifierManagement(FlashSpy main){
		this.main = main;
	}
	
	
	public void registerSpyCommand(Player initiated, Player toggled){
		if(!listening.contains(toggled.getUniqueId())){
			main.s.enabledSpy(toggled.getUniqueId());
			
			//Notifications
			//Notify other people with spy
			for(Player tempP : Bukkit.getOnlinePlayers()){
				if(listening.contains(tempP.getUniqueId())){
					if(!(tempP==initiated)||!(tempP==toggled)){
						tempP.sendMessage(StringUtil.color(main.s.spyonnotify.replace("%player%", initiated.getName()).replaceAll("%enabledplayer%", toggled.getName())));
					}
				}
			}
			listening.add(toggled.getUniqueId());
			//Duplicate Message Check
			if(initiated==toggled){
				initiated.sendMessage(StringUtil.color(main.s.spyon.replace("%player%", toggled.getName())));
			}
			else{
				initiated.sendMessage(StringUtil.color(main.s.spyon.replace("%player%", toggled.getName())));
				toggled.sendMessage(StringUtil.color(main.s.spyonother.replace("%player%", initiated.getName())));
			}
		}
		else{
			main.s.disabledSpy(toggled.getUniqueId());
			listening.remove(toggled.getUniqueId());
			//Notifications
			//Notify other people with spy
			for(Player tempP : Bukkit.getOnlinePlayers()){
				if(listening.contains(tempP.getUniqueId())){
					if(!(tempP==initiated)||!(tempP==toggled)){
						tempP.sendMessage(StringUtil.color(main.s.spyoffnotify.replace("%player%", initiated.getName()).replaceAll("%disabledplayer%", toggled.getName())));
					}
				}
			}
			//Duplicate Message Check
			if(initiated==toggled){
				initiated.sendMessage(StringUtil.color(main.s.spyoff.replace("%player%", toggled.getName())));
			}
			else{
				initiated.sendMessage(StringUtil.color(main.s.spyoff.replace("%player%", toggled.getName())));
				toggled.sendMessage(StringUtil.color(main.s.spyoffother.replace("%player%", initiated.getName())));
			}
		}
	}
	
	public void registerCommand(Player p, String cmd){
		String firstarg = cmd.split(" ")[0];
		if(main.s.blacklistedcommands.contains(firstarg.toLowerCase())) return;
		if(main.s.hiddenplayers.contains(p.getName())) return;
		if(firstarg.equalsIgnoreCase("/flashspy")||firstarg.equalsIgnoreCase("/fs")) return;
		
		
		for(Player tempP : Bukkit.getOnlinePlayers()){
			if(!(p.getName()==tempP.getName())){
				if(listening.contains(tempP.getUniqueId())){
					tempP.sendMessage(StringUtil.color(main.s.commandnotifiermessage.replace("%player%", p.getName()).replaceAll("%message%", cmd)));
				}
			}
		}
	}
	
}
