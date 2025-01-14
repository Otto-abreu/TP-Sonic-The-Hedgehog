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
	
	public CrabMeat(int posX, int posY, TiledMapTileLayer collisionLayer) {
		super(posX, posY);
		animationManager = new CrabmeatAnimationManager();
		this.collisionLayer = collisionLayer;
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

		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		boolean blockIsValid = true;

		if (speed > 0) {
			Vector2 rightMiddleBlockPos, rightBottomBlockPos;
			rightMiddleBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2),
					(getY() + getHeight() / 2) / (tileHeight / 2));
			rightBottomBlockPos = new Vector2((getX() + getWidth()) / (tileWidth / 2), getY() / (tileHeight / 2));
			Cell cellRightMiddle = collisionLayer.getCell((int) rightMiddleBlockPos.x, (int) rightMiddleBlockPos.y);
			Cell cellRightBottom = collisionLayer.getCell((int) rightBottomBlockPos.x, (int) rightBottomBlockPos.y);

			blockIsValid = checkPossibilityToWalk(cellRightMiddle);

		}

		if (speed < 0) {
			Vector2 leftMiddleBlockPos, leftBottomBlockPos;

			leftMiddleBlockPos = new Vector2(getX() / (tileWidth / 2), (getY() + getHeight() / 2) / (tileHeight / 2));
			leftBottomBlockPos = new Vector2(getX() / (tileWidth / 2), getY() / (tileHeight / 2));
			Cell cellLeftMiddle = collisionLayer.getCell((int) leftMiddleBlockPos.x, (int) leftMiddleBlockPos.y);
			Cell cellLeftBottom = collisionLayer.getCell((int) leftBottomBlockPos.x, (int) leftBottomBlockPos.y);

			blockIsValid = checkPossibilityToWalk(cellLeftMiddle);
		}

		return blockIsValid;
	}
	
	@Override
	public void move() {
		if (chasing) {

			if (targetPos < getX()) {
				speed = -velocity;
			}
			if (targetPos > getX()) {
				speed = velocity;
			}
			if (nextBlockIsValid()) {
				setX(getX() + speed);
			}
		}
	}
}
