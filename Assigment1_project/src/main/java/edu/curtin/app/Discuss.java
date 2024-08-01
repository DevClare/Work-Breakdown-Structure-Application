package edu.curtin.app;

import java.util.List;
import java.util.Scanner;

public class Discuss implements Estimate
{
    private Scanner input;

    public Discuss(Scanner input)
    {
        this.input = input;
    }
    
    @Override
    public int doOption(List<Integer> effortAdded)
    {
        int revisedEffort = 0;

        try
        {
            System.out.println("");
            System.out.println("Discuss among yourself for a single value for effort estimated.");
            System.out.println("Please enter a single revised estimate:");
            revisedEffort = Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException e)
        {
            //displayed when user enter something non-numerical
            System.out.println("Please only enter a number");
        }
        
        return revisedEffort;
    }
}
