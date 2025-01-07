package Game.game.gameActors;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import Game.game.controllers.InputHandler;

public class Sonic extends GameObject {

	private Texture image;
	private Vector2 speed;
	private int floor = 650;
	private final float finalSpeedX = 10;
	private final float finalSpeedY = 7;
	private final float acelerationX = (float) 0.2;
	private final float decelerationX = (float) 0.1;
	private float lastJumpTime = 0;
	private boolean firstJump = false;
	private float elapsedTime;
	private static Sonic instance;

	private InputHandler inputHandler;

	private TiledMapTileLayer collisionLayer;

	public static Sonic getInstance(TiledMapTileLayer collisionLayer) {
		if (instance == null) {
			instance = new Sonic(collisionLayer);
		}
		return instance;
	}

	private Sonic(TiledMapTileLayer collisionLayer) {
		this.image = new Texture(Gdx.files.internal("sonic.png"));

		setPosition(20, 1000);
		this.setScale((float) 0.5, (float) 0.5);

		inputHandler = new InputHandler();

		this.speed = new Vector2();

		this.collisionLayer = collisionLayer;

		setWidth(64);
		setHeight(64);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
	}

	public void act(float delta) {
		super.act(delta);
		inputHandler.handleYAxisInput(this);
		if (inputHandler.handleXAxisInput(this) == false) {
			decelerate();
		}
		applyGravity();

		setY(getY() + speed.y);
		
		float oldX = getX();
		setX(getX() + speed.x);
		handleCollisionX(oldX);
		
		elapsedTime += Gdx.graphics.getDeltaTime();



	}

	public void dispose() {
		image.dispose();
	}

	public void decelerate() {
		if (speed.x < 0) {
			speed.x = (float) (speed.x + decelerationX);

			if (speed.x > 0) {
				speed.x = 0;
			}
		}
		if (speed.x > 0) {
			speed.x = (float) (speed.x - decelerationX);

			if (speed.x < 0) {
				speed.x = 0;

			}
		}
	}

	public void handleCollisionX(float oldX) {
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		// MapProperties mapProperties = collisionLayer.getCell((int)((getX() +
		// getWidth()) / tileWidth/2), (int)((getY() + getHeight()) /
		// tileHeight/2)).getTile().getProperties();

		if (speed.x > 0) {
			// middle right
			Vector2 rightMiddleBlockPos, rightTopBlockPos, rightBottomBlockPos;
			rightMiddleBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight() / 2) / (tileHeight / 2));
			rightTopBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight()) / (tileHeight / 2));
			rightBottomBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2), getY() / (tileHeight / 2));

			Cell cell = collisionLayer.getCell((int) rightMiddleBlockPos.x, (int) rightMiddleBlockPos.y);
			Cell cellTop = collisionLayer.getCell((int) rightTopBlockPos.x, (int) rightTopBlockPos.y);
			Cell cellBottom = collisionLayer.getCell((int) rightBottomBlockPos.x, (int) rightBottomBlockPos.y);
			
			collisionX = checkCellCollision(cellTop);
			if (collisionX == false)
				collisionX = checkCellCollision(cell);
			if (collisionX == false)
				collisionX = checkCellCollision(cellBottom);
		}
		
		if(collisionX == true) {
			setX(oldX);
			speed.x = 0;
		}

	}

	private boolean checkCellCollision(Cell cell) {
		boolean returnValue = false;
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("blocked")) {
				System.out.println("COLISAO");
				returnValue = true;
			}
		}
		return returnValue;
	}

	private void applyGravity() {
		if (getY() > floor) {
			speed.y = speed.y - Map.getGravity();
		} else if (speed.y < 0 && getY() <= floor) {
			speed.y = 0;
		}
	}

	public float getSpeedX() {
		return speed.x;
	}

	public void setSpeedX(float speedX) {
		this.speed.x = speedX;
	}

	public float getSpeedY() {
		return speed.y;
	}

	public void setSpeedY(float speedY) {
		this.speed.y = speedY;
	}

	public float getLastJumpTime() {
		return lastJumpTime;
	}

	public void setLastJumpTime(float lastJumpTime) {
		this.lastJumpTime = lastJumpTime;
	}

	public boolean isFirstJump() {
		return firstJump;
	}

	public void setFirstJump(boolean firstJump) {
		this.firstJump = firstJump;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public float getFinalSpeedX() {
		return finalSpeedX;
	}

	public float getFinalSpeedY() {
		return finalSpeedY;
	}

	public float getAcelerationX() {
		return acelerationX;
	}

	public float getDecelerationX() {
		return decelerationX;
	}

}