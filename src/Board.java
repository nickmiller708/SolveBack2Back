//Programming Assignment 1
//Author: Alex Cody
//This class creates the playing board for the Back2Back game
//import java.util.*;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
//import java.io.*;
public class Board
{
    private String[][] frontBoard; //2D array for the front of the board
    private String[][] backBoard; //2D array for the back of the board

    private Piece[] pieces; //array containing the puzzle pieces
    private Piece[] unused; //pieces remaining
    private String[] onBoard;
    
    private int rows = 5; //number of rows
    private int cols = 6; //number of columns

    public Board()
    {
	//Constructor
    onBoard = new String[11];
	frontBoard = new String[rows][cols];
	backBoard = new String[rows][cols];
	for(int i=0;i<rows;i++)
	    {
		for(int j=0;j<cols;j++)
		    {
			frontBoard[i][j] = "00";
			backBoard[i][j] = "00";
		    }
	    }
    }
    
    public int getRows()
    {
	return rows;
    }

    public int getCols()
    {
	return cols;
    }

    public String[][] getSide(String side)
    {
	if(side=="front")
	    {
		return frontBoard;
	    }
	else
	    {
		return backBoard;
	    }
    }
    
    public Piece[] getPieces()
    {
	return pieces;
    }

    public Piece[] getUnusedPieces()
    {
	return unused;
    }

    public void clearBoard() 
    {
	//resets all values on the board to "00" and sets all pieces to unused
	for(int i=0;i<rows;i++)
	    {
		for(int j=0;j<cols;j++)
		    {
			frontBoard[i][j] = "00";
			backBoard[i][j] = "00";
		    }
	    }
	unused = new Piece[pieces.length];
	for(int i=0;i<pieces.length;i++)
	    {
			unused[i] = pieces[i];
	    }
    }
    
    public void setBoard(String[][] front, String[][] back, Piece[] pieces)
    {
    	frontBoard = front;
    	backBoard = back;
    }

    public void setPieces(Piece[] pieces)
    {
    	this.pieces = pieces;
    }
    
    @SuppressWarnings("resource")
	public void setPieces(String filename) throws IOException
    {
	//Inputs: String that represents the file containing the pieces
	String[] colors = new String[11]; //array containing the colors of the pieces
	colors[0] = "Red";
	colors[1] = "Orange";
	colors[2] = "Yellow";
	colors[3] = "Green";
	colors[4] = "Teal";
	colors[5] = "Sky";
	colors[6] = "Azure";
	colors[7] = "Blue";
	colors[8] = "Pink1";
	colors[9] = "Pink2";
	colors[10] = "Violet";

	FileReader file = new FileReader(filename);
	Scanner stringIn = new Scanner(file);
	String[] temp_pieces = new String[11]; 
	int index = 0;
	while(stringIn.hasNext())
	    {
		temp_pieces[index] = stringIn.nextLine(); 
		index++;
	    }

	pieces = new Piece[11];
	unused = new Piece[11];
	for(int i=0;i<temp_pieces.length;i++)
	    {
		Piece temp = new Piece(temp_pieces[i],colors[i]); //creates new pieces from the temp array
		pieces[i] = temp;
		unused[i] = temp;
		}
    }    
    
    public void setUnusedPieces(Piece[] pieces)
    {
    	unused = new Piece[pieces.length];
    	for(int i=0;i<pieces.length;i++)
    	{
    		unused[i] = pieces[i];
    	}
    }
    
    public void printPieces()
    {
	//prints all available pieces
	int last = 1;
	for(int i=0;i<unused.length;i++)
	    {
		if(unused[i]!=null)
		    {
			System.out.println(last + ". " + unused[i]);
			last++;
		    }
	    }
    }

    public void printPiece(int num)
    {
	//prints a single piece
	System.out.println(unused[num]);
    }
    
    public Piece getPiece(int num)
    {
	//returns a specific piece
	return unused[num];
    }
    
