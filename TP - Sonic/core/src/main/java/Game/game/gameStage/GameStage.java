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
	private ArrayList<Coin> coins;

	public GameStage(GameScreen screen) {
		super(new ScreenViewport());
		Gdx.input.setInputProcessor(this);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		music.setLooping(true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		mapa = Map.getInstance(camera);

		sonic = Sonic.getInstance((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
		
		coins = new ArrayList<Coin>();
		
		this.addActor(new Background());
		this.addActor(sonic);
		this.addActor(mapa);
		this.addActor(new CrabMeat());
		this.addActor(new BuzzBomber());
		this.addActor(new Eggman());
		
		addJumpPads(mapa.getMapSelected());
		addCoins(mapa.getMapSelected());
	}

	@Override
	public void act(float delta) {
		
		super.act(delta);
		
		if(!music.isPlaying()) {
			//music.play();
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
		JumpPad aux = null;

		for (int i = 0; i < this.getActors().size; i++) {
			if (this.getActors().get(i) instanceof JumpPad) {
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
			removeJumpPads(mapa.getMapSelected());
			removeRemainingCoins(mapa.getMapSelected());
			mapa.chengeMap(2);
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			addCoins(mapa.getMapSelected());
	
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.M) && mapa.getMapSelected() == 2) {
			mapa.chengeMap(3);
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			addJumpPads(mapa.getMapSelected());
		}
	}
	
	public void removeJumpPads(int mapId) {
		for(int i = this.getActors().size - 1; i >= 0; i--) {
			if(getActors().get(i) instanceof JumpPad) {
				JumpPad aux = (JumpPad) getActors().get(i);
				if(aux.getBelongingMap() == mapId) {
					this.getActors().removeIndex(i);
				}
			}
		}
	}
	
	public void addCoins(int mapId) {
		switch (mapId) {
		case 1:
			this.addActor(new Coin( 1279, 1152, mapId));
			this.addActor(new Coin( 2496, 1216, mapId));
			this.addActor(new Coin( 2176, 641, mapId));
			this.addActor(new Coin( 3778, 128, mapId));
			this.addActor(new Coin( 6720, 577, mapId));
			this.addActor(new Coin( 5184, 577, mapId));
			this.addActor(new Coin( 10814, 577, mapId));
			this.addActor(new Coin( 10878, 577, mapId));
			this.addActor(new Coin( 12477, 513, mapId));
			this.addActor(new Coin( 15105, 1024, mapId));
			this.addActor(new Coin( 19073, 641, mapId));
			this.addActor(new Coin( 19970, 64, mapId));
			this.addActor(new Coin( 20992, 448, mapId));

			break;
		case 2:
			this.addActor(new Coin((float) 1151, 1088, mapId));
			this.addActor(new Coin((float) 2241, 128, mapId));
			this.addActor(new Coin((float) 3200, 448, mapId));
			this.addActor(new Coin((float) 4288, 576, mapId));
			break;
		case 3:

			break;
		default:
			break;
		}
	}
	
	public void removeRemainingCoins(int mapId) {
		
		for(int i = this.getActors().size - 1; i >= 0; i--) {
			
			if(this.getActors().get(i) instanceof Coin) {
				Coin aux = (Coin) this.getActors().get(i);
				
				if(aux.getBelongingMap() == mapId) {
					this.getActors().removeIndex(i);
				}
			}
		}
	}
	
	public void addJumpPads(int mapId) {
		switch (mapId) {
		case 1:
			this.addActor(new JumpPad((float) 4479.9, 128, mapId));
			break;
		case 2:
			break;
		case 3:
			this.addActor(new JumpPad((float) 16959.89, 128, mapId));
			this.addActor(new JumpPad((float) 18431, 128, mapId));
			this.addActor(new JumpPad((float) 18111, 576, mapId));
			break;
		default:
			break;
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
