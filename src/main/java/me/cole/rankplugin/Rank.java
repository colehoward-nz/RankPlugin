package me.cole.rankplugin;

import me.cole.rankplugin.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Rank extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        try
        {
            Database db = new Database();
            db.initialiseDatabase();
        }
        catch (SQLException exception)
        {
            System.out.println("Unable to connect to MySQL server.");
            exception.printStackTrace();
        }
    }
}
