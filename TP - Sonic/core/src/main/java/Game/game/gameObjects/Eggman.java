package Game.game.gameObjects;

import Game.game.gameAssets.EggManAnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Eggman extends Enemy{

	private EggManAnimationManager animationManager;
	
	public Eggman(float posX, float posY) {
		super(posX, posY);
		animationManager = new EggManAnimationManager();

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
