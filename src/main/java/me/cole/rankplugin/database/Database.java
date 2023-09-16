package me.cole.rankplugin.database;

import me.cole.rankplugin.model.DatabaseStructure;

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

        String url = "jdbc:mysql://localhost/rank_plugin";
        String username = "root";
        String password = "";

        this.connection = DriverManager.getConnection(url, username, password);
        return this.connection;


    }

    public void initialiseDatabase() throws SQLException
    {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS player_statistics(userUUID varchar(200) primary key, userGroup varchar(200), perm int)";
        statement.execute(sql);

        System.out.println("MySQL server connection and initialisation completed successfully.");
        statement.close();
    }

    public DatabaseStructure searchUUID(String userUUID) throws SQLException
    {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM rank_plugin WHERE userUUID = ?");
        preparedStatement.setString(1, userUUID);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseStructure databaseStructure;

        if (resultSet.next())
        {
            databaseStructure = new DatabaseStructure(resultSet.getString("userUUID"), resultSet.getString("userGroup"), resultSet.getInt("perm"));
            preparedStatement.close();

            return databaseStructure;
        }

        preparedStatement.close();
        return null;
    }
}
