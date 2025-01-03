package Game.game.gameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Sonic extends GameObject {

	private float speedX = 0;
	private float speedY = 0;
	private final float finalSpeedX = 10;
	private final float finalSpeedY = 7;
	private final float acelerationX = (float) 0.2;
	private final float decelerationX = (float) 0.1;
	private final float gravity = (float) 0.1;
	private float lastJumpTime = 0;
	private boolean firstJump = false;
	private float elapsedTime;

	public Sonic() {
		image = new Texture(Gdx.files.internal("sonic.png"));
		setPosition(320, 240);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
	}

	public void act(float delta) {
		super.act(delta);
		moveX();
		moveY();
		elapsedTime += Gdx.graphics.getDeltaTime();
	}

	public void dispose() {
		image.dispose();
	}

	public void moveX() {

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (speedX < finalSpeedX) {
				speedX = (float) (speedX + acelerationX);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (speedX > -finalSpeedX) {
				speedX = (float) (speedX - acelerationX);
			}
		} else {
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
		setX(getX() + speedX);
	}

	public void moveY() {
		
		System.out.println("position: " + getX() + " - " + getY());
		if (Gdx.input.isKeyPressed(Input.Keys.W) && (elapsedTime - lastJumpTime >= 2 || firstJump == false)) {
			firstJump = true;
			jump();
		}
		if(getY() > 200) {
			speedY = speedY - gravity;
		}else if(speedY < 0 && getY() <= 200){
			speedY = 0;
		}
		
		setY(getY() + speedY);
	}

	public void jump() {
		lastJumpTime = elapsedTime;
		speedY =+ finalSpeedY;
	}
	
	public float getPositionY() {
		return getY();
	}
	
	public float getPositionX() {
		return getX();
	}
}
