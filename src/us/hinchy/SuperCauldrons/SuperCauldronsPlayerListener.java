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

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class SuperCauldronsPlayerListener extends PlayerListener {
	
	Logger log = Logger.getLogger("Minecraft");
	public SuperCauldrons plugin;
	 
	public SuperCauldronsPlayerListener(SuperCauldrons instance) {
	    plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.CAULDRON)) {
			
			byte metadata = event.getClickedBlock().getData();
			int metavalue = (int)metadata;
			
			if (metavalue != 0) {
				metadata = (byte)3;
				event.getClickedBlock().setData(metadata);
				
				if (event.getPlayer().getItemInHand().getType() == Material.BUCKET) {
					event.getPlayer().setItemInHand(new ItemStack(Material.WATER_BUCKET,1));
				} else if (event.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE) {
					int amount = event.getPlayer().getItemInHand().getAmount();
					event.getPlayer().setItemInHand(new ItemStack(Material.POTION,1,(short)0));
					if (amount > 1) {
						int i = amount;
						while (i > 1) {
							i--;
							if (event.getPlayer().getInventory().firstEmpty() > -1) event.getPlayer().getInventory().addItem(new ItemStack(Material.POTION,1,(short)0));
							else event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), new ItemStack(Material.POTION,1,(short)0));
						}
					}
				}
				event.getPlayer().updateInventory();
			}
		}
		
	}
	
}
