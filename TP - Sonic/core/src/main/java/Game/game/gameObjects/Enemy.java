package Game.game.gameObjects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import Game.game.observer.Subscriber;

public abstract class Enemy extends GameObject implements Subscriber {

	protected static final int seekingRange = 750;
	protected static final int contactRange = 50;
	protected boolean chasing = false;
	protected Sonic target = null;
	protected double velocity;
	protected Vector2 initialPos;
	protected TiledMapTileLayer collisionLayer;
	protected boolean canMove = true;
	protected int points;
	protected int belongingMap;

	public Enemy(float posX, float posY, int map) {
		setPosition(posX, posY);
		setWidth(64);
		setHeight(64);
		initialPos = new Vector2();
		this.belongingMap = map;
		
	}

	public static int getSeekingRange() {
		return seekingRange;
	}

	@Override
	public void receiveUpdate(Sonic sonic, boolean chase) {
		chasing = chase;
		this.target = sonic;
	}
	
	public void move() {
		
	}
	
	public static int getContactRange() {
		return contactRange;
	}
	public int getPoints() {
		return points;
	}
	public int getBelongingMap() {
		return belongingMap;
	}
}
