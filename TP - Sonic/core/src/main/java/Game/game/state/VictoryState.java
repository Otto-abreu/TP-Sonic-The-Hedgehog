package Game.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Game.game.gameStage.GameStage;


public class VictoryState extends StageState{
	
	private Sprite victoryScreen;
	private Rectangle playAgainButton;
	private boolean buttonClicked = false;
	
	public VictoryState() {
		playAgainButton = new Rectangle(446, 193, 629, 154);
		victoryScreen = new Sprite(new Texture(Gdx.files.internal("VictoryScreen.png")));
		victoryScreen.setPosition(0, 0);
	}
	
	public void handleInput(GameStage stage) {
		int mousePosX = Gdx.input.getX();
		int mousePosY = Gdx.input.getY();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) 
				&& playAgainButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			buttonClicked = true;
			System.out.println(buttonClicked);
		}
	}
	
	public void update(GameStage stage) {
		if(buttonClicked)
			stage.setState(new HomeScreenState());
	}
	public Sprite getVictoryScreen() {
		return victoryScreen;
	}

}
