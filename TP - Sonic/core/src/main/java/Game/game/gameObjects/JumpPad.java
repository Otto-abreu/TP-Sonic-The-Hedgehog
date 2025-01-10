package Game.game.gameObjects;

import Game.game.gameAssets.JumpPadsAnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class JumpPad extends GameObject{
	
	private int belongingMap;
	
	private JumpPadsAnimationManager animationManager;
	
	public JumpPad(float posX, float posY, int map/*, JumpPadsAnimationManager animationManager*/) {
		image = new Sprite(new Texture(Gdx.files.internal("trampolim.png")));
		image.setSize(64, 64);
		
		//animationManager = new JumpPadsAnimationManager();
		setPosition(posX, posY);
		setWidth(64);
		setHeight(64);
		belongingMap = map;
		
		//this.bounds = new Rectangle(posX, posY, 64, 64);
		this.bounds = image.getBoundingRectangle();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
		//TextureRegion currentFrame = animationManager.getCurrentFrame(Gdx.graphics.getDeltaTime());
		//batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public void activate() {
		animationManager.activate();
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public int getBelongingMap() {
		return belongingMap;
	}


}
