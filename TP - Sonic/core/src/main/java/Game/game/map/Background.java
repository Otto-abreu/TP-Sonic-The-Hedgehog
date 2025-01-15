package Game.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {

	private Vector2 initialPosition;
	private Sprite image;

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
		batch.draw(image, image.getX() + image.getWidth() * 2, image.getY(), image.getWidth(), image.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void move(float sonicSpeedX, float sonicSpeedY) {

		image.setY(image.getY() - sonicSpeedY / 4);
		image.setX(image.getX() - sonicSpeedX / 4);

	}

	public void initialPos() {

		image.setX(initialPosition.x);
		image.setY(initialPosition.y);
	}

}
