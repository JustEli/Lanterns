package me.justeli.lanterns.events;

import me.justeli.lanterns.main.Load;
import me.justeli.lanterns.settings.Settings;
import me.justeli.lanterns.time.Time;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Eli on 1/7/2017.
 *
 */

class Lantern {

    static boolean get (Location l)
    {
        File storage = new File( Load.main.getDataFolder() + File.separator + "lanterns.yml" );
        FileConfiguration data = YamlConfiguration.loadConfiguration( storage );

        List<String> list = data.getStringList("lanterns");
        for (String location : list)
        {
            String[] values = location.split("::");

            String world = values[0];
            int x = Integer.parseInt(values[1]);
            int y = Integer.parseInt(values[2]);
            int z = Integer.parseInt(values[3]);

            if (l.equals(new Location(Bukkit.getWorld(world), x, y, z)))
                return true;
        }

        return false;
    }

    static void save (Location l)
    {
        File storage = new File( Load.main.getDataFolder() + File.separator + "lanterns.yml" );
        FileConfiguration data = YamlConfiguration.loadConfiguration( storage );

        World world = l.getWorld();
        List<String> list = data.getStringList("lanterns");
        String saving = world.getName() + "::" + l.getBlockX() + "::" + l.getBlockY() + "::" + l.getBlockZ();

        if ( Time.daytime(world.getTime()) )
            l.getBlock().setType(Material.GLASS);
        else
            l.getBlock().setType(Material.GLOWSTONE);

        list.add( saving );
        data.set("lanterns", list);

        try { data.save( storage ); }
        catch (IOException e) { e.printStackTrace(); }
    }

    static void remove (Location l)
    {
        File storage = new File( Load.main.getDataFolder() + File.separator + "lanterns.yml" );
        FileConfiguration data = YamlConfiguration.loadConfiguration( storage );

        List<String> list = data.getStringList("lanterns");

        for (String value : list)
        {
            String[] values = value.split("::");

            String world = values[0];
            int x = Integer.parseInt(values[1]);
            int y = Integer.parseInt(values[2]);
            int z = Integer.parseInt(values[3]);

            if (l.equals(new Location(Bukkit.getWorld(world), x, y, z)))
            {
                list.remove(value);
                data.set("lanterns", list);

                try { data.save( storage ); }
                catch (IOException e) { e.printStackTrace(); }

                break;
            }

        }
    }

    static boolean getEnabled (Player p)
    {
        return Settings.enabled.get(p.getName()) != null;
    }

}
