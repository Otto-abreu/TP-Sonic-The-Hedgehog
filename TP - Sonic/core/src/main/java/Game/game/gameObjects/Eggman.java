package Game.game.gameObjects;

import Game.game.gameAssets.EggManAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Eggman extends Enemy {
	
	private int lives = 10;
	private EggManAnimationManager animationManager;

	public Eggman(float posX, float posY, int map) {
		super(posX, posY, map);
		animationManager = new EggManAnimationManager();
		initialPos.x = getX();
		initialPos.y = getY();
		velocity = 1.5;
		points = 1500;
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

	public boolean targetOutOfSeekingRange() {
		return target.getX() - initialPos.x >= seekingRange || target.getX() - initialPos.x <= -seekingRange;
	}

	public void move() {
		if (chasing) {
			if (!this.isOutOfSeekingRange() && !this.targetOutOfSeekingRange()) {

				if (target.getX() < getX()) {
					animationManager.setAction("movementLeft");
					setX((float) (getX() - velocity));

				}
				if (target.getX() > getX()) {
					animationManager.setAction("movementRight");
					setX((float) (getX() + velocity));

				}

				if (target.getY() < getY()) {
					setY((float) (getY() - velocity));
				}

				if (target.getY() > getY()) {
					setY((float) (getY() + velocity));
				}

			} 
		}
		if(!chasing) {
			if (initialPos.x < getX()) {
				animationManager.setAction("movementLeft");
				setX((float) (getX() - velocity));

			}
			if (initialPos.x > getX()) {
				animationManager.setAction("movementRight");
				setX((float) (getX() + velocity));

			}
			if (initialPos.y < getY()) {
				setY((float) (getY() - velocity));
			}
			if (initialPos.y > getY()) {
				setY((float) (getY() + velocity));
			}
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
