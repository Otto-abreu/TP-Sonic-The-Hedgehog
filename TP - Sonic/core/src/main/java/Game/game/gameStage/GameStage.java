package Game.game.gameStage;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Game.game.gameObjects.*;
import Game.game.gameScreen.GameScreen;

public class GameStage extends Stage {

	private OrthographicCamera camera;
	private Map mapa;
	private Sonic sonic;
	private Music music;

	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		Gdx.input.setInputProcessor(this);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		music.setLooping(true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// camera.zoom = (float) 10;

		mapa = Map.getInstance(camera);

		sonic = Sonic.getInstance((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
		
		this.addActor(new Background());
		this.addActor(sonic);
		this.addActor(mapa);
		this.addActor(new CrabMeat());
		this.addActor(new BuzzBomber());
		this.addActor(new Eggman());
		this.addActor(new JumpPad((float) 4479.9, 128, 1));

	}

	@Override
	public void act(float delta) {
		
		super.act(delta);
		
		if(!music.isPlaying()) {
			music.play();
		}
		
		for (int i = 0; i < this.getActors().size; i++) {

			this.getActors().get(i).act(delta);
			if (this.getActors().get(i) instanceof Sonic) {
				Sonic aux = (Sonic) this.getActors().get(i);
				camera.position.set(aux.getX() + 190, aux.getY() + 200, 0);
				camera.update();

			}if (getActors().get(i) instanceof Background) {
				Background aux = (Background) getActors().get(i);
				aux.move(sonic.getSpeedX(), sonic.getSpeedY(), sonic.getLives());
			}
		}
		
		ArrayList<JumpPad> closeJumpPads = getCloserJumpPad();
		if (closeJumpPads != null) {
			for(int i = 0; i < closeJumpPads.size(); i++) {
				sonic.checkJumpPadTouch(closeJumpPads.get(i));
			}
		}

		handleLevelChange();
	}

	public ArrayList<JumpPad> getCloserJumpPad() {
		
		ArrayList<JumpPad> jumpPads = new ArrayList<JumpPad>();
		int counter = 0;
		JumpPad aux = null;


		for (int i = 0; i < this.getActors().size; i++) {
			if (this.getActors().get(i) instanceof JumpPad) {
				counter++;
				System.out.println(counter);
				aux = (JumpPad) this.getActors().get(i);
				if ((sonic.getX() - aux.getX() <= 50 && sonic.getX() - aux.getX() >= -50
						&& sonic.getY() - aux.getY() <= 50 && sonic.getY() - aux.getY() >= -50) == false) {
					aux = null;
				} else {
					jumpPads.add(aux);
				}
			}
		}
		return jumpPads;

	}

	public void handleLevelChange() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.M) && mapa.getMapSelected() == 1) {
			mapa.chengeMap(2);
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			for (int i = 0; i < getActors().size; i++) {
				if (this.getActors().get(i) instanceof JumpPad) {
					this.getActors().removeIndex(i);
				}
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.M) && mapa.getMapSelected() == 2) {
			mapa.chengeMap(3);
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			this.addActor(new JumpPad((float) 16959.89, 128, 3));
			this.addActor(new JumpPad((float) 18431, 128, 3));
			this.addActor(new JumpPad((float) 18111, 576, 3));
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
