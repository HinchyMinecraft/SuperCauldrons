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

@SuppressWarnings("deprecation")
public class SuperCauldronsPlayerListener extends PlayerListener {
	
	Logger log = Logger.getLogger("Minecraft");
	public SuperCauldrons plugin;
	 
	public SuperCauldronsPlayerListener(SuperCauldrons instance) {
	    plugin = instance;
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		if (event.getClickedBlock().getType() == Material.CAULDRON) {

			int metadata = (int)event.getClickedBlock().getData();

			if (event.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET) {
				event.getPlayer().setItemInHand(new ItemStack(Material.BUCKET,1));
				event.getClickedBlock().setData((byte)3);
			} else {
				if (metadata != 0) {
					if (event.getPlayer().getItemInHand().getType() == Material.BUCKET) {
						if ((plugin.getConfig().getBoolean("supercauldrons.infinite") == true) || (metadata > 2)) {
							event.getPlayer().setItemInHand(new ItemStack(Material.WATER_BUCKET,1));
						}
						if ((plugin.getConfig().getBoolean("supercauldrons.infinite") == false) && (metadata > 2)) {
							event.getClickedBlock().setData((byte)0);
							event.setCancelled(true);
						}
					} else if (event.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE) {
						event.setCancelled(true);
						if (event.getPlayer().getInventory().firstEmpty() > -1) {
							if (event.getPlayer().getItemInHand().getAmount() > 1) event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
							event.getPlayer().getInventory().addItem(new ItemStack(Material.POTION,1,(byte)0));
							if (plugin.getConfig().getBoolean("supercauldrons.infinite") == false) event.getClickedBlock().setData((byte)(metadata-1));
						}
					}
					event.getPlayer().updateInventory();
	
					if (plugin.getConfig().getBoolean("supercauldrons.infinite") == true) {
						event.getClickedBlock().setData((byte)3);
					}
				}
			}

			// Don't do stuff with cauldron metadata for bottle operations in non-infinite mode;
			// the game handles those perfectly well on its own.
			if (((event.getPlayer().getItemInHand().getType() != Material.POTION)
			&& (event.getPlayer().getItemInHand().getType() != Material.GLASS_BOTTLE))
			|| (plugin.getConfig().getBoolean("supercauldrons.infinite") == true)) {
				new SuperCauldronUpdate(plugin,event.getPlayer(),event.getClickedBlock());
			}			
		}
	}
	
}
