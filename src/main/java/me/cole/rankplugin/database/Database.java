package me.cole.rankplugin.database;

import me.cole.rankplugin.model.DatabaseStructure;
import org.bukkit.entity.Player;

import java.sql.*;

public class Database
{
    private Connection connection;

    public Connection getConnection() throws SQLException
    {
        if (connection != null)
        {
            return connection;
        }

        String url = "jdbc:mysql://localhost/kitpvp";
        String username = "root";
        String password = "";

        this.connection = DriverManager.getConnection(url, username, password);
        return this.connection;


    }

    public void initialiseDatabase() throws SQLException
    {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS player_statistics(userUUID varchar(200) primary key, userGroup varchar(200), kills int, deaths int, level int, exp int, bal int)";
        statement.execute(sql);

        System.out.println("[Rank] MySQL server connection and initialisation completed successfully.");
        statement.close();
    }

    public DatabaseStructure getUserStatistics(Player player) throws SQLException
    {
        DatabaseStructure userStatistics = searchUUID(player.getUniqueId().toString());

        if (userStatistics == null)
        {
            userStatistics = new DatabaseStructure(player.getUniqueId().toString(), "User", 0, 0, 0, 0, 100);
            createUserStatistics(userStatistics);
        }

        return userStatistics;
    }

    public DatabaseStructure searchUUID(String userUUID) throws SQLException
    {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM player_statistics WHERE userUUID = ?");
        preparedStatement.setString(1, userUUID);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseStructure databaseStructure;

        if (resultSet.next())
        {
            databaseStructure = new DatabaseStructure(resultSet.getString("userUUID"),
                    resultSet.getString("userGroup"), resultSet.getInt("kills"), resultSet.getInt("deaths"),
                    resultSet.getInt("level"), resultSet.getInt("exp"), resultSet.getInt("bal"));
            preparedStatement.close();

            return databaseStructure;
        }

        preparedStatement.close();
        return null;
    }

    public void createUserStatistics(DatabaseStructure databaseStructure) throws SQLException
    {

        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO player_statistics(userUUID, userGroup, kills, deaths, level, exp, bal) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, databaseStructure.getUserUUID());
        preparedStatement.setString(2, databaseStructure.getUserGroup());
        preparedStatement.setInt(3, databaseStructure.getKills());
        preparedStatement.setInt(4, databaseStructure.getDeaths());
        preparedStatement.setInt(5, databaseStructure.getLevel());
        preparedStatement.setInt(6, databaseStructure.getExp());
        preparedStatement.setInt(7, databaseStructure.getBal());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public void updateUserStatistics(DatabaseStructure databaseStructure) throws SQLException
    {

        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE player_statistics SET userUUID = ?, userGroup = ?, kills = ?, deaths = ?, " +
                "level = ?, exp = ?, bal = ?");
        preparedStatement.setString(1, databaseStructure.getUserUUID());
        preparedStatement.setString(2, databaseStructure.getUserGroup());
        preparedStatement.setInt(3, databaseStructure.getKills());
        preparedStatement.setInt(4, databaseStructure.getDeaths());
        preparedStatement.setInt(5, databaseStructure.getLevel());
        preparedStatement.setInt(6, databaseStructure.getExp());
        preparedStatement.setInt(7, databaseStructure.getBal());

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public void deleteUserStatistics(DatabaseStructure databaseStructure) throws SQLException
    {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM player_statistics WHERE userUUID = ?");
        preparedStatement.setString(1, databaseStructure.getUserUUID());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
