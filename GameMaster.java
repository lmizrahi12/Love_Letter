import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.Stack;

public class GameMaster {

	Card[] cards = new Card[16];
	Stack<Card> deck = new Stack<Card>();
	ArrayList<Player> players = new ArrayList<Player>(4);
	Card discard_card;
	int player_turn;
	
	public GameMaster(){
			for (int i = 0; i < 5; i++) {
				this.cards[i] = new Card(1, "Guard", "Guard Effect");
			}
			
			for (int i = 5; i < 7; i++) {
				this.cards[i] = new Card(2, "Priest", "Priest Effect");
			}
			
			for (int i = 7; i < 9; i++) {
				this.cards[i] = new Card(3, "Baron", "Baron Effect");
			}
			
			for (int i = 9; i < 11; i++) {
				this.cards[i] = new Card(4, "Handmaiden", "Handmaiden Effect");
			}
			
			for (int i = 11; i < 13; i++) {
				this.cards[i] = new Card(5, "Prince", "Prince Effect");
			}
			
			this.cards[13] = new Card(6, "King", "King Effect");
			this.cards[14] = new Card(7, "Countess", "Countess Effect");
			this.cards[15] = new Card(8, "Princess", "Princess Effect");
			
			for (int i = 0; i < cards.length; i++) {
				this.deck.push(cards[i]);
			}
			
			for (int i = 0; i < 4; i++) {
				this.players.add(new Player("random"));
			}
			
			
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public void discardTopCard(){
		this.discard_card = this.deck.pop();
	}
	
	public void deal(){
		for(int i = 0; i < this.players.size(); i++)
		{
			this.players.get(i).hand.add(this.deck.pop());
		}
		
//		System.out.println("Cards Dealt");
	}
	
	public void dealCard(int player_num){
		Card card = this.deck.pop();
		this.players.get(player_num).hand.add(card);
	}
	
	public void dealDiscardCard(int player_num){
		Card card = this.discard_card;
		this.players.get(player_num).hand.add(card);
	}
	
	public void chooseStartPlayer(){
		Random rand = new Random();
		this.player_turn = rand.nextInt(4);
	}
	
	public void endTurn(){
		player_turn++;
		player_turn = player_turn % 4;
	}
	
	public void printPlayerHands(){
		for (int i = 0; i < this.players.size(); i++) {
			System.out.println("\tPlayer " + i + ":");
			
			if (this.players.get(i).getIs_out()) {
				System.out.println("\t\tELIMINATED!");
			}
			else{
				System.out.println("\t\tHand:");
				for (int j = 0; j < this.players.get(i).hand.size(); j++) {
					System.out.println("\t\t\t" + this.players.get(i).hand.get(j).getName() + ", " + this.players.get(i).hand.get(j).getValue());
				}
			}
			
			System.out.println("\t\tDiscard Pile:");
			if (this.players.get(i).discard_pile.size() == 0) {
				System.out.println("\t\t\tempty");
			}
			for (int k = 0; k < this.players.get(i).discard_pile.size(); k++) {
				System.out.println("\t\t\t" + this.players.get(i).discard_pile.get(k).getName() + ", " + this.players.get(i).discard_pile.get(k).getValue());
			}
		}
	}

	public int getDeckSize(){
		return this.deck.size();
	}
	
	public void printDeck(){
		while(this.getDeckSize() > 0){
			Card card = deck.pop();
			System.out.println(card.getValue() + ", " + card.getName() + ", " + card.getEffect());
		}
	}
	
	public int[] getCardsInPlay(int player_num){
		int[] cards_left = {0, 0, 2, 2, 2, 2, 1, 1, 1};
		
		for (int i = 0; i < this.players.size(); i++) {
			for (int j = 0; j < this.players.get(i).discard_pile.size(); j++) {
				cards_left[ this.players.get(i).discard_pile.get(j).getValue() ] --;
			}
		}
		
		for (int i = 0; i < this.players.get(player_num).hand.size(); i++) {
			cards_left[ this.players.get(player_num).hand.get(i).getValue() ] --;
		}
		
		return cards_left;
	}
	
	public ArrayList<String> getAvailableMoves(ArrayList<Card> playerHand, ArrayList<Player> players, int playerNum){
		ArrayList<String> availableMoves = new ArrayList<String>();
		
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
				
//				for(int card_num = 2; card_num <= 8; card_num++)
//				{	
//					availableMoves.add(String.valueOf(1) + "," + player_num + "," + card_num);
//				}
				
				int[] cards_left = getCardsInPlay(playerNum);
				
				for (int i = 2; i < cards_left.length; i++) {
					if (cards_left[i] != 0) {
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
	
	public void processCard(int playerValue, int playedCardValue, int targetPlayerValue, int targetCardValue, boolean PRINT){
		
		if(playedCardValue == 1)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 1 to Player " + targetPlayerValue + " and guessed a " + targetCardValue);
			
			if(this.players.get(targetPlayerValue).handContains(targetCardValue))
			{
				Card card = this.players.get(targetPlayerValue).hand.get(0);
				this.players.get(targetPlayerValue).hand.remove(0);
				this.players.get(targetPlayerValue).discard_pile.add(card);
				
				this.players.get(targetPlayerValue).Eliminate(PRINT);
			}
		}
		else if(playedCardValue == 2)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 2 to Player " + targetPlayerValue);
			
			int oppCardValue = this.players.get(targetPlayerValue).hand.get(0).getValue();
			this.players.get(playerValue).updateMemory(targetPlayerValue, oppCardValue);
		}
		else if(playedCardValue == 3)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 3 to Player " + targetPlayerValue);
			
			if(this.players.get(playerValue).hand.get(0).getValue() < this.players.get(targetPlayerValue).hand.get(0).getValue() )
			{
				if(PRINT)
					System.out.println("\tPlayer " + playerValue + " lost");
				Card card = this.players.get(playerValue).hand.get(0);
				this.players.get(playerValue).hand.remove(0);
				this.players.get(playerValue).discard_pile.add(card);
				
				this.players.get(playerValue).Eliminate(PRINT);
			}
			else if(this.players.get(playerValue).hand.get(0).getValue() > this.players.get(targetPlayerValue).hand.get(0).getValue() )
			{
				if(PRINT)
					System.out.println("\tPlayer " + targetPlayerValue + " lost");
				Card card = this.players.get(targetPlayerValue).hand.get(0);
				this.players.get(targetPlayerValue).hand.remove(0);
				this.players.get(targetPlayerValue).discard_pile.add(card);
				
				this.players.get(targetPlayerValue).Eliminate(PRINT);
			}
			else
				if(PRINT)
					System.out.println("\tThere was a tie. Nothing Happens");
		}
		else if(playedCardValue == 4)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 4");
			this.players.get(playerValue).Protect(PRINT);
		}
		else if(playedCardValue == 5)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 5 to Player " + targetPlayerValue);
			Card card = this.players.get(targetPlayerValue).hand.get(0);
			this.players.get(targetPlayerValue).hand.remove(0);
			this.players.get(targetPlayerValue).discard_pile.add(card);
			
			if(card.getValue() == 8){
				this.players.get(targetPlayerValue).Eliminate(PRINT);
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
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 6 to Player " + targetPlayerValue);
			Card card = this.players.get(playerValue).hand.get(0);
			this.players.get(playerValue).hand.remove(0);
			
			this.players.get(playerValue).hand.add( this.players.get(targetPlayerValue).hand.get(0) );
			
			this.players.get(targetPlayerValue).hand.remove(0);
			
			this.players.get(targetPlayerValue).hand.add(card);
			
			int value = this.players.get(playerValue).hand.get(0).getValue();
			this.players.get(playerValue).updateMemory(targetPlayerValue, value);
			this.players.get(playerValue).swapMemory(playerValue, targetPlayerValue);
		}
		else if(playedCardValue == 7)
		{
			if(PRINT)
				System.out.println("Player " + playerValue + " played a 7");
		}
		else if(playedCardValue == 8)
		{
			this.players.get(targetPlayerValue).Eliminate(PRINT);
		}
	}
	
