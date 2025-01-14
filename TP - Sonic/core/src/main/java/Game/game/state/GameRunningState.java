package Game.game.state;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import Game.game.gameAssets.CoinAnimationManager;
import Game.game.gameAssets.JumpPadsAnimationManager;
import Game.game.gameObjects.BuzzBomber;
import Game.game.gameObjects.Coin;
import Game.game.gameObjects.CrabMeat;
import Game.game.gameObjects.Eggman;
import Game.game.gameObjects.Enemy;
import Game.game.gameObjects.JumpPad;
import Game.game.gameObjects.Sonic;
import Game.game.gameStage.GameStage;
import Game.game.map.*;
import Game.game.observer.Subscriber;

public class GameRunningState implements StageState {

	private Map map;
	private Sonic sonic;
	private OrthographicCamera camera;
	private CoinAnimationManager coinAnimationManager = new CoinAnimationManager();
	private JumpPadsAnimationManager jumpPadsAnimationManager = new JumpPadsAnimationManager();
	private static GameRunningState instance;
	private BitmapFont fontYellow;
	private BitmapFont fontWhite;
	private float elapsedTime = 0;
	private Array<Subscriber> enemies;

	public static GameRunningState getInstance(GameStage stage) {
		if (instance == null) {
			instance = new GameRunningState(stage);
		}
		return instance;
	}

	private GameRunningState(GameStage stage) {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		map = Map.getInstance(camera);

		sonic = Sonic.getInstance((TiledMapTileLayer) map.getMap().getLayers().get("1"));

		stage.addActor(map.getBackground());
		stage.addActor(sonic);
		stage.addActor(map);

		CrabMeat crabMeat = new CrabMeat(2564, 640);
		enemies = new Array<Subscriber>();
		subscribeEnemy(crabMeat, stage.getActors());

		stage.addActor(new BuzzBomber(100, 1000));
		stage.addActor(new Eggman(200, 1000));

		addJumpPads(map.getMapSelected(), stage);
		addCoins(map.getMapSelected(), stage);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixelifySans.ttf"));

		FreeTypeFontParameter parameterYellow = new FreeTypeFontParameter();
		parameterYellow.size = 36;
		parameterYellow.color = Color.YELLOW;
		fontYellow = generator.generateFont(parameterYellow);

		FreeTypeFontParameter parameterWhite = new FreeTypeFontParameter();
		parameterWhite.size = 36;
		parameterWhite.color = Color.WHITE;
		fontWhite = generator.generateFont(parameterWhite);

	}

	@Override
	public void update(GameStage stage) {

		elapsedTime += Gdx.graphics.getDeltaTime();

		for (int i = 0; i < stage.getActors().size; i++) {

			stage.getActors().get(i).act(stage.getDelta());

			if (stage.getActors().get(i) instanceof Sonic) {
				Sonic aux = (Sonic) stage.getActors().get(i);
				camera.position.set(aux.getX() + 190, aux.getY() + 200, 0);
				camera.update();

			}
			if (stage.getActors().get(i) instanceof Background) {
				Background aux = (Background) stage.getActors().get(i);
				aux.move(sonic.getSpeedX(), sonic.getSpeedY());
			}

		}

		ArrayList<JumpPad> closeJumpPads = getCloserJumpPad(stage);
		if (closeJumpPads != null) {
			for (int i = 0; i < closeJumpPads.size(); i++) {
				sonic.checkJumpPadTouch(closeJumpPads.get(i));
			}
		}

		if (sonic.isDead()) {
			setInitialGameConfig(stage);
			stage.setState(new GameOverState());
		}

		for (int i = 0; i < enemies.size; i++) {
			Enemy aux = (Enemy) enemies.get(i);
			if (sonic.getX() - aux.getX() <= Enemy.getSeekingRange()
					&& sonic.getX() - aux.getX() >= -Enemy.getSeekingRange()) {
				sendNotification(enemies.get(i), sonic.getX(), true);
			}else {
				sendNotification(enemies.get(i), sonic.getX(), false);
			}
		}
		handleLevelChange(stage);

		checkSonicOverlapsCoin(stage);
	}

