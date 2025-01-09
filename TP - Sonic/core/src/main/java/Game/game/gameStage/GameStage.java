package Game.game.gameStage;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

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

	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// camera.zoom = (float) 10;

		mapa = Map.getInstance(camera);

		sonic = Sonic.getInstance((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));

		this.addActor(sonic);
		this.addActor(mapa);
		this.addActor(new CrabMeat());
		this.addActor(new BuzzBomber());
		this.addActor(new Eggman());
		this.addActor(new JumpPad((float) 4479.9, 128));

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for (int i = 0; i < this.getActors().size; i++) {

			this.getActors().get(i).act(delta);
			if (this.getActors().get(i) instanceof Sonic) {
				Sonic aux = (Sonic) this.getActors().get(i);
				camera.position.set(aux.getX() + 190, aux.getY() + 200, 0);
				camera.update();
			}
		}
		ArrayList<JumpPad> jumpPads = getCloseJumpPads();
		for (int i = 0; i < jumpPads.size(); i++) {
			sonic.checkJumpPadTouch(jumpPads.get(i));
		}
	}

	public ArrayList<JumpPad> getCloseJumpPads() {
		ArrayList<JumpPad> closeJumpPads = new ArrayList<JumpPad>();
		for (int i = 0; i < this.getActors().size; i++) {
			if (this.getActors().get(i) instanceof JumpPad) {
				JumpPad aux = (JumpPad) this.getActors().get(i);
				if (sonic.getX() - aux.getX() <= 50 && sonic.getX() - aux.getX() >= -50
						&& sonic.getY() - aux.getY() <= 50 && sonic.getY() - aux.getY() >= -50) {
					closeJumpPads.add(aux);
				}
			}
		}
		return closeJumpPads;

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
