package Game.game.gameObjects;

import Game.game.gameAssets.SonicAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import Game.game.commander.InputHandler;
import Game.game.map.*;

public class Sonic extends GameObject {

	private int coinsCollected = 0;
	private Vector2 speed;
	private Vector2 oldPosition;
	private final float maxSpeedX = 5;
	private final float maxSpeedY = 5;
	private final int jumpPadLaunchSpeed = 10;
	private final float acelerationX = (float) 0.2;
	private final float decelerationX = (float) 0.1;
	private boolean jumpEnabled = false;
	private boolean jumpPadTouched = false;
	private static Sonic instance;
	private Vector2 initialPosition;

	private InputHandler inputHandler;

	private TiledMapTileLayer collisionLayer;

	private SonicAnimationManager animationManager;

	public static Sonic getInstance(TiledMapTileLayer collisionLayer) {
		if (instance == null) {
			instance = new Sonic(collisionLayer);
		}
		return instance;
	}

	private Sonic(TiledMapTileLayer collisionLayer) {
		image = new Sprite(new Texture(Gdx.files.internal("sonicBasicMotion/sonicBasicMotion1.png")));
		animationManager = new SonicAnimationManager();
		setPosition(100, 1000);
		initialPosition = new Vector2(getX(), getY());

		inputHandler = new InputHandler();

		this.speed = new Vector2();
		this.oldPosition = new Vector2();

		this.collisionLayer = collisionLayer;

		this.bounds = image.getBoundingRectangle();

		setWidth(64);
		setHeight(64);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		this.bounds = new Rectangle(currentFrame.getRegionX(), currentFrame.getRegionY(), currentFrame.getRegionWidth(),
				currentFrame.getRegionHeight());
		batch.draw(currentFrame, this.getX(), this.getY(), getWidth(), getHeight());
	}

	@Override
	public void act(float delta) {

		super.act(delta);

		update();

		System.out.println(getX() + " - " + getY());

	}

	private void update() {
		
		inputHandler.handleYAxisInput(this);
		if (speed.y != 0) {
			animationManager.setAction("jump");
		} else if (speed.x > 0) {
			animationManager.setAction("walkRight");
		} else if (speed.x < 0) {
			animationManager.setAction("walkLeft");
		} else {
			animationManager.setAction("idle");
		}

		if (inputHandler.handleXAxisInput(this) == false) {
			decelerate();
		}

		if (jumpPadTouched == true) {
			speed.y = jumpPadLaunchSpeed;
			jumpPadTouched = false;
		}

		applyGravity();

		oldPosition.x = getX();
		setX(getX() + speed.x);

		oldPosition.y = getY();
		setY(getY() + speed.y);

		handleCollision(oldPosition.x, oldPosition.y);

		elapsedTime += Gdx.graphics.getDeltaTime();
		
		if(getY() <= -10) {
			reSpawn();
		}
	}

	public boolean checkJumpPadTouch(JumpPad jumpPad) {
		boolean returnValue = false;

		if (this.bounds.overlaps(jumpPad.getBounds())) {
			jumpPadTouched = true;
			returnValue = true;
		}

		return returnValue;
	}

	private void decelerate() {
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

	public void handleCollision(float oldX, float oldY) {
		checkCollisionOnX(oldX);
		checkCollisionOnY(oldY);
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public void checkCollisionOnX(float oldX) {

		boolean collisionX = false;

		if (speed.x > 0) {

			float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
			Vector2 rightMiddleBlockPos, rightTopBlockPos;
			rightTopBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight()) / (tileHeight / 2));
			rightMiddleBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight() / 2) / (tileHeight / 2));

			Cell cellMiddle = collisionLayer.getCell((int) rightMiddleBlockPos.x, (int) rightMiddleBlockPos.y);
			Cell cellTop = collisionLayer.getCell((int) rightTopBlockPos.x, (int) rightTopBlockPos.y);

