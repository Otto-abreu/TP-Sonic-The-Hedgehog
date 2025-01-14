package Game.game.gameObjects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import Game.game.observer.Subscriber;

public abstract class Enemy extends GameObject implements Subscriber {

	protected boolean alive;
	protected static final int seekingRange = 750;
	protected boolean chasing = false;
	protected float targetPos = 0;
	protected int speed;
	protected int velocity;
	protected Vector2 initialPos;
	protected TiledMapTileLayer collisionLayer;
	protected boolean canMove = true;

	public Enemy(float posX, float posY) {
		setPosition(posX, posY);
		setWidth(64);
		setHeight(64);
		
	}

	public static int getSeekingRange() {
		return seekingRange;
	}

	@Override
	public void receiveUpdate(float targetPos, boolean chase) {
		chasing = chase;
		this.targetPos = targetPos;
	}
	
	public void move() {
		
	}

}
