package Game.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Game.game.gameStage.GameStage;

public class GameOverState extends StageState {

	private Sprite gameOverScreen;
	private Rectangle playAgainButton;
	private boolean buttonClicked = false;

	public GameOverState() {
		playAgainButton = new Rectangle(461, 175, 616, 158);
		gameOverScreen = new Sprite(new Texture(Gdx.files.internal("GameOverScreen.png")));
		gameOverScreen.setPosition(0, 0);
	}

	public void handleInput(GameStage stage) {
		int mousePosX = Gdx.input.getX();
		int mousePosY = Gdx.input.getY();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
				&& playAgainButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			buttonClicked = true;
		}

	}
	
	public void update(GameStage stage) {
		if(buttonClicked)
			stage.setState(new HomeScreenState());
	}

	public Sprite getGameOverScreen() {
		return gameOverScreen;
	}

}
