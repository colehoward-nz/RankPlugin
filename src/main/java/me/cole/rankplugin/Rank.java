package me.cole.rankplugin;

import me.cole.rankplugin.commands.RankCommand;
import me.cole.rankplugin.database.Database;
import me.cole.rankplugin.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Rank extends JavaPlugin
{
    private Database database;

    @Override
    public void onEnable()
    {
        try
        {
            this.database = new Database();
            database.initialiseDatabase();
        }
        catch (SQLException exception)
        {
            System.out.println("Unable to connect to MySQL server.");
            exception.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new Listeners(database), this);
        getCommand("rank").setExecutor(new RankCommand(database));
    }

    public Database getDatabase()
    {

        return this.database;
    }
}
