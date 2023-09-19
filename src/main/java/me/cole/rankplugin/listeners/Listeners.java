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
            event.setJoinMessage(ChatColor.GREEN + "(" + userStatistics.getUserGroup() + ")" + " " + p.getDisplayName() + " has connected to this server.");
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
            event.setQuitMessage(ChatColor.GREEN + "(" + userStatistics.getUserGroup() + ")" + " " + p.getDisplayName() + " has disconnected from the server.");
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
                prefix = ChatColor.WHITE + "[" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "SpicyOwner" + ChatColor.WHITE + "]" + ChatColor.DARK_PURPLE + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.DARK_PURPLE + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("developer"))
            {
                prefix = ChatColor.WHITE + "[" + ChatColor.AQUA + ChatColor.BOLD + "Dev" + ChatColor.WHITE + "]" + ChatColor.AQUA + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.AQUA + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("admin"))
            {
                prefix = ChatColor.WHITE + "[" + ChatColor.RED + ChatColor.BOLD + "Admin" + ChatColor.WHITE + "]" + ChatColor.RED + " ";
                event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + event.getMessage());
            }
            else if (userStatistics.getUserGroup().equalsIgnoreCase("mod"))
            {
                prefix = ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Mod" + ChatColor.WHITE + "]" + ChatColor.DARK_GREEN + " ";
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
