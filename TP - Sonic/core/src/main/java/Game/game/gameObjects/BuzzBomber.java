package Game.game.gameObjects;

import Game.game.gameAssets.BuzzBomberAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class BuzzBomber extends Enemy {

	private BuzzBomberAnimationManager animationManager;
	private Vector2 initialPosition;

	public BuzzBomber(float posX, float posY) {
		super(posX, posY);
		animationManager = new BuzzBomberAnimationManager();
		initialPosition = new Vector2(posX, posY);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (chasing) {
			move();
		}

	}

	public boolean isOutOfSeekingRange() {
		return getX() >= initialPos.x + seekingRange || getX() <= initialPos.x - seekingRange;
	}

	public void move() {
		if (!isOutOfSeekingRange()) {

			if (targetPos < getX()) {
				speed = -velocity;
				animationManager.setAction("movementLeft");
			}
			if (targetPos > getX()) {
				speed = velocity;
				animationManager.setAction("movementRight");
			}
		}
	}

}
