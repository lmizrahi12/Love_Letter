import java.util.ArrayList;

public class Player {
	ArrayList<Card> hand = new ArrayList<Card>(2);
	ArrayList<Card> discard_pile = new ArrayList<Card>();
	private Boolean is_protected = false;
	private Boolean is_out = false;
	private int[] memory = new int[4];
	String player_type;
	
	public Player(String type)
	{
		this.player_type = type.toLowerCase();
		for (int i = 0; i < memory.length; i++) {
			this.memory[i] = 0;
		}
	}

	public Boolean getIs_protected() {
		return is_protected;
	}


	private void setIs_protected(Boolean is_protected) {
		this.is_protected = is_protected;
	}


	public Boolean getIs_out() {
		return is_out;
	}


	private void setIs_out(Boolean is_out) {
		this.is_out = is_out;
	}
	
	
	public void Eliminate(boolean PRINT){
		setIs_out(true);
		if(PRINT)
			System.out.println("Player Eliminated!!");
	}
	
	public void Protect(boolean PRINT){
		setIs_protected(true);
		if(PRINT)
			System.out.println("Player Protected");
	}
	
	public void Unprotect(){
		setIs_protected(false);
		//System.out.println("Player Unprotected");
	}
	
	public void printHand(){
		for(int i = 0; i < this.hand.size(); i++)
		{
			System.out.println("Card " + i + ":" + this.hand.get(i).getName());
		}
		
	}
	
	public boolean handContains(int cardValue){
		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getValue() == cardValue) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getIndexOfCard(int val){
		if(this.hand.get(0).getValue() == val)
			return 0;
		
		else
			return 1;
	}
	
	public Player cloneKnownInfo(){
//		ArrayList<Card> discard_pile = new ArrayList<Card>();
//		private Boolean is_protected = false;
//		private Boolean is_out = false;
		
		Player player2 = new Player("random");
		
		for (int i = 0; i < this.discard_pile.size(); i++) {
			player2.discard_pile.add( this.discard_pile.get(i).clone() );
		}
		
		player2.is_protected = this.is_protected;
		
		player2.is_out = this.is_out;
		
		return player2;
	}
	
	public void knowThySelf(int player){
		this.memory[player] = this.hand.get(0).getValue();
	}
	
	public int[] getMemory(){
		return this.memory;
	}
	
	public void updateMemory(int player, int value){
		this.memory[player] = value;
	}
	
	public void swapMemory(int player1, int player2){
		int tmp = this.memory[player1];
		this.memory[player1] = this.memory[player2];
		this.memory[player2] = tmp;
	}
	
	public void checkMemory(ArrayList<Player> players){
		for (int i = 0; i < players.size(); i++) {
			int size = players.get(i).discard_pile.size();
			if(size > 0){
				if ( players.get(i).discard_pile.get( size-1 ).getValue() == this.memory[i] ) {
					this.memory[i] = 0;
				}
			}
		}
	}
	
}
