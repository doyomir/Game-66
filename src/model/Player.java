package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Player {
	
	private ArrayList<Card> cards;
	private Card playedCard;
	private int score;
	
	public ArrayList<Card> getCards() {
		cards.sort((Comparator.comparing(Card::getPoints)));
		cards.sort((Comparator.comparing(Card::getColorPoints)));
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public Card getPlayedCard() {
		return playedCard;
	}
	
	public void setPlayedCard(Card playedCard) {
		this.playedCard = playedCard;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
