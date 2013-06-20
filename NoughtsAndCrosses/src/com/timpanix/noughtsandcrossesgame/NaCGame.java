package com.timpanix.noughtsandcrossesgame;
import java.util.Random;
import java.util.Scanner;


/**
 * @author Daniel
 *
 */
public class NaCGame {
	
	private static final int NUM_OF_LINES = 3;
	private static final int NUM_OF_FIELDS = NUM_OF_LINES * NUM_OF_LINES;
	private static final String[] MATRIX_POSITIONS = {"1a", "1b", "1c", "2a", "2b", "2c", "3a", "3b", "3c"};
	private static final int NUM_OF_PLAYERS = 2;
	private static final int NAME_MAX_LENGTH = 10;
	
	private static Player[] players = {new Player(), new Player()};
	private static int gameCount = 0;
	private static int drawCount = 0;
	
	private static final int NUM_OF_COLS_AND_ROWS = (getNumOfLines() * 2) + 1;	// empty line at the beginning and after every line of 0/X
	private String[] matrixValues;
	boolean[] freeFields;
	
	// constructor
	public NaCGame(){
		
		matrixValues = new String[getNumOfFields()];	// instantiate 2dim String array to hold all the played noughts and crosses
		freeFields = new boolean[getNumOfFields()];		// instantiate 2dm boolean array to keep track of all empty fields
		
		for(int i = 0; i < getMatrixValues().length; i++){
			setMatrixValues(i, " ");	// initialise all matrix values to empty Strings
			setFreeFields(i, true);		// set all fields empty
		}
		
		displayTitle();
		System.out.println("Game No. " + ++gameCount);
	}


	public void displayTitle() {
		
		System.out.println("X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X");
		System.out.println("\n\tN O U G H T S   a n d   C R O S S E S\n");
		System.out.println("X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X0X\n\n");
	}

	public int whoStartsTheGame(Scanner sc) {
		
		String starter = new String();
		int starterIndex = -1;
		displayLine();
		System.out.println("Who makes the first move?");
		System.out.println("Please type \n - the starting player's name OR\n - select \"R\" for random selection");
		
		boolean selectionOk = false;
		
		while(!selectionOk){
			System.out.print("Your selection: ");
			starter = sc.next();
			if(starter.equals(getPlayers()[0].getName())){			// if player1 starts
				starterIndex = 0;
				selectionOk = true;
			} else if(starter.equals(getPlayers()[1].getName())){	// if player2 starts
				starterIndex = 1;
				selectionOk = true;
			} else if(starter.equals("R") || starter.equals("r")){		// random selection was chosen
				starterIndex = selectRandomPlayer();
				selectionOk = true;
			} else													// invalid input
				System.out.println("This is not a valid selection. Please try again.");
		}

		displayLine();
		System.out.println(getPlayers()[starterIndex].getName() + " starts the game."); 
		
		return starterIndex;
	}

	public void displayLine() {
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - -");
	}

	public int selectRandomPlayer() {
		
		Random r = new Random();
		int i = r.nextInt(2);	// 2 values starting at 0 ( = 0 or 1)
		return i;
	}
	
	private static int getNumOfLines(){
		return NUM_OF_LINES;
	}
	
	private static int getNumOfFields(){
		return NUM_OF_FIELDS;
	}
	
	private static int getNameMaxLength(){
		return NAME_MAX_LENGTH;
	}

	private static String[] getMatrixPositions(){
		return MATRIX_POSITIONS;
	}
	
	private boolean[] getFreeFields(){
		return freeFields;
	}
	
	private String[] getMatrixValues(){
		return this.matrixValues;
	}
	
	private static int getNumOfColsAndRows(){
		return NUM_OF_COLS_AND_ROWS;
	}
	
	
	public void setPlayers(Scanner sc) {
	
		boolean player1IsOk = false;
		boolean player2isOk = false;

		
		// set name of player 1
		System.out.print("Name of Player 1: ");
		while(!player1IsOk){
			String namePlayer1 = sc.next();
			if(namePlayer1.length() <= getNameMaxLength()){
				getPlayers()[0].setName(namePlayer1);
				player1IsOk = true;
			}else
				System.out.print("Name is too long (max. 10 letters). Please use a shorter name: ");
		}
		
		// set name of player 2
		System.out.print("Name of Player 2: ");
		while(!player2isOk){
			String namePlayer2 = sc.next();
			if(namePlayer2.length() <= getNameMaxLength()){
				getPlayers()[1].setName(namePlayer2);
				player2isOk = true;
			}else
				System.out.print("Name is too long (max. 10 letters). Please use a shorter name: ");
		}
	}

	
	public static Player[] getPlayers(){
		return players;
	} 
	
	
	public void setGameBoard() {
		
		int lineDisplayNo = 0;
		int index = -1;
		System.out.println("\n  |  a  |  b  |  c  ");

		for(int i = 0; i < getNumOfColsAndRows(); i++){	
			for(int j = 0; j < getNumOfColsAndRows(); j++){	
				if(i % 2 == 0)
					System.out.print("---");
				else if(i % 2 == 1 && j == 0)
					System.out.print(++lineDisplayNo);
				else if (j % 2 == 1)
					System.out.print(" | ");
				else
					System.out.print(" " + getMatrixValues()[++index] + " ");
			}
			System.out.print("\n"); // create new line
		}
		
	}

