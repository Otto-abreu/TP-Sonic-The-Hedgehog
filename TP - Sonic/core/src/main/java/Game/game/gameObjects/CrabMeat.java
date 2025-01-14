package Game.game.gameObjects;

import Game.game.gameAssets.CrabmeatAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CrabMeat extends Enemy{

	private CrabmeatAnimationManager animationManager;
	
	public CrabMeat(int posX, int posY) {
		super(posX, posY);
		animationManager = new CrabmeatAnimationManager();
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(chasing) {
			move();
		}
	}
}
