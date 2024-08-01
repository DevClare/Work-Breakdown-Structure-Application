package edu.curtin.app;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Configure 
{ 
    private static Scanner input = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(App.class.getName());

    //method to get N, number of estimators from user
    public int settingVal()
    {
        int numEstimate, defaultVal = 3;
        try
        {
            System.out.println("");
            System.out.println("Please enter the new default value for number of estimators(not less than 1):");
            numEstimate = Integer.parseInt(input.nextLine()); 

            if(numEstimate > 0)
            {
                defaultVal = numEstimate;
                logger.log(Level.INFO, "User insert the new N value, number of estimators");
            }
            else
            {
                System.out.println("Please enter a positive integer only.");
            }
        }
        catch(NumberFormatException e)
        {
            //display message when user enter something non-numerical
            System.out.println("Invalid input! Please only enter integer.");
        }

        return defaultVal;
    }
  
    //method to set default reconciliation approach
    public int configureHandling()
    {
        int defaultConfigure = 3;
        System.out.println("Please choose the reconciliation approach:");
        System.out.println("(1) Take the highest estimate.");
        System.out.println("(2) Take the median estimate.");
        System.out.println("(3) Discuss among yourself for a single value for effort estimated.");
        System.out.println("Your option:");
        try
        {
            switch (Integer.parseInt(input.nextLine())) 
            {
                case 1:
                    logger.log(Level.INFO, "User choose highest estimate as the reconciliation approach");
                    defaultConfigure = 1;
                    break;
            
                case 2:
                    logger.log(Level.INFO, "User choose median estimate as the reconciliation approach");
                    defaultConfigure = 2;
                    break;

                case 3:
                    logger.log(Level.INFO, "User choose discussion as the reconciliation approach");
                    defaultConfigure = 3;
                    break;
                    
                //when user input numerical value but it is not in the option
                default:
                    logger.log(Level.WARNING, "User insert integer that are not in the option");
                    System.out.println("Invalid Number! Please enter a valid number");
                    break;
            }
        }
        catch(NumberFormatException e)
        {
            logger.log(Level.WARNING, "User insert wrong data type for the option");
            // The user entered something non-numerical.
            System.out.println("Please only enter a number");
        }

        return defaultConfigure;
    }

}
