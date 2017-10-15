import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController{
	
	GameStateModel state;
	GameView view;
	
	public GameController(GameStateModel state, GameView view){
		
		this.view = view;
		this.state = state;
		
		view.addStartListener(new GameListener());
		view.addNextPlayerListener(new NextPlayerListener());
		
	}
	
	class GameListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			view.setActivePlayer(state.turn_tracker);
			
			
		}
		
		
	}
	
	class NextPlayerListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			state.endTurn();
			view.setActivePlayer(state.turn_tracker);
			
			
		}
		
		
	}

}
