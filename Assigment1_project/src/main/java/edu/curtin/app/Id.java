package edu.curtin.app;

import java.util.*;

public class Id implements TaskApplication
{

    private String parentId;
    private String taskDesc;
    private List<TaskApplication> currentId;

    public Id(String parentId, String taskDesc, List<TaskApplication> currentId)
    {
        this.parentId = parentId;
        this.taskDesc = taskDesc;
        this.currentId = currentId;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String newParent)
    {
        this.parentId = newParent;
    }

    public String getTaskDesc()
    {
        return taskDesc;
    }
    
    public void setTaskDesc(String newTDesc)
    {
        this.taskDesc = newTDesc;
    }

    public List<TaskApplication> getCurrentId() 
    {
        return currentId;
    }

    public void setCurrentId(List<TaskApplication> newCurrent) 
    {
        this.currentId = newCurrent;
    }
    
    @Override
    public boolean findId(String checkId)
    {
        boolean exists = true;
        for (TaskApplication id : currentId)
        {
            exists = id.findId(checkId);
            if (exists)
            {
                break;
            }
        }
        return exists;
    }

    @Override
    public void changeEffort(String checkId, int newEffort)
    {
        @SuppressWarnings("PMD")
        boolean found = true;
        for (TaskApplication id : currentId)
        {
            found = id.findId(checkId);
            if (found)
            {
                id.changeEffort(checkId, newEffort);
                break;
            }
        }
    }

    @Override
    public int newEffort(String checkId)
    {
        int effort = 0;

        @SuppressWarnings("PMD")
        boolean found = true;
        
        for (TaskApplication id : currentId)
        {
            found = id.findId(checkId);
            if (found)
            {
                effort = id.newEffort(checkId);
                break;
            }
        }
        return effort;
    }

    @Override
    public Id getWorkId(String pId)
    {
        if (this.parentId.equals(pId))
        {
            return this;
        }

        for (TaskApplication id : currentId)
        {
            Id foundId = id.getWorkId(pId);
            if (foundId != null)
            {
                return foundId;
            }
        }
        return null;
    }
   
    @Override
    public void display(String indent)
    {
        if (parentId.equals("none"))
        {      
            for (TaskApplication data : currentId)
            {
                data.display("");
            }
        }
        else
        {
            System.out.println(indent + parentId + ": " + taskDesc);
            for (TaskApplication data : currentId)
            {
                data.display(indent + "\t");
            }
        }
    }

    @Override
    public int unknownTask()
    {
        int unknown = 0;
        for (TaskApplication id : currentId)
        {
            unknown += id.unknownTask(); 
        }
        return unknown;
    }

    @Override
    public int countEffort()
    {
        int count = 0;
        for (TaskApplication id : currentId)
        {
            count += id.countEffort(); 
        }
        return count;
    }

}