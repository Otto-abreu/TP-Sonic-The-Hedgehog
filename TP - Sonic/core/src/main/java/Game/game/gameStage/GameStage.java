package Game.game.gameStage;

import Game.game.gameAssets.JumpPadsAnimationManager;
import Game.game.gameAssets.CoinAnimationManager;
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

	CoinAnimationManager coinAnimationManager = new CoinAnimationManager();
	JumpPadsAnimationManager jumpPadsAnimationManager = new JumpPadsAnimationManager();

	private Background background;


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
		
		background = new Background();

		this.addActor(background);
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

		if (!music.isPlaying()) {
			music.play();
		}

		for (int i = 0; i < this.getActors().size; i++) {

			this.getActors().get(i).act(delta);
			if (this.getActors().get(i) instanceof Sonic) {
				Sonic aux = (Sonic) this.getActors().get(i);
				camera.position.set(aux.getX() + 190, aux.getY() + 200, 0);
				camera.update();

			}
			if (getActors().get(i) instanceof Background) {
				Background aux = (Background) getActors().get(i);
				aux.move(sonic.getSpeedX(), sonic.getSpeedY(), sonic.getLives());
			}
		}

		ArrayList<JumpPad> closeJumpPads = getCloserJumpPad();
		if (closeJumpPads != null) {
			for (int i = 0; i < closeJumpPads.size(); i++) {
				sonic.checkJumpPadTouch(closeJumpPads.get(i));
			}
		}

		handleLevelChange();

		checkSonicOverlapsCoin();
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
					System.out.println("JumpPad adicionado");
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
			mapa.chengeMap(mapa.getMapSelected() + 1);
			background.initialPos();
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			addCoins(mapa.getMapSelected());

		} else if (Gdx.input.isKeyJustPressed(Input.Keys.M) && mapa.getMapSelected() == 2) {
			removeRemainingCoins(mapa.getMapSelected());
			mapa.chengeMap(mapa.getMapSelected() + 1);
			background.initialPos();
			sonic.setCollisionLayer((TiledMapTileLayer) mapa.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			addJumpPads(mapa.getMapSelected());
			addCoins(mapa.getMapSelected());
		}
	}

	public void removeJumpPads(int mapId) {
		for (int i = this.getActors().size - 1; i >= 0; i--) {
			if (getActors().get(i) instanceof JumpPad) {
				JumpPad aux = (JumpPad) getActors().get(i);
				if (aux.getBelongingMap() == mapId) {
					this.getActors().removeIndex(i);
				}
			}
		}
	}

	public void checkSonicOverlapsCoin() {

		for (int i = 0; i < this.getActors().size; i++) {
			if (this.getActors().get(i) instanceof Coin) {
				Coin aux = (Coin) this.getActors().get(i);
				if ((sonic.getX() - aux.getX() <= 50 && sonic.getX() - aux.getX() >= -50
						&& sonic.getY() - aux.getY() <= 50 && sonic.getY() - aux.getY() >= -50)) {
					collectCoin(aux.getId());
				}
			}
		}
	}

	public void collectCoin(int coinId) {

		for (int i = this.getActors().size - 1; i >= 0; i--) {
			if (this.getActors().get(i) instanceof Coin) {

				Coin aux = (Coin) this.getActors().get(i);

				if (aux.getId() == coinId) {
					this.getActors().removeIndex(i);
					sonic.setCoinsCollected(sonic.getCoinsCollected() + 1);
					System.out.println(sonic.getCoinsCollected());
				}
			}
		}
		;
	}

	public void addCoins(int mapId) {
		switch (mapId) {
		case 1:
			this.addActor(new Coin(1279, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(2496, 1216, mapId, coinAnimationManager));
			this.addActor(new Coin(2176, 641, mapId, coinAnimationManager));
			this.addActor(new Coin(3778, 128, mapId, coinAnimationManager));
			this.addActor(new Coin(6720, 577, mapId, coinAnimationManager));
			this.addActor(new Coin(5184, 577, mapId, coinAnimationManager));
			this.addActor(new Coin(10814, 577, mapId, coinAnimationManager));
			this.addActor(new Coin(10878, 577, mapId, coinAnimationManager));
			this.addActor(new Coin(12477, 513, mapId, coinAnimationManager));
			this.addActor(new Coin(15105, 1024, mapId, coinAnimationManager));
			this.addActor(new Coin(19073, 641, mapId, coinAnimationManager));
			this.addActor(new Coin(19970, 64, mapId, coinAnimationManager));
			this.addActor(new Coin(20992, 448, mapId, coinAnimationManager));

			break;
		case 2:
			this.addActor(new Coin(1151, 1088, mapId, coinAnimationManager));
			this.addActor(new Coin(2241, 128, mapId, coinAnimationManager));
			this.addActor(new Coin(3200, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(4288, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(6527, 128, mapId, coinAnimationManager));
			this.addActor(new Coin(10176, 704, mapId, coinAnimationManager));
			this.addActor(new Coin(10818, 64, mapId, coinAnimationManager));
			this.addActor(new Coin(11585, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(13120, 896, mapId, coinAnimationManager));
			this.addActor(new Coin(14784, 768, mapId, coinAnimationManager));
			this.addActor(new Coin(14848, 64, mapId, coinAnimationManager));
			this.addActor(new Coin(13825, 256, mapId, coinAnimationManager));
			this.addActor(new Coin(11071, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(13819, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(12673, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(12737, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(11839, 192, mapId, coinAnimationManager));
			this.addActor(new Coin(12031, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(16836, 832 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(20161, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(24063, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(24127, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(24191, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(26047, 1024, mapId, coinAnimationManager));
			this.addActor(new Coin(24001, 1216, mapId, coinAnimationManager));
			this.addActor(new Coin(26688, 192 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(28031, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(28800, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(33154, 64 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(33471, 64 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(33474, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(33023, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(34623, 192, mapId, coinAnimationManager));
			this.addActor(new Coin(34432, 192, mapId, coinAnimationManager));
			this.addActor(new Coin(34240, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(34240 - 128, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(33602, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(33602 + 128, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(33086, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(33086 + 128, 576, mapId, coinAnimationManager));
			break;
		case 3:
			this.addActor(new Coin(960, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(2240, 704, mapId, coinAnimationManager));
			this.addActor(new Coin(3199, 768, mapId, coinAnimationManager));
			this.addActor(new Coin(3904, 640, mapId, coinAnimationManager));
			this.addActor(new Coin(5694, 1024 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(5694 + 64, 1024 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(5694 + 128, 1024 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(8895, 832 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(9920, 768, mapId, coinAnimationManager));
			this.addActor(new Coin(10308, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(9920, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(9794, 256, mapId, coinAnimationManager));
			this.addActor(new Coin(10816, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(11712, 256, mapId, coinAnimationManager));
			this.addActor(new Coin(12328, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(10753, 960, mapId, coinAnimationManager));
			this.addActor(new Coin(11715, 704, mapId, coinAnimationManager));
			this.addActor(new Coin(12610, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(12863, 320, mapId, coinAnimationManager));
			this.addActor(new Coin(13440, 576 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(12290, 896, mapId, coinAnimationManager));
			this.addActor(new Coin(14850, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(15425, 128, mapId, coinAnimationManager));
			this.addActor(new Coin(16191, 128 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(3904, 640, mapId, coinAnimationManager));
			this.addActor(new Coin(17023 - 64 * 5, 512 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(18175 - 128, 576 - 64 * 4, mapId, coinAnimationManager));
			this.addActor(new Coin(19263, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(19776, 832 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(20161, 832 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(21313, 896, mapId, coinAnimationManager));
			this.addActor(new Coin(22850, 832, mapId, coinAnimationManager));
			this.addActor(new Coin(24768, 960, mapId, coinAnimationManager));
			this.addActor(new Coin(26371, 704, mapId, coinAnimationManager));
			this.addActor(new Coin(25151, 448, mapId, coinAnimationManager));
			this.addActor(new Coin(26176, 320, mapId, coinAnimationManager));
			this.addActor(new Coin(26624, 320, mapId, coinAnimationManager));
			this.addActor(new Coin(27072 - 64, 576, mapId, coinAnimationManager));
			this.addActor(new Coin(28096, 448 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(28929, 256 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(30017, 896, mapId, coinAnimationManager));
			this.addActor(new Coin(29632, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(29632 - 64, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(29632 - 128, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(29632 - 64 * 3, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(29632 - 64 * 4, 1152, mapId, coinAnimationManager));
			this.addActor(new Coin(31233, 960, mapId, coinAnimationManager));
			this.addActor(new Coin(32513, 768 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(32192, 320, mapId, coinAnimationManager));
			this.addActor(new Coin(32192, 320 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(32192, 320 + 128, mapId, coinAnimationManager));
			this.addActor(new Coin(30720, 384, mapId, coinAnimationManager));
			this.addActor(new Coin(34178, 320, mapId, coinAnimationManager));
			this.addActor(new Coin(33217, 512, mapId, coinAnimationManager));
			this.addActor(new Coin(34497, 832 + 64, mapId, coinAnimationManager));
			this.addActor(new Coin(35327, 512, mapId, coinAnimationManager));
			break;
		default:
			break;
		}
	}

	public void removeRemainingCoins(int mapId) {

		for (int i = this.getActors().size - 1; i >= 0; i--) {

			if (this.getActors().get(i) instanceof Coin) {
				Coin aux = (Coin) this.getActors().get(i);

				if (aux.getBelongingMap() == mapId) {
					this.getActors().removeIndex(i);
					sonic.setCoinsCollected(0);
				}
			}
		}
	}

	public void addJumpPads(int mapId) {
		switch (mapId) {
		case 1:
			this.addActor(new JumpPad((float) 4479.9, 128, mapId, jumpPadsAnimationManager));
			break;
		case 2:
			break;
		case 3:
			this.addActor(new JumpPad((float) 16959.89, 128, mapId, jumpPadsAnimationManager));
			this.addActor(new JumpPad((float) 18431, 128, mapId, jumpPadsAnimationManager));
			this.addActor(new JumpPad((float) 18111, 576, mapId, jumpPadsAnimationManager));
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
