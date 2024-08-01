package edu.curtin.app;

import java.io.*;
import java.util.*;
import java.util.Collection;

public class EffortEstimation 
{
    private static Scanner input = new Scanner(System.in);
    private static Map<Integer, Estimate> doEstimate = new HashMap<>();

    public void effortMenu(TaskApplication workTask, int configureSelected, int defaultVal) 
    {
        //suppress PMD as effortList need to be called multiple times/duplicate mention to add multiple value of effort from user input
        @SuppressWarnings("PMD")
        //create new array list to add task's effort input by user
        List<Integer> effortList = new ArrayList<>();
        String inputTask;
        @SuppressWarnings("PMD")
        int newEffort = 0;
        boolean done = false;

        while(!done)
        {
            System.out.println("");
            System.out.println("Please enter the Task Id: ");
            inputTask = input.nextLine();
            
            //calling method to check if the Id inserted exist
            boolean checkId = workTask.findId(inputTask);
            boolean notEmpty = !inputTask.isEmpty();

            //only proceed with condition Id exist and the input is not empty
            if (checkId && notEmpty)     
            {
                //calling method to take user input for effort and add effort into the list
                effortList = addEffort(inputTask, defaultVal);
                //calling method to handle effort inserted by user and return only single value of effort
                newEffort = settingEffort(effortList, configureSelected);
                //calling method to set the new effort
                workTask.changeEffort(inputTask, newEffort);
                done = true;
            }
            else
            {
                System.out.println();
                System.out.println("Task ID does not exist! Please enter an existing Task ID that are broken down");
            }
        }
    
    }

    public List<Integer> addEffort(String effortId, int numEstimate) 
    {
        List<Integer> newEffort = new ArrayList<>(); //declare array list
        int effortInput;
        try
        {
            for (int i = 1; i <= numEstimate; i++)
            {
                System.out.println("");
                System.out.println("Effort Estimated " + i + ":");
                effortInput = Integer.parseInt(input.nextLine());
                newEffort.add(effortInput); //adding user input for Effort to the array list
            }
        }
        catch(NumberFormatException e)
        {
            // Displayed error message when the user entered something non-numerical.
            System.out.println("Please only enter a number for effort value");
        }
        display(effortId, newEffort); //calling display method
        return newEffort;
    }

    //to handle effort value inserted by user and return single value of new effort with appropriate handling
    public int settingEffort (List<Integer> inputEffort, int configDefault)
    {
        @SuppressWarnings("PMD")
        int newEffort = 0;

        //calling method to check whether there is different value of effort inserted
        boolean different = checkEffort(inputEffort);

        Collections.sort(inputEffort);    //to sort list in an ascending order

        doEstimate.put(1, new Highest());
        doEstimate.put(2, new Median());
        doEstimate.put(3, new Discuss(input));

        //when there is effort with different value
        if (different)
        {
            //call the selected/default approach for different value of effort
            newEffort = doEstimate.get(configDefault).doOption(inputEffort);
        }

        else
        {
            newEffort = inputEffort.get(0);
        }
        
        return newEffort;
    }

    //to display the Task's id and effort insert by user
    public void display(String id, List<Integer> newEffort)
    {
        System.out.println("");
        System.out.println("Task Id : " + id.toUpperCase());
        System.out.println("Effort Estimates: ");
        for (Integer value : newEffort) 
        {
            System.out.print(value + " ");
        }
    }

    //to check if the effort insert by user are similar, return true when there is similar effort value added
    public boolean checkEffort(List<Integer> newEffort)
    {
        boolean different = false;
        int effort = newEffort.get(0);
        //loop through the list to check if there is any different value of effort 
        for (Integer value : newEffort) 
        {
            if (value != effort)
            {
                different = true;
                break;
            }
        }
        return different;
    }

}
