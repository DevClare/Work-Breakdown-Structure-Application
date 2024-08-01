package edu.curtin.app;

import java.util.*;

public class Task implements TaskApplication
{
    private String parent;
    private String id;
    private String desc;
    private int effort;

    public Task(String parent, String id, String desc, int effort)
    {
        this.parent = parent;
        this.id = id;
        this.desc = desc;
        this.effort = effort;
    }

    public String getParent()
    {
        return this.parent;
    }

    public void setParent(String newParent)
    {
        parent = newParent;
    }
    
    public String getId()
    {
        return this.id;
    }

    public void setId(String newId)
    {
        id = newId;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public void setDesc(String newDesc)
    {
        desc = newDesc;
    }

    public int getEffort()
    {
        return this.effort;
    }

    public void setEffort(int newEffort)
    {
        effort = newEffort;
    }

    @Override
    public boolean findId(String checkId)
    {
        if (id.toUpperCase().contains(checkId.toUpperCase()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void changeEffort(String id, int newEffort)
    {
        setEffort(newEffort);
        System.out.println();
        System.out.println();
        System.out.println(id.toUpperCase() + " : " + desc + ", effort = " + effort);
    }

    @Override
    public int newEffort(String checkId)
    {
        return effort;
    }

    @Override
    public Id getWorkId(String id)
    {
        return null;
    }

    @Override
    public void display(String indent)
    {
        if (effort == 0)
        {
            System.out.println(indent + "" + id + ": " + desc);
        }
        else
        {
            System.out.println(indent + "" + id + ": " + desc + ", effort = " + effort);
        }
    }

    @Override 
    public int unknownTask()
    {
        if (effort == 0)
        {
            return 1;
        }
        return 0;
    }


    @Override
    public int countEffort()
    {
        return effort;
    }

}
