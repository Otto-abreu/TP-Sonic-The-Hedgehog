package Game.game.gameActors;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import Game.game.controllers.InputHandler;

public class Sonic extends GameObject {

	private Texture image;
	private float speedX = 0;
	private float speedY = 0;
	private final float finalSpeedX = 10;
	private final float finalSpeedY = 7;
	private final float acelerationX = (float) 0.2;
	private final float decelerationX = (float) 0.1;
	private float lastJumpTime = 0;
	private boolean firstJump = false;
	private float elapsedTime;
	private static Sonic instance;

	private InputHandler inputHandler;

	
	public static Sonic getInstance() {
		if(instance == null) {
			instance = new Sonic();
		}
		return instance;
	}
	private Sonic() {
		this.image = new Texture(Gdx.files.internal("sonic.png"));

		setPosition(20, 350);
		this.setScale((float) 0.5, (float) 0.5);

		inputHandler = new InputHandler();
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
		
		setY(getY() + speedY);

		setX(getX() + speedX);

		elapsedTime += Gdx.graphics.getDeltaTime();
	}

	public void dispose() {
		image.dispose();
	}

	public void decelerate() {
		if (speedX < 0) {
			speedX = (float) (speedX + decelerationX);

			if (speedX > 0) {
				speedX = 0;
			}
		}
		if (speedX > 0) {
			speedX = (float) (speedX - decelerationX);

			if (speedX < 0) {
				speedX = 0;
			}
		}
	}
	
	public void applyGravity() {
		if (getY() > 200) {
			speedY = speedY - Map.getGravity();
		} else if (speedY < 0 && getY() <= 200) {
			speedY = 0;
		}
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
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