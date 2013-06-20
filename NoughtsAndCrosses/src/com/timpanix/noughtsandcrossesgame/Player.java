package com.timpanix.noughtsandcrossesgame;

public class Player {
	
	private String name;
	private String colour;
	private int noOfWins;
	
	public Player(){
		this.noOfWins = 0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String string) {
		this.colour = string;
	}
	public int getNoOfWins() {
		return noOfWins;
	}
	
	public void updateNoOfWins(){
		noOfWins++;
	}
}
