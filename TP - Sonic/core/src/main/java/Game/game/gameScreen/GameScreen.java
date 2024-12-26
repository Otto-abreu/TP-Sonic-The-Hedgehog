package Game.game.gameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Game.game.gameStage.GameStage;

public class GameScreen implements Screen {
	private Stage currentStage;

	public GameScreen() {
		currentStage = new GameStage(this);
	}

	public Stage getStage() {
		return currentStage;
	}

	public void setStage(Stage stage) {
		this.currentStage = stage;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		currentStage.draw();
		currentStage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		currentStage.dispose();

	}
	/*
	 * public void changeStage(int i) { switch (i) { case 0: stage.clear(); stage =
	 * new GameStage(volume); break; case 1: stage.clear(); stage = new
	 * MenuStage(this); break; case 2: stage.clear(); stage = new
	 * SettingsStage(this); break; } }
	 */
}
