package Game.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Game.game.gameStage.GameStage;

public class HomeScreenState extends StageState {

	private Sprite homeScreen;
	private Rectangle startButton;
	private boolean buttonClicked = false;

	public HomeScreenState() {
		startButton = new Rectangle(174, 371, 554, 116);
		homeScreen = new Sprite(new Texture(Gdx.files.internal("HomeScreen1.png")));
		homeScreen.setPosition(0, 0);
	}

	public void handleInput(GameStage stage) {

		int mousePosX = Gdx.input.getX();
		int mousePosY = Gdx.input.getY();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) 
				&& startButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			buttonClicked = true;
		}

	}

	@Override
	public void update(GameStage stage) {
		if(buttonClicked)
		stage.setState(GameRunningState.getInstance(stage));
	}

	public Sprite getHomeScreen() {
		return homeScreen;
	}

	public Rectangle getButton() {
		return startButton;
	}
}