    public boolean placePiece(Piece piece,String side, int row, int col)
    {
	//Inputs: Piece to be placed, String containing which side to place
	//the piece on, int representing the row and int representing the column
	//of where the upper left side of the piece will be situated on the board
	boolean placed = true;
	int[][] shape = piece.getShape(); //shape of the piece
	String[][] fBoard = new String[rows][cols]; //temp front board
	String[][] bBoard = new String[rows][cols]; //temp back board
	for(int i=0;i<rows;i++)
	    {
		for(int j=0;j<cols;j++)
		    {
			fBoard[i][j] = frontBoard[i][j];
			bBoard[i][j] = backBoard[i][j];
		    }
	    }
	int insertRow = row; //row on the board being altered
	int insertCol = col; //column on the board being altered
	for(int i=0;i<piece.getPieceRows();i++)
	    {
		for(int j=0;j<piece.getPieceCols();j++)
		    {
			if(insertRow<0 || insertRow>rows-1 || insertCol<0 || insertCol>cols-1) //checks that the row and column numbers are valid
			    {
				placed = false;
				break;
			    }
			else
			    {
				if(side=="front") //places the piece relative to the front of the board
				    {
					if(fBoard[insertRow][insertCol].equals("0") || fBoard[insertRow][insertCol].charAt(1)=='0') 
					    //if the front board has a 0
					    {
						if(shape[i][j]!=0)//if the piece does not have a 0 in the spot
						    {
							fBoard[insertRow][insertCol] = piece.getColor().charAt(0) + "" + 2; //front gets a color and a 2
							bBoard[insertRow][rows-insertCol] = piece.getColor().charAt(0) + "" + shape[i][j]; //back gets a color and a 1
						    }
					    }
					else if(fBoard[insertRow][insertCol].charAt(1)=='1')
					    //if the front board has a 1
					    {
						if(shape[i][j]!=2) //if the piece does not have a 2
						    {
							fBoard[insertRow][insertCol] = piece.getColor().charAt(0) + "" + 2; //front of the board gets a 2
						    }
						else
						    {
							placed = false;
							break;
						    }
					    }
					else
					    //if the front of the board has a 2, only a piece with a 0 can be placed there
					    {
						if(shape[i][j]!=0)
						    {
						    placed = false;
							break;
						    }
					    }
				    }
				else //places the piece relative to the back of the board (same rules as above only reversed)
				    {
					if(bBoard[insertRow][insertCol].equals("0") || bBoard[insertRow][insertCol].charAt(1)=='0')
					    {
						if(shape[i][j]!=0)
						    {
							bBoard[insertRow][insertCol] = piece.getColor().charAt(0) + "" + 2;
							fBoard[insertRow][rows-insertCol] = piece.getColor().charAt(0) + "" + shape[i][j];
						    }
					    }
					else if(bBoard[insertRow][insertCol].charAt(1)=='1')
					    {
						if(shape[i][j]!=2)
						    {
							bBoard[insertRow][insertCol] = piece.getColor().charAt(0) + "" + 2;	    
						    }
						else
						    {
							placed = false;
							break;
						    }
					    }
					else
					    {
					        if(shape[i][j]!=0)
						    {
						        placed = false;
							break;
						    }
					    }
				    }
			    }
			insertCol++;
		    }
		insertRow++;
		insertCol = col;
	    }
	if(placed) //if the piece did not encounter a collision, change the actual front and back of the board
	{
		frontBoard = fBoard;
		backBoard = bBoard;
		String color = piece.getColor();
		int index = 0;
		while(!unused[index].getColor().equals(color))
		{
			index++;
		}
		for(int j=index;j<unused.length-1;j++)
	    {
			unused[j] = unused[j+1];
	    }
		unused[unused.length-1] = null;
		index = 0;
		if(onBoard.length>0)
		{
			while(onBoard[index]!=null && index<10)
			{
				index++;
			}
		}
		if(index<10)
		{
			onBoard[index] = color;
		}
	}
	return placed;
    }
    
    public boolean containsPiece(String color)
    {
    	boolean contains = false;
    	for(int i=0;i<onBoard.length;i++)
    	{
    		if(onBoard[i]!=null && color!=null)
    		{
    			if(onBoard[i].equals(color))
    			{
    				contains = true;
    			}
    		}
    	}
    	return contains;
    }
    
    public boolean equals(Object o)
    {
    	boolean equals = true;
    	Board compare = (Board) o;
    	String[][] compFront = compare.getSide("front");
    	String[][] compBack = compare.getSide("back");
    	for(int i=0;i<rows;i++)
    	{
    		for(int j=0;j<cols;j++)
    		{
    			if(!frontBoard[i][j].equals(compFront[i][j]) || !backBoard[i][rows-j].equals(compBack[i][rows-j]))
    			{
    				equals = false;
    			}
    		}
    	}
    	return equals;
    }

    public String toString()
    {

	String toPrint = "Front View:               Back View:\n  0  1  2  3  4  5          0  1  2  3  4  5\n";
	for(int i=0;i<rows;i++)
	    {
		toPrint += i + " ";
		for(int j=0;j<cols;j++)
		    {
			toPrint += frontBoard[i][j] + " ";
		    }
		toPrint += "  |   " + i + " ";;
		for(int k=0;k<cols;k++)
		    {
			toPrint += backBoard[i][k] + " ";
		    }
		toPrint += "\n";
	    }
	return toPrint;
    }
}
