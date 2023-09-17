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
            event.setQuitMessage(ChatColor.GREEN + "(" + userStatistics.getUserGroup() + ")" + " " + p + " has connected to this server.");
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
            if (userStatistics.getUserGroup().equals("Owner"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.RED + "Owner" + ChatColor.GRAY + "]" + ChatColor.RED + " ";
            }
            else if (userStatistics.getUserGroup().equals("Developer"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "Developer" + ChatColor.GRAY + "]" + ChatColor.AQUA + " ";
            }
            else if (userStatistics.getUserGroup().equals("Admin"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "Admin" + ChatColor.GRAY + "]" + ChatColor.GOLD + " ";
            }
            else if (userStatistics.getUserGroup().equals("Mod"))
            {
                prefix = ChatColor.GRAY + "[" + ChatColor.YELLOW + "Mod" + ChatColor.GRAY + "]" + ChatColor.YELLOW + " ";
            }
            else if (userStatistics.getUserGroup().equals("User"))
            {
                prefix = ChatColor.GRAY + "";
            }

            event.setFormat(prefix + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + event.getMessage());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Could not update player stats after quit.");
        }
    }
}
