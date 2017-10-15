
public class Start {
	public static void main(String[] args) {
		
		GameStateModel model = new GameStateModel();
		GameView view = new GameView();
		GameController controller = new GameController(model, view);
		view.setVisible(true);
		
		
			
	}
}
