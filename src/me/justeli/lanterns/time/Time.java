package me.justeli.lanterns.time;

import me.justeli.lanterns.main.Load;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

/**
 * Created by Eli on 1/7/2017.
 *
 */

public class Time extends BukkitRunnable {

    @Override
    public void run() {
        File storage = new File( Load.main.getDataFolder() + File.separator + "lanterns.yml" );
        FileConfiguration data = YamlConfiguration.loadConfiguration( storage );

        List<String> list = data.getStringList("lanterns");

        new BukkitRunnable() {
            int i = 0;
            public void run()
            {
                if (list.size() != 0)
                {
                    String value = list.get(i);
                    String[] values = value.split("::");

                    World world = Bukkit.getWorld(values[0]);
                    int x = Integer.parseInt(values[1]);
                    int y = Integer.parseInt(values[2]);
                    int z = Integer.parseInt(values[3]);

                    Block block = world.getBlockAt(x, y, z);
                    long time = world.getTime();

                    if ( daytime(time) ) block.setType(Material.GLASS);
                    else block.setType(Material.GLOWSTONE);

                    if (i >= list.size() - 1)
                        this.cancel();

                    i ++;
                }
            }
        }.runTaskTimer(Load.main, 1, 1);
    }

    public static boolean daytime (long time)
    {
        return !(time < 23000 && time > 13000);
    }

}
