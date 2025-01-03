package Game.game.gameStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import Game.game.gameActors.Map;
import Game.game.gameActors.Sonic;
import Game.game.gameScreen.GameScreen;
public class GameStage extends Stage{

	private OrthographicCamera camera;
	private GameScreen screen;
	private Map mapa;
	
	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		Gdx.input.setInputProcessor(this);
		
		this.screen = screen;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		mapa = new Map(camera);
		
		this.addActor(new Sonic());
		this.addActor(mapa);
		
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		for(int i = 0; i < this.getActors().size; i++) {
		
			this.getActors().get(i).act(delta);
			if(this.getActors().get(i) instanceof Sonic) {
				Sonic aux = (Sonic) this.getActors().get(i);
				camera.position.set(aux.getPositionX() + 20, aux.getPositionY(), 0);
				camera.update();
				System.out.println(camera.position.x);
			}
		}
	}
	@Override
	public void draw() {
		super.draw();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		//mapa.dispose();
	}

}
