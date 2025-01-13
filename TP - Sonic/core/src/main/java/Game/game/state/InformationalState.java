package Game.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Game.game.gameStage.GameStage;

public class InformationalState implements StageState {

	private Sprite infoScreen;
	private Rectangle goToHomeButton;
	private boolean buttonClicked = false;

	public InformationalState() {
		goToHomeButton = new Rectangle(1368, 55, 123, 56);
		infoScreen = new Sprite(new Texture(Gdx.files.internal("InfoScreen.png")));
		infoScreen.setPosition(0, 0);
	}

	public void handleInput(GameStage stage) {

		int mousePosX = Gdx.input.getX();
		int mousePosY = Gdx.input.getY();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
				&& goToHomeButton.contains(mousePosX, stage.getWindowSize().y - mousePosY)) {
			buttonClicked = true;
		}

	}

	@Override
	public void update(GameStage stage) {
		if (buttonClicked)
			stage.setState(new HomeScreenState());
	}

	public Sprite getInfoScreen() {
		return infoScreen;
	}

}
