package me.justeli.lanterns.main;

import me.justeli.lanterns.settings.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmds implements CommandExecutor {

	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String l, String[] args)
	{
		if ( l.equalsIgnoreCase("lanterns") || l.equalsIgnoreCase("lantern") || l.equalsIgnoreCase("gl") ) {

            if ( !(sender instanceof Player) || !sender.hasPermission("lanterns.use"))
            {
                sender.sendMessage(ChatColor.RED + "You cannot enable lanterns! :O");
                return true;
            }

            Player p = (Player) sender;
            if (Settings.enabled.get(p.getName()) == null)
            {
                Settings.enabled.put(p.getName(), true);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&oPlacing lanterns has been enabled."));
            }
            else
            {
                Settings.enabled.remove(p.getName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&oPlacing lanterns has been disabled."));
            }

            return true;
        }
		
		return false;
    }

}
