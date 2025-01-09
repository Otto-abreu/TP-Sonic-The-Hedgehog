package Game.game.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Background extends GameObject {
	
	private Vector2 initialPosition;
	private int sonicLives = -1;
	
	public Background() {
		image = new Sprite(new Texture(Gdx.files.internal("background.png")));
		image.setX(-400);
		image.setY(-440);
		initialPosition = new Vector2(-400, -440);
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, image.getX(), image.getY(), image.getWidth(), image.getHeight());
		batch.draw(image, image.getX() + image.getWidth(), image.getY(), image.getWidth(), image.getHeight());
		batch.draw(image, image.getX() + image.getWidth()*2, image.getY(), image.getWidth(), image.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void move(float sonicSpeedX, float sonicSpeedY, int sonicLives) {
		if(this.sonicLives == -1) {
			this.sonicLives = sonicLives;
		}else {
			int oldSonicLives = this.sonicLives;
			this.sonicLives = sonicLives;
			if(oldSonicLives != this.sonicLives) {
				image.setX(initialPosition.x);
				image.setY(initialPosition.y);

			}
		}
		image.setY(image.getY() - sonicSpeedY/4);
		image.setX(image.getX() - sonicSpeedX/4);

	}

}
