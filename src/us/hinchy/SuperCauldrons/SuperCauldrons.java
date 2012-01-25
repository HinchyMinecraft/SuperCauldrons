/*
 *  Copyright 2011 Zach Hinchy.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package us.hinchy.SuperCauldrons;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperCauldrons extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");

	private final SuperCauldronsPlayerListener scPlayerListener = new SuperCauldronsPlayerListener(this);
	private final SuperCauldronsBlockListener scBlockListener = new SuperCauldronsBlockListener(this);
	
	@SuppressWarnings("deprecation")
	public void onEnable() { 
		PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvent(Event.Type.PLAYER_INTERACT, scPlayerListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.BLOCK_FROMTO, scBlockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.REDSTONE_CHANGE, scBlockListener, Event.Priority.Normal, this);
		
		this.getConfig();

		log.info("[SuperCauldrons] Version 1.2.0 by Zach Hinchy (http://hinchy.us/) has been enabled.");
		if (this.getConfig().isSet("supercauldrons") == false) {
			this.saveDefaultConfig();
			log.info("[SuperCauldrons] Config did not exist or was invalid, default config saved.");
		}
		
	}
	 
	public void onDisable() { 
		log.info("[SuperCauldrons] SuperCauldrons has been disabled.");
	}	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("screload")) {
			this.reloadConfig();
			sender.sendMessage("SuperCauldrons configuration reloaded.");
			return true;
		}
		return false; 
	}
	
}