	public int makeMove(int currentPlayerIndex, String move) {
		
		for(int i = 0; i < getMatrixPositions().length; i++){
			// if the current move (eg.1a) is legitimate and the field is free
			if(move.equals(getMatrixPositions()[i]) && getFreeFields()[i]){
				// put the players colour (X or ) into the chosen field
				//matrixValues[i] = getPlayers()[currentPlayerIndex].getColour();
				setMatrixValues(i, getPlayers()[currentPlayerIndex].getColour());
				setFreeFields(i, false);	// set the flag to false
				setGameBoard();		// display the updated gameboard
				return 1;
			} else if(move.equals(getMatrixPositions()[i]) && !getFreeFields()[i])
				return -1;
		}
		return -2;
	}
	
	private void setMatrixValues(int index, String colour) {
		this.matrixValues[index] = colour;
	}


	private void setFreeFields(int index, boolean value) {
		this.freeFields[index] = value;
	}


	/*
	 * this method checks if there are 3 noughts/crosses in a row, column or diagonally
	 */
//	
	public boolean checkStatus() {
		
		// check diagonally 
		
		// check 1: value is not an empty String
		int index = 0;
		if(!(getMatrixValues()[index].equals(" "))){
			// check 2a: top left to bottom right
			if(getMatrixValues()[index].equals(getMatrixValues()[index + 4]) && getMatrixValues()[index].equals(getMatrixValues()[index + 8])){
				return true;
			}
		}
			
		index = 2;
		if(!(getMatrixValues()[index].equals(" "))){
			// check 2b: top right to bottom left
			if(getMatrixValues()[index].equals(getMatrixValues()[index + 2]) && getMatrixValues()[index].equals(getMatrixValues()[index + 4])){
				return true;
			}
		}
		// check columns
		for(int i = 0; i < 3; i++){
			if(getMatrixValues()[i].equals(getMatrixValues()[i + 3]) && getMatrixValues()[i].equals(getMatrixValues()[i + 6]) && !(getMatrixValues()[i].equals(" "))){
				return true;
			}
		}
		
		
		// check rows
		final int[] rowStartNumbers = {0, 3, 6};
		index = -1;
		
		for(int i = 0; i < rowStartNumbers.length; i++){
			// check 1: value is not an empty String
			index = rowStartNumbers[i];
			if(!(getMatrixValues()[index].equals(" "))){
				// check 2a: three equal values in a row
				if(getMatrixValues()[index].equals(getMatrixValues()[index + 1]) && getMatrixValues()[index].equals(getMatrixValues()[index + 2])){
					return true;
				}	
			}
		}
		return false;
	}

	public void choseColour(int currentPlayerIndex, Scanner sc) {
		
		boolean colourIsOk = false;
		System.out.print("Would you like to play with noughts or crosses? (X/0) ");
		while(!colourIsOk){	
			String chosenColour = sc.next();
			displayLine();
			if(chosenColour.toUpperCase().equals("X")){
				System.out.println("You chose crosses.\n");
				getPlayers()[currentPlayerIndex].setColour("X");
				getPlayers()[(currentPlayerIndex + 1) % getNumOfPlayers()].setColour("0");	// this sets the colour of the other player
				colourIsOk = true;
			} else if(chosenColour.equals("0")){
				System.out.println("You chose noughts.\n");
				getPlayers()[currentPlayerIndex].setColour("0");
				getPlayers()[(currentPlayerIndex + 1) % getNumOfPlayers()].setColour("X");	// this sets the colour of the other player
				colourIsOk = true;
			} else
				System.out.print("Invalid input. Please try again: (0/X) ");
		}
	}
	
	public static int getNumOfPlayers(){
		return NUM_OF_PLAYERS;
	}
	
	public static int getDrawCount(){
		return drawCount;
	}
	
	public static void updateDrawCount(){
		drawCount++;
	}
	
	public void displayStats(){
		
		System.out.println("Statistics:");
		System.out.println("Wins for " + getPlayers()[0].getName() + ": " + getPlayers()[0].getNoOfWins());
		System.out.println("Wins for " + getPlayers()[1].getName() + ": " + getPlayers()[1].getNoOfWins());
		System.out.println("Draws: " + getDrawCount());
	}


	public void displayStartInfo() {
		
		System.out.println("START OF THE GAME");
		System.out.println("=================");
		System.out.println("Please make the first move.");
		System.out.println("Examples:\nTo put a cross/nought into");
		System.out.println(" - the top left field:     1a\n - the bottom right field: 3c");	
	}
}
