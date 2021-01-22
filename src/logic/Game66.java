package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import enums.CardColor;
import enums.CardNames;
import enums.Lead;
import model.Card;
import model.Player;

public class Game66 {

		protected Shell shell;
		protected ArrayList<Card> deck;
		protected Player userPlayer = new Player();		
		protected Player computerPlayer = new Player();
		protected Card trumpCard = new Card();
		protected boolean wait = true; //When true some of the buttons can't be pressed by the user
		protected boolean closed = false;
		protected Lead lead;
		protected boolean showComputerCards = false;
		
		

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */		
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("Santase 66");	
		ArrayList<Button> playerCardsButtons = new ArrayList<Button>();
		ArrayList<Button> computerCardsButtons = new ArrayList<Button>();
		
		Button btnPlayerCard1 = new Button(shell, SWT.NONE);		
		btnPlayerCard1.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard1.setBounds(41, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard1);
		
		Button btnPlayerCard2 = new Button(shell, SWT.NONE);
		btnPlayerCard2.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard2.setBounds(161, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard2);
		
		Button btnPlayerCard3 = new Button(shell, SWT.NONE);
		btnPlayerCard3.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard3.setBounds(281, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard3);
		
		Button btnPlayerCard4 = new Button(shell, SWT.NONE);
		btnPlayerCard4.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard4.setBounds(401, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard4);		
		
		Button btnPlayerCard5 = new Button(shell, SWT.NONE);
		btnPlayerCard5.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard5.setBounds(521, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard5);
		
		Button btnPlayerCard6 = new Button(shell, SWT.NONE);
		btnPlayerCard6.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnPlayerCard6.setBounds(641, 402, 114, 149);
		playerCardsButtons.add(btnPlayerCard6);
		
		Button btnComputerCard1 = new Button(shell, SWT.NONE);
		btnComputerCard1.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard1.setBounds(41, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard1);
		
		Button btnComputerCard2 = new Button(shell, SWT.NONE);
		btnComputerCard2.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard2.setBounds(161, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard2);
		
		Button btnComputerCard3 = new Button(shell, SWT.NONE);
		btnComputerCard3.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard3.setBounds(281, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard3);
		
		Button btnComputerCard4 = new Button(shell, SWT.NONE);
		btnComputerCard4.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard4.setBounds(401, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard4);
		
		Button btnComputerCard5 = new Button(shell, SWT.NONE);
		btnComputerCard5.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard5.setBounds(521, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard5);
		
		Button btnComputerCard6 = new Button(shell, SWT.NONE);
		btnComputerCard6.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnComputerCard6.setBounds(641, 10, 114, 149);
		computerCardsButtons.add(btnComputerCard6);
		
		Button btnDeckCards = new Button(shell, SWT.NONE);
		btnDeckCards.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnDeckCards.setBounds(41, 196, 114, 149);
		btnDeckCards.setToolTipText("Closed");
		
		Button btnTrumpCard = new Button(shell, SWT.NONE);
		btnTrumpCard.setBounds(130, 196, 114, 149);
		btnTrumpCard.setToolTipText("Change card");
		
		Button btnPlayedCardComputer = new Button(shell, SWT.NONE);
		btnPlayedCardComputer.setBounds(348, 165, 114, 149);
		
		Button btnPlayedCardPlayer = new Button(shell, SWT.NONE);
		btnPlayedCardPlayer.setBounds(468, 247, 114, 149);
		
		Label lblScoreComputer = new Label(shell, SWT.NONE);
		lblScoreComputer.setBounds(641, 165, 55, 15);
		lblScoreComputer.setText("Score:");
		
		Label lblScorePlayer = new Label(shell, SWT.NONE);
		lblScorePlayer.setText("Score:");
		lblScorePlayer.setBounds(641, 381, 55, 15);
		
		Button btnShowComputerCards = new Button(shell, SWT.NONE);
		btnShowComputerCards.setBounds(641, 350, 120, 25);
		btnShowComputerCards.setText("Show computer cards");		
		
		Button btnNewGame = new Button(shell, SWT.NONE);
		btnNewGame.setText("New Game");
		btnNewGame.setBounds(641, 320, 120, 25);
		
