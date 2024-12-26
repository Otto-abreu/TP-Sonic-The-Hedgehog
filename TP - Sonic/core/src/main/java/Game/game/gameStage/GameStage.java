package Game.game.gameStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Game.game.gameActors.Sonic;
import Game.game.gameScreen.GameScreen;
public class GameStage extends Stage{

	private OrthographicCamera orto;
	private GameScreen screen;

	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		
		Gdx.input.setInputProcessor(this);
		
		orto = new OrthographicCamera();
		orto.setToOrtho(false, 640, 480);
		
		this.addActor(new Sonic());
		
		this.screen = screen;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		for(int i = 0; i < this.getActors().size; i++) {
			this.getActors().get(i).act(delta);
		}
	}
	@Override
	public void draw() {
		super.draw();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
