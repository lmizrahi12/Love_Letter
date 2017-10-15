import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Program {

	public static void main(String[] args) {
		int num_games = 1000;
		int num_simulations = 5000;
		boolean PRINT = false;
		int ave_turns = 0;
		int ave_moves = 0;
		int[] wins = {0,0,0,0};
		String fname = "results.csv";
		
		for (int j = 0; j < num_games; j++) {
			
			int current_turns = 0;
			int current_moves = 0;
			
		
					GameMaster gm = new GameMaster();
//					System.out.println("New GM, deck, discard card and Players made");
					Random rand = new Random();
										
					gm.chooseStartPlayer();
					
					gm.shuffle();
					
					gm.discardTopCard();
					
					gm.deal();																							//deal initial cards
					
//					System.out.println("----------Starting game----------");
						
			
					while(!gm.is_onePlayerLeft() && !gm.deck.isEmpty()){
						
						
						current_turns++;
						
						
						int i = gm.player_turn;
						
						if (!gm.players.get(i).getIs_out()) {
//							System.out.println("Player " + i + "'s turn");
							gm.players.get(i).Unprotect(); 																	//set protection to false at start of every players turn
//							System.out.println();
							
							//TODO fix memory!!
//							gm.printMemory();
							
							gm.dealCard(i);
							
//							System.out.println("CURRENT STATE:");
//							System.out.println("\tDeck: " + gm.getDeckSize() + '\n');
//							gm.printPlayerHands();																			//print current state
//							System.out.println();
							
							ArrayList<String> moves = gm.getAvailableMoves(gm.players.get(i).hand, gm.players, i);			//return list of all available moves
//							gm.print_available_moves(moves);																//print available moves
							
							
							current_moves += moves.size();
							
							
							if (moves.size() > 0) {
								
								int r = 0;
								
								//if player is not player 0
								if (i != 0) {
									r = rand.nextInt(moves.size());
//									System.out.println("Chose random move: " + r);
								}
								
								//else if player is player 0
								else{
//									System.out.println("<----------STARTING SIMULATIONS---------->");
									r = gm.MCTS(moves, num_simulations);
//									System.out.println("<----------FINISHED SIMULATIONS---------->");
//									System.out.println("Chose simulated decision: " + r);
								}
								
								String[] str = moves.get(r).split(",");														//get chosen move and split it into 3 int tokens
								int played_card_value = Integer.parseInt(str[0]);
								int target_player_value = Integer.parseInt(str[1]);
								int target_card_value = Integer.parseInt(str[2]);
								
								int played_card_index = gm.players.get(i).getIndexOfCard(played_card_value);				//get index of chosen card
								
								gm.discardCard(i, played_card_index);														//remove the selected card from hand and put in discard pile
								
								gm.players.get(i).knowThySelf(i);
								
								gm.processCard(i, played_card_value, target_player_value, target_card_value, PRINT);				//process the move chosen
							
							}
							else{
								int r = rand.nextInt(2);
								gm.discardCard(i, r);
//								System.out.println("No legal moves so just discarding card " + r + " with no effect");
							}
							
//							System.out.println();
//							System.out.println("NEW STATE");
//							System.out.println("\tDeck: " + gm.getDeckSize() + '\n');
//							gm.printPlayerHands();																		//print new state after chosen move
//							System.out.println("########################################################");
						}
						
						gm.endTurn();
						
					}
//					System.out.println("Discarded Card: " + gm.discard_card.getValue());
					int winning_player = gm.getWinningPlayer(PRINT);															//get the winning player
					
					
			ave_moves += (float)current_moves/(float)current_turns;
			ave_turns += current_turns;
			wins[winning_player] ++;
			
			System.out.println("Game: " + j + "/" + num_games);
//			System.out.println("\tWinner: " + winning_player);
//			System.out.println("\t " + );
		}
		
		System.out.println("Ave turns: " + (float)ave_turns/num_games);
		System.out.println("Ave moves: " + (float)ave_moves/num_games);
		System.out.println("Player wins:");
		System.out.println("\tPlayer 0: " + wins[0] + "\tPlayer 1: " + wins[1] + "\tPlayer 2: " + wins[2] + "\tPlayer 3: " + wins[3]);
		
		appendToFile(fname, ave_turns, ave_moves, wins);
		
	}
		

	
	public static void appendToFile(String fname, float turns, float moves, int[] wins) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			File file = new File(fname);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(Float.toString(turns));
			bw.write("," + Float.toString(moves));
			for (int i = 0; i < wins.length; i++) {
				bw.write("," + String.valueOf(wins[i]));
			}
			bw.write("\n");
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}
	
	
	


}
