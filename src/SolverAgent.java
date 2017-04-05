//Programming Assignment 2
//Author: Alex Cody
//
//This class solves the Back2Back puzzle.
import java.util.*;
import java.io.*;
public class SolverAgent
{
    
    private Node start;
   
    private PriorityQueue<Node> queue;

    private double goalScore = 0.0;

    public SolverAgent(Board startState)
    {
    	try{
		start = new Node(startState,null,null);
		start.generateChildren();
		
    	queue = new PriorityQueue<Node>(11);
    	queue.add(start);
    	}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String printPath(Node current_state)
    {
    	String returner = "";
    	Stack<Node> pathStack = new Stack<Node>();
    	Node explorer = current_state;
    	while (explorer != null)
    	{
    		Node parent = explorer.getParent();
    		pathStack.push(parent);
    	}
    	while (pathStack.isEmpty() == false)
    	{
    		Node inOrder = pathStack.pop();
    		returner += inOrder.toString();
    	}
    	return returner;
    }
    
    public boolean solve()
    {
    	long start = System.nanoTime();
    	boolean solved = false;
    	HashSet<String> explored_set = new HashSet<String>();
    	int numExplored = 0;
    	while(queue.size()>0 || !solved)
    	{
    		numExplored++;
    		Node current = queue.poll();
    		if(current.getCurrentBoardCost()==goalScore)
    		{
    			solved = true;
    			System.out.println("\nSolution:\n");
    			System.out.println(current.getCurrentState());
    			//System.out.println(printPath(current));
    			
    			break;
    		}
    		else
    		{
    			String addCurrent = current.getCurrentState().toString();
    			explored_set.add(addCurrent);
    			current.generateChildren();
    			ArrayList<Node> children = current.getChildren();
    		for(int i=0;i<children.size();i++)
    			{
    				if(!explored_set.contains(children.get(i).getCurrentState().toString()))
    				{
    					queue.add(children.get(i));
    				}
    			}
    		}
    	}
    	double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
    	System.out.println("I'm Done!");
    	System.out.println("Nodes Explored: " + numExplored);
    	System.out.println("Time elapsed in sec: " + elapsedTimeInSec);
    	return solved;
    }
}