			collisionX = checkCellCollision(cellTop);
			if (collisionX == false)
				collisionX = checkCellCollision(cellMiddle);

		}

		else if (speed.x < 0) {

			float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
			Vector2 leftMiddleBlockPos, leftTopBlockPos;

			leftTopBlockPos = new Vector2(getX() / (tileWidth / 2), (getY() + getHeight()) / (tileHeight / 2));
			leftMiddleBlockPos = new Vector2(getX() / (tileWidth / 2), (getY() + getHeight() / 2) / (tileHeight / 2));

			Cell cellTop = collisionLayer.getCell((int) leftTopBlockPos.x, (int) leftTopBlockPos.y);
			Cell cellMiddle = collisionLayer.getCell((int) leftMiddleBlockPos.x, (int) leftMiddleBlockPos.y);

			collisionX = checkCellCollision(cellTop);
			if (collisionX == false)
				collisionX = checkCellCollision(cellMiddle);
		}

		if (collisionX == true) {
			setX(oldX);
			speed.x = 0;
		}

	}

	public void checkCollisionOnY(float oldY) {

		boolean collisionY = false;

		if (speed.y > 0) {
			float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
			Vector2 topMiddleBlockPos, topLeftBlockPos, topRightBlockPos;

			topLeftBlockPos = new Vector2(getX() / (tileWidth / 2), (getY() + getHeight()) / (tileHeight / 2));
			topMiddleBlockPos = new Vector2(((getX() + getWidth() / 2) / (tileWidth / 2)),
					(getY() + getHeight()) / (tileHeight / 2));
			topRightBlockPos = new Vector2((getX() + getWidth() / 2), (getY() + getHeight()) / (tileHeight / 2));

			Cell cellLeft = collisionLayer.getCell((int) topLeftBlockPos.x, (int) topLeftBlockPos.y);
			Cell cellMiddle = collisionLayer.getCell((int) topMiddleBlockPos.x, (int) topMiddleBlockPos.y);
			Cell cellRight = collisionLayer.getCell((int) topRightBlockPos.x, (int) topRightBlockPos.y);

			collisionY = checkCellCollision(cellLeft);
			if (collisionY == false)
				collisionY = checkCellCollision(cellMiddle);
			if (collisionY == false)
				collisionY = checkCellCollision(cellRight);

		} else if (speed.y < 0) {
			float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
			Vector2 bottomMiddleBlockPos, bottomLeftBlockPos, bottomRightBlockPos;

			bottomLeftBlockPos = new Vector2(getX() / (tileWidth / 2), getY() / (tileHeight / 2));
			bottomMiddleBlockPos = new Vector2((getX() + getWidth() / 2) / (tileWidth / 2), getY() / (tileHeight / 2));
			bottomRightBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2), getY() / (tileHeight / 2));

			Cell cellLeft = collisionLayer.getCell((int) bottomLeftBlockPos.x, (int) bottomLeftBlockPos.y);
			Cell cellMiddle = collisionLayer.getCell((int) bottomMiddleBlockPos.x, (int) bottomMiddleBlockPos.y);
			Cell cellRight = collisionLayer.getCell((int) bottomRightBlockPos.x, (int) bottomRightBlockPos.y);

			collisionY = checkCellCollision(cellLeft);
			if (collisionY == false)
				collisionY = checkCellCollision(cellMiddle);
			if (collisionY == false)
				collisionY = checkCellCollision(cellRight);
		}

		if (collisionY == true) {
			setY(oldY);
			speed.y = 0;
			jumpEnabled = true;
		} else {
			jumpEnabled = false;
		}
	}

	private boolean checkCellCollision(Cell cell) {
		boolean returnValue = false;
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("blocked")) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	private void applyGravity() {
		speed.y = speed.y - Map.getGravity();
	}

	private void reSpawn() {
		setPosition(initialPosition.x, initialPosition.y);
		coinsCollected = 0;
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

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public float getFinalSpeedX() {
		return maxSpeedX;
	}

	public float getFinalSpeedY() {
		return maxSpeedY;
	}

	public float getAcelerationX() {
		return acelerationX;
	}

	public float getDecelerationX() {
		return decelerationX;
	}

	public boolean isJumpEnabled() {
		return jumpEnabled;
	}

	public void setJumpEnabled(boolean jumpEnabled) {
		this.jumpEnabled = jumpEnabled;
	}

	public void setInitialPosition() {
		this.setX(initialPosition.x);
		this.setY(initialPosition.y);
		speed.x = 0;
		speed.y = 0;
	}

	public int getCoinsCollected() {
		return coinsCollected;
	}

	public void setCoinsCollected(int coinsCollected) {
		this.coinsCollected = coinsCollected;
	}
	
	public boolean isDead() {
		return getY() <= -10 && coinsCollected == 0;
	}

}