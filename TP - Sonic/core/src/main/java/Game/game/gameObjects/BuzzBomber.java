package Game.game.gameObjects;

import Game.game.gameAssets.BuzzBomberAnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BuzzBomber extends Enemy {

	private BuzzBomberAnimationManager animationManager;
	
	public BuzzBomber() {
		//image = new Sprite(new Texture(Gdx.files.internal("mosca.jpg")));
		//image.setSize(128, 128);

		animationManager = new BuzzBomberAnimationManager();
		setPosition(200, 800);
		setWidth(64);
		setHeight(64);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		//batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
		TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
