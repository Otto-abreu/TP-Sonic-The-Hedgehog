package Game.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Game.game.gameStage.GameStage;

public class HomeScreenState implements StageState {

	private Sprite homeScreen;
	private Rectangle startButton;
	private Rectangle infoButton;
	private boolean startButtonClicked = false;
	private boolean infoButtonClicked = false;

	public HomeScreenState() {
		startButton = new Rectangle(174, 371, 554, 116);
		infoButton = new Rectangle(179, 83, 554, 116);
		homeScreen = new Sprite(new Texture(Gdx.files.internal("HomeScreen1.png")));
		homeScreen.setPosition(0, 0);
	}

	public void handleInput(GameStage stage) {

		int mousePosX = Gdx.input.getX();
		int mousePosY = Gdx.input.getY();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
				&& startButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			startButtonClicked = true;
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
				&& infoButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			infoButtonClicked = true;
		}

	}

	@Override
	public void update(GameStage stage) {
		if (startButtonClicked)
			stage.setState(GameRunningState.getInstance(stage));

		if (infoButtonClicked)
			stage.setState(new InformationalState());
	}

	public Sprite getHomeScreen() {
		return homeScreen;
	}

	public Rectangle getButton() {
		return startButton;
	}
}
