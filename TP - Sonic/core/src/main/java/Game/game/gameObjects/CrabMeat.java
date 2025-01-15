package Game.game.gameObjects;

import Game.game.gameAssets.CrabmeatAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class CrabMeat extends Enemy {

	private CrabmeatAnimationManager animationManager;
	private TiledMapTileLayer collisionLayer;
	private double speed;

	public CrabMeat(int posX, int posY, int map, TiledMapTileLayer collisionLayer) {
		super(posX, posY, map);
		animationManager = new CrabmeatAnimationManager();
		this.collisionLayer = collisionLayer;
		velocity = 3;
		points = 100;
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

	private boolean checkPossibilityToWalk(Cell cellNextTo) {

		return cellNextTo == null;
	}

	public boolean nextBlockIsValid() {

		
		boolean blockIsValid = true;

		if (speed > 0) {
			float tileWidth = collisionLayer.getTileWidth();
			float tileHeight = collisionLayer.getTileHeight();
			
			Vector2 rightMiddleBlockPos;
			rightMiddleBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight() / 2) / (tileHeight / 2));

			Cell cellRightMiddle = collisionLayer.getCell((int) rightMiddleBlockPos.x, (int) rightMiddleBlockPos.y);

			blockIsValid = checkPossibilityToWalk(cellRightMiddle);

		}

		if (speed < 0) {
			float tileWidth = collisionLayer.getTileWidth();
			float tileHeight = collisionLayer.getTileHeight();
			Vector2 leftMiddleBlockPos;
			leftMiddleBlockPos = new Vector2(getX() / (tileWidth / 2), (getY() + getHeight() / 2) / (tileHeight / 2));

			Cell cellLeftMiddle = collisionLayer.getCell((int) leftMiddleBlockPos.x, (int) leftMiddleBlockPos.y);

			blockIsValid = checkPossibilityToWalk(cellLeftMiddle);
		}

		return blockIsValid;
	}

	@Override
	public void move() {
		if (chasing) {

			if (target.getX() < getX()) {
				speed = -velocity;
			}
			if (target.getX() > getX()) {
				speed = velocity;
			}
			if (nextBlockIsValid()) {
				setX((float) (getX() + speed));
			}
		}
	}
}