	public ArrayList<JumpPad> getCloserJumpPad(GameStage stage) {

		ArrayList<JumpPad> jumpPads = new ArrayList<JumpPad>();
		JumpPad aux = null;

		for (int i = 0; i < stage.getActors().size; i++) {
			if (stage.getActors().get(i) instanceof JumpPad) {
				aux = (JumpPad) stage.getActors().get(i);
				if ((sonic.getX() - aux.getX() <= JumpPad.getContactRange()
						&& sonic.getX() - aux.getX() >= -JumpPad.getContactRange()
						&& sonic.getY() - aux.getY() <= JumpPad.getContactRange()
						&& sonic.getY() - aux.getY() >= -JumpPad.getContactRange()) == false) {
					aux = null;
				} else {

					jumpPads.add(aux);
				}
			}
		}
		return jumpPads;

	}

	public void handleLevelChange(GameStage stage) {
		if (map.getMapSelected() == 1 && (sonic.getX() > map.getMapSize(map.getMapSelected()))) {

			removeJumpPads(map.getMapSelected(), stage);
			removeRemainingCoins(map.getMapSelected(), stage);

			map.chengeMap(map.getMapSelected() + 1);

			map.getBackground().initialPos();

			sonic.setCollisionLayer((TiledMapTileLayer) map.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			sonic.setSpeedX(0);
			sonic.setSpeedY(0);

			addCoins(map.getMapSelected(), stage);

		}
		if (map.getMapSelected() == 2 && (sonic.getX() > map.getMapSize(map.getMapSelected()))) {

			removeRemainingCoins(map.getMapSelected(), stage);

			map.chengeMap(map.getMapSelected() + 1);

			map.getBackground().initialPos();

			sonic.setCollisionLayer((TiledMapTileLayer) map.getMap().getLayers().get("1"));
			sonic.setInitialPosition();
			sonic.setSpeedX(0);
			sonic.setSpeedY(0);

			addJumpPads(map.getMapSelected(), stage);
			addCoins(map.getMapSelected(), stage);
		}

		if (map.getMapSelected() == 3 && sonic.getX() > map.getMapSize(map.getMapSelected())) {

			setInitialGameConfig(stage);

			stage.setState(new VictoryState());

		}
	}

	public void setInitialGameConfig(GameStage stage) {
		sonic.setInitialPosition();
		sonic.setCoinsCollected(0);
		sonic.setSpeedX(0);
		sonic.setSpeedY(0);

		removeJumpPads(map.getMapSelected(), stage);
		removeRemainingCoins(map.getMapSelected(), stage);

		map.getBackground().initialPos();

		map.chengeMap(1);

		sonic.setCollisionLayer((TiledMapTileLayer) map.getMap().getLayers().get("1"));

		addCoins(map.getMapSelected(), stage);
		addJumpPads(map.getMapSelected(), stage);
	}

	public void removeJumpPads(int mapId, GameStage stage) {
		for (int i = stage.getActors().size - 1; i >= 0; i--) {
			if (stage.getActors().get(i) instanceof JumpPad) {
				JumpPad aux = (JumpPad) stage.getActors().get(i);
				if (aux.getBelongingMap() == mapId) {
					stage.getActors().removeIndex(i);
				}
			}
		}
	}

	public void checkSonicOverlapsCoin(GameStage stage) {

		for (int i = 0; i < stage.getActors().size; i++) {
			if (stage.getActors().get(i) instanceof Coin) {
				Coin aux = (Coin) stage.getActors().get(i);
				if ((sonic.getX() - aux.getX() <= Coin.getContactRange()
						&& sonic.getX() - aux.getX() >= -Coin.getContactRange()
						&& sonic.getY() - aux.getY() <= Coin.getContactRange()
						&& sonic.getY() - aux.getY() >= -Coin.getContactRange())) {
					collectCoin(aux.getId(), stage);
				}
			}
		}
	}

	public void collectCoin(int coinId, GameStage stage) {

		for (int i = stage.getActors().size - 1; i >= 0; i--) {
			if (stage.getActors().get(i) instanceof Coin) {

				Coin aux = (Coin) stage.getActors().get(i);

				if (aux.getId() == coinId) {
					stage.getActors().removeIndex(i);
					sonic.setCoinsCollected(sonic.getCoinsCollected() + 1);
				}
			}
		}
		;
	}

	public void addCoins(int mapId, GameStage stage) {
		switch (mapId) {
		case 1:
			stage.addActor(new Coin(1279, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(2496, 1216, mapId, coinAnimationManager));
			stage.addActor(new Coin(2176, 641, mapId, coinAnimationManager));
			stage.addActor(new Coin(3778, 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(6720, 577, mapId, coinAnimationManager));
			stage.addActor(new Coin(5184, 577, mapId, coinAnimationManager));
			stage.addActor(new Coin(10814, 577, mapId, coinAnimationManager));
			stage.addActor(new Coin(10878, 577, mapId, coinAnimationManager));
			stage.addActor(new Coin(12477, 513, mapId, coinAnimationManager));
			stage.addActor(new Coin(15105, 1024, mapId, coinAnimationManager));
			stage.addActor(new Coin(19073, 641, mapId, coinAnimationManager));
			stage.addActor(new Coin(19970, 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(20992, 448, mapId, coinAnimationManager));

			break;
		case 2:
			stage.addActor(new Coin(1151, 1088, mapId, coinAnimationManager));
			stage.addActor(new Coin(2241, 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(3200, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(4288, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(6527, 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(10176, 704, mapId, coinAnimationManager));
			stage.addActor(new Coin(10818, 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(11585, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(13120, 896, mapId, coinAnimationManager));
			stage.addActor(new Coin(14784, 768, mapId, coinAnimationManager));
			stage.addActor(new Coin(14848, 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(13825, 256, mapId, coinAnimationManager));
			stage.addActor(new Coin(11071, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(13819, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(12673, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(12737, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(11839, 192, mapId, coinAnimationManager));
			stage.addActor(new Coin(12031, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(16836, 832 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(20161, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(24063, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(24127, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(24191, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(26047, 1024, mapId, coinAnimationManager));
			stage.addActor(new Coin(24001, 1216, mapId, coinAnimationManager));
			stage.addActor(new Coin(26688, 192 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(28031, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(28800, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(33154, 64 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(33471, 64 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(33474, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(33023, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(34623, 192, mapId, coinAnimationManager));
			stage.addActor(new Coin(34432, 192, mapId, coinAnimationManager));
			stage.addActor(new Coin(34240, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(34240 - 128, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(33602, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(33602 + 128, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(33086, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(33086 + 128, 576, mapId, coinAnimationManager));
			break;
		case 3:
			stage.addActor(new Coin(960, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(2240, 704, mapId, coinAnimationManager));
			stage.addActor(new Coin(3199, 768, mapId, coinAnimationManager));
			stage.addActor(new Coin(3904, 640, mapId, coinAnimationManager));
			stage.addActor(new Coin(5694, 1024 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(5694 + 64, 1024 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(5694 + 128, 1024 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(8895, 832 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(9920, 768, mapId, coinAnimationManager));
			stage.addActor(new Coin(10308, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(9920, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(9794, 256, mapId, coinAnimationManager));
			stage.addActor(new Coin(10816, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(11712, 256, mapId, coinAnimationManager));
			stage.addActor(new Coin(12328, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(10753, 960, mapId, coinAnimationManager));
			stage.addActor(new Coin(11715, 704, mapId, coinAnimationManager));
			stage.addActor(new Coin(12610, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(12863, 320, mapId, coinAnimationManager));
			stage.addActor(new Coin(13440, 576 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(12290, 896, mapId, coinAnimationManager));
			stage.addActor(new Coin(14850, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(15425, 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(16191, 128 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(3904, 640, mapId, coinAnimationManager));
			stage.addActor(new Coin(17023 - 64 * 5, 512 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(18175 - 128, 576 - 64 * 4, mapId, coinAnimationManager));
			stage.addActor(new Coin(19263, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(19776, 832 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(20161, 832 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(21313, 896, mapId, coinAnimationManager));
			stage.addActor(new Coin(22850, 832, mapId, coinAnimationManager));
			stage.addActor(new Coin(24768, 960, mapId, coinAnimationManager));
			stage.addActor(new Coin(26371, 704, mapId, coinAnimationManager));
			stage.addActor(new Coin(25151, 448, mapId, coinAnimationManager));
			stage.addActor(new Coin(26176, 320, mapId, coinAnimationManager));
			stage.addActor(new Coin(26624, 320, mapId, coinAnimationManager));
			stage.addActor(new Coin(27072 - 64, 576, mapId, coinAnimationManager));
			stage.addActor(new Coin(28096, 448 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(28929, 256 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(30017, 896, mapId, coinAnimationManager));
			stage.addActor(new Coin(29632, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(29632 - 64, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(29632 - 128, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(29632 - 64 * 3, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(29632 - 64 * 4, 1152, mapId, coinAnimationManager));
			stage.addActor(new Coin(31233, 960, mapId, coinAnimationManager));
			stage.addActor(new Coin(32513, 768 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(32192, 320, mapId, coinAnimationManager));
			stage.addActor(new Coin(32192, 320 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(32192, 320 + 128, mapId, coinAnimationManager));
			stage.addActor(new Coin(30720, 384, mapId, coinAnimationManager));
			stage.addActor(new Coin(34178, 320, mapId, coinAnimationManager));
			stage.addActor(new Coin(33217, 512, mapId, coinAnimationManager));
			stage.addActor(new Coin(34497, 832 + 64, mapId, coinAnimationManager));
			stage.addActor(new Coin(35327, 512, mapId, coinAnimationManager));
			break;
		default:
			break;
		}
	}

	public void removeRemainingCoins(int mapId, GameStage stage) {

		for (int i = stage.getActors().size - 1; i >= 0; i--) {

			if (stage.getActors().get(i) instanceof Coin) {
				Coin aux = (Coin) stage.getActors().get(i);

				if (aux.getBelongingMap() == mapId) {
					stage.getActors().removeIndex(i);
					sonic.setCoinsCollected(0);
				}
			}
		}
	}

	public void addJumpPads(int mapId, GameStage stage) {
		switch (mapId) {
		case 1:
			stage.addActor(new JumpPad((float) 4479.9, 128, mapId, jumpPadsAnimationManager));
			break;
		case 2:
			break;
		case 3:
			stage.addActor(new JumpPad((float) 16959.89, 128, mapId, jumpPadsAnimationManager));
			stage.addActor(new JumpPad((float) 18431, 128, mapId, jumpPadsAnimationManager));
			stage.addActor(new JumpPad((float) 18111, 576, mapId, jumpPadsAnimationManager));
			break;
		default:
			break;
		}
	}

	public BitmapFont getYellowFont() {
		return fontYellow;
	}

	public BitmapFont getWhiteFont() {
		return fontWhite;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public Sonic getSonic() {
		return sonic;
	}

	@Override
	public void handleInput(GameStage stage) {

	}

	public void subscribeEnemy(Subscriber newSubscriber, Array<Actor> actorsInStage) {
		enemies.add(newSubscriber);

		actorsInStage.add((Enemy) newSubscriber);

	}

	public void unsubscribeEnemy(Subscriber subscriberToDelete, Array<Actor> actorsInStage) {
		enemies.removeValue(subscriberToDelete, false);

		actorsInStage.removeValue((Enemy) subscriberToDelete, false);
	}

	public void sendNotification(Subscriber subscriber, float targetPos, boolean chase) {
		subscriber.receiveUpdate(targetPos, chase);
	}

}
