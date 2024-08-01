package edu.curtin.app;

import java.util.List;

public class Median implements Estimate
{
    public Median(){}

    @Override
    public int doOption(List<Integer> effortAdded)
    {
        @SuppressWarnings("PMD")
        int index, newEffort = 0;
        int total = effortAdded.size();

        if ((total % 2) == 0) //to handle oddnumber of estimator
        {
            index = total / 2; 
            newEffort = (effortAdded.get(index - 1) + effortAdded.get(index)) / 2;
        }
        else    //to handle even number of estimator
        {
            index = (total + 1) / 2; 
            newEffort = effortAdded.get(index);
        }
    
        return newEffort;
    }
}

