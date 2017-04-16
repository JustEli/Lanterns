package me.justeli.lanterns.events;

import me.justeli.lanterns.api.ActionBar;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Eli on 1/7/2017.
 *
 */

public class Placement implements Listener {

    @EventHandler (ignoreCancelled = true)
    public void place (BlockPlaceEvent e)
    {
        Material type = e.getBlockPlaced().getType();
        if (type.equals(Material.GLASS) || type.equals(Material.GLOWSTONE) || type.equals(Material.SEA_LANTERN))
        {
            if ( e.getPlayer().hasPermission("lanterns.use") && Lantern.getEnabled(e.getPlayer()) )
            {
                Location l = e.getBlockPlaced().getLocation();
                Lantern.save(l);

                if ( Lantern.get(l) )
                {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&e&oYou placed a lantern at &f&o" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + "&e&o."));
                }

            }
        }

    }

    @EventHandler (ignoreCancelled = true)
    public void breaking (BlockBreakEvent e)
    {
        Material type = e.getBlock().getType();
        if (type.equals(Material.GLASS) || type.equals(Material.GLOWSTONE) || type.equals(Material.SEA_LANTERN))
        {
            Player p = e.getPlayer();
            Location l = e.getBlock().getLocation();

            if ( Lantern.get(l) )
            {
                if (p.hasPermission("lanterns.use"))
                {
                    Lantern.remove(l);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&e&oYou removed a lantern at &f&o" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + "&e&o."));
                }

                else
                {
                    new ActionBar("&4&oYou cannot break lanterns.").send(e.getPlayer());
                    e.setCancelled(true);
                }

            }

        }
    }

}
