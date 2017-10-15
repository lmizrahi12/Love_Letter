import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class GameStateModel {

	ArrayList<Player> players = new ArrayList<Player>(4);
	Stack<Card> deck = new Stack<Card>();
	Card removed_card;
	int turn_tracker;
	
	
	
	public GameStateModel() {
		fillDeck();
		shuffle();
		removeTopCard();
		deal();
		chooseStartingPlayer();
		
		
		
		
	}

	public void knowThySelf() {
		Player self = players.get(turn_tracker);
		self.memory[turn_tracker] =  self.hand.get(0).getValue();
	}
	
	public void swapKnowledge(int p1, int p2){
		
		for (int i = 0; i < players.size(); i++) {
			int tmp = players.get(i).memory[p1];
			players.get(i).memory[p1] = players.get(i).memory[p2];
			players.get(i).memory[p2] = tmp;
		}
		
	}
	
	public boolean handContains(int cardValue){
		ArrayList<Card> player_hand = this.players.get(turn_tracker).hand;
		
		for (int i = 0; i < player_hand.size(); i++) {
			if (player_hand.get(i).getValue() == cardValue) {
				return true;
			}
		}
		return false;
	}
	
	public int[] cardsInPlay() {
		
		int[] cards_left = {0, 0, 2, 2, 2, 2, 1, 1, 1};
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < players.get(i).discard_pile.size(); j++) {
				cards_left[players.get(i).discard_pile.get(j).getValue()] --;
			}
		}
		
		return cards_left;
		
	}
	
	public ArrayList<String> getAvailableMoves(){
		
		ArrayList<String> availableMoves = new ArrayList<String>();
		int playerNum = turn_tracker;
		
		
		if ( players.get(playerNum).handContains(7) && ( players.get(playerNum).handContains(6) || players.get(playerNum).handContains(5) ) ) {
			availableMoves.add(String.valueOf(7) + "," + playerNum + "," + String.valueOf(0));
			return availableMoves;
		}
		
		if (players.get(playerNum).handContains(1)) {
			for(int player_num = 0; player_num < players.size(); player_num++)
			{
				if(player_num == playerNum || players.get(player_num).getIs_out() || players.get(player_num).getIs_protected() )
				{
					continue;
				}
				
				int[] cards_in_play = cardsInPlay();
				for(int i = 0; i < cards_in_play.length; i++)
				{	
					if (cards_in_play[i] != 0) {
						availableMoves.add(String.valueOf(1) + "," + player_num + "," + i);
					}
					
				}
				
			}
		}
		
		if (players.get(playerNum).handContains(2)) {
			for(int player_num = 0; player_num < players.size(); player_num++)
			{
				if(player_num == playerNum || players.get(player_num).getIs_out() || players.get(player_num).getIs_protected() )
				{
					continue;
				}
				
				availableMoves.add(String.valueOf(2) + "," + player_num + "," + String.valueOf(0));
			}
		}
		
		if (players.get(playerNum).handContains(3)) {
			for(int player_num = 0; player_num < players.size(); player_num++)
			{
				if(player_num == playerNum || players.get(player_num).getIs_out() || players.get(player_num).getIs_protected() )
				{
					continue;
				}
				
				availableMoves.add(String.valueOf(3) + "," + player_num + "," + String.valueOf(0));
			}
		}
		
		if (players.get(playerNum).handContains(4)) {
			availableMoves.add(String.valueOf(4) + "," + playerNum + "," + String.valueOf(0));
		}
		
		if (players.get(playerNum).handContains(5)) {
			for(int player_num = 0; player_num < players.size(); player_num++)
			{
				if(players.get(player_num).getIs_out() || players.get(player_num).getIs_protected() )
				{
					continue;
				}
				
				availableMoves.add(String.valueOf(5) + "," + player_num + "," + String.valueOf(0));
			}
		}
		
		if (players.get(playerNum).handContains(6)) {
			for(int player_num = 0; player_num < players.size(); player_num++)
			{
				if(player_num == playerNum || players.get(player_num).getIs_out() || players.get(player_num).getIs_protected() )
				{
					continue;
				}
				
				availableMoves.add(String.valueOf(6) + "," + player_num + "," + String.valueOf(0));
			}
		}
		
		if(players.get(playerNum).handContains(7)) {
			availableMoves.add(String.valueOf(7) + "," + playerNum + "," + String.valueOf(0));
		}
		
		return availableMoves;
	}
	
	public void processCard(int playedCardValue, int targetPlayerValue, int targetCardValue){
		
		int playerValue = turn_tracker;
		
		if(playedCardValue == 1)
		{
			System.out.println("Player " + playerValue + " played a 1 to Player " + targetPlayerValue + " and guessed a " + targetCardValue);
			if(this.players.get(targetPlayerValue).handContains(targetCardValue))
			{
				Card card = this.players.get(targetPlayerValue).hand.get(0);
				this.players.get(targetPlayerValue).hand.remove(0);
				this.players.get(targetPlayerValue).discard_pile.add(card);
				
				this.players.get(targetPlayerValue).Eliminate();
			}
		}
		else if(playedCardValue == 2)
		{
			// update knowlage to what you have seen
			
			this.players.get(playerValue).memory[targetPlayerValue] = this.players.get(targetPlayerValue).hand.get(0).getValue();
			
			System.out.println("Player " + playerValue + " played a 2 to Player " + targetPlayerValue);
		}
		else if(playedCardValue == 3)
		{
			System.out.println("Player " + playerValue + " played a 3 to Player " + targetPlayerValue);
			
			if(this.players.get(playerValue).hand.get(0).getValue() < this.players.get(targetPlayerValue).hand.get(0).getValue() )
			{
				System.out.println("\tPlayer " + playerValue + " lost");
				Card card = this.players.get(playerValue).hand.get(0);
				this.players.get(playerValue).hand.remove(0);
				this.players.get(playerValue).discard_pile.add(card);
				
				this.players.get(playerValue).Eliminate();
			}
			else if(this.players.get(playerValue).hand.get(0).getValue() > this.players.get(targetPlayerValue).hand.get(0).getValue() )
			{
				System.out.println("\tPlayer " + targetPlayerValue + " lost");
				Card card = this.players.get(targetPlayerValue).hand.get(0);
				this.players.get(targetPlayerValue).hand.remove(0);
				this.players.get(targetPlayerValue).discard_pile.add(card);
				
				this.players.get(targetPlayerValue).Eliminate();
			}
			else
				System.out.println("\tThere was a tie. Nothing Happens");
		}
		else if(playedCardValue == 4)
		{
			System.out.println("Player " + playerValue + " played a 4");
			this.players.get(playerValue).Protect();
		}
		else if(playedCardValue == 5)
		{
			System.out.println("Player " + playerValue + " played a 5 to Player " + targetPlayerValue);
			Card card = this.players.get(targetPlayerValue).hand.get(0);
			this.players.get(targetPlayerValue).hand.remove(0);
			this.players.get(targetPlayerValue).discard_pile.add(card);
			
			if(card.getValue() == 8){
				this.players.get(targetPlayerValue).Eliminate();
			}
			else{
				if (this.getDeckSize() > 0) {
					this.dealCard(targetPlayerValue);
				}
				else{
					this.dealDiscardCard(targetPlayerValue);
				}
			}
		}
		else if(playedCardValue == 6)
		{
			//order
			
			// * discard 6, update memory, * colne-remove card from hand, *steal card , *give them old card, swap_knowlage(p1,p2)
			
			
			
			
			// update memory of own card to remaining card
			
			knowThySelf();
			
			
			
			
			System.out.println("Player " + playerValue + " played a 6 to Player " + targetPlayerValue);
			Card card = this.players.get(playerValue).hand.get(0);
			this.players.get(playerValue).hand.remove(0);
			
			this.players.get(playerValue).hand.add( this.players.get(targetPlayerValue).hand.get(0) );
			
			this.players.get(targetPlayerValue).hand.remove(0);
			
			this.players.get(targetPlayerValue).hand.add(card);

			// update knowlage that swap happened
			swapKnowledge(playerValue, targetPlayerValue);
		
		}
		else if(playedCardValue == 7)
		{
			System.out.println("Player " + playerValue + " played a 7");
		}
		else if(playedCardValue == 8)
		{
			this.players.get(targetPlayerValue).Eliminate();
		}
	}

	
	public void fillDeck() {
		
		for (int i = 0; i < 5; i++) {
			this.deck.push(new Card(1, "Guard", "Guard Effect"));
		}
		
		for (int i = 5; i < 7; i++) {
			this.deck.push(new Card(2, "Priest", "Priest Effect"));
		}
		
		for (int i = 7; i < 9; i++) {
			this.deck.push(new Card(3, "Baron", "Baron Effect"));
		}
		
		for (int i = 9; i < 11; i++) {
			this.deck.push(new Card(4, "Handmaiden", "Handmaiden Effect"));
		}
		
		for (int i = 11; i < 13; i++) {
			this.deck.push(new Card(5, "Prince", "Prince Effect"));
		}
		
		this.deck.push(new Card(6, "King", "King Effect"));
		this.deck.push(new Card(7, "Countess", "Countess Effect"));
		this.deck.push(new Card(8, "Princess", "Princess Effect"));
		
	}
	
	public void shuffle(){
		
		Collections.shuffle(this.deck);
		
	}
	
	public void removeTopCard() {
		
		this.removed_card = this.deck.pop();
		
	}
	
	public void deal(){
		
		for(int i = 0; i < this.players.size(); i++)
		{
			this.players.get(i).hand.add(this.deck.pop());
		}
		
	}
	
	public void chooseStartingPlayer() {
		
		Random rand = new Random();
		int r = rand.nextInt(4);
		this.turn_tracker = r;
		
	}
	
	public int getDeckSize(){
		
		return this.deck.size();
		
	}
	
	public void dealCard(int player_num){
		
		Card card = this.deck.pop();
		this.players.get(player_num).hand.add(card);
		
	}
	
	public void dealDiscardCard(int player_num){
		
		Card card = this.removed_card;
		this.players.get(player_num).hand.add(card);
		
	}
	
	public void endTurn() {
		
		this.turn_tracker++;
		this.turn_tracker = this.turn_tracker % 4;
		
	}
	
	
	
	
}
