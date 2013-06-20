package com.timpanix.noughtsandcrossesgame;

import static com.timpanix.noughtsandcrossesgame.NaCGame.*;
import java.util.Scanner;

public class NaCManager {

	/**
	 * @param args
	 */
public static void main(String[] args) {
		
		Player[] players = new Player[2];
		int currentPlayerIndex = -1;
		boolean firstGame = true;
		boolean playAgain = true;
		Scanner sc = new Scanner(System.in);	// instantiate new scanner object to get user input

		while(playAgain){	// playAgain is set to false when the user decides to quit the game.
		
			// instantiate a new game
			NaCGame game = new NaCGame();
			
			if(firstGame){
				// ask for player's names (only for the first game)
				game.setPlayers(sc);
			}
	
			// ask the players who wants to start the game
			currentPlayerIndex = game.whoStartsTheGame(sc);
			
			// ask the starting player to chose crosses or noughts
			game.choseColour(currentPlayerIndex, sc);
			
			// retrieve player data
			players = NaCGame.getPlayers();
			
			// display names and colours (starting player is displayed first)
			int otherPlayerIndex = (currentPlayerIndex + 1) % getNumOfPlayers();
			System.out.println(players[currentPlayerIndex].getName() + ":\t " + players[currentPlayerIndex].getColour());
			System.out.println(players[otherPlayerIndex].getName() + ":\t " + players[otherPlayerIndex].getColour());
			game.displayLine();
		
			// display "START OF THE GAME" and some help
			game.displayStartInfo();
			
			//set the empty game board
			game.setGameBoard();
			game.displayLine();
			
			// start the game
			int moveCounter = 0;
			boolean gameOver = false;
			int flag = 0;
			
			// play the game as long as the moveCounter is less than 9 and the game is not over
			while(!gameOver && moveCounter < 9){
				if(moveCounter == 0) // if the first move ...
					// ... ask the starting player to make the first move
					System.out.print(players[currentPlayerIndex].getName() +", please make the first move: ");
				else // otherwise just display the player's name (eg. Daniel: )
					System.out.print(players[currentPlayerIndex].getName() + ": ");
				// receive input (flag is set to 1 if input is valid)
				flag = game.makeMove(currentPlayerIndex, sc.next());
				// if the move is not legitimate because the field does not exist (eg. 4a) or the field is already filled...
				while(flag < 0){
					if(flag == -1)
						System.out.print("This field is not empty. Please select an empty field: ");
					if(flag == -2)
						System.out.print("This field does not exist. Please select a valid field: ");
					flag = game.makeMove(currentPlayerIndex, sc.next());	// ... ask the user to input another value
				}
				gameOver = game.checkStatus();
				moveCounter++;
				currentPlayerIndex = (currentPlayerIndex + 1) % getNumOfPlayers();		// change the current player
				game.displayLine();
			}
		
			// game over
			System.out.println("\t\tG A M E   O V E R");
			game.displayLine();
			if(gameOver){	// a player won
				currentPlayerIndex = (currentPlayerIndex + 1) % getNumOfPlayers();		// undo the latest change of the player
				System.out.println(players[currentPlayerIndex].getName() + " wins. Congratulations!\n");
				players[currentPlayerIndex].updateNoOfWins();	// add the win
			}else{			// nobody won
				System.out.println("This game ended without a winner");
				NaCGame.updateDrawCount();
			}
			
			// display statistics
			game.displayStats();
			
			// ask the users whether they like to play again
			System.out.print("Would you like to play again? (Y/N) ");
			String input = sc.next().toUpperCase();
			while(!(input.equals("N") || input.equals("Y"))){
				System.out.println("Invalid input. Please try again: ");
				input = sc.next().toUpperCase();
			}
			if(input.equals("N"))
				playAgain = false;		// end the game
			else
				firstGame = false;		// if the user wants to play again, don't ask for their names the next time
					
		}
		sc.close();						// close the scanner object
		System.out.println("\n\nX 0 X 0 X 0 X 0 X 0 THE END X 0 X 0 X 0 X 0 X 0 X");
	}

}
