package edu.curtin.app;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class App
{
    private static Scanner input = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args)
    {
        String fileName;
        
        if (args.length != 1) //display error message when there is more than one command line arguments
        {
            System.out.println("Please enter one filename only!");
        }

        fileName = args[0];
        
        try
        {
            TaskApplication work = readWorkStructure(fileName);
            showMenu(work, fileName);
        }
        catch(IOException e)
        {
            System.out.println("Could not read from " + fileName + ": " + e.getMessage());
        }
    }

    public static void showMenu(TaskApplication work, String fileName) throws IOException
    {
        EffortEstimation effort = new EffortEstimation();   //create new instance of EffortEstimation class
        Configure config = new Configure(); //create new instance of Configure class
        int defaultVal = 3; //initialize default value,N as 3 
        int configureSelected = 3;  //initialize configure option, reconciliciation approach default 
        boolean done = false;

        logger.log(Level.INFO, "Starting the program...");

        while(!done) //loop until user choose quit option 
        {
            System.out.println("");

            work.display("\t"); //display the WBS

            //display the total known effort and unknown tasks
            System.out.println();
            System.out.println("Total known effort = " + work.countEffort());
            System.out.println("Unknown tasks = " + work.unknownTask());

            System.out.println();
            System.out.println("(1) Effort Estimation");
            System.out.println("(2) Configure");
            System.out.println("(3) Quit");
            System.out.println("Your option:");
            try
            {
                switch(Integer.parseInt(input.nextLine()))
                { 
                    case 1:
                        logger.log(Level.INFO, "User choose the effort estimation option");
                        //call method in EffortEstimation class
                        effort.effortMenu(work, configureSelected, defaultVal);
                        //save the new effort into the file
                        writeFile(fileName, work);
                        break;
                        
                    case 2:
                        logger.log(Level.INFO, "User choose the configure option");
                        //call method in Configure class that will return integer, the new N default, number of estimators
                        defaultVal = config.settingVal();
                        //call method in Configure class that will return integer, the new reconciliation approach  default
                        configureSelected = config.configureHandling();
                        break;
                        
                    case 3:
                        logger.log(Level.INFO, "End of program");
                        //end the while loop 
                        done = true;
                        break;

                    default:
                        logger.log(Level.WARNING, "User insert integer that are not in the menu");
                        //to handle numerical input that are not in the option
                        System.out.println("Invalid Number! Please enter a valid number");
                        break;
                }
            }
            catch(NumberFormatException e)
            {
                logger.log(Level.WARNING, "User insert wrong data type for the menu");
                // Displayed error message when the user entered something non-numerical.
                System.out.println("Please only enter a number");
            }
        }
    }

    public static TaskApplication readWorkStructure(String fileName) throws IOException
    {
        //intialize "none" as root or initial parentId
        TaskApplication app = new Id("none", "", new ArrayList<>());
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                if(!line.isEmpty())
                {
                    //to ignore the whitespaces on either side of ';'
                    String[] parts = line.split(";\\s*", -1);
                                    
                    // Note: 
                    // parts[0] contains the parent's id(if any).
                    // parts[1] contains the current's task id.
                    // parts[2] contains the task's description.
                    // parts[3] contains the task's effort estimate(integer).

                    String parentId = parts[0];
                    String currentId = parts[1];
                    String desc = parts[2];
                    int effort = 0;

                    if (parentId.isEmpty()) //for id with no Parent's Task id, Parent's id is set to "none"
                    {
                        parentId = "none";
                    }
     
                    //calling method to check if parent task id exist
                    Id parentTask = app.getWorkId(parentId);

                    switch(parts.length)
                    {
                        case 3:  //For task that are broken down
                            
                            // Create new task
                            TaskApplication subTask = new Id(currentId, desc, new ArrayList<>());     
                            parentTask.getCurrentId().add(subTask);
                            break;

                        case 4:  //For task that are broken down, have task's effort estimate(either empty or positive integer)
                            try
                            {
                                if (!parts[3].isEmpty())
                                {
                                    effort = Integer.parseInt(parts[3]);
                                }
                                // Create new task
                                Task newTask = new Task(parentId, currentId, desc, effort);
                                parentTask.getCurrentId().add(newTask);

                            }
                            catch(NumberFormatException e)
                            {
                                throw new IOException("Task in WBS: Invalid number format", e);
                            }
                            break;

                        default:
                            //error message for invalid line format in the file
                            throw new IOException("Unknown line format for Task in WBS");
                        }                                
                }
                line = reader.readLine();
            }
        }
        return app;
    }

    public static void writeFile(String fileName, TaskApplication task) throws IOException
    {
        //create new String Array List to add each line from the existing file
        List<String> taskList = new ArrayList<>();
        //before writing the file, read the file before making the changes
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                if(!line.isEmpty())
                {
                    String[] parts = line.split(";\\s*", -1);
                    
                    String parentId = parts[0];
                    String currentId = parts[1];
                    String desc = parts[2];

                    switch(parts.length)
                    {
                        case 3: //For task that are broken down, no task's effort estimate
                            if (parentId.isEmpty()) 
                            {
                                taskList.add("; " + currentId + "; " + desc);
                            }
                            else
                            {
                                taskList.add(parentId+ "; " + currentId + "; " + desc);
                            }
                            break;

                        case 4:  //For task that are broken down
                            //calling method to get modified task's effort estimate
                            String effort = Integer.toString(task.newEffort(currentId));
                            if (parentId.isEmpty())
                            {
                                taskList.add("; " + currentId + "; " + desc + "; " + effort);
                            }
                            else
                            {
                                taskList.add(parentId+ "; " + currentId + "; " + desc + "; " + effort);
                            }
                            break;

                        default:
                            //error message for invalid line format in the file
                            throw new IOException("Unknown line format for Task in WBS"); 
                    }
                }
                line = reader.readLine();
            }
        }
        try (BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter("testChangedEffort.txt"));)
        {
            logger.log(Level.INFO, "Write the file with new value of effort");
    
            //loop through the list
            for (String content : taskList)
            {
                bufferedWrite.write(content);
                bufferedWrite.newLine();
            }

            //display message when file successfully written
            System.out.println("New effort have been recorded!");

        }
        catch(IOException e)
        {
            logger.log(Level.WARNING, "Error! Failed to write the file");
        	//display error message to handle the exception, when file failed to be written
        	System.out.println("Failed to save the new effort");
        }
    }
}
