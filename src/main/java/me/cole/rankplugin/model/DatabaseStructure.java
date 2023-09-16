package me.cole.rankplugin.model;

import javax.xml.crypto.Data;

public class DatabaseStructure
{
    private String userUUID;
    private String userGroup;
    private int perm;

    public DatabaseStructure(String userUUID, String userGroup, int perm)
    {
        this.userUUID = userUUID;
        this.userGroup = userGroup;
        this.perm = perm;
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
    public void setUserGroup(String userUUID)
    {
        this.userGroup = userGroup;
    }

    public int getPerm()
    {
        return this.perm;
    }
    public void setPerm(String userUUID)
    {
        this.perm = perm;
    }

}
