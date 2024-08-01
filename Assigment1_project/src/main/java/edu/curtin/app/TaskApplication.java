package edu.curtin.app;

public interface TaskApplication 
{

    void changeEffort(String id, int newEffort);
    
    boolean findId(String id);

    int newEffort(String id);

    Id getWorkId(String pId);

    void display(String indent);

    int unknownTask();

    int countEffort();
}
