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
import enums.CardName;
import enums.Lead;
import enums.Winner;
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
		protected Winner winner;
		protected boolean showComputerCards = false;
		protected ArrayList<Button> playerCardsButtons = new ArrayList<Button>();
		protected ArrayList<Button> computerCardsButtons = new ArrayList<Button>();
		protected Button btnDeckCards;
		protected Button btnTrumpCard;
		protected Button btnPlayedCardComputer;
		protected Button btnPlayedCardPlayer;
		protected Label lblScoreComputer;
		protected Label lblScorePlayer;
		
		

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
		
		createPlayerCards();
		createComputerCards();
				
		btnDeckCards = new Button(shell, SWT.NONE);
		btnDeckCards.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		btnDeckCards.setBounds(41, 196, 114, 149);
		btnDeckCards.setToolTipText("Closed");
		
		btnTrumpCard = new Button(shell, SWT.NONE);
		btnTrumpCard.setBounds(130, 196, 114, 149);
		btnTrumpCard.setToolTipText("Change card");
		
		btnPlayedCardComputer = new Button(shell, SWT.NONE);
		btnPlayedCardComputer.setBounds(348, 165, 114, 149);
		
		btnPlayedCardPlayer = new Button(shell, SWT.NONE);
		btnPlayedCardPlayer.setBounds(468, 247, 114, 149);
		
		lblScoreComputer = new Label(shell, SWT.NONE);
		lblScoreComputer.setBounds(641, 165, 55, 15);
		lblScoreComputer.setText("Score:");
		
		lblScorePlayer = new Label(shell, SWT.NONE);
		lblScorePlayer.setText("Score:");
		lblScorePlayer.setBounds(641, 381, 55, 15);
		
		Button btnShowComputerCards = new Button(shell, SWT.NONE);
		btnShowComputerCards.setBounds(641, 350, 120, 25);
		btnShowComputerCards.setText("Show computer cards");		
		
		Button btnNewGame = new Button(shell, SWT.NONE);
		btnNewGame.setText("New Game");
		btnNewGame.setBounds(641, 320, 120, 25);
		
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {	
				startNewGame();				
			}
		});
		
		//Create listeners on player cards buttons.
		//What happens when you click a player card
		for (int i = 0; i < playerCardsButtons.size(); i++) {
			final int index = i;
			playerCardsButtons.get(index).addMouseListener(new MouseAdapter() {
				@Override
				public  void mouseDown(MouseEvent e) {
					playerCardClick(index);									
				}				
			});
		}
		
		btnPlayedCardPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {	
				playedCard();				
			}			
		});
		
		//Change with a nine trump card
		btnTrumpCard.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {
				changeTrumpCard();				
			}
		});
		
		//Close
		btnDeckCards.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {
				closed();				
			}
		});
		
		btnShowComputerCards.addMouseListener(new MouseAdapter() {
			@Override
			public  void mouseDown(MouseEvent e) {				
				showComputerCards(btnShowComputerCards);								
			}
		});		
	}
	
	/*
	  Content methods
	 */
	
	private void createPlayerCards(){
		//Creating Player Cards buttons
				for (int i = 40; i <= 640; i+=120) {
					Button button = new Button(shell, SWT.NONE);		
					button.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
					button.setBounds(i, 400, 115, 150);
					playerCardsButtons.add(button);			
				}
	}
	
	private void createComputerCards() {
		//Creating Computer Cards buttons
				for (int i = 40; i <= 640; i+=120) {
					Button button = new Button(shell, SWT.NONE);		
					button.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
					button.setBounds(i, 10, 115, 150);
					computerCardsButtons.add(button);			
				}
	}
	
	private void playerCardClick(int index) {
		if(!wait) {
			if( userPlayer.getCards().size() >= index+1) {				//can't click on empty card
				if(isTheCardAllowed(userPlayer.getCards().get(index))) {
					wait = true;
					//checking for marriage								
					checkForMarriage(userPlayer.getCards().get(index));		
					
					userPlayer.setPlayedCard(userPlayer.getCards().get(index));
					userPlayer.getCards().remove(index);							
					//when player leads computer must reply with card.
					if(lead == Lead.PLAYER) {
						Card replyCard = computerReplyCard();									
						computerPlayer.setPlayedCard(replyCard);
						computerPlayer.getCards().remove(replyCard);
					}					
					render();								
				}								
			}						
		}	
	}

	private void playedCard(){
		if (wait) {										
			trickBattle();
			endGameCheck();
			//when computer leads computer must play card.
			if (lead == Lead.COMPUTER) {
				computerPlayer.setPlayedCard(computerPlayCard());
				computerPlayer.getCards().remove(computerPlayer.getPlayedCard());
			}						
			wait = false;
			render();					
		}
	}
	
	private void changeTrumpCard() {
		//If player has trump nine card, he can change it from the deck
		if (deck.size() > 1 && deck.size() < 11 && closed == false && lead == Lead.PLAYER) {					
			if (!getTrumpColorCards(userPlayer).isEmpty()
					&& getTrumpColorCards(userPlayer).get(0).getName() == CardName.NINE) {
				userPlayer.getCards().add(trumpCard);
				trumpCard = getTrumpColorCards(userPlayer).get(0);
				userPlayer.getCards().remove(getTrumpColorCards(userPlayer).get(0));
				render();			
			} 
		}
	}
	
	private void closed() {
		if(lead == Lead.PLAYER && deck.size() > 1 && deck.size() < 11) {					
			closed = true;
			render();
		}
	}
		
	private void showComputerCards(Button btnShowComputerCards) {
		showComputerCards = (showComputerCards == true) ? false : true;
		String btnShowComputerCadsText = (showComputerCards == true) ? "Hide computer cards" : "Show computer cards";
		btnShowComputerCards.setText(btnShowComputerCadsText);				
		render();
	}
	
	/*
	  New game end game methods
	 */
	
	private void startNewGame() {		
		deck = getShuffledDeck();			
		userPlayer.setCards(getPlayingCards());
		userPlayer.setScore(0);		
		computerPlayer.setCards(getPlayingCards());
		computerPlayer.setScore(0);		
		lead = Lead.PLAYER;			//Every new game human player leads		
		trumpCard = getTrumpCard();	
		computerPlayer.setPlayedCard(null);
		userPlayer.setPlayedCard(null);
		closed = false;
		wait = false;
		render();		
	}
	
	private void endGame(Winner winner) {		
		EndGameDialog dialog = new EndGameDialog(shell, 1, userPlayer.getScore(), computerPlayer.getScore(), winner);
		dialog.open();
		shell.close();
		Game66 window = new Game66();
		window.open();
	}
		
	/*
	  Computer playing cards logic
	 */
	
	private Card computerReplyCard() {	
		
		ArrayList<Card> wantedColorCards = getWantedColorCards(computerPlayer, userPlayer.getPlayedCard());
		
		ArrayList<Card> trumpColorCards = getTrumpColorCards(computerPlayer);
		
		ArrayList<Card> noTrumpColorCards = getNoTrumpColorCards(computerPlayer);
		
		if(!closed) {			
			//if player's card is not trump card and can be taken
			if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints()>userPlayer.getPlayedCard().getPoints()) {
				for (Card card : wantedColorCards) {
					if(card.getPoints()>userPlayer.getPlayedCard().getPoints()) {
						return card;
					}			
				}					
			}
			//if player's card is not trump card and is higher card
			if(userPlayer.getPlayedCard().getPoints()>= 10 && userPlayer.getPlayedCard().getColor()!=trumpCard.getColor()) {
				//if there is any trump cards in the hand
				if(!trumpColorCards.isEmpty()) {
					return trumpColorCards.get(0);
				}			
			}		
			//if player's card is trump card
			if(userPlayer.getPlayedCard().getColor()==trumpCard.getColor()) {
				//if player's card is ten
				if(userPlayer.getPlayedCard().getPoints() == 10) {
					//if there is an Ace
					if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints() ==11) {
						return wantedColorCards.get(wantedColorCards.size()-1);						
					}					
				}			
			}			
		}
		
		if(closed) {
			//if the card can be taken
			if(!wantedColorCards.isEmpty() && wantedColorCards.get(wantedColorCards.size()-1).getPoints()>userPlayer.getPlayedCard().getPoints()) {
				for (Card card : wantedColorCards) {
					if(card.getPoints()>userPlayer.getPlayedCard().getPoints()) {
						return card;
					}			
				}				
			}			
			//if there is lower card with the same color
			if(!wantedColorCards.isEmpty()) {	
				return wantedColorCards.get(0);								
			}
			//Using a trump card 
			if(!trumpColorCards.isEmpty()) {
				return trumpColorCards.get(0);
			}		
		}	
		//Player wins trick
				
		return noTrumpColorCards.get(0);
		
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
		//if card are not the same color
		if(userPlayer.getPlayedCard().getColor() != computerPlayer.getPlayedCard().getColor()) {
			//If there is no trump card
			if(computerPlayer.getPlayedCard().getColor()!=trumpCard.getColor() && userPlayer.getPlayedCard().getColor()!=trumpCard.getColor()) {
				if(lead == Lead.COMPUTER) {
					computerWins(trickScore);
				}
				else {
					playerWins(trickScore);
				}			
			}
			//If computer card is a trump card
			if(computerPlayer.getPlayedCard().getColor()==trumpCard.getColor() && userPlayer.getPlayedCard().getColor()!=trumpCard.getColor()){
				computerWins(trickScore);
			}
			
			//If player card is a trump card
			if(computerPlayer.getPlayedCard().getColor()!=trumpCard.getColor() && userPlayer.getPlayedCard().getColor()==trumpCard.getColor()){
				playerWins(trickScore);
			}
		}
		userPlayer.setPlayedCard(null);
		computerPlayer.setPlayedCard(null);
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
	
	
	/*
	 Card Functions
	 */
	
	
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
	
		for(CardName cardName : CardName.values()) {
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
	
		
	
	//Help functions
	
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
		
		//Checks for marriage queen and king from the same color
		private void checkForMarriage(Card chekedCard) {
			if(chekedCard.getName() == CardName.QUEEN) {
				int marriagePoints = 0;
				if (deck.size() < 11 && userPlayer.getScore()>0 && lead == Lead.PLAYER) {
					for (Card card : userPlayer.getCards()) {
						if (card.getName() == CardName.KING && card.getColor() == chekedCard.getColor()) {
							marriagePoints = 20;
						}						
						if (card.getName() == CardName.KING && card.getColor() == trumpCard.getColor() && card.getColor() == chekedCard.getColor()) {
							marriagePoints = 40;
						}						
					}
				}
				
				if(marriagePoints>0) {
					MarriageDialog marriageDialog = new MarriageDialog(shell, 1, marriagePoints);
					marriageDialog.open();
					userPlayer.setScore(userPlayer.getScore() + marriagePoints);
					lblScorePlayer.setText("Score:" + userPlayer.getScore());
					endGameCheck();
				}
			}	
		}
	
	private void endGameCheck() {
		if (lead == Lead.COMPUTER && computerPlayer.getScore() >= 66) {
			endGame(Winner.COMPUTER_WINNER);			
		}
		
		if(lead == Lead.PLAYER && userPlayer.getScore()>=66) {
			endGame(Winner.PLAYER_WINNER);
		}
		
		if (userPlayer.getCards().isEmpty() && computerPlayer.getCards().isEmpty() && deck.isEmpty()) {
			if(lead == Lead.COMPUTER) {
				endGame(Winner.COMPUTER_WINNER);
			}
			else{
				endGame(Winner.PLAYER_WINNER);
			}			
		}
		
		if (userPlayer.getCards().isEmpty() && computerPlayer.getCards().isEmpty() && !deck.isEmpty()) {
			if(userPlayer.getScore()>=66) {
				endGame(Winner.PLAYER_WINNER);
			}
			else{
				endGame(Winner.COMPUTER_WINNER);
			}			
		}	
	}
	
	
	//Refresh the view of all the GUI elements.
	private void render() {
		if (showComputerCards == true) {

			for (int i = 0; i < 6; i++) {
				try {
					computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
							computerPlayer.getCards().get(i).getCardImagePath()));
				} catch (Exception e1) {//if the cards are less than six the catch will set the rest to null
					computerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
				}
			}
		}
		for (int i = 0; i < 6; i++) {
			try {
				playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class,
						userPlayer.getCards().get(i).getCardImagePath()));
			} catch (Exception e1) {//if the cards are less than six the catch will set the rest to null
				playerCardsButtons.get(i).setImage(SWTResourceManager.getImage(Game66.class, null));
			}
		}
				
		String btnPlayedCardComputerImagePath = (computerPlayer.getPlayedCard() == null) ? "null" : computerPlayer.getPlayedCard().getCardImagePath();
		btnPlayedCardComputer.setImage(SWTResourceManager.getImage(Game66.class, btnPlayedCardComputerImagePath));
		
		String btnPlayedCardPlayerImagePath = (userPlayer.getPlayedCard() == null) ? "null" : userPlayer.getPlayedCard().getCardImagePath();
		btnPlayedCardPlayer.setImage(SWTResourceManager.getImage(Game66.class, btnPlayedCardPlayerImagePath));
		
		lblScoreComputer.setText("Score:" + computerPlayer.getScore());
		lblScorePlayer.setText("Score:" + userPlayer.getScore());
		
		btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, trumpCard.getCardImagePath()));
		btnDeckCards.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
		//Change the trump card only to color image
		if (closed == true) {
			if(deck.isEmpty()) {
				btnDeckCards.setImage(SWTResourceManager.getImage(Game66.class, trumpCard.getColorImagePath()));
				btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, null));	
			}
			else {
				btnDeckCards.setImage(SWTResourceManager.getImage(Game66.class, trumpCard.getCardImagePath()));
				btnTrumpCard.setImage(SWTResourceManager.getImage(Game66.class, "/resources/drawable-hdpi/card_back.png"));
			}
			
		}
		
		
	}
	
}
