package me.cole.rankplugin.model;

public class DatabaseStructure
{
    private String userUUID;
    private String userGroup;
    private int kills;
    private int deaths;
    private int level;
    private int exp;
    private int bal;


    public DatabaseStructure(String userUUID, String userGroup, int kills, int deaths, int level, int exp, int bal)
    {
        this.userUUID = userUUID;
        this.userGroup = userGroup;
        this.kills = kills;
        this.deaths = deaths;
        this.level = level;
        this.exp = exp;
        this.bal = bal;

    }

    public String getUserUUID()
    {
        return this.userUUID;
    }
    public void setUserUUID(String userUUID)
    {
        this.userUUID = userUUID;
    }

    public String getUserGroup()
    {
        return this.userGroup;
    }
    public void setUserGroup(String userGroup)
    {
        this.userGroup = userGroup;
    }

    public int getKills()
    {
        return this.kills;
    }
    public void setKills(int kills)
    {
        this.kills = kills;
    }

    public int getDeaths()
    {
        return this.deaths;
    }
    public void setDeaths(int deaths)
    {
        this.deaths = deaths;
    }

    public int getLevel()
    {
        return this.level;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getExp()
    {
        return this.exp;
    }
    public void setExp(int exp)
    {
        this.exp = exp;
    }

    public int getBal()
    {
        return this.bal;
    }
    public void setBal(int bal)
    {
        this.bal = bal;
    }

}
