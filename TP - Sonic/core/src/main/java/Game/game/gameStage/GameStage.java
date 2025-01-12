package Game.game.gameStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Game.game.gameScreen.GameScreen;
import Game.game.state.GameOverState;
import Game.game.state.GameRunningState;
import Game.game.state.HomeScreenState;
import Game.game.state.StageState;
import Game.game.state.VictoryState;

public class GameStage extends Stage {

	private float delta = 0;
	private Music music;
	private SpriteBatch batch;
	private final Vector2 windowSize = new Vector2(1540, 950);

	private StageState stageState;

	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();

		music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		music.setLooping(true);

		stageState = new HomeScreenState();

	}

	@Override
	public void draw() {
		if (stageState instanceof HomeScreenState) {
			HomeScreenState state = (HomeScreenState) stageState;
			batch.begin();
			batch.draw(state.getHomeScreen(), state.getHomeScreen().getX(), state.getHomeScreen().getY(),
					state.getHomeScreen().getWidth(), state.getHomeScreen().getHeight());
			batch.end();

		}
		if (stageState instanceof GameRunningState) {
			GameRunningState state = GameRunningState.getInstance(this);
			super.draw();
			batch.begin();
			
			state.getYellowFont().draw(batch, "SCORE", 10, 940);
			state.getWhiteFont().draw(batch, "Ainda n temos pontuacao", 170, 940);
			
			state.getYellowFont().draw(batch, "TIME", 10, 910);
			state.getWhiteFont().draw(batch, String.format("%.2f", state.getElapsedTime()), 170, 910);
			
			state.getYellowFont().draw(batch, "RINGS", 10, 880);
			state.getWhiteFont().draw(batch, "" + state.getSonic().getCoinsCollected(), 170, 880);
			
			batch.end();

		}

		if (stageState instanceof VictoryState) {
			VictoryState state = (VictoryState) stageState;
			batch.begin();
			batch.draw(state.getVictoryScreen(), state.getVictoryScreen().getX(), state.getVictoryScreen().getY(),
					state.getVictoryScreen().getWidth(), state.getVictoryScreen().getHeight());
			batch.end();

		}

		if (stageState instanceof GameOverState) {
			GameOverState state = (GameOverState) stageState;
			batch.begin();
			batch.draw(state.getGameOverScreen(), state.getGameOverScreen().getX(), state.getGameOverScreen().getY(),
					state.getGameOverScreen().getWidth(), state.getGameOverScreen().getHeight());
			batch.end();

		}

	}

	@Override
	public void act(float delta) {

		this.delta = delta;
		super.act(delta);

		if (!music.isPlaying()) {
			music.play();
		}

		if (stageState instanceof HomeScreenState) {
			HomeScreenState state = (HomeScreenState) stageState;
			state.handleInput(this);
			state.update(this);

		}

		if (stageState instanceof GameRunningState) {
			GameRunningState state = (GameRunningState) stageState;
			state.update(this);

		}

		if (stageState instanceof VictoryState) {
			VictoryState state = (VictoryState) stageState;
			state.handleInput(this);
			state.update(this);
		}

		if (stageState instanceof GameOverState) {
			GameOverState state = (GameOverState) stageState;
			state.handleInput(this);
			state.update(this);
		}

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void setState(StageState state) {
		this.stageState = state;
	}

	public Vector2 getWindowSize() {
		return windowSize;
	}

	public float getDelta() {
		return delta;
	}

}
