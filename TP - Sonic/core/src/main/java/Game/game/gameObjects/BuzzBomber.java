package Game.game.gameObjects;

import Game.game.gameAssets.BuzzBomberAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BuzzBomber extends Enemy{

	private BuzzBomberAnimationManager animationManager;
	
	public BuzzBomber(float posX, float posY) {
		super(posX, posY);
		animationManager = new BuzzBomberAnimationManager();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