	public void printMemory(){
		System.out.println("MEMORY:");
		for (int i = 0; i < this.players.size(); i++) {
			this.printArray(this.players.get(i).getMemory());
		}
	}

	public boolean is_onePlayerLeft() {
		int players_not_out = 0;
		for (int i = 0; i < this.players.size(); i++) {
			if(players.get(i) != null){
				if(players.get(i).getIs_out() == false){
					players_not_out++;
				}
			}
		}
		if(players_not_out == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void print_available_moves(ArrayList<String> moves)
	{
		System.out.println("Available moves:");
		System.out.println("\t<played_card_value>, <target_player>, <target_card_value>");
		for (int i = 0; i < moves.size(); i++) {
			System.out.println("\t" + moves.get(i));
		}
	}
	
	public int getWinningPlayer(boolean PRINT){
		
		int winning_player_value = 0;
		int winning_player = -1;
		
		if (this.is_onePlayerLeft()) {
			if(PRINT)
				System.out.println("Only 1 player left");
			for (int j = 0; j < this.players.size(); j++) {
				if (!this.players.get(j).getIs_out()) {
					winning_player = j;
				}
			}
			if(PRINT)
				System.out.println("PLAYER " + winning_player + " IS LAST MAN STANDING!!");
		}
		
		else if (this.deck.isEmpty()) {
			if(PRINT)
				System.out.println("Deck ran out of cards");
			for (int j = 0; j < this.players.size(); j++) {
				if (!this.players.get(j).getIs_out()) {
					int player_hand_value = this.players.get(j).hand.get(0).getValue();
					if (player_hand_value > winning_player_value) {
						winning_player_value = player_hand_value;
						winning_player = j;
					}
				}
			}
			if(PRINT)
				System.out.println("PLAYER " + winning_player + " WINS BY HIGHEST VALUE!!");
		}
		
		return winning_player;
	}

	public void discardCard(int player_value, int r) {
//		System.out.println("&&&&&&&&&&Player " + player_value + "'s hand size: " + this.players.get(player_value).hand.size());
		Card card = this.players.get(player_value).hand.get(r);
		this.players.get(player_value).hand.remove(r);
		this.players.get(player_value).discard_pile.add(card);
	}
	
	public void printArray(int[] arr){
		System.out.print("[" + arr[0]);
		for (int i = 1; i < arr.length; i++) {
			System.out.print(", " + arr[i]);
		}
		System.out.print("]\n");
	}
	
	
	
	
	
	
	
	
	
	public GameMaster cloneKnownInfo(){
//		Stack<Card> deck = new Stack<Card>();
//		ArrayList<Player> players = new ArrayList<Player>(4);
//		Card discard_card;
		
		GameMaster gm2 = new GameMaster();
		
		gm2.deck.removeAllElements();
		gm2.players.clear();
		
		for (int i = 0; i < this.players.size(); i++) {
			gm2.players.add( this.players.get(i).cloneKnownInfo() );
		}
		
		for (int i = 0; i < this.players.get(0).hand.size(); i++) {
			gm2.players.get(0).hand.add( this.players.get(0).hand.get(i).clone() );
		}
		
		gm2.player_turn = this.player_turn;
		
		return gm2;
	}
	
	public int simulate(String move){
		Random rand = new Random();
		
		int i = this.player_turn;
		
		String[] str = move.split(",");														//get chosen move and split it into 3 int tokens
		int played_card_value = Integer.parseInt(str[0]);
		int target_player_value = Integer.parseInt(str[1]);
		int target_card_value = Integer.parseInt(str[2]);
		
		int played_card_index = this.players.get(i).getIndexOfCard(played_card_value);				//get index of chosen card
		
		this.discardCard(i, played_card_index);														//remove the selected card from hand and put in discard pile
		
		this.processCard(i, played_card_value, target_player_value, target_card_value, false);				//process the move chosen
		
		this.endTurn();
			
			
		while(!this.is_onePlayerLeft() && !this.deck.isEmpty()){
			i = this.player_turn;
			
			if (!this.players.get(i).getIs_out()) {
//				System.out.println("Player " + i + "'s turn");
				this.players.get(i).Unprotect(); 																	//set protection to false at start of every players turn
//				System.out.println();
				
				this.dealCard(i);																					//deal single card to player i
				
//				System.out.println("CURRENT STATE:");
//				this.printPlayerHands();																			//print current state
//				System.out.println();
				
				ArrayList<String> moves = this.getAvailableMoves(this.players.get(i).hand, this.players, i);			//return list of all available moves
//				this.print_available_moves(moves);																//print available moves
				
				if (moves.size() > 0) {
					int r = rand.nextInt(moves.size());
//					System.out.println("Chose random move: " + r);
					
					str = moves.get(r).split(",");														//get chosen move and split it into 3 int tokens
					played_card_value = Integer.parseInt(str[0]);
					target_player_value = Integer.parseInt(str[1]);
					target_card_value = Integer.parseInt(str[2]);
					
					played_card_index = this.players.get(i).getIndexOfCard(played_card_value);				//get index of chosen card
					
					this.discardCard(i, played_card_index);														//remove the selected card from hand and put in discard pile
					
					this.processCard(i, played_card_value, target_player_value, target_card_value, false);				//process the move chosen
				
				}
				else{
					int r = rand.nextInt(2);
					this.discardCard(i, r);
//					System.out.println("No legal moves so just discarding card " + r + " with no effect");
				}
				
//				System.out.println();
//				System.out.println("NEW STATE");
//				this.printPlayerHands();																		//print new state after chosen move
//				System.out.println("########################################################");
			}
			
			this.endTurn();
			
		}
		
		int winning_player = this.getWinningPlayer(false);															//get the winning player
		
		return winning_player;
	}

	public int MCTS(ArrayList<String> moves, int  num_simulations) {
		Random rand = new Random();
		int[] move_wins = new int[moves.size()];
		
		for(int m = 0; m < moves.size(); m++){
			move_wins[m] = 0;
			for (int i = 0; i < num_simulations; i++) {
				GameMaster simulated_gm = new GameMaster();
				simulated_gm = this.cloneKnownInfo();
				simulated_gm.getSimulatedDeck();
				simulated_gm.shuffle();
				simulated_gm.discardTopCard();
				if(simulated_gm.getDeckSize() > 0)
					simulated_gm.simulatedDeal();
				
				int winner = simulated_gm.simulate(moves.get(m));
				if (winner == 0) {
					//System.out.println("Winner is 0? : " + winner);
					move_wins[m]++;
				}
			}
		}
		
//		System.out.println("SIMULATED WINS: ");
//		this.printArray(move_wins);
		
		
		
//		int max_index = 0;
//		int max_value = move_wins[0];
//		for (int i = 0; i < move_wins.length; i++) {
//			if (move_wins[i] > max_value) {
//				max_index = i;
//				max_value = move_wins[i];
//			}
//		}
		
		IntSummaryStatistics stat = Arrays.stream(move_wins).summaryStatistics();
		int max = stat.getMax();
		//System.out.println("MAX: " + max);

		ArrayList<Integer> best_moves = new ArrayList<Integer>();
		for (int i = 0; i < move_wins.length; i++) {
			if (move_wins[i] == max) {
				best_moves.add(i);
			}
		}
		
		int max_index = best_moves.get( rand.nextInt(best_moves.size()) );
		
		return max_index;
	}
	
	public void getSimulatedDeck(){
		int[] cards_left = {0, 5, 2, 2, 2, 2, 1, 1, 1};
		
		for (int i = 0; i < this.players.get(0).hand.size(); i++) {
			cards_left[ this.players.get(0).hand.get(i).getValue() ]--;
		}
		
		for (int j = 0; j < this.players.size(); j++) {
			for (int k = 0; k < this.players.get(j).discard_pile.size(); k++) {
				cards_left[ this.players.get(j).discard_pile.get(k).getValue() ]--;
			}
		}
		
		for (int i = 0; i < cards_left[1]; i++) {
			this.deck.add( new Card(1, "Guard", "Guard Effect") );
		}
		
		for (int i = 0; i < cards_left[2]; i++) {
			this.deck.add( new Card(2, "Priest", "Priest Effect") );
		}
		
		for (int i = 0; i < cards_left[3]; i++) {
			this.deck.add( new Card(3, "Baron", "Baron Effect") );
		}
		
		for (int i = 0; i < cards_left[4]; i++) {
			this.deck.add( new Card(4, "Handmaiden", "Handmaiden Effect") );
		}
		
		for (int i = 0; i < cards_left[5]; i++) {
			this.deck.add( new Card(5, "Prince", "Prince Effect") );
		}
		
		if(cards_left[6] == 1){
			this.deck.add( new Card(6, "King", "King Effect") );
		}
		
		if(cards_left[7] == 1){
			this.deck.add( new Card(7, "Countess", "Countess Effect") );
		}
		
		if(cards_left[8] == 1){
			this.deck.add( new Card(8, "Princess", "Princess Effect") );
		}
		
	}
	
	public void simulatedDeal(){
		for(int i = 1; i < this.players.size(); i++)
		{
			if(!this.players.get(i).getIs_out())
				this.players.get(i).hand.add(this.deck.pop());
		}
		
		//System.out.println("***Simulated Cards Dealt");
	}
	

	

	

}
