package model;

import enums.CardColor;
import enums.CardNames;

public class Card {
	
	private CardNames name;
	private CardColor color;
	private int points;
	@SuppressWarnings("unused")
	private String cardImagePath;
	
	
	public CardNames getName() {
		return name;
	}
	public void setName(CardNames name) {
		this.name = name;
	}
	public CardColor getColor() {
		return color;
	}
	
	public int getColorPoints() {
		return color.getPoints();
	}
	
	public void setColor(CardColor color) {
		this.color = color;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getCardImagePath() {
		final String cardName = name.toString().toLowerCase();
		final String cardColor = color.toString().toLowerCase();
		return  "/resources/drawable-hdpi/"+cardName+cardColor+".png";		
	}
	
}
