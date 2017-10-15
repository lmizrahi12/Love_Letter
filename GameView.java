import java.awt.event.ActionListener;
import javax.swing.*;


public class GameView extends JFrame{

	private JLabel activePlayer  = new JLabel();
	private JButton startButton = new JButton("Start");
	private JButton nextPlayerButton = new JButton("<html> <h1> Player " + 1 + " </h1>");
	
	String player_text = "<html><h1>Player: 2</h1>\n<p>Imunity: &#9745; Active: &#9744; </p>\n<p>Discarded</p>\n<ol>\n  <li>(4) Handie Maiden</li>\n  <li>(1) guard</li>\n</ol>" ;
	
	String p4 = player_text + "\n\n" + player_text + "\n\n" + player_text + "\n\n" + player_text + "\n\n" ;
	
	private JLabel playerData  = new JLabel(p4);
	
	public GameView(){
		
		JPanel panel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		
		panel.add(activePlayer);
		panel.add(startButton);
		panel.add(nextPlayerButton);
		panel.add(playerData);
		
		this.add(panel);
		
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer.setText("Player " +  Integer.toString(activePlayer));
	}
	
	void addStartListener(ActionListener listenForStartButton){
        startButton.addActionListener(listenForStartButton);
    }
	
	void addNextPlayerListener(ActionListener listenForNextPlayerButton){
        nextPlayerButton.addActionListener(listenForNextPlayerButton);
    }


    
	
}
