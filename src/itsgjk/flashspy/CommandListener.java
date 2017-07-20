// Copyright 2017 Gabriel Keller. This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

package itsgjk.flashspy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import itsgjk.flashspy.util.StringUtil;

public class CommandListener implements Listener {

	FlashSpy main;
	
	public CommandListener(FlashSpy main){
		this.main = main;
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if(e.getMessage().toLowerCase().startsWith("/flashspy")||e.getMessage().toLowerCase().startsWith("/fs")){
			String[] args = e.getMessage().split(" ");
			if(args.length==1){
				if(p.hasPermission("flashspy.toggle")){
					e.setCancelled(true);
					main.nm.registerSpyCommand(p, p);
				}
				else if(main.s.enablenopermissionmessage){
					e.setCancelled(true);
					p.sendMessage(StringUtil.color(main.s.nopermissionmessage));
				}
			}else if(args.length==2){
				if(p.hasPermission("flashspy.toggle.others")){
					e.setCancelled(true);
					try{
						Player selectedPlayer = Bukkit.getPlayerExact(args[1]);
						if(Bukkit.getOnlinePlayers().contains(selectedPlayer)){
							main.nm.registerSpyCommand(p, selectedPlayer);
						}
						else p.sendMessage(StringUtil.color(main.s.invalidplayer));
					}catch(Exception ex){
						p.sendMessage(StringUtil.color(main.s.invalidplayer));
					}
				}
				else if(main.s.enablenopermissionmessage){
					e.setCancelled(true);
					p.sendMessage(StringUtil.color(main.s.nopermissionmessage));
				}
			}
			
		}
		
		main.nm.registerCommand(p, e.getMessage());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(main.s.playerKeep(e.getPlayer().getUniqueId())){
			main.nm.listening.add(e.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		//Prevent memory leaks
		if(main.nm.listening.contains(e.getPlayer().getUniqueId())){
			main.nm.listening.remove(e.getPlayer().getUniqueId());
		}
	}
}
