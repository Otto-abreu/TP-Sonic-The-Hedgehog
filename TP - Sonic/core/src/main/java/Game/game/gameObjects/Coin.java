package Game.game.gameObjects;

import Game.game.gameAssets.CoinAnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends GameObject{
	
	private static int quantity = 0;
	private int id;
	
	private int belongingMap;
	
	private CoinAnimationManager animationManager;
	
	public Coin(float posX, float posY, int map, CoinAnimationManager animationManager) {
	
		//image = new Sprite(new Texture(Gdx.files.internal("coin.png")));
		//image.setSize(64, 64);
		this.animationManager = animationManager;
		setPosition(posX, posY);
		setWidth(64); setHeight(64);
		belongingMap = map;
		
		this.bounds = new Rectangle(posX, posY, 64, 64);
		//this.bounds = image.getBoundingRectangle();
		quantity++;
		id = quantity;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		//batch.draw(image, this.getX(), this.getY(), image.getWidth(), image.getHeight());
		TextureRegion currentFrame = animationManager.getCurrentFrame(com.badlogic.gdx.Gdx.graphics.getDeltaTime());
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public int getBelongingMap() {
		return belongingMap;
	}
	
	public int getId() {
		return id;
	}

}
