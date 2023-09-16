package me.cole.rankplugin.commands;

import me.cole.rankplugin.database.Database;
import me.cole.rankplugin.model.DatabaseStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class RankCommand implements CommandExecutor
{
    private final Database database;

    public RankCommand(Database database) {
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments)
    {
        if (sender instanceof Player player)
        {
            if (arguments.length == 0 || arguments.length == 1)
            {
                player.sendMessage(ChatColor.RED + "Incorrect usage: /rank <user> <set|display> [group]");
            }
            else if (arguments.length == 2 || arguments.length == 3)
            {
                Player argumentUser = Bukkit.getPlayer(arguments[0]);

                if (arguments[1].equals("display"))
                {
                    try
                    {
                        DatabaseStructure userStatistics = database.getUserStatistics(player);
                        player.sendMessage(ChatColor.YELLOW + "User Statistics stored for " + player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "userUUID = " + ChatColor.WHITE + userStatistics.getUserUUID());
                        player.sendMessage(ChatColor.GREEN + "userGroup = " + ChatColor.WHITE + userStatistics.getUserGroup());
                        player.sendMessage(ChatColor.GREEN + "perm = " + ChatColor.WHITE + userStatistics.getPerm());
                    }
                    catch (SQLException exception)
                    {
                        exception.printStackTrace();
                        System.out.println("Failed to retrieve UserStatistics during RankCommand.onCommand");
                        player.sendMessage(ChatColor.RED + "Failed to retrieve UserStatistics");
                    }
                }
                else if (arguments[1].equals("set"))
                {
                    if (arguments.length == 3)
                    {
                        String oldGroup = "";
                        String newGroup = "";

                        try
                        {
                            DatabaseStructure userStatistics = database.getUserStatistics(player);
                            oldGroup = userStatistics.getUserGroup();
                            userStatistics.setUserGroup(arguments[2]);
                            newGroup = arguments[2];
                            database.updateUserStatistics(userStatistics);
                        }
                        catch (SQLException exception)
                        {
                            exception.printStackTrace();
                            System.out.println("Failed to update UserStatistics during RankCommand.onCommand");
                            player.sendMessage(ChatColor.RED + "Failed to retrieve UserStatistics");
                        }

                        player.sendMessage(ChatColor.YELLOW + "User Group updated from " + oldGroup + " to " + newGroup + " for " + player.getDisplayName());
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "Incorrect usage: /rank <user> <set|display> [group]");
                    }

                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "Incorrect usage: /rank <user> <set|display> [group]");
            }
        }
        return true;
    }
}
