
import java.util.ArrayList;
import java.io.IOException;
public class Node implements Comparable<Node>
{

	private Node parent;
	private ArrayList<Node> children;
	private Piece addedPiece;
	private String action;
	private double board_cost;
	private double total_cost;
	private double action_cost;
	private double path_cost;
	private double heuristic;
	private Board current_state;
	private Piece[] remaining;
	
	public Node(Board current,Node par,Piece p) throws IOException
	{
		current_state = new Board();
		for(int i=0;i<current.getRows();i++)
		{
			for(int j=0;j<current.getCols();j++)
			{
				(current_state.getSide("front"))[i][j] = (current.getSide("front"))[i][j];
				(current_state.getSide("back"))[i][j] = (current.getSide("back"))[i][j];
			}	
		}

		current_state.setUnusedPieces(current.getUnusedPieces());
		current_state.setPieces("src/pieces.txt");
		
		String[][] front = current_state.getSide("front");
		String[][] back = current_state.getSide("back");
		parent = par;
		addedPiece = p;
		heuristic = 0.0;
		board_cost = 0.0;
		for(int i=0;i<current_state.getRows();i++)
		{
			for(int j=0;j<current_state.getCols();j++)
			{
				if(front[i][j].charAt(1)=='2' && back[i][current_state.getRows()-j].charAt(1)=='2')
			    	{
						heuristic += 0;
						board_cost += 0;
			    	}
				else if(front[i][j].charAt(1)=='1' || back[i][current_state.getRows()-j].charAt(1)=='1')
			    	{
						heuristic += 1.5;
						board_cost += 1.5;
			    	}
				else
			    	{
						heuristic += 2;
						board_cost += 2;
			    	}	
			}
		}
		if(p!=null)
		{
			action_cost = p.getPieceScore();
		}
		else
		{
			action_cost = 0;
		}
		if(par!=null)
		{
			path_cost = parent.getPathCost();
		}
		path_cost += action_cost;
		
		total_cost = path_cost + heuristic;
		remaining = current_state.getUnusedPieces();
		children = new ArrayList<Node>();
	}
	
	public void generateChildren()
	{
		if(remaining.length!=0)
		{
			for(int q=0;q<2;q++)
			{
				for(int y=0;y<current_state.getRows();y++)
				{
					for(int z=0;z<current_state.getCols();z++)
					{
						for(int w=0;w<remaining.length;w++)
						{
							Piece adder = remaining[w];
							for(int x=0;x<4;x++)
							{
								Board dummy = new Board();
								dummy.setBoard(current_state.getSide("front"), current_state.getSide("back"), remaining);
								dummy.setUnusedPieces(remaining);
								dummy.setPieces(current_state.getPieces());
								if(adder!=null)
								{
									if(!dummy.containsPiece(adder.getColor()))
									{
									adder.rotateRight();
									if(q==0)
									{
										if(dummy.placePiece(adder, "front", y, z)==true)
										{
											action = "Front. Row: " + y + " Column: " + z;
											try 
											{
												Node newNode = new Node(dummy,this,adder);
												if(!children.contains(newNode))
												{
													children.add(newNode);
												}
											} 
											catch (IOException e) 
											{
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
									else
									{
										if(dummy.placePiece(adder, "back", y, z)==true)
										{		
											action = "Back. Row: " + y + " Column: " + z;
											try 
											{
												Node newNode = new Node(dummy,this,adder);
												if(!children.contains(newNode))
												{
													children.add(newNode);
												}
											}
											catch (IOException e) 
											{
												// TODO Auto-generated catch block
												e.printStackTrace();
											}	
										}
									}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public double getPathCost()
	{
		return path_cost;
	}
	
	public double getCost()
	{
		return total_cost;
	}
	
	public double getCurrentBoardCost()
	{
		return board_cost;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public Piece getAddedPiece()
	{
		return addedPiece;
	}
	
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	
	public Board getCurrentState()
	{
		return current_state;
	}
	
	public Piece[] getRemainingPieces()
	{
		return remaining;
	}
	
	public int compareTo(Node compare)
	{
		int comp;
		if(total_cost<compare.getCost())
		{
			comp = -1;
		}
		else if(total_cost>compare.getCost())
		{
			comp = 1;
		}
		else
		{
			comp = 0;
		}
		return comp;
	}
	
	public boolean equals(Object o)
	{
		boolean equals = false;
		Node node = (Node) o;
		if(total_cost == node.getCost())
		{
			if(current_state.equals(node.getCurrentState()))
			{
				equals = true;
			}
		}
		return equals;
	}

	public String toString()
	{
		String returner = "";
		returner += "Current Board: " + current_state.toString() + "\n";
		returner += "Piece Added: " + addedPiece +"\n";
		returner += "Action Taken: " + action;
		return returner;
	}
}

