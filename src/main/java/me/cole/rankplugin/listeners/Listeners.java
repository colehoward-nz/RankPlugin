package me.cole.rankplugin.listeners;

import me.cole.rankplugin.database.Database;
import me.cole.rankplugin.model.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class Listeners implements Listener
{
    private final Database database;

    public Listeners(Database database) {
        this.database = database;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        try
        {
            DatabaseStructure userStatistics = database.getUserStatistics(p);
            String str = userStatistics.getUserGroup();
            event.setJoinMessage(ChatColor.GREEN + "(" + str.substring(0, 1).toUpperCase() + str.substring(1)  + ")" + " " + p.getDisplayName() + " has connected to this server.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Could not update player stats after join.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        try
        {
            DatabaseStructure userStatistics = database.getUserStatistics(p);
            String str = userStatistics.getUserGroup();
            event.setQuitMessage(ChatColor.GREEN + "(" + str.substring(0, 1).toUpperCase() + str.substring(1) + ")" + " " + p.getDisplayName() + " has disconnected from the server.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Could not update player stats after quit.");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        try
        {
            DatabaseStructure userStatistics = database.getUserStatistics(player);
            String prefix = "";
            if (userStatistics.getUserGroup().equalsIgnoreCase("owner"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "SpicyOwner" + ChatColor.GRAY + "]" + ChatColor.DARK_PURPLE + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.DARK_PURPLE + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("developer"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + ChatColor.BOLD + "Dev" + ChatColor.GRAY + "]" + ChatColor.AQUA + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.AQUA + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("admin"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "Admin" + ChatColor.GRAY + "]" + ChatColor.RED + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("mod"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Mod" + ChatColor.GRAY + "]" + ChatColor.DARK_GREEN + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("user"))
            {
                prefix = ChatColor.GRAY + "";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Could not update player stats after quit.");
        }
    }
}
