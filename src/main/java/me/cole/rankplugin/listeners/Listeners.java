package me.cole.rankplugin.listeners;

import me.cole.rankplugin.database.Database;
import me.cole.rankplugin.model.DatabaseStructure;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
}
