package edu.curtin.app;

import java.util.List;

public class Highest implements Estimate
{
    public Highest(){}
    
    @Override
    public int doOption(List<Integer> effortAdded)
    {
        int size = effortAdded.size();
        int effort = effortAdded.get(size-1);
        return effort;
    }
}


