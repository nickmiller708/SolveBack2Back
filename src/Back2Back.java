//Programming Assignment 1
//Author: Alex Cody
//
//This program simulates the puzzle game Back2Back.
//import java.util.*;
import java.util.Scanner;
import java.io.*;
public class Back2Back
{
    public static void prompt()
    {
	//Asks the player which action they would like to perform
	System.out.println("Which action would you like to take?");
	System.out.println("Press ENTER after every selection.");
	System.out.println("1. Clear Board");
	System.out.println("2. Place Piece");
	System.out.println("3. Solve");
	System.out.println("4. Quit");
    }

    public static Piece pieceChoice(Scanner scan,Board board)
    {
	//Inputs: Scanner being used, board that contains the pieces and puzzle
	//Returns: Piece corresponding to the choice that the player makes in the scanner
	System.out.println("Which piece would you like to place?");
	board.printPieces(); //prints all of the available pieces
	Piece piece = null;
	if(scan.hasNextInt())
	    {
		int choice = scan.nextInt()-1; //numbering starts at 0 so 1 must be subtracted
		if(choice<11) //checks for a valid input
		    {
			piece = board.getPiece(choice);
		    }
		else
		    {
		        piece = null;
		    }
	    }
	return piece;
    } 

    public static String boardChoice(Scanner scan)
    {
	//Inputs: Scanner being used
	//Returns: String containing the name of the side on which the player wants
	//to place a piece (either front or back)
	System.out.println("On which board would you like to place the piece?");
	System.out.println("[F] Front");
	System.out.println("[B] Back");
	String bChoice = "";
	if(scan.hasNext())
	    {
		String choice = scan.next();
		if(choice.equalsIgnoreCase("f"))
		    {
			bChoice = "front";
		    }
		else if(choice.equalsIgnoreCase("b"))
		    {
			bChoice = "back";
		    }
		else
		    {
			bChoice = "";
		    }
	    }
	return bChoice;
    }

    public static Piece rotate(Scanner scan, Piece piece,Board board)
    {
	//Inputs: Scanner being used, the piece that was selected, the
	//board being played on
	//Returns: the piece after it has been rotated the desired number
	//of times in the direction that was indicated
	System.out.println("To rotate your piece LEFT type L + ENTER.");
	System.out.println("To rotate your piece RIGHT type R + ENTER.");
	System.out.println("Otherwise, type C + ENTER to continue.");
	if(scan.hasNext())
	    {
		String input = scan.next();
		if(input.toUpperCase().equals("L")) //rotates the piece to the left
		   {
		       piece.rotateLeft();
		       System.out.println(board);
		       System.out.println(piece);
		       piece = rotate(scan,piece,board);
		   }
		else if(input.toUpperCase().equals("R")) //rotates the piece to the right
		    {
			piece.rotateRight();
			System.out.println(board);
			System.out.println(piece);
			piece = rotate(scan,piece,board);
		    }
		else if(!(input.toUpperCase()).equals("C")) //alerts an invalid input
		    {
			System.out.println("Invalid Input, Previous Rotation will now be Used");
		    }
	    }
	return piece;
    }

    public static int setCoordinates(Scanner scan,String pos,String side)
    {
	//Inputs: Scanner being used, String saying either ROW or COLUMN, String
	//sayinng either FRONT or BACK
	//Returns: int containing the desired ROW or COLUMN number where the top 
	//left corner of the puzzle piece will be placed
	System.out.println("Please enter the number cooresponding");
	System.out.println("to the " + pos.toUpperCase() + " on the " + side.toUpperCase());
	System.out.println("at which you would");
	System.out.println("like the TOP LEFT corner of");
	System.out.println("the puzzle piece to sit.");
	int coord = 0;
	if(scan.hasNextInt())
	    {
		coord = scan.nextInt();
	    }
	return coord;
    }

    public static void main(String[] args) throws IOException
    {
	System.out.println("\nWelcome to the Back2Back puzzle game.\n");
	System.out.println("The goal is to fill every slot in");
	System.out.println("the grid using every puzzle piece.");
	System.out.println("When a piece is placed on the board,");
	System.out.println("a letter representing the color and");
	System.out.println("a 1 or 2 represending the depth of");
	System.out.println("the piece will appear in the grid.");
	System.out.println("A 1 indicates that a piece with");
	System.out.println("either a 0 or a 1 in the same spot");
	System.out.println("may be placed there, whereas");
	System.out.println("a 2 indicates that only a piece with a");
	System.out.println("0 in the same spot may be placed there.");
	System.out.println("Type Ctrl + C at any time to quit.\n");

	Board board = new Board();
	board.setPieces("src/pieces.txt");
	System.out.println(board);

	Scanner conIn = new Scanner(System.in);
	System.out.println("Which action would you like to take?");
	System.out.println("Press ENTER after every selection.");
	System.out.println("1. Clear Board");
	System.out.println("2. Place Piece");
	System.out.println("3. Solve");
	System.out.println("4. Quit");

	int piecesUsed = 0;
	int number = 0;
	while(conIn.hasNext() && number!=4)
	    {
		if(conIn.hasNextInt())
		    {
			number = conIn.nextInt();
			if(number==1) //clears the board
			    {
				board.clearBoard();
				System.out.println(board);
				prompt();
			    }
			else if(number==2) //goes through the process of placing a piece
			    {
				System.out.println(board);
				Piece piece = pieceChoice(conIn,board);
				if(piece==null) //alerts that an invalid piece selection was made
				    {
					System.out.println("Invalid Piece Selection\n");
				        System.out.println(board);
					prompt();
				    }
				else
				    {
					System.out.println(board);
					System.out.println(piece);
					piece = rotate(conIn,piece,board); //goes through piece rotation process
					String side = boardChoice(conIn);
					if(side=="") //alerts that an invalid side selection was made
					    {
						System.out.println("Invalid Side Selection\n");
					        System.out.println(board);
						prompt();
					    }
					else
					    {
						System.out.println(board);
						System.out.println(piece);
						System.out.println("Your piece will be placed on the " + side.toUpperCase() + " side of the board.\n");
						int row = setCoordinates(conIn,"row",side); //sets the ROW for the upper left corner of the piece
						int col = setCoordinates(conIn,"column",side); //sets the COLUMN for the upper left corner of the piece
						if(row<0 || row>board.getRows()-1 || col<0 || col>board.getCols()-1) //alerts that invalid coordinates were entered
						    {
							System.out.println("Incorrect Coordinates");
						        System.out.println(board);
							prompt();
						    }
						else
						    {
							board.placePiece(piece,side,row,col); //places the piece on the board
							piecesUsed++;
							if(piecesUsed==11) //indicates that the puzzle was solved
							    {
								System.out.println(board);
								System.out.println("Congratulations! You solved the puzzle!");
								return;
							    }
							else
							    {
								System.out.println(board); //begins prompting for a new action
								prompt();
							    }
						    }
					    }
				    }
			    }
			else if(number==3) //calls the problem solving agent
			    {
				SolverAgent solver = new SolverAgent(board);
				boolean solved = solver.solve();
				if(!solved)
				{
					System.out.println("\nNo Solution Found");
				}
				return;
			    }
			else if(number==4) //quits the program
			    {
				return;
			    }
			else //indicates that the user inputed an invalid Action selection
			    {
				System.out.println("Invalid Action Selection\n"); 
			        System.out.println(board);
				prompt();
			    }
		    }
	    }
	return;
    }
}

