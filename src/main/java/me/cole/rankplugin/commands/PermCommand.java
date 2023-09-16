package me.cole.rankplugin.commands;

import me.cole.rankplugin.database.Database;
import me.cole.rankplugin.model.DatabaseStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PermCommand implements CommandExecutor
{
    private final Database database;

    public PermCommand(Database database) {
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments)
    {
        if (sender instanceof Player player)
        {
            if (arguments.length == 0 || arguments.length == 1)
            {
                player.sendMessage(ChatColor.RED + "Incorrect usage: /perm <user> <set|display> [0-9]");
            }
            else if (arguments.length == 2 || arguments.length == 3)
            {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arguments[0]);
                Player argumentUser = (Player) offlinePlayer;

                if (arguments[1].equals("display"))
                {
                    try
                    {
                        DatabaseStructure userStatistics = database.getUserStatistics(argumentUser);
                        player.sendMessage(ChatColor.YELLOW + "User Perm stored for " + argumentUser.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "userUUID = " + ChatColor.WHITE + userStatistics.getUserUUID());
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
                        Integer oldPerm = 0;
                        Integer newPerm = 0;

                        try
                        {
                            DatabaseStructure userStatistics = database.getUserStatistics(argumentUser);
                            oldPerm = userStatistics.getPerm();
                            player.sendMessage(arguments[2]);
                            userStatistics.setPerm(Integer.parseInt(arguments[2]));
                            newPerm = Integer.parseInt(arguments[2]);
                            database.updateUserStatistics(userStatistics);
                        }
                        catch (SQLException exception)
                        {
                            exception.printStackTrace();
                            System.out.println("Failed to update UserStatistics during RankCommand.onCommand");
                            player.sendMessage(ChatColor.RED + "Failed to retrieve UserStatistics");
                        }

                        player.sendMessage(ChatColor.YELLOW + "User Perm updated from " + oldPerm + " to " + newPerm + " for " + argumentUser.getDisplayName());
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