		//Create listeners on player cards buttons.
		for (int i = 0; i < playerCardsButtons.size(); i++) {
			final int index = i;
			playerCardsButtons.get(index).addMouseListener(new MouseAdapter() {
				@Override
				public  void mouseDown(MouseEvent e) {
					if(!wait) {
						if( userPlayer.getCards().size() >= index+1) {
							if(isTheCardAllowed(userPlayer.getCards().get(index))) {
								wait = true;
								//checking for pair
								if(userPlayer.getCards().get(index).getName() == CardNames.QUEEN) {
									int pairScore = checkForPair(userPlayer.getCards().get(index));
									if(pairScore>0) {
										PairDialog pairDialog = new PairDialog(shell, 1, pairScore);
										pairDialog.open();
										userPlayer.setScore(userPlayer.getScore() + pairScore);
										lblScorePlayer.setText("Score:" + userPlayer.getScore());
									}									
								}
								userPlayer.setPlayedCard(userPlayer.getCards().get(index));
								userPlayer.getCards().remove(index);				
								playerCardsButtons.get(index).setImage(SWTResourceManager.getImage(Game66.class, null));
								btnPlayedCardPlayer.setImage(SWTResourceManager.getImage(Game66.class, userPlayer.getPlayedCard().getCardImagePath()));
								//when player leads computer must reply with card.
								if(lead == Lead.PLAYER) {
									
									computerPlayer.setPlayedCard(computerReplyCard());
									btnPlayedCardComputer.setImage(SWTResourceManager.getImage(Game66.class, computerPlayer.getPlayedCard().getCardImagePath()));	
								}
								//If computer leads trickBattle determines who wins the trick.
								else {
									trickBattle();
								}								
							}								
						}						
					}					
				}				
			});
		}
		
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {	
				startNewGame();
				//Visualize all the cards
				if (showComputerCards == true) {
					for (int i = 0; i < 6; i++) {
						computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, computerPlayer.getCards().get(i).getCardImagePath()));
					}
				}
				
				for (int i = 0; i < 6; i++) {					
						playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, userPlayer.getCards().get(i).getCardImagePath()));
				}
				
				btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, trumpCard.getCardImagePath()));
				btnPlayedCardComputer.setImage(SWTResourceManager.getImage(Game66.class, null));
				btnPlayedCardPlayer.setImage(SWTResourceManager.getImage(Game66.class, null));
				lblScoreComputer.setText("Score:");
				lblScorePlayer.setText("Score:");
				
			}
		});

		
		btnPlayedCardPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {				
				//Visualize the cards if the cards are less than six the catch will set the rest to empty.
				if (wait) {
					if (showComputerCards == true) {

						for (int i = 0; i < 6; i++) {
							try {
								computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
										computerPlayer.getCards().get(i).getCardImagePath()));
							} catch (Exception e1) {
								computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
							}
						}
					}
					for (int i = 0; i < 6; i++) {
						try {
							playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
									userPlayer.getCards().get(i).getCardImagePath()));
						} catch (Exception e1) {
							playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
						}
					}
					
					btnPlayedCardComputer.setImage(SWTResourceManager.getImage(Game66.class, null));
					btnPlayedCardPlayer.setImage(SWTResourceManager.getImage(Game66.class, null));
					userPlayer.setPlayedCard(null);
					computerPlayer.setPlayedCard(null);
					lblScorePlayer.setText("Score:" + userPlayer.getScore());
					lblScoreComputer.setText("Score:" + computerPlayer.getScore());
					if (closed == true) {
						final String trumpColor = trumpCard.getColor().toString().toLowerCase();
						btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class,
								"/resources/drawable-hdpi/" + trumpColor + ".png"));
					}
					
					if (!computerPlayer.getCards().isEmpty()) {
						if (lead == Lead.COMPUTER) {
							if(computerPlayer.getScore() >= 66) {
								endGame();
							}
							
							computerPlayer.setPlayedCard(computerPlayCard());
							computerPlayer.getCards().remove(computerPlayer.getPlayedCard());
							btnPlayedCardComputer.setImage(SWTResourceManager.getImage(Game66.class,
									computerPlayer.getPlayedCard().getCardImagePath()));
						}						
					}
					
					wait = false;
					
					if (userPlayer.getCards().isEmpty() && computerPlayer.getCards().isEmpty()) {
						endGame();
					} 
					
					if(lead == Lead.PLAYER && userPlayer.getScore()>=66) {
						endGame();
					}
				}
			}
		});
		
		//Change with a nine trump card
		btnTrumpCard.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {
				//If player has trump nine card, he can change it from the deck
				if (deck.size() > 1 && deck.size() < 11) {					
					if (!getTrumpColorCards(userPlayer).isEmpty()
							&& getTrumpColorCards(userPlayer).get(0).getName() == CardNames.NINE) {
						userPlayer.getCards().add(trumpCard);
						trumpCard = getTrumpColorCards(userPlayer).get(0);
						userPlayer.getCards().remove(getTrumpColorCards(userPlayer).get(0));
						
						for (int i = 0; i < 6; i++) {
							try {
								playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
										userPlayer.getCards().get(i).getCardImagePath()));
							} catch (Exception e1) {
								playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
							}
						}
						
						btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, trumpCard.getCardImagePath()));
					} 
				}
			}
		});
		//Close
		btnDeckCards.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {	
				if(lead == Lead.PLAYER && deck.size() > 1 && deck.size() < 11) {
					btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/" + trumpCard.getColor() + ".png"));
					closed = true;
				}
			}
		});
		
		btnShowComputerCards.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {				
				
				showComputerCards = (showComputerCards == true) ? false : true;
				String btnShowComputerCadsText = (showComputerCards == true) ? "Hide computer cards" : "Show computer cards";
				btnShowComputerCards.setText(btnShowComputerCadsText);				
				
				for (int i = 0; i < 6; i++) {
					try {
						String cardImagePath = (showComputerCards == true) ? computerPlayer.getCards().get(i).getCardImagePath() : "/resources/drawable-hdpi/card_back.png";
						computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
								cardImagePath));
					} catch (Exception e1) {
						computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
					}
				} 
			}
		});
		
	}
	

	
	private void startNewGame() {
		
		userPlayer.setPlayedCard(null);
		computerPlayer.setPlayedCard(null);
		
		deck = getShuffledDeck();		
		
		userPlayer.setCards(getPlayingCards());
		userPlayer.setScore(0);
		
		computerPlayer.setCards(getPlayingCards());
		computerPlayer.setScore(0);
		
		lead = Lead.PLAYER;			//Every new game human player leads
		
		trumpCard = getTrumpCard();		
		
		wait = false;
		closed = false;
		
	}
	
	private void endGame() {		
		EndGameDialog dialog = new EndGameDialog(shell, 1, userPlayer.getScore(), computerPlayer.getScore());
		dialog.open();
		shell.close();
		open();				
	}
	
	
	private Card computerReplyCard() {	
		
		ArrayList<Card> wantedColorCards = getWantedColorCards(computerPlayer, userPlayer.getPlayedCard());
		
		ArrayList<Card> trumpColorCards = getTrumpColorCards(computerPlayer);
		
		if(!closed) {			
			//if player's card is not trump card and can be taken
			if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints()>userPlayer.getPlayedCard().getPoints()) {
				return computerTakesTheCard(wantedColorCards);					
			}
			//if player's card is not trump card and is higher card
			if(userPlayer.getPlayedCard().getPoints()>= 10 && userPlayer.getPlayedCard().getColor()!=trumpCard.getColor()) {
				//if there is any trump cards in the hand
				if(!trumpColorCards.isEmpty()) {
					computerPlayer.getCards().remove(trumpColorCards.get(0));
					computerWins(trumpColorCards.get(0).getPoints() + userPlayer.getPlayedCard().getPoints());
					return trumpColorCards.get(0);
				}			
			}		
			//if player's card is trump card
			if(userPlayer.getPlayedCard().getColor()==trumpCard.getColor()) {
				//if player's card is ten
				if(userPlayer.getPlayedCard().getPoints() == 10) {
					//if there is an Ace
					if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints() ==11) {
						return computerTakesTheCard(wantedColorCards);						
					}					
				}			
			}			
		}
		
		if(closed) {
			//if the card can be taken
			if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints()>userPlayer.getPlayedCard().getPoints()) {
				return computerTakesTheCard(wantedColorCards);				
			}			
			//if there is lower card with the same color
			if(!wantedColorCards.isEmpty()) {				
				computerPlayer.getCards().remove(wantedColorCards.get(0));
				playerWins(wantedColorCards.get(0).getPoints() + userPlayer.getPlayedCard().getPoints());
				
				return wantedColorCards.get(0);								
			}
			//Using a trump card 
			if(!trumpColorCards.isEmpty()) {
				computerPlayer.getCards().remove(trumpColorCards.get(0));
				computerWins(trumpColorCards.get(0).getPoints() + userPlayer.getPlayedCard().getPoints());
				return trumpColorCards.get(0);
			}		
		}	
		//Player wins trick
		ArrayList<Card> noTrumpColorCards = getNoTrumpColorCards(computerPlayer);
		
		computerPlayer.getCards().remove(noTrumpColorCards.get(0));
		playerWins(noTrumpColorCards.get(0).getPoints() + userPlayer.getPlayedCard().getPoints());
				
		return noTrumpColorCards.get(0);
		
	}
	

	private Card computerTakesTheCard(ArrayList<Card> wantedColorCards) {
		
		Card returnedCard = new Card();

		for (Card card : wantedColorCards) {
			if(card.getPoints()>userPlayer.getPlayedCard().getPoints()) {
				returnedCard = card;
				break;
			}			
		}
		
		computerPlayer.getCards().remove(returnedCard);
		computerWins(returnedCard.getPoints() + userPlayer.getPlayedCard().getPoints());
		
		return returnedCard;
	}
	
	private void trickBattle() {
		int trickScore = userPlayer.getPlayedCard().getPoints() + computerPlayer.getPlayedCard().getPoints();
		
		//if card are the same color
		if(userPlayer.getPlayedCard().getColor() == computerPlayer.getPlayedCard().getColor()) {
			//if computer card is bigger computer wins
			if(userPlayer.getPlayedCard().getPoints() < computerPlayer.getPlayedCard().getPoints()) {
				computerWins(trickScore);
			}
			//if player card is bigger player wins
			if(userPlayer.getPlayedCard().getPoints() > computerPlayer.getPlayedCard().getPoints()) {
				playerWins(trickScore);
			}
		}
		
		//if cards are different color
		if(userPlayer.getPlayedCard().getColor() != computerPlayer.getPlayedCard().getColor()) {
			//if player card is trump card player wins
			if(userPlayer.getPlayedCard().getColor() == trumpCard.getColor()) {
				playerWins(trickScore);
			}
			else {
				computerWins(trickScore);
			}
		}
	}

	
	private Card computerPlayCard() {
		
		if(closed) {
			return computerPlayer.getCards().get(computerPlayer.getCards().size() - 1);
			
		}
		if(!getNoTrumpColorCards(computerPlayer).isEmpty()) {
			return getNoTrumpColorCards(computerPlayer).get(0);
		}
		
		return computerPlayer.getCards().get(0);
		
	}
	
	private void computerWins(int trickScore) {
		int lastTenPoints = 0;
		if(userPlayer.getCards().isEmpty() && computerPlayer.getCards().isEmpty()) {
			lastTenPoints = 10;
		}		
		computerPlayer.setScore(computerPlayer.getScore() + trickScore + lastTenPoints);
		lead = Lead.COMPUTER;
		getCardFromTheDeck(computerPlayer, userPlayer);
		
	}
	
	private void playerWins(int trickScore) {
		int lastTenPoints = 0;
		if(userPlayer.getCards().isEmpty() && computerPlayer.getCards().isEmpty()) {
			lastTenPoints = 10;
		}	
		userPlayer.setScore(userPlayer.getScore() + trickScore+lastTenPoints);
		lead = Lead.PLAYER;
		getCardFromTheDeck(userPlayer, computerPlayer);
	}
	

	//Card Functions
	
	//returns the first six cards in the deck.
	private ArrayList<Card> getPlayingCards(){
		ArrayList<Card> playingCards = new ArrayList<Card>();
		
		for (int i = 0; i < 6; i++) {
			playingCards.add(deck.get(0));
			deck.remove(0);
		}		
		return playingCards;		
	}
	
	//returns the trump card.
	private Card getTrumpCard(){
		Card trumpCard = deck.get(0);
		deck.remove(0);
		
		return trumpCard;		
	}
	
	//returns a card from the deck after every trick.
	private void getCardFromTheDeck(Player winner, Player loser) {
		if(closed==false) {
			if(deck.size()>=3) {
				winner.getCards().add(deck.get(0));
				deck.remove(0);
				loser.getCards().add(deck.get(0));
				deck.remove(0);	
			}
			
			else {
				winner.getCards().add(deck.get(0));
				deck.remove(0);
				loser.getCards().add(trumpCard);
				closed = true;
			}
		}		
	}
	
	//returns Shuffled deck.
	private ArrayList<Card> getShuffledDeck(){
		
		ArrayList<Card> deck = new ArrayList<Card>();
	
		for(CardNames cardName : CardNames.values()) {
			for(CardColor cardColor: CardColor.values()) {
				Card card = new Card();
				card.setName(cardName);
				card.setPoints(cardName.getPoints());
				card.setColor(cardColor);
				deck.add(card);
			}			
		}		
		Collections.shuffle(deck);		
		return deck;
	}	
	
	//Returns a list of cards with same color like the played card. 
	private ArrayList<Card> getWantedColorCards(Player player, Card playedCard){
		ArrayList<Card> wantedColorCards = new ArrayList<Card>();
		
		for(Card card : player.getCards()) {
			if(playedCard.getColor() == card.getColor()) {
				wantedColorCards.add(card);
			}
		}
		
		if(!wantedColorCards.isEmpty()) {
			wantedColorCards.sort((Comparator.comparing(Card::getPoints)));	
		}		
		return wantedColorCards;
	}
	
	//Returns a list of cards with the trump color.
	private ArrayList<Card> getTrumpColorCards(Player player){
		ArrayList<Card> trumpColorCards = new ArrayList<Card>();
		
		for(Card card : player.getCards()) {
			if(card.getColor() == trumpCard.getColor()) {
				trumpColorCards.add(card);
			}
		}
		
		if(!trumpColorCards.isEmpty()) {
			trumpColorCards.sort((Comparator.comparing(Card::getPoints)));		
		}
		return trumpColorCards;
	}
	    
	//Returns a list of all cards except the trump color cards.
	private ArrayList<Card> getNoTrumpColorCards(Player player){
		ArrayList<Card> noTrumpColorCards = new ArrayList<Card>();
		
		for(Card card : player.getCards()) {
			if(card.getColor() != trumpCard.getColor()) {
				noTrumpColorCards.add(card);
			}
		}
		
		if(!noTrumpColorCards.isEmpty()) {
			noTrumpColorCards.sort((Comparator.comparing(Card::getPoints)));		
		}
		return noTrumpColorCards;
	}
	
	//Checks if the card that User Player gives matches the game rules.
	private boolean isTheCardAllowed(Card card) {
		if(lead == Lead.COMPUTER && closed == true) {
			if(card.getColor() == computerPlayer.getPlayedCard().getColor()){
				return true;
			}
			
			if(card.getColor() == trumpCard.getColor() && getWantedColorCards(userPlayer, computerPlayer.getPlayedCard()).isEmpty()) {
				return true;
			}
			
			if(card.getColor() != trumpCard.getColor() && getWantedColorCards(userPlayer, computerPlayer.getPlayedCard()).isEmpty() && getTrumpColorCards(userPlayer).isEmpty()) {
				return true;
			}
			
			return false;
		}
		
		return true;
		
	}
	//Checks for pair and trump pair
	private int checkForPair(Card queenCard) {
		if (deck.size() < 11 && userPlayer.getScore()>0 && lead == Lead.PLAYER) {
			for (Card card : userPlayer.getCards()) {
				if (card.getName() == CardNames.KING && card.getColor() == queenCard.getColor()) {
					if (card.getColor() == trumpCard.getColor()) {
						return 40;
					}
					return 20;
				}
			} 
		}
		return 0;
	}
	
}
