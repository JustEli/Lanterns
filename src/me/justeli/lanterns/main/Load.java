package me.justeli.lanterns.main;

import me.justeli.lanterns.events.Placement;
import me.justeli.lanterns.time.Time;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Eli on 12/13/2016.
 *
 */

public class Load extends JavaPlugin
{
    public static Load main;

    @Override
    public void onEnable ()
    {
        main = this;

        registerEvents();
        registerCommands();
        registerSchedules();
    }

    private void registerEvents ()
    {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new Placement(), this);
    }

    private void registerSchedules ()
    {
        new Time().runTaskTimerAsynchronously(this, 0, 100);
    }

    private void registerCommands ()
    {
        this.getCommand("lanterns").setExecutor(new Cmds());
    }

}
